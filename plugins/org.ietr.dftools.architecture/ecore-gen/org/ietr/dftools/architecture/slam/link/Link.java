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
package org.ietr.dftools.architecture.slam.link;

import org.eclipse.emf.ecore.EObject;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.component.ComInterface;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Link</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.ietr.dftools.architecture.slam.link.Link#getSourceInterface
 * <em> Source Interface</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.link.Link#getDestinationInterface
 * <em>Destination Interface</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.link.Link#getSourceComponentInstance
 * <em>Source Component Instance</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.link.Link#getDestinationComponentInstance
 * <em>Destination Component Instance</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.link.Link#getUuid
 * <em>Uuid</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.link.Link#isDirected
 * <em>Directed </em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ietr.dftools.architecture.slam.link.LinkPackage#getLink()
 * @model abstract="true"
 * @generated
 */
public interface Link extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Interface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Interface</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Source Interface</em>' reference.
	 * @see #setSourceInterface(ComInterface)
	 * @see org.ietr.dftools.architecture.slam.link.LinkPackage#getLink_SourceInterface()
	 * @model required="true"
	 * @generated
	 */
	ComInterface getSourceInterface();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getSourceInterface
	 * <em>Source Interface</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Source Interface</em>' reference.
	 * @see #getSourceInterface()
	 * @generated
	 */
	void setSourceInterface(ComInterface value);

	/**
	 * Returns the value of the '<em><b>Destination Interface</b></em>'
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination Interface</em>' reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Destination Interface</em>' reference.
	 * @see #setDestinationInterface(ComInterface)
	 * @see org.ietr.dftools.architecture.slam.link.LinkPackage#getLink_DestinationInterface()
	 * @model required="true"
	 * @generated
	 */
	ComInterface getDestinationInterface();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getDestinationInterface
	 * <em>Destination Interface</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Destination Interface</em>'
	 *            reference.
	 * @see #getDestinationInterface()
	 * @generated
	 */
	void setDestinationInterface(ComInterface value);

	/**
	 * Returns the value of the '<em><b>Source Component Instance</b></em>'
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Component Instance</em>' reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Source Component Instance</em>' reference.
	 * @see #setSourceComponentInstance(ComponentInstance)
	 * @see org.ietr.dftools.architecture.slam.link.LinkPackage#getLink_SourceComponentInstance()
	 * @model required="true"
	 * @generated
	 */
	ComponentInstance getSourceComponentInstance();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getSourceComponentInstance
	 * <em>Source Component Instance</em>}' reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Source Component Instance</em>'
	 *            reference.
	 * @see #getSourceComponentInstance()
	 * @generated
	 */
	void setSourceComponentInstance(ComponentInstance value);

	/**
	 * Returns the value of the '<em><b>Destination Component Instance</b></em>'
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination Component Instance</em>' reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Destination Component Instance</em>'
	 *         reference.
	 * @see #setDestinationComponentInstance(ComponentInstance)
	 * @see org.ietr.dftools.architecture.slam.link.LinkPackage#getLink_DestinationComponentInstance()
	 * @model required="true"
	 * @generated
	 */
	ComponentInstance getDestinationComponentInstance();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getDestinationComponentInstance
	 * <em>Destination Component Instance</em>}' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Destination Component Instance</em>'
	 *            reference.
	 * @see #getDestinationComponentInstance()
	 * @generated
	 */
	void setDestinationComponentInstance(ComponentInstance value);

	/**
	 * Returns the value of the '<em><b>Uuid</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uuid</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Uuid</em>' attribute.
	 * @see #setUuid(String)
	 * @see org.ietr.dftools.architecture.slam.link.LinkPackage#getLink_Uuid()
	 * @model required="true"
	 * @generated
	 */
	String getUuid();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#getUuid
	 * <em>Uuid</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Uuid</em>' attribute.
	 * @see #getUuid()
	 * @generated
	 */
	void setUuid(String value);

	/**
	 * Returns the value of the '<em><b>Directed</b></em>' attribute. The
	 * default value is <code>"false"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Directed</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Directed</em>' attribute.
	 * @see #setDirected(boolean)
	 * @see org.ietr.dftools.architecture.slam.link.LinkPackage#getLink_Directed()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isDirected();

	/**
	 * Sets the value of the '
	 * {@link org.ietr.dftools.architecture.slam.link.Link#isDirected
	 * <em>Directed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @param value
	 *            the new value of the '<em>Directed</em>' attribute.
	 * @see #isDirected()
	 * @generated
	 */
	void setDirected(boolean value);

} // Link
