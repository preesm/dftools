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
package org.ietr.dftools.architecture.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ietr.dftools.architecture.VLNV;
import org.ietr.dftools.architecture.component.BusInterface;
import org.ietr.dftools.architecture.design.transforms.Instantiator;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.Multigraph;

/**
 * An IP-XACT design.
 * 
 * @author Ghislain Roquier
 * 
 */

public class Design {

	private String name;

	private VLNV vlnv;

	private UndirectedGraph<Vertex, Connection> graph;

	private Map<String, BusInterface> interfaces;

	public Design(String name) {
		this.name = name;
		this.vlnv = new VLNV(name);
		this.graph = new Multigraph<Vertex, Connection>(Connection.class);
	}

	public Design(String name, VLNV vlnv,
			UndirectedGraph<Vertex, Connection> graph) {
		this.name = name;
		this.vlnv = vlnv;
		this.graph = graph;
	}

	public boolean containsComponentInstance(String name) {
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isComponentInstance()) {
				if (vertex.getComponentInstance().getComponent().getName()
						.equals(name))
					return true;
			}
		}
		return false;
	}

	public String getBusBetween(String startName, String endName) {
		Vertex start = getVertex(startName);
		Vertex end = getVertex(endName);
		if (start.equals(end)) {
			return null;
		}
		List<Connection> conns = DijkstraShortestPath.findPathBetween(graph,
				start, end);
		if (conns.size() == 2) {
			ComponentInstance inst = graph.getEdgeTarget(conns.get(0))
					.getComponentInstance();

			if (inst.getId().equals(startName)) {
				inst = graph.getEdgeSource(conns.get(0)).getComponentInstance();
			}

			return inst.getId();
		} else {
			// errors
			return null;
		}
	}

	public List<BusInterface> getBusInterfaces() {
		List<BusInterface> interfaces = new ArrayList<BusInterface>();
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isBusInterface()) {
				interfaces.add(vertex.getBusInterface());
			}
		}
		return interfaces;
	}

	public ComponentInstance getComponentInstance(String name) {
		ComponentInstance instance = null;
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isComponentInstance()) {
				if (vertex.getComponentInstance().getId().equals(name)) {
					instance = vertex.getComponentInstance();
					break;
				}
			}
		}
		return instance;
	}

	public List<ComponentInstance> getComponentInstances() {
		List<ComponentInstance> instances = new ArrayList<ComponentInstance>();
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isComponentInstance()) {
				instances.add(vertex.getComponentInstance());
			}
		}
		return instances;
	}

	public UndirectedGraph<Vertex, Connection> getGraph() {
		return graph;
	}

	public Connection getInterConnection(String srcName, String tgtName) {
		Connection conn = null;
		for (Connection connection : graph.edgeSet()) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);
			if (src.isComponentInstance() && tgt.isComponentInstance()) {
				if (src.getComponentInstance().getId().equals(srcName)
						&& tgt.getComponentInstance().getId().equals(tgtName)) {
					conn = connection;
					break;
				} else if (tgt.getComponentInstance().getId().equals(srcName)
						&& src.getComponentInstance().getId().equals(tgtName)) {
					conn = connection;
					break;
				}

			}
		}
		return conn;
	}

	public Map<String, BusInterface> getInterfaces() {
		return interfaces;
	}

	public String getName() {
		return name;
	}

	public Vertex getVertex(String name) {
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isComponentInstance()) {
				if (vertex.getComponentInstance().getId().equals(name)) {
					return vertex;
				}
			}
		}
		return null;
	}

	public VLNV getVlnv() {
		return vlnv;
	}

	/**
	 * Walks through the hierarchy, instantiate components, and checks that
	 * connections actually point to ports defined in components.
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void instantiate(String path) throws Exception {
		new Instantiator(path).transform(this);
	}
}
