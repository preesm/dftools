package net.sf.dftools.algorithm.generator;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import net.sf.dftools.algorithm.Rational;
import net.sf.dftools.algorithm.SDFMath;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFVertex;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

import org.jgrapht.alg.CycleDetector;

/**
 * Generate a schedulable Random graph, 
 * by setting the number of vertices and who have random numbers of sources and sinks.
 * Moreover the production and consumption between two vertices is randomly set.
 * 
 * @author pthebault
 * 
 */
public class SDFRandomGraph {
	// ~ Static fields/initializers
	// ---------------------------------------------

	/**
	 * Static field containing all the instances of this class
	 */

	public static Vector<SDFRandomGraph> adapters = new Vector<SDFRandomGraph>();
	/**
	 * Instance fractions is the fraction of each vertex
	 */
	public static HashMap<SDFAbstractVertex, Rational> fractions;

	private static final String CLUSTER = "cluster";
	// ~ Instance fields
	// --------------------------------------------------------
	
	/**
	 * Alternative method to calculate the repetition vector of a graph
	 *
	 * @param graph	is the graph to calculate the repetition Vector
	 * @param nbVertexgraph is the number of vertices of the graph
	 * 
	 * @return the repetition vector
	 */
	public static HashMap<SDFAbstractVertex, Integer> CalcRepetitionVector(SDFGraph graph,int nbVertexgraph){
		
		HashMap<SDFAbstractVertex, Integer> vrb=new HashMap<SDFAbstractVertex, Integer>(nbVertexgraph);
		int l=1;
		// Find lowest common multiple (lcm) of all denominators
	    for (SDFAbstractVertex vertex:graph.vertexSet()){
	        l = SDFMath.lcm(l, fractions.get(vertex).getDenum());
	    }
	    // Zero vector?
	    if (l == 0)
	        return vrb;
	    // Calculate non-zero repetition vector
	    for (SDFAbstractVertex vertex:graph.vertexSet()){
	        vrb.put(vertex, (fractions.get(vertex).getNum() * l) / fractions.get(vertex).getDenum());
	   }
	    // Find greatest common divisor (gcd)
	    int g=0;
	    for (SDFAbstractVertex vertex:graph.vertexSet()){
	        g = SDFMath.gcd(g, vrb.get(vertex));
	    }
	    // Minimize the repetition vector using the gcd
	    for (SDFAbstractVertex vertex:graph.vertexSet()){
	        vrb.put(vertex, vrb.get(vertex)/g);
	   	    }
	    return vrb;
	}
	
	
	
	
	
	/**
	 * Set consumption and production on edge in order make the graph schedulable
	 * 
	 * @param graph is the graph to make consistent 
	 */
	public static void makeConsistentConnectedActors(SDFGraph graph) {
		Rational RatioSrcDst;
		for (SDFAbstractVertex Src :graph.vertexSet()){
			for (SDFAbstractVertex Dst :graph.vertexSet()) {
				if (graph.containsEdge(Src, Dst)) {
					RatioSrcDst = Rational.div(fractions.get(Src), fractions
							.get(Dst));
					graph.getEdge(Src, Dst).setProd(new SDFIntEdgePropertyType((RatioSrcDst.getDenum())));
					graph.getEdge(Src, Dst).setCons(new SDFIntEdgePropertyType( RatioSrcDst.getNum()));
				}
			}
		}
	}


