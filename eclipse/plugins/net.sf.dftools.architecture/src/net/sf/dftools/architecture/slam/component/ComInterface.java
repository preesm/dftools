/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.component;

import net.sf.dftools.architecture.slam.attributes.VLNV;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Com Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.component.ComInterface#getComponent <em>Component</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.component.ComInterface#getBusType <em>Bus Type</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.component.ComInterface#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComInterface()
 * @model
 * @generated
 */
public interface ComInterface extends EObject {
	/**
	 * Returns the value of the '<em><b>Component</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link net.sf.dftools.architecture.slam.component.Component#getInterfaces <em>Interfaces</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' container reference.
	 * @see #setComponent(Component)
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComInterface_Component()
	 * @see net.sf.dftools.architecture.slam.component.Component#getInterfaces
	 * @model opposite="interfaces" required="true" transient="false"
	 * @generated
	 */
	Component getComponent();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.component.ComInterface#getComponent <em>Component</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' container reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(Component value);

	/**
	 * Returns the value of the '<em><b>Bus Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bus Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bus Type</em>' containment reference.
	 * @see #setBusType(VLNV)
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComInterface_BusType()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VLNV getBusType();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.component.ComInterface#getBusType <em>Bus Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bus Type</em>' containment reference.
	 * @see #getBusType()
	 * @generated
	 */
	void setBusType(VLNV value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComInterface_Name()
	 * @model default="" id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.component.ComInterface#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // ComInterface
