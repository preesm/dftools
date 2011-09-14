/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam;

import net.sf.dftools.architecture.slam.attributes.VLNV;
import net.sf.dftools.architecture.slam.component.Component;
import net.sf.dftools.architecture.slam.component.HierarchyPort;
import net.sf.dftools.architecture.slam.link.Link;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Design</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.Design#getComponentInstances <em>Component Instances</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.Design#getLinks <em>Links</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.Design#getHierarchyPorts <em>Hierarchy Ports</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.Design#getRefined <em>Refined</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.Design#getPath <em>Path</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.Design#getComponentHolder <em>Component Holder</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign()
 * @model
 * @generated
 */
public interface Design extends VLNVedElement, ParameterizedElement {
	/**
	 * Returns the value of the '<em><b>Component Instances</b></em>'
	 * containment reference list. The list contents are of type
	 * {@link net.sf.dftools.architecture.slam.ComponentInstance}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Instances</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Component Instances</em>' containment
	 *         reference list.
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign_ComponentInstances()
	 * @model containment="true" keys="instanceName" ordered="false"
	 * @generated
	 */
	EList<ComponentInstance> getComponentInstances();

	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.dftools.architecture.slam.link.Link}.
	 * <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Links</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign_Links()
	 * @model containment="true"
	 * @generated
	 */
	EList<Link> getLinks();

	/**
	 * Returns the value of the '<em><b>Hierarchy Ports</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link net.sf.dftools.architecture.slam.component.HierarchyPort}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hierarchy Ports</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Hierarchy Ports</em>' containment reference
	 *         list.
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign_HierarchyPorts()
	 * @model containment="true"
	 * @generated
	 */
	EList<HierarchyPort> getHierarchyPorts();

	/**
	 * Returns the value of the '<em><b>Refined</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link net.sf.dftools.architecture.slam.component.Component#getRefinement <em>Refinement</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the refined component does not exist, it is created.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refined</em>' container reference.
	 * @see #setRefined(Component)
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign_Refined()
	 * @see net.sf.dftools.architecture.slam.component.Component#getRefinement
	 * @model opposite="refinement" transient="false"
	 * @generated
	 */
	Component getRefined();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.Design#getRefined <em>Refined</em>}' container reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Refined</em>' container reference.
	 * @see #getRefined()
	 * @generated
	 */
	void setRefined(Component value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign_Path()
	 * @model
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.Design#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Component Holder</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Holder</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Holder</em>' reference.
	 * @see #setComponentHolder(ComponentHolder)
	 * @see net.sf.dftools.architecture.slam.SlamPackage#getDesign_ComponentHolder()
	 * @model required="true"
	 * @generated
	 */
	ComponentHolder getComponentHolder();

	/**
	 * Sets the value of the '{@link net.sf.dftools.architecture.slam.Design#getComponentHolder <em>Component Holder</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Component Holder</em>' reference.
	 * @see #getComponentHolder()
	 * @generated
	 */
	void setComponentHolder(ComponentHolder value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model required="true" nameRequired="true"
	 * @generated
	 */
	boolean containsComponentInstance(String name);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model required="true" nameRequired="true"
	 * @generated
	 */
	boolean containsComponent(VLNV name);

	/**
	 * <!-- begin-user-doc --> Gets the instances set in the design without
	 * managing the hierarchy <!-- end-user-doc -->
	 * @model required="true" nameRequired="true"
	 * @generated
	 */
	ComponentInstance getComponentInstance(String name);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model required="true" nameRequired="true" classRequired="true"
	 * @generated
	 */
	Component getComponent(VLNV name, EClass class_);

} // Design
