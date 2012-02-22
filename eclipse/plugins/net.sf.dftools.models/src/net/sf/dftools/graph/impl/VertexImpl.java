/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph.impl;

import static net.sf.dftools.util.UtilFactory.eINSTANCE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.GraphPackage;
import net.sf.dftools.graph.Vertex;
import net.sf.dftools.util.Attribute;
import net.sf.dftools.util.impl.NameableImpl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Vertex</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.dftools.graph.impl.VertexImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link net.sf.dftools.graph.impl.VertexImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link net.sf.dftools.graph.impl.VertexImpl#getOutgoing <em>Outgoing</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VertexImpl extends NameableImpl implements Vertex {

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> incoming;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> outgoing;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected VertexImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GraphPackage.VERTEX__ATTRIBUTES:
			return getAttributes();
		case GraphPackage.VERTEX__INCOMING:
			return getIncoming();
		case GraphPackage.VERTEX__OUTGOING:
			return getOutgoing();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.VERTEX__INCOMING:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncoming())
					.basicAdd(otherEnd, msgs);
		case GraphPackage.VERTEX__OUTGOING:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutgoing())
					.basicAdd(otherEnd, msgs);
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
		case GraphPackage.VERTEX__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd,
					msgs);
		case GraphPackage.VERTEX__INCOMING:
			return ((InternalEList<?>) getIncoming()).basicRemove(otherEnd,
					msgs);
		case GraphPackage.VERTEX__OUTGOING:
			return ((InternalEList<?>) getOutgoing()).basicRemove(otherEnd,
					msgs);
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
		case GraphPackage.VERTEX__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		case GraphPackage.VERTEX__INCOMING:
			return incoming != null && !incoming.isEmpty();
		case GraphPackage.VERTEX__OUTGOING:
			return outgoing != null && !outgoing.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GraphPackage.VERTEX__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends Attribute>) newValue);
			return;
		case GraphPackage.VERTEX__INCOMING:
			getIncoming().clear();
			getIncoming().addAll((Collection<? extends Edge>) newValue);
			return;
		case GraphPackage.VERTEX__OUTGOING:
			getOutgoing().clear();
			getOutgoing().addAll((Collection<? extends Edge>) newValue);
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
		return GraphPackage.Literals.VERTEX;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GraphPackage.VERTEX__ATTRIBUTES:
			getAttributes().clear();
			return;
		case GraphPackage.VERTEX__INCOMING:
			getIncoming().clear();
			return;
		case GraphPackage.VERTEX__OUTGOING:
			getOutgoing().clear();
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
					Attribute.class, this, GraphPackage.VERTEX__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<Edge>(Edge.class,
					this, GraphPackage.VERTEX__INCOMING,
					GraphPackage.EDGE__TARGET);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<Edge>(Edge.class,
					this, GraphPackage.VERTEX__OUTGOING,
					GraphPackage.EDGE__SOURCE);
		}
		return outgoing;
	}

	@Override
	public List<Vertex> getPredecessors() {
		List<Vertex> predecessors = new ArrayList<Vertex>();
		for (Edge edge : getIncoming()) {
			Vertex source = edge.getSource();
			if (!predecessors.contains(source)) {
				predecessors.add(source);
			}
		}
		return predecessors;
	}

	@Override
	public List<Vertex> getSuccessors() {
		List<Vertex> successors = new ArrayList<Vertex>();
		for (Edge edge : getOutgoing()) {
			Vertex target = edge.getTarget();
			if (!successors.contains(target)) {
				successors.add(target);
			}
		}
		return successors;
	}

	@Override
	public void setAttribute(String name, EObject value) {
		for (Attribute attribute : getAttributes()) {
			if (name.equals(attribute.getName())) {
				attribute.setValue(value);
				return;
			}
		}

		getAttributes().add(0, eINSTANCE.createAttribute(name, value));
	}

} // VertexImpl
