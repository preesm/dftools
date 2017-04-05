/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ietr.dftools.architecture.slam.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.ietr.dftools.architecture.slam.ComponentHolder;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.SlamFactory;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.VLNVedElement;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class SlamFactoryImpl extends EFactoryImpl implements SlamFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static SlamFactory init() {
		try {
			SlamFactory theSlamFactory = (SlamFactory) EPackage.Registry.INSTANCE
					.getEFactory("http://net.sf.dftools/architecture/slam");
			if (theSlamFactory != null) {
				return theSlamFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SlamFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public SlamFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case SlamPackage.DESIGN:
			return createDesign();
		case SlamPackage.COMPONENT_INSTANCE:
			return createComponentInstance();
		case SlamPackage.VLN_VED_ELEMENT:
			return createVLNVedElement();
		case SlamPackage.PARAMETERIZED_ELEMENT:
			return createParameterizedElement();
		case SlamPackage.COMPONENT_HOLDER:
			return createComponentHolder();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName()
					+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Design createDesign() {
		DesignImpl design = new DesignImpl();
		return design;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ComponentInstance createComponentInstance() {
		ComponentInstanceImpl componentInstance = new ComponentInstanceImpl();
		return componentInstance;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VLNVedElement createVLNVedElement() {
		VLNVedElementImpl vlnVedElement = new VLNVedElementImpl();
		return vlnVedElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ParameterizedElement createParameterizedElement() {
		ParameterizedElementImpl parameterizedElement = new ParameterizedElementImpl();
		return parameterizedElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ComponentHolder createComponentHolder() {
		ComponentHolderImpl componentHolder = new ComponentHolderImpl();
		return componentHolder;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SlamPackage getSlamPackage() {
		return (SlamPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SlamPackage getPackage() {
		return SlamPackage.eINSTANCE;
	}

} // SlamFactoryImpl
