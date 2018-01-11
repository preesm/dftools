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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
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
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage
 * @generated
 */
public class ComponentSwitch<T> extends Switch<T> {
  /**
   * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  protected static ComponentPackage modelPackage;

  /**
   * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public ComponentSwitch() {
    if (ComponentSwitch.modelPackage == null) {
      ComponentSwitch.modelPackage = ComponentPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(final EPackage ePackage) {
    return ePackage == ComponentSwitch.modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(final int classifierID, final EObject theEObject) {
    switch (classifierID) {
      case ComponentPackage.COMPONENT: {
        final Component component = (Component) theEObject;
        T result = caseComponent(component);
        if (result == null) {
          result = caseVLNVedElement(component);
        }
        if (result == null) {
          result = caseParameterizedElement(component);
        }
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case ComponentPackage.OPERATOR: {
        final Operator operator = (Operator) theEObject;
        T result = caseOperator(operator);
        if (result == null) {
          result = caseComponent(operator);
        }
        if (result == null) {
          result = caseVLNVedElement(operator);
        }
        if (result == null) {
          result = caseParameterizedElement(operator);
        }
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case ComponentPackage.COM_NODE: {
        final ComNode comNode = (ComNode) theEObject;
        T result = caseComNode(comNode);
        if (result == null) {
          result = caseComponent(comNode);
        }
        if (result == null) {
          result = caseVLNVedElement(comNode);
        }
        if (result == null) {
          result = caseParameterizedElement(comNode);
        }
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case ComponentPackage.ENABLER: {
        final Enabler enabler = (Enabler) theEObject;
        T result = caseEnabler(enabler);
        if (result == null) {
          result = caseComponent(enabler);
        }
        if (result == null) {
          result = caseVLNVedElement(enabler);
        }
        if (result == null) {
          result = caseParameterizedElement(enabler);
        }
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case ComponentPackage.DMA: {
        final Dma dma = (Dma) theEObject;
        T result = caseDma(dma);
        if (result == null) {
          result = caseEnabler(dma);
        }
        if (result == null) {
          result = caseComponent(dma);
        }
        if (result == null) {
          result = caseVLNVedElement(dma);
        }
        if (result == null) {
          result = caseParameterizedElement(dma);
        }
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case ComponentPackage.MEM: {
        final Mem mem = (Mem) theEObject;
        T result = caseMem(mem);
        if (result == null) {
          result = caseEnabler(mem);
        }
        if (result == null) {
          result = caseComponent(mem);
        }
        if (result == null) {
          result = caseVLNVedElement(mem);
        }
        if (result == null) {
          result = caseParameterizedElement(mem);
        }
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case ComponentPackage.HIERARCHY_PORT: {
        final HierarchyPort hierarchyPort = (HierarchyPort) theEObject;
        T result = caseHierarchyPort(hierarchyPort);
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case ComponentPackage.COM_INTERFACE: {
        final ComInterface comInterface = (ComInterface) theEObject;
        T result = caseComInterface(comInterface);
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      default:
        return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Component</em>'. <!-- begin-user-doc --> This
   * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Component</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseComponent(final Component object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Operator</em>'. <!-- begin-user-doc --> This
   * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Operator</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOperator(final Operator object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of ' <em>Com Node</em>'. <!-- begin-user-doc --> This
   * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of ' <em>Com Node</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseComNode(final ComNode object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Enabler</em>'. <!-- begin-user-doc --> This
   * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Enabler</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnabler(final Enabler object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of ' <em>Dma</em>'. <!-- begin-user-doc --> This
   * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of ' <em>Dma</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDma(final Dma object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of ' <em>Mem</em>'. <!-- begin-user-doc --> This
   * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of ' <em>Mem</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMem(final Mem object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Hierarchy Port</em>'. <!-- begin-user-doc -->
   * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Hierarchy Port</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseHierarchyPort(final HierarchyPort object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Com Interface</em>'. <!-- begin-user-doc -->
   * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Com Interface</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseComInterface(final ComInterface object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>VLN Ved Element</em>'. <!-- begin-user-doc -->
   * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>VLN Ved Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVLNVedElement(final VLNVedElement object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parameterized Element</em>'. <!--
   * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
   * end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parameterized Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParameterizedElement(final ParameterizedElement object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
   * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
   * anyway. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(final EObject object) {
    return null;
  }

} // ComponentSwitch
