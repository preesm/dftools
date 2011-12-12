package net.sf.dftools.algorithm.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import net.sf.dftools.algorithm.demo.SDFAdapterDemo;
import net.sf.dftools.algorithm.factories.SDFEdgeFactory;
import net.sf.dftools.algorithm.factories.SDFVertexFactory;
import net.sf.dftools.algorithm.importer.GMLGenericImporter;
import net.sf.dftools.algorithm.importer.GMLImporter;
import net.sf.dftools.algorithm.importer.InvalidFileException;
import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.CodeRefinement;
import net.sf.dftools.algorithm.model.InterfaceDirection;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Importer for SDF graphs
 * 
 * @author jpiat
 * 
 */
public class GMLOldSDFImporter extends
		GMLImporter<SDFGraph, SDFAbstractVertex, SDFEdge> {

	/**
	 * Main function allowing to debug the class
	 * 
	 * @param args
	 * @throws InvalidExpressionException
	 */
	public static void main(String[] args) throws InvalidExpressionException {
		SDFAdapterDemo applet = new SDFAdapterDemo();
		GMLOldSDFImporter importer = new GMLOldSDFImporter();
		try {
			SDFGraph graph = importer
					.parse(new File(
							"D:\\Preesm\\trunk\\tests\\IDCT2D\\idct2dCadOptim.graphml"));
			applet.init(graph);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * COnstructs a new importer for SDF graphs
	 */
	public GMLOldSDFImporter() {
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
	public void parseEdge(Element edgeElt, SDFGraph parentGraph) {
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
	public SDFGraph parseGraph(Element graphElt) {
		SDFGraph graph = new SDFGraph((SDFEdgeFactory) edgeFactory);
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
		parseKeys(graphElt, graph);
		return graph;
	}

	protected void parseGraphDescription(SDFAbstractVertex vertex,
			Element parentElt) {
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
						GMLOldSDFImporter importer = new GMLOldSDFImporter();
						try {
							File refinementFile = new File(directoryPath + path);
							String fileName = refinementFile.getName();
							fileName = fileName.substring(0, fileName.indexOf('.'));
							SDFGraph refine;
							try{
								refine = (SDFGraph) importer
										.parse(refinementFile);
							}catch(Exception e){
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
						} catch (InvalidFileException e) {
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
	public SDFAbstractVertex parseNode(Element vertexElt) {

		SDFAbstractVertex vertex;
		HashMap<String, String> attributes = new HashMap<String, String>();
		for (int i = 0; i < vertexElt.getAttributes().getLength(); i++) {
			attributes.put(vertexElt.getAttributes().item(i).getNodeName(),
					vertexElt.getAttributes().item(i).getNodeValue());
		}
		vertex = SDFVertexFactory.getInstance().createVertex(attributes);
		vertex.setId(vertexElt.getAttribute("id"));
		vertex.setName(vertexElt.getAttribute("id"));
		parseKeys(vertexElt, vertex);
		vertexFromId.put(vertex.getId(), vertex);
		parseArguments(vertex, vertexElt);
		parseGraphDescription(vertex, vertexElt);
		return vertex;
	}

	@Override
	public SDFAbstractVertex parsePort(Element portElt) {
		return null;
	}
	
	public void disableRefinementParsing(){
		
	}

}
