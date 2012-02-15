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
	 * Returns the list of this graph's vertices.
	 * 
	 * @return the list of this graph's vertices
	 * @model
	 */
	EList<Vertex> getVertices();

}
