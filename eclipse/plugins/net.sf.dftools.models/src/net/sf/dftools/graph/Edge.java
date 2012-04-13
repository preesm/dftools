/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph;

import net.sf.dftools.util.Attributable;

/**
 * This class defines an edge. An edge has a source vertex and a target vertex,
 * as well as a list of attributes.
 * 
 * @model extends="Attributable"
 */
public interface Edge extends Attributable {

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see net.sf.dftools.graph.GraphPackage#getEdge_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link net.sf.dftools.graph.Edge#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the source vertex of this edge. This has an opposite relation to
	 * {@link Vertex#getOutgoing()}.
	 * 
	 * @return the source vertex of this edge
	 * 
	 * @model opposite="outgoing"
	 */
	Vertex getSource();

	/**
	 * Returns the target vertex of this edge. This has an opposite relation to
	 * {@link Vertex#getIncoming()}.
	 * 
	 * @return the target vertex of this edge
	 * 
	 * @model opposite="incoming"
	 */
	Vertex getTarget();

	/**
	 * Sets the source vertex of this edge.
	 * 
	 * @param source
	 *            the new source of this edge
	 */
	void setSource(Vertex source);

	/**
	 * Sets the target vertex of this edge.
	 * 
	 * @param target
	 *            the new target of this edge
	 */
	void setTarget(Vertex target);

}
