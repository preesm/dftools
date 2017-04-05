package org.ietr.dftools.algorithm.test;

import java.util.Set;

import org.ietr.dftools.algorithm.generator.SDFRandomGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.junit.Assert;
import org.junit.Test;

public class SDFRandomGraphTest {

	@Test
	public void testRandomGen() {
		final int nbVertex = 20;

		final SDFRandomGraph sdfRandomGraph = new SDFRandomGraph();
		Assert.assertNotNull(sdfRandomGraph);
		try {
			final SDFGraph createRandomGraph = sdfRandomGraph.createRandomGraph(nbVertex, 1, 5, 1, 5, 1, 12);
			Assert.assertNotNull(createRandomGraph);
			final Set<SDFAbstractVertex> allVertices = createRandomGraph.getAllVertices();
			Assert.assertNotNull(allVertices);
			final int size = allVertices.size();
			Assert.assertEquals(nbVertex, size);

		} catch (SDF4JException e) {
			Assert.fail();
		}
	}

}
