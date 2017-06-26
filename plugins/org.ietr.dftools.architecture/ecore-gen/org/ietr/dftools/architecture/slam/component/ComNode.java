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

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Com Node</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.ietr.dftools.architecture.slam.component.ComNode#isParallel <em>Parallel</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.component.ComNode#getSpeed <em>Speed</em>}</li>
 * </ul>
 *
 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComNode()
 * @model
 * @generated
 */
public interface ComNode extends Component {

  /**
   * Returns the value of the '<em><b>Parallel</b></em>' attribute. The default value is <code>"true"</code>. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parallel</em>' attribute isn't clear, there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   *
   * @return the value of the '<em>Parallel</em>' attribute.
   * @see #setParallel(boolean)
   * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComNode_Parallel()
   * @model default="true" required="true"
   * @generated
   */
  boolean isParallel();

  /**
   * Sets the value of the ' {@link org.ietr.dftools.architecture.slam.component.ComNode#isParallel <em>Parallel</em>}' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @param value
   *          the new value of the '<em>Parallel</em>' attribute.
   * @see #isParallel()
   * @generated
   */
  void setParallel(boolean value);

  /**
   * Returns the value of the '<em><b>Speed</b></em>' attribute. The default value is <code>"1"</code>. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Speed</em>' attribute isn't clear, there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   *
   * @return the value of the '<em>Speed</em>' attribute.
   * @see #setSpeed(float)
   * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComNode_Speed()
   * @model default="1" required="true"
   * @generated
   */
  float getSpeed();

  /**
   * Sets the value of the '{@link org.ietr.dftools.architecture.slam.component.ComNode#getSpeed <em>Speed</em>}' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @param value
   *          the new value of the '<em>Speed</em>' attribute.
   * @see #getSpeed()
   * @generated
   */
  void setSpeed(float value);
} // ComNode
