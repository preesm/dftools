package net.sf.dftools.algorithm.model.sdf.types;

import net.sf.dftools.algorithm.model.AbstractEdgePropertyType;

/**
 * Class used to represent the string edge property type in a SDF
 * 
 * @author mpelcat
 * 
 */
public class SDFStringEdgePropertyType extends
		AbstractEdgePropertyType<String> {

	/**
	 * Creates a new SDFDefaultEdgePropertyType with the given String value
	 * 
	 * @param val
	 *            The String value of this SDFDefaultEdgePropertyType
	 */
	public SDFStringEdgePropertyType(String val) {
		super(val);
	}

	@Override
	public AbstractEdgePropertyType<String> clone() {
		return new SDFStringEdgePropertyType(value);
	}

	@Override
	public int intValue() {
		return 0;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
