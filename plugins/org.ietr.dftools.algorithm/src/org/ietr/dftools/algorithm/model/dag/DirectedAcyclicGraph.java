/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2011)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2013)
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
package org.ietr.dftools.algorithm.model.dag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.ietr.dftools.algorithm.exceptions.CreateCycleException;
import org.ietr.dftools.algorithm.exceptions.CreateMultigraphException;
import org.ietr.dftools.algorithm.factories.DAGEdgeFactory;
import org.ietr.dftools.algorithm.factories.DAGVertexFactory;
import org.ietr.dftools.algorithm.factories.ModelVertexFactory;
import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.PropertyFactory;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.jgrapht.EdgeFactory;
import org.jgrapht.alg.CycleDetector;

/**
 * Class used to represent a Directed Acyclic Graph
 * 
 * @author jpiat
 * @author kdesnos
 * 
 */
public class DirectedAcyclicGraph extends AbstractGraph<DAGVertex, DAGEdge> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3860891539321306793L;

	private final static String SDF = "sdf";

	/**
	 * Constructs a new DAG graph with the default Dag edge factory
	 */
	public DirectedAcyclicGraph() {
		super(new DAGEdgeFactory());
		this.getPropertyBean().setValue(AbstractGraph.MODEL, "dag");
	}

	/**
	 * Creates a new DirectedAcyclicGraph with the given Edge factory
	 * 
	 * @param arg0
	 *            The factory to use to create Edge in this graph
	 */
	public DirectedAcyclicGraph(EdgeFactory<DAGVertex, DAGEdge> arg0) {
		super(arg0);
		this.getPropertyBean().setValue(AbstractGraph.MODEL, "dag");
	}

	/**
	 * Add an Edge to this Graph
	 * 
	 * @param source
	 *            The source vertex of this edge
	 * @param target
	 *            The target vertex of this edge
	 * @return The created Edge
	 * @throws CreateMultigraphException
	 *             This Edge creates a Multi-graph
	 * @throws CreateCycleException
	 *             This Edge creates a cycle
	 */
	public DAGEdge addDAGEdge(DAGVertex source, DAGVertex target)
			throws CreateMultigraphException, CreateCycleException {
		if (this.getAllEdges(source, target).size() > 0) {
			throw (new CreateMultigraphException());
		} else {
			DAGEdge newEdge = addEdge(source, target);
			CycleDetector<DAGVertex, DAGEdge> detector = new CycleDetector<DAGVertex, DAGEdge>(
					this);
			if (detector.detectCyclesContainingVertex(source)) {
				Set<DAGVertex> cycle = detector
						.findCyclesContainingVertex(source);
				String cycleString = "Added edge forms a cycle: {";
				for (DAGVertex vertex : cycle) {
					cycleString += vertex.getName() + " ";
				}
				cycleString += "}";

				this.removeEdge(newEdge);
				throw ((new CreateCycleException(cycleString)));
			} else if (detector.detectCyclesContainingVertex(target)) {
				Set<DAGVertex> cycle = detector
						.findCyclesContainingVertex(target);
				String cycleString = "Added edge forms a cycle: {";
				for (DAGVertex vertex : cycle) {
					cycleString += vertex.getName() + " ";
				}
				cycleString += "}";

				this.removeEdge(newEdge);
				throw ((new CreateCycleException(cycleString)));
			}
			return newEdge;
		}
	}

	@Override
	public DAGEdge addEdge(DAGVertex source, DAGVertex target) {
		DAGEdge edge = super.addEdge(source, target);
		return edge;
	}

	@Override
	public boolean addVertex(DAGVertex vertex) {
		return super.addVertex(vertex);
	}

	/**
	 * Gives the DAGVertex with the given name in the graph
	 * 
	 * @param name
	 *            The name of the vertex we want to obtain
	 * @return The DAG vertex with the given name
	 */
	@Override
	public DAGVertex getVertex(String name) {
		for (DAGVertex vertex : vertexSet()) {
			if (vertex.getName().equals(name)) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Test if this graph respect the DAG rules
	 * 
	 * @return trues if this graph is DAG compliant
	 */
	public boolean isDAG() {
		CycleDetector<DAGVertex, DAGEdge> detector = new CycleDetector<DAGVertex, DAGEdge>(
				this);
		return !detector.detectCycles();
	}

	@Override
	public void update(AbstractGraph<?, ?> observable, Object arg) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public AbstractGraph clone() {
		return null;
	}

	@Override
	public boolean validateModel(Logger logger) throws SDF4JException {
		// TODO Auto-generated method stub
		return true;
	}

	public SDFGraph getCorrespondingSDFGraph() {
		return (SDFGraph) this.getPropertyBean().getValue(SDF);
	}

	public void setCorrespondingSDFGraph(SDFGraph graph) {
		this.getPropertyBean().setValue(SDF, graph);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelVertexFactory getVertexFactory() {
		return DAGVertexFactory.getInstance();
	}

	/**
	 * Returns a set containing all the {@link DAGVertex} that are successors to
	 * the given {@link DAGVertex}.
	 * 
	 * @param vertex
	 *            the {@link DAGVertex} whose successor vertex list is retrieved
	 * @return A {@link Set} containing all {@link DAGVertex} that are
	 *         successors to the given {@link DAGVertex}
	 */
	public Set<DAGVertex> getSuccessorVerticesOf(DAGVertex vertex) {
		// Create a list of the vertex to process
		List<DAGVertex> toProcess = new ArrayList<DAGVertex>();
		Set<DAGVertex> processed = new HashSet<DAGVertex>();
		toProcess.add(vertex);

		// While there is a vertex in the toProcess list
		while (!toProcess.isEmpty()) {
			DAGVertex processedVertex = toProcess.remove(0);
			processed.add(processedVertex);

			// Add all its successors vertices to the toProcess list (unless
			// they were already added or processed)
			for (DAGEdge outEdge : this.outgoingEdgesOf(processedVertex)) {
				DAGVertex target = outEdge.getTarget();
				if (!toProcess.contains(target) && !processed.contains(target)) {
					toProcess.add(target);
				}
			}
		}

		processed.remove(vertex);
		return processed;
	}

	/**
	 * Returns a set containing all the edges that are successors to the given
	 * {@link DAGVertex}.
	 * 
	 * @param vertex
	 *            the {@link DAGVertex} whose successor edge list is retrieved
	 * @return A {@link Set} containing all {@link DAGEdge} that are successors
	 *         to the given {@link DAGVertex}
	 */
	public Set<DAGEdge> getSuccessorEdgesOf(DAGVertex vertex) {
		Set<DAGEdge> result = new HashSet<DAGEdge>();

		// Create a list of the vertex to process
		List<DAGVertex> toProcess = new ArrayList<DAGVertex>();
		Set<DAGVertex> processed = new HashSet<DAGVertex>();
		toProcess.add(vertex);

		// While there is a vertex in the toProcess list
		while (!toProcess.isEmpty()) {
			DAGVertex processedVertex = toProcess.remove(0);
			processed.add(processedVertex);

			// Add all its outgoing edges to the result
			result.addAll(this.outgoingEdgesOf(processedVertex));

			// Add all its successors vertices to the toProcess list (unless
			// they were already added or processed)
			for (DAGEdge outEdge : this.outgoingEdgesOf(processedVertex)) {
				DAGVertex target = outEdge.getTarget();
				if (!toProcess.contains(target) && !processed.contains(target)) {
					toProcess.add(target);
				}
			}
		}

		return result;
	}

	/**
	 * Returns a set containing all the {@link DAGVertex} that are predecessors
	 * to the given {@link DAGVertex}.
	 * 
	 * @param vertex
	 *            the {@link DAGVertex} whose predecessor vertex list is
	 *            retrieved
	 * @return A {@link Set} containing all {@link DAGVertex} that are
	 *         predecessor to the given {@link DAGVertex}
	 */
	public Set<DAGVertex> getPredecessorVerticesOf(DAGVertex vertex) {
		// Create a list of the vertex to process
		List<DAGVertex> toProcess = new ArrayList<DAGVertex>();
		Set<DAGVertex> processed = new HashSet<DAGVertex>();
		toProcess.add(vertex);

		// While there is a vertex in the toProcess list
		while (!toProcess.isEmpty()) {
			DAGVertex processedVertex = toProcess.remove(0);
			processed.add(processedVertex);

			// Add all its predecessors vertices to the toProcess list (unless
			// they were already added or processed)
			for (DAGEdge inEdge : this.incomingEdgesOf(processedVertex)) {
				DAGVertex source = inEdge.getSource();
				if (!toProcess.contains(source) && !processed.contains(source)) {
					toProcess.add(source);
				}
			}
		}

		processed.remove(vertex);
		return processed;
	}

	/**
	 * Returns a set containing all the edges that are predecessors to the given
	 * {@link DAGVertex}.
	 * 
	 * @param vertex
	 *            the {@link DAGVertex} whose predecessor edge list is retrieved
	 * @return A {@link Set} containing all {@link DAGEdge} that are predecessor
	 *         to the given {@link DAGVertex}
	 */
	public Set<DAGEdge> getPredecessorEdgesOf(DAGVertex vertex) {
		Set<DAGEdge> result = new HashSet<DAGEdge>();

		// Create a list of the vertex to process
		List<DAGVertex> toProcess = new ArrayList<DAGVertex>();
		Set<DAGVertex> processed = new HashSet<DAGVertex>();
		toProcess.add(vertex);

		// While there is a vertex in the toProcess list
		while (!toProcess.isEmpty()) {
			DAGVertex processedVertex = toProcess.remove(0);
			processed.add(processedVertex);

			// Add all its incoming edges to the result
			result.addAll(this.incomingEdgesOf(processedVertex));

			// Add all its predecessors vertices to the toProcess list (unless
			// they were already added or processed)
			for (DAGEdge inEdge : this.incomingEdgesOf(processedVertex)) {
				DAGVertex source = inEdge.getSource();
				if (!toProcess.contains(source) && !processed.contains(source)) {
					toProcess.add(source);
				}
			}
		}

		return result;
	}

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

}
