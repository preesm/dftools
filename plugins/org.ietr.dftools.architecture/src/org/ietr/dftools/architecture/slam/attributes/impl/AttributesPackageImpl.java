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
package org.ietr.dftools.architecture.slam.attributes.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.attributes.AttributesFactory;
import org.ietr.dftools.architecture.slam.attributes.AttributesPackage;
import org.ietr.dftools.architecture.slam.attributes.Parameter;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;
import org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl;
import org.ietr.dftools.architecture.slam.impl.SlamPackageImpl;
import org.ietr.dftools.architecture.slam.link.LinkPackage;
import org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class AttributesPackageImpl extends EPackageImpl implements
		AttributesPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass vlnvEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass parameterEClass = null;

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
	 * @see org.ietr.dftools.architecture.slam.attributes.AttributesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AttributesPackageImpl() {
		super(eNS_URI, AttributesFactory.eINSTANCE);
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
	 * This method is used to initialize {@link AttributesPackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly.
	 * Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AttributesPackage init() {
		if (isInited)
			return (AttributesPackage) EPackage.Registry.INSTANCE
					.getEPackage(AttributesPackage.eNS_URI);

		// Obtain or create and register package
		AttributesPackageImpl theAttributesPackage = (AttributesPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof AttributesPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new AttributesPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		SlamPackageImpl theSlamPackage = (SlamPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(SlamPackage.eNS_URI) instanceof SlamPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(SlamPackage.eNS_URI) : SlamPackage.eINSTANCE);
		ComponentPackageImpl theComponentPackage = (ComponentPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(ComponentPackage.eNS_URI) instanceof ComponentPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(ComponentPackage.eNS_URI)
				: ComponentPackage.eINSTANCE);
		LinkPackageImpl theLinkPackage = (LinkPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(LinkPackage.eNS_URI) instanceof LinkPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(LinkPackage.eNS_URI) : LinkPackage.eINSTANCE);

		// Create package meta-data objects
		theAttributesPackage.createPackageContents();
		theSlamPackage.createPackageContents();
		theComponentPackage.createPackageContents();
		theLinkPackage.createPackageContents();

		// Initialize created meta-data
		theAttributesPackage.initializePackageContents();
		theSlamPackage.initializePackageContents();
		theComponentPackage.initializePackageContents();
		theLinkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAttributesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AttributesPackage.eNS_URI,
				theAttributesPackage);
		return theAttributesPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getVLNV() {
		return vlnvEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVLNV_Vendor() {
		return (EAttribute) vlnvEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVLNV_Library() {
		return (EAttribute) vlnvEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVLNV_Name() {
		return (EAttribute) vlnvEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVLNV_Version() {
		return (EAttribute) vlnvEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getParameter_Key() {
		return (EAttribute) parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getParameter_Value() {
		return (EAttribute) parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AttributesFactory getAttributesFactory() {
		return (AttributesFactory) getEFactoryInstance();
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
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		vlnvEClass = createEClass(VLNV);
		createEAttribute(vlnvEClass, VLNV__VENDOR);
		createEAttribute(vlnvEClass, VLNV__LIBRARY);
		createEAttribute(vlnvEClass, VLNV__NAME);
		createEAttribute(vlnvEClass, VLNV__VERSION);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__KEY);
		createEAttribute(parameterEClass, PARAMETER__VALUE);
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
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(vlnvEClass,
				org.ietr.dftools.architecture.slam.attributes.VLNV.class, "VLNV",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVLNV_Vendor(), ecorePackage.getEString(), "vendor",
				"", 1, 1,
				org.ietr.dftools.architecture.slam.attributes.VLNV.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVLNV_Library(), ecorePackage.getEString(), "library",
				null, 1, 1,
				org.ietr.dftools.architecture.slam.attributes.VLNV.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVLNV_Name(), ecorePackage.getEString(), "name", null,
				1, 1, org.ietr.dftools.architecture.slam.attributes.VLNV.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVLNV_Version(), ecorePackage.getEString(), "version",
				null, 1, 1,
				org.ietr.dftools.architecture.slam.attributes.VLNV.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_Key(), ecorePackage.getEString(), "key",
				null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getParameter_Value(), ecorePackage.getEString(),
				"value", null, 1, 1, Parameter.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
	}

} // AttributesPackageImpl
