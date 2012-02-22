/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph;

import net.sf.dftools.util.Attribute;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an edge. An edge has a source vertex and a target vertex,
 * as well as a list of attributes.
 * 
 * @model
 */
public interface Edge extends EObject {

	Attribute getAttribute(String name);

	/**
	 * Returns the attributes of this edge.
	 * 
	 * @return the attributes of this edge
	 * 
	 * @model containment="true"
	 */
	EList<Attribute> getAttributes();

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
	 * Returns <code>true</code> if this edge points to a node previously
	 * visited.
	 * 
	 * @return <code>true</code> if this edge points to a node previously
	 *         visited
	 * @model transient="true"
	 */
	boolean isBackEdge();

	/**
	 * Sets the value of the attribute with the given name to the given value.
	 * If no attribute exists with the given name, a new attribute is created
	 * and inserted at the beginning of the attribute list. This makes it easier
	 * to reference attributes by their index (the last attribute that was added
	 * will be at index 0, not index getAttributes().size() - 1).
	 * 
	 * @param name
	 *            name of the attribute
	 * @param value
	 *            value to set
	 */
	void setAttribute(String name, EObject value);

	/**
	 * Sets the "back edge" flag to true or false.
	 * 
	 * @param backEdge
	 *            a boolean
	 */
	void setBackEdge(boolean backEdge);

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
