package net.sf.dftools.algorithm.factories;

import net.sf.dftools.algorithm.model.generic.GenericEdge;
import net.sf.dftools.algorithm.model.generic.GenericVertex;

import org.jgrapht.EdgeFactory;

public class GenericEdgeFactory implements
		EdgeFactory<GenericVertex, GenericEdge> {

	@Override
	public GenericEdge createEdge(GenericVertex arg0, GenericVertex arg1) {
		return new GenericEdge();
	}

}
