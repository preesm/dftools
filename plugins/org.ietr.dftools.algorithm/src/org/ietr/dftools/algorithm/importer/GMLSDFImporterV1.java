/**
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2018) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017 - 2018)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Hervé Yviquel <hyviquel@gmail.com> (2012)
 * Jonathan Piat <jpiat@laas.fr> (2011)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to help prototyping
 * parallel applications using dataflow formalism.
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
package org.ietr.dftools.algorithm.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.eclipse.core.runtime.Path;
import org.ietr.dftools.algorithm.demo.SDFAdapterDemo;
import org.ietr.dftools.algorithm.factories.SDFEdgeFactory;
import org.ietr.dftools.algorithm.factories.SDFVertexFactory;
import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.CodeRefinement;
import org.ietr.dftools.algorithm.model.InterfaceDirection;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * Importer for SDF graphs.
 *
 * @author jpiat
 */
public class GMLSDFImporterV1 extends GMLImporter<SDFGraph, SDFAbstractVertex, SDFEdge> {

  /**
   * Main function allowing to debug the class.
   *
   * @param args
   *          the arguments
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  public static void main(final String[] args) throws InvalidExpressionException {
    final SDFAdapterDemo applet = new SDFAdapterDemo();
    final GMLSDFImporterV1 importer = new GMLSDFImporterV1();
    try {
      final SDFGraph graph = importer.parse(new File("D:\\Preesm\\trunk\\tests\\IDCT2D\\idct2dCadOptim.graphml"));
      applet.init(graph);
    } catch (FileNotFoundException | InvalidModelException e) {
      e.printStackTrace();
    }

  }

  /**
   * COnstructs a new importer for SDF graphs.
   */
  public GMLSDFImporterV1() {
    super(new SDFEdgeFactory());
  }

  /**
   * Parses an Edge in the DOM document.
   *
   * @param edgeElt
   *          The DOM Element
   * @param parentGraph
   *          The parent Graph of this Edge
   * @throws InvalidModelException
   *           the invalid model exception
   */
  @Override
  public void parseEdge(final Element edgeElt, final SDFGraph parentGraph) throws InvalidModelException {
    final SDFAbstractVertex vertexSource = this.vertexFromId.get(edgeElt.getAttribute("source"));
    final SDFAbstractVertex vertexTarget = this.vertexFromId.get(edgeElt.getAttribute("target"));

    SDFInterfaceVertex sourcePort = null;
    SDFInterfaceVertex targetPort = null;
    final String sourcePortName = edgeElt.getAttribute("sourceport");
    for (final SDFInterfaceVertex sinksPort : vertexSource.getSinks()) {
      if (sinksPort.getName().equals(sourcePortName)) {
        sourcePort = sinksPort;
      }
    }
    if (sourcePort == null) {
      sourcePort = new SDFSinkInterfaceVertex();
      sourcePort.setName(sourcePortName);
      vertexSource.addSink(sourcePort);
    }
    final String targetPortName = edgeElt.getAttribute("targetport");
    for (final SDFInterfaceVertex sourcesPort : vertexTarget.getSources()) {
      if (sourcesPort.getName().equals(targetPortName)) {
        targetPort = sourcesPort;
      }
    }
    if (targetPort == null) {
      targetPort = new SDFSourceInterfaceVertex();
      targetPort.setName(targetPortName);
      vertexTarget.addSource(targetPort);
    }

    final SDFEdge edge = parentGraph.addEdge(vertexSource, vertexTarget);
    edge.setSourceInterface(sourcePort);
    vertexSource.setInterfaceVertexExternalLink(edge, sourcePort);
    edge.setTargetInterface(targetPort);
    vertexTarget.setInterfaceVertexExternalLink(edge, targetPort);
    parseKeys(edgeElt, edge);
  }

  /**
   * Parses a Graph in the DOM document.
   *
   * @param graphElt
   *          The graph Element in the DOM document
   * @return The parsed graph
   * @throws InvalidModelException
   *           the invalid model exception
   */
  @Override
  public SDFGraph parseGraph(final Element graphElt) throws InvalidModelException {
    final SDFGraph graph = new SDFGraph((SDFEdgeFactory) this.edgeFactory);
    final NodeList childList = graphElt.getChildNodes();
    parseParameters(graph, graphElt);
    parseVariables(graph, graphElt);
    for (int i = 0; i < childList.getLength(); i++) {
      if (childList.item(i).getNodeName().equals("node")) {
        final Element vertexElt = (Element) childList.item(i);
        parseNode(vertexElt, graph);
      }
    }
    for (int i = 0; i < childList.getLength(); i++) {
      if (childList.item(i).getNodeName().equals("edge")) {
        final Element edgeElt = (Element) childList.item(i);
        parseEdge(edgeElt, graph);
      }
    }
    parseKeys(graphElt, graph);
    return graph;
  }

