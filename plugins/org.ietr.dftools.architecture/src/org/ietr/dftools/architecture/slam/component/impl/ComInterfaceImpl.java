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
package org.ietr.dftools.architecture.slam.component.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.ietr.dftools.architecture.slam.attributes.VLNV;
import org.ietr.dftools.architecture.slam.component.ComInterface;
import org.ietr.dftools.architecture.slam.component.Component;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Com Interface</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.impl.ComInterfaceImpl#getComponent
 * <em>Component</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.impl.ComInterfaceImpl#getBusType
 * <em>Bus Type</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.impl.ComInterfaceImpl#getName
 * <em>Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ComInterfaceImpl extends EObjectImpl implements ComInterface {
	/**
	 * The cached value of the '{@link #getBusType() <em>Bus Type</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBusType()
	 * @generated
	 * @ordered
	 */
	protected VLNV busType;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "";

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ComInterfaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COM_INTERFACE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Component getComponent() {
		if (eContainerFeatureID() != ComponentPackage.COM_INTERFACE__COMPONENT)
			return null;
		return (Component) eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetComponent(Component newComponent,
			NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newComponent,
				ComponentPackage.COM_INTERFACE__COMPONENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setComponent(Component newComponent) {
		if (newComponent != eInternalContainer()
				|| (eContainerFeatureID() != ComponentPackage.COM_INTERFACE__COMPONENT && newComponent != null)) {
			if (EcoreUtil.isAncestor(this, newComponent))
				throw new IllegalArgumentException(
						"Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newComponent != null)
				msgs = ((InternalEObject) newComponent).eInverseAdd(this,
						ComponentPackage.COMPONENT__INTERFACES,
						Component.class, msgs);
			msgs = basicSetComponent(newComponent, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ComponentPackage.COM_INTERFACE__COMPONENT, newComponent,
					newComponent));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VLNV getBusType() {
		return busType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBusType(VLNV newBusType,
			NotificationChain msgs) {
		VLNV oldBusType = busType;
		busType = newBusType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, ComponentPackage.COM_INTERFACE__BUS_TYPE,
					oldBusType, newBusType);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setBusType(VLNV newBusType) {
		if (newBusType != busType) {
			NotificationChain msgs = null;
			if (busType != null)
				msgs = ((InternalEObject) busType).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- ComponentPackage.COM_INTERFACE__BUS_TYPE,
						null, msgs);
			if (newBusType != null)
				msgs = ((InternalEObject) newBusType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- ComponentPackage.COM_INTERFACE__BUS_TYPE,
						null, msgs);
			msgs = basicSetBusType(newBusType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ComponentPackage.COM_INTERFACE__BUS_TYPE, newBusType,
					newBusType));
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
					ComponentPackage.COM_INTERFACE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ComponentPackage.COM_INTERFACE__COMPONENT:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetComponent((Component) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ComponentPackage.COM_INTERFACE__COMPONENT:
			return basicSetComponent(null, msgs);
		case ComponentPackage.COM_INTERFACE__BUS_TYPE:
			return basicSetBusType(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(
			NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case ComponentPackage.COM_INTERFACE__COMPONENT:
			return eInternalContainer().eInverseRemove(this,
					ComponentPackage.COMPONENT__INTERFACES, Component.class,
					msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ComponentPackage.COM_INTERFACE__COMPONENT:
			return getComponent();
		case ComponentPackage.COM_INTERFACE__BUS_TYPE:
			return getBusType();
		case ComponentPackage.COM_INTERFACE__NAME:
			return getName();
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
		case ComponentPackage.COM_INTERFACE__COMPONENT:
			setComponent((Component) newValue);
			return;
		case ComponentPackage.COM_INTERFACE__BUS_TYPE:
			setBusType((VLNV) newValue);
			return;
		case ComponentPackage.COM_INTERFACE__NAME:
			setName((String) newValue);
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
		case ComponentPackage.COM_INTERFACE__COMPONENT:
			setComponent((Component) null);
			return;
		case ComponentPackage.COM_INTERFACE__BUS_TYPE:
			setBusType((VLNV) null);
			return;
		case ComponentPackage.COM_INTERFACE__NAME:
			setName(NAME_EDEFAULT);
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
		case ComponentPackage.COM_INTERFACE__COMPONENT:
			return getComponent() != null;
		case ComponentPackage.COM_INTERFACE__BUS_TYPE:
			return busType != null;
		case ComponentPackage.COM_INTERFACE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // ComInterfaceImpl
