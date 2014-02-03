package org.ietr.dftools.algorithm.factories;

import org.ietr.dftools.algorithm.model.generic.GenericEdge;
import org.ietr.dftools.algorithm.model.generic.GenericVertex;
import org.jgrapht.EdgeFactory;

public class GenericEdgeFactory implements
		EdgeFactory<GenericVertex, GenericEdge> {

	@Override
	public GenericEdge createEdge(GenericVertex arg0, GenericVertex arg1) {
		return new GenericEdge();
	}

}
