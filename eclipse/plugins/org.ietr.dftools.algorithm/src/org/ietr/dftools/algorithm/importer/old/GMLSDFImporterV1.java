package org.ietr.dftools.algorithm.importer.old;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.ietr.dftools.algorithm.demo.SDFAdapterDemo;
import org.ietr.dftools.algorithm.factories.SDFEdgeFactory;
import org.ietr.dftools.algorithm.factories.SDFVertexFactory;
import org.ietr.dftools.algorithm.importer.GMLGenericImporter;
import org.ietr.dftools.algorithm.importer.GMLImporter;
import org.ietr.dftools.algorithm.importer.GMLSDFImporter;
import org.ietr.dftools.algorithm.importer.InvalidModelException;
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

/**
 * Importer for SDF graphs
 * 
 * @author jpiat
 * 
 */
public class GMLSDFImporterV1 extends
		GMLImporter<SDFGraph, SDFAbstractVertex, SDFEdge> {

	/**
	 * Main function allowing to debug the class
	 * 
	 * @param args
	 * @throws InvalidExpressionException
	 */
	public static void main(String[] args) throws InvalidExpressionException {
		SDFAdapterDemo applet = new SDFAdapterDemo();
		GMLSDFImporterV1 importer = new GMLSDFImporterV1();
		try {
			SDFGraph graph = importer
					.parse(new File(
							"D:\\Preesm\\trunk\\tests\\IDCT2D\\idct2dCadOptim.graphml"));
			applet.init(graph);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * COnstructs a new importer for SDF graphs
	 */
	public GMLSDFImporterV1() {
		super(new SDFEdgeFactory());
	}

	/**
	 * Parses an Edge in the DOM document
	 * 
	 * @param edgeElt
	 *            The DOM Element
	 * @param parentGraph
	 *            The parent Graph of this Edge
	 */
	public void parseEdge(Element edgeElt, SDFGraph parentGraph)
			throws InvalidModelException {
		SDFAbstractVertex vertexSource = vertexFromId.get(edgeElt
				.getAttribute("source"));
		SDFAbstractVertex vertexTarget = vertexFromId.get(edgeElt
				.getAttribute("target"));

		SDFInterfaceVertex sourcePort = null;
		SDFInterfaceVertex targetPort = null;
		String sourcePortName = edgeElt.getAttribute("sourceport");
		for (SDFInterfaceVertex sinksPort : vertexSource.getSinks()) {
			if (sinksPort.getName().equals(sourcePortName)) {
				sourcePort = sinksPort;
			}
		}
		if (sourcePort == null) {
			sourcePort = new SDFSinkInterfaceVertex();
			sourcePort.setName(sourcePortName);
			vertexSource.addSink(sourcePort);
		}
		String targetPortName = edgeElt.getAttribute("targetport");
		for (SDFInterfaceVertex sourcesPort : vertexTarget.getSources()) {
			if (sourcesPort.getName().equals(targetPortName)) {
				targetPort = sourcesPort;
			}
		}
		if (targetPort == null) {
			targetPort = new SDFSourceInterfaceVertex();
			targetPort.setName(targetPortName);
			vertexTarget.addSource(targetPort);
		}

		SDFEdge edge = parentGraph.addEdge(vertexSource, vertexTarget);
		edge.setSourceInterface(sourcePort);
		vertexSource.setInterfaceVertexExternalLink(edge, sourcePort);
		edge.setTargetInterface(targetPort);
		vertexTarget.setInterfaceVertexExternalLink(edge, targetPort);
		parseKeys(edgeElt, edge);
	}

	/**
	 * Parses a Graph in the DOM document
	 * 
	 * @param graphElt
	 *            The graph Element in the DOM document
	 * @return The parsed graph
	 */
	public SDFGraph parseGraph(Element graphElt) throws InvalidModelException {
		SDFGraph graph = new SDFGraph((SDFEdgeFactory) edgeFactory);
		NodeList childList = graphElt.getChildNodes();
		parseParameters(graph, graphElt);
		parseVariables(graph, graphElt);
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("node")) {
				Element vertexElt = (Element) childList.item(i);
				parseNode(vertexElt, graph);
			}
		}
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("edge")) {
				Element edgeElt = (Element) childList.item(i);
				parseEdge(edgeElt, graph);
			}
		}
		parseKeys(graphElt, graph);
		return graph;
	}

	protected void parseGraphDescription(SDFAbstractVertex vertex,
			Element parentElt) throws InvalidModelException {
		NodeList childList = parentElt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("data")
					&& ((Element) childList.item(i)).getAttribute("key")
							.equals(AbstractVertex.REFINEMENT)) {
				Element graphDesc = (Element) childList.item(i);
				String path = graphDesc.getTextContent();
				if (path.contains(".graphml")) {
					if (this.path != null && path.length() > 0) {
						String directoryPath = this.path.substring(0,
								this.path.lastIndexOf(File.separator) + 1);
						GMLSDFImporter importer = new GMLSDFImporter();
						try {
							File refinementFile = new File(directoryPath + path);
							String fileName = refinementFile.getName();
							fileName = fileName.substring(0,
									fileName.indexOf('.'));
							SDFGraph refine;
							try {
								refine = (SDFGraph) importer
										.parse(refinementFile);
							} catch (Exception e) {
								GMLGenericImporter genericImporter = new GMLGenericImporter();
								refine = (SDFGraph) genericImporter
										.parse(refinementFile);
							}
							refine.setName(fileName);
							vertex.setGraphDescription(refine);
							for (SDFAbstractVertex refineVertex : refine
									.vertexSet()) {
								if (refineVertex instanceof SDFInterfaceVertex) {
									if (((SDFInterfaceVertex) refineVertex)
											.getDirection() == InterfaceDirection.Input) {
										vertex.addSource(((SDFInterfaceVertex) refineVertex)
												.clone());
									} else if (((SDFInterfaceVertex) refineVertex)
											.getDirection() == InterfaceDirection.Output) {
										vertex.addSink(((SDFInterfaceVertex) refineVertex)
												.clone());
									}
								}
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvalidModelException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (path.length() > 0) {
					vertex.setRefinement(new CodeRefinement(path));
				}
			}
		}
	}

	/**
	 * Parses a Vertex from the DOM document
	 * 
	 * @param vertexElt
	 *            The node Element in the DOM document
	 * @return The parsed node
	 */
	@SuppressWarnings("deprecation")
	public SDFAbstractVertex parseNode(Element vertexElt, SDFGraph parentGraph)
			throws InvalidModelException {

		SDFAbstractVertex vertex;
		HashMap<String, String> attributes = new HashMap<String, String>();
		for (int i = 0; i < vertexElt.getAttributes().getLength(); i++) {
			attributes.put(vertexElt.getAttributes().item(i).getNodeName(),
					vertexElt.getAttributes().item(i).getNodeValue());
		}
		vertex = SDFVertexFactory.getInstance().createVertex(attributes);
		parentGraph.addVertex(vertex);
		vertex.setId(vertexElt.getAttribute("id"));
		vertex.setName(vertexElt.getAttribute("id"));
		parseKeys(vertexElt, vertex);
		vertexFromId.put(vertex.getId(), vertex);
		parseArguments(vertex, vertexElt);
		parseGraphDescription(vertex, vertexElt);
		return vertex;
	}

	@Override
	public SDFAbstractVertex parsePort(Element portElt, SDFGraph parentGraph)
			throws InvalidModelException {
		return null;
	}

}
