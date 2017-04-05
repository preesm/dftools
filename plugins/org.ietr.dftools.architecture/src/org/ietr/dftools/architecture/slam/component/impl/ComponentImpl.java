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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.attributes.Parameter;
import org.ietr.dftools.architecture.slam.component.ComInterface;
import org.ietr.dftools.architecture.slam.component.Component;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;
import org.ietr.dftools.architecture.slam.impl.VLNVedElementImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Component</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.impl.ComponentImpl#getParameters
 * <em>Parameters</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.impl.ComponentImpl#getInterfaces
 * <em>Interfaces</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.impl.ComponentImpl#getInstances
 * <em>Instances</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.impl.ComponentImpl#getRefinements
 * <em>Refinements</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentImpl extends VLNVedElementImpl implements Component {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * The cached value of the '{@link #getInterfaces() <em>Interfaces</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInterfaces()
	 * @generated
	 * @ordered
	 */
	protected EList<ComInterface> interfaces;

	/**
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<ComponentInstance> instances;

	/**
	 * The cached value of the '{@link #getRefinements() <em>Refinements</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getRefinements()
	 * @generated
	 * @ordered
	 */
	protected EList<Design> refinements;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COMPONENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Parameter> getParameters() {
		if (this.parameters == null) {
			this.parameters = new EObjectContainmentEList<>(Parameter.class, this, ComponentPackage.COMPONENT__PARAMETERS);
		}
		return this.parameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<ComInterface> getInterfaces() {
		if (this.interfaces == null) {
			this.interfaces = new EObjectContainmentWithInverseEList<>(ComInterface.class, this, ComponentPackage.COMPONENT__INTERFACES,
					ComponentPackage.COM_INTERFACE__COMPONENT);
		}
		return this.interfaces;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<ComponentInstance> getInstances() {
		if (this.instances == null) {
			this.instances = new EObjectWithInverseResolvingEList<>(ComponentInstance.class, this, ComponentPackage.COMPONENT__INSTANCES,
					SlamPackage.COMPONENT_INSTANCE__COMPONENT);
		}
		return this.instances;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Design> getRefinements() {
		if (this.refinements == null) {
			this.refinements = new EObjectContainmentWithInverseEList<>(Design.class, this, ComponentPackage.COMPONENT__REFINEMENTS,
					SlamPackage.DESIGN__REFINED);
		}
		return this.refinements;
	}

	/**
	 * <!-- begin-user-doc --> Getting the com interface with the given ID <!--
	 * end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public ComInterface getInterface(final String name) {
		if (this.interfaces != null) {
			for (final ComInterface intf : this.interfaces) {
				if (intf.getName().equals(name)) {
					return intf;
				}
			}
		}

		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, final NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.COMPONENT__INTERFACES:
				return ((InternalEList<InternalEObject>) (InternalEList<?>) getInterfaces()).basicAdd(otherEnd, msgs);
			case ComponentPackage.COMPONENT__INSTANCES:
				return ((InternalEList<InternalEObject>) (InternalEList<?>) getInstances()).basicAdd(otherEnd, msgs);
			case ComponentPackage.COMPONENT__REFINEMENTS:
				return ((InternalEList<InternalEObject>) (InternalEList<?>) getRefinements()).basicAdd(otherEnd, msgs);
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
			case ComponentPackage.COMPONENT__PARAMETERS:
				return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPONENT__INTERFACES:
				return ((InternalEList<?>) getInterfaces()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPONENT__INSTANCES:
				return ((InternalEList<?>) getInstances()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPONENT__REFINEMENTS:
				return ((InternalEList<?>) getRefinements()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.COMPONENT__PARAMETERS:
				return getParameters();
			case ComponentPackage.COMPONENT__INTERFACES:
				return getInterfaces();
			case ComponentPackage.COMPONENT__INSTANCES:
				return getInstances();
			case ComponentPackage.COMPONENT__REFINEMENTS:
				return getRefinements();
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
			case ComponentPackage.COMPONENT__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>) newValue);
				return;
			case ComponentPackage.COMPONENT__INTERFACES:
				getInterfaces().clear();
				getInterfaces().addAll((Collection<? extends ComInterface>) newValue);
				return;
			case ComponentPackage.COMPONENT__INSTANCES:
				getInstances().clear();
				getInstances().addAll((Collection<? extends ComponentInstance>) newValue);
				return;
			case ComponentPackage.COMPONENT__REFINEMENTS:
				getRefinements().clear();
				getRefinements().addAll((Collection<? extends Design>) newValue);
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
			case ComponentPackage.COMPONENT__PARAMETERS:
				getParameters().clear();
				return;
			case ComponentPackage.COMPONENT__INTERFACES:
				getInterfaces().clear();
				return;
			case ComponentPackage.COMPONENT__INSTANCES:
				getInstances().clear();
				return;
			case ComponentPackage.COMPONENT__REFINEMENTS:
				getRefinements().clear();
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
			case ComponentPackage.COMPONENT__PARAMETERS:
				return (this.parameters != null) && !this.parameters.isEmpty();
			case ComponentPackage.COMPONENT__INTERFACES:
				return (this.interfaces != null) && !this.interfaces.isEmpty();
			case ComponentPackage.COMPONENT__INSTANCES:
				return (this.instances != null) && !this.instances.isEmpty();
			case ComponentPackage.COMPONENT__REFINEMENTS:
				return (this.refinements != null) && !this.refinements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(final int derivedFeatureID, final Class<?> baseClass) {
		if (baseClass == ParameterizedElement.class) {
			switch (derivedFeatureID) {
				case ComponentPackage.COMPONENT__PARAMETERS:
					return SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(final int baseFeatureID, final Class<?> baseClass) {
		if (baseClass == ParameterizedElement.class) {
			switch (baseFeatureID) {
				case SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS:
					return ComponentPackage.COMPONENT__PARAMETERS;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // ComponentImpl
