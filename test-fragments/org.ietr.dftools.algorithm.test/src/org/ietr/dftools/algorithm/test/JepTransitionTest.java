package org.ietr.dftools.algorithm.test;

import org.ietr.dftools.algorithm.model.parameters.ExpressionValue;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.parameters.NoIntegerValueException;
import org.junit.Assert;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JepTransitionTest.
 */
public class JepTransitionTest {

  /**
   * Test expression value.
   */
  @Test
  public void testExpressionValue() {
    final double expected = 2 + (((5 * 7) / 1.2) * 7.00002);
    final ExpressionValue value = new ExpressionValue("2 + 5 * 7 / 1.2 * 7.00002");
    try {
      final int intValue = value.intValue();
      Assert.assertEquals(new Double(expected).intValue(), intValue);
    } catch (final InvalidExpressionException e) {
      e.printStackTrace();
      Assert.fail();
    } catch (final NoIntegerValueException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }
}
