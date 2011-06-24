/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.link;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.link.DataLink#isDirected <em>Directed</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.architecture.slam.link.LinkPackage#getDataLink()
 * @model
 * @generated
 */
public interface DataLink extends Link {
	/**
	 * Returns the value of the '<em><b>Directed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Directed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Directed</em>' attribute.
	 * @see #setDirected(boolean)
	 * @see net.sf.dftools.architecture.slam.link.LinkPackage#getDataLink_Directed()
	 * @model required="true"
	 * @generated
	 */
	boolean isDirected();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.link.DataLink#isDirected <em>Directed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Directed</em>' attribute.
	 * @see #isDirected()
	 * @generated
	 */
	void setDirected(boolean value);

} // DataLink
