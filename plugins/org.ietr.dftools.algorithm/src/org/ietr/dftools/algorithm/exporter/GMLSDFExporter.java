/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2011)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
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
 *******************************************************************************/
package org.ietr.dftools.algorithm.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.parameters.Argument;
import org.ietr.dftools.algorithm.model.parameters.Parameter;
import org.ietr.dftools.algorithm.model.parameters.Variable;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.w3c.dom.Element;

/**
 * This class represent a GML exporter for SDF
 * 
 * @author jpiat
 * 
 */
public class GMLSDFExporter extends GMLExporter<SDFAbstractVertex, SDFEdge> {

	/**
	 * Creates a graph to test the Explorer
	 * 
	 * @return The created Graph
	 */
	public static SDFGraph createTestComGraph() {

		SDFGraph graph = new SDFGraph();

		// test_com_basique
		SDFInterfaceVertex sensorInt = new SDFSourceInterfaceVertex();
		sensorInt.setName("sensor_Int");
		graph.addVertex(sensorInt);

		SDFVertex gen5 = new SDFVertex();
		gen5.setName("Gen5");
		graph.addVertex(gen5);

		SDFVertex recopie5 = new SDFVertex();
		recopie5.setName("recopie_5");
		graph.addVertex(recopie5);

		SDFInterfaceVertex acqData = new SDFSinkInterfaceVertex();
		acqData.setName("acq_data");
		graph.addVertex(acqData);

		gen5.addArgument(new Argument("NB_COPY", "100"));

		SDFEdge sensGen = graph.addEdge(sensorInt, gen5);
		sensGen.setProd(new SDFIntEdgePropertyType(1));
		sensGen.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge genRec = graph.addEdge(gen5, recopie5);
		genRec.setProd(new SDFIntEdgePropertyType(2));
		genRec.setCons(new SDFIntEdgePropertyType(3));

		SDFEdge genAcq = graph.addEdge(gen5, acqData);
		genAcq.setProd(new SDFIntEdgePropertyType(1));
		genAcq.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge recAcq = graph.addEdgeWithInterfaces(recopie5, acqData);
		recAcq.setProd(new SDFIntEdgePropertyType(3));
		recAcq.setCons(new SDFIntEdgePropertyType(2));

		graph.addParameter(new Parameter("SIZE"));
		graph.addParameter(new Parameter("NB_COPY"));

		graph.addVariable(new Variable("a", "5"));
		graph.addVariable(new Variable("b", "10"));

		return graph;
	}

	/**
	 * Tests this exporter behavior
	 * 
	 * @param args
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		SDFGraph graph = createTestComGraph();
		GMLGenericExporter exporter = new GMLGenericExporter();
		exporter.export((AbstractGraph) graph, "./test.graphml");
	}

	private String path;

	/**
	 * Creates a new Instance of GMLExporter
	 */
	public GMLSDFExporter() {
		super();
	}

	@Override
	public void export(AbstractGraph<SDFAbstractVertex, SDFEdge> graph,
			String path) {
		this.path = path;
		try {
			exportGraph(graph);
			transform(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Export an Edge in the Document
	 * 
	 * @param edge
	 *            The edge to export
	 * @param parentELement
	 *            The DOM document parent Element
	 */
	@Override
	protected Element exportEdge(SDFEdge edge, Element parentELement) {
		String sourcePort = "";
		String targetPort = "";
		if (edge.getSourceInterface() != null) {
			sourcePort = edge.getSourceInterface().getName();
		}
		if (edge.getTargetInterface() != null) {
			targetPort = edge.getTargetInterface().getName();
		}
		Element edgeElt = createEdge(parentELement, edge.getSource().getName(),
				edge.getTarget().getName(), sourcePort, targetPort);
		exportKeys(edge, "edge", edgeElt);
		return edgeElt;
	}

	/**
	 * Exports a Graph in the DOM document
	 * 
	 * @param graph
	 *            The graph to export
	 * @param out
	 *            The OutputStream to write
	 */
	@Override
	public Element exportGraph(AbstractGraph<SDFAbstractVertex, SDFEdge> graph) {
		addKeySet(rootElt);
		SDFGraph myGraph = (SDFGraph) graph;
		Element graphElt = createGraph(rootElt, true);
		graphElt.setAttribute("edgedefault", "directed");
		graphElt.setAttribute("kind", "sdf");
		exportKeys(graph, "graph", graphElt);
		if (myGraph.getParameters() != null) {
			exportParameters(myGraph.getParameters(), graphElt);
		}
		if (myGraph.getVariables() != null) {
			exportVariables(myGraph.getVariables(), graphElt);
		}
		for (SDFAbstractVertex child : myGraph.vertexSet()) {
			exportNode(child, graphElt);
		}
		for (SDFEdge edge : myGraph.edgeSet()) {
			exportEdge(edge, graphElt);
		}
		return graphElt;
	}

	/**
	 * Exports a Vertex in the DOM document
	 * 
	 * @param vertex
	 *            The vertex to export
	 * @param parentELement
	 *            The parent Element in the DOM document
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Element exportNode(SDFAbstractVertex vertex, Element parentELement) {

		Element vertexElt = createNode(parentELement, vertex.getName());
		if (vertex instanceof SDFInterfaceVertex) {
			vertexElt.setAttribute("port_direction",
					((SDFInterfaceVertex) vertex).getDirection().toString());
		}
		
		if (vertex.getGraphDescription() != null
				&& vertex.getGraphDescription().getName().length() > 0) {
			String filePath = vertex.getGraphDescription().getName();
			if (!filePath.contains(".graphml")) {
				filePath = filePath + ".graphml";
				vertex.getGraphDescription().setName(filePath);
			}
			filePath.replace(File.separator, "/");
			String thisPathPrefix = path.substring(0,
					path.lastIndexOf(File.separator) + 1);

			if (filePath.lastIndexOf("/") > 0
					&& filePath.contains(thisPathPrefix)) {
				if (filePath.compareTo(thisPathPrefix) > 0) {
					vertex.getGraphDescription().setName(
							filePath.substring(filePath.length()
									- filePath.compareTo(thisPathPrefix)));
					GMLSDFExporter decExporter = new GMLSDFExporter();
					decExporter.export(
							vertex.getGraphDescription(),
							filePath.substring(filePath.length()
									- filePath.compareTo(thisPathPrefix)));
				}
			} else {
				GMLSDFExporter decExporter = new GMLSDFExporter();
				decExporter.export(vertex.getGraphDescription(), thisPathPrefix
						+ filePath);
			}
		}
		exportKeys(vertex, "node", vertexElt);
		if (vertex.getArguments() != null) {
			exportArguments(vertex.getArguments(), vertexElt);
		}
		return vertexElt;
	}

	/**
	 * Exports an interface
	 * 
	 * @param interfaceVertex
	 *            The interface to export
	 * @param parentELement
	 *            The DOM parent Element of this Interface
	 */
	@Override
	protected Element exportPort(SDFAbstractVertex interfaceVertex,
			Element parentELement) {
		Element interfaceElt = createPort(parentELement,
				interfaceVertex.getName());
		interfaceElt.setAttribute("port_direction",
				((SDFInterfaceVertex) interfaceVertex).getDirection()
						.toString());
		exportKeys(interfaceVertex, "port", interfaceElt);
		return interfaceElt;
	}

}
