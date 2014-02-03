package org.ietr.dftools.algorithm.model.visitors;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.AbstractVertex;

/**
 * Interface of the SDF visitor
 * 
 * @author jpiat
 * @param <G> The graph type
 * @param <V> The vertex type
 * @param <E> The edge type
 * 
 */
@SuppressWarnings("rawtypes")
public interface IGraphVisitor<G extends AbstractGraph,V extends AbstractVertex,E extends AbstractEdge> {

	/**
	 * Visit the given edge
	 * 
	 * @param sdfEdge
	 */
	public abstract void visit(E sdfEdge);

	/**
	 * Visit the given graph
	 * 
	 * @param sdf
	 * @throws SDF4JException 
	 */
	public abstract void visit(G sdf)throws SDF4JException;

	/**
	 * Visit the given  vertex
	 * 
	 * @param sdfVertex
	 * @throws SDF4JException 
	 */
	public abstract void visit(V sdfVertex)throws SDF4JException;

}
