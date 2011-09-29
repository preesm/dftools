/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam;

import net.sf.dftools.architecture.slam.component.Component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Component Holder</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link net.sf.dftools.architecture.slam.ComponentHolder#getComponents
 * <em>Components</em>}</li>
 * </ul>
 * </p>
 * 
 * @see net.sf.dftools.architecture.slam.SlamPackage#getComponentHolder()
 * @model
 * @generated
 */
public interface ComponentHolder extends EObject {
	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link net.sf.dftools.architecture.slam.component.Component}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Components</em>' containment reference
	 *         list.
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getComponentHolder_Components()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Component> getComponents();

} // ComponentHolder
