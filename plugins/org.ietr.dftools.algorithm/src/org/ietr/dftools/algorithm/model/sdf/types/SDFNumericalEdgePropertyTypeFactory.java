package org.ietr.dftools.algorithm.model.sdf.types;

import org.ietr.dftools.algorithm.model.AbstractEdgePropertyType;
import org.ietr.dftools.algorithm.model.PropertyFactory;
import org.ietr.dftools.algorithm.model.parameters.ExpressionValue;

/**
 * Factory to build SDF edge property base on an input string
 * 
 * @author jpiat
 * 
 */
public class SDFNumericalEdgePropertyTypeFactory implements PropertyFactory{

	
	private static SDFNumericalEdgePropertyTypeFactory instance ;
	
	private SDFNumericalEdgePropertyTypeFactory(){
		
	}
	
	public static SDFNumericalEdgePropertyTypeFactory getInstance(){
		if(instance == null){
			instance = new SDFNumericalEdgePropertyTypeFactory();
		}
		return instance ;
	}
	
	/**
	 * Creates a new SDFExpressionEdgePropertyType given the expression expr
	 * 
	 * @param expr
	 *            The expression
	 * @return The created SDFExpressionEdgePropertyType
	 */
	public AbstractEdgePropertyType<?> getSDFEdgePropertyType(
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
	public AbstractEdgePropertyType<?> getSDFEdgePropertyType(int val) {
		return new SDFIntEdgePropertyType(val);
	}

	@Override
	public Object create(Object value) {
		if(value instanceof String){
			return getSDFEdgePropertyType((String) value);
		}else if(value instanceof Integer){
			return getSDFEdgePropertyType((Integer) value);
		}
		return null ;
	}
}
