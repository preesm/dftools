package net.sf.dftools.algorithm.model.sdf.types;


/**
 * Factory to build SDF edge property base on an input string
 * @author jpiat
 *
 */
public class SDFTextualEdgePropertyTypeFactory {

	
	/**
	 * Creates a new SDFStringEdgePropertyType given the value val
	 * @param val The value
	 * @return The created SDFStringEdgePropertyType
	 */
	public static SDFStringEdgePropertyType getSDFEdgePropertyType(String val){
		return new SDFStringEdgePropertyType(val);
	}

}
