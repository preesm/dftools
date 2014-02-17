package org.ietr.dftools.algorithm.model.dag.types;

import org.ietr.dftools.algorithm.model.AbstractEdgePropertyType;

/**
 * Class used to represent Edge properties in a DAG
 * 
 * @author jpiat
 * 
 */
public class DAGDefaultEdgePropertyType extends
		AbstractEdgePropertyType<Integer> {

	/**
	 * Creates a new DAGDefaultEdgePropertyType without specifying any value
	 */
	public DAGDefaultEdgePropertyType() {
		super();
	}

	/**
	 * Creates a new DAGDefaultEdgePropertyType with the given value
	 * 
	 * @param val
	 *            The value to set for this DAGDefaultEdgePropertyType
	 */
	public DAGDefaultEdgePropertyType(int val) {
		super(val);
	}
	
	/**
	 * Creates a new DAGDefaultEdgePropertyType with the given value
	 * @param val The value to set
	 */
	public DAGDefaultEdgePropertyType(String val) {
		super(new Integer(val));
	}

	@Override
	public AbstractEdgePropertyType<Integer> clone() {
		return new DAGDefaultEdgePropertyType(value);
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
