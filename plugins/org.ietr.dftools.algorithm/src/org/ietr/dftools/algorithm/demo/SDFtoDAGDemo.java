/**
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2011)
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
package org.ietr.dftools.algorithm.demo;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.ietr.dftools.algorithm.factories.DAGVertexFactory;
import org.ietr.dftools.algorithm.generator.SDFRandomGraph;
import org.ietr.dftools.algorithm.iterators.DAGIterator;
import org.ietr.dftools.algorithm.model.dag.DAGEdge;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.ietr.dftools.algorithm.model.listenable.DAGListenableGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.visitors.DAGTransformation;
import org.ietr.dftools.algorithm.model.sdf.visitors.TopologyVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.jgraph.JGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

// TODO: Auto-generated Javadoc
/**
 * Test class to test the translation of an SDF graph to a Directed Acyclic Graph.
 *
 * @author pthebault
 */
public class SDFtoDAGDemo extends SDFAdapterDemo {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 4381611894567369046L;

  /**
   * applet as an application.
   *
   * @param args
   *          ignored.
   */
  public static void main(final String[] args) {
    final int nbVertex = 10;
    final int minInDegree = 1;
    final int maxInDegree = 3;
    final int minOutDegree = 1;
    final int maxOutDegree = 3;
    final SDFAdapterDemo applet1 = new SDFAdapterDemo();
    final SDFtoDAGDemo applet2 = new SDFtoDAGDemo();

    // Creates a random SDF graph
    final int minrate = 1;
    final int maxrate = 15;
    try {
      final SDFRandomGraph test = new SDFRandomGraph();
      final SDFGraph demoGraph = test.createRandomGraph(nbVertex, minInDegree, maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate);
      // SDFGraph demoGraph =createTestComGraph();
      final TopologyVisitor topo = new TopologyVisitor();
      try {
        demoGraph.accept(topo);
      } catch (final SDF4JException e) {
        e.printStackTrace();
      }
      applet1.init(demoGraph);

      final DAGTransformation<DirectedAcyclicGraph> visitor = new DAGTransformation<>(new DirectedAcyclicGraph(), DAGVertexFactory.getInstance());
      try {
        demoGraph.accept(visitor);
      } catch (final SDF4JException e) {
        e.printStackTrace();
      }
      applet2.init(visitor.getOutput());
      visitor.getOutput();
    } catch (final Exception e) {
      e.printStackTrace();
    }

  }

  /** The model. */
  private DAGListenableGraph model;

  /**
   * Initialize a created SDFAdpaterDemo with the given Graph to display.
   *
   * @param graphIn
   *          The graph to display
   */
  public void init(final DirectedAcyclicGraph graphIn) {

    final DirectedAcyclicGraph graph = (DirectedAcyclicGraph) graphIn.clone();
    // create a JGraphT graph
    this.model = new DAGListenableGraph();

    // create a visualization using JGraph, via an adapter
    this.jgAdapter = new JGraphModelAdapter<>(this.model);

    final JGraph jgraph = new JGraph(this.jgAdapter);

    adjustDisplaySettings(jgraph);
    getContentPane().add(jgraph);
    resize(SDFAdapterDemo.DEFAULT_SIZE);
    System.out.println(" graph has " + graph.vertexSet().size() + " vertice, including broadcast");
    for (final DAGVertex vertex : graph.vertexSet()) {
      this.model.addVertex(vertex);
    }

    for (final DAGEdge edge : graph.edgeSet()) {
      final DAGEdge newEdge = this.model.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
      for (final String propertyKey : edge.getPropertyBean().keys()) {
        final Object property = edge.getPropertyBean().getValue(propertyKey);
        newEdge.getPropertyBean().setValue(propertyKey, property);
      }
    }

    final CycleDetector<DAGVertex, DAGEdge> detector = new CycleDetector<>(this.model);
    GraphIterator<DAGVertex, DAGEdge> order;
    if (detector.detectCycles()) {
      order = new DAGIterator(this.model);
    } else {
      order = new TopologicalOrderIterator<>(this.model);
    }

    final Vector<DAGVertex> vertices = new Vector<>();
    int x = 0;
    int y = 100;
    int ymax = y;
    DAGVertex previousVertex = null;
    while (order.hasNext()) {
      final DAGVertex nextVertex = order.next();
      vertices.add(nextVertex);
      if ((previousVertex != null) && (this.model.getEdge(nextVertex, previousVertex) == null) && (this.model.getEdge(previousVertex, nextVertex) == null)) {
        y += 50;
        positionVertexAt(nextVertex, x, y);
        if (y > ymax) {
          ymax = y;
        }
      } else {
        y = 100;
        x += 200;
        positionVertexAt(nextVertex, x, 100);
        previousVertex = nextVertex;
      }
    }

    final JFrame frame = new JFrame();
    jgraph.setPreferredSize(new Dimension(x + 200, ymax + 300));
    frame.setContentPane(new ScrollPane());
    frame.getContentPane().add(this);
    frame.setTitle("DAG Transformation");
    if (SDFAdapterDemo.adapters.size() == 1) {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } else {
      frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
    frame.pack();
    frame.setVisible(true);
  }

}
