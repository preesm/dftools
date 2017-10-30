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
package org.ietr.dftools.architecture.slam.link.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.attributes.AttributesPackage;
import org.ietr.dftools.architecture.slam.attributes.impl.AttributesPackageImpl;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;
import org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl;
import org.ietr.dftools.architecture.slam.impl.SlamPackageImpl;
import org.ietr.dftools.architecture.slam.link.ControlLink;
import org.ietr.dftools.architecture.slam.link.DataLink;
import org.ietr.dftools.architecture.slam.link.Link;
import org.ietr.dftools.architecture.slam.link.LinkFactory;
import org.ietr.dftools.architecture.slam.link.LinkPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class LinkPackageImpl extends EPackageImpl implements LinkPackage {
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass linkEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass dataLinkEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private EClass controlLinkEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package
   * URI value.
   * <p>
   * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also performs initialization of the package, or
   * returns the registered package, if one already exists. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.ietr.dftools.architecture.slam.link.LinkPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private LinkPackageImpl() {
    super(LinkPackage.eNS_URI, LinkFactory.eINSTANCE);
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
   * This method is used to initialize {@link LinkPackage#eINSTANCE} when that field is accessed. Clients should not invoke it directly. Instead, they should
   * simply access that field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static LinkPackage init() {
    if (LinkPackageImpl.isInited) {
      return (LinkPackage) EPackage.Registry.INSTANCE.getEPackage(LinkPackage.eNS_URI);
    }

    // Obtain or create and register package
    final LinkPackageImpl theLinkPackage = (LinkPackageImpl) (EPackage.Registry.INSTANCE.get(LinkPackage.eNS_URI) instanceof LinkPackageImpl
        ? EPackage.Registry.INSTANCE.get(LinkPackage.eNS_URI)
        : new LinkPackageImpl());

    LinkPackageImpl.isInited = true;

    // Obtain or create and register interdependencies
    final SlamPackageImpl theSlamPackage = (SlamPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI) instanceof SlamPackageImpl
        ? EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI)
        : SlamPackage.eINSTANCE);
    final ComponentPackageImpl theComponentPackage = (ComponentPackageImpl) (EPackage.Registry.INSTANCE
        .getEPackage(ComponentPackage.eNS_URI) instanceof ComponentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI)
            : ComponentPackage.eINSTANCE);
    final AttributesPackageImpl theAttributesPackage = (AttributesPackageImpl) (EPackage.Registry.INSTANCE
        .getEPackage(AttributesPackage.eNS_URI) instanceof AttributesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributesPackage.eNS_URI)
            : AttributesPackage.eINSTANCE);

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
   *
   * @generated
   */
  @Override
  public EClass getLink() {
    return this.linkEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getLink_SourceInterface() {
    return (EReference) this.linkEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getLink_DestinationInterface() {
    return (EReference) this.linkEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getLink_SourceComponentInstance() {
    return (EReference) this.linkEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EReference getLink_DestinationComponentInstance() {
    return (EReference) this.linkEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EAttribute getLink_Uuid() {
    return (EAttribute) this.linkEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EAttribute getLink_Directed() {
    return (EAttribute) this.linkEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getDataLink() {
    return this.dataLinkEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public EClass getControlLink() {
    return this.controlLinkEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public LinkFactory getLinkFactory() {
    return (LinkFactory) getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but its first. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @generated
   */
  public void createPackageContents() {
    if (this.isCreated) {
      return;
    }
    this.isCreated = true;

    // Create classes and their features
    this.linkEClass = createEClass(LinkPackage.LINK);
    createEReference(this.linkEClass, LinkPackage.LINK__SOURCE_INTERFACE);
    createEReference(this.linkEClass, LinkPackage.LINK__DESTINATION_INTERFACE);
    createEReference(this.linkEClass, LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE);
    createEReference(this.linkEClass, LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE);
    createEAttribute(this.linkEClass, LinkPackage.LINK__UUID);
    createEAttribute(this.linkEClass, LinkPackage.LINK__DIRECTED);

    this.dataLinkEClass = createEClass(LinkPackage.DATA_LINK);

    this.controlLinkEClass = createEClass(LinkPackage.CONTROL_LINK);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any invocation but its first. <!--
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
    setName(LinkPackage.eNAME);
    setNsPrefix(LinkPackage.eNS_PREFIX);
    setNsURI(LinkPackage.eNS_URI);

    // Obtain other dependent packages
    final ComponentPackage theComponentPackage = (ComponentPackage) EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI);
    final SlamPackage theSlamPackage = (SlamPackage) EPackage.Registry.INSTANCE.getEPackage(SlamPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    this.dataLinkEClass.getESuperTypes().add(getLink());
    this.controlLinkEClass.getESuperTypes().add(getLink());

    // Initialize classes and features; add operations and parameters
    initEClass(this.linkEClass, Link.class, "Link", EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE, EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLink_SourceInterface(), theComponentPackage.getComInterface(), null, "sourceInterface", null, 1, 1, Link.class,
        !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_COMPOSITE, EPackageImpl.IS_RESOLVE_PROXIES,
        !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEReference(getLink_DestinationInterface(), theComponentPackage.getComInterface(), null, "destinationInterface", null, 1, 1, Link.class,
        !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_COMPOSITE, EPackageImpl.IS_RESOLVE_PROXIES,
        !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEReference(getLink_SourceComponentInstance(), theSlamPackage.getComponentInstance(), null, "sourceComponentInstance", null, 1, 1, Link.class,
        !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_COMPOSITE, EPackageImpl.IS_RESOLVE_PROXIES,
        !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEReference(getLink_DestinationComponentInstance(), theSlamPackage.getComponentInstance(), null, "destinationComponentInstance", null, 1, 1, Link.class,
        !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_COMPOSITE, EPackageImpl.IS_RESOLVE_PROXIES,
        !EPackageImpl.IS_UNSETTABLE, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);
    initEAttribute(getLink_Uuid(), this.ecorePackage.getEString(), "uuid", null, 1, 1, Link.class, !EPackageImpl.IS_TRANSIENT, !EPackageImpl.IS_VOLATILE,
        EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE, !EPackageImpl.IS_DERIVED,
        EPackageImpl.IS_ORDERED);
    initEAttribute(getLink_Directed(), this.ecorePackage.getEBoolean(), "directed", "false", 1, 1, Link.class, !EPackageImpl.IS_TRANSIENT,
        !EPackageImpl.IS_VOLATILE, EPackageImpl.IS_CHANGEABLE, !EPackageImpl.IS_UNSETTABLE, !EPackageImpl.IS_ID, EPackageImpl.IS_UNIQUE,
        !EPackageImpl.IS_DERIVED, EPackageImpl.IS_ORDERED);

    initEClass(this.dataLinkEClass, DataLink.class, "DataLink", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
        EPackageImpl.IS_GENERATED_INSTANCE_CLASS);

    initEClass(this.controlLinkEClass, ControlLink.class, "ControlLink", !EPackageImpl.IS_ABSTRACT, !EPackageImpl.IS_INTERFACE,
        EPackageImpl.IS_GENERATED_INSTANCE_CLASS);
  }

} // LinkPackageImpl
