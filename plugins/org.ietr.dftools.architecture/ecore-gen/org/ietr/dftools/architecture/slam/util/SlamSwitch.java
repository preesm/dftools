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
package org.ietr.dftools.architecture.slam.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.ietr.dftools.architecture.slam.ComponentHolder;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.VLNVedElement;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke
 * the <code>caseXXX</code> method for each class of the model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.ietr.dftools.architecture.slam.SlamPackage
 * @generated
 */
public class SlamSwitch<T> extends Switch<T> {
  /**
   * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  protected static SlamPackage modelPackage;

  /**
   * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public SlamSwitch() {
    if (SlamSwitch.modelPackage == null) {
      SlamSwitch.modelPackage = SlamPackage.eINSTANCE;
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
    return ePackage == SlamSwitch.modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(final int classifierID, final EObject theEObject) {
    switch (classifierID) {
      case SlamPackage.DESIGN: {
        final Design design = (Design) theEObject;
        T result = caseDesign(design);
        if (result == null) {
          result = caseVLNVedElement(design);
        }
        if (result == null) {
          result = caseParameterizedElement(design);
        }
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case SlamPackage.COMPONENT_INSTANCE: {
        final ComponentInstance componentInstance = (ComponentInstance) theEObject;
        T result = caseComponentInstance(componentInstance);
        if (result == null) {
          result = caseParameterizedElement(componentInstance);
        }
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case SlamPackage.VLN_VED_ELEMENT: {
        final VLNVedElement vlnVedElement = (VLNVedElement) theEObject;
        T result = caseVLNVedElement(vlnVedElement);
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case SlamPackage.PARAMETERIZED_ELEMENT: {
        final ParameterizedElement parameterizedElement = (ParameterizedElement) theEObject;
        T result = caseParameterizedElement(parameterizedElement);
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case SlamPackage.COMPONENT_HOLDER: {
        final ComponentHolder componentHolder = (ComponentHolder) theEObject;
        T result = caseComponentHolder(componentHolder);
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
   * Returns the result of interpreting the object as an instance of '<em>Design</em>'. <!-- begin-user-doc --> This implementation returns null; returning a
   * non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Design</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDesign(final Design object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Component Instance</em>'. <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Component Instance</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseComponentInstance(final ComponentInstance object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>VLN Ved Element</em>'. <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
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
   * Returns the result of interpreting the object as an instance of '<em>Parameterized Element</em>'. <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
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
   * Returns the result of interpreting the object as an instance of '<em>Component Holder</em>'. <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Component Holder</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseComponentHolder(final ComponentHolder object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This implementation returns null; returning a
   * non-null result will terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
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

} // SlamSwitch
