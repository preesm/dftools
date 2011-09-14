/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam;

import net.sf.dftools.architecture.slam.attributes.Parameter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Parameterized Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.ParameterizedElement#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.architecture.slam.SlamPackage#getParameterizedElement()
 * @model
 * @generated
 */
public interface ParameterizedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link net.sf.dftools.architecture.slam.attributes.Parameter}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameters</em>' containment reference
	 *         list.
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getParameterizedElement_Parameters()
	 * @model containment="true" keys="key"
	 * @generated
	 */
	EList<Parameter> getParameters();

} // ParameterizedElement
