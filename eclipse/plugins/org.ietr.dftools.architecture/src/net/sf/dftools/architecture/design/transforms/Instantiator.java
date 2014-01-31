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
package net.sf.dftools.architecture.design.transforms;

import net.sf.dftools.architecture.component.BusInterface;
import net.sf.dftools.architecture.component.Component;
import net.sf.dftools.architecture.design.ComponentInstance;
import net.sf.dftools.architecture.design.Connection;
import net.sf.dftools.architecture.design.Design;
import net.sf.dftools.architecture.design.Vertex;

import org.jgrapht.UndirectedGraph;

/**
 * This class defines a design transformation that instantiates a design.
 * 
 * @author ghislain roquier
 */

public class Instantiator implements IDesignTransform {

	String path;
	private Design design;
	private UndirectedGraph<Vertex, Connection> graph;

	public Instantiator(String path) {
		this.path = path;
	}

	@Override
	public void transform(Design design) throws Exception {
		this.design = design;
		graph = design.getGraph();

		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isComponentInstance()) {
				Component component = vertex.getComponentInstance()
						.getComponent();
				if (component.isHierarchical()) {
					component.getDesign().instantiate(path);
				}
			}
		}
		updateConnections();
	}

	private void updateConnection(Connection connection) throws Exception {
		Vertex srcVertex = graph.getEdgeSource(connection);
		Vertex tgtVertex = graph.getEdgeTarget(connection);

		@SuppressWarnings("unused")
		String sourceString;
		if (srcVertex.isComponentInstance()) {
			ComponentInstance source = srcVertex.getComponentInstance();
			String intfUName = connection.getSource().getName();

			BusInterface intfU = source.getComponent().getInterface(intfUName);

			if (intfU == null) {
				throw new Exception("In design \"" + design.getVlnv().getName()
						+ "\": A Connection refers to "
						+ "a non-existent port: \"" + intfUName
						+ "\" of instance \"" + source.getId() + "\"");
			}

			connection.setSource(intfU);

			sourceString = intfU + " of " + source;
		} else {
			sourceString = srcVertex.getBusInterface().toString();
		}
		@SuppressWarnings("unused")
		String targetString;
		if (tgtVertex.isComponentInstance()) {
			ComponentInstance target = tgtVertex.getComponentInstance();
			String intfVName = connection.getTarget().getName();

			BusInterface intfV = target.getComponent().getInterface(intfVName);

			if (intfV == null) {
				throw new Exception("In design \"" + design.getVlnv().getName()
						+ "\": A Connection refers to "
						+ "a non-existent port: \"" + intfVName
						+ "\" of instance \"" + target.getId() + "\"");
			}

			connection.setTarget(intfV);

			targetString = intfV + " of " + target;
		} else {
			targetString = tgtVertex.getBusInterface().toString();
		}
	}

	private void updateConnections() throws Exception {
		for (Connection connection : graph.edgeSet()) {
			updateConnection(connection);
		}
	}

}
