package org.ietr.dftools.algorithm.model.dag.types;

import org.ietr.dftools.algorithm.model.AbstractEdgePropertyType;

/**
 * Class used to represent a DAG edge property
 * 
 * @author jpiat
 * 
 */
public class DAGEdgePropertyType extends AbstractEdgePropertyType<Integer> {

	/**
	 * Creates a new empty DAGEdgePropertyType
	 */
	public DAGEdgePropertyType() {
		super();
	}

	/**
	 * Creates a new DAGEdgePropertyType with the given value
	 * 
	 * @param val
	 */
	public DAGEdgePropertyType(int val) {
		value = val;
	}

	@Override
	public AbstractEdgePropertyType<Integer> clone() {
		return new DAGEdgePropertyType(value);
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
