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
package org.ietr.dftools.architecture.slam;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.ietr.dftools.architecture.slam.SlamFactory
 * @model kind="package"
 * @generated
 */
public interface SlamPackage extends EPackage {
  /**
   * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  String eNAME = "slam";

  /**
   * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  String eNS_URI = "http://net.sf.dftools/architecture/slam";

  /**
   * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  String eNS_PREFIX = "slam";

  /**
   * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  SlamPackage eINSTANCE = org.ietr.dftools.architecture.slam.impl.SlamPackageImpl.init();

  /**
   * The meta object id for the '{@link org.ietr.dftools.architecture.slam.impl.VLNVedElementImpl <em>VLN Ved Element</em>}' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @see org.ietr.dftools.architecture.slam.impl.VLNVedElementImpl
   * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getVLNVedElement()
   * @generated
   */
  int VLN_VED_ELEMENT = 2;

  /**
   * The feature id for the '<em><b>Vlnv</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int VLN_VED_ELEMENT__VLNV = 0;

  /**
   * The number of structural features of the '<em>VLN Ved Element</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int VLN_VED_ELEMENT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.ietr.dftools.architecture.slam.impl.DesignImpl <em>Design</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   *
   * @see org.ietr.dftools.architecture.slam.impl.DesignImpl
   * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getDesign()
   * @generated
   */
  int DESIGN = 0;

  /**
   * The feature id for the '<em><b>Vlnv</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int DESIGN__VLNV = SlamPackage.VLN_VED_ELEMENT__VLNV;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int DESIGN__PARAMETERS = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Component Instances</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int DESIGN__COMPONENT_INSTANCES = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Links</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int DESIGN__LINKS = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Hierarchy Ports</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int DESIGN__HIERARCHY_PORTS = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Refined</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int DESIGN__REFINED = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Path</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int DESIGN__PATH = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Component Holder</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int DESIGN__COMPONENT_HOLDER = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 6;

  /**
   * The number of structural features of the '<em>Design</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int DESIGN_FEATURE_COUNT = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 7;

  /**
   * The meta object id for the '{@link org.ietr.dftools.architecture.slam.impl.ComponentInstanceImpl <em>Component Instance</em>}' class. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   *
   * @see org.ietr.dftools.architecture.slam.impl.ComponentInstanceImpl
   * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getComponentInstance()
   * @generated
   */
  int COMPONENT_INSTANCE = 1;

  /**
   * The meta object id for the '{@link org.ietr.dftools.architecture.slam.impl.ParameterizedElementImpl <em>Parameterized Element</em>}' class. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   *
   * @see org.ietr.dftools.architecture.slam.impl.ParameterizedElementImpl
   * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getParameterizedElement()
   * @generated
   */
  int PARAMETERIZED_ELEMENT = 3;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int PARAMETERIZED_ELEMENT__PARAMETERS = 0;

  /**
   * The number of structural features of the '<em>Parameterized Element</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int PARAMETERIZED_ELEMENT_FEATURE_COUNT = 1;

  /**
   * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int COMPONENT_INSTANCE__PARAMETERS = SlamPackage.PARAMETERIZED_ELEMENT__PARAMETERS;

  /**
   * The feature id for the '<em><b>Component</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int COMPONENT_INSTANCE__COMPONENT = SlamPackage.PARAMETERIZED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Instance Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int COMPONENT_INSTANCE__INSTANCE_NAME = SlamPackage.PARAMETERIZED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Component Instance</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int COMPONENT_INSTANCE_FEATURE_COUNT = SlamPackage.PARAMETERIZED_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.ietr.dftools.architecture.slam.impl.ComponentHolderImpl <em>Component Holder</em>}' class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   *
   * @see org.ietr.dftools.architecture.slam.impl.ComponentHolderImpl
   * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getComponentHolder()
   * @generated
   */
  int COMPONENT_HOLDER = 4;

  /**
   * The feature id for the '<em><b>Components</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int COMPONENT_HOLDER__COMPONENTS = 0;

  /**
   * The number of structural features of the '<em>Component Holder</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   * @ordered
   */
  int COMPONENT_HOLDER_FEATURE_COUNT = 1;

  /**
   * Returns the meta object for class ' {@link org.ietr.dftools.architecture.slam.Design <em>Design</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the meta object for class '<em>Design</em>'.
   * @see org.ietr.dftools.architecture.slam.Design
   * @generated
   */
  EClass getDesign();

  /**
   * Returns the meta object for the containment reference list ' {@link org.ietr.dftools.architecture.slam.Design#getComponentInstances <em>Component
   * Instances</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the meta object for the containment reference list ' <em>Component Instances</em>'.
   * @see org.ietr.dftools.architecture.slam.Design#getComponentInstances()
   * @see #getDesign()
   * @generated
   */
  EReference getDesign_ComponentInstances();

