/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.link.impl;

import net.sf.dftools.architecture.slam.link.ControlLink;
import net.sf.dftools.architecture.slam.link.LinkPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Control Link</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link net.sf.dftools.architecture.slam.link.impl.ControlLinkImpl#getSetupTime
 * <em>Setup Time</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ControlLinkImpl extends LinkImpl implements ControlLink {
	/**
	 * The default value of the '{@link #getSetupTime() <em>Setup Time</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSetupTime()
	 * @generated
	 * @ordered
	 */
	protected static final int SETUP_TIME_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getSetupTime() <em>Setup Time</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSetupTime()
	 * @generated
	 * @ordered
	 */
	protected int setupTime = SETUP_TIME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ControlLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LinkPackage.Literals.CONTROL_LINK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getSetupTime() {
		return setupTime;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSetupTime(int newSetupTime) {
		int oldSetupTime = setupTime;
		setupTime = newSetupTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkPackage.CONTROL_LINK__SETUP_TIME, oldSetupTime,
					setupTime));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LinkPackage.CONTROL_LINK__SETUP_TIME:
			return getSetupTime();
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
		case LinkPackage.CONTROL_LINK__SETUP_TIME:
			setSetupTime((Integer) newValue);
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
		case LinkPackage.CONTROL_LINK__SETUP_TIME:
			setSetupTime(SETUP_TIME_EDEFAULT);
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
		case LinkPackage.CONTROL_LINK__SETUP_TIME:
			return setupTime != SETUP_TIME_EDEFAULT;
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
		result.append(" (setupTime: ");
		result.append(setupTime);
		result.append(')');
		return result.toString();
	}

} // ControlLinkImpl
