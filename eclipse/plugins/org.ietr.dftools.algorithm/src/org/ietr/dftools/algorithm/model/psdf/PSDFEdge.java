package org.ietr.dftools.algorithm.model.psdf;

import org.ietr.dftools.algorithm.model.PropertyFactory;
import org.ietr.dftools.algorithm.model.psdf.types.PSDFNumericalEdgePropertyTypeFactory;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.types.SDFStringEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.types.SDFTextualEdgePropertyTypeFactory;

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
