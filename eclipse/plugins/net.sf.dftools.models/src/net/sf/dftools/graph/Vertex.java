/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph;

import java.util.List;

import net.sf.dftools.util.Attribute;
import net.sf.dftools.util.Nameable;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a vertex. A vertex has incoming edges and outgoing edges.
 * It also has a list of attributes. The predecessor/successor information is
 * actually deduced from the incoming/outgoing edges.
 * 
 * @model
 */
public interface Vertex extends Nameable {

	Attribute getAttribute(String name);

	/**
	 * Returns the attributes of this vertex.
	 * 
	 * @return the attributes of this vertex
	 * 
	 * @model containment="true"
	 */
	EList<Attribute> getAttributes();

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

	/**
	 * Sets the value of the attribute with the given name to the given value.
	 * If no attribute exists with the given name, a new attribute is created
	 * and inserted at the beginning of the attribute list. This makes it easier
	 * to reference attributes by their index (the last attribute that was added
	 * will be at index 0, not index getAttributes().size() - 1).
	 * <p>
	 * If the given value is not an instance of EObject, it is set as a runtime
	 * value of the attribute.
	 * </p>
	 * 
	 * @param name
	 *            name of the attribute
	 * @param value
	 *            value to set
	 */
	void setAttribute(String name, Object value);

}
