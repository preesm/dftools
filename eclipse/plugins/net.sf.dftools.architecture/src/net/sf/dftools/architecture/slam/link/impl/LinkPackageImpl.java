/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.link.impl;

import net.sf.dftools.architecture.slam.SlamPackage;
import net.sf.dftools.architecture.slam.attributes.AttributesPackage;
import net.sf.dftools.architecture.slam.attributes.impl.AttributesPackageImpl;
import net.sf.dftools.architecture.slam.component.ComponentPackage;
import net.sf.dftools.architecture.slam.component.impl.ComponentPackageImpl;
import net.sf.dftools.architecture.slam.impl.SlamPackageImpl;
import net.sf.dftools.architecture.slam.link.ControlLink;
import net.sf.dftools.architecture.slam.link.DataLink;
import net.sf.dftools.architecture.slam.link.Link;
import net.sf.dftools.architecture.slam.link.LinkFactory;
import net.sf.dftools.architecture.slam.link.LinkPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class LinkPackageImpl extends EPackageImpl implements LinkPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataLinkEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass controlLinkEClass = null;

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
	 * @see net.sf.dftools.architecture.slam.link.LinkPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LinkPackageImpl() {
		super(eNS_URI, LinkFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link LinkPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LinkPackage init() {
		if (isInited) return (LinkPackage)EPackage.Registry.INSTANCE.getEPackage(LinkPackage.eNS_URI);

		// Obtain or create and register package
		LinkPackageImpl theLinkPackage = (LinkPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof LinkPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new LinkPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		SlamPackageImpl theSlamPackage = (SlamPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI) instanceof SlamPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI) : SlamPackage.eINSTANCE);
		ComponentPackageImpl theComponentPackage = (ComponentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI) instanceof ComponentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI) : ComponentPackage.eINSTANCE);
		AttributesPackageImpl theAttributesPackage = (AttributesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributesPackage.eNS_URI) instanceof AttributesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributesPackage.eNS_URI) : AttributesPackage.eINSTANCE);

		// Create package meta-data objects
		theLinkPackage.createPackageContents();
		theSlamPackage.createPackageContents();
		theComponentPackage.createPackageContents();
		theAttributesPackage.createPackageContents();

		// Initialize created meta-data
		theLinkPackage.initializePackageContents();
		theSlamPackage.initializePackageContents();
		theComponentPackage.initializePackageContents();
		theAttributesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLinkPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LinkPackage.eNS_URI, theLinkPackage);
		return theLinkPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLink() {
		return linkEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLink_SourceInterface() {
		return (EReference)linkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLink_DestinationInterface() {
		return (EReference)linkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLink_SourceComponentInstance() {
		return (EReference)linkEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLink_DestinationComponentInstance() {
		return (EReference)linkEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLink_Uuid() {
		return (EAttribute)linkEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLink_Directed() {
		return (EAttribute)linkEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataLink() {
		return dataLinkEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getControlLink() {
		return controlLinkEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getControlLink_SetupTime() {
		return (EAttribute)controlLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public LinkFactory getLinkFactory() {
		return (LinkFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		linkEClass = createEClass(LINK);
		createEReference(linkEClass, LINK__SOURCE_INTERFACE);
		createEReference(linkEClass, LINK__DESTINATION_INTERFACE);
		createEReference(linkEClass, LINK__SOURCE_COMPONENT_INSTANCE);
		createEReference(linkEClass, LINK__DESTINATION_COMPONENT_INSTANCE);
		createEAttribute(linkEClass, LINK__UUID);
		createEAttribute(linkEClass, LINK__DIRECTED);

		dataLinkEClass = createEClass(DATA_LINK);

		controlLinkEClass = createEClass(CONTROL_LINK);
		createEAttribute(controlLinkEClass, CONTROL_LINK__SETUP_TIME);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ComponentPackage theComponentPackage = (ComponentPackage)EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI);
		SlamPackage theSlamPackage = (SlamPackage)EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dataLinkEClass.getESuperTypes().add(this.getLink());
		controlLinkEClass.getESuperTypes().add(this.getLink());

		// Initialize classes and features; add operations and parameters
		initEClass(linkEClass, Link.class, "Link", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLink_SourceInterface(), theComponentPackage.getComInterface(), null, "sourceInterface", null, 1, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLink_DestinationInterface(), theComponentPackage.getComInterface(), null, "destinationInterface", null, 1, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLink_SourceComponentInstance(), theSlamPackage.getComponentInstance(), null, "sourceComponentInstance", null, 1, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLink_DestinationComponentInstance(), theSlamPackage.getComponentInstance(), null, "destinationComponentInstance", null, 1, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLink_Uuid(), ecorePackage.getEString(), "uuid", null, 1, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLink_Directed(), ecorePackage.getEBoolean(), "directed", "false", 1, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataLinkEClass, DataLink.class, "DataLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(controlLinkEClass, ControlLink.class, "ControlLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getControlLink_SetupTime(), ecorePackage.getEInt(), "setupTime", "0", 0, 1, ControlLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} // LinkPackageImpl
