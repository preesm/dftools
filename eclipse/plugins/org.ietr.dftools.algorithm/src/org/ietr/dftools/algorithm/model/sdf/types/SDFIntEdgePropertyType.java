package org.ietr.dftools.algorithm.model.sdf.types;

import org.ietr.dftools.algorithm.model.AbstractEdgePropertyType;

/**
 * Class used to represent the integer edge property type in a SDF
 * 
 * @author jpiat
 * 
 */
public class SDFIntEdgePropertyType extends
		AbstractEdgePropertyType<Integer> {

	/**
	 * Creates a new SDFDefaultEdgePropertyType with the given Integer value
	 * 
	 * @param val
	 *            The Integer value of this SDFDefaultEdgePropertyType
	 */
	public SDFIntEdgePropertyType(int val) {
		super(val);
	}

	/**
	 * Creates a new SDFDefaultEdgePropertyType with the given String value
	 * 
	 * @param val
	 *            The String value of this SDFDefaultEdgePropertyType
	 */
	public SDFIntEdgePropertyType(String val) {
		super(new Integer(val));
	}

	@Override
	public AbstractEdgePropertyType<Integer> clone() {
		return new SDFIntEdgePropertyType(value);
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
