/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.component.impl;

import java.util.Collection;

import net.sf.dftools.architecture.slam.ComponentInstance;
import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.ParameterizedElement;
import net.sf.dftools.architecture.slam.SlamPackage;
import net.sf.dftools.architecture.slam.attributes.Parameter;
import net.sf.dftools.architecture.slam.component.ComInterface;
import net.sf.dftools.architecture.slam.component.Component;
import net.sf.dftools.architecture.slam.component.ComponentPackage;
import net.sf.dftools.architecture.slam.impl.VLNVedElementImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Component</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link net.sf.dftools.architecture.slam.component.impl.ComponentImpl#getParameters
 * <em>Parameters</em>}</li>
 * <li>
 * {@link net.sf.dftools.architecture.slam.component.impl.ComponentImpl#getInterfaces
 * <em>Interfaces</em>}</li>
 * <li>
 * {@link net.sf.dftools.architecture.slam.component.impl.ComponentImpl#getInstances
 * <em>Instances</em>}</li>
 * <li>
 * {@link net.sf.dftools.architecture.slam.component.impl.ComponentImpl#getRefinement
 * <em>Refinement</em>}</li>
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
	 * The cached value of the '{@link #getRefinement() <em>Refinement</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRefinement()
	 * @generated
	 * @ordered
	 */
	protected Design refinement;

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
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(
					Parameter.class, this,
					ComponentPackage.COMPONENT__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ComInterface> getInterfaces() {
		if (interfaces == null) {
			interfaces = new EObjectContainmentWithInverseEList<ComInterface>(
					ComInterface.class, this,
					ComponentPackage.COMPONENT__INTERFACES,
					ComponentPackage.COM_INTERFACE__COMPONENT);
		}
		return interfaces;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ComponentInstance> getInstances() {
		if (instances == null) {
			instances = new EObjectWithInverseResolvingEList<ComponentInstance>(
					ComponentInstance.class, this,
					ComponentPackage.COMPONENT__INSTANCES,
					SlamPackage.COMPONENT_INSTANCE__COMPONENT);
		}
		return instances;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Design getRefinement() {
		return refinement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetRefinement(Design newRefinement,
			NotificationChain msgs) {
		Design oldRefinement = refinement;
		refinement = newRefinement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, ComponentPackage.COMPONENT__REFINEMENT,
					oldRefinement, newRefinement);
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
	public void setRefinement(Design newRefinement) {
		if (newRefinement != refinement) {
			NotificationChain msgs = null;
			if (refinement != null)
				msgs = ((InternalEObject) refinement).eInverseRemove(this,
						SlamPackage.DESIGN__REFINED, Design.class, msgs);
			if (newRefinement != null)
				msgs = ((InternalEObject) newRefinement).eInverseAdd(this,
						SlamPackage.DESIGN__REFINED, Design.class, msgs);
			msgs = basicSetRefinement(newRefinement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ComponentPackage.COMPONENT__REFINEMENT, newRefinement,
					newRefinement));
	}

	/**
	 * <!-- begin-user-doc --> Getting the com interface with the given ID <!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ComInterface getInterface(String name) {
		if (interfaces != null) {
			for (ComInterface intf : interfaces) {
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
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ComponentPackage.COMPONENT__INTERFACES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getInterfaces())
					.basicAdd(otherEnd, msgs);
		case ComponentPackage.COMPONENT__INSTANCES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getInstances())
					.basicAdd(otherEnd, msgs);
		case ComponentPackage.COMPONENT__REFINEMENT:
			if (refinement != null)
				msgs = ((InternalEObject) refinement).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- ComponentPackage.COMPONENT__REFINEMENT, null,
						msgs);
			return basicSetRefinement((Design) otherEnd, msgs);
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
		case ComponentPackage.COMPONENT__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd,
					msgs);
		case ComponentPackage.COMPONENT__INTERFACES:
			return ((InternalEList<?>) getInterfaces()).basicRemove(otherEnd,
					msgs);
		case ComponentPackage.COMPONENT__INSTANCES:
			return ((InternalEList<?>) getInstances()).basicRemove(otherEnd,
					msgs);
		case ComponentPackage.COMPONENT__REFINEMENT:
			return basicSetRefinement(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ComponentPackage.COMPONENT__PARAMETERS:
			return getParameters();
		case ComponentPackage.COMPONENT__INTERFACES:
			return getInterfaces();
		case ComponentPackage.COMPONENT__INSTANCES:
			return getInstances();
		case ComponentPackage.COMPONENT__REFINEMENT:
			return getRefinement();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ComponentPackage.COMPONENT__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends Parameter>) newValue);
			return;
		case ComponentPackage.COMPONENT__INTERFACES:
			getInterfaces().clear();
			getInterfaces().addAll(
					(Collection<? extends ComInterface>) newValue);
			return;
		case ComponentPackage.COMPONENT__INSTANCES:
			getInstances().clear();
			getInstances().addAll(
					(Collection<? extends ComponentInstance>) newValue);
			return;
		case ComponentPackage.COMPONENT__REFINEMENT:
			setRefinement((Design) newValue);
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
		case ComponentPackage.COMPONENT__PARAMETERS:
			getParameters().clear();
			return;
		case ComponentPackage.COMPONENT__INTERFACES:
			getInterfaces().clear();
			return;
		case ComponentPackage.COMPONENT__INSTANCES:
			getInstances().clear();
			return;
		case ComponentPackage.COMPONENT__REFINEMENT:
			setRefinement((Design) null);
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
		case ComponentPackage.COMPONENT__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case ComponentPackage.COMPONENT__INTERFACES:
			return interfaces != null && !interfaces.isEmpty();
		case ComponentPackage.COMPONENT__INSTANCES:
			return instances != null && !instances.isEmpty();
		case ComponentPackage.COMPONENT__REFINEMENT:
			return refinement != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
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
