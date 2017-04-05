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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.ietr.dftools.architecture.slam.component.ComNode;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Com
 * Node</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.impl.ComNodeImpl#isParallel
 * <em>Parallel</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.impl.ComNodeImpl#getSpeed
 * <em>Speed</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComNodeImpl extends ComponentImpl implements ComNode {
	/**
	 * The default value of the '{@link #isParallel() <em>Parallel</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isParallel()
	 * @generated
	 * @ordered
	 */
	protected static final boolean	PARALLEL_EDEFAULT	= true;
	/**
	 * The cached value of the '{@link #isParallel() <em>Parallel</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isParallel()
	 * @generated
	 * @ordered
	 */
	protected boolean				parallel			= ComNodeImpl.PARALLEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected static final float	SPEED_EDEFAULT	= 1.0F;
	/**
	 * The cached value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected float					speed			= ComNodeImpl.SPEED_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ComNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COM_NODE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isParallel() {
		return this.parallel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setParallel(final boolean newParallel) {
		final boolean oldParallel = this.parallel;
		this.parallel = newParallel;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COM_NODE__PARALLEL, oldParallel, this.parallel));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public float getSpeed() {
		return this.speed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSpeed(final float newSpeed) {
		final float oldSpeed = this.speed;
		this.speed = newSpeed;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COM_NODE__SPEED, oldSpeed, this.speed));
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
			case ComponentPackage.COM_NODE__PARALLEL:
				return isParallel();
			case ComponentPackage.COM_NODE__SPEED:
				return getSpeed();
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
			case ComponentPackage.COM_NODE__PARALLEL:
				setParallel((Boolean) newValue);
				return;
			case ComponentPackage.COM_NODE__SPEED:
				setSpeed((Float) newValue);
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
			case ComponentPackage.COM_NODE__PARALLEL:
				setParallel(ComNodeImpl.PARALLEL_EDEFAULT);
				return;
			case ComponentPackage.COM_NODE__SPEED:
				setSpeed(ComNodeImpl.SPEED_EDEFAULT);
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
			case ComponentPackage.COM_NODE__PARALLEL:
				return this.parallel != ComNodeImpl.PARALLEL_EDEFAULT;
			case ComponentPackage.COM_NODE__SPEED:
				return this.speed != ComNodeImpl.SPEED_EDEFAULT;
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
		result.append(" (parallel: ");
		result.append(this.parallel);
		result.append(", speed: ");
		result.append(this.speed);
		result.append(')');
		return result.toString();
	}

} // ComNodeImpl
