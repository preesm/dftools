/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
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
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Link</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getSourceInterface <em>Source Interface</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getDestinationInterface <em>Destination Interface</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getSourceComponentInstance <em>Source Component Instance</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getDestinationComponentInstance <em>Destination Component Instance</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#getUuid <em>Uuid</em>}</li>
 * <li>{@link org.ietr.dftools.architecture.slam.link.impl.LinkImpl#isDirected <em>Directed</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class LinkImpl extends EObjectImpl implements Link {
  /**
   * The cached value of the '{@link #getSourceInterface() <em>Source Interface</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getSourceInterface()
   * @generated
   * @ordered
   */
  protected ComInterface sourceInterface;

  /**
   * The cached value of the '{@link #getDestinationInterface() <em>Destination Interface</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getDestinationInterface()
   * @generated
   * @ordered
   */
  protected ComInterface destinationInterface;

  /**
   * The cached value of the '{@link #getSourceComponentInstance() <em>Source Component Instance</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getSourceComponentInstance()
   * @generated
   * @ordered
   */
  protected ComponentInstance sourceComponentInstance;

  /**
   * The cached value of the '{@link #getDestinationComponentInstance() <em>Destination Component Instance</em>}' reference. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   *
   * @see #getDestinationComponentInstance()
   * @generated
   * @ordered
   */
  protected ComponentInstance destinationComponentInstance;

  /**
   * The default value of the '{@link #getUuid() <em>Uuid</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getUuid()
   * @generated
   * @ordered
   */
  protected static final String UUID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUuid() <em>Uuid</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #getUuid()
   * @generated
   * @ordered
   */
  protected String uuid = LinkImpl.UUID_EDEFAULT;

  /**
   * The default value of the '{@link #isDirected() <em>Directed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #isDirected()
   * @generated
   * @ordered
   */
  protected static final boolean DIRECTED_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isDirected() <em>Directed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @see #isDirected()
   * @generated
   * @ordered
   */
  protected boolean directed = LinkImpl.DIRECTED_EDEFAULT;

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
    if ((this.sourceInterface != null) && this.sourceInterface.eIsProxy()) {
      final InternalEObject oldSourceInterface = (InternalEObject) this.sourceInterface;
      this.sourceInterface = (ComInterface) eResolveProxy(oldSourceInterface);
      if (this.sourceInterface != oldSourceInterface) {
        if (eNotificationRequired()) {
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, LinkPackage.LINK__SOURCE_INTERFACE, oldSourceInterface, this.sourceInterface));
        }
      }
    }
    return this.sourceInterface;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public ComInterface basicGetSourceInterface() {
    return this.sourceInterface;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setSourceInterface(final ComInterface newSourceInterface) {
    final ComInterface oldSourceInterface = this.sourceInterface;
    this.sourceInterface = newSourceInterface;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__SOURCE_INTERFACE, oldSourceInterface, this.sourceInterface));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public ComInterface getDestinationInterface() {
    if ((this.destinationInterface != null) && this.destinationInterface.eIsProxy()) {
      final InternalEObject oldDestinationInterface = (InternalEObject) this.destinationInterface;
      this.destinationInterface = (ComInterface) eResolveProxy(oldDestinationInterface);
      if (this.destinationInterface != oldDestinationInterface) {
        if (eNotificationRequired()) {
          eNotify(
              new ENotificationImpl(this, Notification.RESOLVE, LinkPackage.LINK__DESTINATION_INTERFACE, oldDestinationInterface, this.destinationInterface));
        }
      }
    }
    return this.destinationInterface;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public ComInterface basicGetDestinationInterface() {
    return this.destinationInterface;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setDestinationInterface(final ComInterface newDestinationInterface) {
    final ComInterface oldDestinationInterface = this.destinationInterface;
    this.destinationInterface = newDestinationInterface;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__DESTINATION_INTERFACE, oldDestinationInterface, this.destinationInterface));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public ComponentInstance getSourceComponentInstance() {
    if ((this.sourceComponentInstance != null) && this.sourceComponentInstance.eIsProxy()) {
      final InternalEObject oldSourceComponentInstance = (InternalEObject) this.sourceComponentInstance;
      this.sourceComponentInstance = (ComponentInstance) eResolveProxy(oldSourceComponentInstance);
      if (this.sourceComponentInstance != oldSourceComponentInstance) {
        if (eNotificationRequired()) {
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE, oldSourceComponentInstance,
              this.sourceComponentInstance));
        }
      }
    }
    return this.sourceComponentInstance;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public ComponentInstance basicGetSourceComponentInstance() {
    return this.sourceComponentInstance;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setSourceComponentInstance(final ComponentInstance newSourceComponentInstance) {
    final ComponentInstance oldSourceComponentInstance = this.sourceComponentInstance;
    this.sourceComponentInstance = newSourceComponentInstance;
    if (eNotificationRequired()) {
      eNotify(
          new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE, oldSourceComponentInstance, this.sourceComponentInstance));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public ComponentInstance getDestinationComponentInstance() {
    if ((this.destinationComponentInstance != null) && this.destinationComponentInstance.eIsProxy()) {
      final InternalEObject oldDestinationComponentInstance = (InternalEObject) this.destinationComponentInstance;
      this.destinationComponentInstance = (ComponentInstance) eResolveProxy(oldDestinationComponentInstance);
      if (this.destinationComponentInstance != oldDestinationComponentInstance) {
        if (eNotificationRequired()) {
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE, oldDestinationComponentInstance,
              this.destinationComponentInstance));
        }
      }
    }
    return this.destinationComponentInstance;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  public ComponentInstance basicGetDestinationComponentInstance() {
    return this.destinationComponentInstance;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setDestinationComponentInstance(final ComponentInstance newDestinationComponentInstance) {
    final ComponentInstance oldDestinationComponentInstance = this.destinationComponentInstance;
    this.destinationComponentInstance = newDestinationComponentInstance;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE, oldDestinationComponentInstance,
          this.destinationComponentInstance));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public String getUuid() {
    return this.uuid;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setUuid(final String newUuid) {
    final String oldUuid = this.uuid;
    this.uuid = newUuid;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__UUID, oldUuid, this.uuid));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public boolean isDirected() {
    return this.directed;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public void setDirected(final boolean newDirected) {
    final boolean oldDirected = this.directed;
    this.directed = newDirected;
    if (eNotificationRequired()) {
      eNotify(new ENotificationImpl(this, Notification.SET, LinkPackage.LINK__DIRECTED, oldDirected, this.directed));
    }
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated
   */
  @Override
  public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
    switch (featureID) {
      case LinkPackage.LINK__SOURCE_INTERFACE:
        if (resolve) {
          return getSourceInterface();
        }
        return basicGetSourceInterface();
      case LinkPackage.LINK__DESTINATION_INTERFACE:
        if (resolve) {
          return getDestinationInterface();
        }
        return basicGetDestinationInterface();
      case LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE:
        if (resolve) {
          return getSourceComponentInstance();
        }
        return basicGetSourceComponentInstance();
      case LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE:
        if (resolve) {
          return getDestinationComponentInstance();
        }
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
  public void eSet(final int featureID, final Object newValue) {
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
  public void eUnset(final int featureID) {
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
        setUuid(LinkImpl.UUID_EDEFAULT);
        return;
      case LinkPackage.LINK__DIRECTED:
        setDirected(LinkImpl.DIRECTED_EDEFAULT);
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
  public boolean eIsSet(final int featureID) {
    switch (featureID) {
      case LinkPackage.LINK__SOURCE_INTERFACE:
        return this.sourceInterface != null;
      case LinkPackage.LINK__DESTINATION_INTERFACE:
        return this.destinationInterface != null;
      case LinkPackage.LINK__SOURCE_COMPONENT_INSTANCE:
        return this.sourceComponentInstance != null;
      case LinkPackage.LINK__DESTINATION_COMPONENT_INSTANCE:
        return this.destinationComponentInstance != null;
      case LinkPackage.LINK__UUID:
        return LinkImpl.UUID_EDEFAULT == null ? this.uuid != null : !LinkImpl.UUID_EDEFAULT.equals(this.uuid);
      case LinkPackage.LINK__DIRECTED:
        return this.directed != LinkImpl.DIRECTED_EDEFAULT;
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
    if (eIsProxy()) {
      return super.toString();
    }

    final StringBuffer result = new StringBuffer(super.toString());
    result.append(" (uuid: ");
    result.append(this.uuid);
    result.append(", directed: ");
    result.append(this.directed);
    result.append(')');
    return result.toString();
  }

} // LinkImpl
