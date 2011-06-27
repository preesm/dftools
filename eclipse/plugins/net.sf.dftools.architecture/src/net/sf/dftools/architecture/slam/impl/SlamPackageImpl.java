/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.impl;

import net.sf.dftools.architecture.slam.ComponentInstance;
import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.ParameterizedElement;
import net.sf.dftools.architecture.slam.SlamFactory;
import net.sf.dftools.architecture.slam.SlamPackage;
import net.sf.dftools.architecture.slam.VLNVedElement;
import net.sf.dftools.architecture.slam.attributes.AttributesPackage;
import net.sf.dftools.architecture.slam.attributes.impl.AttributesPackageImpl;
import net.sf.dftools.architecture.slam.component.ComponentPackage;
import net.sf.dftools.architecture.slam.component.impl.ComponentPackageImpl;
import net.sf.dftools.architecture.slam.link.LinkPackage;
import net.sf.dftools.architecture.slam.link.impl.LinkPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SlamPackageImpl extends EPackageImpl implements SlamPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass designEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentInstanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vlnVedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterizedElementEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see net.sf.dftools.architecture.slam.SlamPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SlamPackageImpl() {
		super(eNS_URI, SlamFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link SlamPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SlamPackage init() {
		if (isInited) return (SlamPackage)EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI);

		// Obtain or create and register package
		SlamPackageImpl theSlamPackage = (SlamPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SlamPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SlamPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ComponentPackageImpl theComponentPackage = (ComponentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI) instanceof ComponentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI) : ComponentPackage.eINSTANCE);
		LinkPackageImpl theLinkPackage = (LinkPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(LinkPackage.eNS_URI) instanceof LinkPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(LinkPackage.eNS_URI) : LinkPackage.eINSTANCE);
		AttributesPackageImpl theAttributesPackage = (AttributesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributesPackage.eNS_URI) instanceof AttributesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributesPackage.eNS_URI) : AttributesPackage.eINSTANCE);

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDesign() {
		return designEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesign_ComponentInstances() {
		return (EReference)designEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesign_Links() {
		return (EReference)designEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesign_HierarchyPorts() {
		return (EReference)designEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesign_Components() {
		return (EReference)designEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesign_Refined() {
		return (EReference)designEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentInstance() {
		return componentInstanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentInstance_Component() {
		return (EReference)componentInstanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentInstance_InstanceName() {
		return (EAttribute)componentInstanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVLNVedElement() {
		return vlnVedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVLNVedElement_Vlnv() {
		return (EReference)vlnVedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterizedElement() {
		return parameterizedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterizedElement_Parameters() {
		return (EReference)parameterizedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlamFactory getSlamFactory() {
		return (SlamFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		designEClass = createEClass(DESIGN);
		createEReference(designEClass, DESIGN__COMPONENT_INSTANCES);
		createEReference(designEClass, DESIGN__LINKS);
		createEReference(designEClass, DESIGN__HIERARCHY_PORTS);
		createEReference(designEClass, DESIGN__COMPONENTS);
		createEReference(designEClass, DESIGN__REFINED);

		componentInstanceEClass = createEClass(COMPONENT_INSTANCE);
		createEReference(componentInstanceEClass, COMPONENT_INSTANCE__COMPONENT);
		createEAttribute(componentInstanceEClass, COMPONENT_INSTANCE__INSTANCE_NAME);

		vlnVedElementEClass = createEClass(VLN_VED_ELEMENT);
		createEReference(vlnVedElementEClass, VLN_VED_ELEMENT__VLNV);

		parameterizedElementEClass = createEClass(PARAMETERIZED_ELEMENT);
		createEReference(parameterizedElementEClass, PARAMETERIZED_ELEMENT__PARAMETERS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ComponentPackage theComponentPackage = (ComponentPackage)EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI);
		LinkPackage theLinkPackage = (LinkPackage)EPackage.Registry.INSTANCE.getEPackage(LinkPackage.eNS_URI);
		AttributesPackage theAttributesPackage = (AttributesPackage)EPackage.Registry.INSTANCE.getEPackage(AttributesPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theComponentPackage);
		getESubpackages().add(theLinkPackage);
		getESubpackages().add(theAttributesPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		designEClass.getESuperTypes().add(this.getVLNVedElement());
		componentInstanceEClass.getESuperTypes().add(this.getVLNVedElement());
		componentInstanceEClass.getESuperTypes().add(this.getParameterizedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(designEClass, Design.class, "Design", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDesign_ComponentInstances(), this.getComponentInstance(), null, "componentInstances", null, 0, -1, Design.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		getDesign_ComponentInstances().getEKeys().add(this.getComponentInstance_InstanceName());
		initEReference(getDesign_Links(), theLinkPackage.getLink(), null, "links", null, 0, -1, Design.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDesign_HierarchyPorts(), theComponentPackage.getHierarchyPort(), null, "hierarchyPorts", null, 0, -1, Design.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDesign_Components(), theComponentPackage.getComponent(), null, "components", null, 0, -1, Design.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getDesign_Refined(), theComponentPackage.getComponent(), theComponentPackage.getComponent_Refinement(), "refined", null, 0, 1, Design.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(designEClass, ecorePackage.getEBoolean(), "containsComponentInstance", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(designEClass, ecorePackage.getEBoolean(), "containsComponent", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(designEClass, theComponentPackage.getComponent(), "getComponent", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(designEClass, this.getComponentInstance(), "getComponentInstance", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(componentInstanceEClass, ComponentInstance.class, "ComponentInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComponentInstance_Component(), theComponentPackage.getComponent(), theComponentPackage.getComponent_Instances(), "component", null, 1, 1, ComponentInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentInstance_InstanceName(), ecorePackage.getEString(), "instanceName", null, 1, 1, ComponentInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(componentInstanceEClass, ecorePackage.getEBoolean(), "isHierarchical", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(vlnVedElementEClass, VLNVedElement.class, "VLNVedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVLNVedElement_Vlnv(), theAttributesPackage.getVLNV(), null, "vlnv", null, 1, 1, VLNVedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterizedElementEClass, ParameterizedElement.class, "ParameterizedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterizedElement_Parameters(), theAttributesPackage.getParameter(), null, "parameters", null, 0, -1, ParameterizedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getParameterizedElement_Parameters().getEKeys().add(theAttributesPackage.getParameter_Key());

		// Create resource
		createResource(eNS_URI);
	}

} //SlamPackageImpl
