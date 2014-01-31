package net.sf.dftools.algorithm.model.parameters.factories;

import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.parameters.Argument;

public class ArgumentFactory {

	protected AbstractVertex<?> pVertex;

	public ArgumentFactory(AbstractVertex<?> vertex) {
		pVertex = vertex;
	}

	public Argument create(String name, String value) {
		return new Argument(name, value);
	}
}
