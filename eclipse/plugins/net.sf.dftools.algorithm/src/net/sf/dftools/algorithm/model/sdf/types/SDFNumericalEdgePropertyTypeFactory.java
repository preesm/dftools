package net.sf.dftools.algorithm.model.sdf.types;

import net.sf.dftools.algorithm.model.AbstractEdgePropertyType;
import net.sf.dftools.algorithm.model.parameters.ExpressionValue;

/**
 * Factory to build SDF edge property base on an input string
 * 
 * @author jpiat
 * 
 */
public class SDFNumericalEdgePropertyTypeFactory {

	/**
	 * Creates a new SDFExpressionEdgePropertyType given the expression expr
	 * 
	 * @param expr
	 *            The expression
	 * @return The created SDFExpressionEdgePropertyType
	 */
	public static AbstractEdgePropertyType<?> getSDFEdgePropertyType(
			String expr) {
		try{
			int value = Integer.decode(expr);
			return new SDFIntEdgePropertyType(value);
		}catch(NumberFormatException e){
			return new SDFExpressionEdgePropertyType(new ExpressionValue(expr));
		}
	}

	/**
	 * Creates a new SDFIntEdgePropertyType given the value val
	 * 
	 * @param val
	 *            The integer value
	 * @return The created SDFIntEdgePropertyType
	 */
	public static AbstractEdgePropertyType<?> getSDFEdgePropertyType(int val) {
		return new SDFIntEdgePropertyType(val);
	}
}
