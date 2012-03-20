/*
 * Copyright (c) 2012, Synflow
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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
package net.sf.dftools.graph.visit;

import java.util.List;

import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.Vertex;

/**
 * This class defines a topological sorter. This visitor can be used in two
 * ways: 1) by calling <code>doSwitch</code> on a graph to obtain its
 * topological order (updates the topological order by visiting all its vertices
 * that do not have outgoing edges) 2) by calling <code>doSwitch</code> on a
 * vertex to update the topological order by recursively visiting the
 * predecessors of that vertex.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TopologicalSorter extends Ordering {

	/**
	 * Creates a new topological sorter.
	 */
	public TopologicalSorter() {
	}
	
	/**
	 * Builds the topological order of the given graph by calling
	 * {@link #caseVertex(Vertex)} for each vertex of the given graph that has
	 * no outgoing edges.
	 * 
	 * @param graph
	 *            a graph
	 */
	public List<Vertex> visitGraph(Graph graph) {
		for (Vertex vertex : graph.getVertices()) {
			if (vertex.getOutgoing().isEmpty()) {
				visitVertex(vertex);
			}
		}
		return vertices;
	}

	public void visitVertex(Vertex vertex) {
		if (!visited.contains(vertex)) {
			visited.add(vertex);
			for (Vertex pred : vertex.getPredecessors()) {
				visitVertex(pred);
			}
			vertices.add(vertex);
		}
	}

}
