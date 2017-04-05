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
import org.ietr.dftools.architecture.slam.attributes.VLNV;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Com
 * Interface</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.ComInterface#getComponent
 * <em>Component</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.component.ComInterface#getBusType
 * <em>Bus Type</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.component.ComInterface#getName
 * <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComInterface()
 * @model
 * @generated
 */
public interface ComInterface extends EObject {
	/**
	 * Returns the value of the '<em><b>Component</b></em>' container reference.
	 * It is bidirectional and its opposite is '
	 * {@link org.ietr.dftools.architecture.slam.component.Component#getInterfaces
	 * <em>Interfaces</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' container reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Component</em>' container reference.
	 * @see #setComponent(Component)
	 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComInterface_Component()
	 * @see org.ietr.dftools.architecture.slam.component.Component#getInterfaces
	 * @model opposite="interfaces" required="true" transient="false"
	 * @generated
	 */
	Component getComponent();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.component.ComInterface#getComponent
	 * <em>Component</em>}' container reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Component</em>' container reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(Component value);

	/**
	 * Returns the value of the '<em><b>Bus Type</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bus Type</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Bus Type</em>' containment reference.
	 * @see #setBusType(VLNV)
	 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComInterface_BusType()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VLNV getBusType();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.component.ComInterface#getBusType
	 * <em>Bus Type</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Bus Type</em>' containment
	 *            reference.
	 * @see #getBusType()
	 * @generated
	 */
	void setBusType(VLNV value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. The default
	 * value is <code>""</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComInterface_Name()
	 * @model default="" id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.component.ComInterface#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // ComInterface
