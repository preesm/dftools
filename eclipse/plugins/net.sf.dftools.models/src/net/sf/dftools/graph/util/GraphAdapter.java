/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.dftools.graph.util;

import static net.sf.dftools.graph.GraphPackage.eINSTANCE;

import java.util.List;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.Vertex;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * This class defines an adapter for a graph that automatically cleans up the
 * graph when vertices are removed from it (by removing their edges from the
 * graph) as well as when edges are removed from it (by unlinking them from
 * their source/target vertex).
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GraphAdapter extends AdapterImpl {

	@Override
	@SuppressWarnings("unchecked")
	public void notifyChanged(Notification msg) {
		Object feature = msg.getFeature();
		if (feature == eINSTANCE.getGraph_Vertices()) {
			switch (msg.getEventType()) {
			case Notification.REMOVE_MANY:
				List<Vertex> vertices = (List<Vertex>) msg.getOldValue();
				for (Vertex vertex : vertices) {
					removeEdgesOf(vertex);
				}
				break;

			case Notification.REMOVE:
				Vertex vertex = (Vertex) msg.getOldValue();
				removeEdgesOf(vertex);
				break;
			}
		} else if (feature == eINSTANCE.getGraph_Edges()) {
			switch (msg.getEventType()) {
			case Notification.REMOVE_MANY:
				List<Edge> edges = (List<Edge>) msg.getOldValue();
				for (Edge edge : edges) {
					unlinkEdge(edge);
				}
				break;

			case Notification.REMOVE:
				Edge edge = (Edge) msg.getOldValue();
				unlinkEdge(edge);
				break;
			}
		}
	}

	/**
	 * Removes incoming and outgoing edges of the given vertex from the graph
	 * that this adapter targets.
	 * 
	 * @param vertex
	 *            a vertex referenced by the graph
	 */
	private void removeEdgesOf(Vertex vertex) {
		List<Edge> edges = ((Graph) target).getEdges();
		edges.removeAll(vertex.getIncoming());
		edges.removeAll(vertex.getOutgoing());
	}

	/**
	 * Sets the source and target attributes of the given edge to
	 * <code>null</code>, so it is not referenced by vertices anymore.
	 * 
	 * @param edge
	 *            an edge of the graph
	 */
	private void unlinkEdge(Edge edge) {
		edge.setSource(null);
		edge.setTarget(null);
	}

}
