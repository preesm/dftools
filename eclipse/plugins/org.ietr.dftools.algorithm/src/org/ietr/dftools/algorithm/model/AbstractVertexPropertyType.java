package org.ietr.dftools.algorithm.model;

/**
 * Abstract generic Class to represent any Vertex Property
 * 
 * @author jpiat
 * 
 * @param <T>
 */
public abstract class AbstractVertexPropertyType<T> implements CloneableProperty{

	protected T value;

	/**
	 * Creates a new AbstractVertexPropertyType without specifying any value
	 */
	public AbstractVertexPropertyType() {
		value = null;
	}

	/**
	 * Creates a new AbstractVertexPropertyType with the given value
	 * 
	 * @param val
	 */
	public AbstractVertexPropertyType(T val) {
		value = val;
	}

	@Override
	public abstract AbstractVertexPropertyType<T> clone();
	
	/**
	 * Gives this AbstractVertexPropertyType value
	 * 
	 * @return This AbstractVertexPropertyType values
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Gives the Integer representation of this AbstractVertexPropertyType value
	 * 
	 * @return The Integer value of this AbstractVertexPropertyType
	 */
	public abstract int intValue();

	/**
	 * Set this AbstractVertexPropertyType value
	 * 
	 * @param val
	 *            The value to set for this AbstractVertexPropertyType
	 */
	public void setValue(T val) {
		value = val;
	}

	@Override
	public abstract String toString();
}
