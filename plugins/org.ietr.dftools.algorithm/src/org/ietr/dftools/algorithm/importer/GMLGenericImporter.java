package org.ietr.dftools.algorithm.importer;

import java.util.List;

import org.ietr.dftools.algorithm.factories.ModelGraphFactory;
import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.IInterface;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GMLGenericImporter extends
		GMLImporter<AbstractGraph, AbstractVertex, AbstractEdge> {

	/**
	 * COnstructs a new importer for SDF graphs
	 */
	public GMLGenericImporter() {
		super(null);
	}

	/**
	 * Parses an Edge in the DOM document
	 * 
	 * @param edgeElt
	 *            The DOM Element
	 * @param parentGraph
	 *            The parent Graph of this Edge
	 */
	@Override
	public void parseEdge(Element edgeElt, AbstractGraph parentGraph)
			throws InvalidModelException {
		AbstractVertex vertexSource = vertexFromId.get(edgeElt
				.getAttribute("source"));
		AbstractVertex vertexTarget = vertexFromId.get(edgeElt
				.getAttribute("target"));

		IInterface sourcePort = null;
		IInterface targetPort = null;
		String sourcePortName = edgeElt.getAttribute("sourceport");
		for (IInterface sinksPort : (List<IInterface>) vertexSource
				.getInterfaces()) {
			if (sinksPort.getName().equals(sourcePortName)) {
				sourcePort = sinksPort;
			}
		}
		if (sourcePort == null) {
			sourcePort = vertexFactory.createInterface(sourcePortName, 1);
			vertexSource.addInterface(sourcePort);
		}
		String targetPortName = edgeElt.getAttribute("targetport");
		for (IInterface sourcesPort : (List<IInterface>) vertexTarget
				.getInterfaces()) {
			if (sourcesPort.getName().equals(targetPortName)) {
				targetPort = sourcesPort;
			}
		}

		if (targetPort == null) {
			targetPort = vertexFactory.createInterface(targetPortName, 0);
			vertexTarget.addInterface(targetPort);
		}
		AbstractEdge edge = parentGraph.addEdge(vertexSource, sourcePort,
				vertexTarget, targetPort);
		parseKeys(edgeElt, edge);
	}

	/**
	 * Parses a Graph in the DOM document
	 * 
	 * @param graphElt
	 *            The graph Element in the DOM document
	 * @return The parsed graph
	 */
	@Override
	public AbstractGraph parseGraph(Element graphElt)
			throws InvalidModelException {
		String parseModel = parseModel(graphElt);
		AbstractGraph graph;
		try {
			graph = ModelGraphFactory.getModel(parseModel);
			this.edgeFactory = graph.getEdgeFactory();
			this.vertexFactory = graph.getVertexFactory();
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
		} catch (InstantiationException e) {
			throw new InvalidModelException(
					"Failed to parse graph with message :" + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new InvalidModelException(
					"Failed to parse graph with message :" + e.getMessage());
		}
	}

	/**
	 * Parses a Vertex from the DOM document
	 * 
	 * @param vertexElt
	 *            The node Element in the DOM document
	 * @return The parsed node
	 */
	@Override
	public AbstractVertex parseNode(Element vertexElt, AbstractGraph parentGraph)
			throws InvalidModelException {
		AbstractVertex vertex;
		vertex = vertexFactory.createVertex(vertexElt);
		vertex.setId(vertexElt.getAttribute("id"));
		vertex.setName(vertexElt.getAttribute("id"));
		parseKeys(vertexElt, vertex);
		parentGraph.addVertex(vertex);
		vertexFromId.put(vertex.getId(), vertex);
		parseArguments(vertex, vertexElt);
		parseGraphDescription(vertex, vertexElt);
		return vertex;
	}

	@Override
	public AbstractVertex parsePort(Element portElt, AbstractGraph parentGraph)
			throws InvalidModelException {
		return null;
	}

}
