package org.ietr.dftools.algorithm.model.parameters.factories;

import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.parameters.Parameter;

public class ParameterFactory {

	protected AbstractGraph<?, ?> pGraph;

	public ParameterFactory(AbstractGraph<?, ?> graph) {
		pGraph = graph;
	}

	public Parameter create(String name) {
		return new Parameter(name);
	}
}
