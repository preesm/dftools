/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam;

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
 * @see net.sf.dftools.architecture.slam.SlamFactory
 * @model kind="package"
 * @generated
 */
public interface SlamPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "slam";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://net.sf.dftools/architecture/slam";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.dftools.architecture.slam";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SlamPackage eINSTANCE = net.sf.dftools.architecture.slam.impl.SlamPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.sf.dftools.architecture.slam.impl.ComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.dftools.architecture.slam.impl.ComponentImpl
	 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 2;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__SIZE = 0;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.dftools.architecture.slam.impl.OperatorImpl <em>Operator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.dftools.architecture.slam.impl.OperatorImpl
	 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getOperator()
	 * @generated
	 */
	int OPERATOR = 0;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATOR__SIZE = COMPONENT__SIZE;

	/**
	 * The number of structural features of the '<em>Operator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATOR_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.dftools.architecture.slam.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.dftools.architecture.slam.impl.ConnectionImpl
	 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 1;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link net.sf.dftools.architecture.slam.impl.ComponentInstanceImpl <em>Component Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.dftools.architecture.slam.impl.ComponentInstanceImpl
	 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getComponentInstance()
	 * @generated
	 */
	int COMPONENT_INSTANCE = 3;

	/**
	 * The number of structural features of the '<em>Component Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_INSTANCE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link net.sf.dftools.architecture.slam.impl.UndirectedConnectionImpl <em>Undirected Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.dftools.architecture.slam.impl.UndirectedConnectionImpl
	 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getUndirectedConnection()
	 * @generated
	 */
	int UNDIRECTED_CONNECTION = 4;

	/**
	 * The number of structural features of the '<em>Undirected Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDIRECTED_CONNECTION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.dftools.architecture.slam.impl.DirectedConnectionImpl <em>Directed Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.dftools.architecture.slam.impl.DirectedConnectionImpl
	 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getDirectedConnection()
	 * @generated
	 */
	int DIRECTED_CONNECTION = 5;

	/**
	 * The number of structural features of the '<em>Directed Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTED_CONNECTION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link net.sf.dftools.architecture.slam.Operator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operator</em>'.
	 * @see net.sf.dftools.architecture.slam.Operator
	 * @generated
	 */
	EClass getOperator();

	/**
	 * Returns the meta object for class '{@link net.sf.dftools.architecture.slam.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see net.sf.dftools.architecture.slam.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for class '{@link net.sf.dftools.architecture.slam.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see net.sf.dftools.architecture.slam.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.dftools.architecture.slam.Component#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.dftools.architecture.slam.Component#getSize()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_Size();

	/**
	 * Returns the meta object for class '{@link net.sf.dftools.architecture.slam.ComponentInstance <em>Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component Instance</em>'.
	 * @see net.sf.dftools.architecture.slam.ComponentInstance
	 * @generated
	 */
	EClass getComponentInstance();

	/**
	 * Returns the meta object for class '{@link net.sf.dftools.architecture.slam.UndirectedConnection <em>Undirected Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Undirected Connection</em>'.
	 * @see net.sf.dftools.architecture.slam.UndirectedConnection
	 * @generated
	 */
	EClass getUndirectedConnection();

	/**
	 * Returns the meta object for class '{@link net.sf.dftools.architecture.slam.DirectedConnection <em>Directed Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Directed Connection</em>'.
	 * @see net.sf.dftools.architecture.slam.DirectedConnection
	 * @generated
	 */
	EClass getDirectedConnection();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SlamFactory getSlamFactory();

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
		 * The meta object literal for the '{@link net.sf.dftools.architecture.slam.impl.OperatorImpl <em>Operator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.dftools.architecture.slam.impl.OperatorImpl
		 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getOperator()
		 * @generated
		 */
		EClass OPERATOR = eINSTANCE.getOperator();

		/**
		 * The meta object literal for the '{@link net.sf.dftools.architecture.slam.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.dftools.architecture.slam.impl.ConnectionImpl
		 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '{@link net.sf.dftools.architecture.slam.impl.ComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.dftools.architecture.slam.impl.ComponentImpl
		 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getComponent()
		 * @generated
		 */
		EClass COMPONENT = eINSTANCE.getComponent();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__SIZE = eINSTANCE.getComponent_Size();

		/**
		 * The meta object literal for the '{@link net.sf.dftools.architecture.slam.impl.ComponentInstanceImpl <em>Component Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.dftools.architecture.slam.impl.ComponentInstanceImpl
		 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getComponentInstance()
		 * @generated
		 */
		EClass COMPONENT_INSTANCE = eINSTANCE.getComponentInstance();

		/**
		 * The meta object literal for the '{@link net.sf.dftools.architecture.slam.impl.UndirectedConnectionImpl <em>Undirected Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.dftools.architecture.slam.impl.UndirectedConnectionImpl
		 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getUndirectedConnection()
		 * @generated
		 */
		EClass UNDIRECTED_CONNECTION = eINSTANCE.getUndirectedConnection();

		/**
		 * The meta object literal for the '{@link net.sf.dftools.architecture.slam.impl.DirectedConnectionImpl <em>Directed Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.dftools.architecture.slam.impl.DirectedConnectionImpl
		 * @see net.sf.dftools.architecture.slam.impl.SlamPackageImpl#getDirectedConnection()
		 * @generated
		 */
		EClass DIRECTED_CONNECTION = eINSTANCE.getDirectedConnection();

	}

} //SlamPackage
