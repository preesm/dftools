/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Attribute</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link net.sf.dftools.graph.Attribute#getName <em>Name</em>}</li>
 * <li>{@link net.sf.dftools.graph.Attribute#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @see net.sf.dftools.graph.GraphPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends EObject {

	String CUSTOM = "Custom";

	String FLAG = "Flag";

	String STRING = "String";

	String TYPE = "Type";

	String VALUE = "Value";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.dftools.graph.GraphPackage#getAttribute_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.sf.dftools.graph.Attribute#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(EObject)
	 * @see net.sf.dftools.graph.GraphPackage#getAttribute_Value()
	 * @model
	 * @generated
	 */
	EObject getValue();

	/**
	 * Sets the value of the '{@link net.sf.dftools.graph.Attribute#getValue
	 * <em>Value</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(EObject value);

} // Attribute
