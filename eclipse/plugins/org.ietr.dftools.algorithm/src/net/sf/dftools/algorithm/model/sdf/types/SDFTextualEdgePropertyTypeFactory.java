package net.sf.dftools.algorithm.model.sdf.types;

import net.sf.dftools.algorithm.model.PropertyFactory;


/**
 * Factory to build SDF edge property base on an input string
 * @author jpiat
 *
 */
public class SDFTextualEdgePropertyTypeFactory implements PropertyFactory{

	private static SDFTextualEdgePropertyTypeFactory instance ;
	
	private SDFTextualEdgePropertyTypeFactory(){
		
	}
	
	public static SDFTextualEdgePropertyTypeFactory getInstance(){
		if(instance == null){
			instance = new SDFTextualEdgePropertyTypeFactory();
		}
		return instance ;
	}
	
	/**
	 * Creates a new SDFStringEdgePropertyType given the value val
	 * @param val The value
	 * @return The created SDFStringEdgePropertyType
	 */
	public SDFStringEdgePropertyType getSDFEdgePropertyType(String val){
		return new SDFStringEdgePropertyType(val);
	}

	@Override
	public Object create(Object value) {
		if(value instanceof String){
			return getSDFEdgePropertyType((String) value);
		}
		return null ;
	}

}
