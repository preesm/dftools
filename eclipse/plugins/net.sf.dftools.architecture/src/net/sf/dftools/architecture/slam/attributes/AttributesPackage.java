/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.attributes;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see net.sf.dftools.architecture.slam.attributes.AttributesFactory
 * @model kind="package"
 * @generated
 */
public interface AttributesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "attributes";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://net.sf.dftools/architecture/slam/attributes";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "attributes";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AttributesPackage eINSTANCE = net.sf.dftools.architecture.slam.attributes.impl.AttributesPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.sf.dftools.architecture.slam.attributes.impl.VLNVImpl <em>VLNV</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.dftools.architecture.slam.attributes.impl.VLNVImpl
	 * @see net.sf.dftools.architecture.slam.attributes.impl.AttributesPackageImpl#getVLNV()
	 * @generated
	 */
	int VLNV = 0;

	/**
	 * The feature id for the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VLNV__VENDOR = 0;

	/**
	 * The feature id for the '<em><b>Library</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VLNV__LIBRARY = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VLNV__NAME = 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VLNV__VERSION = 3;

	/**
	 * The number of structural features of the '<em>VLNV</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VLNV_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link net.sf.dftools.architecture.slam.attributes.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.dftools.architecture.slam.attributes.impl.ParameterImpl
	 * @see net.sf.dftools.architecture.slam.attributes.impl.AttributesPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link net.sf.dftools.architecture.slam.attributes.VLNV <em>VLNV</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VLNV</em>'.
	 * @see net.sf.dftools.architecture.slam.attributes.VLNV
	 * @generated
	 */
	EClass getVLNV();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.dftools.architecture.slam.attributes.VLNV#getVendor <em>Vendor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor</em>'.
	 * @see net.sf.dftools.architecture.slam.attributes.VLNV#getVendor()
	 * @see #getVLNV()
	 * @generated
	 */
	EAttribute getVLNV_Vendor();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.dftools.architecture.slam.attributes.VLNV#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Library</em>'.
	 * @see net.sf.dftools.architecture.slam.attributes.VLNV#getLibrary()
	 * @see #getVLNV()
	 * @generated
	 */
	EAttribute getVLNV_Library();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.dftools.architecture.slam.attributes.VLNV#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.dftools.architecture.slam.attributes.VLNV#getName()
	 * @see #getVLNV()
	 * @generated
	 */
	EAttribute getVLNV_Name();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.dftools.architecture.slam.attributes.VLNV#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see net.sf.dftools.architecture.slam.attributes.VLNV#getVersion()
	 * @see #getVLNV()
	 * @generated
	 */
	EAttribute getVLNV_Version();

	/**
	 * Returns the meta object for class '{@link net.sf.dftools.architecture.slam.attributes.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see net.sf.dftools.architecture.slam.attributes.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.dftools.architecture.slam.attributes.Parameter#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see net.sf.dftools.architecture.slam.attributes.Parameter#getKey()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Key();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.dftools.architecture.slam.attributes.Parameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see net.sf.dftools.architecture.slam.attributes.Parameter#getValue()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AttributesFactory getAttributesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link net.sf.dftools.architecture.slam.attributes.impl.VLNVImpl <em>VLNV</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.dftools.architecture.slam.attributes.impl.VLNVImpl
		 * @see net.sf.dftools.architecture.slam.attributes.impl.AttributesPackageImpl#getVLNV()
		 * @generated
		 */
		EClass VLNV = eINSTANCE.getVLNV();

		/**
		 * The meta object literal for the '<em><b>Vendor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VLNV__VENDOR = eINSTANCE.getVLNV_Vendor();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VLNV__LIBRARY = eINSTANCE.getVLNV_Library();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VLNV__NAME = eINSTANCE.getVLNV_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VLNV__VERSION = eINSTANCE.getVLNV_Version();

		/**
		 * The meta object literal for the '{@link net.sf.dftools.architecture.slam.attributes.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.dftools.architecture.slam.attributes.impl.ParameterImpl
		 * @see net.sf.dftools.architecture.slam.attributes.impl.AttributesPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__KEY = eINSTANCE.getParameter_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

	}

} //AttributesPackage
