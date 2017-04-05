package org.ietr.dftools.architecture.test;

import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.SlamFactory;
import org.junit.Assert;
import org.junit.Test;

public class FactoryTest {

	@Test
	public void testFactoryDesign() {
		Design d = SlamFactory.eINSTANCE.createDesign();
		Assert.assertNotNull(d);
	}
}
