/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam;

import net.sf.dftools.architecture.slam.attributes.VLNV;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>VLN Ved Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.VLNVedElement#getVlnv <em>Vlnv</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.architecture.slam.SlamPackage#getVLNVedElement()
 * @model
 * @generated
 */
public interface VLNVedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Vlnv</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vlnv</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vlnv</em>' containment reference.
	 * @see #setVlnv(VLNV)
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getVLNVedElement_Vlnv()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VLNV getVlnv();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.VLNVedElement#getVlnv <em>Vlnv</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Vlnv</em>' containment reference.
	 * @see #getVlnv()
	 * @generated
	 */
	void setVlnv(VLNV value);

} // VLNVedElement
