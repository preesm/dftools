package org.ietr.dftools.algorithm.model.dag.edag;

import org.ietr.dftools.algorithm.model.AbstractVertexPropertyType;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;

/**
 * Class used to represent a braodcast Vertex in a Directed Acyclic Graph
 * 
 * @author jpiat
 * 
 */
public class DAGBroadcastVertex extends DAGVertex{

	/**
	 * Key to access to property dag_broadcast_vertex
	 */
	public static final String DAG_BROADCAST_VERTEX = "dag_broadcast_vertex";


	/**
	 * Creates a new DAGVertex
	 */
	public DAGBroadcastVertex() {
		super();
		setKind(DAG_BROADCAST_VERTEX);
	}
	
	/**
	 * Creates a new DAGBroadcastVertex with the name "n", the execution time "t" and the
	 * number of repetition "nb"
	 * 
	 * @param n
	 *            This Vertex name
	 * @param t
	 *            This Vertex execution time
	 * @param nb
	 *            This Vertex number of repetition
	 */
	public DAGBroadcastVertex(String n, AbstractVertexPropertyType<?> t,
			AbstractVertexPropertyType<?> nb) {
		super(n, t, nb);
		setKind(DAG_BROADCAST_VERTEX);
	}
	
	@Override
	public String toString() {
		return getName() ;
	}
}
