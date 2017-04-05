/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
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
package org.ietr.dftools.architecture.slam.component;

import org.eclipse.emf.ecore.EObject;
import org.ietr.dftools.architecture.slam.ComponentInstance;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Hierarchy Port</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort#getExternalInterface
 * <em>External Interface</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort#getInternalInterface
 * <em>Internal Interface</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort#getInternalComponentInstance
 * <em>Internal Component Instance</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getHierarchyPort()
 * @model
 * @generated
 */
public interface HierarchyPort extends EObject {
	/**
	 * Returns the value of the '<em><b>External Interface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External Interface</em>' reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>External Interface</em>' reference.
	 * @see #setExternalInterface(ComInterface)
	 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getHierarchyPort_ExternalInterface()
	 * @model required="true"
	 * @generated
	 */
	ComInterface getExternalInterface();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort#getExternalInterface
	 * <em>External Interface</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>External Interface</em>' reference.
	 * @see #getExternalInterface()
	 * @generated
	 */
	void setExternalInterface(ComInterface value);

	/**
	 * Returns the value of the '<em><b>Internal Interface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Internal Interface</em>' reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Internal Interface</em>' reference.
	 * @see #setInternalInterface(ComInterface)
	 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getHierarchyPort_InternalInterface()
	 * @model required="true"
	 * @generated
	 */
	ComInterface getInternalInterface();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort#getInternalInterface
	 * <em>Internal Interface</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Internal Interface</em>' reference.
	 * @see #getInternalInterface()
	 * @generated
	 */
	void setInternalInterface(ComInterface value);

	/**
	 * Returns the value of the '<em><b>Internal Component Instance</b></em>'
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Internal Component Instance</em>' reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Internal Component Instance</em>'
	 *         reference.
	 * @see #setInternalComponentInstance(ComponentInstance)
	 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getHierarchyPort_InternalComponentInstance()
	 * @model required="true"
	 * @generated
	 */
	ComponentInstance getInternalComponentInstance();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort#getInternalComponentInstance
	 * <em>Internal Component Instance</em>}' reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Internal Component Instance</em>'
	 *            reference.
	 * @see #getInternalComponentInstance()
	 * @generated
	 */
	void setInternalComponentInstance(ComponentInstance value);

} // HierarchyPort
