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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.Vertex;

/**
 * This class defines a topological sorter.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ReversePostOrder extends DFS {

	/**
	 * Builds the topological order of the given graph by calling
	 * {@link #caseVertex(Vertex)} for each vertex of the given graph that has
	 * no outgoing edges.
	 * 
	 * @param graph
	 *            a graph
	 */
	@SuppressWarnings("unchecked")
	public ReversePostOrder(Graph graph, List<? extends Vertex> entries) {
		super(graph.getVertices().size());

		if (entries == null) {
			entries = new ArrayList<Vertex>();
			for (Vertex vertex : graph.getVertices()) {
				if (vertex.getIncoming().isEmpty()) {
					((List<Vertex>) entries).add(vertex);
				}
			}
		}

		if (entries.isEmpty()) {
			// no entry point in the graph, take the first vertex
			visitPost(graph.getVertices().get(0));
		} else {
			for (Vertex vertex : entries) {
				visitPost(vertex);
			}
		}

		Collections.reverse(vertices);
	}

	/**
	 * Creates a new topological sorter.
	 */
	public ReversePostOrder(Vertex vertex) {
		super(vertex, true);
		Collections.reverse(vertices);
	}

}
