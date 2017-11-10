package org.ietr.dftools.architecture.utils;

import org.eclipse.emf.ecore.EClass;
import org.ietr.dftools.architecture.slam.attributes.VLNV;
import org.ietr.dftools.architecture.slam.component.Component;
import org.ietr.dftools.architecture.slam.component.ComponentFactory;

/**
 *
 * @author anmorvan
 *
 */
public class SlamUserFactory {

  private SlamUserFactory() {
    // Not meant to be instantiated: use static methods.
  }

  private static final ComponentFactory factory = ComponentFactory.eINSTANCE;

  /**
   *
   */
  public static final Component getComponent(final VLNV name, final String componentType) {
    final EClass eClass = (EClass) factory.getEPackage().getEClassifier(componentType);
    final Component component = (Component) factory.create(eClass);
    component.setVlnv(name);
    return component;
  }

}
