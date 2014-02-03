package org.ietr.dftools.algorithm.demo;

import java.util.HashMap;
import java.util.Vector;

import org.ietr.dftools.algorithm.SDFMath;
import org.ietr.dftools.algorithm.generator.SDFRandomGraph;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.ietr.dftools.algorithm.optimisations.clustering.internalisation.SDFInternalisation;

/**
 * @author pthebault
 *
 */
public class SDFInternalisationDemo{

	private static final String CLUSTER = "cluster";


	
	
	/**
	 * applet as an application.
	 * 
	 * @param args
	 *            ignored.
	 * @throws InvalidExpressionException 
	 * @throws SDF4JException 
	 */

	public static void main(String[] args) throws InvalidExpressionException, SDF4JException {
		int nbVertex =40, minInDegree = 1, maxInDegree = 2, minOutDegree = 1, maxOutDegree =2, minrate = 1, maxrate = 20;
		SDFAdapterDemo applet = new SDFAdapterDemo();
		SDFRandomGraph test = new SDFRandomGraph();
		
		SDFGraph demoGraph = test.createRandomGraph(nbVertex, minInDegree,
				maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate);
		
		demoGraph=SDFInternalisation.PartitionGraph(demoGraph);
		HashMap<Integer, Vector<SDFAbstractVertex>> clusters_internalisation=new HashMap<Integer, Vector<SDFAbstractVertex>>();
		for(SDFAbstractVertex vertex:demoGraph.vertexSet()){
			if(clusters_internalisation.get(vertex.getPropertyBean().getValue(CLUSTER))==null){
				clusters_internalisation.put((Integer) vertex.getPropertyBean().getValue(CLUSTER),new Vector<SDFAbstractVertex>());
			}
			clusters_internalisation.get((Integer)vertex.getPropertyBean().getValue(CLUSTER)).add(vertex);	
		}
		HashMap<Integer, Integer> intenalisation_vrb_cluster=new HashMap<Integer, Integer>();
		for(Integer key:clusters_internalisation.keySet()){
			int gcd=0;
			for(SDFAbstractVertex vertex :clusters_internalisation.get(key)){
				gcd=SDFMath.gcd(gcd,vertex.getNbRepeatAsInteger());
			}
			intenalisation_vrb_cluster.put(key, gcd);
		}	
		int h_internalisation=0;
		for(SDFEdge edge:demoGraph.edgeSet()){
			if(edge.getSource().getPropertyBean().getValue(CLUSTER)!=edge.getTarget().getPropertyBean().getValue(CLUSTER)){
				h_internalisation+=edge.getProd().intValue()*edge.getSource().getNbRepeatAsInteger();
			}
		}
		
		System.out.println(clusters_internalisation.toString());
		System.out.println(intenalisation_vrb_cluster.toString());
		System.out.println(h_internalisation);
		SDFInternalisation.SDFclusteringColor(demoGraph);
		applet.init(demoGraph);
		

	}


}
