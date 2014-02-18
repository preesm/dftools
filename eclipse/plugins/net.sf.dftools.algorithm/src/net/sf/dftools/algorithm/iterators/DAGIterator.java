package net.sf.dftools.algorithm.iterators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import net.sf.dftools.algorithm.model.dag.DAGEdge;
import net.sf.dftools.algorithm.model.dag.DAGVertex;
import net.sf.dftools.algorithm.model.dag.DirectedAcyclicGraph;

import org.jgrapht.alg.CycleDetector;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.traverse.GraphIterator;

/**
 * Class used to iterate over a DAG following the dependencies order
 * 
 * @author pthebault
 *
 */
public class DAGIterator implements GraphIterator<DAGVertex, DAGEdge> {
	private CycleDetector<DAGVertex, DAGEdge> cycleDetector;
	private HashMap<DAGVertex, DAGEdge> cycleVertex;
	private DirectedAcyclicGraph graph;
	private ArrayList<DAGVertex> stack;
	private Vector<DAGVertex> treated;

	/**
	 * Creates a new DAGIterator on the given DAGGraph
	 * 
	 * @param graph
	 *            THe graph to iterate over
	 */
	public DAGIterator(DirectedAcyclicGraph graph) {
		this.graph = graph;
		cycleDetector = new CycleDetector<DAGVertex, DAGEdge>(graph);
		stack = new ArrayList<DAGVertex>();
		cycleVertex = new HashMap<DAGVertex, DAGEdge>();
		treated = new Vector<DAGVertex>();
		for (DAGVertex vertex : graph.vertexSet()) {
			if (graph.incomingEdgesOf(vertex).size() == 0) {
				stack.add(vertex);
			}
		}
	}

	@Override
	public void addTraversalListener(
			TraversalListener<DAGVertex, DAGEdge> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasNext() {
		if (stack.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isCrossComponentTraversal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReuseEvents() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DAGVertex next() {
		if (hasNext()) {
			boolean cycleTreated = false;
			DAGVertex next = stack.get(0);
			treated.add(next);
			Set<DAGEdge> outgoingEdges = graph.outgoingEdgesOf(next);
			Set<DAGVertex> cycle = cycleDetector
					.findCyclesContainingVertex(next);
			for (DAGVertex vertex : cycle) {
				if (cycleVertex.get(vertex) != null) {
					cycleTreated = true;
					break;
				}
			}
			if (cycle.size() != 0 && !cycleTreated) {
				for (DAGEdge incEdge : graph.incomingEdgesOf(next)) {
					if (cycle.contains(graph.getEdgeSource(incEdge))) {
						cycleVertex.put(next, incEdge);
					}
				}
			}

			for (DAGEdge edge : outgoingEdges) {
				if (graph.getEdgeTarget(edge) != next) {
					if (cycleVertex.get(graph.getEdgeTarget(edge)) == null
							|| graph.getEdgeSource(cycleVertex.get(graph
									.getEdgeTarget(edge))) != next) {
						boolean prevTreated = true;
						DAGVertex fol = graph.getEdgeTarget(edge);
						for (DAGEdge incomingEdge : graph.incomingEdgesOf(fol)) {
							if (graph.getEdgeSource(incomingEdge) != fol) {
								prevTreated = prevTreated
										&& (treated.contains(graph
												.getEdgeSource(incomingEdge)));
							}
						}
						if (prevTreated) {
							stack.add(graph.getEdgeTarget(edge));
						}
					}
				}
			}
			stack.remove(0);
			return next;
		}
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTraversalListener(
			TraversalListener<DAGVertex, DAGEdge> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReuseEvents(boolean arg0) {
		// TODO Auto-generated method stub

	}



}
