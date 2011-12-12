package net.sf.dftools.algorithm.model.generic;

import java.util.logging.Logger;

import net.sf.dftools.algorithm.factories.GenericEdgeFactory;
import net.sf.dftools.algorithm.factories.ModelVertexFactory;
import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.PropertyFactory;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

public class GenericGraph extends AbstractGraph<GenericVertex, GenericEdge> {

	public GenericGraph(GenericEdgeFactory factory) {
		super(factory);
	}

	@Override
	public void update(AbstractGraph<?, ?> observable, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public AbstractGraph<GenericVertex, GenericEdge> clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateModel(Logger logger) throws SDF4JException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ModelVertexFactory<GenericVertex> getVertexFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

}
