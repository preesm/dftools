package org.ietr.dftools.algorithm.model.dag.types;

import org.ietr.dftools.algorithm.model.AbstractVertexPropertyType;

/**
 * Class used to represent a Vertex property in a DAG
 * 
 * @author jpiat
 * 
 */
public class DAGVertexPropertyType extends AbstractVertexPropertyType<Integer> {

	/**
	 * Creates a new empty DAGVertexPropertyType
	 */
	public DAGVertexPropertyType() {
		super();
	}

	/**
	 * Creates a new DAGVertexPropertyType with the given value
	 * 
	 * @param val
	 *            The value to set for this DAGVertexPropertyType
	 */
	public DAGVertexPropertyType(int val) {
		value = val;
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
