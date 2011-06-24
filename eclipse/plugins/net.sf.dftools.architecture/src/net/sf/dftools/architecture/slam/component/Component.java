/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.component;

import net.sf.dftools.architecture.slam.ComponentInstance;
import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.ParameterizedElement;
import net.sf.dftools.architecture.slam.VLNVedElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.component.Component#getInterfaces <em>Interfaces</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.component.Component#getInstances <em>Instances</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.component.Component#getRefinement <em>Refinement</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComponent()
 * @model abstract="true"
 * @generated
 */
public interface Component extends VLNVedElement, ParameterizedElement {
	/**
	 * Returns the value of the '<em><b>Interfaces</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.dftools.architecture.slam.component.ComInterface}.
	 * It is bidirectional and its opposite is '{@link net.sf.dftools.architecture.slam.component.ComInterface#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interfaces</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interfaces</em>' containment reference list.
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComponent_Interfaces()
	 * @see net.sf.dftools.architecture.slam.component.ComInterface#getComponent
	 * @model opposite="component" containment="true"
	 * @generated
	 */
	EList<ComInterface> getInterfaces();

	/**
	 * Returns the value of the '<em><b>Instances</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.dftools.architecture.slam.ComponentInstance}.
	 * It is bidirectional and its opposite is '{@link net.sf.dftools.architecture.slam.ComponentInstance#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instances</em>' containment reference list.
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComponent_Instances()
	 * @see net.sf.dftools.architecture.slam.ComponentInstance#getComponent
	 * @model opposite="component" containment="true"
	 * @generated
	 */
	EList<ComponentInstance> getInstances();

	/**
	 * Returns the value of the '<em><b>Refinement</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refinement</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refinement</em>' reference.
	 * @see #setRefinement(Design)
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComponent_Refinement()
	 * @model
	 * @generated
	 */
	Design getRefinement();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.component.Component#getRefinement <em>Refinement</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refinement</em>' reference.
	 * @see #getRefinement()
	 * @generated
	 */
	void setRefinement(Design value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	boolean isHierarchical();

} // Component
