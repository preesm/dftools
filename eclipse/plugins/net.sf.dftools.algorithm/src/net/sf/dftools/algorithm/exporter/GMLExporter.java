package net.sf.dftools.algorithm.exporter;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.PropertySource;
import net.sf.dftools.algorithm.model.parameters.Argument;
import net.sf.dftools.algorithm.model.parameters.ArgumentSet;
import net.sf.dftools.algorithm.model.parameters.Parameter;
import net.sf.dftools.algorithm.model.parameters.ParameterSet;
import net.sf.dftools.algorithm.model.parameters.Variable;
import net.sf.dftools.algorithm.model.parameters.VariableSet;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * Class used to export a SDFGraph into a GML document
 * 
 * @author jpiat
 * 
 * @param <V>
 *            The vertex type
 * @param <E>
 *            The edge type
 */
@SuppressWarnings("rawtypes")
public abstract class GMLExporter<V extends AbstractVertex, E extends AbstractEdge> {

	protected Document domDocument;
	protected String path;

	protected HashMap<String, List<Key>> classKeySet;

	protected int index = 0;
	protected Element rootElt;
	protected Element graphElt;

	/**
	 * Creates a new Instance of GMLExporter
	 */
	public GMLExporter() {
		classKeySet = new HashMap<String, List<Key>>();
		addKey(AbstractGraph.PARAMETERS, SDFGraph.PARAMETERS, "graph", null,
				null);
		addKey(AbstractGraph.VARIABLES, SDFGraph.VARIABLES, "graph", null, null);
		addKey(AbstractVertex.ARGUMENTS, AbstractVertex.ARGUMENTS, "node",
				null, null);
		DOMImplementationRegistry registry;
		DOMImplementation impl;
		try {
			registry = DOMImplementationRegistry.newInstance();
			impl = registry.getDOMImplementation("Core 3.0 XML 3.0 LS");
			domDocument = impl.createDocument(
					"http://graphml.graphdrawing.org/xmlns", "graphml", null);
		} catch (ClassCastException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		rootElt = domDocument.getDocumentElement();
	}

	/**
	 * Add a key to this exporter
	 * 
	 * @param name
	 *            The name of the Key
	 * @param elt
	 *            The Class this key applies to
	 * @param type
	 *            The value type of this key
	 * @param desc
	 *            This key description
	 */
	private void addKey(String name, String elt, String type, Class<?> desc) {
		Key key = new Key(name, elt, type, desc);
		if (classKeySet.get(elt) == null) {
			ArrayList<Key> keys = new ArrayList<Key>();
			classKeySet.put(elt, keys);
		}
		key.setId("k" + index);
		index++;
		classKeySet.get(elt).add(key);
	}

	/**
	 * Adds a key with the speicified informations
	 * 
	 * @param id
	 * @param name
	 * @param elt
	 * @param type
	 * @param desc
	 */
	private void addKey(String id, String name, String elt, String type,
			Class<?> desc) {
		Key key = new Key(name, elt, type, desc);
		if (classKeySet.get(elt) == null) {
			ArrayList<Key> keys = new ArrayList<Key>();
			classKeySet.put(elt, keys);
		}
		key.setId(id);
		classKeySet.get(elt).add(key);
	}

	/**
	 * Writes the key set in the DOM document
	 * 
	 * @param docELement
	 */
	protected void addKeySet(Element docELement) {
		for (List<Key> keys : classKeySet.values()) {
			for (Key key : keys) {
				Element keyElt = appendChild(docELement, "key");
				keyElt.setAttribute("id", key.getId());

				keyElt.setAttribute("for", key.getApplyTo());
				keyElt.setAttribute("attr.name", key.getName());
				if (key.getType() != null) {
					keyElt.setAttribute("attr.type", key.getType());
				}
				if (key.getTypeClass() != null) {
					Element desc = appendChild(keyElt, "desc");
					desc.setTextContent(key.getTypeClass().getName());
				}
			}
		}
	}

	protected void addKey(String eltType, Key key) {
		key.setId(key.getName());
		if (classKeySet.get(eltType) == null) {
			classKeySet.put(eltType, new ArrayList<Key>());
		}
		if (!classKeySet.get(eltType).contains(key)) {
			classKeySet.get(eltType).add(key);
			Element newElt = domDocument.createElement("key");
			newElt.setAttribute("for", key.getApplyTo());
			newElt.setAttribute("attr.name", key.getName());
			if (key.getType() != null) {
				newElt.setAttribute("attr.type", key.getType());
			}
			rootElt.insertBefore(newElt, graphElt);
		}
	}

	/**
	 * Creates a new child for the given parent Element with the name "name"
	 * 
	 * @param parentElement
	 *            The element to add a child
	 * @param name
	 *            The name of this Element
	 * @return The created Element
	 */
	protected Element appendChild(Node parentElement, String name) {
		Element newElt = domDocument.createElement(name);
		parentElement.appendChild(newElt);
		return newElt;
	}

	/**
	 * Creates a GML edge
	 * 
	 * @param parentElement
	 *            The parent element of the edge
	 * @param sourceId
	 *            The id of the source of the edge
	 * @param targetId
	 *            The id of the target of the edge
	 * @return The created element
	 */
	public Element createEdge(Element parentElement, String sourceId,
			String targetId) {
		Element edgeElt = appendChild(parentElement, "edge");
		edgeElt.setAttribute("source", sourceId);
		edgeElt.setAttribute("target", targetId);
		return edgeElt;
	}

	/**
	 * Creates an edge with source port and target port
	 * 
	 * @param parentElement
	 *            The parent element of the edge
	 * @param sourceId
	 *            The source id
	 * @param targetId
	 *            The target id
	 * @param sourcePort
	 *            The source port name
	 * @param targetPort
	 *            The target port name
	 * @return The created edge
	 */
	public Element createEdge(Element parentElement, String sourceId,
			String targetId, String sourcePort, String targetPort) {
		Element edgeElt = appendChild(parentElement, "edge");
		edgeElt.setAttribute("source", sourceId);
		edgeElt.setAttribute("sourceport", sourcePort);
		edgeElt.setAttribute("target", targetId);
		edgeElt.setAttribute("targetport", targetPort);
		return edgeElt;
	}

	/**
	 * Creates a GML graph
	 * 
	 * @param parentElement
	 *            The parent element of the graph
	 * @param directed
	 *            True if the graph is directed
	 * @return The created element
	 */
	public Element createGraph(Element parentElement, boolean directed) {
		Element newElt = appendChild(parentElement, "graph");
		graphElt = newElt;
		if (directed) {
			newElt.setAttribute("edgedefault", "directed");
		}
		return newElt;
	}

	/**
	 * Creates a GML node
	 * 
	 * @param parentElement
	 *            The parent element of this node
	 * @param id
	 *            The id of the node
	 * @return The created element
	 */
	public Element createNode(Element parentElement, String id) {
		Element vertexElt = appendChild(parentElement, "node");
		vertexElt.setAttribute("id", id);
		return vertexElt;
	}

	/**
	 * Creates a GML port
	 * 
	 * @param parentElement
	 *            The parent element of the port
	 * @param name
	 *            The name of the port
	 * @return The created element
	 */
	public Element createPort(Element parentElement, String name) {
		Element newElt = appendChild(parentElement, "port");
		newElt.setAttribute("name", name);
		return newElt;
	}

	/**
	 * Exports the given graph at the given path
	 * 
	 * @param graph
	 *            The graph to export
	 * @param path
	 *            The path where to export the graph
	 */
	public abstract void export(AbstractGraph<V, E> graph, String path);

	/*
	 * Export an Edge in the Document
	 * 
	 * @param edge The edge to export
	 * 
	 * @param parentELement The DOM document parent Element
	 */
	protected abstract Element exportEdge(E edge, Element parentELement);

	/**
	 * Exports a Graph in the DOM document
	 * 
	 * @param graph
	 *            The graph to export
	 * @param out
	 *            The OutputStream to write
	 */
	public abstract Element exportGraph(AbstractGraph<V, E> graph);

	protected void exportKeys(PropertySource source, String forElt,
			Element parentElt) {
		for (String key : source.getPublicProperties()) {
			if (!(key.equals("parameters") || key.equals("variables") || key
					.equals("arguments"))) {
				if (source.getPropertyStringValue(key) != null) {
					Element dataElt = appendChild(parentElt, "data");
					dataElt.setAttribute("key", key);
					dataElt.setTextContent(source.getPropertyStringValue(key));
					if (source.getPropertyBean().getValue(key) != null && source.getPropertyBean().getValue(key) instanceof Number) {
						this.addKey(forElt, new Key(key, forElt, "int", null));
					} else {
						this.addKey(forElt,
								new Key(key, forElt, "string", null));
					}

				}
			}
		}
	}

	/**
	 * Exports a Vertex in the DOM document
	 * 
	 * @param vertex
	 *            The vertex to export
	 * @param parentELement
	 *            The parent Element in the DOM document
	 */
	protected abstract Element exportNode(V vertex, Element parentELement);

	/**
	 * Exports an interface
	 * 
	 * @param interfaceVertex
	 *            The interface to export
	 * @param parentELement
	 *            The DOM parent Element of this Interface
	 */
	protected abstract Element exportPort(V interfaceVertex,
			Element parentELement);

	/**
	 * Gives this Exporter key set
	 * 
	 * @return an HashMap containing this Exporter key set
	 */
	public HashMap<String, List<Key>> getKeySet() {
		return classKeySet;
	}

	/**
	 * Sets this exporter key set
	 * 
	 * @param keys
	 *            The key set
	 */
	public void setKeySet(HashMap<String, List<Key>> keys) {
		classKeySet = keys;
	}

	/**
	 * Transforms the dom to the outputStream
	 * 
	 * @param out
	 *            The output stream to write to
	 */
	public void transform(OutputStream out) {
		DOMImplementationLS impl = (DOMImplementationLS) domDocument
				.getImplementation();

		LSOutput output = impl.createLSOutput();
		output.setByteStream(out);

		LSSerializer serializer = impl.createLSSerializer();
		serializer.getDomConfig().setParameter("format-pretty-print", true);
		serializer.write(domDocument, output);
	}

	protected void exportParameters(ParameterSet parameters,
			Element parentELement) {
		Element dataElt = appendChild(parentELement, "data");
		dataElt.setAttribute("key", "parameters");
		for (Parameter param : parameters.values()) {
			Element paramElt = appendChild(dataElt, "parameter");
			paramElt.setAttribute("name", param.getName());
		}
	}

	protected void exportArguments(ArgumentSet arguments, Element parentELement) {
		Element dataElt = appendChild(parentELement, "data");
		dataElt.setAttribute("key", "arguments");
		for (Argument arg : arguments.values()) {
			Element argElt = appendChild(dataElt, "argument");
			argElt.setAttribute("name", arg.getName());
			argElt.setAttribute("value", arg.getValue());
		}
	}

	protected void exportVariables(VariableSet variables, Element parentELement) {
		Element dataElt = appendChild(parentELement, "data");
		dataElt.setAttribute("key", "variables");
		for (Variable var : variables.values()) {
			Element varElt = appendChild(dataElt, "variable");
			varElt.setAttribute("name", var.getName());
			varElt.setAttribute("value", var.getValue());
		}
	}

}
