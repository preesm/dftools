/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (%%DATE%%) :
 *
 * %%AUTHORS%%
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
package org.ietr.dftools.architecture.slam.link.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.component.ComInterface;
import org.ietr.dftools.architecture.slam.link.Link;
import org.ietr.dftools.architecture.slam.link.LinkPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Link</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getSourceInterface
 * <em>Source Interface</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getDestinationInterface
 * <em>Destination Interface</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getSourceComponentInstance
 * <em>Source Component Instance</em>}</li>
 * <li>
 * {@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getDestinationComponentInstance
 * <em>Destination Component Instance</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getUuid <em>
 * Uuid</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#isDirected
 * <em>Directed</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class LinkImpl extends EObjectImpl implements Link {
	/**
	 * The cached value of the '{@link #getSourceInterface()
	 * <em>Source Interface</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getSourceInterface()
	 * @generated
	 * @ordered
	 */
	protected ComInterface sourceInterface;

	/**
	 * The cached value of the '{@link #getDestinationInterface()
	 * <em>Destination Interface</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDestinationInterface()
	 * @generated
	 * @ordered
	 */
	protected ComInterface destinationInterface;

	/**
	 * The cached value of the '{@link #getSourceComponentInstance()
	 * <em>Source Component Instance</em>}' reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSourceComponentInstance()
	 * @generated
	 * @ordered
	 */
	protected ComponentInstance sourceComponentInstance;

	/**
	 * The cached value of the '{@link #getDestinationComponentInstance()
	 * <em>Destination Component Instance</em>}' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getDestinationComponentInstance()
	 * @generated
	 * @ordered
	 */
	protected ComponentInstance destinationComponentInstance;

	/**
	 * The default value of the '{@link #getUuid() <em>Uuid</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUuid()
	 * @generated
	 * @ordered
	 */
	protected static final String UUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUuid() <em>Uuid</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUuid()
	 * @generated
	 * @ordered
	 */
	protected String uuid = UUID_EDEFAULT;

	/**
	 * The default value of the '{@link #isDirected() <em>Directed</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDirected()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DIRECTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDirected() <em>Directed</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDirected()
	 * @generated
	 * @ordered
	 */
	protected boolean directed = DIRECTED_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected LinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LinkPackage.Literals.LINK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ComInterface getSourceInterface() {
		if (sourceInterface != null && sourceInterface.eIsProxy()) {
			InternalEObject oldSourceInterface = (InternalEObject) sourceInterface;
			sourceInterface = (ComInterface) eResolveProxy(oldSourceInterface);
			if (sourceInterface != oldSourceInterface) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LinkPackage.LINK__SOURCE_INTERFACE,
							oldSourceInterface, sourceInterface));
			}
		}
		return sourceInterface;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ComInterface basicGetSourceInterface() {
		return sourceInterface;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSourceInterface(ComInterface newSourceInterface) {
		ComInterface oldSourceInterface = sourceInterface;
		sourceInterface = newSourceInterface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkPackage.LINK__SOURCE_INTERFACE, oldSourceInterface,
					sourceInterface));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ComInterface getDestinationInterface() {
		if (destinationInterface != null && destinationInterface.eIsProxy()) {
			InternalEObject oldDestinationInterface = (InternalEObject) destinationInterface;
			destinationInterface = (ComInterface) eResolveProxy(oldDestinationInterface);
			if (destinationInterface != oldDestinationInterface) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LinkPackage.LINK__DESTINATION_INTERFACE,
							oldDestinationInterface, destinationInterface));
			}
		}
		return destinationInterface;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ComInterface basicGetDestinationInterface() {
		return destinationInterface;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDestinationInterface(ComInterface newDestinationInterface) {
		ComInterface oldDestinationInterface = destinationInterface;
		destinationInterface = newDestinationInterface;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkPackage.LINK__DESTINATION_INTERFACE,
					oldDestinationInterface, destinationInterface));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ComponentInstance getSourceComponentInstance() {
		if (sourceComponentInstance != null
				&& sourceComponentInstance.eIsProxy()) {
			InternalEObject oldSourceComponentInstance = (InternalEObject) sourceComponentInstance;
			sourceComponentInstance = (ComponentInstance) eResolveProxy(oldSourceComponentInstance);
			if (sourceComponentInstance != oldSourceComponentInstance) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE,
							oldSourceComponentInstance, sourceComponentInstance));
			}
		}
		return sourceComponentInstance;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ComponentInstance basicGetSourceComponentInstance() {
		return sourceComponentInstance;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSourceComponentInstance(
			ComponentInstance newSourceComponentInstance) {
		ComponentInstance oldSourceComponentInstance = sourceComponentInstance;
		sourceComponentInstance = newSourceComponentInstance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE,
					oldSourceComponentInstance, sourceComponentInstance));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ComponentInstance getDestinationComponentInstance() {
		if (destinationComponentInstance != null
				&& destinationComponentInstance.eIsProxy()) {
			InternalEObject oldDestinationComponentInstance = (InternalEObject) destinationComponentInstance;
			destinationComponentInstance = (ComponentInstance) eResolveProxy(oldDestinationComponentInstance);
			if (destinationComponentInstance != oldDestinationComponentInstance) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE,
							oldDestinationComponentInstance,
							destinationComponentInstance));
			}
		}
		return destinationComponentInstance;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ComponentInstance basicGetDestinationComponentInstance() {
		return destinationComponentInstance;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDestinationComponentInstance(
			ComponentInstance newDestinationComponentInstance) {
		ComponentInstance oldDestinationComponentInstance = destinationComponentInstance;
		destinationComponentInstance = newDestinationComponentInstance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE,
					oldDestinationComponentInstance,
					destinationComponentInstance));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getUuid() {
		return uuid;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setUuid(String newUuid) {
		String oldUuid = uuid;
		uuid = newUuid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkPackage.LINK__UUID, oldUuid, uuid));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isDirected() {
		return directed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDirected(boolean newDirected) {
		boolean oldDirected = directed;
		directed = newDirected;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LinkPackage.LINK__DIRECTED, oldDirected, directed));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LinkPackage.LINK__SOURCE_INTERFACE:
			if (resolve)
				return getSourceInterface();
			return basicGetSourceInterface();
		case LinkPackage.LINK__DESTINATION_INTERFACE:
			if (resolve)
				return getDestinationInterface();
			return basicGetDestinationInterface();
		case LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE:
			if (resolve)
				return getSourceComponentInstance();
			return basicGetSourceComponentInstance();
		case LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE:
			if (resolve)
				return getDestinationComponentInstance();
			return basicGetDestinationComponentInstance();
		case LinkPackage.LINK__UUID:
			return getUuid();
		case LinkPackage.LINK__DIRECTED:
			return isDirected();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LinkPackage.LINK__SOURCE_INTERFACE:
			setSourceInterface((ComInterface) newValue);
			return;
		case LinkPackage.LINK__DESTINATION_INTERFACE:
			setDestinationInterface((ComInterface) newValue);
			return;
		case LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE:
			setSourceComponentInstance((ComponentInstance) newValue);
			return;
		case LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE:
			setDestinationComponentInstance((ComponentInstance) newValue);
			return;
		case LinkPackage.LINK__UUID:
			setUuid((String) newValue);
			return;
		case LinkPackage.LINK__DIRECTED:
			setDirected((Boolean) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case LinkPackage.LINK__SOURCE_INTERFACE:
			setSourceInterface((ComInterface) null);
			return;
		case LinkPackage.LINK__DESTINATION_INTERFACE:
			setDestinationInterface((ComInterface) null);
			return;
		case LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE:
			setSourceComponentInstance((ComponentInstance) null);
			return;
		case LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE:
			setDestinationComponentInstance((ComponentInstance) null);
			return;
		case LinkPackage.LINK__UUID:
			setUuid(UUID_EDEFAULT);
			return;
		case LinkPackage.LINK__DIRECTED:
			setDirected(DIRECTED_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
		case LinkPackage.LINK__UUID:
			return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT
					.equals(uuid);
		case LinkPackage.LINK__DIRECTED:
			return directed != DIRECTED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (uuid: ");
		result.append(uuid);
		result.append(", directed: ");
		result.append(directed);
		result.append(')');
		return result.toString();
	}

} // LinkImpl
