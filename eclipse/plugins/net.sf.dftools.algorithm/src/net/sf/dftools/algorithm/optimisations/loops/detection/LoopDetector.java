package net.sf.dftools.algorithm.optimisations.loops.detection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.sf.dftools.algorithm.SDFMath;
import net.sf.dftools.algorithm.demo.SDFAdapterDemo;
import net.sf.dftools.algorithm.generator.SDFRandomGraph;
import net.sf.dftools.algorithm.iterators.SDFIterator;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

/**
 * Class to detect potential loops in the graph
 * 
 * @author jpiat
 * 
 */
public class LoopDetector {

	/**
	 * String key for the property vertex_color of vertices
	 */
	public static final String VERTEX_COLOR = "vertex_color";

	/**
	 * Main function for debug purposes
	 * 
	 * @param args
	 * @throws InvalidExpressionException 
	 * @throws SDF4JException 
	 */
	public static void main(String[] args) throws InvalidExpressionException, SDF4JException {
		int nbVertex = 80, minInDegree = 1, maxInDegree = 2, minOutDegree = 1, maxOutDegree = 3;
		// Creates a random SDF graph
		int minrate = 1, maxrate = 100;
		SDFRandomGraph test = new SDFRandomGraph();
		SDFGraph demoGraph = test.createRandomGraph(nbVertex, minInDegree,
				maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate);
		if (!demoGraph.isSchedulable()) {
			System.out.println("not schedulable");
			System.exit(-1);
		}
		// beforeApplet.init(demoGraph);
		LoopDetector detector = new LoopDetector(demoGraph);
		// detector.enumerate();
		detector.detect(5);
		HashMap<Color, Integer> colorToGcd = new HashMap<Color, Integer>() ;
		for(SDFAbstractVertex vertex : demoGraph.vertexSet()){
			Color vertexColor = (Color) vertex.getPropertyBean().getValue(VERTEX_COLOR) ;
			if(colorToGcd.get(vertexColor) == null){
				colorToGcd.put(vertexColor, (Integer) vertex.getNbRepeat());
			}else{
				int gcd = SDFMath.gcd((Integer) vertex.getNbRepeat(), colorToGcd.get(vertexColor));
				colorToGcd.put(vertexColor, gcd);
			}
		}
		for(Color color : colorToGcd.keySet()){
			System.out.println(color.toString()+" gcd = "+colorToGcd.get(color));
		}
		SDFAdapterDemo adapter = new SDFAdapterDemo();
		adapter.init(demoGraph);
	}
	private List<SDFAbstractVertex> treated;
	private HashMap<String, List<SDFAbstractVertex>> loops;
	private HashMap<String, Integer> loopsLength;
	private SDFIterator iterator;

	private SDFGraph graph;

	/**
	 * Builds a new LoopDetector for the given graph
	 * 
	 * @param graph
	 *            The graph in which loops are to be find
	 * @throws InvalidExpressionException 
	 */
	public LoopDetector(SDFGraph graph) throws InvalidExpressionException {
		this.graph = graph;
		loops = new HashMap<String, List<SDFAbstractVertex>>();
		loopsLength = new HashMap<String, Integer>();
		treated = new ArrayList<SDFAbstractVertex>();
		iterator = new SDFIterator(graph);
	}
	
	/**
	 * Detect the loop by trying to maximize the number of vertices in the loop
	 * and the length of the iteration domain
	 * @param minLoopSize Specifies the minimum length of the loop (if 1, every loop in the graph will be considered valid ...)
	 * @throws InvalidExpressionException 
	 */
	private void detect(int minLoopSize) throws InvalidExpressionException {
		HashMap<SDFAbstractVertex, Integer> treatedCount = new HashMap<SDFAbstractVertex, Integer>();
		Random rand = new Random(System.nanoTime());
		for (SDFAbstractVertex vertex : graph.vertexSet()) {
			vertex.getPropertyBean().setValue(VERTEX_COLOR,
					new Color(rand.nextInt()));
		}
		List<SDFAbstractVertex> vertexList = new ArrayList<SDFAbstractVertex>(
				graph.vertexSet());
		while (treated.size() != vertexList.size()) {
			SDFAbstractVertex vertex = vertexList.get(rand.nextInt(vertexList
					.size()));
			if(treatedCount.get(vertex) == null){
				treatedCount.put(vertex, 1);
			}else{
				int nbCounts = treatedCount.get(vertex);
				nbCounts ++ ;
				treatedCount.put(vertex, nbCounts);
			}
			/*while(treatedCount.get(vertex) > graph.vertexSet().size()){
				 vertex = vertexList.get(rand.nextInt(vertexList
							.size()));
			}*/
			SDFAbstractVertex maxGcdVertex = null;
			int maxGcd = minLoopSize;
			for (SDFEdge edge : graph.incomingEdgesOf(vertex)) {
				if (graph.getEdgeSource(edge) != vertex) {
					SDFAbstractVertex neighbour = graph.getEdgeSource(edge);
					List<SDFAbstractVertex> done = new ArrayList<SDFAbstractVertex>();
					done.add(vertex);
					int gcd = SDFMath.gcd((Integer) vertex.getNbRepeat(), getColorGcd(neighbour,done ));
					if (gcd >= maxGcd) {
						maxGcdVertex = neighbour;
						maxGcd = gcd;
					}
				}
			}
			if(maxGcdVertex != null){
				Color oldColor = (Color) vertex.getPropertyBean().getValue(VERTEX_COLOR);
				Color newColor ;
				if(treatedCount.get(vertex) > graph.vertexSet().size()){
					newColor = oldColor ;
				}else{
					newColor = (Color) maxGcdVertex.getPropertyBean().getValue(VERTEX_COLOR);
				}
				vertex.getPropertyBean().setValue(VERTEX_COLOR,
						newColor);
				if ((!treated.contains(vertex) && oldColor.equals(newColor))) {
					treated.add(vertex);
				}else if(! oldColor.equals(newColor)){
					for (SDFEdge edge : graph.outgoingEdgesOf(vertex)) {
						SDFAbstractVertex nexVertex = graph.getEdgeTarget(edge);
						if(treated.contains(nexVertex)){
							treated.remove(nexVertex);
						}
					}
				}
			}else{
				if (!treated.contains(vertex)) {
					treated.add(vertex);
				}
			}
		}

	}

