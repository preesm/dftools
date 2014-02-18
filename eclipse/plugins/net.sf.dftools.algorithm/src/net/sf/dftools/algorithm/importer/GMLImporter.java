package net.sf.dftools.algorithm.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.dftools.algorithm.exporter.Key;
import net.sf.dftools.algorithm.factories.ModelVertexFactory;
import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.CodeRefinement;
import net.sf.dftools.algorithm.model.PropertyBean;
import net.sf.dftools.algorithm.model.PropertyFactory;
import net.sf.dftools.algorithm.model.PropertySource;
import net.sf.dftools.algorithm.model.parameters.Argument;
import net.sf.dftools.algorithm.model.parameters.Parameter;
import net.sf.dftools.algorithm.model.parameters.Variable;

import org.jgrapht.EdgeFactory;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

/**
 * Class used to import a Graph from a GML InputStream
 * 
 * @author jpiat
 * 
 * @param <G>
 * @param <V>
 * @param <E>
 */
public abstract class GMLImporter<G extends AbstractGraph<?, ?>, V extends AbstractVertex<?>, E extends AbstractEdge<?, ?>> {

	protected HashMap<String, List<Key>> classKeySet;
	protected EdgeFactory<V, E> edgeFactory;
	protected ModelVertexFactory<V> vertexFactory;
	protected InputStream inputStream;
	protected String path;
	protected HashMap<String, V> vertexFromId = new HashMap<String, V>();

	/**
	 * Creates a new GMLImporter
	 * 
	 * @param edgeFactory
	 *            The edge factory to create Edges
	 */
	public GMLImporter(EdgeFactory<V, E> edgeFactory) {
		this.edgeFactory = edgeFactory;
		classKeySet = new HashMap<String, List<Key>>();
	}

	/**
	 * 
	 * Gives this Importer
	 * 
	 * @return This Importer Key set
	 */
	public HashMap<String, List<Key>> getKeySet() {
		return classKeySet;
	}

	/**
	 * Parses the given file
	 * 
	 * @param f
	 *            The file to parse
	 * @return The parsed graph
	 * @throws InvalidModelException
	 * @throws FileNotFoundException
	 */
	public G parse(File f) throws InvalidModelException, FileNotFoundException {
		this.path = f.getAbsolutePath();
		return parse(new FileInputStream(f));
	}

	/**
	 * Parses the given file
	 * 
	 * @param input
	 *            The input stream to parse
	 * @param path
	 *            The of the file to parse
	 * @return The parsed graph
	 * @throws InvalidModelException
	 * @throws FileNotFoundException
	 */
	public G parse(InputStream input, String path)
			throws InvalidModelException, FileNotFoundException {
		this.path = path;
		return parse(input);
	}

