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
package net.sf.dftools.architecture.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.dftools.architecture.VLNV;
import net.sf.dftools.architecture.component.BusInterface;
import net.sf.dftools.architecture.design.transforms.Instantiator;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;

/**
 * An IP-XACT design.
 * 
 * @author Ghislain Roquier
 * 
 */

public class Design {

	private VLNV vlnv;

	private UndirectedGraph<Vertex, Connection> graph;

	private Map<String, BusInterface> interfaces;

	public Design(String id, VLNV vlnv,
			UndirectedGraph<Vertex, Connection> graph) {
		this.vlnv = vlnv;
		this.graph = graph;
	}

	public UndirectedGraph<Vertex, Connection> getGraph() {
		return graph;
	}

	public VLNV getVlnv() {
		return vlnv;
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
	
	public boolean containsComponentInstance(String name){
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isComponentInstance()) {
				if(vertex.getComponentInstance().getComponent().getClasz().equals(name))
					return true;
			}
		}
		return false;
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

	public Map<String, BusInterface> getInterfaces() {
		return interfaces;
	}

	public List<Connection> getInterConnections() {
		List<Connection> interconnection = new ArrayList<Connection>();
		for (Connection connection : graph.edgeSet()) {
			if (connection instanceof InterConnection) {
				interconnection.add(connection);
			}
		}
		return interconnection;
	}

	public List<Connection> getHierConnections() {
		List<Connection> hierconnections = new ArrayList<Connection>();
		for (Connection connection : graph.edgeSet()) {
			if (connection instanceof HierConnection) {
				hierconnections.add(connection);
			}
		}
		return hierconnections;
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

	public InterConnection getInterConnection(String srcName, String tgtName) {
		InterConnection conn = null;
		for (Connection connection : getInterConnections()) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);
			if (src.getComponentInstance().getId().equals(srcName)
					&& tgt.getComponentInstance().getId().equals(tgtName)) {
				conn = (InterConnection) connection;
				break;
			} else if (tgt.getComponentInstance().getId().equals(srcName)
					&& src.getComponentInstance().getId().equals(tgtName)) {
				conn = (InterConnection) connection;
				break;
			}
		}
		return conn;
	}

	public void instantiate(String path) throws Exception {
		new Instantiator(path).transform(this);
	}

	public BusInterface getInterface(String name) {
		return interfaces.get(name);
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

	public String getBusBetween(String startName, String endName) {
		Vertex start = getVertex(startName);
		Vertex end = getVertex(endName);
		
		if(start.equals(end)) {
			return null;
		}

		List<Connection> conns = DijkstraShortestPath.findPathBetween(graph,
				start, end);

		if (conns.size() == 2) {
			ComponentInstance inst = graph.getEdgeTarget(conns.get(0))
					.getComponentInstance();
			
			if(inst.getId().equals(startName)) {
				inst = graph.getEdgeSource(conns.get(0))
				.getComponentInstance();
			}

			return inst.getId();
		} else {
			// errors
			return null;
		}
	}

}
