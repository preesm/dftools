package org.ietr.dftools.algorithm.model.psdf.parameters.factories;

import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.parameters.Argument;
import org.ietr.dftools.algorithm.model.parameters.factories.ArgumentFactory;
import org.ietr.dftools.algorithm.model.psdf.PSDFGraph;
import org.ietr.dftools.algorithm.model.psdf.parameters.PSDFDynamicArgument;
import org.ietr.dftools.algorithm.model.psdf.parameters.PSDFDynamicParameter;

public class DynamicArgumentFactory extends ArgumentFactory {

	public DynamicArgumentFactory(AbstractVertex<?> vertex) {
		super(vertex);
	}

	public Argument create(String name, String value) {
		if (value.charAt(0) == '$' && pVertex.getBase() != null) {
			PSDFGraph pGraph = ((PSDFGraph) pVertex.getBase());
			PSDFDynamicParameter dParam = pGraph.getDynamicParameter(value
					.substring(1));
			return new PSDFDynamicArgument(name, dParam);
		} else {
			return super.create(name, value);
		}
	}
}
