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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.ietr.dftools.architecture.slam.ComponentHolder;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.SlamFactory;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.VLNVedElement;
import org.ietr.dftools.architecture.slam.attributes.AttributesPackage;
import org.ietr.dftools.architecture.slam.attributes.impl.AttributesPackageImpl;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;
import org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl;
import org.ietr.dftools.architecture.slam.link.LinkPackage;
import org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class SlamPackageImpl extends EPackageImpl implements SlamPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass designEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass componentInstanceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass vlnVedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass parameterizedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass componentHolderEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.ietr.dftools.architecture.slam.SlamPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SlamPackageImpl() {
		super(SlamPackage.eNS_URI, SlamFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link SlamPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SlamPackage init() {
		if (SlamPackageImpl.isInited) {
			return (SlamPackage) EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI);
		}

		// Obtain or create and register package
		final SlamPackageImpl theSlamPackage = (SlamPackageImpl) (EPackage.Registry.INSTANCE.get(SlamPackage.eNS_URI) instanceof SlamPackageImpl
				? EPackage.Registry.INSTANCE.get(SlamPackage.eNS_URI) : new SlamPackageImpl());

		SlamPackageImpl.isInited = true;

		// Obtain or create and register interdependencies
		final ComponentPackageImpl theComponentPackage = (ComponentPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(ComponentPackage.eNS_URI) instanceof ComponentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI)
						: ComponentPackage.eINSTANCE);
		final LinkPackageImpl theLinkPackage = (LinkPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(LinkPackage.eNS_URI) instanceof LinkPackageImpl
				? EPackage.Registry.INSTANCE.getEPackage(LinkPackage.eNS_URI) : LinkPackage.eINSTANCE);
		final AttributesPackageImpl theAttributesPackage = (AttributesPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(AttributesPackage.eNS_URI) instanceof AttributesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributesPackage.eNS_URI)
						: AttributesPackage.eINSTANCE);

		// Create package meta-data objects
		theSlamPackage.createPackageContents();
		theComponentPackage.createPackageContents();
		theLinkPackage.createPackageContents();
		theAttributesPackage.createPackageContents();

		// Initialize created meta-data
		theSlamPackage.initializePackageContents();
		theComponentPackage.initializePackageContents();
		theLinkPackage.initializePackageContents();
		theAttributesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSlamPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SlamPackage.eNS_URI, theSlamPackage);
		return theSlamPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDesign() {
		return this.designEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDesign_ComponentInstances() {
		return (EReference) this.designEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDesign_Links() {
		return (EReference) this.designEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDesign_HierarchyPorts() {
		return (EReference) this.designEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDesign_Refined() {
		return (EReference) this.designEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getDesign_Path() {
		return (EAttribute) this.designEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDesign_ComponentHolder() {
		return (EReference) this.designEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getComponentInstance() {
		return this.componentInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getComponentInstance_Component() {
		return (EReference) this.componentInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getComponentInstance_InstanceName() {
		return (EAttribute) this.componentInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getVLNVedElement() {
		return this.vlnVedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getVLNVedElement_Vlnv() {
		return (EReference) this.vlnVedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getParameterizedElement() {
		return this.parameterizedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getParameterizedElement_Parameters() {
		return (EReference) this.parameterizedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getComponentHolder() {
		return this.componentHolderEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getComponentHolder_Components() {
		return (EReference) this.componentHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SlamFactory getSlamFactory() {
		return (SlamFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (this.isCreated) {
			return;
		}
		this.isCreated = true;

		// Create classes and their features
		this.designEClass = createEClass(SlamPackage.DESIGN);
		createEReference(this.designEClass, SlamPackage.DESIGN__COMPONENT_INSTANCES);
		createEReference(this.designEClass, SlamPackage.DESIGN__LINKS);
		createEReference(this.designEClass, SlamPackage.DESIGN__HIERARCHY_PORTS);
		createEReference(this.designEClass, SlamPackage.DESIGN__REFINED);
		createEAttribute(this.designEClass, SlamPackage.DESIGN__PATH);
		createEReference(this.designEClass, SlamPackage.DESIGN__COMPONENT_HOLDER);

		this.componentInstanceEClass = createEClass(SlamPackage.COMPONENT_INSTANCE);
		createEReference(this.componentInstanceEClass, SlamPackage.COMPONENT_INSTANCE__COMPONENT);
		createEAttribute(this.componentInstanceEClass, SlamPackage.COMPONENT_INSTANCE__INSTANCE_NAME);

		this.vlnVedElementEClass = createEClass(SlamPackage.VLN_VED_ELEMENT);
		createEReference(this.vlnVedElementEClass, SlamPackage.VLN_VED_ELEMENT__VLNV);

		this.parameterizedElementEClass = createEClass(SlamPackage.PARAMETERIZED_ELEMENT);
		createEReference(this.parameterizedElementEClass, SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS);

		this.componentHolderEClass = createEClass(SlamPackage.COMPONENT_HOLDER);
		createEReference(this.componentHolderEClass, SlamPackage.COMPONENT_HOLDER__COMPONENTS);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (this.isInitialized) {
			return;
		}
		this.isInitialized = true;

		// Initialize package
		setName(SlamPackage.eNAME);
		setNsPrefix(SlamPackage.eNS_PREFIX);
		setNsURI(SlamPackage.eNS_URI);

		// Obtain other dependent packages
		final ComponentPackage theComponentPackage = (ComponentPackage) EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI);
		final LinkPackage theLinkPackage = (LinkPackage) EPackage.Registry.INSTANCE.getEPackage(LinkPackage.eNS_URI);
		final AttributesPackage theAttributesPackage = (AttributesPackage) EPackage.Registry.INSTANCE.getEPackage(AttributesPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theComponentPackage);
		getESubpackages().add(theLinkPackage);
		getESubpackages().add(theAttributesPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		this.designEClass.getESuperTypes().add(getVLNVedElement());
		this.designEClass.getESuperTypes().add(getParameterizedElement());
		this.componentInstanceEClass.getESuperTypes().add(getParameterizedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(this.designEClass, Design.class, "Design", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE, EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDesign_ComponentInstances(), getComponentInstance(), null, "componentInstances", null, 0, -1, Design.class,
				!EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES,
				!EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, !EPackageImpl.IS_ORDERED);
		getDesign_ComponentInstances().getEKeys().add(getComponentInstance_InstanceName());
		initEReference(getDesign_Links(), theLinkPackage.getLink(), null, "links", null, 0, -1, Design.class, !EPackageImpl.IS_TRANSIENT,
				!EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE,
				EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
		initEReference(getDesign_HierarchyPorts(), theComponentPackage.getHierarchyPort(), null, "hierarchyPorts", null, 0, -1, Design.class,
				!EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES,
				!EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
		initEReference(getDesign_Refined(), theComponentPackage.getComponent(), theComponentPackage.getComponent_Refinements(), "refined", null, 0, 1,
				Design.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_COMPOSITE,
				!EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
		initEAttribute(getDesign_Path(), this.ecorePackage.getEString(), "path", null, 0, 1, Design.class, !EPackageImpl.IS_TRANSIENT,
				!EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE,
				!EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
		initEReference(getDesign_ComponentHolder(), getComponentHolder(), null, "componentHolder", null, 1, 1, Design.class, !EPackageImpl.IS_TRANSIENT,
				!EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_COMPOSITE, EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE,
				EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

		EOperation op = addEOperation(this.designEClass, this.ecorePackage.getEBoolean(), "containsComponentInstance", 1, 1, EPackageImpl.IS_UNIQUE,
				EPackageImpl.IS_ORDERED);
		addEParameter(op, this.ecorePackage.getEString(), "name", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);

		op = addEOperation(this.designEClass, this.ecorePackage.getEBoolean(), "containsComponent", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);
		addEParameter(op, theAttributesPackage.getVLNV(), "name", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);

		op = addEOperation(this.designEClass, getComponentInstance(), "getComponentInstance", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);
		addEParameter(op, this.ecorePackage.getEString(), "name", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);

		op = addEOperation(this.designEClass, theComponentPackage.getComponent(), "getComponent", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);
		addEParameter(op, theAttributesPackage.getVLNV(), "name", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);
		addEParameter(op, this.ecorePackage.getEClass(), "class_", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);

		initEClass(this.componentInstanceEClass, ComponentInstance.class, "ComponentInstance", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
				EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComponentInstance_Component(), theComponentPackage.getComponent(), theComponentPackage.getComponent_Instances(), "component", null, 1,
				1, ComponentInstance.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_COMPOSITE,
				EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
		initEAttribute(getComponentInstance_InstanceName(), this.ecorePackage.getEString(), "instanceName", null, 1, 1, ComponentInstance.class,
				!EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID,
				EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

		addEOperation(this.componentInstanceEClass, this.ecorePackage.getEBoolean(), "isHierarchical", 1, 1, EPackageImpl.IS_UNIQUE, EPackageImpl.IS_ORDERED);

		initEClass(this.vlnVedElementEClass, VLNVedElement.class, "VLNVedElement", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
				EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVLNVedElement_Vlnv(), theAttributesPackage.getVLNV(), null, "vlnv", null, 1, 1, VLNVedElement.class, !EPackageImpl.IS_TRANSIENT,
				!EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES, !EPackageImpl.IS_UNSETTABLE,
				EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

		initEClass(this.parameterizedElementEClass, ParameterizedElement.class, "ParameterizedElement", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
				EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterizedElement_Parameters(), theAttributesPackage.getParameter(), null, "parameters", null, 0, -1, ParameterizedElement.class,
				!EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES,
				!EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
		getParameterizedElement_Parameters().getEKeys().add(theAttributesPackage.getParameter_Key());

		initEClass(this.componentHolderEClass, ComponentHolder.class, "ComponentHolder", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
				EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComponentHolder_Components(), theComponentPackage.getComponent(), null, "components", null, 0, -1, ComponentHolder.class,
				!EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, EPackageImpl.IS_COMPOSITE, !EPackageImpl.IS_RESOLVE_PROXIES,
				!EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, !EPackageImpl.IS_ORDERED);

		// Create resource
		createResource(SlamPackage.eNS_URI);
	}

} // SlamPackageImpl
