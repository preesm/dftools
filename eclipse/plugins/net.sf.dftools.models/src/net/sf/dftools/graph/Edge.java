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
