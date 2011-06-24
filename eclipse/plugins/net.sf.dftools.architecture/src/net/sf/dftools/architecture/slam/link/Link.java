/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.link;

import net.sf.dftools.architecture.slam.ParameterizedElement;

import net.sf.dftools.architecture.slam.component.ComInterface;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.link.Link#getSource <em>Source</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.link.Link#getDestination <em>Destination</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.architecture.slam.link.LinkPackage#getLink()
 * @model abstract="true"
 * @generated
 */
public interface Link extends ParameterizedElement {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(ComInterface)
	 * @see net.sf.dftools.architecture.slam.link.LinkPackage#getLink_Source()
	 * @model required="true"
	 * @generated
	 */
	ComInterface getSource();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.link.Link#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(ComInterface value);

	/**
	 * Returns the value of the '<em><b>Destination</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination</em>' reference.
	 * @see #setDestination(ComInterface)
	 * @see net.sf.dftools.architecture.slam.link.LinkPackage#getLink_Destination()
	 * @model required="true"
	 * @generated
	 */
	ComInterface getDestination();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.link.Link#getDestination <em>Destination</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination</em>' reference.
	 * @see #getDestination()
	 * @generated
	 */
	void setDestination(ComInterface value);

} // Link
