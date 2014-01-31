package net.sf.dftools.algorithm.model.psdf.parameters.factories;

import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.parameters.Argument;
import net.sf.dftools.algorithm.model.parameters.factories.ArgumentFactory;
import net.sf.dftools.algorithm.model.psdf.PSDFGraph;
import net.sf.dftools.algorithm.model.psdf.parameters.PSDFDynamicArgument;
import net.sf.dftools.algorithm.model.psdf.parameters.PSDFDynamicParameter;

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
