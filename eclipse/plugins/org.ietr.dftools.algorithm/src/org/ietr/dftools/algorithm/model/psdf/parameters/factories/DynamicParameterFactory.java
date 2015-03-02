package org.ietr.dftools.algorithm.model.psdf.parameters.factories;

import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.parameters.Parameter;
import org.ietr.dftools.algorithm.model.parameters.factories.ParameterFactory;
import org.ietr.dftools.algorithm.model.psdf.parameters.PSDFDynamicParameter;

public class DynamicParameterFactory extends ParameterFactory {

	public DynamicParameterFactory(AbstractGraph<?, ?> graph) {
		super(graph);
	}

	@Override
	public Parameter create(String name) {
		if (name.startsWith("$")) {
			return new PSDFDynamicParameter(name.substring(1));
		} else {
			return new Parameter(name);
		}
	}
}
