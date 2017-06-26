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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.VLNVedElement;
import org.ietr.dftools.architecture.slam.attributes.VLNV;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>VLN Ved Element</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.ietr.dftools.architecture.slam.impl.VLNVedElementImpl#getVlnv <em>Vlnv</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VLNVedElementImpl extends EObjectImpl implements VLNVedElement {
  /**
   * The cached value of the '{@link #getVlnv() <em>Vlnv</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getVlnv()
   * @generated
   * @ordered
   */
  protected VLNV vlnv;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  protected VLNVedElementImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return SlamPackage.Literals.VLN_VED_ELEMENT;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public VLNV getVlnv() {
    return this.vlnv;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public NotificationChain basicSetVlnv(final VLNV newVlnv, NotificationChain msgs) {
    final VLNV oldVlnv = this.vlnv;
    this.vlnv = newVlnv;
    if (eNotificationRequired()) {
      final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SlamPackage.VLN_VED_ELEMENT__VLNV, oldVlnv, newVlnv);
      if (msgs == null) {
        msgs = notification;
      } else {
        msgs.add(notification);
      }
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setVlnv(final VLNV newVlnv) {
    if (newVlnv != this.vlnv) {
      NotificationChain msgs = null;
      if (this.vlnv != null) {
        msgs = ((InternalEObject) this.vlnv).eInverseRemove(this, InternalEObject.EOPPOSITE_FEATURE_BASE - SlamPackage.VLN_VED_ELEMENT__VLNV, null, msgs);
      }
      if (newVlnv != null) {
        msgs = ((InternalEObject) newVlnv).eInverseAdd(this, InternalEObject.EOPPOSITE_FEATURE_BASE - SlamPackage.VLN_VED_ELEMENT__VLNV, null, msgs);
      }
      msgs = basicSetVlnv(newVlnv, msgs);
      if (msgs != null) {
        msgs.dispatch();
      }
    } else if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, SlamPackage.VLN_VED_ELEMENT__VLNV, newVlnv, newVlnv));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID, final NotificationChain msgs) {
    switch (featureID) {
      case SlamPackage.VLN_VED_ELEMENT__VLNV:
        return basicSetVlnv(null, msgs);
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
      case SlamPackage.VLN_VED_ELEMENT__VLNV:
        return getVlnv();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void eSet(final int featureID, final Object newValue) {
    switch (featureID) {
      case SlamPackage.VLN_VED_ELEMENT__VLNV:
        setVlnv((VLNV) newValue);
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
      case SlamPackage.VLN_VED_ELEMENT__VLNV:
        setVlnv((VLNV) null);
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
      case SlamPackage.VLN_VED_ELEMENT__VLNV:
        return this.vlnv != null;
    }
    return super.eIsSet(featureID);
  }

} // VLNVedElementImpl
