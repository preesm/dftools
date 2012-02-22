/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph;

import java.util.List;

import net.sf.dftools.util.Attributable;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a vertex. A vertex has incoming edges and outgoing edges.
 * It also has a list of attributes. The predecessor/successor information is
 * actually deduced from the incoming/outgoing edges.
 * 
 * @model
 */
public interface Vertex extends Attributable {

	/**
	 * Returns the list of incoming edges.
	 * 
	 * @return the list of incoming edges
	 * @model opposite="target"
	 */
	EList<Edge> getIncoming();

	/**
	 * Returns the list of outgoing edges.
	 * 
	 * @return the list of outgoing edges
	 * @model opposite="source"
	 */
	EList<Edge> getOutgoing();

	/**
	 * Returns the list of predecessors of this vertex. This list is built
	 * on-the-fly from the incoming list.
	 * 
	 * @return the list of predecessors of this vertex
	 */
	List<Vertex> getPredecessors();

	/**
	 * Returns the list of successors of this vertex. This list is built
	 * on-the-fly from the outgoing list.
	 * 
	 * @return the list of successors of this vertex
	 */
	List<Vertex> getSuccessors();

}
