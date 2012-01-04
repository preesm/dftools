package net.sf.dftools.algorithm.model.parameters.factories;

import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.parameters.Parameter;
import net.sf.dftools.algorithm.model.psdf.parameters.PSDFDynamicParameter;

public class ParameterFactory {

	protected AbstractGraph<?, ?> pGraph;

	public ParameterFactory(AbstractGraph<?, ?> graph) {
		pGraph = graph;
	}

	public Parameter create(String name) {
		return new Parameter(name);
	}
}
