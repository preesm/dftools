/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (%%DATE%%) :
 *
 * %%AUTHORS%%
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
package org.ietr.dftools.algorithm.iterators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.ietr.dftools.algorithm.model.dag.DAGEdge;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
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
