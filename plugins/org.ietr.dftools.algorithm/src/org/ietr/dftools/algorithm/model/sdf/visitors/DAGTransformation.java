/**
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2018) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017 - 2018)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Florian Arrestier <florian.arrestier@insa-rennes.fr> (2018)
 * Jonathan Piat <jpiat@laas.fr> (2011 - 2012)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2013 - 2015)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to help prototyping
 * parallel applications using dataflow formalism.
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
 */
package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.ietr.dftools.algorithm.SDFMath;
import org.ietr.dftools.algorithm.exceptions.CreateCycleException;
import org.ietr.dftools.algorithm.exceptions.CreateMultigraphException;
import org.ietr.dftools.algorithm.factories.ModelVertexFactory;
import org.ietr.dftools.algorithm.iterators.SDFIterator;
import org.ietr.dftools.algorithm.model.dag.DAGEdge;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.ietr.dftools.algorithm.model.dag.edag.DAGBroadcastVertex;
import org.ietr.dftools.algorithm.model.dag.edag.DAGEndVertex;
import org.ietr.dftools.algorithm.model.dag.edag.DAGForkVertex;
import org.ietr.dftools.algorithm.model.dag.edag.DAGInitVertex;
import org.ietr.dftools.algorithm.model.dag.edag.DAGJoinVertex;
import org.ietr.dftools.algorithm.model.dag.types.DAGDefaultEdgePropertyType;
import org.ietr.dftools.algorithm.model.dag.types.DAGDefaultVertexPropertyType;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFBroadcastVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFEndVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFForkVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFInitVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFJoinVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.transformations.SpecialActorPortsIndexer;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.ietr.dftools.workflow.WorkflowException;
import org.jgrapht.alg.CycleDetector;

/**
 * Visitor to use to transform a SDF Graph in a Directed Acyclic Graph.
 *
 * @author pthebault
 * @author kdesnos
 * @param <T>
 *          The DAG type of the output dag
 */
