/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
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
package org.ietr.dftools.algorithm.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.ietr.dftools.algorithm.demo.SDFAdapterDemo;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;

/**
 * Generate a random SDF close to a Directed Acyclic graph. The generated Graph
 * is acyclic and directed and have unitary production and consumption on edge.
 *
 * @author pthebault
 *
 */

public class DirectedAcyclicGraphGenerator {
	// ~ Static fields/initializers
	// ---------------------------------------------

	/**
	 * Static field containing all the instances of this class
	 */

	public static Vector<DirectedAcyclicGraphGenerator> adapters = new Vector<>();

	/**
	 * Testing the generator
	 *
	 * @param args
	 * @throws InvalidExpressionException
	 */
	public static void main(final String[] args) throws InvalidExpressionException {
		final List<SDFAdapterDemo> demos = new ArrayList<>();
		final DirectedAcyclicGraphGenerator generator = new DirectedAcyclicGraphGenerator();
		final Random rand = new Random(System.nanoTime());
		for (int i = 0; i < 5; i++) {
			demos.add(new SDFAdapterDemo());
			final SDFGraph newGraph = generator.createAcyclicRandomGraph(100, 1, 3, 1, 3, rand.nextInt(5));
			demos.get(i).init(newGraph);
		}
	}

	// graph

	// ~ Instance fields
	// --------------------------------------------------------

	private int nbVertexgraph = 0;// Number of Vertex created on the

	/**
	 * Creates a new RandomGraph
	 */
	public DirectedAcyclicGraphGenerator() {
		DirectedAcyclicGraphGenerator.adapters.add(this);
	}

	/**
	 * Creates an acyclic random graph with a number of vertices fix by the
	 * parameter nbVertex. The number of sources and sinks of each vertex and
	 * the production and consumption are randomly set. The number of minimum
	 * input vertices is set to 1
	 *
	 * @param nbVertex
	 *            is the number of vertices to create in the graph
	 * @param minInDegree
	 *            is the minimum sinks of each vertex
	 * @param maxInDegree
	 *            is the maximum sinks of each vertex
	 * @param minOutDegree
	 *            is the minimum sources of each vertex
	 * @param maxOutDegree
	 *            is the maximum sources of each vertex
	 *
	 *
	 * @return the created graph
	 *
	 */
	public SDFGraph createAcyclicRandomGraph(final int nbVertex, final int minInDegree, final int maxInDegree, final int minOutDegree, final int maxOutDegree) {
		return createAcyclicRandomGraph(nbVertex, minInDegree, maxInDegree, minOutDegree, maxOutDegree, 1);
	}

	/**
	 * Creates an acyclic random graph with a number of vertices fix by the
	 * parameter nbVertex. The number of sources and sinks of each vertex and
	 * the production and consumption are randomly set.
	 *
	 * @param nbVertex
	 *            is the number of vertices to create in the graph
	 * @param minInDegree
	 *            is the minimum sinks of each vertex
	 * @param maxInDegree
	 *            is the maximum sinks of each vertex
	 * @param minOutDegree
	 *            is the minimum sources of each vertex
	 * @param maxOutDegree
	 *            is the maximum sources of each vertex
	 * @param nbSensors
	 *            Exact number of input vertices in the graph
	 *
	 * @return the created graph
	 *
	 */

