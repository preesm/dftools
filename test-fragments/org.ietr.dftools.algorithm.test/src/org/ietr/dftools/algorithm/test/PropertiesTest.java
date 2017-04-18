package org.ietr.dftools.algorithm.test;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.junit.Assert;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertiesTest.
 */
public class PropertiesTest {

  /** The Constant MY_PROPERTY_CONSTANT. */
  private static final String MY_PROPERTY_CONSTANT = "toregregzegergregreto";

  /**
   * Test set prop.
   */
  @Test
  public void testSetProp() {
    final SDFAbstractVertex toto = new SDFAbstractVertex() {

      @Override
      public void connectionRemoved(final AbstractEdge<?, ?> e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void connectionAdded(final AbstractEdge<?, ?> e) {
        // TODO Auto-generated method stub

      }

      @Override
      public SDFAbstractVertex clone() {
        // TODO Auto-generated method stub
        return null;
      }
    };

    final int input = 15;
    toto.getPropertyBean().setValue(PropertiesTest.MY_PROPERTY_CONSTANT, input);

    final Integer value = (Integer) toto.getPropertyBean().getValue(PropertiesTest.MY_PROPERTY_CONSTANT, Integer.class);
    Assert.assertEquals(input, value.intValue());

  }

}
