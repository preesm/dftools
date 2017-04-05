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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.ietr.dftools.architecture.slam.SlamPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.ietr.dftools.architecture.slam.component.ComponentFactory
 * @model kind="package"
 * @generated
 */
public interface ComponentPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "component";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://net.sf.dftools/architecture/slam/component";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "component";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	ComponentPackage eINSTANCE = org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl.init();

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.component.impl.ComponentImpl
	 * <em>Component</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentImpl
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 0;

	/**
	 * The feature id for the '<em><b>Vlnv</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT__VLNV = SlamPackage.VLN_VED_ELEMENT__VLNV;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PARAMETERS = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT__INTERFACES = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT__INSTANCES = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Refinements</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT__REFINEMENTS = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Component</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = SlamPackage.VLN_VED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.component.impl.OperatorImpl
	 * <em>Operator</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.ietr.dftools.architecture.slam.component.impl.OperatorImpl
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getOperator()
	 * @generated
	 */
	int OPERATOR = 1;

	/**
	 * The feature id for the '<em><b>Vlnv</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPERATOR__VLNV = ComponentPackage.COMPONENT__VLNV;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPERATOR__PARAMETERS = ComponentPackage.COMPONENT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPERATOR__INTERFACES = ComponentPackage.COMPONENT__INTERFACES;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPERATOR__INSTANCES = ComponentPackage.COMPONENT__INSTANCES;

	/**
	 * The feature id for the '<em><b>Refinements</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPERATOR__REFINEMENTS = ComponentPackage.COMPONENT__REFINEMENTS;

	/**
	 * The number of structural features of the '<em>Operator</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int OPERATOR_FEATURE_COUNT = ComponentPackage.COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.component.impl.ComNodeImpl
	 * <em>Com Node</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComNodeImpl
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getComNode()
	 * @generated
	 */
	int COM_NODE = 2;

	/**
	 * The feature id for the '<em><b>Vlnv</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_NODE__VLNV = ComponentPackage.COMPONENT__VLNV;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_NODE__PARAMETERS = ComponentPackage.COMPONENT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_NODE__INTERFACES = ComponentPackage.COMPONENT__INTERFACES;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_NODE__INSTANCES = ComponentPackage.COMPONENT__INSTANCES;

	/**
	 * The feature id for the '<em><b>Refinements</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_NODE__REFINEMENTS = ComponentPackage.COMPONENT__REFINEMENTS;

	/**
	 * The feature id for the '<em><b>Parallel</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_NODE__PARALLEL = ComponentPackage.COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Speed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_NODE__SPEED = ComponentPackage.COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Com Node</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_NODE_FEATURE_COUNT = ComponentPackage.COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.component.impl.EnablerImpl
	 * <em>Enabler</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.ietr.dftools.architecture.slam.component.impl.EnablerImpl
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getEnabler()
	 * @generated
	 */
	int ENABLER = 3;

	/**
	 * The feature id for the '<em><b>Vlnv</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENABLER__VLNV = ComponentPackage.COMPONENT__VLNV;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENABLER__PARAMETERS = ComponentPackage.COMPONENT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENABLER__INTERFACES = ComponentPackage.COMPONENT__INTERFACES;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENABLER__INSTANCES = ComponentPackage.COMPONENT__INSTANCES;

	/**
	 * The feature id for the '<em><b>Refinements</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENABLER__REFINEMENTS = ComponentPackage.COMPONENT__REFINEMENTS;

	/**
	 * The number of structural features of the '<em>Enabler</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ENABLER_FEATURE_COUNT = ComponentPackage.COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.component.impl.DmaImpl
	 * <em>Dma</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.ietr.dftools.architecture.slam.component.impl.DmaImpl
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getDma()
	 * @generated
	 */
	int DMA = 4;

	/**
	 * The feature id for the '<em><b>Vlnv</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMA__VLNV = ComponentPackage.ENABLER__VLNV;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMA__PARAMETERS = ComponentPackage.ENABLER__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMA__INTERFACES = ComponentPackage.ENABLER__INTERFACES;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMA__INSTANCES = ComponentPackage.ENABLER__INSTANCES;

	/**
	 * The feature id for the '<em><b>Refinements</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMA__REFINEMENTS = ComponentPackage.ENABLER__REFINEMENTS;

	/**
	 * The feature id for the '<em><b>Setup Time</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMA__SETUP_TIME = ComponentPackage.ENABLER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dma</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMA_FEATURE_COUNT = ComponentPackage.ENABLER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.component.impl.MemImpl
	 * <em>Mem</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.ietr.dftools.architecture.slam.component.impl.MemImpl
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getMem()
	 * @generated
	 */
	int MEM = 5;

	/**
	 * The feature id for the '<em><b>Vlnv</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEM__VLNV = ComponentPackage.ENABLER__VLNV;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEM__PARAMETERS = ComponentPackage.ENABLER__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Interfaces</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEM__INTERFACES = ComponentPackage.ENABLER__INTERFACES;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEM__INSTANCES = ComponentPackage.ENABLER__INSTANCES;

	/**
	 * The feature id for the '<em><b>Refinements</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEM__REFINEMENTS = ComponentPackage.ENABLER__REFINEMENTS;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEM__SIZE = ComponentPackage.ENABLER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mem</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MEM_FEATURE_COUNT = ComponentPackage.ENABLER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.component.impl.HierarchyPortImpl
	 * <em>Hierarchy Port</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see org.ietr.dftools.architecture.slam.component.impl.HierarchyPortImpl
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getHierarchyPort()
	 * @generated
	 */
	int HIERARCHY_PORT = 6;

	/**
	 * The feature id for the '<em><b>External Interface</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_PORT__EXTERNAL_INTERFACE = 0;

	/**
	 * The feature id for the '<em><b>Internal Interface</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_PORT__INTERNAL_INTERFACE = 1;

	/**
	 * The feature id for the '<em><b>Internal Component Instance</b></em>'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_PORT__INTERNAL_COMPONENT_INSTANCE = 2;

	/**
	 * The number of structural features of the '<em>Hierarchy Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_PORT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '
	 * {@link org.ietr.dftools.architecture.slam.component.impl.ComInterfaceImpl
	 * <em>Com Interface</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComInterfaceImpl
	 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getComInterface()
	 * @generated
	 */
	int COM_INTERFACE = 7;

	/**
	 * The feature id for the '<em><b>Component</b></em>' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_INTERFACE__COMPONENT = 0;

	/**
	 * The feature id for the '<em><b>Bus Type</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_INTERFACE__BUS_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_INTERFACE__NAME = 2;

	/**
	 * The number of structural features of the '<em>Com Interface</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COM_INTERFACE_FEATURE_COUNT = 3;

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.component.Component
	 * <em>Component</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Component</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.ietr.dftools.architecture.slam.component.Component#getInterfaces
	 * <em>Interfaces</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '
	 *         <em>Interfaces</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Component#getInterfaces()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Interfaces();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.ietr.dftools.architecture.slam.component.Component#getInstances
	 * <em>Instances</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Instances</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Component#getInstances()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Instances();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.ietr.dftools.architecture.slam.component.Component#getRefinements
	 * <em>Refinements</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '
	 *         <em>Refinements</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Component#getRefinements()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Refinements();

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.component.Operator
	 * <em>Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Operator</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Operator
	 * @generated
	 */
	EClass getOperator();

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.component.ComNode <em>Com
	 * Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Com Node</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.ComNode
	 * @generated
	 */
	EClass getComNode();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.ietr.dftools.architecture.slam.component.ComNode#isParallel
	 * <em>Parallel</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Parallel</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.ComNode#isParallel()
	 * @see #getComNode()
	 * @generated
	 */
	EAttribute getComNode_Parallel();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.ietr.dftools.architecture.slam.component.ComNode#getSpeed
	 * <em>Speed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Speed</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.ComNode#getSpeed()
	 * @see #getComNode()
	 * @generated
	 */
	EAttribute getComNode_Speed();

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.component.Enabler
	 * <em>Enabler</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Enabler</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Enabler
	 * @generated
	 */
	EClass getEnabler();

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.component.Dma <em>Dma</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Dma</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Dma
	 * @generated
	 */
	EClass getDma();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.ietr.dftools.architecture.slam.component.Dma#getSetupTime
	 * <em>Setup Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Setup Time</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Dma#getSetupTime()
	 * @see #getDma()
	 * @generated
	 */
	EAttribute getDma_SetupTime();

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.component.Mem <em>Mem</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Mem</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Mem
	 * @generated
	 */
	EClass getMem();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.ietr.dftools.architecture.slam.component.Mem#getSize
	 * <em>Size</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.Mem#getSize()
	 * @see #getMem()
	 * @generated
	 */
	EAttribute getMem_Size();

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort
	 * <em>Hierarchy Port</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Hierarchy Port</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.HierarchyPort
	 * @generated
	 */
	EClass getHierarchyPort();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort#getExternalInterface
	 * <em>External Interface</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the meta object for the reference '<em>External Interface</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.HierarchyPort#getExternalInterface()
	 * @see #getHierarchyPort()
	 * @generated
	 */
	EReference getHierarchyPort_ExternalInterface();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort#getInternalInterface
	 * <em>Internal Interface</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the meta object for the reference '<em>Internal Interface</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.HierarchyPort#getInternalInterface()
	 * @see #getHierarchyPort()
	 * @generated
	 */
	EReference getHierarchyPort_InternalInterface();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.ietr.dftools.architecture.slam.component.HierarchyPort#getInternalComponentInstance
	 * <em>Internal Component Instance</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the reference ' <em>Internal Component
	 *         Instance</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.HierarchyPort#getInternalComponentInstance()
	 * @see #getHierarchyPort()
	 * @generated
	 */
	EReference getHierarchyPort_InternalComponentInstance();

	/**
	 * Returns the meta object for class '
	 * {@link org.ietr.dftools.architecture.slam.component.ComInterface <em>Com
	 * Interface</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Com Interface</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.ComInterface
	 * @generated
	 */
	EClass getComInterface();

	/**
	 * Returns the meta object for the container reference '
	 * {@link org.ietr.dftools.architecture.slam.component.ComInterface#getComponent
	 * <em>Component</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the container reference '<em>Component</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.ComInterface#getComponent()
	 * @see #getComInterface()
	 * @generated
	 */
	EReference getComInterface_Component();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.ietr.dftools.architecture.slam.component.ComInterface#getBusType
	 * <em>Bus Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Bus Type</em>
	 *         '.
	 * @see org.ietr.dftools.architecture.slam.component.ComInterface#getBusType()
	 * @see #getComInterface()
	 * @generated
	 */
	EReference getComInterface_BusType();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.ietr.dftools.architecture.slam.component.ComInterface#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.ietr.dftools.architecture.slam.component.ComInterface#getName()
	 * @see #getComInterface()
	 * @generated
	 */
	EAttribute getComInterface_Name();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ComponentFactory getComponentFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
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
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.component.impl.ComponentImpl
		 * <em>Component</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 *
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentImpl
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getComponent()
		 * @generated
		 */
		EClass COMPONENT = ComponentPackage.eINSTANCE.getComponent();

		/**
		 * The meta object literal for the '<em><b>Interfaces</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPONENT__INTERFACES = ComponentPackage.eINSTANCE.getComponent_Interfaces();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPONENT__INSTANCES = ComponentPackage.eINSTANCE.getComponent_Instances();

		/**
		 * The meta object literal for the '<em><b>Refinements</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPONENT__REFINEMENTS = ComponentPackage.eINSTANCE.getComponent_Refinements();

		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.component.impl.OperatorImpl
		 * <em>Operator</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 *
		 * @see org.ietr.dftools.architecture.slam.component.impl.OperatorImpl
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getOperator()
		 * @generated
		 */
		EClass OPERATOR = ComponentPackage.eINSTANCE.getOperator();

		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.component.impl.ComNodeImpl
		 * <em>Com Node</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 *
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComNodeImpl
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getComNode()
		 * @generated
		 */
		EClass COM_NODE = ComponentPackage.eINSTANCE.getComNode();

		/**
		 * The meta object literal for the '<em><b>Parallel</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute COM_NODE__PARALLEL = ComponentPackage.eINSTANCE.getComNode_Parallel();

		/**
		 * The meta object literal for the '<em><b>Speed</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute COM_NODE__SPEED = ComponentPackage.eINSTANCE.getComNode_Speed();

		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.component.impl.EnablerImpl
		 * <em>Enabler</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 *
		 * @see org.ietr.dftools.architecture.slam.component.impl.EnablerImpl
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getEnabler()
		 * @generated
		 */
		EClass ENABLER = ComponentPackage.eINSTANCE.getEnabler();

		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.component.impl.DmaImpl
		 * <em>Dma</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.ietr.dftools.architecture.slam.component.impl.DmaImpl
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getDma()
		 * @generated
		 */
		EClass DMA = ComponentPackage.eINSTANCE.getDma();

		/**
		 * The meta object literal for the '<em><b>Setup Time</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute DMA__SETUP_TIME = ComponentPackage.eINSTANCE.getDma_SetupTime();

		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.component.impl.MemImpl
		 * <em>Mem</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.ietr.dftools.architecture.slam.component.impl.MemImpl
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getMem()
		 * @generated
		 */
		EClass MEM = ComponentPackage.eINSTANCE.getMem();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MEM__SIZE = ComponentPackage.eINSTANCE.getMem_Size();

		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.component.impl.HierarchyPortImpl
		 * <em>Hierarchy Port</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see org.ietr.dftools.architecture.slam.component.impl.HierarchyPortImpl
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getHierarchyPort()
		 * @generated
		 */
		EClass HIERARCHY_PORT = ComponentPackage.eINSTANCE.getHierarchyPort();

		/**
		 * The meta object literal for the '<em><b>External Interface</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference HIERARCHY_PORT__EXTERNAL_INTERFACE = ComponentPackage.eINSTANCE.getHierarchyPort_ExternalInterface();

		/**
		 * The meta object literal for the '<em><b>Internal Interface</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference HIERARCHY_PORT__INTERNAL_INTERFACE = ComponentPackage.eINSTANCE.getHierarchyPort_InternalInterface();

		/**
		 * The meta object literal for the ' <em><b>Internal Component
		 * Instance</b></em>' reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated
		 */
		EReference HIERARCHY_PORT__INTERNAL_COMPONENT_INSTANCE = ComponentPackage.eINSTANCE.getHierarchyPort_InternalComponentInstance();

		/**
		 * The meta object literal for the '
		 * {@link org.ietr.dftools.architecture.slam.component.impl.ComInterfaceImpl
		 * <em>Com Interface</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComInterfaceImpl
		 * @see org.ietr.dftools.architecture.slam.component.impl.ComponentPackageImpl#getComInterface()
		 * @generated
		 */
		EClass COM_INTERFACE = ComponentPackage.eINSTANCE.getComInterface();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' container
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference COM_INTERFACE__COMPONENT = ComponentPackage.eINSTANCE.getComInterface_Component();

		/**
		 * The meta object literal for the '<em><b>Bus Type</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated
		 */
		EReference COM_INTERFACE__BUS_TYPE = ComponentPackage.eINSTANCE.getComInterface_BusType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute COM_INTERFACE__NAME = ComponentPackage.eINSTANCE.getComInterface_Name();

	}

} // ComponentPackage