	/**
	 * Place delays on Edge to make the random graph schedulable.
	 * 
	 * @param graph	The graph on which to place delays in order to get a schedulable graph
	 * @param nbVertexgraph The number of vertices of the graph
	 * @param sensors The input vertices of the Graph
	 * @throws InvalidExpressionException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void PlaceDelay(SDFGraph graph,int nbVertexgraph ,Vector<SDFAbstractVertex> sensors) throws InvalidExpressionException {
		SDFGraph newgraph = (SDFGraph) graph.clone();// new graph is created to reduce execution time of cycle detection
		HashMap<SDFAbstractVertex, Integer> vrb=CalcRepetitionVector(graph,nbVertexgraph);
		for (SDFAbstractVertex Dst:graph.vertexSet()) {
			//if there is a cycle containing the source and the target of an edge a delay is on placed on it
			CycleDetector<SDFVertex, SDFEdge> Cycle = new CycleDetector(newgraph);
			Set<SDFVertex> test = Cycle.findCyclesContainingVertex((SDFVertex) newgraph.getVertex(Dst.getName()));
			for (SDFAbstractVertex Src:graph.vertexSet()) {
				if (graph.containsEdge(Src,Dst)) {
					if (test.contains(newgraph.getVertex(Src.getName()))){
						SDFEdge edge=graph.getEdge(Src,Dst);
						int Q_xy = vrb.get(edge.getSource()).intValue()/SDFMath.gcd(vrb.get(edge.getSource()).intValue(), vrb.get(edge.getTarget()).intValue());
						edge.setDelay(new SDFIntEdgePropertyType(Q_xy*edge.getProd().intValue()));
					}
				}
			}
			newgraph.removeVertex(newgraph.getVertex(Dst.getName()));
		}
		for(SDFAbstractVertex vertex:sensors){
			for(SDFEdge edge:graph.incomingEdgesOf(vertex)){
				if (edge.getDelay().intValue()==0){
					int Q_xy = vrb.get(edge.getSource()).intValue()/SDFMath.gcd(vrb.get(edge.getSource()).intValue(), vrb.get(edge.getTarget()).intValue());
					edge.setDelay(new SDFIntEdgePropertyType(Q_xy*edge.getProd().intValue()));
					
				}
			}
		}
	}
	/**
	 * Creates a new RandomGraph
	 */
	public SDFRandomGraph() {
		adapters.add(this);
	}

	/**
	 * 
	 * Creates a new schedulable Random graph, 
     * by setting the number of vertices and who have random numbers of sources and sinks.
     * Moreover the production and consumption between two vertices is randomly set.
	 * 
	 * @param nbVertex is the number of vertices to create in the graph
	 * @param minInDegree is the minimum sinks of each vertex
	 * @param maxInDegree is the maximum sinks of each vertex
	 * @param minOutDegree is the minimum sources of each vertex
	 * @param maxOutDegree is the maximum sources of each vertex
	 * @param minRate is the minimum production and consumption on edge
	 * @param maxRate is the maximum production and consumption on edge
	 * 
	 * @return The created random graph
	 * @throws SDF4JException 
	 * @throws InvalidExpressionException 
	 * 
	 */
	public SDFGraph createRandomGraph(int nbVertex, int minInDegree,
			int maxInDegree, int minOutDegree, int maxOutDegree, int minRate,
			int maxRate) throws SDF4JException{
		try {
			return createRandomGraph(nbVertex,minInDegree,
					maxInDegree,  minOutDegree,  maxOutDegree,  minRate,
					maxRate,1);
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw(new SDF4JException(e.getMessage()));
		}
	}

