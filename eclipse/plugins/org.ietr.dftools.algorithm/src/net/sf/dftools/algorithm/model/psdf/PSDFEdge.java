package net.sf.dftools.algorithm.model.psdf;

import net.sf.dftools.algorithm.model.PropertyFactory;
import net.sf.dftools.algorithm.model.psdf.types.PSDFNumericalEdgePropertyTypeFactory;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.sdf.types.SDFStringEdgePropertyType;
import net.sf.dftools.algorithm.model.sdf.types.SDFTextualEdgePropertyTypeFactory;

public class PSDFEdge extends SDFEdge {

	public PSDFEdge(SDFIntEdgePropertyType sdfIntEdgePropertyType,
			SDFIntEdgePropertyType sdfIntEdgePropertyType2,
			SDFIntEdgePropertyType sdfIntEdgePropertyType3,
			SDFStringEdgePropertyType sdfStringEdgePropertyType) {
		super(sdfStringEdgePropertyType, sdfIntEdgePropertyType2,
				sdfIntEdgePropertyType3, sdfStringEdgePropertyType);
	}

	public PropertyFactory getFactoryForProperty(String propertyName) {
		if (propertyName.equals(EDGE_CONS) || propertyName.equals(EDGE_PROD)
				|| propertyName.equals(EDGE_DELAY)) {
			return PSDFNumericalEdgePropertyTypeFactory.getInstance();
		} else if (propertyName.equals(DATA_TYPE)) {
			return SDFTextualEdgePropertyTypeFactory.getInstance();
		}
		return null;
	}
}
