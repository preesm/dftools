package org.ietr.dftools.algorithm.optimisations.loops.hyperplan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.ietr.dftools.algorithm.SDFMath;
import org.ietr.dftools.algorithm.demo.SDFAdapterDemo;
import org.ietr.dftools.algorithm.importer.GMLSDFImporter;
import org.ietr.dftools.algorithm.importer.InvalidModelException;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.jgrapht.alg.StrongConnectivityInspector;

public class HyperplanMethod {

	HashMap<SDFInterfaceVertex, Integer> interfaceToInteger;

	public static void main(String[] args) throws SDF4JException,
			InvalidExpressionException {
		SDFAdapterDemo applet = new SDFAdapterDemo();
		GMLSDFImporter importer = new GMLSDFImporter();
		SDFGraph demoGraph;
		try {
			@SuppressWarnings("unused")
			HashMap<SDFEdge, Integer> edgeToData = new HashMap<SDFEdge, Integer>();
			demoGraph = importer
					.parse(new File(
							"D:\\Preesm\\branches\\psdf\\tests\\ProdMatVect\\Algo\\TestMatVect.graphml"));
			demoGraph.validateModel(Logger.getAnonymousLogger());
			applet.init(demoGraph);
			analyzeGraph(demoGraph, null, new LoopDependencies());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void analyzeGraph(SDFGraph graph, UUID parentUUID,
			LoopDependencies dependencies) throws SDF4JException,
			InvalidExpressionException {
		UUID indexUUID = null;
		clusterizeStronglyConnected(graph);
		for (SDFAbstractVertex oneVertex :graph.vertexSet()) {
			if (oneVertex.getNbRepeat() instanceof Integer && ((Integer) oneVertex.getNbRepeat()) > 1) {
				indexUUID = UUID.randomUUID();
					if ((oneVertex.getNbRepeat() instanceof Integer)
							&& (((Integer) oneVertex.getNbRepeat()) > 1)) {

						for (SDFEdge edge : graph.incomingEdgesOf(oneVertex)) {
							if (edge.getSource() instanceof SDFInterfaceVertex) {

							} else {

								dependencies.addReadDataDependency(oneVertex,
										edge, edge.getDelay().intValue(), edge
												.getCons().intValue(),
										indexUUID);
							}
						}
						for (SDFEdge edge : graph.outgoingEdgesOf(oneVertex)) {
							if (edge.getTarget() instanceof SDFInterfaceVertex) {

							} else {
								dependencies.addWriteDataDependency(oneVertex,
										edge, 0, edge.getProd().intValue(),
										indexUUID);
							}
						}
					}
				
			}
			if (oneVertex.getGraphDescription() != null
					&& oneVertex.getGraphDescription() instanceof SDFGraph) {
				analyzeGraph((SDFGraph) oneVertex
						.getGraphDescription(), indexUUID,
						dependencies);
			}
		}
	}

	public static void clusterizeStronglyConnected(SDFGraph graph)
			throws SDF4JException {
		int i = 0;
		StrongConnectivityInspector<SDFAbstractVertex, SDFEdge> inspector = new StrongConnectivityInspector<SDFAbstractVertex, SDFEdge>(
				graph);
		// runs through the detected cycles
		for (Set<SDFAbstractVertex> strong : inspector.stronglyConnectedSets()) {
			boolean noInterface = true;
			for (SDFAbstractVertex vertex : strong) { // test whether or not the
				// cycle contains an
				// interface
				noInterface &= !(vertex instanceof SDFInterfaceVertex);
			}
			if (noInterface && strong.size() > 1) { // if the cycle has no
				// interface and has a size
				// greater than on, perform
				// the clustering
				try {
					culsterizeLoop(graph, new ArrayList<SDFAbstractVertex>(
							strong), "cluster_" + i);
				} catch (InvalidExpressionException e) {
					e.printStackTrace();
				}
				i++;
			}

		}
	}

	public static SDFAbstractVertex culsterizeLoop(SDFGraph graph,
			List<SDFAbstractVertex> block, String name)
			throws InvalidExpressionException, SDF4JException {
		try {
			graph.validateModel(Logger.getAnonymousLogger());
		} catch (SDF4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		if (block.size() > 1) {
			int pgcd = 0;
			int nbLoopPort = 0;
			boolean hasDelay = false;
			SDFGraph clusterGraph = graph.clone();
			clusterGraph.setName(name);
			SDFVertex cluster = new SDFVertex();
			cluster.setName(name);
			cluster.setGraphDescription(clusterGraph);
			graph.addVertex(cluster);
			HashMap<SDFAbstractVertex, SDFAbstractVertex> copies = new HashMap<SDFAbstractVertex, SDFAbstractVertex>();
			List<SDFAbstractVertex> vertices = new ArrayList<SDFAbstractVertex>(
					clusterGraph.vertexSet());
			for (int i = 0; i < vertices.size(); i++) {
				boolean isInBlock = false;
				for (int r = 0; r < block.size(); r++) {
					if (block.get(r).getName()
							.equals(vertices.get(i).getName())) {
						isInBlock = true;
						copies.put(block.get(r), vertices.get(i));
					}
				}
				if (!isInBlock) {
					clusterGraph.removeVertex(vertices.get(i));
				}
			}
			for (int r = 0; r < block.size(); r++) {
				SDFAbstractVertex seed = copies.get(block.get(r));
				if (pgcd == 0) {
					pgcd = seed.getNbRepeatAsInteger();
				} else {
					pgcd = SDFMath.gcd(pgcd, seed.getNbRepeatAsInteger());
				}
				List<SDFEdge> outgoingEdges = new ArrayList<SDFEdge>(graph
						.outgoingEdgesOf(block.get(r)));
				for (SDFEdge edge : outgoingEdges) {
					SDFAbstractVertex target = graph.getEdgeTarget(edge);
					if (!block.contains(target)) {
						SDFInterfaceVertex targetPort = new SDFSinkInterfaceVertex();
						targetPort.setName(cluster.getName() + "_"
								+ edge.getTargetInterface().getName());
						int i = 0;
						String portName = targetPort.getName();
						while (!cluster.addSink(targetPort)) {
							targetPort.setName(portName + "_" + i);
							i++;
						}
						SDFEdge extEdge = graph.addEdge(cluster, target);
						extEdge.copyProperties(edge);
						extEdge.setSourceInterface(targetPort);
						cluster.setInterfaceVertexExternalLink(extEdge,
								targetPort);
						SDFEdge newEdge = clusterGraph
								.addEdge(seed, targetPort);
						newEdge.copyProperties(edge);
						newEdge.setCons(new SDFIntEdgePropertyType(newEdge
								.getProd().intValue()));
						graph.removeEdge(edge);
					}
				}
				List<SDFEdge> incomingEdges = new ArrayList<SDFEdge>(graph
						.incomingEdgesOf(block.get(r)));
				for (SDFEdge edge : incomingEdges) {
					SDFAbstractVertex source = graph.getEdgeSource(edge);
					SDFAbstractVertex target = graph.getEdgeTarget(edge);
					if (block.contains(source) && block.contains(target)
							&& edge.getDelay().intValue() > 0) {
						SDFInterfaceVertex targetPort = new SDFSinkInterfaceVertex();
						targetPort.setName("outLoopPort_" + nbLoopPort);
						SDFInterfaceVertex sourcePort = new SDFSourceInterfaceVertex();
						sourcePort.setName("inLoopPort_" + nbLoopPort);
						nbLoopPort++;
						int i = 0;
						String portName = targetPort.getName();
						while (!cluster.addSink(targetPort)) {
							targetPort.setName(portName + "_" + i);
							i++;
						}

						i = 0;
						portName = sourcePort.getName();
						while (!cluster.addSource(sourcePort)) {
							sourcePort.setName(portName + "_" + i);
							i++;
						}

						SDFEdge loopEdge = graph.addEdge(cluster, cluster);
						loopEdge.copyProperties(edge);
						loopEdge.setSourceInterface(targetPort);
						loopEdge.setTargetInterface(sourcePort);

						SDFEdge lastLoop = clusterGraph.addEdge(copies
								.get(source), targetPort);
						lastLoop.copyProperties(edge);
						lastLoop.setDelay(new SDFIntEdgePropertyType(0));

						SDFEdge firstLoop = clusterGraph.addEdge(sourcePort,
								copies.get(target));
						firstLoop.copyProperties(edge);
						firstLoop.setDelay(new SDFIntEdgePropertyType(0));
						SDFEdge inLoopEdge = clusterGraph.getEdge(copies
								.get(source), copies.get(target));
						if (inLoopEdge.getDelay().intValue() > 0) {
							clusterGraph.removeEdge(inLoopEdge);
						}
						graph.removeEdge(edge);
						hasDelay = true;
					} else if (!block.contains(source)) {
						SDFInterfaceVertex sourcePort = new SDFSourceInterfaceVertex();
						sourcePort.setName(cluster.getName() + "_"
								+ edge.getSource().getName());

						int i = 0;
						String portName = sourcePort.getName();
						while (!cluster.addSource(sourcePort)) {
							sourcePort.setName(portName + "_" + i);
							i++;
						}
						SDFEdge extEdge = graph.addEdge(source, cluster);
						extEdge.copyProperties(edge);
						extEdge.setTargetInterface(sourcePort);
						cluster.setInterfaceVertexExternalLink(extEdge,
								sourcePort);
						SDFEdge newEdge = clusterGraph
								.addEdge(sourcePort, seed);
						newEdge.copyProperties(edge);
						newEdge.setProd(newEdge.getCons());
						graph.removeEdge(edge);
					}
				}
			}
			for (int r = 0; r < block.size(); r++) {
				graph.removeVertex(block.get(r));
			}
			clusterGraph.validateModel(Logger.getAnonymousLogger());
			cluster.setNbRepeat(pgcd);
			for (SDFAbstractVertex vertex : clusterGraph.vertexSet()) {
				if (!(vertex instanceof SDFInterfaceVertex)) {
					vertex.setNbRepeat(vertex.getNbRepeatAsInteger() / pgcd);
				}
			}
			if (!hasDelay) {
				throw (new SDF4JException("Cycle with no delay in " + graph));
			}
			return cluster;
		} else {
			return null;
		}
	}
}
