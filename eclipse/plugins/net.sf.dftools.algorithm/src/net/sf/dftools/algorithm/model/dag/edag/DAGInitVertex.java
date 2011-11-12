package net.sf.dftools.algorithm.model.dag.edag;

import net.sf.dftools.algorithm.model.AbstractVertexPropertyType;
import net.sf.dftools.algorithm.model.dag.DAGVertex;

/**
 * Represent a initialization vertex in the DAG
 * @author jpiat
 *
 */
public class DAGInitVertex extends DAGVertex{

	/**
	 * Key to access to property dag_init_vertex
	 */
	public static final String DAG_INIT_VERTEX = "dag_init_vertex";

	public static final String END_REFERENCE ="END_REFERENCE"; 
	
	
	/**
	 * Creates a new DAGInitVertex
	 */
	public DAGInitVertex() {
		super();
		setKind(DAG_INIT_VERTEX);
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
	public DAGInitVertex(String n, AbstractVertexPropertyType<?> t,
			AbstractVertexPropertyType<?> nb) {
		super(n, t, nb);
		setKind(DAG_INIT_VERTEX);
	}
	
	public void setEndReference(DAGEndVertex ref){
		this.getPropertyBean().setValue(END_REFERENCE, ref);
	}
	
	public DAGEndVertex getEndReference(){
		return (DAGEndVertex) this.getPropertyBean().getValue(END_REFERENCE);
	}
	
	public String toString() {
		return getName() ;
	}
}