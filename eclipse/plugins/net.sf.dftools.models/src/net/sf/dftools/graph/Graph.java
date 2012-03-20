/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a graph as a list of vertices and edges. The vertices are
 * not contained in this graph; containment is considered to be the
 * responsibility of clients of this class (including subclasses).
 * 
 * @model
 */
public interface Graph extends EObject {

	/**
	 * Returns the list of this graph's edges.
	 * 
	 * @return the list of this graph's edges
	 * @model containment="true"
	 */
	EList<Edge> getEdges();

	/**
	 * Returns the entry vertex of this graph. The entry vertex, if it exists,
	 * is the one vertex of the graph that has no incoming edges. If there are
	 * no such vertices, or several such vertices, this method returns
	 * <code>null</code> .
	 * 
	 * @return the entry vertex of this graph, or <code>null</code>
	 */
	Vertex getEntry();

	/**
	 * Returns the exit vertex of this graph. The exit vertex, if it exists, is
	 * the one vertex of the graph that has no outgoing edges. If there are no
	 * such vertices, or several such vertices, this method returns
	 * <code>null</code> .
	 * 
	 * @return the exit vertex of this graph, or <code>null</code>
	 */
	Vertex getExit();

	/**
	 * Returns the list of this graph's vertices.
	 * 
	 * @return the list of this graph's vertices
	 * @model
	 */
	EList<Vertex> getVertices();

}
