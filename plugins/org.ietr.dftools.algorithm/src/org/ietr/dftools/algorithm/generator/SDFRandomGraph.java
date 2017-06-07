/**
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2011)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2012)
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
package org.ietr.dftools.algorithm.generator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.ietr.dftools.algorithm.Rational;
import org.ietr.dftools.algorithm.SDFMath;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.jgrapht.alg.CycleDetector;

// TODO: Auto-generated Javadoc
/**
 * Generate a schedulable Random graph, by setting the number of vertices and who have random numbers of sources and sinks. Moreover the production and
 * consumption between two vertices is randomly set.
 *
 * @author pthebault
 *
 */
public class SDFRandomGraph {
  // ~ Static fields/initializers
  // ---------------------------------------------

  /** Static field containing all the instances of this class. */

  public static Vector<SDFRandomGraph> adapters = new Vector<>();

  /** Instance fractions is the fraction of each vertex. */
  public static Map<SDFAbstractVertex, Rational> fractions;

  /** The Constant CLUSTER. */
  private static final String CLUSTER = "cluster";
  // ~ Instance fields
  // --------------------------------------------------------

  /**
   * Alternative method to calculate the repetition vector of a graph.
   *
   * @param graph
   *          is the graph to calculate the repetition Vector
   * @param nbVertexgraph
   *          is the number of vertices of the graph
   * @return the repetition vector
   */
  public static Map<SDFAbstractVertex, Integer> CalcRepetitionVector(final SDFGraph graph, final int nbVertexgraph) {

    final Map<SDFAbstractVertex, Integer> vrb = new LinkedHashMap<>(nbVertexgraph);
    int l = 1;
    // Find lowest common multiple (lcm) of all denominators
    for (final SDFAbstractVertex vertex : graph.vertexSet()) {
      l = SDFMath.lcm(l, SDFRandomGraph.fractions.get(vertex).getDenum());
    }
    // Zero vector?
    if (l == 0) {
      return vrb;
    }
    // Calculate non-zero repetition vector
    for (final SDFAbstractVertex vertex : graph.vertexSet()) {
      vrb.put(vertex, (SDFRandomGraph.fractions.get(vertex).getNum() * l) / SDFRandomGraph.fractions.get(vertex).getDenum());
    }
    // Find greatest common divisor (gcd)
    int g = 0;
    for (final SDFAbstractVertex vertex : graph.vertexSet()) {
      g = SDFMath.gcd(g, vrb.get(vertex));
    }
    // Minimize the repetition vector using the gcd
    for (final SDFAbstractVertex vertex : graph.vertexSet()) {
      vrb.put(vertex, vrb.get(vertex) / g);
    }
    return vrb;
  }

  /**
   * Set consumption and production on edge in order make the graph schedulable.
   *
   * @param graph
   *          is the graph to make consistent
   * @param rateMultiplier
   *          the rate multiplier
   */
  public static void makeConsistentConnectedActors(final SDFGraph graph, final int rateMultiplier) {
    Rational RatioSrcDst;
    for (final SDFAbstractVertex Src : graph.vertexSet()) {
      for (final SDFAbstractVertex Dst : graph.vertexSet()) {
        if (graph.containsEdge(Src, Dst)) {
          RatioSrcDst = Rational.div(SDFRandomGraph.fractions.get(Src), SDFRandomGraph.fractions.get(Dst));
          graph.getEdge(Src, Dst).setProd(new SDFIntEdgePropertyType(RatioSrcDst.getDenum() * rateMultiplier));
          graph.getEdge(Src, Dst).setCons(new SDFIntEdgePropertyType(RatioSrcDst.getNum() * rateMultiplier));
        }
      }
    }
  }

  /**
   * Place delays on Edge to make the random graph schedulable.
   *
   * @param graph
   *          The graph on which to place delays in order to get a schedulable graph
   * @param nbVertexgraph
   *          The number of vertices of the graph
   * @param sensors
   *          The input vertices of the Graph
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void PlaceDelay(final SDFGraph graph, final int nbVertexgraph, final Vector<SDFAbstractVertex> sensors) throws InvalidExpressionException {
    final SDFGraph newgraph = graph.clone();// new graph is created to
    // reduce execution time of
    // cycle detection
    final Map<SDFAbstractVertex, Integer> vrb = SDFRandomGraph.CalcRepetitionVector(graph, nbVertexgraph);
    for (final SDFAbstractVertex Dst : graph.vertexSet()) {
      // if there is a cycle containing the source and the target of an
      // edge a delay is on placed on it
      final CycleDetector<SDFVertex, SDFEdge> Cycle = new CycleDetector(newgraph);
      final Set<SDFVertex> test = Cycle.findCyclesContainingVertex((SDFVertex) newgraph.getVertex(Dst.getName()));
      for (final SDFAbstractVertex Src : graph.vertexSet()) {
        if (graph.containsEdge(Src, Dst)) {
          if (test.contains(newgraph.getVertex(Src.getName()))) {
            final SDFEdge edge = graph.getEdge(Src, Dst);
            final int Q_xy = vrb.get(edge.getSource()).intValue() / SDFMath.gcd(vrb.get(edge.getSource()).intValue(), vrb.get(edge.getTarget()).intValue());
            edge.setDelay(new SDFIntEdgePropertyType(Q_xy * edge.getProd().intValue()));
          }
        }
      }
      newgraph.removeVertex(newgraph.getVertex(Dst.getName()));
    }
    for (final SDFAbstractVertex vertex : sensors) {
      for (final SDFEdge edge : graph.incomingEdgesOf(vertex)) {
        if (edge.getDelay().intValue() == 0) {
          final int Q_xy = vrb.get(edge.getSource()).intValue() / SDFMath.gcd(vrb.get(edge.getSource()).intValue(), vrb.get(edge.getTarget()).intValue());
          edge.setDelay(new SDFIntEdgePropertyType(Q_xy * edge.getProd().intValue()));

        }
      }
    }
  }

  /**
   * Creates a new RandomGraph.
   */
  public SDFRandomGraph() {
    SDFRandomGraph.adapters.add(this);
  }

