/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.component.Operator#getOperatorType <em>Operator Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getOperator()
 * @model
 * @generated
 */
public interface Operator extends Component {

	/**
	 * Returns the value of the '<em><b>Operator Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator Type</em>' attribute.
	 * @see #isSetOperatorType()
	 * @see #unsetOperatorType()
	 * @see #setOperatorType(String)
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getOperator_OperatorType()
	 * @model unsettable="true" required="true"
	 * @generated
	 */
	String getOperatorType();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.component.Operator#getOperatorType <em>Operator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator Type</em>' attribute.
	 * @see #isSetOperatorType()
	 * @see #unsetOperatorType()
	 * @see #getOperatorType()
	 * @generated
	 */
	void setOperatorType(String value);

	/**
	 * Unsets the value of the '{@link net.sf.dftools.architecture.slam.component.Operator#getOperatorType <em>Operator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOperatorType()
	 * @see #getOperatorType()
	 * @see #setOperatorType(String)
	 * @generated
	 */
	void unsetOperatorType();

	/**
	 * Returns whether the value of the '{@link net.sf.dftools.architecture.slam.component.Operator#getOperatorType <em>Operator Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Operator Type</em>' attribute is set.
	 * @see #unsetOperatorType()
	 * @see #getOperatorType()
	 * @see #setOperatorType(String)
	 * @generated
	 */
	boolean isSetOperatorType();
} // Operator
