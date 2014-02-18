package net.sf.dftools.algorithm.model.psdf.types;

import net.sf.dftools.algorithm.model.AbstractEdgePropertyType;
import net.sf.dftools.algorithm.model.PropertyFactory;
import net.sf.dftools.algorithm.model.parameters.ExpressionValue;
import net.sf.dftools.algorithm.model.sdf.types.SDFExpressionEdgePropertyType;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;

/**
 * Factory to build SDF edge property base on an input string
 * 
 * @author jpiat
 * 
 */
public class PSDFNumericalEdgePropertyTypeFactory implements PropertyFactory {

	private static PSDFNumericalEdgePropertyTypeFactory instance;

	private PSDFNumericalEdgePropertyTypeFactory() {

	}

	public static PSDFNumericalEdgePropertyTypeFactory getInstance() {
		if (instance == null) {
			instance = new PSDFNumericalEdgePropertyTypeFactory();
		}
		return instance;
	}

	/**
	 * Creates a new SDFExpressionEdgePropertyType given the expression expr
	 * 
	 * @param expr
	 *            The expression
	 * @return The created SDFExpressionEdgePropertyType
	 */
	public AbstractEdgePropertyType<?> getPSDFEdgePropertyType(String expr) {
		try {
			if (expr.charAt(0) == '$') {
				return new PSDFEdgePropertyType(expr.substring(1));
			}
			int value = Integer.decode(expr);
			return new SDFIntEdgePropertyType(value);
		} catch (NumberFormatException e) {
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

	@Override
	public Object create(Object value) {
		if (value instanceof String) {
			return getPSDFEdgePropertyType((String) value);
		} else if (value instanceof Integer) {
			return getPSDFEdgePropertyType((Integer) value);
		}
		return null;
	}
}
