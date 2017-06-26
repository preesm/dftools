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
package org.ietr.dftools.architecture.slam.link.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.ietr.dftools.architecture.slam.link.ControlLink;
import org.ietr.dftools.architecture.slam.link.DataLink;
import org.ietr.dftools.architecture.slam.link.Link;
import org.ietr.dftools.architecture.slam.link.LinkPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke
 * the <code>caseXXX</code> method for each class of the model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.ietr.dftools.architecture.slam.link.LinkPackage
 * @generated
 */
public class LinkSwitch<T> extends Switch<T> {
  /**
   * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  protected static LinkPackage modelPackage;

  /**
   * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public LinkSwitch() {
    if (LinkSwitch.modelPackage == null) {
      LinkSwitch.modelPackage = LinkPackage.eINSTANCE;
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
    return ePackage == LinkSwitch.modelPackage;
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
      case LinkPackage.LINK: {
        final Link link = (Link) theEObject;
        T result = caseLink(link);
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case LinkPackage.DATA_LINK: {
        final DataLink dataLink = (DataLink) theEObject;
        T result = caseDataLink(dataLink);
        if (result == null) {
          result = caseLink(dataLink);
        }
        if (result == null) {
          result = defaultCase(theEObject);
        }
        return result;
      }
      case LinkPackage.CONTROL_LINK: {
        final ControlLink controlLink = (ControlLink) theEObject;
        T result = caseControlLink(controlLink);
        if (result == null) {
          result = caseLink(controlLink);
        }
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
   * Returns the result of interpreting the object as an instance of ' <em>Link</em>'. <!-- begin-user-doc --> This implementation returns null; returning a
   * non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of ' <em>Link</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLink(final Link object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Data Link</em>'. <!-- begin-user-doc --> This implementation returns null; returning a
   * non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Data Link</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDataLink(final DataLink object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Control Link</em>'. <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   *
   * @param object
   *          the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Control Link</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseControlLink(final ControlLink object) {
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

} // LinkSwitch
