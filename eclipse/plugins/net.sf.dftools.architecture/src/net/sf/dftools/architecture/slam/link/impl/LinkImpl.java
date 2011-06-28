/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.architecture.slam.link.impl;

import net.sf.dftools.architecture.slam.ComponentInstance;
import net.sf.dftools.architecture.slam.component.ComInterface;
import net.sf.dftools.architecture.slam.impl.ParameterizedElementImpl;
import net.sf.dftools.architecture.slam.link.Link;
import net.sf.dftools.architecture.slam.link.LinkPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.dftools.architecture.slam.link.impl.LinkImpl#getSourceInterface <em>Source Interface</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.link.impl.LinkImpl#getDestinationInterface <em>Destination Interface</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.link.impl.LinkImpl#getSourceComponentInstance <em>Source Component Instance</em>}</li>
 *   <li>{@link net.sf.dftools.architecture.slam.link.impl.LinkImpl#getDestinationComponentInstance <em>Destination Component Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class LinkImpl extends ParameterizedElementImpl implements Link {
	/**
	 * The cached value of the '{@link #getSourceInterface() <em>Source Interface</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceInterface()
	 * @generated
	 * @ordered
	 */
	protected ComInterface sourceInterface;

	/**
	 * The cached value of the '{@link #getDestinationInterface() <em>Destination Interface</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinationInterface()
	 * @generated
	 * @ordered
	 */
	protected ComInterface destinationInterface;

	/**
	 * The cached value of the '{@link #getSourceComponentInstance() <em>Source Component Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceComponentInstance()
	 * @generated
	 * @ordered
	 */
	protected ComponentInstance sourceComponentInstance;

	/**
	 * The cached value of the '{@link #getDestinationComponentInstance() <em>Destination Component Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinationComponentInstance()
	 * @generated
	 * @ordered
	 */
	protected ComponentInstance destinationComponentInstance;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LinkPackage.Literals.LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComInterface getSourceInterface() {
		if (sourceInterface != null && sourceInterface.eIsProxy()) {
			InternalEObject oldSourceInterface = (InternalEObject)sourceInterface;
			sourceInterface = (ComInterface)eResolveProxy(oldSourceInterface);
			if (sourceInterface != oldSourceInterface) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LinkPackage.LINK__SOURCE_INTERFACE, oldSourceInterface, sourceInterface));
			}
		}
		return sourceInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComInterface basicGetSourceInterface() {
		return sourceInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceInterface(ComInterface newSourceInterface) {
		ComInterface oldSourceInterface = sourceInterface;
		sourceInterface = newSourceInterface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__SOURCE_INTERFACE, oldSourceInterface, sourceInterface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComInterface getDestinationInterface() {
		if (destinationInterface != null && destinationInterface.eIsProxy()) {
			InternalEObject oldDestinationInterface = (InternalEObject)destinationInterface;
			destinationInterface = (ComInterface)eResolveProxy(oldDestinationInterface);
			if (destinationInterface != oldDestinationInterface) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LinkPackage.LINK__DESTINATION_INTERFACE, oldDestinationInterface, destinationInterface));
			}
		}
		return destinationInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComInterface basicGetDestinationInterface() {
		return destinationInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationInterface(ComInterface newDestinationInterface) {
		ComInterface oldDestinationInterface = destinationInterface;
		destinationInterface = newDestinationInterface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__DESTINATION_INTERFACE, oldDestinationInterface, destinationInterface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance getSourceComponentInstance() {
		if (sourceComponentInstance != null && sourceComponentInstance.eIsProxy()) {
			InternalEObject oldSourceComponentInstance = (InternalEObject)sourceComponentInstance;
			sourceComponentInstance = (ComponentInstance)eResolveProxy(oldSourceComponentInstance);
			if (sourceComponentInstance != oldSourceComponentInstance) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE, oldSourceComponentInstance, sourceComponentInstance));
			}
		}
		return sourceComponentInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance basicGetSourceComponentInstance() {
		return sourceComponentInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceComponentInstance(ComponentInstance newSourceComponentInstance) {
		ComponentInstance oldSourceComponentInstance = sourceComponentInstance;
		sourceComponentInstance = newSourceComponentInstance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE, oldSourceComponentInstance, sourceComponentInstance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance getDestinationComponentInstance() {
		if (destinationComponentInstance != null && destinationComponentInstance.eIsProxy()) {
			InternalEObject oldDestinationComponentInstance = (InternalEObject)destinationComponentInstance;
			destinationComponentInstance = (ComponentInstance)eResolveProxy(oldDestinationComponentInstance);
			if (destinationComponentInstance != oldDestinationComponentInstance) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE, oldDestinationComponentInstance, destinationComponentInstance));
			}
		}
		return destinationComponentInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance basicGetDestinationComponentInstance() {
		return destinationComponentInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationComponentInstance(ComponentInstance newDestinationComponentInstance) {
		ComponentInstance oldDestinationComponentInstance = destinationComponentInstance;
		destinationComponentInstance = newDestinationComponentInstance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE, oldDestinationComponentInstance, destinationComponentInstance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LinkPackage.LINK__SOURCE_INTERFACE:
				if (resolve) return getSourceInterface();
				return basicGetSourceInterface();
			case LinkPackage.LINK__DESTINATION_INTERFACE:
				if (resolve) return getDestinationInterface();
				return basicGetDestinationInterface();
			case LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE:
				if (resolve) return getSourceComponentInstance();
				return basicGetSourceComponentInstance();
			case LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE:
				if (resolve) return getDestinationComponentInstance();
				return basicGetDestinationComponentInstance();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LinkPackage.LINK__SOURCE_INTERFACE:
				setSourceInterface((ComInterface)newValue);
				return;
			case LinkPackage.LINK__DESTINATION_INTERFACE:
				setDestinationInterface((ComInterface)newValue);
				return;
			case LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE:
				setSourceComponentInstance((ComponentInstance)newValue);
				return;
			case LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE:
				setDestinationComponentInstance((ComponentInstance)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LinkPackage.LINK__SOURCE_INTERFACE:
				setSourceInterface((ComInterface)null);
				return;
			case LinkPackage.LINK__DESTINATION_INTERFACE:
				setDestinationInterface((ComInterface)null);
				return;
			case LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE:
				setSourceComponentInstance((ComponentInstance)null);
				return;
			case LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE:
				setDestinationComponentInstance((ComponentInstance)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LinkPackage.LINK__SOURCE_INTERFACE:
				return sourceInterface != null;
			case LinkPackage.LINK__DESTINATION_INTERFACE:
				return destinationInterface != null;
			case LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE:
				return sourceComponentInstance != null;
			case LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE:
				return destinationComponentInstance != null;
		}
		return super.eIsSet(featureID);
	}

} //LinkImpl
