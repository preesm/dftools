/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Jonathan Piat <jpiat@laas.fr> (2011)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
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
	private final CycleDetector<DAGVertex, DAGEdge>	cycleDetector;
	private final HashMap<DAGVertex, DAGEdge>		cycleVertex;
	private final DirectedAcyclicGraph				graph;
	private final ArrayList<DAGVertex>				stack;
	private final Vector<DAGVertex>					treated;

	/**
	 * Creates a new DAGIterator on the given DAGGraph
	 *
	 * @param graph
	 *            THe graph to iterate over
	 */
	public DAGIterator(final DirectedAcyclicGraph graph) {
		this.graph = graph;
		this.cycleDetector = new CycleDetector<>(graph);
		this.stack = new ArrayList<>();
		this.cycleVertex = new HashMap<>();
		this.treated = new Vector<>();
		for (final DAGVertex vertex : graph.vertexSet()) {
			if (graph.incomingEdgesOf(vertex).size() == 0) {
				this.stack.add(vertex);
			}
		}
	}

	@Override
	public void addTraversalListener(final TraversalListener<DAGVertex, DAGEdge> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasNext() {
		if (this.stack.size() == 0) {
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
			final DAGVertex next = this.stack.get(0);
			this.treated.add(next);
			final Set<DAGEdge> outgoingEdges = this.graph.outgoingEdgesOf(next);
			final Set<DAGVertex> cycle = this.cycleDetector.findCyclesContainingVertex(next);
			for (final DAGVertex vertex : cycle) {
				if (this.cycleVertex.get(vertex) != null) {
					cycleTreated = true;
					break;
				}
			}
			if ((cycle.size() != 0) && !cycleTreated) {
				for (final DAGEdge incEdge : this.graph.incomingEdgesOf(next)) {
					if (cycle.contains(this.graph.getEdgeSource(incEdge))) {
						this.cycleVertex.put(next, incEdge);
					}
				}
			}

			for (final DAGEdge edge : outgoingEdges) {
				if (this.graph.getEdgeTarget(edge) != next) {
					if ((this.cycleVertex.get(this.graph.getEdgeTarget(edge)) == null)
							|| (this.graph.getEdgeSource(this.cycleVertex.get(this.graph.getEdgeTarget(edge))) != next)) {
						boolean prevTreated = true;
						final DAGVertex fol = this.graph.getEdgeTarget(edge);
						for (final DAGEdge incomingEdge : this.graph.incomingEdgesOf(fol)) {
							if (this.graph.getEdgeSource(incomingEdge) != fol) {
								prevTreated = prevTreated && (this.treated.contains(this.graph.getEdgeSource(incomingEdge)));
							}
						}
						if (prevTreated) {
							this.stack.add(this.graph.getEdgeTarget(edge));
						}
					}
				}
			}
			this.stack.remove(0);
			return next;
		}
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTraversalListener(final TraversalListener<DAGVertex, DAGEdge> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReuseEvents(final boolean arg0) {
		// TODO Auto-generated method stub

	}

}
