/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.component;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Com Node</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link net.sf.dftools.architecture.slam.component.ComNode#isParallel <em>
 * Parallel</em>}</li>
 * <li>{@link net.sf.dftools.architecture.slam.component.ComNode#getSpeed <em>
 * Speed</em>}</li>
 * </ul>
 * </p>
 * 
 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComNode()
 * @model
 * @generated
 */
public interface ComNode extends Component {

	/**
	 * Returns the value of the '<em><b>Parallel</b></em>' attribute. The
	 * default value is <code>"true"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parallel</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parallel</em>' attribute.
	 * @see #setParallel(boolean)
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComNode_Parallel()
	 * @model default="true" required="true"
	 * @generated
	 */
	boolean isParallel();

	/**
	 * Sets the value of the '
	 * {@link net.sf.dftools.architecture.slam.component.ComNode#isParallel
	 * <em>Parallel</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Parallel</em>' attribute.
	 * @see #isParallel()
	 * @generated
	 */
	void setParallel(boolean value);

	/**
	 * Returns the value of the '<em><b>Speed</b></em>' attribute. The default
	 * value is <code>"1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Speed</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Speed</em>' attribute.
	 * @see #setSpeed(float)
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComNode_Speed()
	 * @model default="1" required="true"
	 * @generated
	 */
	float getSpeed();

	/**
	 * Sets the value of the '
	 * {@link net.sf.dftools.architecture.slam.component.ComNode#getSpeed
	 * <em>Speed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Speed</em>' attribute.
	 * @see #getSpeed()
	 * @generated
	 */
	void setSpeed(float value);
} // ComNode
