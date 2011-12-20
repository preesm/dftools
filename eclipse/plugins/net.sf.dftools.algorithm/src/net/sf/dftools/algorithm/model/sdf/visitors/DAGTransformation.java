package net.sf.dftools.algorithm.model.sdf.visitors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import net.sf.dftools.algorithm.SDFMath;
import net.sf.dftools.algorithm.demo.SDFAdapterDemo;
import net.sf.dftools.algorithm.demo.SDFtoDAGDemo;
import net.sf.dftools.algorithm.exceptions.CreateCycleException;
import net.sf.dftools.algorithm.exceptions.CreateMultigraphException;
import net.sf.dftools.algorithm.factories.DAGVertexFactory;
import net.sf.dftools.algorithm.factories.ModelVertexFactory;
import net.sf.dftools.algorithm.generator.SDFRandomGraph;
import net.sf.dftools.algorithm.importer.GMLSDFImporter;
import net.sf.dftools.algorithm.importer.InvalidModelException;
import net.sf.dftools.algorithm.iterators.SDFIterator;
import net.sf.dftools.algorithm.model.dag.DAGEdge;
import net.sf.dftools.algorithm.model.dag.DAGVertex;
import net.sf.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import net.sf.dftools.algorithm.model.dag.edag.DAGBroadcastVertex;
import net.sf.dftools.algorithm.model.dag.edag.DAGEndVertex;
import net.sf.dftools.algorithm.model.dag.edag.DAGForkVertex;
import net.sf.dftools.algorithm.model.dag.edag.DAGInitVertex;
import net.sf.dftools.algorithm.model.dag.edag.DAGJoinVertex;
import net.sf.dftools.algorithm.model.dag.types.DAGDefaultEdgePropertyType;
import net.sf.dftools.algorithm.model.dag.types.DAGDefaultVertexPropertyType;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.psdf.PSDFGraph;
import net.sf.dftools.algorithm.model.psdf.types.PSDFEdgePropertyType;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFBroadcastVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFEndVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFForkVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFInitVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFJoinVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

import org.jgrapht.alg.CycleDetector;

/**
 * Visitor to use to transform a SDF Graph in a Directed Acyclic Graph
 * 
 * @author pthebault
 * @param <T>
 *            The DAG type of the output dag
 * 
 */
