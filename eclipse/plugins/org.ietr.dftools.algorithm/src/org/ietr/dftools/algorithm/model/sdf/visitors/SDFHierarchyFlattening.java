package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.parameters.Argument;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.parameters.NoIntegerValueException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFBroadcastVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFForkVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFJoinVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFRoundBufferVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.types.SDFStringEdgePropertyType;
import org.ietr.dftools.algorithm.model.visitors.AbstractHierarchyFlattening;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;

/**
 * HierarchyFlattening for a given depth
 * 
 * @author jpiat
 * 
 */
public class SDFHierarchyFlattening extends
		AbstractHierarchyFlattening<SDFGraph> {

	/**
	 * Flatten the hierarchy of the given graph to the given depth
	 * 
	 * @param sdf
	 *            The graph to flatten
	 * @param depth
	 *            The depth to flatten the graph
	 * @param log
	 *            The logger in which output information
	 * @throws SDF4JException
	 */
	public void flattenGraph(SDFGraph sdf, int depth) throws SDF4JException {
		boolean flatteningEnded = false;
		int newDepth = depth;
		SDFGraph graph = sdf;
		
		while (!flatteningEnded) {
			newDepth--;

			prepareGraphHierarchy(graph, newDepth);

			SDFHierarchyInstanciation instantiate = new SDFHierarchyInstanciation();
			graph.accept(instantiate);

			output = instantiate.getOutput();

			if (!output.isSchedulable()) {
				throw (new SDF4JException("graph not schedulable"));
			}

			flattenVertices(output);

			output.getPropertyBean().setValue("schedulable", true);

			flatteningEnded = newDepth == 0;

			graph = output;
		}
		//
		// if (depth > 0) {
		// int newDepth = depth - 1;
		//
		// prepareGraphHierarchy(sdf, newDepth);
		//
		// SDFHierarchyInstanciation instantiate = new
		// SDFHierarchyInstanciation();
		// sdf.accept(instantiate);
		//
		// output = instantiate.getOutput();
		//
		// if (!output.isSchedulable()) {
		// throw (new SDF4JException("graph not schedulable"));
		// }
		//
		// flattenVertices(output);
		//
		// output.getPropertyBean().setValue("schedulable", true);
		//
		// flattenGraph(output, newDepth);
		//
		// } else {
		// return;
		// }
	}

	/**
	 * Call AbstractHierarchyFlattening.prepareHierarchy on each subgraph of sdf
	 * 
	 * @param sdf
	 *            the graph from which we prepare hierarchy
	 * @param depth
	 *            the current depth of the flattening
	 * @throws SDF4JException
	 */
	private void prepareGraphHierarchy(SDFGraph sdf, int depth)
			throws SDF4JException {
		for (SDFAbstractVertex vertex : sdf.vertexSet()) {
			if (vertex.getGraphDescription() != null
					&& vertex.getGraphDescription() instanceof SDFGraph) {
				try {
					prepareHierarchy(vertex, depth);
				} catch (InvalidExpressionException e) {
					e.printStackTrace();
					throw (new SDF4JException(e.getMessage()));
				}
			}
		}
	}

	/**
	 * Call flattenVertex on each subgraph of sdf
	 * 
	 * @param sdf
	 *            the graph from which we flatten the subgraphs
	 * @throws SDF4JException
	 */
	private void flattenVertices(SDFGraph sdf) throws SDF4JException {
		Vector<SDFAbstractVertex> vertices = new Vector<SDFAbstractVertex>(
				sdf.vertexSet());
		for (SDFAbstractVertex vertex : vertices) {
			if (vertex.getGraphDescription() != null
					&& vertex.getGraphDescription() instanceof SDFGraph) {
				try {
					flattenVertex(vertex, sdf);
				} catch (InvalidExpressionException e) {
					e.printStackTrace();
					throw (new SDF4JException(e.getMessage()));
				}
				sdf.removeVertex(vertex);
			}
		}
	}

	/**
	 * Flatten one vertex given its parent Copy every vertex contained by the
	 * vertex and add the copies to the parentGraph and copy the edges contained
	 * by the vertex into the parentGraph
	 * 
	 * @param vertex
	 *            The vertex to flatten
	 * @param parentGraph
	 *            The new parent graph
	 * @throws InvalidExpressionException
	 */
	private void flattenVertex(SDFAbstractVertex vertex, SDFGraph parentGraph)
			throws InvalidExpressionException {

		// Map from original vertices to their copies (i.e., the graph described
		// by vertex)
		Map<SDFAbstractVertex, SDFAbstractVertex> matchCopies;

		// Copy every vertex from vertex to parentGraph and link original
		// vertices to their copies
		matchCopies = copyVerticesFromSubgraphtoSupergraph(vertex, parentGraph);
		// Copy every edge from vertex to parentGraph
		copyEdgesFromSubgraphToSupergraph(vertex, parentGraph, matchCopies);

	}

	private void copyEdgesFromSubgraphToSupergraph(SDFAbstractVertex vertex,
			SDFGraph parentGraph,
			Map<SDFAbstractVertex, SDFAbstractVertex> matchCopies)
			throws InvalidExpressionException {

		// List of edges contained by vertex
		@SuppressWarnings("unchecked")
		Vector<SDFEdge> edges = new Vector<SDFEdge>(vertex
				.getGraphDescription().edgeSet());

		// For every edge of the subgraph (i.e., the graph described by
		// vertex)
		for (SDFEdge edge : edges) {
			// Get all the information necessary to "clone" the edge
			// into the
			// parentGraph
			EdgeDescription desc = getEdgeDescriptionFor(edge, vertex,
					matchCopies);
			// Use them to copy the edge into parentGraph
			copyEdge(desc, parentGraph);
		}
	}

	/**
	 * Copy an edge using its description from its graph to a parent graph
	 * 
	 * @param desc
	 *            the description of the edge to copy
	 * @param parentGraph
	 *            the SDFGraph in which we copy the edge
	 */
	private void copyEdge(EdgeDescription desc, SDFGraph parentGraph) {
		SDFAbstractVertex sourceVertex = desc.sourceVertex;
		SDFAbstractVertex targetVertex = desc.targetVertex;
		int delayValue = desc.delayValue;
		int prodValue = desc.prodValue;
		int consValue = desc.consValue;
		SDFStringEdgePropertyType sourceModifier = desc.sourceModifier;
		SDFStringEdgePropertyType targetModifier = desc.targetModifier;

		if (sourceVertex != null && targetVertex != null) {
			// "Clone" the edge into parentGraph
			SDFEdge newEdge = parentGraph.addEdge(sourceVertex, targetVertex);
			newEdge.copyProperties(desc.originalEdge);
			newEdge.setCons(new SDFIntEdgePropertyType(consValue));
			newEdge.setProd(new SDFIntEdgePropertyType(prodValue));
			if (delayValue != 0) {
				newEdge.setDelay(new SDFIntEdgePropertyType(delayValue));
			}
			if (newEdge.getTargetPortModifier() == null
					&& targetModifier != null) {
				// If the target port modifier was not already defined from the
				// inside edge and the outside edge had
				// a modifier
				newEdge.setTargetPortModifier(targetModifier);
			}
			if (newEdge.getSourcePortModifier() == null
					&& sourceModifier != null) {
				// If the source port modifier was not already defined from the
				// inside edge and the outside edge had
				// a modifier
				newEdge.setSourcePortModifier(sourceModifier);
			}
		}
	}

	/**
	 * Clone all the vertices contained by subgraph, copy their properties and
	 * arguments to their clones, and add the clones to parentGraph
	 * 
	 * @param subgraph
	 *            the SDFAbstractVertex containing the vertices to clone
	 * @param parentGraph
	 *            the SDFGraph to which we want to clone the vertices
	 * @return the map from subgraph vertices to their clones in parentGraph
	 * @throws InvalidExpressionException
	 */
	private Map<SDFAbstractVertex, SDFAbstractVertex> copyVerticesFromSubgraphtoSupergraph(
			SDFAbstractVertex subgraph, SDFGraph parentGraph)
			throws InvalidExpressionException {

		// List of vertices contained by vertex
		@SuppressWarnings("unchecked")
		Vector<SDFAbstractVertex> vertices = new Vector<SDFAbstractVertex>(
				subgraph.getGraphDescription().vertexSet());
		// Map from original vertices to their clones (i.e., the graph described
		// by vertex)
		Map<SDFAbstractVertex, SDFAbstractVertex> matchCopies = new HashMap<SDFAbstractVertex, SDFAbstractVertex>();

		// For every non-Interface vertex of the subgraph
		for (SDFAbstractVertex trueVertex : vertices) {
			if (!(trueVertex instanceof SDFInterfaceVertex)) {
				// Clone this vertex, copy its properties and arguments to its
				// clone
				SDFAbstractVertex cloneVertex = copyVertexWithPropertiesAndArguments(
						trueVertex, subgraph);
				// Add the clone to the parent graph
				parentGraph.addVertex(cloneVertex);
				// And add it to the matchCopies map
				matchCopies.put(trueVertex, cloneVertex);
			}
		}
		return matchCopies;
	}

	/**
	 * Clone a vertex, copy its properties and arguments to its clone
	 * 
	 * @param trueVertex
	 *            the vertex to clone
	 * @param parentVertex
	 *            the vertex container of trueVertex
	 * @return the clone of trueVertex
	 * @throws InvalidExpressionException
	 */
	private SDFAbstractVertex copyVertexWithPropertiesAndArguments(
			SDFAbstractVertex trueVertex, SDFAbstractVertex parentVertex)
			throws InvalidExpressionException {
		// Start by cloning trueVertex
		SDFAbstractVertex cloneVertex = trueVertex.clone();
		// Copy its properties
		cloneVertex.copyProperties(trueVertex);
		// Set its number of repeat
		cloneVertex.setNbRepeat(trueVertex.getNbRepeatAsInteger()
				* parentVertex.getNbRepeatAsInteger());
		// and its name
		cloneVertex.setName(parentVertex.getName() + "_"
				+ cloneVertex.getName());
		// Copy its arguments, if any
		if (trueVertex.getArguments() != null) {
			for (Argument arg : trueVertex.getArguments().values()) {
				arg.setExpressionSolver(trueVertex.getBase());
				Integer valueOfArg;
				try {
					valueOfArg = arg.intValue();
					cloneVertex.getArgument(arg.getName()).setValue(
							String.valueOf(valueOfArg));
				} catch (NoIntegerValueException e) {
					e.printStackTrace();
				}
			}
		}
		return cloneVertex;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void treatSinkInterface(AbstractVertex port,
			AbstractGraph parentGraph, int depth)
			throws InvalidExpressionException {

		if (!(port instanceof SDFSinkInterfaceVertex)) {
			return;
		}

		SDFSinkInterfaceVertex vertex = (SDFSinkInterfaceVertex) port;
		boolean needRoundBuffer = false;
		boolean needImplode = false;
		Vector<SDFEdge> inEdges = new Vector<SDFEdge>(
				parentGraph.incomingEdgesOf(vertex));
		if (inEdges.size() > 0) {
			needRoundBuffer = true;
		} else {
			for (SDFEdge inEdge : inEdges) {
				if (inEdge.getCons().intValue() < (inEdge.getProd().intValue() * inEdge
						.getSource().getNbRepeatAsInteger())) {
					needRoundBuffer = true;
					break;
				} else if (inEdge.getCons().intValue() > (inEdge.getProd()
						.intValue())) {
					needImplode = true;
				}
			}
		}
		
		if (needRoundBuffer) {
			addRoundBuffer(parentGraph, vertex);
		} else if (needImplode && depth == 0) {
			addImplode(parentGraph, vertex);
		}
	}

	private void addImplode(
			AbstractGraph<SDFAbstractVertex, SDFEdge> parentGraph,
			SDFSinkInterfaceVertex vertex) throws InvalidExpressionException {

		Vector<SDFEdge> inEdges = new Vector<SDFEdge>(
				parentGraph.incomingEdgesOf(vertex));

		SDFJoinVertex implodeBuffer = new SDFJoinVertex();
		SDFSinkInterfaceVertex output = new SDFSinkInterfaceVertex();
		output.setName("out");
		implodeBuffer.setName("implode_" + vertex.getName());
		parentGraph.addVertex(implodeBuffer);
		SDFEdge edge = (SDFEdge) parentGraph.addEdge(implodeBuffer, vertex);
		edge.copyProperties(inEdges.get(0));
		// The modifier of the source port should not be copied.
		edge.setSourcePortModifier(null);
		edge.setProd(new SDFIntEdgePropertyType(inEdges.get(0).getCons()
				.intValue()));
		edge.setCons(new SDFIntEdgePropertyType(inEdges.get(0).getCons()
				.intValue()));
		edge.setSourceInterface(output);

		// Add all input edges
		int nbTokens = 0;
		while (inEdges.size() > 0) {
			SDFEdge treatEdge = inEdges.get(0);

			SDFSourceInterfaceVertex input = new SDFSourceInterfaceVertex();
			input.setName("in" + nbTokens);
			nbTokens += treatEdge.getProd().intValue()
					* treatEdge.getSource().getNbRepeatAsInteger();

			SDFAbstractVertex source = treatEdge.getSource();
			SDFEdge newEdge = (SDFEdge) parentGraph.addEdge(source,
					implodeBuffer);
			newEdge.copyProperties(treatEdge);
			// The modifier of the target port should not be copied.
			edge.setTargetPortModifier(null);
			newEdge.setProd(new SDFIntEdgePropertyType(treatEdge.getProd()
					.intValue()));
			newEdge.setCons(new SDFIntEdgePropertyType(treatEdge.getProd()
					.intValue() * source.getNbRepeatAsInteger()));
			newEdge.setTargetInterface(input);
			newEdge.setSourceInterface(treatEdge.getSourceInterface());
			parentGraph.removeEdge(treatEdge);
			inEdges.remove(0);
		}
	}

	private void addRoundBuffer(
			AbstractGraph<SDFAbstractVertex, SDFEdge> parentGraph,
			SDFSinkInterfaceVertex vertex) throws InvalidExpressionException {
		Vector<SDFEdge> inEdges = new Vector<SDFEdge>(
				parentGraph.incomingEdgesOf(vertex));

		SDFRoundBufferVertex roundBuffer = new SDFRoundBufferVertex();
		SDFSinkInterfaceVertex output = new SDFSinkInterfaceVertex();
		output.setName("out");
		roundBuffer.addSource(output);
		roundBuffer.setName("roundBuffer_" + vertex.getName());
		parentGraph.addVertex(roundBuffer);
		SDFEdge edge = (SDFEdge) parentGraph.addEdge(roundBuffer, vertex);
		edge.copyProperties(inEdges.get(0));
		// The modifier of the source port should not be copied.
		// Instead, always set it to write_only
		edge.setSourcePortModifier(new SDFStringEdgePropertyType(
				SDFEdge.MODIFIER_WRITE_ONLY));
		edge.setProd(new SDFIntEdgePropertyType(inEdges.get(0).getCons()
				.intValue()));
		edge.setCons(new SDFIntEdgePropertyType(inEdges.get(0).getCons()
				.intValue()));
		edge.setSourceInterface(output);
		// Get the total sum size of the input
		Iterator<SDFEdge> iter = inEdges.iterator();
		int totalSize = 0;
		while (iter.hasNext()) {
			SDFEdge inEdge = iter.next();
			totalSize += inEdge.getProd().intValue()
					* inEdge.getSource().getNbRepeatAsInteger();
		}

		// Add all input edges
		int nbTokens = 0;
		while (inEdges.size() > 0) {
			SDFEdge treatEdge = inEdges.get(0);

			// Create the input port
			SDFSourceInterfaceVertex input = new SDFSourceInterfaceVertex();
			input.setName("in" + nbTokens / edge.getProd().intValue() + "_"
					+ nbTokens % edge.getProd().intValue());
			nbTokens += treatEdge.getProd().intValue()
					* treatEdge.getSource().getNbRepeatAsInteger();

			roundBuffer.addSink(input);

			SDFAbstractVertex source = treatEdge.getSource();
			SDFEdge newEdge = (SDFEdge) parentGraph
					.addEdge(source, roundBuffer);
			newEdge.copyProperties(treatEdge);
			newEdge.setProd(new SDFIntEdgePropertyType(treatEdge.getProd()
					.intValue()));
			newEdge.setCons(new SDFIntEdgePropertyType(treatEdge.getProd()
					.intValue() * treatEdge.getSource().getNbRepeatAsInteger()));
			// The modifier of the target port should not be copied.
			// Instead always set to unused except for last inputs
			totalSize -= newEdge.getCons().intValue();
			if (totalSize > edge.getProd().intValue()) {
				newEdge.setTargetPortModifier(new SDFStringEdgePropertyType(
						SDFEdge.MODIFIER_UNUSED));
			} else {
				newEdge.setTargetPortModifier(new SDFStringEdgePropertyType(
						SDFEdge.MODIFIER_READ_ONLY));
			}

			newEdge.setTargetInterface(input);
			newEdge.setSourceInterface(treatEdge.getSourceInterface());
			parentGraph.removeEdge(treatEdge);
			inEdges.remove(0);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void treatSourceInterface(AbstractVertex port,
			AbstractGraph parentGraph, int depth)
			throws InvalidExpressionException {
		if (!(port instanceof SDFSourceInterfaceVertex)) {
			return;
		}
		SDFSourceInterfaceVertex vertex = (SDFSourceInterfaceVertex) port;
		boolean needBroadcast = false;
		boolean needExplode = false;
		Vector<SDFEdge> outEdges = new Vector<SDFEdge>(
				parentGraph.outgoingEdgesOf(vertex));
		if (outEdges.size() > 1) {
			needBroadcast = true;
		} else {
			for (SDFEdge outEdge : outEdges) {
				if (outEdge.getProd().intValue() < (outEdge.getCons()
						.intValue() * outEdge.getTarget()
						.getNbRepeatAsInteger())) {
					needBroadcast = true;
					break;
				} else if (outEdge.getProd().intValue() > (outEdge.getCons()
						.intValue())) {
					needExplode = true;
				}
			}
		}
		if (needBroadcast) {
			addBroadcast(parentGraph, vertex);
		} else if (needExplode && depth == 0) {
			addExplode(parentGraph, vertex);
		}
	}

	private void addExplode(
			AbstractGraph<SDFAbstractVertex, SDFEdge> parentGraph,
			SDFSourceInterfaceVertex vertex) throws InvalidExpressionException {

		Vector<SDFEdge> outEdges = new Vector<SDFEdge>(
				parentGraph.outgoingEdgesOf(vertex));

		SDFForkVertex explode = new SDFForkVertex();
		SDFSourceInterfaceVertex input = new SDFSourceInterfaceVertex();
		input.setName("in");
		explode.setName("explode_" + vertex.getName());
		parentGraph.addVertex(explode);
		SDFEdge edge = (SDFEdge) parentGraph.addEdge(vertex, explode);
		edge.copyProperties(outEdges.get(0));
		// The modifier of the target port should not be copied.
		edge.setTargetPortModifier(null);
		edge.setProd(new SDFIntEdgePropertyType(outEdges.get(0).getProd()
				.intValue()));
		edge.setCons(new SDFIntEdgePropertyType(outEdges.get(0).getProd()
				.intValue()));
		edge.setTargetInterface(input);

		// Add all output edges
		int nbTokens = 0;
		while (outEdges.size() > 0) {
			SDFEdge treatEdge = outEdges.get(0);

			SDFSinkInterfaceVertex output = new SDFSinkInterfaceVertex();
			output.setName("out" + nbTokens);
			nbTokens += treatEdge.getCons().intValue()
					* treatEdge.getTarget().getNbRepeatAsInteger();

			SDFAbstractVertex target = treatEdge.getTarget();
			SDFEdge newEdge = (SDFEdge) parentGraph.addEdge(explode, target);
			newEdge.copyProperties(treatEdge);
			// The modifier of the source port should not be copied.
			edge.setSourcePortModifier(null);
			newEdge.setCons(new SDFIntEdgePropertyType(treatEdge.getCons()
					.intValue()));
			newEdge.setProd(new SDFIntEdgePropertyType(treatEdge.getCons()
					.intValue() * treatEdge.getTarget().getNbRepeatAsInteger()));
			newEdge.setSourceInterface(output);
			newEdge.setTargetInterface(treatEdge.getTargetInterface());
			newEdge.setDataType(treatEdge.getDataType());
			parentGraph.removeEdge(treatEdge);
			outEdges.remove(0);
		}
	}

	private void addBroadcast(
			AbstractGraph<SDFAbstractVertex, SDFEdge> parentGraph,
			SDFSourceInterfaceVertex vertex) throws InvalidExpressionException {

		Vector<SDFEdge> outEdges = new Vector<SDFEdge>(
				parentGraph.outgoingEdgesOf(vertex));

		SDFBroadcastVertex broadcast = new SDFBroadcastVertex();
		SDFSourceInterfaceVertex input = new SDFSourceInterfaceVertex();
		input.setName("in");
		broadcast.setName("broadcast_" + vertex.getName());
		broadcast.addSink(input);

		parentGraph.addVertex(broadcast);
		SDFEdge edge = (SDFEdge) parentGraph.addEdge(vertex, broadcast);
		edge.copyProperties(outEdges.get(0));
		// The modifier of the target port should not be copied.
		// Instead, always set to read_only
		edge.setTargetPortModifier(new SDFStringEdgePropertyType(
				SDFEdge.MODIFIER_READ_ONLY));
		edge.setProd(new SDFIntEdgePropertyType(outEdges.get(0).getProd()
				.intValue()));
		edge.setCons(new SDFIntEdgePropertyType(outEdges.get(0).getProd()
				.intValue()));
		edge.setTargetInterface(input);

		// Add all output edges
		int nbTokens = 0;
		while (outEdges.size() > 0) {
			// The processed edge
			SDFEdge treatEdge = outEdges.get(0);

			// Create a new output port
			SDFSinkInterfaceVertex output = new SDFSinkInterfaceVertex();
			output.setName("out_" + nbTokens / edge.getCons().intValue() + "_"
					+ nbTokens % edge.getCons().intValue());
			nbTokens += treatEdge.getProd().intValue();
			broadcast.addSource(output);

			SDFAbstractVertex target = treatEdge.getTarget();
			SDFEdge newEdge = (SDFEdge) parentGraph.addEdge(broadcast, target);
			newEdge.copyProperties(treatEdge);
			// The modifier of the source port should not be copied.
			// Instead, always set to write_only
			newEdge.setSourcePortModifier(new SDFStringEdgePropertyType(
					SDFEdge.MODIFIER_WRITE_ONLY));
			newEdge.setCons(new SDFIntEdgePropertyType(treatEdge.getCons()
					.intValue()));
			newEdge.setProd(new SDFIntEdgePropertyType(treatEdge.getCons()
					.intValue() * target.getNbRepeatAsInteger()));
			newEdge.setSourceInterface(output);
			newEdge.setTargetInterface(treatEdge.getTargetInterface());
			newEdge.setDataType(treatEdge.getDataType());
			parentGraph.removeEdge(treatEdge);
			outEdges.remove(0);
		}
	}

	/**
	 * 
	 * @param edge
	 *            the SDFEdge on which we want information
	 * @param parentVertex
	 *            the container vertex of edge
	 * @param matchCopies
	 *            the map from vertices of parentVertex to their clones
	 * @return an EdgeDescritpion containing information about source, target,
	 *         delay and rates of edge
	 * @throws InvalidExpressionException
	 */
	private EdgeDescription getEdgeDescriptionFor(SDFEdge edge,
			SDFAbstractVertex parentVertex,
			Map<SDFAbstractVertex, SDFAbstractVertex> matchCopies)
			throws InvalidExpressionException {

		EdgeDescription result = new EdgeDescription(edge);

		if (edge.getSource() instanceof SDFInterfaceVertex) {
			SDFInterfaceVertex sourceInterface = (SDFInterfaceVertex) edge
					.getSource();
			if (parentVertex.getAssociatedEdge(sourceInterface) != null) {
				result.sourceVertex = parentVertex.getAssociatedEdge(
						sourceInterface).getSource();
				result.delayValue = parentVertex
						.getAssociatedEdge(sourceInterface).getDelay()
						.intValue();
				result.prodValue = parentVertex
						.getAssociatedEdge(sourceInterface).getProd()
						.intValue();
				edge.setSourceInterface(parentVertex.getAssociatedEdge(
						sourceInterface).getSourceInterface());
				result.targetModifier = parentVertex.getAssociatedEdge(
						sourceInterface).getTargetPortModifier();
			}

		} else {
			result.sourceVertex = matchCopies.get(edge.getSource());
			result.prodValue = edge.getProd().intValue();
		}
		if (edge.getTarget() instanceof SDFInterfaceVertex) {
			SDFInterfaceVertex targetInterface = (SDFInterfaceVertex) edge
					.getTarget();
			if (parentVertex.getAssociatedEdge(targetInterface) != null) {
				result.targetVertex = parentVertex.getAssociatedEdge(
						targetInterface).getTarget();
				result.delayValue = parentVertex
						.getAssociatedEdge(targetInterface).getDelay()
						.intValue();
				result.consValue = parentVertex
						.getAssociatedEdge(targetInterface).getCons()
						.intValue();
				edge.setTargetInterface(parentVertex.getAssociatedEdge(
						targetInterface).getTargetInterface());
				result.sourceModifier = parentVertex.getAssociatedEdge(
						targetInterface).getSourcePortModifier();
			}

		} else {
			result.targetVertex = matchCopies.get(edge.getTarget());
			result.consValue = edge.getCons().intValue();
		}

		return result;
	}

	/**
	 * Class stocking information about SDFEdge
	 * 
	 * @author cguy
	 * 
	 */
	private class EdgeDescription {

		public EdgeDescription(SDFEdge edge) {
			this.originalEdge = edge;
		}

		// The edge from which we built the description
		SDFEdge originalEdge;
		// The source of the edge (either direct or indirect in case of
		// interface source)
		SDFAbstractVertex sourceVertex;
		// The target of the edge (either direct or indirect in case of
		// interface target)
		SDFAbstractVertex targetVertex;
		// The delay of the edge
		int delayValue = 0;
		// The production rate of the edge
		int prodValue = 0;
		// The consumption rate of the edge
		int consValue = 0;
		SDFStringEdgePropertyType sourceModifier;
		SDFStringEdgePropertyType targetModifier;
	}

}
