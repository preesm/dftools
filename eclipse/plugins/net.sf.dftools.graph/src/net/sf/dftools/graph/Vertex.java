/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph;

import java.util.List;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Vertex</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.graph.Vertex#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link net.sf.dftools.graph.Vertex#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link net.sf.dftools.graph.Vertex#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.graph.GraphPackage#getVertex()
 * @model
 * @generated
 */
public interface Vertex extends Nameable {
	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list. The
	 * list contents are of type {@link net.sf.dftools.graph.Edge}. It is
	 * bidirectional and its opposite is '
	 * {@link net.sf.dftools.graph.Edge#getSource <em>Source</em>}'. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see net.sf.dftools.graph.GraphPackage#getVertex_Outgoing()
	 * @see net.sf.dftools.graph.Edge#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Edge> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list. The
	 * list contents are of type {@link net.sf.dftools.graph.Edge}. It is
	 * bidirectional and its opposite is '
	 * {@link net.sf.dftools.graph.Edge#getTarget <em>Target</em>}'. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see net.sf.dftools.graph.GraphPackage#getVertex_Incoming()
	 * @see net.sf.dftools.graph.Edge#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Edge> getIncoming();

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.dftools.graph.Attribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see net.sf.dftools.graph.GraphPackage#getVertex_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Attribute> getAttributes();

	List<Vertex> getPredecessors();

	List<Vertex> getSuccessors();

	Attribute getAttribute(String name);

} // Vertex
