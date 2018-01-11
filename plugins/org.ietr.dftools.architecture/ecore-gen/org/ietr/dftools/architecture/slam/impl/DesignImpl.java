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
package org.ietr.dftools.architecture.slam.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.ietr.dftools.architecture.slam.ComponentHolder;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.attributes.Parameter;
import org.ietr.dftools.architecture.slam.attributes.VLNV;
import org.ietr.dftools.architecture.slam.component.Component;
import org.ietr.dftools.architecture.slam.component.ComponentFactory;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;
import org.ietr.dftools.architecture.slam.component.HierarchyPort;
import org.ietr.dftools.architecture.slam.link.Link;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Design</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.ietr.dftools.architecture.slam.impl.DesignImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.impl.DesignImpl#getComponentInstances <em>Component
 * Instances</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.impl.DesignImpl#getLinks <em>Links</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.impl.DesignImpl#getHierarchyPorts <em>Hierarchy Ports</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.impl.DesignImpl#getRefined <em>Refined</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.impl.DesignImpl#getPath <em>Path</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.impl.DesignImpl#getComponentHolder <em>Component Holder</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DesignImpl extends VLNVedElementImpl implements Design {
  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected EList<Parameter> parameters;

  /**
   * The cached value of the '{@link #getComponentInstances() <em>Component Instances</em>}' containment reference list.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getComponentInstances()
   * @generated
   * @ordered
   */
  protected EList<ComponentInstance> componentInstances;

  /**
   * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference list. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   *
   * @see #getLinks()
   * @generated
   * @ordered
   */
  protected EList<Link> links;

  /**
   * The cached value of the '{@link #getHierarchyPorts() <em>Hierarchy Ports</em>}' containment reference list. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getHierarchyPorts()
   * @generated
   * @ordered
   */
  protected EList<HierarchyPort> hierarchyPorts;

  /**
   * The default value of the '{@link #getPath() <em>Path</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   *
   * @see #getPath()
   * @generated
   * @ordered
   */
  protected static final String PATH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPath() <em>Path</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getPath()
   * @generated
   * @ordered
   */
  protected String path = DesignImpl.PATH_EDEFAULT;

  /**
   * The cached value of the '{@link #getComponentHolder() <em>Component Holder</em>}' reference. <!-- begin-user-doc
   * --> Component definitions are common to all the designs of a hierarchical architecture description. The holder
   * keeps these definitions available from all designs <!-- end-user-doc -->
   *
   * @see #getComponentHolder()
   * @generated
   * @ordered
   */
  protected ComponentHolder componentHolder;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  protected DesignImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return SlamPackage.Literals.DESIGN;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EList<Parameter> getParameters() {
    if (this.parameters == null) {
      this.parameters = new EObjectContainmentEList<>(Parameter.class, this, SlamPackage.DESIGN__PARAMETERS);
    }
    return this.parameters;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EList<ComponentInstance> getComponentInstances() {
    if (this.componentInstances == null) {
      this.componentInstances = new EObjectContainmentEList<>(ComponentInstance.class, this,
          SlamPackage.DESIGN__COMPONENT_INSTANCES);
    }
    return this.componentInstances;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EList<Link> getLinks() {
    if (this.links == null) {
      this.links = new EObjectContainmentEList<>(Link.class, this, SlamPackage.DESIGN__LINKS);
    }
    return this.links;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EList<HierarchyPort> getHierarchyPorts() {
    if (this.hierarchyPorts == null) {
      this.hierarchyPorts = new EObjectContainmentEList<>(HierarchyPort.class, this,
          SlamPackage.DESIGN__HIERARCHY_PORTS);
    }
    return this.hierarchyPorts;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public Component getRefined() {
    if (eContainerFeatureID() != SlamPackage.DESIGN__REFINED) {
      return null;
    }
    return (Component) eInternalContainer();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public NotificationChain basicSetRefined(final Component newRefined, NotificationChain msgs) {
    msgs = eBasicSetContainer((InternalEObject) newRefined, SlamPackage.DESIGN__REFINED, msgs);
    return msgs;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setRefined(final Component newRefined) {
    if ((newRefined != eInternalContainer())
        || ((eContainerFeatureID() != SlamPackage.DESIGN__REFINED) && (newRefined != null))) {
      if (EcoreUtil.isAncestor(this, newRefined)) {
        throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
      }
      NotificationChain msgs = null;
      if (eInternalContainer() != null) {
        msgs = eBasicRemoveFromContainer(msgs);
      }
      if (newRefined != null) {
        msgs = ((InternalEObject) newRefined).eInverseAdd(this, ComponentPackage.COMPONENT__REFINEMENTS,
            Component.class, msgs);
      }
      msgs = basicSetRefined(newRefined, msgs);
      if (msgs != null) {
        msgs.dispatch();
      }
    } else if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, SlamPackage.DESIGN__REFINED, newRefined, newRefined));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public String getPath() {
    return this.path;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setPath(final String newPath) {
    final String oldPath = this.path;
    this.path = newPath;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, SlamPackage.DESIGN__PATH, oldPath, this.path));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public ComponentHolder getComponentHolder() {
    if ((this.componentHolder != null) && this.componentHolder.eIsProxy()) {
      final InternalEObject oldComponentHolder = (InternalEObject) this.componentHolder;
      this.componentHolder = (ComponentHolder) eResolveProxy(oldComponentHolder);
      if (this.componentHolder != oldComponentHolder) {
        if (eNotificationRequired()) {
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, SlamPackage.DESIGN__COMPONENT_HOLDER,
              oldComponentHolder, this.componentHolder));
        }
      }
    }
    return this.componentHolder;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public ComponentHolder basicGetComponentHolder() {
    return this.componentHolder;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setComponentHolder(final ComponentHolder newComponentHolder) {
    final ComponentHolder oldComponentHolder = this.componentHolder;
    this.componentHolder = newComponentHolder;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, SlamPackage.DESIGN__COMPONENT_HOLDER, oldComponentHolder,
          this.componentHolder));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated NOT
   */
  @Override
  public boolean containsComponentInstance(final String name) {
    for (final ComponentInstance instance : this.componentInstances) {
      if (instance.getInstanceName().equals(name)) {
        return true;
      }
    }

    return false;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated NOT
   */
  @Override
  public boolean containsComponent(final VLNV name) {
    for (final Component component : getComponentHolder().getComponents()) {
      if (name.equals(component.getVlnv())) {
        return true;
      }
    }
    return false;
  }

  /**
   * <!-- begin-user-doc --> Getting a component instance by its name <!-- end-user-doc -->
   *
   * @generated NOT
   */
  @Override
  public ComponentInstance getComponentInstance(final String name) {
    for (final ComponentInstance instance : this.componentInstances) {
      if (instance.getInstanceName().equals(name)) {
        return instance;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->Return null if absent<!-- end-user-doc -->
   *
   * @generated NOT
   */
  @Override
  public Component getComponent(final VLNV name) {

    for (final Component component : getComponentHolder().getComponents()) {
      if (name.equals(component.getVlnv())) {
        return component;
      }
    }
    return null;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
    switch (featureID) {
      case SlamPackage.DESIGN__REFINED:
        if (eInternalContainer() != null) {
          msgs = eBasicRemoveFromContainer(msgs);
        }
        return basicSetRefined((Component) otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
      final NotificationChain msgs) {
    switch (featureID) {
      case SlamPackage.DESIGN__PARAMETERS:
        return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
      case SlamPackage.DESIGN__COMPONENT_INSTANCES:
        return ((InternalEList<?>) getComponentInstances()).basicRemove(otherEnd, msgs);
      case SlamPackage.DESIGN__LINKS:
        return ((InternalEList<?>) getLinks()).basicRemove(otherEnd, msgs);
      case SlamPackage.DESIGN__HIERARCHY_PORTS:
        return ((InternalEList<?>) getHierarchyPorts()).basicRemove(otherEnd, msgs);
      case SlamPackage.DESIGN__REFINED:
        return basicSetRefined(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public NotificationChain eBasicRemoveFromContainerFeature(final NotificationChain msgs) {
    switch (eContainerFeatureID()) {
      case SlamPackage.DESIGN__REFINED:
        return eInternalContainer().eInverseRemove(this, ComponentPackage.COMPONENT__REFINEMENTS, Component.class,
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
  public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
    switch (featureID) {
      case SlamPackage.DESIGN__PARAMETERS:
        return getParameters();
      case SlamPackage.DESIGN__COMPONENT_INSTANCES:
        return getComponentInstances();
      case SlamPackage.DESIGN__LINKS:
        return getLinks();
      case SlamPackage.DESIGN__HIERARCHY_PORTS:
        return getHierarchyPorts();
      case SlamPackage.DESIGN__REFINED:
        return getRefined();
      case SlamPackage.DESIGN__PATH:
        return getPath();
      case SlamPackage.DESIGN__COMPONENT_HOLDER:
        if (resolve) {
          return getComponentHolder();
        }
        return basicGetComponentHolder();
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
      case SlamPackage.DESIGN__PARAMETERS:
        getParameters().clear();
        getParameters().addAll((Collection<? extends Parameter>) newValue);
        return;
      case SlamPackage.DESIGN__COMPONENT_INSTANCES:
        getComponentInstances().clear();
        getComponentInstances().addAll((Collection<? extends ComponentInstance>) newValue);
        return;
      case SlamPackage.DESIGN__LINKS:
        getLinks().clear();
        getLinks().addAll((Collection<? extends Link>) newValue);
        return;
      case SlamPackage.DESIGN__HIERARCHY_PORTS:
        getHierarchyPorts().clear();
        getHierarchyPorts().addAll((Collection<? extends HierarchyPort>) newValue);
        return;
      case SlamPackage.DESIGN__REFINED:
        setRefined((Component) newValue);
        return;
      case SlamPackage.DESIGN__PATH:
        setPath((String) newValue);
        return;
      case SlamPackage.DESIGN__COMPONENT_HOLDER:
        setComponentHolder((ComponentHolder) newValue);
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
      case SlamPackage.DESIGN__PARAMETERS:
        getParameters().clear();
        return;
      case SlamPackage.DESIGN__COMPONENT_INSTANCES:
        getComponentInstances().clear();
        return;
      case SlamPackage.DESIGN__LINKS:
        getLinks().clear();
        return;
      case SlamPackage.DESIGN__HIERARCHY_PORTS:
        getHierarchyPorts().clear();
        return;
      case SlamPackage.DESIGN__REFINED:
        setRefined((Component) null);
        return;
      case SlamPackage.DESIGN__PATH:
        setPath(DesignImpl.PATH_EDEFAULT);
        return;
      case SlamPackage.DESIGN__COMPONENT_HOLDER:
        setComponentHolder((ComponentHolder) null);
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
      case SlamPackage.DESIGN__PARAMETERS:
        return (this.parameters != null) && !this.parameters.isEmpty();
      case SlamPackage.DESIGN__COMPONENT_INSTANCES:
        return (this.componentInstances != null) && !this.componentInstances.isEmpty();
      case SlamPackage.DESIGN__LINKS:
        return (this.links != null) && !this.links.isEmpty();
      case SlamPackage.DESIGN__HIERARCHY_PORTS:
        return (this.hierarchyPorts != null) && !this.hierarchyPorts.isEmpty();
      case SlamPackage.DESIGN__REFINED:
        return getRefined() != null;
      case SlamPackage.DESIGN__PATH:
        return DesignImpl.PATH_EDEFAULT == null ? this.path != null : !DesignImpl.PATH_EDEFAULT.equals(this.path);
      case SlamPackage.DESIGN__COMPONENT_HOLDER:
        return this.componentHolder != null;
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
        case SlamPackage.DESIGN__PARAMETERS:
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
          return SlamPackage.DESIGN__PARAMETERS;
        default:
          return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
    result.append(" (path: ");
    result.append(this.path);
    result.append(')');
    return result.toString();
  }

  @Deprecated
  @Override
  public Component getComponent(final VLNV name, final EClass class_) {

    for (final Component component : getComponentHolder().getComponents()) {
      if (name.equals(component.getVlnv())) {
        return component;
      }
    }

    final Component component = (Component) ComponentFactory.eINSTANCE.create(class_);
    component.setVlnv(name);

    getComponentHolder().getComponents().add(component);
    return component;
  }
} // DesignImpl
