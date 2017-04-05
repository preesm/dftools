/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ietr.dftools.architecture.slam.component;

import org.eclipse.emf.common.util.EList;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.VLNVedElement;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Component</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.Component#getInterfaces
 * <em>Interfaces</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.component.Component#getInstances
 * <em>Instances</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.Component#getRefinements
 * <em>Refinements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComponent()
 * @model
 * @generated
 */
public interface Component extends VLNVedElement, ParameterizedElement {
	/**
	 * Returns the value of the '<em><b>Interfaces</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.ietr.dftools.architecture.slam.component.ComInterface}. It is
	 * bidirectional and its opposite is '
	 * {@link org.ietr.dftools.architecture.slam.component.ComInterface#getComponent
	 * <em>Component</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interfaces</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Interfaces</em>' containment reference
	 *         list.
	 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComponent_Interfaces()
	 * @see org.ietr.dftools.architecture.slam.component.ComInterface#getComponent
	 * @model opposite="component" containment="true"
	 * @generated
	 */
	EList<ComInterface> getInterfaces();

	/**
	 * Returns the value of the '<em><b>Instances</b></em>' reference list. The
	 * list contents are of type
	 * {@link org.ietr.dftools.architecture.slam.ComponentInstance}. It is
	 * bidirectional and its opposite is '
	 * {@link org.ietr.dftools.architecture.slam.ComponentInstance#getComponent
	 * <em>Component</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instances</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Instances</em>' reference list.
	 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComponent_Instances()
	 * @see org.ietr.dftools.architecture.slam.ComponentInstance#getComponent
	 * @model opposite="component"
	 * @generated
	 */
	EList<ComponentInstance> getInstances();

	/**
	 * Returns the value of the '<em><b>Refinements</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.ietr.dftools.architecture.slam.Design}. It is bidirectional and
	 * its opposite is '
	 * {@link org.ietr.dftools.architecture.slam.Design#getRefined
	 * <em>Refined</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refinements</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Refinements</em>' containment reference
	 *         list.
	 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComponent_Refinements()
	 * @see org.ietr.dftools.architecture.slam.Design#getRefined
	 * @model opposite="refined" containment="true"
	 * @generated
	 */
	EList<Design> getRefinements();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" nameRequired="true"
	 * @generated
	 */
	ComInterface getInterface(String name);

} // Component