  /**
   * Parses the graph description.
   *
   * @param vertex
   *          the vertex
   * @param parentElt
   *          the parent elt
   * @throws InvalidModelException
   *           the invalid model exception
   */
  protected void parseGraphDescription(final SDFAbstractVertex vertex, final Element parentElt)
      throws InvalidModelException {
    final NodeList childList = parentElt.getChildNodes();
    for (int i = 0; i < childList.getLength(); i++) {
      if (childList.item(i).getNodeName().equals("data")
          && ((Element) childList.item(i)).getAttribute("key").equals(AbstractVertex.REFINEMENT)) {
        final Element graphDesc = (Element) childList.item(i);
        final String path = graphDesc.getTextContent();
        if (path.contains(".graphml")) {
          if ((this.path != null) && (path.length() > 0)) {
            final String directoryPath = this.path.substring(0, this.path.lastIndexOf(File.separator) + 1);
            final GMLSDFImporter importer = new GMLSDFImporter();
            try {
              final File refinementFile = new File(directoryPath + path);
              String fileName = refinementFile.getName();
              fileName = fileName.substring(0, fileName.indexOf('.'));
              SDFGraph refine;
              try {
                refine = importer.parse(refinementFile);
              } catch (FileNotFoundException | InvalidModelException e) {
                final GMLGenericImporter genericImporter = new GMLGenericImporter();
                refine = (SDFGraph) genericImporter.parse(refinementFile);
              }
              refine.setName(fileName);
              vertex.setGraphDescription(refine);
              for (final SDFAbstractVertex refineVertex : refine.vertexSet()) {
                if (refineVertex instanceof SDFInterfaceVertex) {
                  if (((SDFInterfaceVertex) refineVertex).getDirection() == InterfaceDirection.Input) {
                    vertex.addSource(((SDFInterfaceVertex) refineVertex).clone());
                  } else if (((SDFInterfaceVertex) refineVertex).getDirection() == InterfaceDirection.Output) {
                    vertex.addSink(((SDFInterfaceVertex) refineVertex).clone());
                  }
                }
              }
            } catch (final FileNotFoundException e) {
              e.printStackTrace();
            }
          }
        } else if (path.length() > 0) {
          vertex.setRefinement(new CodeRefinement(new Path(path)));
        }
      }
    }
  }

  /**
   * Parses a Vertex from the DOM document.
   *
   * @param vertexElt
   *          The node Element in the DOM document
   * @param parentGraph
   *          the parent graph
   * @return The parsed node
   * @throws InvalidModelException
   *           the invalid model exception
   */
  @Override
  @SuppressWarnings("deprecation")
  public SDFAbstractVertex parseNode(final Element vertexElt, final SDFGraph parentGraph) throws InvalidModelException {

    SDFAbstractVertex vertex;
    final Map<String, String> attributes = new LinkedHashMap<>();
    for (int i = 0; i < vertexElt.getAttributes().getLength(); i++) {
      attributes.put(vertexElt.getAttributes().item(i).getNodeName(), vertexElt.getAttributes().item(i).getNodeValue());
    }
    vertex = SDFVertexFactory.getInstance().createVertex(attributes);
    parentGraph.addVertex(vertex);
    vertex.setId(vertexElt.getAttribute("id"));
    vertex.setName(vertexElt.getAttribute("id"));
    parseKeys(vertexElt, vertex);
    this.vertexFromId.put(vertex.getId(), vertex);
    parseArguments(vertex, vertexElt);
    parseGraphDescription(vertex, vertexElt);
    return vertex;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.ietr.dftools.algorithm.importer.GMLImporter#parsePort(org.w3c.dom.Element,
   * org.ietr.dftools.algorithm.model.AbstractGraph)
   */
  @Override
  public SDFAbstractVertex parsePort(final Element portElt, final SDFGraph parentGraph) throws InvalidModelException {
    return null;
  }

}