  /**
   * Returns the meta object for the containment reference list '{@link org.ietr.dftools.architecture.slam.Design#getLinks <em>Links</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   *
   * @return the meta object for the containment reference list '<em>Links</em>'.
   * @see org.ietr.dftools.architecture.slam.Design#getLinks()
   * @see #getDesign()
   * @generated
   */
  EReference getDesign_Links();

  /**
   * Returns the meta object for the containment reference list '{@link org.ietr.dftools.architecture.slam.Design#getHierarchyPorts <em>Hierarchy Ports</em>}'.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the meta object for the containment reference list '<em>Hierarchy Ports</em>'.
   * @see org.ietr.dftools.architecture.slam.Design#getHierarchyPorts()
   * @see #getDesign()
   * @generated
   */
  EReference getDesign_HierarchyPorts();

  /**
   * Returns the meta object for the container reference '{@link org.ietr.dftools.architecture.slam.Design#getRefined <em>Refined</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   *
   * @return the meta object for the container reference '<em>Refined</em>'.
   * @see org.ietr.dftools.architecture.slam.Design#getRefined()
   * @see #getDesign()
   * @generated
   */
  EReference getDesign_Refined();

  /**
   * Returns the meta object for the attribute '{@link org.ietr.dftools.architecture.slam.Design#getPath <em>Path</em>}'. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @return the meta object for the attribute '<em>Path</em>'.
   * @see org.ietr.dftools.architecture.slam.Design#getPath()
   * @see #getDesign()
   * @generated
   */
  EAttribute getDesign_Path();

  /**
   * Returns the meta object for the reference ' {@link org.ietr.dftools.architecture.slam.Design#getComponentHolder <em>Component Holder</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the meta object for the reference '<em>Component Holder</em>'.
   * @see org.ietr.dftools.architecture.slam.Design#getComponentHolder()
   * @see #getDesign()
   * @generated
   */
  EReference getDesign_ComponentHolder();

  /**
   * Returns the meta object for class '{@link org.ietr.dftools.architecture.slam.ComponentInstance <em>Component Instance</em>}'. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @return the meta object for class '<em>Component Instance</em>'.
   * @see org.ietr.dftools.architecture.slam.ComponentInstance
   * @generated
   */
  EClass getComponentInstance();

  /**
   * Returns the meta object for the reference '{@link org.ietr.dftools.architecture.slam.ComponentInstance#getComponent <em>Component</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the meta object for the reference '<em>Component</em>'.
   * @see org.ietr.dftools.architecture.slam.ComponentInstance#getComponent()
   * @see #getComponentInstance()
   * @generated
   */
  EReference getComponentInstance_Component();

  /**
   * Returns the meta object for the attribute '{@link org.ietr.dftools.architecture.slam.ComponentInstance#getInstanceName <em>Instance Name</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the meta object for the attribute '<em>Instance Name</em>'.
   * @see org.ietr.dftools.architecture.slam.ComponentInstance#getInstanceName()
   * @see #getComponentInstance()
   * @generated
   */
  EAttribute getComponentInstance_InstanceName();

  /**
   * Returns the meta object for class '{@link org.ietr.dftools.architecture.slam.VLNVedElement <em>VLN Ved Element</em>}'. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @return the meta object for class '<em>VLN Ved Element</em>'.
   * @see org.ietr.dftools.architecture.slam.VLNVedElement
   * @generated
   */
  EClass getVLNVedElement();

  /**
   * Returns the meta object for the containment reference '{@link org.ietr.dftools.architecture.slam.VLNVedElement#getVlnv <em>Vlnv</em>}'. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   *
   * @return the meta object for the containment reference '<em>Vlnv</em>'.
   * @see org.ietr.dftools.architecture.slam.VLNVedElement#getVlnv()
   * @see #getVLNVedElement()
   * @generated
   */
  EReference getVLNVedElement_Vlnv();

  /**
   * Returns the meta object for class '{@link org.ietr.dftools.architecture.slam.ParameterizedElement <em>Parameterized Element</em>}'. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   *
   * @return the meta object for class '<em>Parameterized Element</em>'.
   * @see org.ietr.dftools.architecture.slam.ParameterizedElement
   * @generated
   */
  EClass getParameterizedElement();

  /**
   * Returns the meta object for the containment reference list '{@link org.ietr.dftools.architecture.slam.ParameterizedElement#getParameters
   * <em>Parameters</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the meta object for the containment reference list '<em>Parameters</em>'.
   * @see org.ietr.dftools.architecture.slam.ParameterizedElement#getParameters()
   * @see #getParameterizedElement()
   * @generated
   */
  EReference getParameterizedElement_Parameters();

  /**
   * Returns the meta object for class '{@link org.ietr.dftools.architecture.slam.ComponentHolder <em>Component Holder</em>}'. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @return the meta object for class '<em>Component Holder</em>'.
   * @see org.ietr.dftools.architecture.slam.ComponentHolder
   * @generated
   */
  EClass getComponentHolder();

