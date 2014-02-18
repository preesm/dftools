package net.sf.dftools.algorithm.optimisations.clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.SDFVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

/**
 * Contains static method for graph clustering
 * 
 * @author jpiat
 * 
 */
public class Clusterize {

	/**
	 * Clusterize the givens vertices in the given graph
	 * 
	 * @param graph
	 *            The graph in which to cluster
	 * @param block
	 *            The block of vertex to cluster
	 * @param name
	 *            The name to apply to the cluster
	 * @return The created vertex
	 * @throws InvalidExpressionException 
	 * @throws SDF4JException 
	 */
	public static SDFAbstractVertex culsterizeBlocks(SDFGraph graph,
			List<SDFAbstractVertex> block, String name) throws InvalidExpressionException, SDF4JException {
		if (block.size() > 1) {
			SDFGraph clusterGraph = new SDFGraph();
			clusterGraph.setName(name);
			SDFVertex cluster = new SDFVertex();
			cluster.setName(name);
			cluster.setGraphDescription(clusterGraph);
			graph.addVertex(cluster);
			for (int r = 0; r < block.size(); r++) {
				SDFAbstractVertex seed = block.get(r);
				clusterGraph.addVertex(seed);
				List<SDFEdge> outgoingEdges = new ArrayList<SDFEdge>(graph
						.outgoingEdgesOf(seed));
				for (SDFEdge edge : outgoingEdges) {
					SDFAbstractVertex target = graph.getEdgeTarget(edge);
					if (block.contains(target)) {
						if (!clusterGraph.vertexSet().contains(target)) {
							clusterGraph.addVertex(target);
						}
						SDFEdge newEdge = clusterGraph.addEdge(seed, target);
						newEdge.copyProperties(edge);
					} else {
						SDFInterfaceVertex targetPort = new SDFSinkInterfaceVertex();
						targetPort.setName(cluster.getName() + "_"
								+ edge.getTargetInterface().getName());
						cluster.addSink(targetPort);
						SDFEdge extEdge = graph.addEdge(cluster, target);
						extEdge.copyProperties(edge);
						extEdge.setSourceInterface(targetPort);
						cluster.setInterfaceVertexExternalLink(extEdge, targetPort);
						SDFEdge newEdge = clusterGraph
								.addEdge(seed, targetPort);
						newEdge.copyProperties(edge);
						newEdge.setCons(new SDFIntEdgePropertyType(newEdge.getProd().intValue()));
						graph.removeEdge(edge);
					}
				}
				List<SDFEdge> incomingEdges = new ArrayList<SDFEdge>(graph
						.incomingEdgesOf(seed));
				for (SDFEdge edge : incomingEdges) {
					SDFAbstractVertex source = graph.getEdgeSource(edge);
					if (!block.contains(source)) {
						SDFInterfaceVertex sourcePort = new SDFSourceInterfaceVertex();
						sourcePort.setName(cluster.getName() + "_"
								+ edge.getSourceInterface().getName());
						cluster.addSource(sourcePort);
						SDFEdge extEdge = graph.addEdge(source, cluster);
						extEdge.copyProperties(edge);
						extEdge.setTargetInterface(sourcePort);
						cluster.setInterfaceVertexExternalLink(extEdge, sourcePort);
						SDFEdge newEdge = clusterGraph
								.addEdge(sourcePort, seed);
						newEdge.copyProperties(edge);
						newEdge.setProd(newEdge.getCons());
						graph.removeEdge(edge);
					}
				}
				graph.removeVertex(seed);
			}
			clusterGraph.validateModel(Logger.getAnonymousLogger());
			graph.validateModel(Logger.getAnonymousLogger());		
			return cluster;
		}
		return null;
	}

}
