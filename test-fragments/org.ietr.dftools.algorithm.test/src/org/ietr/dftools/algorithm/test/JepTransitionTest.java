package org.ietr.dftools.algorithm.test;

import org.ietr.dftools.algorithm.model.parameters.ExpressionValue;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.parameters.NoIntegerValueException;
import org.junit.Assert;
import org.junit.Test;

public class JepTransitionTest {

	@Test
	public void testExpressionValue() {
		double expected = 2 + 5 * 7 / 1.2 * 7.00002;
		ExpressionValue value = new ExpressionValue("2 + 5 * 7 / 1.2 * 7.00002");
		try {
			int intValue = value.intValue();
			Assert.assertEquals(new Double(expected).intValue(), intValue);
		} catch (InvalidExpressionException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (NoIntegerValueException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
