package net.sf.dftools.algorithm.model.psdf.visitors;

import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.psdf.PSDFGraph;
import net.sf.dftools.algorithm.model.visitors.AbstractHierarchyFlattening;

public class PSDFHierarchyFlattening extends AbstractHierarchyFlattening<PSDFGraph>{

	@SuppressWarnings("rawtypes")
	@Override
	protected void treatSinkInterface(AbstractVertex vertex,
			AbstractGraph parentGraph, int depth)
			throws InvalidExpressionException {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void treatSourceInterface(AbstractVertex vertex,
			AbstractGraph parentGraph, int depth)
			throws InvalidExpressionException {
		// TODO Auto-generated method stub
		
	}

}
