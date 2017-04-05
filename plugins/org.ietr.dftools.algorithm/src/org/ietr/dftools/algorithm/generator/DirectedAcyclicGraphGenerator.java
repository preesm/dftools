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
 * Generate a random SDF close to a Directed Acyclic graph.
 * The generated Graph is acyclic and directed and have unitary production and consumption on edge.
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

	public static Vector<DirectedAcyclicGraphGenerator> adapters = new Vector<DirectedAcyclicGraphGenerator>();
	/**
	 * Testing the generator
	 * @param args
	 * @throws InvalidExpressionException 
	 */
	public static void main(String [] args) throws InvalidExpressionException{
		List<SDFAdapterDemo> demos = new ArrayList<SDFAdapterDemo>();
		DirectedAcyclicGraphGenerator generator = new DirectedAcyclicGraphGenerator();
		Random rand = new Random(System.nanoTime());
		for(int i =0 ; i< 5 ; i++){
			demos.add(new SDFAdapterDemo());
			SDFGraph newGraph = generator.createAcyclicRandomGraph(100, 1,3,1,3, rand.nextInt(5));
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
		adapters.add(this);
	}
	/**
	 * Creates an acyclic random graph with a number of vertices fix by the parameter nbVertex.
	 * The number of sources and sinks of each vertex and the production and consumption are randomly set.
	 * The number of minimum input vertices is set to 1  
	 * 
	 * @param nbVertex is the number of vertices to create in the graph
	 * @param minInDegree is the minimum sinks of each vertex
	 * @param maxInDegree is the maximum sinks of each vertex
	 * @param minOutDegree is the minimum sources of each vertex
	 * @param maxOutDegree is the maximum sources of each vertex
	 *
	 * 
	 * @return the created graph
	 * 
	 */
	public SDFGraph createAcyclicRandomGraph(int nbVertex, int minInDegree,
			int maxInDegree, int minOutDegree, int maxOutDegree) {
		return createAcyclicRandomGraph( nbVertex,  minInDegree,
				 maxInDegree,  minOutDegree,  maxOutDegree, 1);
	}
	
	/**
	 * Creates an acyclic random graph with a number of vertices fix by the parameter nbVertex.
	 * The number of sources and sinks of each vertex and the production and consumption are randomly set.  
	 * 
	 * @param nbVertex is the number of vertices to create in the graph
	 * @param minInDegree is the minimum sinks of each vertex
	 * @param maxInDegree is the maximum sinks of each vertex
	 * @param minOutDegree is the minimum sources of each vertex
	 * @param maxOutDegree is the maximum sources of each vertex
	 * @param nbSensors Exact number of input vertices in the graph
	 * 
	 * @return the created graph
	 * 
	 */

	public SDFGraph createAcyclicRandomGraph(int nbVertex, int minInDegree,
			int maxInDegree, int minOutDegree, int maxOutDegree, int nbSensors) {
		nbVertexgraph = 0 ;
		int[] nbSinksVertex = new int[nbVertex];
		int[] nbSourcesVertex = new int[nbVertex];
		int[][] Created_edge = new int[nbVertex][nbVertex];
		int nbSinks = 0, nbSources = 0;

		SDFVertex[] arrayVertex = new SDFVertex[nbVertex];
		Vector<SDFEdge> VecEdge = new Vector<SDFEdge>(nbVertex - 1, 10);

		// Create an SDF Graph
		SDFGraph graph = new SDFGraph();
	

		// Create graph with nbVertex Vertex
		while (nbVertexgraph < nbVertex) {

			int max, min;

			// Add a new vertex to the graph
			SDFVertex Vertex = new SDFVertex();
			Vertex.setName("Vertex " + (nbVertexgraph + 1));
			arrayVertex[nbVertexgraph] = Vertex;
			graph.addVertex(arrayVertex[nbVertexgraph]);
			// Choose a random number of sinks for the new vertex
			max = Math.min(maxOutDegree, nbVertex - nbVertexgraph - 1);
			min = Math.min(max, minOutDegree);
			nbSourcesVertex[nbVertexgraph] = min
					+ (int) (Math.random() * (max - min));
			// Choose a random number of sources for the new vertex
			max = Math.min(maxInDegree, nbVertexgraph);
			min = Math.min(max, minInDegree);
			nbSinksVertex[nbVertexgraph] = min
					+ (int) (Math.random() * (max - min));

			nbSinks += nbSinksVertex[nbVertexgraph];
			nbSources += nbSourcesVertex[nbVertexgraph];
			// If Not the first
			if (nbVertexgraph >= nbSensors && nbSinks != 0 && nbSources != 0) {

				// Create an edge between the last Vertex and another random
				// Vertex
				if (nbSinksVertex[nbVertexgraph] > 0) {
					int randout;
					do {
						randout = (int) (Math.random() * (nbVertexgraph));
					} while (nbSourcesVertex[randout] == 0);
					SDFEdge Edge = graph.addEdgeWithInterfaces(arrayVertex[randout],
							arrayVertex[nbVertexgraph]);

					VecEdge.add(Edge);
					// Set production and consumption

					VecEdge.lastElement().setProd(
							new SDFIntEdgePropertyType(1));

					VecEdge.lastElement().setCons(
							new SDFIntEdgePropertyType(1));
					// arrayEdge[nbVertexgraph-1].setDelay(new
					// SDFEdgeDefaultPropertyType(randRate));
					Created_edge[randout][nbVertexgraph] = nbVertexgraph;
					nbSourcesVertex[randout]--;
					nbSinksVertex[nbVertexgraph]--;
					nbSinks--;
					nbSources--;
				}
			}
			nbVertexgraph++;
		}

		// Create Edge

		int highestid = nbVertex - 1, nb_edge = nbVertexgraph - 1;

		while (nbSources != 0 && nbSinks != 0) {
			int randout = (int) (Math.random() * (highestid));
			int randin = randout + 1
					+ (int) (Math.random() * (nbVertex - randout - 1));
			if (nbSinksVertex[randin] != 0
					&& Created_edge[randout][randin] == 0
					&& nbSourcesVertex[randout] != 0) {
				Created_edge[randout][randin] = nb_edge + 1;
				SDFEdge Edge = graph.addEdgeWithInterfaces(arrayVertex[randout],
						arrayVertex[randin]);
				VecEdge.add(Edge);
				// Set production and consumption

				VecEdge.lastElement()
						.setProd(new SDFIntEdgePropertyType(1));
				VecEdge.lastElement()
						.setCons(new SDFIntEdgePropertyType(1));
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
