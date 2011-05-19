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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.dftools.architecture.component.BusInterface;
import net.sf.dftools.architecture.design.ComponentInstance;
import net.sf.dftools.architecture.design.Connection;
import net.sf.dftools.architecture.design.Design;
import net.sf.dftools.architecture.design.serialize.DesignWriter;
import net.sf.dftools.architecture.utils.ArchitectureUtil;
import net.sf.graphiti.io.ITransformation;
import net.sf.graphiti.io.LayoutWriter;
import net.sf.graphiti.model.Edge;
import net.sf.graphiti.model.Graph;
import net.sf.graphiti.model.ObjectType;
import net.sf.graphiti.model.Vertex;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * This class defines an Ip-Xact exporter.
 * 
 * @author Ghislain Roquier
 * 
 */
public class IpXactExporter implements ITransformation {

	private Map<Vertex, net.sf.dftools.architecture.design.Vertex> vertexMap;

	private void addEdge(Design design, Edge edge) {
		net.sf.dftools.architecture.design.Vertex source = vertexMap.get(edge
				.getSource());
		net.sf.dftools.architecture.design.Vertex target = vertexMap.get(edge
				.getTarget());

		String sourceName = (String) edge
				.getValue(ObjectType.PARAMETER_SOURCE_PORT);
		BusInterface sourcePort = null;
		if (sourceName != null) {
			sourcePort = new BusInterface(sourceName);
		}

		String targetName = (String) edge
				.getValue(ObjectType.PARAMETER_TARGET_PORT);
		BusInterface targetPort = null;
		if (targetName != null) {
			targetPort = new BusInterface(targetName);
		}
		// connection
		Connection connection = new Connection(sourcePort, targetPort);
		design.getGraph().addEdge(source, target, connection);
	}

	private void addVertex(Design design, Vertex vertex) {
		String id = (String) vertex.getValue(PARAMETER_ID);
		net.sf.dftools.architecture.design.Vertex designVertex;

		if ("BusInterface".equals(vertex.getType().getName())) {
			BusInterface intf = new BusInterface(id);
			design.getBusInterfaces().add(intf);
			designVertex = new net.sf.dftools.architecture.design.Vertex(intf);
		} else {
			String clasz = (String) vertex.getValue(PARAMETER_REFINEMENT);
			ComponentInstance inst = new ComponentInstance(id, clasz);
			designVertex = new net.sf.dftools.architecture.design.Vertex(inst);
		}
		design.getGraph().addVertex(designVertex);
		vertexMap.put(vertex, designVertex);
	}

	@Override
	public void transform(Graph graph, OutputStream out) {
		vertexMap = new HashMap<Vertex, net.sf.dftools.architecture.design.Vertex>();

		String name = (String) graph.getValue(PARAMETER_ID);
		Design design = new Design(name);
		for (Vertex vertex : graph.vertexSet()) {
			addVertex(design, vertex);
		}

		for (Edge edge : graph.edgeSet()) {
			addEdge(design, edge);
		}

		DesignWriter writer = new DesignWriter();
		writer.write(design, out);

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = graph.getFile();
		file = root.getFile(file.getFullPath().removeFileExtension()
				.addFileExtension("layout"));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		new LayoutWriter().write(graph, bos);
		try {
			InputStream is = new ByteArrayInputStream(bos.toByteArray());
			if (file.exists()) {
				file.setContents(is, true, false, null);
			} else {
				IContainer container = file.getParent();
				if (container.getType() == IResource.FOLDER) {
					ArchitectureUtil.createFolder((IFolder) container);
				}
				file.create(is, true, null);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Graph transform(IFile file) {
		return null;
	}

}
