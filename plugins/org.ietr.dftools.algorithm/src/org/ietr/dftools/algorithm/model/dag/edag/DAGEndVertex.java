package org.ietr.dftools.algorithm.model.dag.edag;

import org.ietr.dftools.algorithm.model.AbstractVertexPropertyType;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;

/**
 * Represent an initialization vertex in the DAG
 * @author jpiat
 *
 */
public class DAGEndVertex extends DAGVertex{

	/**
	 * Key to access to property dag_end_vertex
	 */
	public static final String DAG_END_VERTEX = "dag_end_vertex";
	
	
	/**
	 * Creates a new DAGInitVertex
	 */
	public DAGEndVertex() {
		super();
		setKind(DAG_END_VERTEX);
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
	public DAGEndVertex(String n, AbstractVertexPropertyType<?> t,
			AbstractVertexPropertyType<?> nb) {
		super(n, t, nb);
		setKind(DAG_END_VERTEX);
	}
	

	
	
	@Override
	public String toString() {
		return getName() ;
	}
}