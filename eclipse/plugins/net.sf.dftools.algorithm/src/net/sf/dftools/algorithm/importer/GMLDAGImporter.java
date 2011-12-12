package net.sf.dftools.algorithm.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import net.sf.dftools.algorithm.exporter.GMLDAGExporter;
import net.sf.dftools.algorithm.factories.DAGEdgeFactory;
import net.sf.dftools.algorithm.model.dag.DAGEdge;
import net.sf.dftools.algorithm.model.dag.DAGVertex;
import net.sf.dftools.algorithm.model.dag.DirectedAcyclicGraph;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Importer for DAG graphs
 * 
 * @author jpiat
 * 
 */
public class GMLDAGImporter extends
		GMLImporter<DirectedAcyclicGraph, DAGVertex, DAGEdge> {

	/**
	 * Test method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GMLDAGImporter importer = new GMLDAGImporter();
			DirectedAcyclicGraph graph = importer.parse(new File(
					"C:\\test_dag_gml.xml"));
			GMLDAGExporter exporter = new GMLDAGExporter();
			exporter.setKeySet(importer.getKeySet());
			exporter.exportGraph(graph);
			exporter.transform(new FileOutputStream("C:\\test_dag_gml_2.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Constructs a new DAG importer with the specified factories
	 */
	public GMLDAGImporter() {
		super(new DAGEdgeFactory());
	}

	@Override
	public void parseEdge(Element edgeElt, DirectedAcyclicGraph parentGraph) {
		DAGVertex vertexSource = vertexFromId.get(edgeElt
				.getAttribute("source"));
		DAGVertex vertexTarget = vertexFromId.get(edgeElt
				.getAttribute("target"));

		DAGEdge edge = parentGraph.addEdge(vertexSource, vertexTarget);

		parseKeys(edgeElt, edge);
	}

	@Override
	public DirectedAcyclicGraph parseGraph(Element graphElt) {
		DirectedAcyclicGraph graph = new DirectedAcyclicGraph(
				(DAGEdgeFactory) edgeFactory);
		parseKeys(graphElt, graph);
		NodeList childList = graphElt.getChildNodes();
		parseParameters(graph, graphElt);
		parseVariables(graph, graphElt);
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("node")) {
				Element vertexElt = (Element) childList.item(i);
				graph.addVertex(parseNode(vertexElt));
			}
		}
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("edge")) {
				Element edgeElt = (Element) childList.item(i);
				parseEdge(edgeElt, graph);
			}
		}
		return graph;
	}

	@Override
	public DAGVertex parseNode(Element vertexElt) {
		DAGVertex vertex = new DAGVertex();
		vertex.setId(vertexElt.getAttribute("id"));
		vertexFromId.put(vertex.getId(), vertex);
		parseKeys(vertexElt, vertex);
		parseArguments(vertex, vertexElt);
		return vertex;
	}

	@Override
	public DAGVertex parsePort(Element portElt) {
		// TODO Auto-generated method stub
		return null;
	}

}
