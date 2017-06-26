/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
package org.ietr.dftools.architecture.slam.component.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.VLNVedElement;
import org.ietr.dftools.architecture.slam.component.ComInterface;
import org.ietr.dftools.architecture.slam.component.ComNode;
import org.ietr.dftools.architecture.slam.component.Component;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;
import org.ietr.dftools.architecture.slam.component.Dma;
import org.ietr.dftools.architecture.slam.component.Enabler;
import org.ietr.dftools.architecture.slam.component.HierarchyPort;
import org.ietr.dftools.architecture.slam.component.Mem;
import org.ietr.dftools.architecture.slam.component.Operator;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 *
 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage
 * @generated
 */
public class ComponentAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  protected static ComponentPackage modelPackage;

  /**
   * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public ComponentAdapterFactory() {
    if (ComponentAdapterFactory.modelPackage == null) {
      ComponentAdapterFactory.modelPackage = ComponentPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This implementation returns <code>true</code> if the object
   * is either the model's package or is an instance object of the model. <!-- end-user-doc -->
   *
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(final Object object) {
    if (object == ComponentAdapterFactory.modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject) object).eClass().getEPackage() == ComponentAdapterFactory.modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  protected ComponentSwitch<Adapter> modelSwitch = new ComponentSwitch<Adapter>() {
    @Override
    public Adapter caseComponent(final Component object) {
      return createComponentAdapter();
    }

    @Override
    public Adapter caseOperator(final Operator object) {
      return createOperatorAdapter();
    }

    @Override
    public Adapter caseComNode(final ComNode object) {
      return createComNodeAdapter();
    }

    @Override
    public Adapter caseEnabler(final Enabler object) {
      return createEnablerAdapter();
    }

    @Override
    public Adapter caseDma(final Dma object) {
      return createDmaAdapter();
    }

    @Override
    public Adapter caseMem(final Mem object) {
      return createMemAdapter();
    }

    @Override
    public Adapter caseHierarchyPort(final HierarchyPort object) {
      return createHierarchyPortAdapter();
    }

    @Override
    public Adapter caseComInterface(final ComInterface object) {
      return createComInterfaceAdapter();
    }

    @Override
    public Adapter caseVLNVedElement(final VLNVedElement object) {
      return createVLNVedElementAdapter();
    }

    @Override
    public Adapter caseParameterizedElement(final ParameterizedElement object) {
      return createParameterizedElementAdapter();
    }

    @Override
    public Adapter defaultCase(final EObject object) {
      return createEObjectAdapter();
    }
  };

  /**
   * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @param target
   *          the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(final Notifier target) {
    return this.modelSwitch.doSwitch((EObject) target);
  }

  /**
   * Creates a new adapter for an object of class ' {@link org.ietr.dftools.architecture.slam.component.Component <em>Component</em>}'. <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.component.Component
   * @generated
   */
  public Adapter createComponentAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class ' {@link org.ietr.dftools.architecture.slam.component.Operator <em>Operator</em>}'. <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.component.Operator
   * @generated
   */
  public Adapter createOperatorAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.ietr.dftools.architecture.slam.component.ComNode <em>Com Node</em>}'. <!-- begin-user-doc --> This
   * default implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
   * end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.component.ComNode
   * @generated
   */
  public Adapter createComNodeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class ' {@link org.ietr.dftools.architecture.slam.component.Enabler <em>Enabler</em>}'. <!-- begin-user-doc --> This
   * default implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
   * end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.component.Enabler
   * @generated
   */
  public Adapter createEnablerAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.ietr.dftools.architecture.slam.component.Dma <em>Dma</em>}'. <!-- begin-user-doc --> This default
   * implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
   * end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.component.Dma
   * @generated
   */
  public Adapter createDmaAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.ietr.dftools.architecture.slam.component.Mem <em>Mem</em>}'. <!-- begin-user-doc --> This default
   * implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
   * end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.component.Mem
   * @generated
   */
  public Adapter createMemAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.ietr.dftools.architecture.slam.component.HierarchyPort <em>Hierarchy Port</em>}'. <!--
   * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch
   * all the cases anyway. <!-- end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.component.HierarchyPort
   * @generated
   */
  public Adapter createHierarchyPortAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class ' {@link org.ietr.dftools.architecture.slam.component.ComInterface <em>Com Interface</em>}'. <!--
   * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch
   * all the cases anyway. <!-- end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.component.ComInterface
   * @generated
   */
  public Adapter createComInterfaceAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class ' {@link org.ietr.dftools.architecture.slam.VLNVedElement <em>VLN Ved Element</em>}'. <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.VLNVedElement
   * @generated
   */
  public Adapter createVLNVedElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.ietr.dftools.architecture.slam.ParameterizedElement <em>Parameterized Element</em>}'. <!--
   * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch
   * all the cases anyway. <!-- end-user-doc -->
   *
   * @return the new adapter.
   * @see org.ietr.dftools.architecture.slam.ParameterizedElement
   * @generated
   */
  public Adapter createParameterizedElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null. <!-- end-user-doc -->
   *
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} // ComponentAdapterFactory
