package org.ietr.dftools.algorithm.model;

import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;

/**
 * Abstract generic Class used to represent a Edge property
 * 
 * @author jpiat
 * 
 * @param <T>
 */
public abstract class AbstractEdgePropertyType<T> implements CloneableProperty{

	protected T value;

	/**
	 * Creates a new AbstractEdgePropertyType without specifyiong any value
	 */
	public AbstractEdgePropertyType() {
		value = null;
	}

	/**
	 * Creates a new AbstractEdgePropertyType with the given value
	 * 
	 * @param val
	 */
	public AbstractEdgePropertyType(T val) {
		value = val;
	}

	@Override
	public abstract AbstractEdgePropertyType<T> clone();

	/**
	 * Gives this AbstractEdgePropertyType value
	 * 
	 * @return The value of this AbstractEdgePropertyType
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Gives the Integer representation of this AbstractEdgePropertyType
	 * 
	 * @return The Integer value of this AbstractEdgePropertyType
	 * @throws InvalidExpressionException 
	 */
	public abstract int intValue() throws InvalidExpressionException;

	/**
	 * Set this AbstractEdgePropertyType value
	 * 
	 * @param val
	 */
	public void setValue(T val) {
		value = val;
	}

	@Override
	public abstract String toString();

}