public class DAGTransformation<T extends DirectedAcyclicGraph> implements
		GraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	/**
	 * Main method for debug purposes ...
	 * 
	 * @param args
	 * @throws InvalidExpressionException
	 * @throws SDF4JException
	 */
	public static void main(String[] args) throws InvalidExpressionException,
			SDF4JException {
		int nbVertex = 30, minInDegree = 1, maxInDegree = 5, minOutDegree = 1, maxOutDegree = 5;
		SDFtoDAGDemo applet = new SDFtoDAGDemo();
		SDFAdapterDemo applet2 = new SDFAdapterDemo();

		// Creates a random SDF graph
		int minrate = 1, maxrate = 100;
		SDFRandomGraph test = new SDFRandomGraph();

		SDFGraph demoGraph = test.createRandomGraph(nbVertex, minInDegree,
				maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate);
		GMLSDFImporter importer = new GMLSDFImporter();
		try {
			/*
			 * demoGraph = importer .parse(new File(
			 * "D:\\Preesm\\trunk\\tests\\IDCT2D\\idct2dCadOptim.graphml"));
			 */
			demoGraph = importer
					.parse(new File(
							"D:\\Preesm\\trunk\\tests\\RACH_Hierarchy\\RACH_Hierarchy\\flatten.graphml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAGTransformation<DirectedAcyclicGraph> dageur = new DAGTransformation<DirectedAcyclicGraph>(
				new DirectedAcyclicGraph(), DAGVertexFactory.getInstance());
		try {
			demoGraph.accept(dageur);
		} catch (SDF4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DirectedAcyclicGraph dag = dageur.getOutput();
		CycleDetector<DAGVertex, DAGEdge> detectCycles = new CycleDetector<DAGVertex, DAGEdge>(
				dag);
		System.out.println("DAG contains cycles  = "
				+ detectCycles.detectCycles());
		applet.init(dag);

		applet2.init(demoGraph);
	}

	private T outputGraph;
	private ModelVertexFactory<DAGVertex> factory;

	/**
	 * Builds a new DAGTransformation visitor,
	 * 
	 * @param outputGraph
	 *            The graph in which the DAG will be output
	 * @param vertexFactory
	 *            The factory used to create vertices
	 */
	public DAGTransformation(T outputGraph,
			ModelVertexFactory<DAGVertex> vertexFactory) {
		this.outputGraph = outputGraph;
		factory = vertexFactory;
	}

	/**
	 * Copy the cycles nb times in the graph
	 * 
	 * @param graph
	 *            The graph in which the cycle should be copied
	 * @param vertices
	 *            The set of vertices of the cycle
	 * @param nb
	 *            The number of copy to produce
	 * @throws InvalidExpressionException
	 */
	private void copyCycle(SDFGraph graph, Set<SDFAbstractVertex> vertices,
			int nb) throws InvalidExpressionException {
		SDFAbstractVertex root = null;
		SDFAbstractVertex last = null;
		SDFEdge loop = null;
		for (SDFAbstractVertex vertex : vertices) {
			vertex.setNbRepeat(vertex.getNbRepeatAsInteger() / nb);
			for (SDFEdge edge : graph.incomingEdgesOf(vertex)) {
				if (edge.getDelay().intValue() > 0) {
					root = edge.getTarget();
					last = edge.getSource();
					loop = edge;
				}
			}
		}
		HashMap<SDFAbstractVertex, List<SDFAbstractVertex>> mapCopies = new HashMap<SDFAbstractVertex, List<SDFAbstractVertex>>();
		List<SDFAbstractVertex> createdVertices = new ArrayList<SDFAbstractVertex>();
		List<SDFAbstractVertex> sortedCycle = new ArrayList<SDFAbstractVertex>();
		SDFIterator iterator = new SDFIterator(graph, root);
		while (iterator.hasNext()) {
			SDFAbstractVertex next = iterator.next();
			if (vertices.contains(next) && !sortedCycle.contains(next)) {
				sortedCycle.add(next);
			}
			if (next == last) {
				break;
			}
		}
		if (root != null && last != null) {
			SDFAbstractVertex previous = last;
			SDFAbstractVertex previousCopy = last;
			for (int i = 1; i < nb; i++) {
				for (SDFAbstractVertex current : sortedCycle) {
					SDFAbstractVertex copy = current.clone();
					if (mapCopies.get(current) == null) {
						mapCopies.put(current,
								new ArrayList<SDFAbstractVertex>());
					}
					mapCopies.get(current).add(copy);
					createdVertices.add(copy);
					copy.setName(copy.getName() + "_" + i);
					graph.addVertex(copy);
					for (SDFEdge edge : graph.getAllEdges(previous, current)) {
						SDFEdge newEdge = graph.addEdge(previousCopy, copy);
						newEdge.copyProperties(edge);
						if (newEdge.getDelay().intValue() > 0) {
							newEdge.setDelay(new SDFIntEdgePropertyType(0));
						}
					}
					for (SDFEdge edge : graph.incomingEdgesOf(current)) {
						if (edge.getSource() != previous
								&& !sortedCycle.contains(edge.getSource())
								&& !createdVertices.contains(edge.getSource())) {
							SDFEdge newEdge = graph.addEdge(edge.getSource(),
									copy);
							newEdge.copyProperties(edge);
							edge.setProd(new SDFIntEdgePropertyType(edge
									.getCons().intValue()));
						} else if (edge.getSource() != previous
								&& sortedCycle.contains(edge.getSource())
								&& !createdVertices.contains(edge.getSource())) {
							SDFEdge newEdge = graph.addEdge(
									mapCopies.get(edge.getSource()).get(i - 1),
									copy);
							newEdge.copyProperties(edge);
						}
					}
					List<SDFEdge> edges = new ArrayList<SDFEdge>(
							graph.outgoingEdgesOf(current));
					for (int k = 0; k < edges.size(); k++) {
						SDFEdge edge = edges.get(k);
						if (!sortedCycle.contains(edge.getTarget())
								&& !createdVertices.contains(edge.getTarget())) {
							// if(! (edge.getTarget() instanceof
							// SDFRoundBufferVertex)){ // need improvements
							SDFEdge newEdge = graph.addEdge(copy,
									edge.getTarget());
							newEdge.copyProperties(edge);
							edge.setCons(new SDFIntEdgePropertyType(edge
									.getProd().intValue()));
						}
					}
					previousCopy = copy;
					previous = current;
				}
			}
		}
		SDFInitVertex initVertex = new SDFInitVertex();
		initVertex.setName(loop.getTarget().getName() + "_init_"
				+ loop.getTargetInterface().getName());
		SDFSinkInterfaceVertex sink_init = new SDFSinkInterfaceVertex();
		sink_init.setName(loop.getSourceInterface().getName());
		initVertex.addSink(sink_init);
		initVertex.setNbRepeat(1);
		graph.addVertex(initVertex);

		SDFEndVertex endVertex = new SDFEndVertex();
		endVertex.setName(loop.getSource().getName() + "_end_"
				+ loop.getSourceInterface().getName());
		SDFSourceInterfaceVertex source_end = new SDFSourceInterfaceVertex();
		source_end.setName(loop.getTargetInterface().getName());
		endVertex.addSource(source_end);
		endVertex.setNbRepeat(1);
		initVertex.setEndReference(endVertex);
		initVertex.setInitSize(loop.getDelay().intValue());
		endVertex.setEndReference(initVertex);
		graph.addVertex(endVertex);

		SDFEdge initEdge = graph.addEdge(initVertex, loop.getTarget());
		initEdge.copyProperties(loop);
		initEdge.setSourceInterface(sink_init);
		initEdge.setDelay(new SDFIntEdgePropertyType(0));

		SDFEdge endEdge = graph.addEdge(
				createdVertices.get(createdVertices.size() - 1), endVertex);
		endEdge.copyProperties(loop);
		endEdge.setTargetInterface(source_end);
		endEdge.setDelay(new SDFIntEdgePropertyType(0));
		graph.removeEdge(loop);
	}

	private int gcdOfVerticesVrb(Set<SDFAbstractVertex> vertices)
			throws InvalidExpressionException {
		int gcd = 0;
		for (SDFAbstractVertex vertex : vertices) {
			if (gcd == 0) {
				gcd = vertex.getNbRepeatAsInteger();
			} else {
				gcd = SDFMath.gcd(gcd, vertex.getNbRepeatAsInteger());
			}
		}
		return gcd;
	}

	/**
	 * GIves this visitor output
	 * 
	 * @return The output of the visitor
	 */
	public T getOutput() {
		return outputGraph;
	}

	private void transformsTop(SDFGraph graph) throws SDF4JException {
		try {
			if (graph.validateModel(Logger.getAnonymousLogger())) {
				// insertImplodeExplodesVertices(graph);
				outputGraph.copyProperties(graph);
				outputGraph.setCorrespondingSDFGraph(graph);
				for (DAGVertex vertex : outputGraph.vertexSet()) {
					vertex.setNbRepeat(new DAGDefaultVertexPropertyType(graph
							.getVertex(vertex.getName()).getNbRepeatAsInteger()));
				}
				DAGEdge newedge;
				for (SDFEdge edge : graph.edgeSet()) {
					if (edge.getDelay().intValue() == 0
							&& !(edge.getDelay() instanceof PSDFEdgePropertyType)) {
						try {
							if (outputGraph.containsEdge(outputGraph
									.getVertex(edge.getSource().getName()),
									outputGraph.getVertex(edge.getTarget()
											.getName()))) {
								newedge = outputGraph.getEdge(outputGraph
										.getVertex(edge.getSource().getName()),
										outputGraph.getVertex(edge.getTarget()
												.getName()));
								newedge.getAggregate().add(edge);
								DAGDefaultEdgePropertyType weigth = (DAGDefaultEdgePropertyType) newedge
										.getWeight();
								newedge.setWeight(new DAGDefaultEdgePropertyType(
										weigth.intValue()
												+ computeEdgeWeight(edge)));
							} else {
								newedge = outputGraph.addDAGEdge(outputGraph
										.getVertex(edge.getSource().getName()),
										outputGraph.getVertex(edge.getTarget()
												.getName()));
								newedge.getAggregate().add(edge);
								newedge.setWeight(new DAGDefaultEdgePropertyType(
										computeEdgeWeight(edge)));

							}
						} catch (CreateMultigraphException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (CreateCycleException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		} catch (InvalidExpressionException e) {
			throw (new SDF4JException(e.getMessage()));
		}
	}

	int computeEdgeWeight(SDFEdge edge) throws InvalidExpressionException {
		return edge.getCons().intValue()
				* edge.getTarget().getNbRepeatAsInteger();

	}

	/**
	 * Treat the cycles in the graph
	 * 
	 * @param graph
	 *            The graph to treat
	 * @throws InvalidExpressionException
	 */
	private void treatCycles(SDFGraph graph) throws InvalidExpressionException {
		List<Set<SDFAbstractVertex>> cycles = new ArrayList<Set<SDFAbstractVertex>>();
		CycleDetector<SDFAbstractVertex, SDFEdge> detector = new CycleDetector<SDFAbstractVertex, SDFEdge>(
				graph);
		List<SDFAbstractVertex> vertices = new ArrayList<SDFAbstractVertex>(
				graph.vertexSet());
		while (vertices.size() > 0) {
			SDFAbstractVertex vertex = vertices.get(0);
			Set<SDFAbstractVertex> cycle = detector
					.findCyclesContainingVertex(vertex);
			if (cycle.size() > 0) {
				vertices.removeAll(cycle);
				cycles.add(cycle);
			}
			vertices.remove(vertex);
		}

		for (Set<SDFAbstractVertex> cycle : cycles) {
			int gcd = gcdOfVerticesVrb(cycle);
			if (gcd > 1 && !(graph instanceof PSDFGraph)) {
				copyCycle(graph, cycle, gcd);
			} else if (!(graph instanceof PSDFGraph)) {
				List<SDFEdge> loops = new ArrayList<SDFEdge>();
				for (SDFAbstractVertex vertex : cycle) {
					for (SDFEdge edge : graph.incomingEdgesOf(vertex)) {
						if (edge.getDelay().intValue() > 0) {
							loops.add(edge);
						}
					}
				}
				for (SDFEdge loop : loops) {
					SDFInitVertex initVertex = new SDFInitVertex();
					initVertex.setName(loop.getTarget().getName() + "_init_"
							+ loop.getTargetInterface().getName());
					SDFSinkInterfaceVertex sink_init = new SDFSinkInterfaceVertex();
					sink_init.setName("init_out");
					initVertex.addSink(sink_init);
					initVertex.setNbRepeat(1);
					graph.addVertex(initVertex);

					SDFEndVertex endVertex = new SDFEndVertex();
					endVertex.setName(loop.getSource().getName() + "_end_"
							+ loop.getSourceInterface().getName());
					SDFSourceInterfaceVertex source_end = new SDFSourceInterfaceVertex();
					source_end.setName("end_in");
					endVertex.addSource(source_end);
					endVertex.setNbRepeat(1);
					initVertex.setEndReference(endVertex);
					initVertex.setInitSize(loop.getDelay().intValue());
					endVertex.setEndReference(initVertex);
					graph.addVertex(endVertex);

					SDFEdge initEdge = graph.addEdge(initVertex,
							loop.getTarget());
					initEdge.copyProperties(loop);
					initEdge.setSourceInterface(sink_init);
					initEdge.setDelay(new SDFIntEdgePropertyType(0));

					SDFEdge endEdge = graph
							.addEdge(loop.getSource(), endVertex);
					endEdge.copyProperties(loop);
					endEdge.setTargetInterface(source_end);
					endEdge.setDelay(new SDFIntEdgePropertyType(0));
					graph.removeEdge(loop);
				}
			}
		}
		// SDFIterator sdfIterator = new SDFIterator(graph);
		// List<SDFAbstractVertex> orderedList = new
		// ArrayList<SDFAbstractVertex>();
		// while (sdfIterator.hasNext()) {
		// SDFAbstractVertex current = sdfIterator.next();
		// orderedList.add(current);
		// if (current instanceof SDFRoundBufferVertex) {
		// int nbTokens = 0;
		// for (SDFEdge edgeData : graph.outgoingEdgesOf(current)) {
		// nbTokens = edgeData.getProd().intValue();
		// }
		// for (int i = orderedList.size() - 1; i >= 0; i--) {
		// if (graph.getAllEdges(orderedList.get(i), current).size() == 1) {
		// if (nbTokens <= 0) {
		// graph.removeAllEdges(orderedList.get(i), current);
		// } else {
		// for (SDFEdge thisEdge : graph.getAllEdges(
		// orderedList.get(i), current)) {
		// nbTokens = nbTokens
		// - thisEdge.getProd().intValue();
		// }
		// }
		// }
		// }
		// // traiter le roundBuffer pour le supprimer
		// if (graph.incomingEdgesOf(current).size() == 1
		// && graph.outgoingEdgesOf(current).size() == 1) {
		// SDFAbstractVertex source = ((SDFEdge) graph
		// .incomingEdgesOf(current).toArray()[0]).getSource();
		// SDFEdge oldEdge = ((SDFEdge) graph.incomingEdgesOf(current)
		// .toArray()[0]);
		// SDFAbstractVertex target = ((SDFEdge) graph
		// .outgoingEdgesOf(current).toArray()[0]).getTarget();
		// SDFEdge refEdge = ((SDFEdge) graph.outgoingEdgesOf(current)
		// .toArray()[0]);
		// SDFEdge newEdge = graph.addEdge(source, target);
		// newEdge.copyProperties(refEdge);
		// graph.removeEdge(refEdge);
		// graph.removeEdge(oldEdge);
		// graph.removeVertex(current);
		// orderedList.remove(current);
		// } else if (graph.incomingEdgesOf(current).size() == 1
		// && graph.outgoingEdgesOf(current).size() > 1) {
		//
		// } else if (graph.incomingEdgesOf(current).size() > 1
		// && graph.outgoingEdgesOf(current).size() == 1) {
		//
		// }
		// }
		// }
	}

	public void treatDelays(SDFGraph graph) {
		ArrayList<SDFEdge> edges = new ArrayList<SDFEdge>(graph.edgeSet());
		while (edges.size() > 0) {
			SDFEdge edge = edges.get(0);
			try {
				if (edge.getDelay().intValue() > 0
						&& !(edge.getDelay() instanceof PSDFEdgePropertyType)) {
					SDFInitVertex initVertex = new SDFInitVertex();
					initVertex.setName(edge.getTarget().getName() + "_init_"
							+ edge.getTargetInterface().getName());
					SDFSinkInterfaceVertex sink_init = new SDFSinkInterfaceVertex();
					sink_init.setName("init_out");
					initVertex.addSink(sink_init);
					initVertex.setNbRepeat(1);
					graph.addVertex(initVertex);

					SDFEndVertex endVertex = new SDFEndVertex();
					endVertex.setName(edge.getSource().getName() + "_end_"
							+ edge.getSourceInterface().getName());
					SDFSourceInterfaceVertex source_end = new SDFSourceInterfaceVertex();
					source_end.setName("end_in");
					endVertex.addSource(source_end);
					endVertex.setNbRepeat(1);
					initVertex.setEndReference(endVertex);
					initVertex.setInitSize(edge.getDelay().intValue());
					endVertex.setEndReference(initVertex);
					graph.addVertex(endVertex);

					SDFEdge initEdge = graph.addEdge(initVertex,
							edge.getTarget());
					initEdge.copyProperties(edge);
					initEdge.setSourceInterface(sink_init);
					initEdge.setDelay(new SDFIntEdgePropertyType(0));
					// initEdge.setProd(edge.getDelay());

					SDFEdge endEdge = graph
							.addEdge(edge.getSource(), endVertex);
					endEdge.copyProperties(edge);
					endEdge.setTargetInterface(source_end);
					endEdge.setDelay(new SDFIntEdgePropertyType(0));
					graph.removeEdge(edge);
				}
			} catch (InvalidExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			edges.remove(0);
		}
	}

	public void visit(SDFEdge sdfEdge) {
	}

	public void visit(SDFGraph sdf) throws SDF4JException {
		try {
			treatCycles(sdf);
			treatDelays(sdf);
			ArrayList<SDFAbstractVertex> vertices = new ArrayList<SDFAbstractVertex>(
					sdf.vertexSet());
			for (int i = 0; i < vertices.size(); i++) {
				vertices.get(i).accept(this);
			}
			sdf.getPropertyBean().setValue("schedulable", true);
			transformsTop(sdf);
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw (new SDF4JException(e.getMessage()));
		}
	}

	public void visit(SDFAbstractVertex sdfVertex) throws SDF4JException {
		DAGVertex vertex;
		if (sdfVertex instanceof SDFBroadcastVertex) {
			vertex = factory
					.createVertex(DAGBroadcastVertex.DAG_BROADCAST_VERTEX);
		} else if (sdfVertex instanceof SDFForkVertex) {
			vertex = factory.createVertex(DAGForkVertex.DAG_FORK_VERTEX);
		} else if (sdfVertex instanceof SDFJoinVertex) {
			vertex = factory.createVertex(DAGJoinVertex.DAG_JOIN_VERTEX);
		} else if (sdfVertex instanceof SDFEndVertex) {
			vertex = factory.createVertex(DAGEndVertex.DAG_END_VERTEX);
		} else if (sdfVertex instanceof SDFInitVertex) {
			vertex = factory.createVertex(DAGInitVertex.DAG_INIT_VERTEX);
			if (outputGraph.getVertex(((SDFInitVertex) sdfVertex)
					.getEndReference().getName()) != null) {
				((DAGInitVertex) vertex)
						.setEndReference((DAGEndVertex) outputGraph
								.getVertex(((SDFInitVertex) sdfVertex)
										.getEndReference().getName()));
			}
		} else {
			vertex = factory.createVertex(DAGVertex.DAG_VERTEX);
		}
		vertex.setName(sdfVertex.getName());
		vertex.setTime(new DAGDefaultVertexPropertyType(0));
		vertex.setNbRepeat(new DAGDefaultVertexPropertyType(0));
		vertex.setCorrespondingSDFVertex(sdfVertex);
		outputGraph.addVertex(vertex);
	}

}
