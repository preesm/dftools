/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
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
package org.ietr.dftools.algorithm.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.ietr.dftools.algorithm.generator.DirectedAcyclicGraphGenerator;
import org.ietr.dftools.algorithm.iterators.SDFIterator;
import org.ietr.dftools.algorithm.model.listenable.SDFListenableGraph;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.visitors.TopologyVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

// TODO: Auto-generated Javadoc
/**
 * Demo class to demonstrate the display features of a random DAG.
 *
 * @author pthebault
 */
public class DirectedAcyclicGraphGeneratorDemo extends JApplet {
  // ~ Static fields/initializers
  // ---------------------------------------------

  /** Static field containing all the instances of this class. */
  public static Vector<DirectedAcyclicGraphGenerator> adapters = new Vector<>();

  /** The Constant DEFAULT_BG_COLOR. */
  private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");

  /** The Constant DEFAULT_SIZE. */
  private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 853642557926207725L;

  // ~ Instance fields
  // --------------------------------------------------------

  // ~ Methods
  // ----------------------------------------------------------------

  /**
   * An alternative starting point for this test, to also allow running this applet as an application.
   *
   * @param args
   *          ignored.
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */

  public static void main(final String[] args) throws InvalidExpressionException {
    final int nbVertex = 30;
    final int minInDegree = 1;
    final int maxInDegree = 5;
    final int minOutDegree = 1;
    final int maxOutDegree = 5;
    final DirectedAcyclicGraphGeneratorDemo applet = new DirectedAcyclicGraphGeneratorDemo();

    // Creates a random DAG
    final DirectedAcyclicGraphGenerator DAGG = new DirectedAcyclicGraphGenerator();
    final SDFGraph demoGraph = DAGG.createAcyclicRandomGraph(nbVertex, minInDegree, maxInDegree, minOutDegree, maxOutDegree);

    final TopologyVisitor topo = new TopologyVisitor();
    try {
      demoGraph.accept(topo);
    } catch (final SDF4JException e) {
      e.printStackTrace();
    }
    applet.init(demoGraph);

  }

  /** The jg adapter. */
  @SuppressWarnings("rawtypes")
  private JGraphModelAdapter jgAdapter;

  /** The model. */
  private SDFListenableGraph model;

  /**
   * Adjust display settings.
   *
   * @param jg
   *          the jg
   */
  private void adjustDisplaySettings(final JGraph jg) {
    jg.setPreferredSize(DirectedAcyclicGraphGeneratorDemo.DEFAULT_SIZE);

    Color c = DirectedAcyclicGraphGeneratorDemo.DEFAULT_BG_COLOR;
    String colorStr = null;

    try {
      colorStr = getParameter("bgcolor");
    } catch (final Exception e) {
      e.printStackTrace();
    }

    if (colorStr != null) {
      c = Color.decode(colorStr);
    }

    jg.setBackground(c);
  }

  /**
   * Initialize a created SDFAdpaterDemo with the given Graph to display.
   *
   * @param graphIn
   *          The graph to display
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  public void init(final SDFGraph graphIn) throws InvalidExpressionException {

    final SDFGraph graph = graphIn.clone();
    // create a JGraphT graph
    this.model = new SDFListenableGraph();

    // create a visualization using JGraph, via an adapter
    this.jgAdapter = new JGraphModelAdapter<>(this.model);

    final JGraph jgraph = new JGraph(this.jgAdapter);

    adjustDisplaySettings(jgraph);
    getContentPane().add(jgraph);
    resize(DirectedAcyclicGraphGeneratorDemo.DEFAULT_SIZE);
    System.out.println(" graph has " + graph.vertexSet().size() + " vertice, including broadcast");
    for (final SDFAbstractVertex vertex : graph.vertexSet()) {
      this.model.addVertex(vertex);
    }

    for (final SDFEdge edge : graph.edgeSet()) {
      final SDFEdge newEdge = this.model.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
      newEdge.setProd(edge.getProd());
      newEdge.setCons(edge.getCons());
      newEdge.setDelay(edge.getDelay());
    }

    final CycleDetector<SDFAbstractVertex, SDFEdge> detector = new CycleDetector<>(this.model);
    GraphIterator<SDFAbstractVertex, SDFEdge> order;
    if (detector.detectCycles()) {
      order = new SDFIterator(this.model);
    } else {
      order = new TopologicalOrderIterator<>(this.model);
    }

    final Vector<SDFAbstractVertex> vertices = new Vector<>();
    int x = 0;
    int y = 100;
    int ymax = y;
    SDFAbstractVertex previousVertex = null;
    while (order.hasNext()) {
      final SDFAbstractVertex nextVertex = order.next();
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
    frame.setTitle("JGraphT Adapter to JGraph Demo");
    if (DirectedAcyclicGraphGeneratorDemo.adapters.size() == 1) {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } else {
      frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Position vertex at.
   *
   * @param vertex
   *          the vertex
   * @param x
   *          the x
   * @param y
   *          the y
   */
  @SuppressWarnings("unchecked")
  // FIXME hb 28-nov-05: See FIXME below
  private void positionVertexAt(final Object vertex, final int x, final int y) {
    final DefaultGraphCell cell = this.jgAdapter.getVertexCell(vertex);
    final AttributeMap attr = cell.getAttributes();
    final Rectangle2D bounds = GraphConstants.getBounds(attr);

    final Rectangle2D newBounds = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());

    GraphConstants.setBounds(attr, newBounds);

    // TODO: Clean up generics once JGraph goes generic
    final AttributeMap cellAttr = new AttributeMap();
    cellAttr.put(cell, attr);
    this.jgAdapter.edit(cellAttr, null, null, null);
  }

}
