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
 * This class defines a graph model as a list of vertices and edges, which are
 * both contained in this graph. The model supports hierarchy by making Graph
 * extends Vertex.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @model
 */
public interface Graph extends Vertex {

	/**
	 * Adds the given edge to this graph's edges. Subclasses may (and are
	 * expected to) override to add the given edge to reference lists.
	 * 
	 * @param edge
	 *            an edge
	 */
	void add(Edge edge);

	/**
	 * Adds the given vertex to this graph's vertices. Subclasses may (and are
	 * expected to) override to add the given vertex to reference lists.
	 * 
	 * @param vertex
	 *            a vertex
	 */
	void add(Vertex vertex);

	/**
	 * Creates and adds an edge to this graph between the two given source and
	 * target vertices.
	 * 
	 * @param source
	 *            source vertex
	 * @param target
	 *            target vertex
	 * @return the newly-created edge
	 */
	Edge add(Vertex source, Vertex target);

	/**
	 * Returns the list of this graph's edges.
	 * 
	 * @return the list of this graph's edges
	 * @model containment="true"
	 */
	EList<Edge> getEdges();

	/**
	 * Returns the first vertex of this graph. The first vertex, if it exists,
	 * is the one vertex of the graph that has no incoming edges. If there are
	 * no such vertices, or several such vertices, this method returns
	 * <code>null</code>.
	 * 
	 * @return the first vertex of this graph, or <code>null</code>
	 */
	Vertex getFirst();

	/**
	 * Returns the last vertex of this graph. The last vertex, if it exists, is
	 * the one vertex of the graph that has no outgoing edges. If there are no
	 * such vertices, or several such vertices, this method returns
	 * <code>null</code>.
	 * 
	 * @return the last vertex of this graph, or <code>null</code>
	 */
	Vertex getLast();

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