  /**
   * Creates a new schedulable Random graph, by setting the number of vertices and who have random numbers of sources and sinks. Moreover the production and
   * consumption between two vertices is randomly set.
   *
   * @param nbVertex
   *          is the number of vertices to create in the graph
   * @param minInDegree
   *          is the minimum sinks of each vertex
   * @param maxInDegree
   *          is the maximum sinks of each vertex
   * @param minOutDegree
   *          is the minimum sources of each vertex
   * @param maxOutDegree
   *          is the maximum sources of each vertex
   * @param minRate
   *          is the minimum production and consumption on edge
   * @param maxRate
   *          is the maximum production and consumption on edge
   * @return The created random graph
   * @throws SDF4JException
   *           the SDF 4 J exception
   */
  public SDFGraph createRandomGraph(final int nbVertex, final int minInDegree, final int maxInDegree, final int minOutDegree, final int maxOutDegree,
      final int minRate, final int maxRate) throws SDF4JException {
    try {
      return createRandomGraph(nbVertex, minInDegree, maxInDegree, minOutDegree, maxOutDegree, minRate, maxRate, 1, 1);
    } catch (final InvalidExpressionException e) {
      e.printStackTrace();
      throw (new SDF4JException(e.getMessage()));
    }
  }

  /**
   * Creates a new schedulable Random graph, by setting the number of vertices and who have random numbers of sources and sinks. Moreover the production and
   * consumption between two vertices is randomly set.
   *
   * @param nbVertex
   *          is the number of vertices to create in the graph
   * @param minInDegree
   *          is the minimum sinks of each vertex
   * @param maxInDegree
   *          is the maximum sinks of each vertex
   * @param minOutDegree
   *          is the minimum sources of each vertex
   * @param maxOutDegree
   *          is the maximum sources of each vertex
   * @param minRate
   *          is the minimum production and consumption on edge
   * @param maxRate
   *          is the maximum production and consumption on edge
   * @param rateMultiplier
   *          a coefficient multiplying ALL productions and consumption rates of the generated sdf.
   * @return The created random graph
   * @throws SDF4JException
   *           the SDF 4 J exception
   */
  public SDFGraph createRandomGraph(final int nbVertex, final int minInDegree, final int maxInDegree, final int minOutDegree, final int maxOutDegree,
      final int minRate, final int maxRate, final int rateMultiplier) throws SDF4JException {
    try {
      return createRandomGraph(nbVertex, minInDegree, maxInDegree, minOutDegree, maxOutDegree, minRate, maxRate, rateMultiplier, 1);
    } catch (final InvalidExpressionException e) {
      e.printStackTrace();
      throw (new SDF4JException(e.getMessage()));
    }
  }

