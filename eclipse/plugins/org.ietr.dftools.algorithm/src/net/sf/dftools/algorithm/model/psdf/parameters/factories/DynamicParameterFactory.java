package net.sf.dftools.algorithm.model.psdf.parameters.factories;

import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.parameters.Parameter;
import net.sf.dftools.algorithm.model.parameters.factories.ParameterFactory;
import net.sf.dftools.algorithm.model.psdf.parameters.PSDFDynamicParameter;

public class DynamicParameterFactory extends ParameterFactory {

	public DynamicParameterFactory(AbstractGraph<?, ?> graph) {
		super(graph);
	}

	public Parameter create(String name) {
		if (name.startsWith("$")) {
			return new PSDFDynamicParameter(name.substring(1));
		} else {
			return new Parameter(name);
		}
	}
}
