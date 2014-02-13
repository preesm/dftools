/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ietr.dftools.architecture.slam.link;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.ietr.dftools.architecture.slam.link.LinkFactory
 * @model kind="package"
 * @generated
 */
public interface LinkPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "link";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://net.sf.dftools/architecture/slam/link";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "link";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	LinkPackage eINSTANCE = org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl
			.init();

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl <em>Link</em>}
	 * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.ietr.dftools.architecture.slam.link.impl.LinkImpl
	 * @see org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 0;

	/**
	 * The feature id for the '<em><b>Source Interface</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__SOURCE_INTERFACE = 0;

	/**
	 * The feature id for the '<em><b>Destination Interface</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__DESTINATION_INTERFACE = 1;

	/**
	 * The feature id for the '<em><b>Source Component Instance</b></em>'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__SOURCE_COMPONENT_INSTANCE = 2;

	/**
	 * The feature id for the '<em><b>Destination Component Instance</b></em>'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__DESTINATION_COMPONENT_INSTANCE = 3;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__UUID = 4;

	/**
	 * The feature id for the '<em><b>Directed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__DIRECTED = 5;

	/**
	 * The number of structural features of the '<em>Link</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.link.impl.DataLinkImpl
	 * <em>Data Link</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.ietr.dftools.architecture.slam.link.impl.DataLinkImpl
	 * @see org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl#getDataLink()
	 * @generated
	 */
	int DATA_LINK = 1;

	/**
	 * The feature id for the '<em><b>Source Interface</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_LINK__SOURCE_INTERFACE = LINK__SOURCE_INTERFACE;

	/**
	 * The feature id for the '<em><b>Destination Interface</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_LINK__DESTINATION_INTERFACE = LINK__DESTINATION_INTERFACE;

	/**
	 * The feature id for the '<em><b>Source Component Instance</b></em>'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_LINK__SOURCE_COMPONENT_INSTANCE = LINK__SOURCE_COMPONENT_INSTANCE;

	/**
	 * The feature id for the '<em><b>Destination Component Instance</b></em>'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_LINK__DESTINATION_COMPONENT_INSTANCE = LINK__DESTINATION_COMPONENT_INSTANCE;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_LINK__UUID = LINK__UUID;

	/**
	 * The feature id for the '<em><b>Directed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_LINK__DIRECTED = LINK__DIRECTED;

	/**
	 * The number of structural features of the '<em>Data Link</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_LINK_FEATURE_COUNT = LINK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.link.impl.ControlLinkImpl
	 * <em>Control Link</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.ietr.dftools.architecture.slam.link.impl.ControlLinkImpl
	 * @see org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl#getControlLink()
	 * @generated
	 */
	int CONTROL_LINK = 2;

	/**
	 * The feature id for the '<em><b>Source Interface</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LINK__SOURCE_INTERFACE = LINK__SOURCE_INTERFACE;

	/**
	 * The feature id for the '<em><b>Destination Interface</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LINK__DESTINATION_INTERFACE = LINK__DESTINATION_INTERFACE;

	/**
	 * The feature id for the '<em><b>Source Component Instance</b></em>'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LINK__SOURCE_COMPONENT_INSTANCE = LINK__SOURCE_COMPONENT_INSTANCE;

	/**
	 * The feature id for the '<em><b>Destination Component Instance</b></em>'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LINK__DESTINATION_COMPONENT_INSTANCE = LINK__DESTINATION_COMPONENT_INSTANCE;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LINK__UUID = LINK__UUID;

	/**
	 * The feature id for the '<em><b>Directed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LINK__DIRECTED = LINK__DIRECTED;

	/**
	 * The number of structural features of the '<em>Control Link</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_LINK_FEATURE_COUNT = LINK_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.link.Link <em>Link</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Link</em>'.
	 * @see org.ietr.dftools.architecture.slam.link.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getSourceInterface
	 * <em>Source Interface</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the reference '<em>Source Interface</em>'.
	 * @see org.ietr.dftools.architecture.slam.link.Link#getSourceInterface()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_SourceInterface();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getDestinationInterface
	 * <em>Destination Interface</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Destination Interface</em>
	 *         '.
	 * @see org.ietr.dftools.architecture.slam.link.Link#getDestinationInterface()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_DestinationInterface();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getSourceComponentInstance
	 * <em>Source Component Instance</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the reference '
	 *         <em>Source Component Instance</em>'.
	 * @see org.ietr.dftools.architecture.slam.link.Link#getSourceComponentInstance()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_SourceComponentInstance();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getDestinationComponentInstance
	 * <em>Destination Component Instance</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the reference '
	 *         <em>Destination Component Instance</em>'.
	 * @see org.ietr.dftools.architecture.slam.link.Link#getDestinationComponentInstance()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_DestinationComponentInstance();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getUuid <em>Uuid</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Uuid</em>'.
	 * @see org.ietr.dftools.architecture.slam.link.Link#getUuid()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_Uuid();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#isDirected
	 * <em>Directed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Directed</em>'.
	 * @see org.ietr.dftools.architecture.slam.link.Link#isDirected()
	 * @see #getLink()
	 * @generated
	 */
	EAttribute getLink_Directed();

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.link.DataLink <em>Data Link</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Data Link</em>'.
	 * @see org.ietr.dftools.architecture.slam.link.DataLink
	 * @generated
	 */
	EClass getDataLink();

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.link.ControlLink
	 * <em>Control Link</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Control Link</em>'.
	 * @see org.ietr.dftools.architecture.slam.link.ControlLink
	 * @generated
	 */
	EClass getControlLink();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LinkFactory getLinkFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl
		 * <em>Link</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.ietr.dftools.architecture.slam.link.impl.LinkImpl
		 * @see org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Source Interface</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LINK__SOURCE_INTERFACE = eINSTANCE.getLink_SourceInterface();

		/**
		 * The meta object literal for the '
		 * <em><b>Destination Interface</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LINK__DESTINATION_INTERFACE = eINSTANCE
				.getLink_DestinationInterface();

		/**
		 * The meta object literal for the '
		 * <em><b>Source Component Instance</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LINK__SOURCE_COMPONENT_INSTANCE = eINSTANCE
				.getLink_SourceComponentInstance();

		/**
		 * The meta object literal for the '
		 * <em><b>Destination Component Instance</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LINK__DESTINATION_COMPONENT_INSTANCE = eINSTANCE
				.getLink_DestinationComponentInstance();

		/**
		 * The meta object literal for the '<em><b>Uuid</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LINK__UUID = eINSTANCE.getLink_Uuid();

		/**
		 * The meta object literal for the '<em><b>Directed</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LINK__DIRECTED = eINSTANCE.getLink_Directed();

		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.link.impl.DataLinkImpl
		 * <em>Data Link</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.ietr.dftools.architecture.slam.link.impl.DataLinkImpl
		 * @see org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl#getDataLink()
		 * @generated
		 */
		EClass DATA_LINK = eINSTANCE.getDataLink();

		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.link.impl.ControlLinkImpl
		 * <em>Control Link</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.ietr.dftools.architecture.slam.link.impl.ControlLinkImpl
		 * @see org.ietr.dftools.architecture.slam.link.impl.LinkPackageImpl#getControlLink()
		 * @generated
		 */
		EClass CONTROL_LINK = eINSTANCE.getControlLink();

	}

} // LinkPackage
