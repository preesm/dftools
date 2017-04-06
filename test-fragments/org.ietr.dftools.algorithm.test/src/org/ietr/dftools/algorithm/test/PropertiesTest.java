package org.ietr.dftools.algorithm.test;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.junit.Assert;
import org.junit.Test;

public class PropertiesTest {

	private static final String MY_PROPERTY_CONSTANT = "toregregzegergregreto";

	@Test
	public void testSetProp() {
		SDFAbstractVertex toto = new SDFAbstractVertex() {

			@Override
			public void connectionRemoved(AbstractEdge<?, ?> e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void connectionAdded(AbstractEdge<?, ?> e) {
				// TODO Auto-generated method stub

			}

			@Override
			public SDFAbstractVertex clone() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		int input = 15;
		toto.getPropertyBean().setValue(MY_PROPERTY_CONSTANT, input);

		final Integer value = (Integer) toto.getPropertyBean().getValue(MY_PROPERTY_CONSTANT,Integer.class);
		Assert.assertEquals(input, value.intValue());

	}

}
