package net.sf.dftools.algorithm.model.sdf.visitors;

import java.util.HashMap;
import java.util.Vector;

import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.parameters.Argument;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.parameters.NoIntegerValueException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFBroadcastVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFForkVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFJoinVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFRoundBufferVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.sdf.types.SDFStringEdgePropertyType;
import net.sf.dftools.algorithm.model.visitors.AbstractHierarchyFlattening;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

/**
 * HierarchyFlattening for a given depth
 * 
 * @author jpiat
 * 
 */
public class SDFHierarchyFlattening extends
		AbstractHierarchyFlattening<SDFGraph> {

	/**
	 * Flatten one vertex given it's parent
	 * 
	 * @param vertex
	 *            The vertex to flatten
	 * @param parentGraph
	 *            The new parent graph
	 * @throws InvalidExpressionException
	 * @throws DynamicExpressionException
	 */
	@SuppressWarnings("unchecked")
	private void treatVertex(SDFAbstractVertex vertex, SDFGraph parentGraph,
			int depth) throws InvalidExpressionException {
		Vector<SDFAbstractVertex> vertices = new Vector<SDFAbstractVertex>(
				vertex.getGraphDescription().vertexSet());
		HashMap<SDFAbstractVertex, SDFAbstractVertex> matchCopies = new HashMap<SDFAbstractVertex, SDFAbstractVertex>();
		for (int i = 0; i < vertices.size(); i++) {
			if (!(vertices.get(i) instanceof SDFInterfaceVertex)) {
				SDFAbstractVertex trueVertex = vertices.get(i);
				SDFAbstractVertex cloneVertex = vertices.get(i).clone();
				parentGraph.addVertex(cloneVertex);
				matchCopies.put(trueVertex, cloneVertex);
				cloneVertex.copyProperties(trueVertex);
				cloneVertex.setNbRepeat(trueVertex.getNbRepeatAsInteger()
						* vertex.getNbRepeatAsInteger());
				cloneVertex.setName(vertex.getName() + "_"
						+ cloneVertex.getName());
				if (trueVertex.getArguments() != null) {
					for (Argument arg : trueVertex.getArguments().values()) {
						arg.setExpressionSolver(trueVertex.getBase());
						Integer valueOfArg;
						try {
							valueOfArg = arg.intValue();
							cloneVertex.getArgument(arg.getName()).setValue(
									String.valueOf(valueOfArg));
						} catch (NoIntegerValueException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		Vector<SDFEdge> edges = new Vector<SDFEdge>(vertex
				.getGraphDescription().edgeSet());
		for (int i = 0; i < edges.size(); i++) {
			SDFAbstractVertex sourceVertex = null;
			SDFAbstractVertex targetVertex = null;
			int delayValue = 0;
			int prodValue = 0;
			int consValue = 0;
			SDFStringEdgePropertyType sourceModifier = null;
			SDFStringEdgePropertyType targetModifier = null;
			if (edges.get(i).getSource() instanceof SDFInterfaceVertex) {
				SDFInterfaceVertex sourceInterface = (SDFInterfaceVertex) edges
						.get(i).getSource();
				if (vertex.getAssociatedEdge(sourceInterface) != null) {
					sourceVertex = vertex.getAssociatedEdge(sourceInterface)
							.getSource();
					delayValue = vertex.getAssociatedEdge(sourceInterface)
							.getDelay().intValue();
					prodValue = vertex.getAssociatedEdge(sourceInterface)
							.getProd().intValue();
					edges.get(i).setSourceInterface(
							vertex.getAssociatedEdge(sourceInterface)
									.getSourceInterface());
					targetModifier = vertex.getAssociatedEdge(sourceInterface)
							.getTargetPortModifier();
				}

			} else {
				sourceVertex = matchCopies.get(edges.get(i).getSource());
				prodValue = edges.get(i).getProd().intValue();
			}
			if (edges.get(i).getTarget() instanceof SDFInterfaceVertex) {
				SDFInterfaceVertex targetInterface = (SDFInterfaceVertex) edges
						.get(i).getTarget();
				if (vertex.getAssociatedEdge(targetInterface) != null) {
					targetVertex = vertex.getAssociatedEdge(targetInterface)
							.getTarget();
					delayValue = vertex.getAssociatedEdge(targetInterface)
							.getDelay().intValue();
					consValue = vertex.getAssociatedEdge(targetInterface)
							.getCons().intValue();
					edges.get(i).setTargetInterface(
							vertex.getAssociatedEdge(targetInterface)
									.getTargetInterface());
					sourceModifier = vertex.getAssociatedEdge(targetInterface)
							.getSourcePortModifier();
				}

			} else {
				targetVertex = matchCopies.get(edges.get(i).getTarget());
				consValue = edges.get(i).getCons().intValue();
			}
			if (sourceVertex != null && targetVertex != null) {
				SDFEdge newEdge = parentGraph.addEdge(sourceVertex,
						targetVertex);
				newEdge.copyProperties(edges.get(i));
				newEdge.setCons(new SDFIntEdgePropertyType(consValue));
				newEdge.setProd(new SDFIntEdgePropertyType(prodValue));
				if (delayValue != 0) {
					newEdge.setDelay(new SDFIntEdgePropertyType(delayValue));
				}
				if (newEdge.getTargetPortModifier() == null
						&& targetModifier != null) {
					// If the target port modifier was not already defined from
					// the inside edge
					// and the outside edge had a modifier
					newEdge.setTargetPortModifier(targetModifier);
				}
				if (newEdge.getSourcePortModifier() == null
						&& sourceModifier != null) {
					// If the source port modifier was not already defined from
					// the inside edge
					// and the outside edge had a modifier
					newEdge.setSourcePortModifier(sourceModifier);
				}
			}
		}

	}

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
		if (depth > 0) {
			int newDepth = depth - 1;
			for (SDFAbstractVertex vertex : sdf.vertexSet()) {
				if (vertex.getGraphDescription() != null
						&& vertex.getGraphDescription() instanceof SDFGraph) {
					try {
						prepareHierarchy(vertex, newDepth);
					} catch (InvalidExpressionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw (new SDF4JException(e.getMessage()));
					}
				}
			}
			SDFHierarchyInstanciation instantiate = new SDFHierarchyInstanciation();
			sdf.accept(instantiate);
			output = instantiate.getOutput();
			if (!output.isSchedulable()) {
				throw (new SDF4JException("graph not schedulable"));
			}
			Vector<SDFAbstractVertex> vertices = new Vector<SDFAbstractVertex>(
					output.vertexSet());
			for (int i = 0; i < vertices.size(); i++) {
				if (vertices.get(i).getGraphDescription() != null
						&& vertices.get(i).getGraphDescription() instanceof SDFGraph) {
					try {
						treatVertex(vertices.get(i), output, newDepth);
					} catch (InvalidExpressionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw (new SDF4JException(e.getMessage()));
					}
					output.removeVertex(vertices.get(i));
				}
			}

			output.getPropertyBean().setValue("schedulable", true);
			flattenGraph(output, newDepth);

		} else {
			return;
		}
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
		if (needRoundBuffer) {
			SDFRoundBufferVertex roundBuffer = new SDFRoundBufferVertex();
			SDFSourceInterfaceVertex input = new SDFSourceInterfaceVertex();
			input.setName("in");
			SDFSinkInterfaceVertex output = new SDFSinkInterfaceVertex();
			output.setName("out");
			roundBuffer.setName("roundBuffer_" + vertex.getName());
			parentGraph.addVertex(roundBuffer);
			SDFEdge edge = (SDFEdge) parentGraph.addEdge(roundBuffer, vertex);
			edge.copyProperties(inEdges.get(0));
			// The modifier of the source port should not be copied.
			edge.setSourcePortModifier(null);
			edge.setProd(new SDFIntEdgePropertyType(inEdges.get(0).getCons()
					.intValue()));
			edge.setCons(new SDFIntEdgePropertyType(inEdges.get(0).getCons()
					.intValue()));
			edge.setSourceInterface(output);
			while (inEdges.size() > 0) {
				SDFEdge treatEdge = inEdges.get(0);
				SDFAbstractVertex source = treatEdge.getSource();
				SDFEdge newEdge = (SDFEdge) parentGraph.addEdge(source,
						roundBuffer);
				newEdge.copyProperties(treatEdge);
				// The modifier of the target port should not be copied.
				edge.setTargetPortModifier(null);
				newEdge.setProd(new SDFIntEdgePropertyType(treatEdge.getProd()
						.intValue()));
				newEdge.setCons(new SDFIntEdgePropertyType(treatEdge.getProd()
						.intValue()
						* treatEdge.getSource().getNbRepeatAsInteger()));
				newEdge.setTargetInterface(input);
				newEdge.setSourceInterface(treatEdge.getSourceInterface());
				parentGraph.removeEdge(treatEdge);
				inEdges.remove(0);
			}
		} else if (needImplode && depth == 0) {
			SDFJoinVertex implodeBuffer = new SDFJoinVertex();
			SDFSourceInterfaceVertex input = new SDFSourceInterfaceVertex();
			input.setName("in");
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
			while (inEdges.size() > 0) {
				SDFEdge treatEdge = inEdges.get(0);
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
			SDFBroadcastVertex broadcast = new SDFBroadcastVertex();
			SDFSourceInterfaceVertex input = new SDFSourceInterfaceVertex();
			input.setName("in");
			SDFSinkInterfaceVertex output = new SDFSinkInterfaceVertex();
			output.setName("out");
			broadcast.setName("broadcast_" + vertex.getName());
			parentGraph.addVertex(broadcast);
			SDFEdge edge = (SDFEdge) parentGraph.addEdge(vertex, broadcast);
			edge.copyProperties(outEdges.get(0));
			// The modifier of the target port should not be copied.
			edge.setTargetPortModifier(null);
			edge.setProd(new SDFIntEdgePropertyType(outEdges.get(0).getProd()
					.intValue()));
			edge.setCons(new SDFIntEdgePropertyType(outEdges.get(0).getProd()
					.intValue()));
			edge.setTargetInterface(input);
			while (outEdges.size() > 0) {
				SDFEdge treatEdge = outEdges.get(0);
				SDFAbstractVertex target = treatEdge.getTarget();
				SDFEdge newEdge = (SDFEdge) parentGraph.addEdge(broadcast,
						target);
				newEdge.copyProperties(treatEdge);
				// The modifier of the source port should not be copied.
				edge.setSourcePortModifier(null);
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

		} else if (needExplode && depth == 0) {
			SDFForkVertex explode = new SDFForkVertex();
			SDFSourceInterfaceVertex input = new SDFSourceInterfaceVertex();
			input.setName("in");
			SDFSinkInterfaceVertex output = new SDFSinkInterfaceVertex();
			output.setName("out");
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
			while (outEdges.size() > 0) {
				SDFEdge treatEdge = outEdges.get(0);
				SDFAbstractVertex target = treatEdge.getTarget();
				SDFEdge newEdge = (SDFEdge) parentGraph
						.addEdge(explode, target);
				newEdge.copyProperties(treatEdge);
				// The modifier of the source port should not be copied.
				edge.setSourcePortModifier(null);
				newEdge.setCons(new SDFIntEdgePropertyType(treatEdge.getCons()
						.intValue()));
				newEdge.setProd(new SDFIntEdgePropertyType(treatEdge.getCons()
						.intValue()
						* treatEdge.getTarget().getNbRepeatAsInteger()));
				newEdge.setSourceInterface(output);
				newEdge.setTargetInterface(treatEdge.getTargetInterface());
				newEdge.setDataType(treatEdge.getDataType());
				parentGraph.removeEdge(treatEdge);
				outEdges.remove(0);
			}

		}
	}

}
