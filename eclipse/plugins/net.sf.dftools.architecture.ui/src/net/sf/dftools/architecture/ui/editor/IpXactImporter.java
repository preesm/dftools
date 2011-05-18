/*
 * Copyright (c) 2011, EPFL
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
 *   * Neither the name of the EPFL nor the names of its contributors may be used 
 *     to endorse or promote products derived from this software without specific
 *     prior written permission.
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
package net.sf.dftools.architecture.ui.editor;

import static net.sf.graphiti.model.ObjectType.PARAMETER_ID;
import static net.sf.graphiti.model.ObjectType.PARAMETER_REFINEMENT;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.dftools.architecture.component.Component;
import net.sf.dftools.architecture.component.Operator;
import net.sf.dftools.architecture.design.Connection;
import net.sf.dftools.architecture.design.Design;
import net.sf.dftools.architecture.design.serialize.DesignParser;
import net.sf.graphiti.GraphitiModelPlugin;
import net.sf.graphiti.io.DomHelper;
import net.sf.graphiti.io.ITransformation;
import net.sf.graphiti.model.Configuration;
import net.sf.graphiti.model.Edge;
import net.sf.graphiti.model.Graph;
import net.sf.graphiti.model.ObjectType;
import net.sf.graphiti.model.Vertex;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.geometry.Rectangle;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class defines an Ip-Xact importer.
 * 
 * @author Ghislain Roquier
 * 
 */
public class IpXactImporter implements ITransformation {

	private Graph graph;

	private Map<net.sf.dftools.architecture.design.Vertex, Vertex> vertexMap;

	private void addEdges(Design design) {
		Configuration configuration = graph.getConfiguration();
		for (Connection connection : design.getGraph().edgeSet()) {

			net.sf.dftools.architecture.design.Vertex srcDesignVertex = design
					.getGraph().getEdgeSource(connection);
			net.sf.dftools.architecture.design.Vertex tgtDesignVertex = design
					.getGraph().getEdgeTarget(connection);

			Vertex src = vertexMap.get(srcDesignVertex);
			Vertex tgt = vertexMap.get(tgtDesignVertex);

			ObjectType type = configuration.getEdgeType("Connection");
			Edge edge = new Edge(type, src, tgt);
			edge.setValue(ObjectType.PARAMETER_SOURCE_PORT, connection
					.getSource().getName());
			edge.setValue(ObjectType.PARAMETER_TARGET_PORT, connection
					.getTarget().getName());
			graph.addEdge(edge);
		}
	}

	private void addVertex(Design design,
			net.sf.dftools.architecture.design.Vertex designVertex) {
		Configuration configuration = graph.getConfiguration();
		Component comp = designVertex.getComponentInstance().getComponent();

		ObjectType type;
		if (comp instanceof Operator) {
			type = configuration.getVertexType("Operator");
		} else {
			type = configuration.getVertexType("Medium");
		}
		Vertex vertex = new Vertex(type);
		String id = designVertex.getComponentInstance().getId();
		String clasz = designVertex.getComponentInstance().getClasz();
		vertex.setValue(PARAMETER_ID, id);
		vertex.setValue(PARAMETER_REFINEMENT, clasz);
		graph.addVertex(vertex);
		vertexMap.put(designVertex, vertex);

	}

	private void addVertices(Design design) {
		for (net.sf.dftools.architecture.design.Vertex vertex : design
				.getGraph().vertexSet()) {
			addVertex(design, vertex);
		}
	}

	@Override
	public void transform(Graph graph, OutputStream out) {
	}

	@Override
	public Graph transform(IFile file) {
		vertexMap = new HashMap<net.sf.dftools.architecture.design.Vertex, Vertex>();
		Configuration configuration = GraphitiModelPlugin.getDefault()
				.getConfiguration("IP-XACT design");
		ObjectType type = configuration.getGraphType("IP-XACT design");

		DesignParser parser = new DesignParser(file);
		Design design = null;
		try {
			design = parser.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}

		graph = new Graph(configuration, type, true);
		graph.setValue(ObjectType.PARAMETER_ID, design.getName());
		graph.setValue(Graph.PROPERTY_HAS_LAYOUT, true);
		graph.setFileName(file.getFullPath());

		addVertices(design);

		addEdges(design);

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile layout = root.getFile(file.getFullPath().removeFileExtension()
				.addFileExtension("layout"));

		try {
			if (layout.exists()) {
				parseLayout(layout);
			} else {
				graph.setValue(Graph.PROPERTY_HAS_LAYOUT, Boolean.FALSE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return graph;
	}

	private void parseLayout(IFile layout) throws Exception {
		try {
			InputStream in = layout.getContents();
			Element element = DomHelper.parse(in).getDocumentElement();
			parseVertices(element.getFirstChild());
			in.close();
		} catch (IOException e) {
			throw new IOException("I/O error when parsing design", e);
		}
	}

	private Node parseVertices(Node node) {
		node = DomHelper.getFirstSiblingNamed(node, "vertices");
		Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeName().equals("vertex")) {
				String id = ((Element) child).getAttribute("id");
				Vertex vertex = graph.findVertex(id);

				String xAttr = ((Element) child).getAttribute("x");
				String yAttr = ((Element) child).getAttribute("y");
				if (!xAttr.isEmpty() && !yAttr.isEmpty()) {
					int x = Integer.parseInt(xAttr);
					int y = Integer.parseInt(yAttr);
					vertex.setValue(Vertex.PROPERTY_SIZE, new Rectangle(x, y,
							0, 0));
				}
			}

			child = child.getNextSibling();
		}

		return node.getNextSibling();
	}

}
