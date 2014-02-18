/**
 * 
 */
package org.ietr.dftools.algorithm.model.dag.types;

import org.ietr.dftools.algorithm.model.AbstractVertexPropertyType;

/**
 * @author mpelcat
 * 
 */

/**
 * Class used to represent a Vertex property for the DAG
 */
public class DAGDefaultVertexPropertyType extends
		AbstractVertexPropertyType<Integer> {

	/**
	 * Creates a new DAGDefaultVertexPropertyType without specifying any value
	 */
	public DAGDefaultVertexPropertyType() {
		super();
	}

	/**
	 * Creates a new DAGDefaultVertexPropertyType with the given value
	 * 
	 * @param val
	 */
	public DAGDefaultVertexPropertyType(int val) {
		super(val);
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public AbstractVertexPropertyType<Integer> clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
