package org.ietr.dftools.algorithm.model.psdf.visitors;

import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.psdf.PSDFGraph;
import org.ietr.dftools.algorithm.model.visitors.AbstractHierarchyFlattening;

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