  /**
   * Returns the meta object for the containment reference list '{@link org.ietr.dftools.architecture.slam.ComponentHolder#getComponents <em>Components</em>}'.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the meta object for the containment reference list '<em>Components</em>'.
   * @see org.ietr.dftools.architecture.slam.ComponentHolder#getComponents()
   * @see #getComponentHolder()
   * @generated
   */
  EReference getComponentHolder_Components();

  /**
   * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @return the factory that creates the instances of the model.
   * @generated
   */
  SlamFactory getSlamFactory();

  /**
   * <!-- begin-user-doc --> Defines literals for the meta objects that represent
   * <ul>
   * <li>each class,</li>
   * <li>each feature of each class,</li>
   * <li>each enum,</li>
   * <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   *
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the ' {@link org.ietr.dftools.architecture.slam.impl.DesignImpl <em>Design</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see org.ietr.dftools.architecture.slam.impl.DesignImpl
     * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getDesign()
     * @generated
     */
    EClass DESIGN = SlamPackage.eINSTANCE.getDesign();

    /**
     * The meta object literal for the '<em><b>Component Instances</b></em>' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EReference DESIGN__COMPONENT_INSTANCES = SlamPackage.eINSTANCE.getDesign_ComponentInstances();

    /**
     * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EReference DESIGN__LINKS = SlamPackage.eINSTANCE.getDesign_Links();

    /**
     * The meta object literal for the '<em><b>Hierarchy Ports</b></em>' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EReference DESIGN__HIERARCHY_PORTS = SlamPackage.eINSTANCE.getDesign_HierarchyPorts();

    /**
     * The meta object literal for the '<em><b>Refined</b></em>' container reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EReference DESIGN__REFINED = SlamPackage.eINSTANCE.getDesign_Refined();

    /**
     * The meta object literal for the '<em><b>Path</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EAttribute DESIGN__PATH = SlamPackage.eINSTANCE.getDesign_Path();

    /**
     * The meta object literal for the '<em><b>Component Holder</b></em>' reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EReference DESIGN__COMPONENT_HOLDER = SlamPackage.eINSTANCE.getDesign_ComponentHolder();

    /**
     * The meta object literal for the '{@link org.ietr.dftools.architecture.slam.impl.ComponentInstanceImpl <em>Component Instance</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.ietr.dftools.architecture.slam.impl.ComponentInstanceImpl
     * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getComponentInstance()
     * @generated
     */
    EClass COMPONENT_INSTANCE = SlamPackage.eINSTANCE.getComponentInstance();

    /**
     * The meta object literal for the '<em><b>Component</b></em>' reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EReference COMPONENT_INSTANCE__COMPONENT = SlamPackage.eINSTANCE.getComponentInstance_Component();

    /**
     * The meta object literal for the '<em><b>Instance Name</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EAttribute COMPONENT_INSTANCE__INSTANCE_NAME = SlamPackage.eINSTANCE.getComponentInstance_InstanceName();

    /**
     * The meta object literal for the '{@link org.ietr.dftools.architecture.slam.impl.VLNVedElementImpl <em>VLN Ved Element</em>}' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see org.ietr.dftools.architecture.slam.impl.VLNVedElementImpl
     * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getVLNVedElement()
     * @generated
     */
    EClass VLN_VED_ELEMENT = SlamPackage.eINSTANCE.getVLNVedElement();

    /**
     * The meta object literal for the '<em><b>Vlnv</b></em>' containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EReference VLN_VED_ELEMENT__VLNV = SlamPackage.eINSTANCE.getVLNVedElement_Vlnv();

    /**
     * The meta object literal for the '{@link org.ietr.dftools.architecture.slam.impl.ParameterizedElementImpl <em>Parameterized Element</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.ietr.dftools.architecture.slam.impl.ParameterizedElementImpl
     * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getParameterizedElement()
     * @generated
     */
    EClass PARAMETERIZED_ELEMENT = SlamPackage.eINSTANCE.getParameterizedElement();

    /**
     * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EReference PARAMETERIZED_ELEMENT__PARAMETERS = SlamPackage.eINSTANCE.getParameterizedElement_Parameters();

    /**
     * The meta object literal for the '{@link org.ietr.dftools.architecture.slam.impl.ComponentHolderImpl <em>Component Holder</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.ietr.dftools.architecture.slam.impl.ComponentHolderImpl
     * @see org.ietr.dftools.architecture.slam.impl.SlamPackageImpl#getComponentHolder()
     * @generated
     */
    EClass COMPONENT_HOLDER = SlamPackage.eINSTANCE.getComponentHolder();

    /**
     * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    EReference COMPONENT_HOLDER__COMPONENTS = SlamPackage.eINSTANCE.getComponentHolder_Components();

  }

} // SlamPackage
