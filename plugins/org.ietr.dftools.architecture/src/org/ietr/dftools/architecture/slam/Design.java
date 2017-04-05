/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (%%DATE%%) :
 *
 * %%AUTHORS%%
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
package org.ietr.dftools.architecture.slam;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.ietr.dftools.architecture.slam.attributes.VLNV;
import org.ietr.dftools.architecture.slam.component.Component;
import org.ietr.dftools.architecture.slam.component.HierarchyPort;
import org.ietr.dftools.architecture.slam.link.Link;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Design</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.ietr.dftools.architecture.slam.Design#getComponentInstances <em>
 * Component Instances</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.Design#getLinks <em>Links</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.Design#getHierarchyPorts <em>
 * Hierarchy Ports</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.Design#getRefined <em>Refined
 * </em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.Design#getPath <em>Path</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.Design#getComponentHolder <em>
 * Component Holder</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.ietr.dftools.architecture.slam.SlamPackage#getDesign()
 * @model
 * @generated
 */
public interface Design extends VLNVedElement, ParameterizedElement {
	/**
	 * Returns the value of the '<em><b>Component Instances</b></em>'
	 * containment reference list. The list contents are of type
	 * {@link org.ietr.dftools.architecture.slam.ComponentInstance}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Instances</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Component Instances</em>' containment
	 *         reference list.
	 * @see org.ietr.dftools.architecture.slam.SlamPackage#getDesign_ComponentInstances()
	 * @model containment="true" keys="instanceName" ordered="false"
	 * @generated
	 */
	EList<ComponentInstance> getComponentInstances();

	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.ietr.dftools.architecture.slam.link.Link}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Links</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @see org.ietr.dftools.architecture.slam.SlamPackage#getDesign_Links()
	 * @model containment="true"
	 * @generated
	 */
	EList<Link> getLinks();

	/**
	 * Returns the value of the '<em><b>Hierarchy Ports</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hierarchy Ports</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Hierarchy Ports</em>' containment reference
	 *         list.
	 * @see org.ietr.dftools.architecture.slam.SlamPackage#getDesign_HierarchyPorts()
	 * @model containment="true"
	 * @generated
	 */
	EList<HierarchyPort> getHierarchyPorts();

	/**
	 * Returns the value of the '<em><b>Refined</b></em>' container reference.
	 * It is bidirectional and its opposite is '
	 * {@link org.ietr.dftools.architecture.slam.component.Component#getRefinements
	 * <em>Refinements</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the refined component does not exist, it is created.
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Refined</em>' container reference.
	 * @see #setRefined(Component)
	 * @see org.ietr.dftools.architecture.slam.SlamPackage#getDesign_Refined()
	 * @see org.ietr.dftools.architecture.slam.component.Component#getRefinements
	 * @model opposite="refinements" transient="false"
	 * @generated
	 */
	Component getRefined();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.Design#getRefined
	 * <em>Refined</em>}' container reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Refined</em>' container reference.
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
	 * @see org.ietr.dftools.architecture.slam.SlamPackage#getDesign_Path()
	 * @model
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.Design#getPath <em>Path</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Path</em>' attribute.
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
	 * 
	 * @return the value of the '<em>Component Holder</em>' reference.
	 * @see #setComponentHolder(ComponentHolder)
	 * @see org.ietr.dftools.architecture.slam.SlamPackage#getDesign_ComponentHolder()
	 * @model required="true"
	 * @generated
	 */
	ComponentHolder getComponentHolder();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.Design#getComponentHolder
	 * <em>Component Holder</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Component Holder</em>' reference.
	 * @see #getComponentHolder()
	 * @generated
	 */
	void setComponentHolder(ComponentHolder value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" nameRequired="true"
	 * @generated
	 */
	boolean containsComponentInstance(String name);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" nameRequired="true"
	 * @generated
	 */
	boolean containsComponent(VLNV name);

	/**
	 * <!-- begin-user-doc --> Gets the instances set in the design without
	 * managing the hierarchy <!-- end-user-doc -->
	 * 
	 * @model required="true" nameRequired="true"
	 * @generated
	 */
	ComponentInstance getComponentInstance(String name);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" nameRequired="true" classRequired="true"
	 * @generated
	 */
	Component getComponent(VLNV name, EClass class_);

} // Design
