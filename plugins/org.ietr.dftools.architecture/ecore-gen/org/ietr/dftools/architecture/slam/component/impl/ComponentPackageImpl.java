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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.attributes.AttributesPackage;
import org.ietr.dftools.architecture.slam.attributes.impl.AttributesPackageImpl;
import org.ietr.dftools.architecture.slam.component.ComInterface;
import org.ietr.dftools.architecture.slam.component.ComNode;
import org.ietr.dftools.architecture.slam.component.Component;
import org.ietr.dftools.architecture.slam.component.ComponentFactory;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;
import org.ietr.dftools.architecture.slam.component.Dma;
import org.ietr.dftools.architecture.slam.component.Enabler;
import org.ietr.dftools.architecture.slam.component.HierarchyPort;
import org.ietr.dftools.architecture.slam.component.Mem;
import org.ietr.dftools.architecture.slam.component.Operator;
import org.ietr.dftools.architecture.slam.impl.SlamPackageImpl;
import org.ietr.dftools.architecture.slam.link.LinkPackage;
import org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ComponentPackageImpl extends EPackageImpl implements ComponentPackage {
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass componentEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass operatorEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass comNodeEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass enablerEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass dmaEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass memEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass hierarchyPortEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass comInterfaceEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
   * EPackage.Registry} by the package package URI value.
   * <p>
   * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
   * performs initialization of the package, or returns the registered package, if one already exists. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   *
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private ComponentPackageImpl() {
    super(ComponentPackage.eNS_URI, ComponentFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>
   * This method is used to initialize {@link ComponentPackage#eINSTANCE} when that field is accessed. Clients should
   * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   *
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static ComponentPackage init() {
    if (ComponentPackageImpl.isInited) {
      return (ComponentPackage) EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI);
    }

    // Obtain or create and register package
    final ComponentPackageImpl theComponentPackage = (ComponentPackageImpl) (EPackage.Registry.INSTANCE
        .get(ComponentPackage.eNS_URI) instanceof ComponentPackageImpl
            ? EPackage.Registry.INSTANCE.get(ComponentPackage.eNS_URI)
            : new ComponentPackageImpl());

    ComponentPackageImpl.isInited = true;

    // Obtain or create and register interdependencies
    final SlamPackageImpl theSlamPackage = (SlamPackageImpl) (EPackage.Registry.INSTANCE
        .getEPackage(SlamPackage.eNS_URI) instanceof SlamPackageImpl
            ? EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI)
            : SlamPackage.eINSTANCE);
    final LinkPackageImpl theLinkPackage = (LinkPackageImpl) (EPackage.Registry.INSTANCE
        .getEPackage(LinkPackage.eNS_URI) instanceof LinkPackageImpl
            ? EPackage.Registry.INSTANCE.getEPackage(LinkPackage.eNS_URI)
            : LinkPackage.eINSTANCE);
    final AttributesPackageImpl theAttributesPackage = (AttributesPackageImpl) (EPackage.Registry.INSTANCE
        .getEPackage(AttributesPackage.eNS_URI) instanceof AttributesPackageImpl
            ? EPackage.Registry.INSTANCE.getEPackage(AttributesPackage.eNS_URI)
            : AttributesPackage.eINSTANCE);

    // Create package meta-data objects
    theComponentPackage.createPackageContents();
    theSlamPackage.createPackageContents();
    theLinkPackage.createPackageContents();
    theAttributesPackage.createPackageContents();

    // Initialize created meta-data
    theComponentPackage.initializePackageContents();
    theSlamPackage.initializePackageContents();
    theLinkPackage.initializePackageContents();
    theAttributesPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theComponentPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(ComponentPackage.eNS_URI, theComponentPackage);
    return theComponentPackage;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getComponent() {
    return this.componentEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getComponent_Interfaces() {
    return (EReference) this.componentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getComponent_Instances() {
    return (EReference) this.componentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getComponent_Refinements() {
    return (EReference) this.componentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getOperator() {
    return this.operatorEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getComNode() {
    return this.comNodeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EAttribute getComNode_Parallel() {
    return (EAttribute) this.comNodeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EAttribute getComNode_Speed() {
    return (EAttribute) this.comNodeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getEnabler() {
    return this.enablerEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getDma() {
    return this.dmaEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EAttribute getDma_SetupTime() {
    return (EAttribute) this.dmaEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getMem() {
    return this.memEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EAttribute getMem_Size() {
    return (EAttribute) this.memEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getHierarchyPort() {
    return this.hierarchyPortEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getHierarchyPort_ExternalInterface() {
    return (EReference) this.hierarchyPortEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getHierarchyPort_InternalInterface() {
    return (EReference) this.hierarchyPortEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getHierarchyPort_InternalComponentInstance() {
    return (EReference) this.hierarchyPortEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getComInterface() {
    return this.comInterfaceEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getComInterface_Component() {
    return (EReference) this.comInterfaceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getComInterface_BusType() {
    return (EReference) this.comInterfaceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EAttribute getComInterface_Name() {
    return (EAttribute) this.comInterfaceEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public ComponentFactory getComponentFactory() {
    return (ComponentFactory) getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but its
   * first. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public void createPackageContents() {
    if (this.isCreated) {
      return;
    }
    this.isCreated = true;

    // Create classes and their features
    this.componentEClass = createEClass(ComponentPackage.COMPONENT);
    createEReference(this.componentEClass, ComponentPackage.COMPONENT__INTERFACES);
    createEReference(this.componentEClass, ComponentPackage.COMPONENT__INSTANCES);
    createEReference(this.componentEClass, ComponentPackage.COMPONENT__REFINEMENTS);

    this.operatorEClass = createEClass(ComponentPackage.OPERATOR);

    this.comNodeEClass = createEClass(ComponentPackage.COM_NODE);
    createEAttribute(this.comNodeEClass, ComponentPackage.COM_NODE__PARALLEL);
    createEAttribute(this.comNodeEClass, ComponentPackage.COM_NODE__SPEED);

    this.enablerEClass = createEClass(ComponentPackage.ENABLER);

    this.dmaEClass = createEClass(ComponentPackage.DMA);
    createEAttribute(this.dmaEClass, ComponentPackage.DMA__SETUP_TIME);

    this.memEClass = createEClass(ComponentPackage.MEM);
    createEAttribute(this.memEClass, ComponentPackage.MEM__SIZE);

    this.hierarchyPortEClass = createEClass(ComponentPackage.HIERARCHY_PORT);
    createEReference(this.hierarchyPortEClass, ComponentPackage.HIERARCHY_PORT__EXTERNAL_INTERFACE);
    createEReference(this.hierarchyPortEClass, ComponentPackage.HIERARCHY_PORT__INTERNAL_INTERFACE);
    createEReference(this.hierarchyPortEClass, ComponentPackage.HIERARCHY_PORT__INTERNAL_COMPONENT_INSTANCE);

    this.comInterfaceEClass = createEClass(ComponentPackage.COM_INTERFACE);
    createEReference(this.comInterfaceEClass, ComponentPackage.COM_INTERFACE__COMPONENT);
    createEReference(this.comInterfaceEClass, ComponentPackage.COM_INTERFACE__BUS_TYPE);
    createEAttribute(this.comInterfaceEClass, ComponentPackage.COM_INTERFACE__NAME);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
   * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public void initializePackageContents() {
    if (this.isInitialized) {
      return;
    }
    this.isInitialized = true;

    // Initialize package
    setName(ComponentPackage.eNAME);
    setNsPrefix(ComponentPackage.eNS_PREFIX);
    setNsURI(ComponentPackage.eNS_URI);

    // Obtain other dependent packages
    final SlamPackage theSlamPackage = (SlamPackage) EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI);
    final AttributesPackage theAttributesPackage = (AttributesPackage) EPackage.Registry.INSTANCE
        .getEPackage(AttributesPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    this.componentEClass.getESuperTypes().add(theSlamPackage.getVLNVedElement());
    this.componentEClass.getESuperTypes().add(theSlamPackage.getParameterizedElement());
    this.operatorEClass.getESuperTypes().add(getComponent());
    this.comNodeEClass.getESuperTypes().add(getComponent());
    this.enablerEClass.getESuperTypes().add(getComponent());
    this.dmaEClass.getESuperTypes().add(getEnabler());
    this.memEClass.getESuperTypes().add(getEnabler());

    // Initialize classes and features; add operations and parameters
    initEClass(this.componentEClass, Component.class, "Component", !EPackageImpl.IS_ABSTRACT,
        !EPackageImpl.IS_INTERFACE, EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
    initEReference(getComponent_Interfaces(), getComInterface(), getComInterface_Component(), "interfaces", null, 0, -1,
        Component.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE,
        EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE,
        EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEReference(getComponent_Instances(), theSlamPackage.getComponentInstance(),
        theSlamPackage.getComponentInstance_Component(), "instances", null, 0, -1, Component.class,
        !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_COMPOSITE,
        EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED,
        EPackageImpl.IS_ORDERED);
    initEReference(getComponent_Refinements(), theSlamPackage.getDesign(), theSlamPackage.getDesign_Refined(),
        "refinements", null, 0, -1, Component.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE,
        EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES,
        !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

    final EOperation op = addEOperation(this.componentEClass, getComInterface(), "getInterface", 1, 1,
        EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);
    addEParameter(op, this.ecorePackage.getEString(), "name", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);

    initEClass(this.operatorEClass, Operator.class, "Operator", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
        EPackageImpl.IS_GENERATED_INSTANCE_CLASS);

    initEClass(this.comNodeEClass, ComNode.class, "ComNode", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
        EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getComNode_Parallel(), this.ecorePackage.getEBoolean(), "parallel", "true", 1, 1, ComNode.class,
        !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE,
        !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEAttribute(getComNode_Speed(), this.ecorePackage.getEFloat(), "speed", "1", 1, 1, ComNode.class,
        !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE,
        !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

    initEClass(this.enablerEClass, Enabler.class, "Enabler", EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
        EPackageImpl.IS_GENERATED_INSTANCE_CLASS);

    initEClass(this.dmaEClass, Dma.class, "Dma", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
        EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDma_SetupTime(), this.ecorePackage.getEInt(), "setupTime", "0", 0, 1, Dma.class,
        !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE,
        !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

    initEClass(this.memEClass, Mem.class, "Mem", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
        EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMem_Size(), this.ecorePackage.getEInt(), "size", "1", 1, 1, Mem.class, !EPackageImpl.IS_TRANSIENT,
        !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID,
        EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

    initEClass(this.hierarchyPortEClass, HierarchyPort.class, "HierarchyPort", !EPackageImpl.IS_ABSTRACT,
        !EPackageImpl.IS_INTERFACE, EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
    initEReference(getHierarchyPort_ExternalInterface(), getComInterface(), null, "externalInterface", null, 1, 1,
        HierarchyPort.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE,
        !EPackageImpl.IS_COMPOSITE, EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE,
        EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEReference(getHierarchyPort_InternalInterface(), getComInterface(), null, "internalInterface", null, 1, 1,
        HierarchyPort.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE,
        !EPackageImpl.IS_COMPOSITE, EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE,
        EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEReference(getHierarchyPort_InternalComponentInstance(), theSlamPackage.getComponentInstance(), null,
        "internalComponentInstance", null, 1, 1, HierarchyPort.class, !EPackageImpl.IS_TRANSIENT,
        !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_COMPOSITE,
        EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED,
        EPackageImpl.IS_ORDERED);

    initEClass(this.comInterfaceEClass, ComInterface.class, "ComInterface", !EPackageImpl.IS_ABSTRACT,
        !EPackageImpl.IS_INTERFACE, EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
    initEReference(getComInterface_Component(), getComponent(), getComponent_Interfaces(), "component", null, 1, 1,
        ComInterface.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE,
        !EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE,
        EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEReference(getComInterface_BusType(), theAttributesPackage.getVLNV(), null, "busType", null, 1, 1,
        ComInterface.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE,
        EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE,
        EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEAttribute(getComInterface_Name(), this.ecorePackage.getEString(), "name", "", 1, 1, ComInterface.class,
        !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE,
        EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
  }

} // ComponentPackageImpl
