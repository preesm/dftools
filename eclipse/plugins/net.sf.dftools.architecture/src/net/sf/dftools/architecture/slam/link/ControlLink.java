/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.link;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Control Link</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link net.sf.dftools.architecture.slam.link.ControlLink#getSetupTime
 * <em>Setup Time</em>}</li>
 * </ul>
 * </p>
 * 
 * @see net.sf.dftools.architecture.slam.link.LinkPackage#getControlLink()
 * @model
 * @generated
 */
public interface ControlLink extends Link {

	/**
	 * Returns the value of the '<em><b>Setup Time</b></em>' attribute. The
	 * default value is <code>"0"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Setup Time</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Setup Time</em>' attribute.
	 * @see #setSetupTime(int)
	 * @see net.sf.dftools.architecture.slam.link.LinkPackage#getControlLink_SetupTime()
	 * @model default="0"
	 * @generated
	 */
	int getSetupTime();

	/**
	 * Sets the value of the '
	 * {@link net.sf.dftools.architecture.slam.link.ControlLink#getSetupTime
	 * <em>Setup Time</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Setup Time</em>' attribute.
	 * @see #getSetupTime()
	 * @generated
	 */
	void setSetupTime(int value);
} // ControlLink
