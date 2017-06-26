/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
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
package org.ietr.dftools.architecture.slam.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.attributes.Parameter;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Parameterized Element</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.ietr.dftools.architecture.slam.impl.ParameterizedElementImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterizedElementImpl extends EObjectImpl implements ParameterizedElement {
  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected EList<Parameter> parameters;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  protected ParameterizedElementImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return SlamPackage.Literals.PARAMETERIZED_ELEMENT;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EList<Parameter> getParameters() {
    if (this.parameters == null) {
      this.parameters = new EObjectContainmentEList<>(Parameter.class, this, SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS);
    }
    return this.parameters;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID, final NotificationChain msgs) {
    switch (featureID) {
      case SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS:
        return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
    switch (featureID) {
      case SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS:
        return getParameters();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(final int featureID, final Object newValue) {
    switch (featureID) {
      case SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends Parameter>) newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void eUnset(final int featureID) {
    switch (featureID) {
      case SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS:
        getParameters().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public boolean eIsSet(final int featureID) {
    switch (featureID) {
      case SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS:
        return (this.parameters != null) && !this.parameters.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} // ParameterizedElementImpl
