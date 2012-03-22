/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a graph as a list of vertices and edges, which are both
 * contained in this graph.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @model
 */
public interface Graph extends EObject {

	/**
	 * Adds the given vertex to this graph's vertices. Subclasses may (and are
	 * expected to) override to add the given vertex to reference lists.
	 * 
	 * @param vertex
	 *            a vertex
	 */
	void add(Vertex vertex);

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
	 * @model containment="true"
	 */
	EList<Vertex> getVertices();

	/**
	 * Removes the given edge from the list of edges and unlinks it (set its
	 * source and target attributes to <code>null</code>).
	 * 
	 * @param edge
	 *            an edge
	 */
	void remove(Edge edge);

	/**
	 * Removes the given vertex from the list of vertices, along with all its
	 * incoming and outgoing edges.
	 * 
	 * @param vertex
	 *            a vertex
	 */
	void remove(Vertex vertex);

	/**
	 * Removes all given edges from the list of edges, and unlinks them (set
	 * their source and target attributes to <code>null</code>).
	 * 
	 * @param edges
	 *            a list of edges
	 */
	void removeEdges(List<? extends Edge> edges);

	/**
	 * Removes all given vertices from the list of vertices, along with all
	 * their incoming and outgoing edges.
	 * 
	 * @param vertices
	 *            a list of vertices
	 */
	void removeVertices(List<? extends Vertex> vertices);

}