  /**
   * Creates a new schedulable Random graph, by setting the number of vertices and who have random numbers of sources and sinks. Moreover the production and
   * consumption between two vertices is randomly set.
   *
   * @param nbVertex
   *          The number of vertices to create in the graph
   * @param minInDegree
   *          The minimum sinks of each vertex
   * @param maxInDegree
   *          The maximum sinks of each vertex
   * @param minOutDegree
   *          The minimum sources of each vertex
   * @param maxOutDegree
   *          The maximum sources of each vertex
   * @param minRate
   *          The minimum production and consumption on edge
   * @param maxRate
   *          The maximum production and consumption on edge
   * @param rateMultiplier
   *          a coefficient multiplying ALL productions and consumption rates of the generated sdf.
   * @param nbSensors
   *          Exact number of input vertices in the graph
   * @return The created random graph
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  public SDFGraph createRandomGraph(final int nbVertex, final int minInDegree, final int maxInDegree, final int minOutDegree, final int maxOutDegree,
      final int minRate, final int maxRate, final int rateMultiplier, final int nbSensors) throws InvalidExpressionException {

    final int[] nbSinksVertex = new int[nbVertex];
    final int[] nbSourcesVertex = new int[nbVertex];
    int nbVertexgraph = 0;// Number of Vertex created on the
    final int[][] Created_edge = new int[nbVertex][nbVertex];
    int nbSinks = 0;
    int nbSources = 0;
    final SDFVertex[] arrayVertex = new SDFVertex[nbVertex];
    final Vector<Integer> In_free_Vertex = new Vector<>(nbVertex, 0);
    final Vector<Integer> Out_free_Vertex = new Vector<>(nbVertex, 0);
    SDFRandomGraph.fractions = new LinkedHashMap<>();
    final Vector<SDFAbstractVertex> Sensors = new Vector<>(nbSensors);
    // Create an SDF Graph
    final SDFGraph graph = new SDFGraph();

    // Create graph with nbVertex Vertex
    while (nbVertexgraph < nbVertex) {
      // Add a new vertex to the graph
      final SDFVertex Vertex = new SDFVertex();
      Vertex.setName("Vertex_" + (nbVertexgraph));
      arrayVertex[nbVertexgraph] = Vertex;
      Vertex.getPropertyBean().setValue(SDFRandomGraph.CLUSTER, 0);
      graph.addVertex(arrayVertex[nbVertexgraph]);
      In_free_Vertex.add(nbVertexgraph);
      Out_free_Vertex.add(nbVertexgraph);

      // Choose a random number of sinks for the new vertex
      int max = Math.min(maxOutDegree, nbVertex);
      nbSourcesVertex[nbVertexgraph] = minOutDegree + (int) (Math.random() * ((max + 1) - minOutDegree));
      // Choose a random number of sources for the new vertex
      max = Math.min(maxInDegree, nbVertex);
      nbSinksVertex[nbVertexgraph] = minInDegree + (int) (Math.random() * ((max + 1) - minInDegree));
      nbSinks += nbSinksVertex[nbVertexgraph];
      nbSources += nbSourcesVertex[nbVertexgraph];
      final double min2 = Math.sqrt(minRate);
      final double max2 = Math.sqrt(maxRate);
      final int randNum = (int) min2 + (int) (Math.random() * ((max2 - min2) + 1));
      final int randDenum = (int) min2 + (int) (Math.random() * ((max2 - min2) + 1));
      SDFRandomGraph.fractions.put(Vertex, new Rational(randNum, randDenum));
      // If Not the first
      if ((nbVertexgraph >= nbSensors) && (nbSinksVertex[nbVertexgraph] != 0) && (nbSources != 0) && (nbSinks != 0)) {
        // Create an edge between the last Vertex and another random
        // Vertex
        int randout;
        do {
          randout = (int) (Math.random() * (nbVertexgraph));
        } while (nbSourcesVertex[randout] == 0);
        graph.addEdgeWithInterfaces(arrayVertex[randout], arrayVertex[nbVertexgraph]);
        Created_edge[randout][nbVertexgraph] = nbVertexgraph - 1;
        nbSourcesVertex[randout]--;
        nbSinksVertex[nbVertexgraph]--;
        nbSinks--;
        nbSources--;
        if (nbSinksVertex[nbVertexgraph] == 0) {
          In_free_Vertex.removeElement(nbVertexgraph);
        }
        if (nbSourcesVertex[randout] == 0) {
          Out_free_Vertex.removeElement(randout);
        }
      } else if (nbVertexgraph < nbSensors) {
        Sensors.add(Vertex);
      }
      nbVertexgraph++;
    }

    // Create Edges
    int nb_edge = nbVertexgraph - 1;
    while ((nbSources != 0) && (nbSinks != 0)) {
      int randout = (int) (Math.random() * (Out_free_Vertex.size()));
      randout = Out_free_Vertex.elementAt(randout);
      int randin = (int) (Math.random() * (In_free_Vertex.size()));
      randin = In_free_Vertex.elementAt(randin);
      if ((nbSinksVertex[randin] != 0) && (Created_edge[randout][randin] == 0) && (nbSourcesVertex[randout] != 0)) {
        Created_edge[randout][randin] = nb_edge + 1;
        graph.addEdgeWithInterfaces(arrayVertex[randout], arrayVertex[randin]);
        nbSinksVertex[randin]--;
        nbSinks--;
        nb_edge++;
        nbSourcesVertex[randout]--;
        nbSources--;
      }
      if (nbSinksVertex[randin] == 0) {
        In_free_Vertex.removeElement(randin);
      }
      if (nbSourcesVertex[randout] == 0) {
        Out_free_Vertex.removeElement(randout);
      }
      int possible = 0;
      for (int i = 0; (i < Out_free_Vertex.size()) && (possible == 0); i++) {
        for (int j = 0; (j < In_free_Vertex.size()) && (possible == 0); j++) {
          if ((Created_edge[Out_free_Vertex.elementAt(i)][In_free_Vertex.elementAt(j)] == 0) && (nbSourcesVertex[Out_free_Vertex.elementAt(i)] != 0)
              && (nbSinksVertex[In_free_Vertex.elementAt(j)] != 0)) {
            possible = 1;
          }
        }
      }
      if (possible == 0) {
        break;
      }
    }

    // Make the graph consistent
    SDFRandomGraph.makeConsistentConnectedActors(graph, rateMultiplier);

    // Place Delays on Edge
    SDFRandomGraph.PlaceDelay(graph, nbVertexgraph, Sensors);

    return graph;
  }
}
