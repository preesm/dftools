package org.ietr.dftools.algorithm.model.generic;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.PropertyFactory;

public class GenericVertex extends AbstractVertex<GenericGraph> {

	@Override
	public AbstractVertex<?> clone() {
		GenericVertex newVertex = new GenericVertex();
		for (String key : this.getPropertyBean().keys()) {
			if (this.getPropertyBean().getValue(key) != null) {
				Object val = this.getPropertyBean().getValue(key);
				newVertex.getPropertyBean().setValue(key, val);
			}
		}

		return newVertex;
	}

	@Override
	public void connectionAdded(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionRemoved(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub

	}

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

}
