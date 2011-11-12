package net.sf.dftools.algorithm.model.psdf.types;

import net.sf.dftools.algorithm.model.AbstractEdgePropertyType;
import net.sf.dftools.algorithm.model.parameters.ExpressionValue;
import net.sf.dftools.algorithm.model.sdf.types.SDFExpressionEdgePropertyType;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;

/**
 * Factory to build SDF edge property base on an input string
 * 
 * @author jpiat
 * 
 */
public class PSDFNumericalEdgePropertyTypeFactory {

	/**
	 * Creates a new SDFExpressionEdgePropertyType given the expression expr
	 * 
	 * @param expr
	 *            The expression
	 * @return The created SDFExpressionEdgePropertyType
	 */
	public static AbstractEdgePropertyType<?> getPSDFEdgePropertyType(
			String expr) {
		try{
			if(expr.charAt(0)== '$'){
				return new PSDFEdgePropertyType(expr.substring(1));
			}
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
	public static AbstractEdgePropertyType<?> getPSDFEdgePropertyType(int val) {
		return new SDFIntEdgePropertyType(val);
	}
}
