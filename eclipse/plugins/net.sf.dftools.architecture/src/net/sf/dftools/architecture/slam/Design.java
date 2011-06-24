/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam;

import net.sf.dftools.architecture.slam.link.Link;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Design</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.Design#getComponentInstances <em>Component Instances</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.Design#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign()
 * @model
 * @generated
 */
public interface Design extends VLNVedElement {
	/**
	 * Returns the value of the '<em><b>Component Instances</b></em>' reference list.
	 * The list contents are of type {@link net.sf.dftools.architecture.slam.ComponentInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Instances</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Instances</em>' reference list.
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign_ComponentInstances()
	 * @model keys="instanceName" ordered="false"
	 * @generated
	 */
	EList<ComponentInstance> getComponentInstances();

	/**
	 * Returns the value of the '<em><b>Links</b></em>' reference list.
	 * The list contents are of type {@link net.sf.dftools.architecture.slam.link.Link}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Links</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' reference list.
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign_Links()
	 * @model
	 * @generated
	 */
	EList<Link> getLinks();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nameRequired="true"
	 * @generated
	 */
	void containsComponentInstance(String name);

} // Design
