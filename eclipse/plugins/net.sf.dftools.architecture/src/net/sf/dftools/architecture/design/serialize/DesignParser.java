/* Copyright (c) 2010-2011 - IETR/INSA de Rennes and EPFL
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA de Rennes and EPFL nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.dftools.architecture.design.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.architecture.VLNV;
import net.sf.dftools.architecture.component.BusInterface;
import net.sf.dftools.architecture.component.Component;
import net.sf.dftools.architecture.component.serialize.ComponentParser;
import net.sf.dftools.architecture.design.ComponentInstance;
import net.sf.dftools.architecture.design.Connection;
import net.sf.dftools.architecture.design.Design;
import net.sf.dftools.architecture.design.HierConnection;
import net.sf.dftools.architecture.design.InterConnection;
import net.sf.dftools.architecture.design.Vertex;
import net.sf.dftools.architecture.utils.DomUtil;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.Multigraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DesignParser {

	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			String fileName;
			fileName = new File(args[0]).getCanonicalPath();

			DesignParser parser = new DesignParser(fileName,
					new HashMap<String, BusInterface>());
			Design design = parser.parse();

			new DesignWriter(new File("D:/temp/ipxact/generated"), design);

		} else {
			System.err.println("Usage: IpXactParser "
					+ "<absolute path of top-level IpXact design>");
		}
	}

	private String file;

	private String path;

	private String id;

	private UndirectedGraph<Vertex, Connection> graph;

	private Map<String, BusInterface> busInterfaces;

	private Map<String, ComponentInstance> instances;

	public DesignParser(String fileName, Map<String, BusInterface> busInterfaces) {
		this.file = fileName;
		this.busInterfaces = busInterfaces;
		path = new File(fileName).getParent();
	}

	private BusInterface getBusInterface(String vertexName, String portName) {
		if (vertexName.isEmpty()) {
			return null;
		} else {
			return new BusInterface(portName, null, false);
		}
	}

	private Vertex getVertex(String vertexName, String intfName) {
		if (vertexName.isEmpty()) {
			BusInterface bus = busInterfaces.get(intfName);
			return new Vertex(bus);
		} else {
			ComponentInstance instance = instances.get(vertexName);
			return new Vertex(instance);
		}
	}

	public Design parse() throws IOException {
		try {
			InputStream is = new FileInputStream(file);
			Document document = DomUtil.parseDocument(is);
			Design design = parseDesign(document);
			is.close();
			return design;
		} catch (IOException e) {
			throw new IOException("I/O error when parsing design", e);
		}
	}

	private void parseComponentInstance(Element comp) throws IOException {
		Node node = comp.getFirstChild();

		String id = null;
		String vendor = null;
		String library = null;
		String name = null;
		String version = null;
		Map<String, String> values = new HashMap<String, String>();

		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:instanceName")) {
					id = elt.getTextContent();
				} else if (type.equals("spirit:componentRef")) {
					vendor = elt.getAttribute("spirit:vendor");
					library = elt.getAttribute("spirit:library");
					name = elt.getAttribute("spirit:name");
					version = elt.getAttribute("spirit:version");
				} else if (type.equals("spirit:configurableElementValues")) {
					parseConfigurableValues(elt, values);
				}
			}
			node = node.getNextSibling();
		}
		ComponentInstance instance = null;
		File compFile = new File(path, name + ".component");
		String filename = compFile.getAbsolutePath();
		Component component = new ComponentParser(filename).parse();

		instance = new ComponentInstance(id, new VLNV(vendor, library, name,
				version), component, values);

		instances.put(id, instance);
		graph.addVertex(new Vertex(instance));
	}

	private void parseComponentInstances(Element components) throws IOException {
		Node node = components.getFirstChild();

		while (node != null) {
			if (node instanceof Element) {
				Element element = (Element) node;
				String type = element.getTagName();
				if (type.equals("spirit:componentInstance")) {
					parseComponentInstance(element);
				}
			}

			node = node.getNextSibling();
		}
	}

	private void parseConfigurableValues(Element callElt,
			Map<String, String> values) {
		Node node = callElt.getFirstChild();

		while (node != null) {

			if (node instanceof Element) {
				Element elt = (Element) node;
				String eltType = elt.getTagName();
				if (eltType.equals("spirit:configurableElementValue")) {
					String name = elt.getAttribute("spirit:referenceId");
					String value = elt.getTextContent();
					values.put(name, value);
				}
			}
			node = node.getNextSibling();
		}

	}

	private Design parseDesign(Document doc) throws IOException {
		Element root = doc.getDocumentElement();
		Node node = root.getFirstChild();

		graph = new Multigraph<Vertex, Connection>(Connection.class);
		instances = new HashMap<String, ComponentInstance>();

		String vendor = null;
		String name = null;
		String library = null;
		String version = null;

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals("spirit:id")) {
					id = element.getTextContent();
				} else if (nodeName.equals("spirit:vendor")) {
					vendor = element.getTextContent();
				} else if (nodeName.equals("spirit:name")) {
					name = element.getTextContent();
				} else if (nodeName.equals("spirit:library")) {
					library = element.getTextContent();
				} else if (nodeName.equals("spirit:version")) {
					version = element.getTextContent();
				} else if (nodeName.equals("spirit:componentInstances")) {
					parseComponentInstances(element);
				} else if (nodeName.equals("spirit:interconnections")) {
					parseInterconnections(element);
				} else if (nodeName.equals("spirit:hierConnections")) {
					parseHierConnections(element);
				} else {
					// manage exception;
				}
			}

			node = node.getNextSibling();
		}

		VLNV vlnv = new VLNV(vendor, library, name, version);
		return new Design(id, vlnv, graph);

	}

	private void parseHierConnection(Element connection) {
		String busName = null;
		String componentName = null;
		String intfName = connection.getAttribute("spirit:interfaceRef");

		BusInterface intf = busInterfaces.get(intfName);
		Vertex vertex = new Vertex(intf);
		graph.addVertex(vertex);

		Node node = connection.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:name")) {
				} else if (type.equals("spirit:displayName")) {
				} else if (type.equals("spirit:description")) {
				} else if (type.equals("spirit:activeInterface")) {
					busName = elt.getAttribute("spirit:busRef");
					componentName = elt.getAttribute("spirit:componentRef");
				}
			}
			node = node.getNextSibling();
		}

		Vertex cmp1 = getVertex(componentName, busName);
		BusInterface bus = getBusInterface(componentName, busName);

		Connection interconn = new HierConnection(intf, bus);
		graph.addEdge(vertex, cmp1, interconn);
	}

	private void parseHierConnections(Element hierconnections) {
		Node node = hierconnections.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:hierConnection")) {
					parseHierConnection(elt);
				}
			}
			node = node.getNextSibling();
		}

	}

	/**
	 * Parses one interconnection
	 */
	private void parseInterconnection(Element connection) {
		List<String> busRefs = new ArrayList<String>(2);
		List<String> componentRefs = new ArrayList<String>(2);

		Node node = connection.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:name")) {
				} else if (type.equals("spirit:displayName")) {
				} else if (type.equals("spirit:description")) {
				} else if (type.equals("spirit:activeInterface")) {
					busRefs.add(elt.getAttribute("spirit:busRef"));
					componentRefs.add(elt.getAttribute("spirit:componentRef"));
				}
			}
			node = node.getNextSibling();
		}

		Vertex cmp1 = getVertex(componentRefs.get(0), busRefs.get(0));
		Vertex cmp2 = getVertex(componentRefs.get(1), busRefs.get(1));
		BusInterface busRef1 = getBusInterface(componentRefs.get(0),
				busRefs.get(0));
		BusInterface busRef2 = getBusInterface(componentRefs.get(1),
				busRefs.get(1));

		Connection interconn = new InterConnection(busRef1, busRef2);
		graph.addEdge(cmp1, cmp2, interconn);

	}

	private void parseInterconnections(Element connections) {
		Node node = connections.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:interconnection")) {
					parseInterconnection(elt);
				}
			}
			node = node.getNextSibling();
		}
	}

}
