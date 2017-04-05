/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ietr.dftools.architecture.slam.link.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.ietr.dftools.architecture.slam.link.ControlLink;
import org.ietr.dftools.architecture.slam.link.DataLink;
import org.ietr.dftools.architecture.slam.link.LinkFactory;
import org.ietr.dftools.architecture.slam.link.LinkPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class LinkFactoryImpl extends EFactoryImpl implements LinkFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static LinkFactory init() {
		try {
			LinkFactory theLinkFactory = (LinkFactory) EPackage.Registry.INSTANCE
					.getEFactory("http://net.sf.dftools/architecture/slam/link");
			if (theLinkFactory != null) {
				return theLinkFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LinkFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public LinkFactoryImpl() {
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
		case LinkPackage.DATA_LINK:
			return createDataLink();
		case LinkPackage.CONTROL_LINK:
			return createControlLink();
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
	public DataLink createDataLink() {
		DataLinkImpl dataLink = new DataLinkImpl();
		return dataLink;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ControlLink createControlLink() {
		ControlLinkImpl controlLink = new ControlLinkImpl();
		return controlLink;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public LinkPackage getLinkPackage() {
		return (LinkPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LinkPackage getPackage() {
		return LinkPackage.eINSTANCE;
	}

} // LinkFactoryImpl
