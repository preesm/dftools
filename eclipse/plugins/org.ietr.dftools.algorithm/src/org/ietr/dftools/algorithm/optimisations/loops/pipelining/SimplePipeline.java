package org.ietr.dftools.algorithm.optimisations.loops.pipelining;

import java.util.ArrayList;
import java.util.List;

import org.ietr.dftools.algorithm.SDFMath;
import org.ietr.dftools.algorithm.demo.SDFAdapterDemo;
import org.ietr.dftools.algorithm.generator.SDFRandomGraph;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.ietr.dftools.algorithm.optimisations.clustering.Clusterize;
import org.ietr.dftools.algorithm.optimisations.loops.detection.LoopDetector;

/**
 * Implementation of the simple pipeline method
 * 
 * @author jpiat
 * 
 */
public class SimplePipeline {

	/**
	 * 
	 * @param args
	 * @throws InvalidExpressionException 
	 * @throws SDF4JException 
	 */
	public static void main(String[] args) throws InvalidExpressionException, SDF4JException {
		int nbVertex = 10, minInDegree = 1, maxInDegree = 2, minOutDegree = 1, maxOutDegree = 3;
		// Creates a random SDF graph
		int minrate = 1, maxrate = 100;
		SDFRandomGraph test = new SDFRandomGraph();
		SDFGraph demoGraph = test.createRandomGraph(nbVertex, minInDegree,
				maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate);
		if (!demoGraph.isSchedulable()) {
			System.out.println("not schedulable");
			System.exit(-1);
		}
			SimplePipeline pipe = new SimplePipeline();
			pipe.extractPipeline(demoGraph,3);

			SDFAdapterDemo adapter = new SDFAdapterDemo();
			adapter.init(demoGraph);
		
	}

	/**
	 * Builds a new SImplePipeline instance
	 */
	public SimplePipeline() {

	}

	/**
	 * Clusterize the given blocks
	 * @param graph The graph in which we work
	 * @param blocks The blocks o clusterize
	 * @param nbCluster
	 * @throws InvalidExpressionException 
	 * @throws SDF4JException 
	 */
	public void culsterizeBlocks(SDFGraph graph, List<List<SDFAbstractVertex>> blocks, int nbCluster) throws InvalidExpressionException, SDF4JException{
		if(blocks.get(blocks.size()-1).size() == 0){
			blocks.remove(blocks.size()-1);
		}
		int nameIndex = 0 ;
		if(blocks.size() > nbCluster){
			double doubleMod = (double) blocks.size()/nbCluster ;
			int moduloBlock = ((Double) doubleMod).intValue();
			if(doubleMod > moduloBlock){
				moduloBlock += 1;
			}
			for(int i = 0 ; i < nbCluster ; i ++ ){
				List<SDFAbstractVertex> block = blocks.get(i);
				for(int j = 0 ; (j < moduloBlock-1 && (i+1) < blocks.size()) ; j ++){
					block.addAll(blocks.get(i+1));
					blocks.remove(i+1);
				}
			}
		}
		for(List<SDFAbstractVertex> block : blocks){
			Clusterize.culsterizeBlocks(graph, block, "cluster_"+nameIndex);
			nameIndex ++ ;
		}
	}
	/**
	 * Extract pipeline informations from the graph
	 * 
	 * @param graph
	 * @param pipeLength
	 * @throws InvalidExpressionException 
	 * @throws SDF4JException 
	 */
	public void extractPipeline(SDFGraph graph, int pipeLength) throws InvalidExpressionException, SDF4JException {
		int i = 0;
		LoopDetector detector = new LoopDetector(graph);
		List<List<SDFAbstractVertex>> loops = detector.getLoops(4);
		for (List<SDFAbstractVertex> loop : loops) {
			System.out.println(" Loop " + i);
			int loopLength = (Integer) loop.get(0).getNbRepeat();
			for (SDFAbstractVertex instance : loop) {
				loopLength = SDFMath.gcd(loopLength, (Integer) instance.getNbRepeat());
			}
			System.out.println(" for i=1:" + loopLength);
			List<List<SDFAbstractVertex>> blocks = new ArrayList<List<SDFAbstractVertex>>();
			blocks.add(new ArrayList<SDFAbstractVertex>());
			int blockIndex = 0;
			int maxBlockSize = 0 ;
			SDFAbstractVertex blockLimit = null;
			for (SDFAbstractVertex instance : loop) {
				blocks.get(blockIndex).add(instance);
				int position = 0;
				for (SDFEdge edge : graph.incomingEdgesOf(instance)) {
					if (edge.getDelay().intValue() > 0
							&& loop.contains(graph.getEdgeSource(edge))
							&& graph.getEdgeSource(edge) != instance) {
						int vertexIndex = loop.indexOf(graph
								.getEdgeSource(edge));
						if (vertexIndex > position) {
							position = vertexIndex;
							blockLimit = graph.getEdgeSource(edge);
						}
					}
				}
				if (instance == blockLimit || blockLimit == null) {
					blocks.add(new ArrayList<SDFAbstractVertex>());
					blockIndex++;
					blockLimit = null;
				}
				if(blocks.get(blockIndex).size() > maxBlockSize){
					maxBlockSize = blocks.get(blockIndex).size() ;
				}
			}
			printSummary(blocks, maxBlockSize);
			System.out.println(blocks);
			culsterizeBlocks(graph, blocks, pipeLength);
			i++;
		}
	}
	/**
	 * Print a summary of the constructed pipelines
	 * @param blocks The blocks extracted from the loops
	 * @param maxBlockSize The max size of a block
	 */
	public void printSummary(List<List<SDFAbstractVertex>> blocks , int maxBlockSize){
		for (int printIndex = 0; printIndex < blocks.size(); printIndex++) {
			for (int j = 0; j <= maxBlockSize; j++) {
				for (int k = 0; k <= printIndex; k++) {
					if(j < blocks.get(k).size()){
						System.out.print(blocks.get(k).get(j) + " ");
					}else{
						System.out.print("\t");
					}
				}
				System.out.println();
			}
		}
	}
}