public class DAGTransformation<T extends DirectedAcyclicGraph>
    implements IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

  /** The output graph. */
  private final T outputGraph;

  /** The factory. */
  private final ModelVertexFactory<DAGVertex> factory;

  /**
   * Builds a new DAGTransformation visitor,.
   *
   * @param outputGraph
   *          The graph in which the DAG will be output
   * @param vertexFactory
   *          The factory used to create vertices
   */
  public DAGTransformation(final T outputGraph, final ModelVertexFactory<DAGVertex> vertexFactory) {
    this.outputGraph = outputGraph;
    this.factory = vertexFactory;
  }

  /**
   * Copy the cycles nb times in the graph.
   *
   * @param graph
   *          The graph in which the cycle should be copied
   * @param vertices
   *          The set of vertices of the cycle
   * @param nb
   *          The number of copy to produce
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  private void copyCycle(final SDFGraph graph, final Set<SDFAbstractVertex> vertices, final int nb)
      throws InvalidExpressionException {
    SDFAbstractVertex root = null;
    SDFAbstractVertex last = null;
    SDFEdge loop = null;
    for (final SDFAbstractVertex vertex : vertices) {
      vertex.setNbRepeat(vertex.getNbRepeatAsInteger() / nb);
      for (final SDFEdge edge : graph.incomingEdgesOf(vertex)) {
        if (edge.getDelay().intValue() > 0) {
          root = edge.getTarget();
          last = edge.getSource();
          loop = edge;
        }
      }
    }
    final Map<SDFAbstractVertex, List<SDFAbstractVertex>> mapCopies = new LinkedHashMap<>();
    final List<SDFAbstractVertex> createdVertices = new ArrayList<>();
    final List<SDFAbstractVertex> sortedCycle = new ArrayList<>();
    final SDFIterator iterator = new SDFIterator(graph, root);
    while (iterator.hasNext()) {
      final SDFAbstractVertex next = iterator.next();
      if (vertices.contains(next) && !sortedCycle.contains(next)) {
        sortedCycle.add(next);
      }
      if (next == last) {
        break;
      }
    }
    if ((root != null) && (last != null)) {
      SDFAbstractVertex previous = last;
      SDFAbstractVertex previousCopy = last;
      for (int i = 1; i < nb; i++) {
        for (final SDFAbstractVertex current : sortedCycle) {
          final SDFAbstractVertex copy = current.clone();
          if (mapCopies.get(current) == null) {
            mapCopies.put(current, new ArrayList<SDFAbstractVertex>());
          }
          mapCopies.get(current).add(copy);
          createdVertices.add(copy);
          copy.setName(copy.getName() + "_" + i);
          graph.addVertex(copy);
          for (final SDFEdge edge : graph.getAllEdges(previous, current)) {
            final SDFEdge newEdge = graph.addEdge(previousCopy, copy);
            newEdge.copyProperties(edge);
            if (newEdge.getDelay().intValue() > 0) {
              newEdge.setDelay(new SDFIntEdgePropertyType(0));
            }
          }
          for (final SDFEdge edge : graph.incomingEdgesOf(current)) {
            if ((edge.getSource() != previous) && !sortedCycle.contains(edge.getSource())
                && !createdVertices.contains(edge.getSource())) {
              final SDFEdge newEdge = graph.addEdge(edge.getSource(), copy);
              newEdge.copyProperties(edge);
              edge.setProd(new SDFIntEdgePropertyType(edge.getCons().intValue()));
            } else if ((edge.getSource() != previous) && sortedCycle.contains(edge.getSource())
                && !createdVertices.contains(edge.getSource())) {
              final SDFEdge newEdge = graph.addEdge(mapCopies.get(edge.getSource()).get(i - 1), copy);
              newEdge.copyProperties(edge);
            }
          }
          final List<SDFEdge> edges = new ArrayList<>(graph.outgoingEdgesOf(current));
          for (int k = 0; k < edges.size(); k++) {
            final SDFEdge edge = edges.get(k);
            if (!sortedCycle.contains(edge.getTarget()) && !createdVertices.contains(edge.getTarget())) {
              // if(! (edge.getTarget() instanceof
              // SDFRoundBufferVertex)){ // need improvements
              final SDFEdge newEdge = graph.addEdge(copy, edge.getTarget());
              newEdge.copyProperties(edge);
              edge.setCons(new SDFIntEdgePropertyType(edge.getProd().intValue()));
            }
          }
          previousCopy = copy;
          previous = current;
        }
      }
    }
    final SDFInitVertex initVertex = new SDFInitVertex();
    initVertex.setName(loop.getTarget().getName() + "_init_" + loop.getTargetInterface().getName());
    final SDFSinkInterfaceVertex sink_init = new SDFSinkInterfaceVertex();
    sink_init.setName(loop.getSourceInterface().getName());
    initVertex.addSink(sink_init);
    initVertex.setNbRepeat(1);
    graph.addVertex(initVertex);

    final SDFEndVertex endVertex = new SDFEndVertex();
    endVertex.setName(loop.getSource().getName() + "_end_" + loop.getSourceInterface().getName());
    final SDFSourceInterfaceVertex source_end = new SDFSourceInterfaceVertex();
    source_end.setName(loop.getTargetInterface().getName());
    endVertex.addSource(source_end);
    endVertex.setNbRepeat(1);
    initVertex.setEndReference(endVertex);
    initVertex.setInitSize(loop.getDelay().intValue());
    endVertex.setEndReference(initVertex);
    graph.addVertex(endVertex);

    final SDFEdge initEdge = graph.addEdge(initVertex, loop.getTarget());
    initEdge.copyProperties(loop);
    initEdge.setSourceInterface(sink_init);
    initEdge.setDelay(new SDFIntEdgePropertyType(0));

    final SDFEdge endEdge = graph.addEdge(createdVertices.get(createdVertices.size() - 1), endVertex);
    endEdge.copyProperties(loop);
    endEdge.setTargetInterface(source_end);
    endEdge.setDelay(new SDFIntEdgePropertyType(0));
    graph.removeEdge(loop);
  }

  /**
   * Gcd of vertices vrb.
   *
   * @param vertices
   *          the vertices
   * @return the int
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  private int gcdOfVerticesVrb(final Set<SDFAbstractVertex> vertices) throws InvalidExpressionException {
    int gcd = 0;
    for (final SDFAbstractVertex vertex : vertices) {
      if (gcd == 0) {
        gcd = vertex.getNbRepeatAsInteger();
      } else {
        gcd = SDFMath.gcd(gcd, vertex.getNbRepeatAsInteger());
      }
    }
    return gcd;
  }

  /**
   * GIves this visitor output.
   *
   * @return The output of the visitor
   */
  public T getOutput() {
    return this.outputGraph;
  }

  /**
   * Transforms top.
   *
   * @param graph
   *          the graph
   * @throws SDF4JException
   *           the SDF 4 J exception
   */
  private void transformsTop(final SDFGraph graph) throws SDF4JException {
    try {
      if (graph.validateModel(Logger.getAnonymousLogger())) {
        // insertImplodeExplodesVertices(graph);
        this.outputGraph.copyProperties(graph);
        this.outputGraph.setCorrespondingSDFGraph(graph);
        for (final DAGVertex vertex : this.outputGraph.vertexSet()) {
          vertex
              .setNbRepeat(new DAGDefaultVertexPropertyType(graph.getVertex(vertex.getName()).getNbRepeatAsInteger()));
        }
        DAGEdge newedge;
        for (final SDFEdge edge : graph.edgeSet()) {
          if (edge.getDelay().intValue() == 0) {
            try {
              final DAGVertex sourceVertex = this.outputGraph.getVertex(edge.getSource().getName());
              final DAGVertex targetVertex = this.outputGraph.getVertex(edge.getTarget().getName());
              if (this.outputGraph.containsEdge(sourceVertex, targetVertex)) {
                newedge = this.outputGraph.getEdge(sourceVertex, targetVertex);
                newedge.getAggregate().add(edge);
                final DAGDefaultEdgePropertyType weigth = (DAGDefaultEdgePropertyType) newedge.getWeight();
                newedge.setWeight(new DAGDefaultEdgePropertyType(weigth.intValue() + computeEdgeWeight(edge)));
              } else {
                newedge = this.outputGraph.addDAGEdge(sourceVertex, targetVertex);
                newedge.getAggregate().add(edge);
                newedge.setWeight(new DAGDefaultEdgePropertyType(computeEdgeWeight(edge)));

              }
            } catch (final CreateMultigraphException | CreateCycleException e) {
              throw new WorkflowException(
                  "Error in the DAG creation. Check the single-rate SDF to identify where delays are missing", e);
            }
          }
        }
      }
    } catch (final InvalidExpressionException e) {
      throw (new SDF4JException(e.getMessage()));
    }
  }

  /**
   * Compute edge weight.
   *
   * @param edge
   *          the edge
   * @return the int
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  int computeEdgeWeight(final SDFEdge edge) throws InvalidExpressionException {
    final int weight = edge.getCons().intValue() * edge.getTarget().getNbRepeatAsInteger();
    final int dataSize = edge.getDataSize().intValue();
    return weight * dataSize;
  }

  /**
   * Treat the cycles in the graph.
   *
   * @param graph
   *          The graph to treat
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  private void treatCycles(final SDFGraph graph) throws InvalidExpressionException {
    final List<Set<SDFAbstractVertex>> cycles = new ArrayList<>();
    final CycleDetector<SDFAbstractVertex, SDFEdge> detector = new CycleDetector<>(graph);
    final List<SDFAbstractVertex> vertices = new ArrayList<>(graph.vertexSet());
    while (vertices.size() > 0) {
      final SDFAbstractVertex vertex = vertices.get(0);
      final Set<SDFAbstractVertex> cycle = detector.findCyclesContainingVertex(vertex);
      if (cycle.size() > 0) {
        vertices.removeAll(cycle);
        cycles.add(cycle);
      }
      vertices.remove(vertex);
    }

    for (final Set<SDFAbstractVertex> cycle : cycles) {
      // This code is dumb for single-rate SDF.
      // Since in a single-rate graph, all actors are fired
      // exactly once
      final int gcd = gcdOfVerticesVrb(cycle);
      if (gcd > 1) {
        copyCycle(graph, cycle, gcd);
      } else {
        treatSDFCycles(graph, cycle);
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
    /*
     * { CycleDetector<SDFAbstractVertex, SDFEdge> detect = new CycleDetector<SDFAbstractVertex, SDFEdge>( graph);
     * List<SDFAbstractVertex> vert = new ArrayList<SDFAbstractVertex>( graph.vertexSet()); while (vert.size() > 0) {
     * SDFAbstractVertex vertex = vert.get(0); Set<SDFAbstractVertex> cycle = detect
     * .findCyclesContainingVertex(vertex); if (cycle.size() > 0) { vert.removeAll(cycle); cycles.add(cycle); }
     * vert.remove(vertex); } }
     */

    return;
  }

  /**
   * Treat SDF cycles.
   *
   * @param graph
   *          the graph
   * @param cycle
   *          the cycle
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  protected void treatSDFCycles(final SDFGraph graph, final Set<SDFAbstractVertex> cycle)
      throws InvalidExpressionException {
    final List<SDFEdge> loops = new ArrayList<>();
    for (final SDFAbstractVertex vertex : cycle) {
      for (final SDFEdge edge : graph.incomingEdgesOf(vertex)) {
        if (edge.getDelay().intValue() > 0) {
          loops.add(edge);
        }
      }
    }
    for (final SDFEdge loop : loops) {
      final SDFInitVertex initVertex = new SDFInitVertex();
      initVertex.setName(loop.getTarget().getName() + "_init_" + loop.getTargetInterface().getName());
      final SDFSinkInterfaceVertex sink_init = new SDFSinkInterfaceVertex();
      sink_init.setName("init_out");
      initVertex.addSink(sink_init);
      initVertex.setNbRepeat(1);
      graph.addVertex(initVertex);

      final SDFEndVertex endVertex = new SDFEndVertex();
      endVertex.setName(loop.getSource().getName() + "_end_" + loop.getSourceInterface().getName());
      final SDFSourceInterfaceVertex source_end = new SDFSourceInterfaceVertex();
      source_end.setName("end_in");
      endVertex.addSource(source_end);
      endVertex.setNbRepeat(1);
      initVertex.setEndReference(endVertex);
      initVertex.setInitSize(loop.getDelay().intValue());
      endVertex.setEndReference(initVertex);
      graph.addVertex(endVertex);

      final SDFEdge initEdge = graph.addEdge(initVertex, loop.getTarget());
      initEdge.copyProperties(loop);
      initEdge.setSourceInterface(sink_init);
      initEdge.setDelay(new SDFIntEdgePropertyType(0));

      final SDFEdge endEdge = graph.addEdge(loop.getSource(), endVertex);
      endEdge.copyProperties(loop);
      endEdge.setTargetInterface(source_end);
      endEdge.setDelay(new SDFIntEdgePropertyType(0));
      graph.removeEdge(loop);
    }
  }

  /**
   * Treat delays.
   *
   * @param graph
   *          the graph
   */
  public void treatDelays(final SDFGraph graph) {
    final ArrayList<SDFEdge> edges = new ArrayList<>(graph.edgeSet());
    while (edges.size() > 0) {
      final SDFEdge edge = edges.get(0);
      try {
        if (edge.getDelay().intValue() > 0) {
          final SDFInitVertex initVertex = new SDFInitVertex();
          initVertex.setName(edge.getTarget().getName() + "_init_" + edge.getTargetInterface().getName());
          final SDFSinkInterfaceVertex sink_init = new SDFSinkInterfaceVertex();
          sink_init.setName("init_out");
          initVertex.addSink(sink_init);
          initVertex.setNbRepeat(1);
          graph.addVertex(initVertex);

          final SDFEndVertex endVertex = new SDFEndVertex();
          endVertex.setName(edge.getSource().getName() + "_end_" + edge.getSourceInterface().getName());
          final SDFSourceInterfaceVertex source_end = new SDFSourceInterfaceVertex();
          source_end.setName("end_in");
          endVertex.addSource(source_end);
          endVertex.setNbRepeat(1);
          initVertex.setEndReference(endVertex);
          initVertex.setInitSize(edge.getDelay().intValue());
          endVertex.setEndReference(initVertex);
          graph.addVertex(endVertex);

          final SDFEdge initEdge = graph.addEdge(initVertex, edge.getTarget());
          initEdge.copyProperties(edge);
          initEdge.setSourceInterface(sink_init);
          initEdge.setDelay(new SDFIntEdgePropertyType(0));
          // initEdge.setProd(edge.getDelay());

          final SDFEdge endEdge = graph.addEdge(edge.getSource(), endVertex);
          endEdge.copyProperties(edge);
          endEdge.setTargetInterface(source_end);
          endEdge.setDelay(new SDFIntEdgePropertyType(0));
          graph.removeEdge(edge);
        }
      } catch (final InvalidExpressionException e) {
        e.printStackTrace();
      }
      edges.remove(0);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.ietr.dftools.algorithm.model.visitors.IGraphVisitor#visit(org.ietr.dftools.algorithm.model.AbstractEdge)
   */
  @Override
  public void visit(final SDFEdge sdfEdge) {
  }

  /*
   * (non-Javadoc)
   *
   * @see org.ietr.dftools.algorithm.model.visitors.IGraphVisitor#visit(org.ietr.dftools.algorithm.model.AbstractGraph)
   */
  @Override
  public void visit(final SDFGraph sdf) throws SDF4JException {
    try {

      int k = 5;
      while (k-- > 0) {
        treatCycles(sdf);
        treatDelays(sdf);
      }

      final ArrayList<SDFAbstractVertex> vertices = new ArrayList<>(sdf.vertexSet());
      for (int i = 0; i < vertices.size(); i++) {
        vertices.get(i).accept(this);
      }
      sdf.getPropertyBean().setValue("schedulable", true);
      transformsTop(sdf);
    } catch (final InvalidExpressionException e) {
      e.printStackTrace();
      throw (new SDF4JException(e.getMessage()));
    }

    // Make sure all ports are in order
    if (!SpecialActorPortsIndexer.checkIndexes(sdf)) {
      throw new SDF4JException("There are still special actors with non-indexed ports. Contact Preesm developers.");
    }
    SpecialActorPortsIndexer.sortIndexedPorts(sdf);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.ietr.dftools.algorithm.model.visitors.IGraphVisitor#visit(org.ietr.dftools.algorithm.model.AbstractVertex)
   */
  @Override
  public void visit(final SDFAbstractVertex sdfVertex) throws SDF4JException {
    if (this.outputGraph.getVertex(sdfVertex.getName()) != null) {
      return;
    }
    DAGVertex vertex;
    if (sdfVertex instanceof SDFBroadcastVertex) {
      vertex = this.factory.createVertex(DAGBroadcastVertex.DAG_BROADCAST_VERTEX);
    } else if (sdfVertex instanceof SDFForkVertex) {
      vertex = this.factory.createVertex(DAGForkVertex.DAG_FORK_VERTEX);
    } else if (sdfVertex instanceof SDFJoinVertex) {
      vertex = this.factory.createVertex(DAGJoinVertex.DAG_JOIN_VERTEX);
    } else if (sdfVertex instanceof SDFEndVertex) {
      vertex = this.factory.createVertex(DAGEndVertex.DAG_END_VERTEX);
    } else if (sdfVertex instanceof SDFInitVertex) {
      vertex = this.factory.createVertex(DAGInitVertex.DAG_INIT_VERTEX);
      final SDFInitVertex sdfInitVertex = (SDFInitVertex) sdfVertex;
      sdfInitVertex.getEndReference().accept(this);
      final String endReferenceName = sdfInitVertex.getEndReference().getName();
      vertex.getPropertyBean().setValue(DAGInitVertex.END_REFERENCE, this.outputGraph.getVertex(endReferenceName));
      vertex.getPropertyBean().setValue(DAGInitVertex.INIT_SIZE, sdfInitVertex.getInitSize());
    } else {
      vertex = this.factory.createVertex(DAGVertex.DAG_VERTEX);
    }

    vertex.setName(sdfVertex.getName());
    vertex.setTime(new DAGDefaultVertexPropertyType(0));
    vertex.setNbRepeat(new DAGDefaultVertexPropertyType(0));
    vertex.setRefinement(sdfVertex.getRefinement());
    vertex.setId(sdfVertex.getId());
    vertex.setInfo(sdfVertex.getInfo());
    vertex.setCorrespondingSDFVertex(sdfVertex);
    this.outputGraph.addVertex(vertex);
  }

}