	/**
	 * 
	 * Creates a new schedulable Random graph, 
     * by setting the number of vertices and who have random numbers of sources and sinks.
     * Moreover the production and consumption between two vertices is randomly set.
	 * 
	 * @param nbVertex The number of vertices to create in the graph
	 * @param minInDegree The minimum sinks of each vertex
	 * @param maxInDegree The maximum sinks of each vertex
	 * @param minOutDegree The minimum sources of each vertex
	 * @param maxOutDegree The maximum sources of each vertex
	 * @param minRate The minimum production and consumption on edge
	 * @param maxRate The maximum production and consumption on edge
	 * @param nbSensors Exact number of input vertices in the graph
	 * 
	 * @return The created random graph
	 * @throws InvalidExpressionException 
	 * 
	 */
	public SDFGraph createRandomGraph(int nbVertex, int minInDegree,
			int maxInDegree, int minOutDegree, int maxOutDegree, int minRate,
			int maxRate,int nbSensors) throws InvalidExpressionException {
		
		
		int[] nbSinksVertex = new int[nbVertex];
		int[] nbSourcesVertex = new int[nbVertex];
		int nbVertexgraph = 0;// Number of Vertex created on the
		int[][]Created_edge = new int[nbVertex][nbVertex];
		int nbSinks = 0, nbSources = 0;
		SDFVertex[] arrayVertex = new SDFVertex[nbVertex];
		Vector<Integer> In_free_Vertex = new Vector<Integer>(nbVertex,0);
		Vector<Integer> Out_free_Vertex = new Vector<Integer>(nbVertex,0);
		fractions = new HashMap<SDFAbstractVertex, Rational>();
		Vector<SDFAbstractVertex> Sensors = new Vector<SDFAbstractVertex>(nbSensors);
		// Create an SDF Graph
		SDFGraph graph = new SDFGraph();
		
		
		
		// Create graph with nbVertex Vertex
		while (nbVertexgraph < nbVertex) {
			int max;
			double min2,max2;
			// Add a new vertex to the graph
			SDFVertex Vertex = new SDFVertex();
			Vertex.setName("Vertex " + (nbVertexgraph));
			arrayVertex[nbVertexgraph] = Vertex;
			Vertex.getPropertyBean().setValue(CLUSTER, 0);
			graph.addVertex(arrayVertex[nbVertexgraph]);
			In_free_Vertex.add(nbVertexgraph);
			Out_free_Vertex.add(nbVertexgraph);
		
			// Choose a random number of sinks for the new vertex
			max = Math.min(maxOutDegree, nbVertex);
			nbSourcesVertex[nbVertexgraph] = minOutDegree + (int) (Math.random() * (max + 1 - minOutDegree));
			// Choose a random number of sources for the new vertex
			max = Math.min(maxInDegree, nbVertex);
			nbSinksVertex[nbVertexgraph] = minInDegree + (int) (Math.random() * (max + 1 - minInDegree));
			nbSinks += nbSinksVertex[nbVertexgraph];
			nbSources += nbSourcesVertex[nbVertexgraph];
			min2 = Math.sqrt( minRate);
			max2 =Math.sqrt( maxRate) ;
			int randNum = (int)min2 + (int) (Math.random() * (max2 - min2 + 1));
			int randDenum = (int)min2 + (int) (Math.random() * (max2 - min2 + 1));
			fractions.put(Vertex,new Rational(randNum, randDenum));
			// If Not the first
			if (nbVertexgraph >= nbSensors && nbSinksVertex[nbVertexgraph] != 0
					&& nbSources != 0 && nbSinks != 0) {
				// Create an edge between the last Vertex and another random
				// Vertex
				int randout;
				do {
					randout = (int) (Math.random() * (nbVertexgraph));
				} while (nbSourcesVertex[randout] == 0);
				graph.addEdgeWithInterfaces(arrayVertex[randout],
						arrayVertex[nbVertexgraph]);
				Created_edge[randout][nbVertexgraph] = nbVertexgraph-1;
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
			}
			else if(nbVertexgraph < nbSensors){
				Sensors.add(Vertex);
			}
			nbVertexgraph++;
		}

		// Create Edges
		int nb_edge = nbVertexgraph - 1;
		while (nbSources != 0 && nbSinks != 0) {
			int randout = (int) (Math.random() * (Out_free_Vertex.size()));
			randout = Out_free_Vertex.elementAt(randout);
			int randin = (int) (Math.random() * (In_free_Vertex.size()));
			randin = In_free_Vertex.elementAt(randin);
			if (nbSinksVertex[randin] != 0
					&& Created_edge[randout][randin] == 0
					&& nbSourcesVertex[randout] != 0) {
				Created_edge[randout][randin] = nb_edge + 1;
				graph.addEdgeWithInterfaces(arrayVertex[randout],
						arrayVertex[randin]);
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
			for (int i = 0; i < Out_free_Vertex.size() && possible == 0; i++) {
				for (int j = 0; j < In_free_Vertex.size() && possible == 0; j++) {
					if (Created_edge[Out_free_Vertex.elementAt(i)][In_free_Vertex
							.elementAt(j)] == 0
							&& nbSourcesVertex[Out_free_Vertex.elementAt(i)] != 0
							&& nbSinksVertex[In_free_Vertex.elementAt(j)] != 0) {
						possible = 1;
					}
				}
			}
			if (possible == 0) {
				break;
			}
		}
		
		// Make the graph consistent
		makeConsistentConnectedActors(graph);

		// Place Delays on Edge
		PlaceDelay(graph,nbVertexgraph,Sensors);
		
		return graph;
	}
}
