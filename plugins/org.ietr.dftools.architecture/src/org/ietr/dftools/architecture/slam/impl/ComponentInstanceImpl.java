/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2012)
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
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.component.Component;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Component Instance</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.impl.ComponentInstanceImpl#getComponent
 * <em>Component</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.impl.ComponentInstanceImpl#getInstanceName
 * <em>Instance Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentInstanceImpl extends ParameterizedElementImpl implements ComponentInstance {
	/**
	 * The cached value of the '{@link #getComponent() <em>Component</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getComponent()
	 * @generated
	 * @ordered
	 */
	protected Component component;

	/**
	 * The default value of the '{@link #getInstanceName() <em>Instance
	 * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInstanceName()
	 * @generated
	 * @ordered
	 */
	protected static final String INSTANCE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInstanceName() <em>Instance
	 * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInstanceName()
	 * @generated
	 * @ordered
	 */
	protected String instanceName = ComponentInstanceImpl.INSTANCE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ComponentInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlamPackage.Literals.COMPONENT_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Component getComponent() {
		if ((this.component != null) && this.component.eIsProxy()) {
			final InternalEObject oldComponent = (InternalEObject) this.component;
			this.component = (Component) eResolveProxy(oldComponent);
			if (this.component != oldComponent) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SlamPackage.COMPONENT_INSTANCE__COMPONENT, oldComponent, this.component));
				}
			}
		}
		return this.component;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Component basicGetComponent() {
		return this.component;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetComponent(final Component newComponent, NotificationChain msgs) {
		final Component oldComponent = this.component;
		this.component = newComponent;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SlamPackage.COMPONENT_INSTANCE__COMPONENT, oldComponent,
					newComponent);
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
	public void setComponent(final Component newComponent) {
		if (newComponent != this.component) {
			NotificationChain msgs = null;
			if (this.component != null) {
				msgs = ((InternalEObject) this.component).eInverseRemove(this, ComponentPackage.COMPONENT__INSTANCES, Component.class, msgs);
			}
			if (newComponent != null) {
				msgs = ((InternalEObject) newComponent).eInverseAdd(this, ComponentPackage.COMPONENT__INSTANCES, Component.class, msgs);
			}
			msgs = basicSetComponent(newComponent, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SlamPackage.COMPONENT_INSTANCE__COMPONENT, newComponent, newComponent));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getInstanceName() {
		return this.instanceName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInstanceName(final String newInstanceName) {
		final String oldInstanceName = this.instanceName;
		this.instanceName = newInstanceName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SlamPackage.COMPONENT_INSTANCE__INSTANCE_NAME, oldInstanceName, this.instanceName));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isHierarchical() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SlamPackage.COMPONENT_INSTANCE__COMPONENT:
				if (this.component != null) {
					msgs = ((InternalEObject) this.component).eInverseRemove(this, ComponentPackage.COMPONENT__INSTANCES, Component.class, msgs);
				}
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
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID, final NotificationChain msgs) {
		switch (featureID) {
			case SlamPackage.COMPONENT_INSTANCE__COMPONENT:
				return basicSetComponent(null, msgs);
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
			case SlamPackage.COMPONENT_INSTANCE__COMPONENT:
				if (resolve) {
					return getComponent();
				}
				return basicGetComponent();
			case SlamPackage.COMPONENT_INSTANCE__INSTANCE_NAME:
				return getInstanceName();
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
			case SlamPackage.COMPONENT_INSTANCE__COMPONENT:
				setComponent((Component) newValue);
				return;
			case SlamPackage.COMPONENT_INSTANCE__INSTANCE_NAME:
				setInstanceName((String) newValue);
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
			case SlamPackage.COMPONENT_INSTANCE__COMPONENT:
				setComponent((Component) null);
				return;
			case SlamPackage.COMPONENT_INSTANCE__INSTANCE_NAME:
				setInstanceName(ComponentInstanceImpl.INSTANCE_NAME_EDEFAULT);
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
			case SlamPackage.COMPONENT_INSTANCE__COMPONENT:
				return this.component != null;
			case SlamPackage.COMPONENT_INSTANCE__INSTANCE_NAME:
				return ComponentInstanceImpl.INSTANCE_NAME_EDEFAULT == null ? this.instanceName != null
						: !ComponentInstanceImpl.INSTANCE_NAME_EDEFAULT.equals(this.instanceName);
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
		return this.instanceName;
		/*
		 * if (eIsProxy()) return super.toString();
		 *
		 * StringBuffer result = new StringBuffer(super.toString());
		 * result.append(" (instanceName: "); result.append(instanceName);
		 * result.append(')'); return result.toString();
		 */
	}

} // ComponentInstanceImpl
