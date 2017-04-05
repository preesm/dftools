/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ietr.dftools.architecture.slam.attributes.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.ietr.dftools.architecture.slam.attributes.AttributesPackage;
import org.ietr.dftools.architecture.slam.attributes.VLNV;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>VLNV</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.attributes.impl.VLNVImpl#getVendor
 * <em>Vendor</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.attributes.impl.VLNVImpl#getLibrary
 * <em>Library</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.attributes.impl.VLNVImpl#getName
 * <em>Name</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.attributes.impl.VLNVImpl#getVersion
 * <em>Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class VLNVImpl extends EObjectImpl implements VLNV {
	/**
	 * The default value of the '{@link #getVendor() <em>Vendor</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVendor()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDOR_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getVendor() <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVendor()
	 * @generated
	 * @ordered
	 */
	protected String vendor = VENDOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getLibrary() <em>Library</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLibrary()
	 * @generated
	 * @ordered
	 */
	protected static final String LIBRARY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLibrary() <em>Library</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLibrary()
	 * @generated
	 * @ordered
	 */
	protected String library = LIBRARY_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

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
		return vendor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVendor(String newVendor) {
		String oldVendor = vendor;
		vendor = newVendor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AttributesPackage.VLNV__VENDOR, oldVendor, vendor));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLibrary() {
		return library;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLibrary(String newLibrary) {
		String oldLibrary = library;
		library = newLibrary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AttributesPackage.VLNV__LIBRARY, oldLibrary, library));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AttributesPackage.VLNV__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AttributesPackage.VLNV__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
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
	public void eSet(int featureID, Object newValue) {
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
	public void eUnset(int featureID) {
		switch (featureID) {
		case AttributesPackage.VLNV__VENDOR:
			setVendor(VENDOR_EDEFAULT);
			return;
		case AttributesPackage.VLNV__LIBRARY:
			setLibrary(LIBRARY_EDEFAULT);
			return;
		case AttributesPackage.VLNV__NAME:
			setName(NAME_EDEFAULT);
			return;
		case AttributesPackage.VLNV__VERSION:
			setVersion(VERSION_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case AttributesPackage.VLNV__VENDOR:
			return VENDOR_EDEFAULT == null ? vendor != null : !VENDOR_EDEFAULT
					.equals(vendor);
		case AttributesPackage.VLNV__LIBRARY:
			return LIBRARY_EDEFAULT == null ? library != null
					: !LIBRARY_EDEFAULT.equals(library);
		case AttributesPackage.VLNV__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case AttributesPackage.VLNV__VERSION:
			return VERSION_EDEFAULT == null ? version != null
					: !VERSION_EDEFAULT.equals(version);
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (vendor: ");
		result.append(vendor);
		result.append(", library: ");
		result.append(library);
		result.append(", name: ");
		result.append(name);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> Comparison of two VLNVs <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VLNV) {
			VLNV vlnv = (VLNV) obj;

			Boolean equals = true;
			if (vendor == null)
				equals &= (vlnv.getVendor() == null);
			else
				equals &= (vendor.equals(vlnv.getVendor()));
			if (library == null)
				equals &= (vlnv.getLibrary() == null);
			else
				equals &= (library.equals(vlnv.getLibrary()));
			if (name == null)
				equals &= (vlnv.getName() == null);
			else
				equals &= (name.equals(vlnv.getName()));
			if (version == null)
				equals &= (vlnv.getVersion() == null);
			else
				equals &= (version.equals(vlnv.getVersion()));

			return equals;
		}
		return false;
	}

} // VLNVImpl
