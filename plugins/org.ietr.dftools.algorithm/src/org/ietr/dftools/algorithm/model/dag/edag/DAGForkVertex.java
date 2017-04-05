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
public class DAGForkVertex extends DAGVertex{

	/**
	 * Key to access to property dag_broadcast_vertex
	 */
	public static final String DAG_FORK_VERTEX = "dag_fork_vertex";
	
	
	/**
	 * String to access the property edges order
	 */
	public static final String EDGES_ORDER ="edges_order"; 


	/**
	 * Creates a new DAGVertex
	 */
	public DAGForkVertex() {
		super();
		setKind(DAG_FORK_VERTEX);
	}
	
	/**
	 * Creates a new DAGForkVertex with the name "n", the execution time "t" and the
	 * number of repetition "nb"
	 * 
	 * @param n
	 *            This Vertex name
	 * @param t
	 *            This Vertex execution time
	 * @param nb
	 *            This Vertex number of repetition
	 */
	public DAGForkVertex(String n, AbstractVertexPropertyType<?> t,
			AbstractVertexPropertyType<?> nb) {
		super(n, t, nb);
		setKind(DAG_FORK_VERTEX);
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