	/**
	 * Enumerate the loops
	 * @throws InvalidExpressionException 
	 */
	public void enumerate() throws InvalidExpressionException {
		while (iterator.hasNext()) {
			SDFAbstractVertex nextVertex = iterator.next();
			treated.add(nextVertex);
			int nbRepeat = (Integer) nextVertex.getNbRepeat();
			if (nbRepeat > 1) {
				for (String id : loopsLength.keySet()) {
					if (SDFMath.gcd(loopsLength.get(id), nbRepeat) > 1
							&& nbRepeat >= loopsLength.get(id)) {
						for (SDFEdge edge : graph.incomingEdgesOf(nextVertex)) {
							if (loops.get(id).contains(
									graph.getEdgeSource(edge))
									&& !loops.get(id).contains(nextVertex)) {
								loops.get(id).add(nextVertex);
								loopsLength.put(id, SDFMath.gcd(loopsLength
										.get(id), nbRepeat));
								break;
							}
						}
					}
				}
				String uuid = UUID.randomUUID().toString();
				loopsLength.put(uuid, nbRepeat);
				ArrayList<SDFAbstractVertex> newLoop = new ArrayList<SDFAbstractVertex>();
				newLoop.add(nextVertex);
				loops.put(uuid, newLoop);
			}
		}

	}

	private int getColorGcd(SDFAbstractVertex vertex, List<SDFAbstractVertex> treatedVertices) throws InvalidExpressionException {
		int gcd = (Integer) vertex.getNbRepeat() ;
		Color color = (Color) vertex.getPropertyBean()
		.getValue(VERTEX_COLOR);
		treatedVertices.add(vertex);
		for (SDFEdge edge : graph.outgoingEdgesOf(vertex)) {
			SDFAbstractVertex nexVertex = graph.getEdgeTarget(edge);
			if((!treatedVertices.contains(nexVertex)) 
					&& nexVertex.getPropertyBean().getValue(
							VERTEX_COLOR).equals(color)){
				gcd = SDFMath.gcd(gcd, getColorGcd(nexVertex, treatedVertices));
			}
		}
		for (SDFEdge edge : graph.incomingEdgesOf(vertex)) {
			if ((!treatedVertices.contains(graph.getEdgeSource(edge))) 
					&& graph.getEdgeSource(edge).getPropertyBean().getValue(
							VERTEX_COLOR).equals(color)) {
				SDFAbstractVertex precVertex = graph.getEdgeSource(edge);
				gcd = SDFMath.gcd(gcd, getColorGcd(precVertex, treatedVertices));
			}
		}
		return gcd;
	}

	/**
	 * 
	 * @param minLoopSize
	 * @return The list of loops
	 * @throws InvalidExpressionException 
	 */
	public List<List<SDFAbstractVertex>> getLoops(int minLoopSize) throws InvalidExpressionException{
		detect(minLoopSize);
		SDFIterator iterator = new SDFIterator(graph) ;
		HashMap<Color, List<SDFAbstractVertex>> colorToLoops = new HashMap<Color, List<SDFAbstractVertex>>() ;
		while(iterator.hasNext()){
			SDFAbstractVertex vertex = iterator.next();
				Color vertexColor = (Color) vertex.getPropertyBean().getValue(VERTEX_COLOR) ;
				if(colorToLoops.get(vertexColor) == null){
					List<SDFAbstractVertex> vertices = new ArrayList<SDFAbstractVertex>();
					vertices.add(vertex);
					colorToLoops.put(vertexColor, vertices);
				}else{
					colorToLoops.get(vertexColor).add(vertex);
				}
		}
		return new ArrayList<List<SDFAbstractVertex>>(colorToLoops.values());
	}


}
