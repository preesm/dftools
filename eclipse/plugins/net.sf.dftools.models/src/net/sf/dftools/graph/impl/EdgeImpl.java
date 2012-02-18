/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph.impl;

import java.util.Collection;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.GraphPackage;
import net.sf.dftools.graph.Vertex;
import net.sf.dftools.util.Attribute;
import net.sf.dftools.util.UtilFactory;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Edge</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.dftools.graph.impl.EdgeImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link net.sf.dftools.graph.impl.EdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link net.sf.dftools.graph.impl.EdgeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link net.sf.dftools.graph.impl.EdgeImpl#isBackEdge <em>Back Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EdgeImpl extends EObjectImpl implements Edge {

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Vertex source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Vertex target;

	/**
	 * The default value of the '{@link #isBackEdge() <em>Back Edge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBackEdge()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BACK_EDGE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBackEdge() <em>Back Edge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBackEdge()
	 * @generated
	 * @ordered
	 */
	protected boolean backEdge = BACK_EDGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(Vertex newSource,
			NotificationChain msgs) {
		Vertex oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, GraphPackage.EDGE__SOURCE, oldSource,
					newSource);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Vertex newTarget,
			NotificationChain msgs) {
		Vertex oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, GraphPackage.EDGE__TARGET, oldTarget,
					newTarget);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GraphPackage.EDGE__ATTRIBUTES:
			return getAttributes();
		case GraphPackage.EDGE__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
		case GraphPackage.EDGE__TARGET:
			if (resolve)
				return getTarget();
			return basicGetTarget();
		case GraphPackage.EDGE__BACK_EDGE:
			return isBackEdge();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.EDGE__SOURCE:
			if (source != null)
				msgs = ((InternalEObject) source).eInverseRemove(this,
						GraphPackage.VERTEX__OUTGOING, Vertex.class, msgs);
			return basicSetSource((Vertex) otherEnd, msgs);
		case GraphPackage.EDGE__TARGET:
			if (target != null)
				msgs = ((InternalEObject) target).eInverseRemove(this,
						GraphPackage.VERTEX__INCOMING, Vertex.class, msgs);
			return basicSetTarget((Vertex) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.EDGE__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd,
					msgs);
		case GraphPackage.EDGE__SOURCE:
			return basicSetSource(null, msgs);
		case GraphPackage.EDGE__TARGET:
			return basicSetTarget(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case GraphPackage.EDGE__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		case GraphPackage.EDGE__SOURCE:
			return source != null;
		case GraphPackage.EDGE__TARGET:
			return target != null;
		case GraphPackage.EDGE__BACK_EDGE:
			return backEdge != BACK_EDGE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (backEdge: ");
		result.append(backEdge);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GraphPackage.EDGE__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends Attribute>) newValue);
			return;
		case GraphPackage.EDGE__SOURCE:
			setSource((Vertex) newValue);
			return;
		case GraphPackage.EDGE__TARGET:
			setTarget((Vertex) newValue);
			return;
		case GraphPackage.EDGE__BACK_EDGE:
			setBackEdge((Boolean) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GraphPackage.Literals.EDGE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GraphPackage.EDGE__ATTRIBUTES:
			getAttributes().clear();
			return;
		case GraphPackage.EDGE__SOURCE:
			setSource((Vertex) null);
			return;
		case GraphPackage.EDGE__TARGET:
			setTarget((Vertex) null);
			return;
		case GraphPackage.EDGE__BACK_EDGE:
			setBackEdge(BACK_EDGE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public Attribute getAttribute(String name) {
		for (Attribute attribute : getAttributes()) {
			if (name.equals(attribute.getName())) {
				return attribute;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<Attribute>(
					Attribute.class, this, GraphPackage.EDGE__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject) source;
			source = (Vertex) eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GraphPackage.EDGE__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject) target;
			target = (Vertex) eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GraphPackage.EDGE__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	@Override
	public void setAttribute(String name, EObject value) {
		for (Attribute attribute : getAttributes()) {
			if (name.equals(attribute.getName())) {
				attribute.setValue(value);
				return;
			}
		}

		getAttributes().add(UtilFactory.eINSTANCE.createAttribute(name, value));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Vertex newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject) source).eInverseRemove(this,
						GraphPackage.VERTEX__OUTGOING, Vertex.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject) newSource).eInverseAdd(this,
						GraphPackage.VERTEX__OUTGOING, Vertex.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GraphPackage.EDGE__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Vertex newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject) target).eInverseRemove(this,
						GraphPackage.VERTEX__INCOMING, Vertex.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject) newTarget).eInverseAdd(this,
						GraphPackage.VERTEX__INCOMING, Vertex.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GraphPackage.EDGE__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBackEdge() {
		return backEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackEdge(boolean newBackEdge) {
		boolean oldBackEdge = backEdge;
		backEdge = newBackEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GraphPackage.EDGE__BACK_EDGE, oldBackEdge, backEdge));
	}

} // EdgeImpl
