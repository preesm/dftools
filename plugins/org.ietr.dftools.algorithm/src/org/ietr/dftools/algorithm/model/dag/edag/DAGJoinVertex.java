/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
package org.ietr.dftools.algorithm.model.dag.edag;

import java.util.ArrayList;
import java.util.List;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractVertexPropertyType;
import org.ietr.dftools.algorithm.model.dag.DAGEdge;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;

/**
 * Class used to represent a braodcast Vertex in a Directed Acyclic Graph
 * 
 * @author jpiat
 * 
 */
public class DAGJoinVertex extends DAGVertex{

	/**
	 * Key to access to property dag_broadcast_vertex
	 */
	public static final String DAG_JOIN_VERTEX = "dag_join_vertex";


	/**
	 * String to access the property edges order
	 */
	public static final String EDGES_ORDER ="edges_order"; 
	
	/**
	 * Creates a new DAGVertex
	 */
	public DAGJoinVertex() {
		super();
		setKind(DAG_JOIN_VERTEX);
	}
	
	/**
	 * Creates a new DAGJoinVertex with the name "n", the execution time "t" and the
	 * number of repetition "nb"
	 * 
	 * @param n
	 *            This Vertex name
	 * @param t
	 *            This Vertex execution time
	 * @param nb
	 *            This Vertex number of repetition
	 */
	public DAGJoinVertex(String n, AbstractVertexPropertyType<?> t,
			AbstractVertexPropertyType<?> nb) {
		super(n, t, nb);
		setKind(DAG_JOIN_VERTEX);
	}
	
	@SuppressWarnings("unchecked")
	private void addConnection(DAGEdge newEdge){
		if(this.getPropertyBean().getValue(EDGES_ORDER) == null){
			List<DAGEdge> connections = new ArrayList<DAGEdge>();
			this.getPropertyBean().setValue(EDGES_ORDER, connections);
		}
		((List<DAGEdge>) this.getPropertyBean().getValue(EDGES_ORDER)).add(newEdge);
	}
	
	@SuppressWarnings("unchecked")
	private void removeConnection(DAGEdge newEdge){
		if(this.getPropertyBean().getValue(EDGES_ORDER) == null){
			List<DAGEdge> connections = new ArrayList<DAGEdge>();
			this.getPropertyBean().setValue(EDGES_ORDER, connections);
		}
		((List<DAGEdge>) this.getPropertyBean().getValue(EDGES_ORDER)).remove(newEdge);
	}
	
	/**
	 * Gives the edge connection index
	 * @param edge The edge to get the connection index
	 * @return  The connection index of the edge
	 */
	@SuppressWarnings("unchecked")
	public Integer getEdgeIndex(DAGEdge edge){
		if(((List<DAGEdge>) this.getPropertyBean().getValue(EDGES_ORDER)) != null){
			int i = 0 ;
			List<DAGEdge> connections = ((List<DAGEdge>) this.getPropertyBean().getValue(EDGES_ORDER));
			for(DAGEdge eqEdge : connections){
				if(eqEdge.compare(edge)){
					return i ;
				}
				i ++ ;
			}
		}
		return 0 ;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionAdded(AbstractEdge e) {
		addConnection((DAGEdge) e);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionRemoved(AbstractEdge e) {
		removeConnection((DAGEdge) e);
	}
	
	@Override
	public String toString() {
		return getName() ;
	}
}
