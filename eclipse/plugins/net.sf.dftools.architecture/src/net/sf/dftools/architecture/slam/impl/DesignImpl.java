/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.impl;

import java.util.Collection;

import net.sf.dftools.architecture.slam.ComponentInstance;
import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.SlamPackage;
import net.sf.dftools.architecture.slam.component.Component;
import net.sf.dftools.architecture.slam.component.ComponentPackage;
import net.sf.dftools.architecture.slam.component.HierarchyPort;
import net.sf.dftools.architecture.slam.link.Link;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Design</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.impl.DesignImpl#getComponentInstances <em>Component Instances</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.impl.DesignImpl#getLinks <em>Links</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.impl.DesignImpl#getHierarchyPorts <em>Hierarchy Ports</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.impl.DesignImpl#getComponents <em>Components</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.impl.DesignImpl#getRefined <em>Refined</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DesignImpl extends VLNVedElementImpl implements Design {
	/**
	 * The cached value of the '{@link #getComponentInstances() <em>Component Instances</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<ComponentInstance> componentInstances;

	/**
	 * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<Link> links;

	/**
	 * The cached value of the '{@link #getHierarchyPorts() <em>Hierarchy Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHierarchyPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<HierarchyPort> hierarchyPorts;

	/**
	 * The cached value of the '{@link #getComponents() <em>Components</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<Component> components;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DesignImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlamPackage.Literals.DESIGN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ComponentInstance> getComponentInstances() {
		if (componentInstances == null) {
			componentInstances = new EObjectContainmentEList<ComponentInstance>(ComponentInstance.class, this, SlamPackage.DESIGN__COMPONENT_INSTANCES);
		}
		return componentInstances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Link> getLinks() {
		if (links == null) {
			links = new EObjectContainmentEList<Link>(Link.class, this, SlamPackage.DESIGN__LINKS);
		}
		return links;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<HierarchyPort> getHierarchyPorts() {
		if (hierarchyPorts == null) {
			hierarchyPorts = new EObjectContainmentEList<HierarchyPort>(HierarchyPort.class, this, SlamPackage.DESIGN__HIERARCHY_PORTS);
		}
		return hierarchyPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Component> getComponents() {
		if (components == null) {
			components = new EObjectContainmentEList<Component>(Component.class, this, SlamPackage.DESIGN__COMPONENTS);
		}
		return components;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Component getRefined() {
		if (eContainerFeatureID() != SlamPackage.DESIGN__REFINED) return null;
		return (Component)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRefined(Component newRefined, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRefined, SlamPackage.DESIGN__REFINED, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRefined(Component newRefined) {
		if (newRefined != eInternalContainer() || (eContainerFeatureID() != SlamPackage.DESIGN__REFINED && newRefined != null)) {
			if (EcoreUtil.isAncestor(this, newRefined))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRefined != null)
				msgs = ((InternalEObject)newRefined).eInverseAdd(this, ComponentPackage.COMPONENT__REFINEMENT, Component.class, msgs);
			msgs = basicSetRefined(newRefined, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SlamPackage.DESIGN__REFINED, newRefined, newRefined));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean containsComponentInstance(String name) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean containsComponent(String name) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Component getComponent(String name) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance getComponentInstance(String name) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SlamPackage.DESIGN__REFINED:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRefined((Component)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SlamPackage.DESIGN__COMPONENT_INSTANCES:
				return ((InternalEList<?>)getComponentInstances()).basicRemove(otherEnd, msgs);
			case SlamPackage.DESIGN__LINKS:
				return ((InternalEList<?>)getLinks()).basicRemove(otherEnd, msgs);
			case SlamPackage.DESIGN__HIERARCHY_PORTS:
				return ((InternalEList<?>)getHierarchyPorts()).basicRemove(otherEnd, msgs);
			case SlamPackage.DESIGN__COMPONENTS:
				return ((InternalEList<?>)getComponents()).basicRemove(otherEnd, msgs);
			case SlamPackage.DESIGN__REFINED:
				return basicSetRefined(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case SlamPackage.DESIGN__REFINED:
				return eInternalContainer().eInverseRemove(this, ComponentPackage.COMPONENT__REFINEMENT, Component.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SlamPackage.DESIGN__COMPONENT_INSTANCES:
				return getComponentInstances();
			case SlamPackage.DESIGN__LINKS:
				return getLinks();
			case SlamPackage.DESIGN__HIERARCHY_PORTS:
				return getHierarchyPorts();
			case SlamPackage.DESIGN__COMPONENTS:
				return getComponents();
			case SlamPackage.DESIGN__REFINED:
				return getRefined();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SlamPackage.DESIGN__COMPONENT_INSTANCES:
				getComponentInstances().clear();
				getComponentInstances().addAll((Collection<? extends ComponentInstance>)newValue);
				return;
			case SlamPackage.DESIGN__LINKS:
				getLinks().clear();
				getLinks().addAll((Collection<? extends Link>)newValue);
				return;
			case SlamPackage.DESIGN__HIERARCHY_PORTS:
				getHierarchyPorts().clear();
				getHierarchyPorts().addAll((Collection<? extends HierarchyPort>)newValue);
				return;
			case SlamPackage.DESIGN__COMPONENTS:
				getComponents().clear();
				getComponents().addAll((Collection<? extends Component>)newValue);
				return;
			case SlamPackage.DESIGN__REFINED:
				setRefined((Component)newValue);
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
			case SlamPackage.DESIGN__COMPONENT_INSTANCES:
				getComponentInstances().clear();
				return;
			case SlamPackage.DESIGN__LINKS:
				getLinks().clear();
				return;
			case SlamPackage.DESIGN__HIERARCHY_PORTS:
				getHierarchyPorts().clear();
				return;
			case SlamPackage.DESIGN__COMPONENTS:
				getComponents().clear();
				return;
			case SlamPackage.DESIGN__REFINED:
				setRefined((Component)null);
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
			case SlamPackage.DESIGN__COMPONENT_INSTANCES:
				return componentInstances != null && !componentInstances.isEmpty();
			case SlamPackage.DESIGN__LINKS:
				return links != null && !links.isEmpty();
			case SlamPackage.DESIGN__HIERARCHY_PORTS:
				return hierarchyPorts != null && !hierarchyPorts.isEmpty();
			case SlamPackage.DESIGN__COMPONENTS:
				return components != null && !components.isEmpty();
			case SlamPackage.DESIGN__REFINED:
				return getRefined() != null;
		}
		return super.eIsSet(featureID);
	}

} //DesignImpl
