/**
 */
package net.sf.dftools.util.impl;

import net.sf.dftools.util.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class UtilFactoryImpl extends EFactoryImpl implements UtilFactory {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UtilPackage getPackage() {
		return UtilPackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static UtilFactory init() {
		try {
			UtilFactory theUtilFactory = (UtilFactory) EPackage.Registry.INSTANCE
					.getEFactory("http://dftools.sf.net/model/2012/Util");
			if (theUtilFactory != null) {
				return theUtilFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UtilFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public UtilFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case UtilPackage.ATTRIBUTE:
			return createAttribute();
		case UtilPackage.WRAPPER_STRING:
			return createWrapperString();
		case UtilPackage.WRAPPER_XML:
			return createWrapperXml();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName()
					+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	@Override
	public Attribute createAttribute(String name, Object value) {
		AttributeImpl attribute = new AttributeImpl();
		attribute.setName(name);
		attribute.setPojoValue(value);
		return attribute;
	}

	@Override
	public Attribute createAttribute(String name, EObject value) {
		AttributeImpl attribute = new AttributeImpl();
		attribute.setName(name);
		attribute.setValue(value);
		return attribute;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public WrapperString createWrapperString() {
		WrapperStringImpl wrapperString = new WrapperStringImpl();
		return wrapperString;
	}

	@Override
	public WrapperString createWrapperString(String value) {
		WrapperStringImpl wrapperString = new WrapperStringImpl();
		wrapperString.setString(value);
		return wrapperString;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public WrapperXml createWrapperXml() {
		WrapperXmlImpl wrapperXml = new WrapperXmlImpl();
		return wrapperXml;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public UtilPackage getUtilPackage() {
		return (UtilPackage) getEPackage();
	}

} // UtilFactoryImpl
