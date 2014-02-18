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
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Component</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link net.sf.dftools.architecture.slam.component.Component#getInterfaces
 * <em>Interfaces</em>}</li>
 * <li>{@link net.sf.dftools.architecture.slam.component.Component#getInstances
 * <em>Instances</em>}</li>
 * <li>
 * {@link net.sf.dftools.architecture.slam.component.Component#getRefinements
 * <em>Refinements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComponent()
 * @model
 * @generated
 */
public interface Component extends VLNVedElement, ParameterizedElement {
	/**
	 * Returns the value of the '<em><b>Interfaces</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link net.sf.dftools.architecture.slam.component.ComInterface}. It is
	 * bidirectional and its opposite is '
	 * {@link net.sf.dftools.architecture.slam.component.ComInterface#getComponent
	 * <em>Component</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interfaces</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Interfaces</em>' containment reference
	 *         list.
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComponent_Interfaces()
	 * @see net.sf.dftools.architecture.slam.component.ComInterface#getComponent
	 * @model opposite="component" containment="true"
	 * @generated
	 */
	EList<ComInterface> getInterfaces();

	/**
	 * Returns the value of the '<em><b>Instances</b></em>' reference list. The
	 * list contents are of type
	 * {@link net.sf.dftools.architecture.slam.ComponentInstance}. It is
	 * bidirectional and its opposite is '
	 * {@link net.sf.dftools.architecture.slam.ComponentInstance#getComponent
	 * <em>Component</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instances</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Instances</em>' reference list.
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComponent_Instances()
	 * @see net.sf.dftools.architecture.slam.ComponentInstance#getComponent
	 * @model opposite="component"
	 * @generated
	 */
	EList<ComponentInstance> getInstances();

	/**
	 * Returns the value of the '<em><b>Refinements</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link net.sf.dftools.architecture.slam.Design}. It is bidirectional and
	 * its opposite is '
	 * {@link net.sf.dftools.architecture.slam.Design#getRefined
	 * <em>Refined</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refinements</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Refinements</em>' containment reference
	 *         list.
	 * @see net.sf.dftools.architecture.slam.component.ComponentPackage#getComponent_Refinements()
	 * @see net.sf.dftools.architecture.slam.Design#getRefined
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