	/**
	 * Parses the input stream as a GML document
	 * 
	 * @param input
	 *            The InputStream to parse
	 * @return The graph parsed from the document
	 * @throws InvalidModelException
	 */
	private G parse(InputStream input) throws InvalidModelException {
		this.inputStream = input;

		// using DOM3
		DOMImplementationRegistry registry = null;
		DOMImplementationLS impl = null;
		try {
			registry = DOMImplementationRegistry.newInstance();
			impl = (DOMImplementationLS) registry
					.getDOMImplementation("Core 3.0 XML 3.0 LS");
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

		LSInput lsInput = impl.createLSInput();
		lsInput.setByteStream(input);

		// parse without comments and whitespace
		LSParser builder = impl.createLSParser(
				DOMImplementationLS.MODE_SYNCHRONOUS, null);
		DOMConfiguration config = builder.getDomConfig();
		config.setParameter("comments", false);
		config.setParameter("element-content-whitespace", false);

		Document doc = builder.parse(lsInput);

		Element rootElt = (Element) doc.getFirstChild();
		if (!rootElt.getNodeName().equals("graphml")) {
			throw (new InvalidModelException());
		}
		recoverKeys(rootElt);
		NodeList childList = rootElt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("graph")) {
				Element graphElt = (Element) childList.item(i);
				G graph =  parseGraph(graphElt);
				// Record the path of the graph
				graph.setPropertyValue(AbstractGraph.PATH, path);
				return graph;
			}
		}
		return null;
	}

	/**
	 * Parses an Edge in the DOM document
	 * 
	 * @param edgeElt
	 *            The DOM Element
	 * @param parentGraph
	 *            The parent Graph of this Edge
	 */
	public abstract void parseEdge(Element edgeElt, G parentGraph)
			throws InvalidModelException;

	/**
	 * Parses a Graph in the DOM document
	 * 
	 * @param graphElt
	 *            The graph Element in the DOM document
	 * @return The parsed Graph
	 */
	public abstract G parseGraph(Element graphElt) throws InvalidModelException;

	/**
	 * Parses a key instance in the document
	 * 
	 * @param dataElt
	 *            The DOM instance of the key
	 * @param eltType
	 *            The Type of the element this jkey belong to (node, port, edge
	 *            ...)
	 * @return a set where index 0 is the name of the attribute and index 1 is
	 *         the value of the attribute
	 */
	public List<Object> parseKey(Element dataElt, String eltType) {
		List<Object> result = new ArrayList<Object>();
		List<Key> keySet = this.classKeySet.get(eltType);
		if (keySet == null) {
			return null;
		}
		String key = dataElt.getAttribute("key");
		for (Key oneKey : keySet) {
			// Ignoring special keys
			if (oneKey.getId().equals(key) && oneKey.getType() != null
					&& !oneKey.getId().equalsIgnoreCase("arguments")
					&& !oneKey.getId().equalsIgnoreCase("parameters")
					&& !oneKey.getId().equalsIgnoreCase("variables")) {
				try {
					Method[] availableFactories = null;
					if (oneKey.getTypeClass() != null) {
						availableFactories = oneKey.getTypeClass()
								.getDeclaredMethods();
					}
					Method toUse = null;
					Class<?> constParam;
					Object param;
					if (oneKey.getType().equals("int")) {
						constParam = int.class;
						param = new Integer(dataElt.getTextContent());
					} else if (oneKey.getType().equals("string")) {
						constParam = String.class;
						param = dataElt.getTextContent();
					} else if (oneKey.getType().equals("double")) {
						constParam = double.class;
						param = new Double(dataElt.getTextContent());
					} else {
						constParam = String.class;
						param = dataElt.getTextContent();
					}
					if (availableFactories != null) {
						for (int i = 0; i < availableFactories.length; i++) {
							Method method = availableFactories[i];
							if (method.getGenericParameterTypes().length == 1
									&& method.getGenericParameterTypes()[0]
											.equals(constParam)) {
								toUse = method;
							}
						}
						if (toUse == null) {
							return null;
						}
						Object value = toUse.invoke(null, param);
						result.add(oneKey.getName());
						result.add(value);
					} else {
						result.add(oneKey.getName());
						result.add(param);
					}

					return result;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return null;
	}

	/**
	 * Parse an element keys
	 * 
	 * @param elt
	 *            The DOM element parent of the keys
	 * @param bean
	 *            The property bean containing the properties
	 * @param eltType
	 *            The type of the element
	 */
	public void old_parseKeys(Element elt, PropertyBean bean, String eltType) {
		NodeList childList = elt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("data")) {
				List<Object> value = parseKey((Element) childList.item(i),
						eltType);
				if (value != null) {
					bean.setValue((String) value.get(0), value.get(1));
				}
			}
		}
	}

	/**
	 * Parse an element keys
	 * 
	 * @param elt
	 *            The DOM element parent of the keys
	 * @param src
	 *            The property source to fill
	 * @param eltType
	 *            The type of the element
	 */
	public void parseKeys(Element elt, PropertySource src) {
		NodeList childList = elt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("data")) {
				String key = ((Element) childList.item(i)).getAttribute("key");
				if ((!(key.equals("arguments") || key.equals("parameters") || key
						.equals("variables")))
						&& src.getPublicProperties().contains(key)) {
					String propertyName = ((Element) childList.item(i))
							.getAttribute("key");
					PropertyFactory factory = src
							.getFactoryForProperty(propertyName);
					if (factory != null) {
						src.setPropertyValue(propertyName, factory
								.create(childList.item(i).getTextContent()));
					} else {
						src.setPropertyValue(propertyName, childList.item(i)
								.getTextContent());
					}
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
	public abstract V parseNode(Element vertexElt, G parentGraph)
			throws InvalidModelException;

	/**
	 * Parses an Interface from the DOM document
	 * 
	 * @param portElt
	 *            The DOM Element to parse
	 * @return The ineterface parsed from the DOM document
	 */
	public abstract V parsePort(Element portElt, G parentGraph)
			throws InvalidModelException;

	/**
	 * Recover the key set from the GML document
	 * 
	 * @param rootElt
	 *            The rootElt of the document
	 */
	@SuppressWarnings("unused")
	public void recoverKeys(Element rootElt) {
		NodeList childList = rootElt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			Node childNode = childList.item(i);
			if (childNode.getNodeName().equals("key")) {
				Element childElt = (Element) childNode;
				// try {
				String attrName = childElt.getAttribute("attr.name");
				String typeParamType = childElt.getAttribute("attr.type");
				if (typeParamType == "") {
					typeParamType = null;
				}
				String isFor = childElt.getAttribute("for");
				String id = childElt.getAttribute("id");
				NodeList keyChild = childElt.getChildNodes();
				Class<?> type = null;
				/*
				 * for (int j = 0; j < keyChild.getLength(); j++) { Node descElt
				 * = keyChild.item(j); if (descElt.getNodeName().equals("desc"))
				 * { String desc = descElt.getTextContent(); Class. type =
				 * Class.forName(desc); } }
				 */
				Key newKey = new Key(attrName, isFor, typeParamType, type);
				newKey.setId(id);
				List<Key> keys;
				if ((keys = classKeySet.get(isFor)) == null) {
					keys = new ArrayList<Key>();
					classKeySet.put(isFor, keys);
				}
				keys.add(newKey);
				/*
				 * } catch (ClassNotFoundException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); }
				 */
			}
		}

	}

	@SuppressWarnings({ "unchecked" })
	protected void parseArguments(AbstractVertex<?> vertex, Element parentElt) {
		NodeList childList = parentElt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("data")
					&& ((Element) childList.item(i)).getAttribute("key")
							.equals("arguments")) {
				NodeList argsList = childList.item(i).getChildNodes();
				for (int j = 0; j < argsList.getLength(); j++) {
					if (argsList.item(j).getNodeName().equals("argument")) {
						Element arg = (Element) argsList.item(j);
						Argument vArg = vertex
								.getBase()
								.getArgumentFactory(vertex)
								.create(arg.getAttribute("name"),
										arg.getAttribute("value"));
						vertex.addArgument(vArg);
					}
				}
			}
		}
	}

	protected void parseParameters(AbstractGraph<?, ?> graph, Element parentElt) {
		NodeList childList = parentElt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("data")
					&& ((Element) childList.item(i)).getAttribute("key")
							.equals("parameters")) {
				NodeList argsList = childList.item(i).getChildNodes();
				for (int j = 0; j < argsList.getLength(); j++) {
					if (argsList.item(j).getNodeName().equals("parameter")) {
						Element param = (Element) argsList.item(j);
						Parameter gParam = graph.getParameterFactory().create(
								param.getAttribute("name"));
						graph.addParameter(gParam);
					}
				}
			}
		}
	}

	protected String parseModel(Element parentElt) {
		NodeList childList = parentElt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("data")
					&& ((Element) childList.item(i)).getAttribute("key")
							.equals(AbstractGraph.MODEL)) {
				return childList.item(i).getTextContent();
			}
		}
		return "generic";
	}

	protected void parseVariables(AbstractGraph<?, ?> graph, Element parentElt) {
		NodeList childList = parentElt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("data")
					&& ((Element) childList.item(i)).getAttribute("key")
							.equals("variables")) {
				NodeList argsList = childList.item(i).getChildNodes();
				for (int j = 0; j < argsList.getLength(); j++) {
					if (argsList.item(j).getNodeName().equals("variable")) {
						Element var = (Element) argsList.item(j);
						graph.addVariable(new Variable(
								var.getAttribute("name"), var
										.getAttribute("value")));
					}
				}
			}
		}
	}

	protected void parseGraphDescription(AbstractVertex<?> vertex,
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
						GMLGenericImporter importer = new GMLGenericImporter();
						try {
							AbstractGraph<?, ?> refine = importer
									.parse(new File(directoryPath + path));
							vertex.setGraphDescription(refine);
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
	 * Sets thi Importer key set
	 * 
	 * @param keys
	 */
	public void setKeySet(HashMap<String, List<Key>> keys) {
		classKeySet = keys;
	}

}
