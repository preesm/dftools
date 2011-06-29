/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.component.impl;

import net.sf.dftools.architecture.slam.component.ComponentPackage;
import net.sf.dftools.architecture.slam.component.Operator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.component.impl.OperatorImpl#getOperatorType <em>Operator Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OperatorImpl extends ComponentImpl implements Operator {
	/**
	 * The default value of the '{@link #getOperatorType() <em>Operator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperatorType()
	 * @generated
	 * @ordered
	 */
	protected static final String OPERATOR_TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOperatorType() <em>Operator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperatorType()
	 * @generated
	 * @ordered
	 */
	protected String operatorType = OPERATOR_TYPE_EDEFAULT;
	/**
	 * This is true if the Operator Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean operatorTypeESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperatorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.OPERATOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOperatorType() {
		return operatorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperatorType(String newOperatorType) {
		String oldOperatorType = operatorType;
		operatorType = newOperatorType;
		boolean oldOperatorTypeESet = operatorTypeESet;
		operatorTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.OPERATOR__OPERATOR_TYPE, oldOperatorType, operatorType, !oldOperatorTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOperatorType() {
		String oldOperatorType = operatorType;
		boolean oldOperatorTypeESet = operatorTypeESet;
		operatorType = OPERATOR_TYPE_EDEFAULT;
		operatorTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.OPERATOR__OPERATOR_TYPE, oldOperatorType, OPERATOR_TYPE_EDEFAULT, oldOperatorTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOperatorType() {
		return operatorTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.OPERATOR__OPERATOR_TYPE:
				return getOperatorType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.OPERATOR__OPERATOR_TYPE:
				setOperatorType((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ComponentPackage.OPERATOR__OPERATOR_TYPE:
				unsetOperatorType();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ComponentPackage.OPERATOR__OPERATOR_TYPE:
				return isSetOperatorType();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (operatorType: ");
		if (operatorTypeESet) result.append(operatorType); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //OperatorImpl