	public SDFGraph createAcyclicRandomGraph(final int nbVertex, final int minInDegree, final int maxInDegree, final int minOutDegree, final int maxOutDegree,
			final int nbSensors) {
		this.nbVertexgraph = 0;
		final int[] nbSinksVertex = new int[nbVertex];
		final int[] nbSourcesVertex = new int[nbVertex];
		final int[][] Created_edge = new int[nbVertex][nbVertex];
		int nbSinks = 0, nbSources = 0;

		final SDFVertex[] arrayVertex = new SDFVertex[nbVertex];
		final Vector<SDFEdge> VecEdge = new Vector<>(nbVertex - 1, 10);

		// Create an SDF Graph
		final SDFGraph graph = new SDFGraph();

		// Create graph with nbVertex Vertex
		while (this.nbVertexgraph < nbVertex) {

			int max, min;

			// Add a new vertex to the graph
			final SDFVertex Vertex = new SDFVertex();
			Vertex.setName("Vertex " + (this.nbVertexgraph + 1));
			arrayVertex[this.nbVertexgraph] = Vertex;
			graph.addVertex(arrayVertex[this.nbVertexgraph]);
			// Choose a random number of sinks for the new vertex
			max = Math.min(maxOutDegree, nbVertex - this.nbVertexgraph - 1);
			min = Math.min(max, minOutDegree);
			nbSourcesVertex[this.nbVertexgraph] = min + (int) (Math.random() * (max - min));
			// Choose a random number of sources for the new vertex
			max = Math.min(maxInDegree, this.nbVertexgraph);
			min = Math.min(max, minInDegree);
			nbSinksVertex[this.nbVertexgraph] = min + (int) (Math.random() * (max - min));

			nbSinks += nbSinksVertex[this.nbVertexgraph];
			nbSources += nbSourcesVertex[this.nbVertexgraph];
			// If Not the first
			if ((this.nbVertexgraph >= nbSensors) && (nbSinks != 0) && (nbSources != 0)) {

				// Create an edge between the last Vertex and another random
				// Vertex
				if (nbSinksVertex[this.nbVertexgraph] > 0) {
					int randout;
					do {
						randout = (int) (Math.random() * (this.nbVertexgraph));
					} while (nbSourcesVertex[randout] == 0);
					final SDFEdge Edge = graph.addEdgeWithInterfaces(arrayVertex[randout], arrayVertex[this.nbVertexgraph]);

					VecEdge.add(Edge);
					// Set production and consumption

					VecEdge.lastElement().setProd(new SDFIntEdgePropertyType(1));

					VecEdge.lastElement().setCons(new SDFIntEdgePropertyType(1));
					// arrayEdge[nbVertexgraph-1].setDelay(new
					// SDFEdgeDefaultPropertyType(randRate));
					Created_edge[randout][this.nbVertexgraph] = this.nbVertexgraph;
					nbSourcesVertex[randout]--;
					nbSinksVertex[this.nbVertexgraph]--;
					nbSinks--;
					nbSources--;
				}
			}
			this.nbVertexgraph++;
		}

		// Create Edge

		int highestid = nbVertex - 1, nb_edge = this.nbVertexgraph - 1;

		while ((nbSources != 0) && (nbSinks != 0)) {
			final int randout = (int) (Math.random() * (highestid));
			final int randin = randout + 1 + (int) (Math.random() * (nbVertex - randout - 1));
			if ((nbSinksVertex[randin] != 0) && (Created_edge[randout][randin] == 0) && (nbSourcesVertex[randout] != 0)) {
				Created_edge[randout][randin] = nb_edge + 1;
				final SDFEdge Edge = graph.addEdgeWithInterfaces(arrayVertex[randout], arrayVertex[randin]);
				VecEdge.add(Edge);
				// Set production and consumption

				VecEdge.lastElement().setProd(new SDFIntEdgePropertyType(1));
				VecEdge.lastElement().setCons(new SDFIntEdgePropertyType(1));
				// arrayEdge[nb_edge].setDelay(new
				// SDFEdgeDefaultPropertyType(randRate));

				nbSinksVertex[randin]--;
				nbSinks--;
				nb_edge++;
				nbSourcesVertex[randout]--;
				nbSources--;
			}

			if (Created_edge[highestid - 1][highestid] != 0) {
				while (nbSourcesVertex[highestid - 1] > 0) {
					nbSourcesVertex[highestid - 1]--;
					nbSources--;
				}
			}
			if (nbSourcesVertex[highestid - 1] == 0) {
				while (nbSinksVertex[highestid] > 0) {
					nbSinksVertex[highestid]--;
					nbSinks--;
				}
			}
			if (nbSinksVertex[highestid] == 0) {
				while (nbSourcesVertex[highestid - 1] > 0) {
					nbSourcesVertex[highestid - 1]--;
					nbSources--;
				}
				highestid--;
			}

		}

		return graph;
	}

}
