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

import org.eclipse.emf.common.util.EList;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.VLNVedElement;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Component</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.ietr.dftools.architecture.slam.component.Component#getInterfaces <em>Interfaces</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.component.Component#getInstances <em>Instances</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.component.Component#getRefinements <em>Refinements</em>}</li>
 * </ul>
 *
 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComponent()
 * @model
 * @generated
 */
public interface Component extends VLNVedElement, ParameterizedElement {
  /**
   * Returns the value of the '<em><b>Interfaces</b></em>' containment reference list. The list contents are of type
   * {@link org.ietr.dftools.architecture.slam.component.ComInterface}. It is bidirectional and its opposite is
   * '{@link org.ietr.dftools.architecture.slam.component.ComInterface#getComponent <em>Component</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Interfaces</em>' containment reference list isn't clear, there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   *
   * @return the value of the '<em>Interfaces</em>' containment reference list.
   * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComponent_Interfaces()
   * @see org.ietr.dftools.architecture.slam.component.ComInterface#getComponent
   * @model opposite="component" containment="true"
   * @generated
   */
  EList<ComInterface> getInterfaces();

  /**
   * Returns the value of the '<em><b>Instances</b></em>' reference list. The list contents are of type
   * {@link org.ietr.dftools.architecture.slam.ComponentInstance}. It is bidirectional and its opposite is
   * '{@link org.ietr.dftools.architecture.slam.ComponentInstance#getComponent <em>Component</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Instances</em>' containment reference list isn't clear, there really should be more of a description here...
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
   * Returns the value of the '<em><b>Refinements</b></em>' containment reference list. The list contents are of type
   * {@link org.ietr.dftools.architecture.slam.Design}. It is bidirectional and its opposite is '{@link org.ietr.dftools.architecture.slam.Design#getRefined
   * <em>Refined</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Refinements</em>' containment reference list isn't clear, there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   *
   * @return the value of the '<em>Refinements</em>' containment reference list.
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
