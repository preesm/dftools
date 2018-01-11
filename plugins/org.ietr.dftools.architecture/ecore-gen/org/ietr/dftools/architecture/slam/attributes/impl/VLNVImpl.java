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
package org.ietr.dftools.architecture.slam.attributes.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.ietr.dftools.architecture.slam.attributes.AttributesPackage;
import org.ietr.dftools.architecture.slam.attributes.VLNV;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>VLNV</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.ietr.dftools.architecture.slam.attributes.impl.VLNVImpl#getVendor <em>Vendor</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.attributes.impl.VLNVImpl#getLibrary <em>Library</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.attributes.impl.VLNVImpl#getName <em>Name</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.attributes.impl.VLNVImpl#getVersion <em>Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VLNVImpl extends EObjectImpl implements VLNV {
  /**
   * The default value of the '{@link #getVendor() <em>Vendor</em>}' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @see #getVendor()
   * @generated
   * @ordered
   */
  protected static final String VENDOR_EDEFAULT = "";

  /**
   * The cached value of the '{@link #getVendor() <em>Vendor</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   *
   * @see #getVendor()
   * @generated
   * @ordered
   */
  protected String vendor = VLNVImpl.VENDOR_EDEFAULT;

  /**
   * The default value of the '{@link #getLibrary() <em>Library</em>}' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @see #getLibrary()
   * @generated
   * @ordered
   */
  protected static final String LIBRARY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLibrary() <em>Library</em>}' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @see #getLibrary()
   * @generated
   * @ordered
   */
  protected String library = VLNVImpl.LIBRARY_EDEFAULT;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   *
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = VLNVImpl.NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getVersion() <em>Version</em>}' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @see #getVersion()
   * @generated
   * @ordered
   */
  protected static final String VERSION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @see #getVersion()
   * @generated
   * @ordered
   */
  protected String version = VLNVImpl.VERSION_EDEFAULT;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  protected VLNVImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return AttributesPackage.Literals.VLNV;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public String getVendor() {
    return this.vendor;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setVendor(final String newVendor) {
    final String oldVendor = this.vendor;
    this.vendor = newVendor;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, AttributesPackage.VLNV__VENDOR, oldVendor, this.vendor));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public String getLibrary() {
    return this.library;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setLibrary(final String newLibrary) {
    final String oldLibrary = this.library;
    this.library = newLibrary;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, AttributesPackage.VLNV__LIBRARY, oldLibrary, this.library));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setName(final String newName) {
    final String oldName = this.name;
    this.name = newName;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, AttributesPackage.VLNV__NAME, oldName, this.name));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public String getVersion() {
    return this.version;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setVersion(final String newVersion) {
    final String oldVersion = this.version;
    this.version = newVersion;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, AttributesPackage.VLNV__VERSION, oldVersion, this.version));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
    switch (featureID) {
      case AttributesPackage.VLNV__VENDOR:
        return getVendor();
      case AttributesPackage.VLNV__LIBRARY:
        return getLibrary();
      case AttributesPackage.VLNV__NAME:
        return getName();
      case AttributesPackage.VLNV__VERSION:
        return getVersion();
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
      case AttributesPackage.VLNV__VENDOR:
        setVendor((String) newValue);
        return;
      case AttributesPackage.VLNV__LIBRARY:
        setLibrary((String) newValue);
        return;
      case AttributesPackage.VLNV__NAME:
        setName((String) newValue);
        return;
      case AttributesPackage.VLNV__VERSION:
        setVersion((String) newValue);
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
      case AttributesPackage.VLNV__VENDOR:
        setVendor(VLNVImpl.VENDOR_EDEFAULT);
        return;
      case AttributesPackage.VLNV__LIBRARY:
        setLibrary(VLNVImpl.LIBRARY_EDEFAULT);
        return;
      case AttributesPackage.VLNV__NAME:
        setName(VLNVImpl.NAME_EDEFAULT);
        return;
      case AttributesPackage.VLNV__VERSION:
        setVersion(VLNVImpl.VERSION_EDEFAULT);
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
      case AttributesPackage.VLNV__VENDOR:
        return VLNVImpl.VENDOR_EDEFAULT == null ? this.vendor != null : !VLNVImpl.VENDOR_EDEFAULT.equals(this.vendor);
      case AttributesPackage.VLNV__LIBRARY:
        return VLNVImpl.LIBRARY_EDEFAULT == null ? this.library != null
            : !VLNVImpl.LIBRARY_EDEFAULT.equals(this.library);
      case AttributesPackage.VLNV__NAME:
        return VLNVImpl.NAME_EDEFAULT == null ? this.name != null : !VLNVImpl.NAME_EDEFAULT.equals(this.name);
      case AttributesPackage.VLNV__VERSION:
        return VLNVImpl.VERSION_EDEFAULT == null ? this.version != null
            : !VLNVImpl.VERSION_EDEFAULT.equals(this.version);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) {
      return super.toString();
    }

    final StringBuffer result = new StringBuffer(super.toString());
    result.append(" (vendor: ");
    result.append(this.vendor);
    result.append(", library: ");
    result.append(this.library);
    result.append(", name: ");
    result.append(this.name);
    result.append(", version: ");
    result.append(this.version);
    result.append(')');
    return result.toString();
  }

  /**
   * <!-- begin-user-doc --> Comparison of two VLNVs <!-- end-user-doc -->
   *
   * @generated NOT
   */
  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof VLNV) {
      final VLNV vlnv = (VLNV) obj;

      Boolean equals = true;
      if (this.vendor == null) {
        equals &= (vlnv.getVendor() == null);
      } else {
        equals &= (this.vendor.equals(vlnv.getVendor()));
      }
      if (this.library == null) {
        equals &= (vlnv.getLibrary() == null);
      } else {
        equals &= (this.library.equals(vlnv.getLibrary()));
      }
      if (this.name == null) {
        equals &= (vlnv.getName() == null);
      } else {
        equals &= (this.name.equals(vlnv.getName()));
      }
      if (this.version == null) {
        equals &= (vlnv.getVersion() == null);
      } else {
        equals &= (this.version.equals(vlnv.getVersion()));
      }

      return equals;
    }
    return false;
  }

} // VLNVImpl
