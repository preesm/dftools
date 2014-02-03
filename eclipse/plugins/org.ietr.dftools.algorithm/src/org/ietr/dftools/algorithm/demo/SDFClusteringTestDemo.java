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
import org.ietr.dftools.algorithm.optimisations.clustering.fast.FastGraphClustering;
import org.ietr.dftools.algorithm.optimisations.clustering.internalisation.SDFInternalisation;
import org.ietr.dftools.algorithm.optimisations.clustering.mfa.MFAPartitioning;



/**
 * Test class to compare three clustering algoritms( FastClustering, Internalisation and MFA)
 * 
 * @author pthebault
 *
 */
public class SDFClusteringTestDemo{
	
	private static final String CLUSTER = "cluster";
	
	// ~ Instance fields
	// --------------------------------------------------------

	
	// ~ Methods
	// ----------------------------------------------------------------

	/**
	 * An alternative starting point for this demo, to also allow running this
	 * applet as an application.
	 * 
	 * @param args
	 *            ignored.
	 * @throws InvalidExpressionException 
	 * @throws SDF4JException 
	 */
	public static void main(String[] args) throws InvalidExpressionException, SDF4JException {
		int nbVertex =10, minInDegree = 1, maxInDegree = 5, minOutDegree = 1, maxOutDegree = 5,
		minrate = 1, maxrate =10;
		
		SDFRandomGraph Demo=new SDFRandomGraph();
		
		SDFGraph demoGraph=new SDFGraph();
		demoGraph = Demo.createRandomGraph(nbVertex, minInDegree,
			maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate);
		
		
		HashMap<Integer, String> Cluster_repeat=new HashMap<Integer, String>();
		SDFGraph demoGraph2=FastGraphClustering.SDFVertexClustering(demoGraph,false,Cluster_repeat);
		
		HashMap<Integer, Vector<SDFAbstractVertex>> clusters_fast=new HashMap<Integer, Vector<SDFAbstractVertex>>();
		for(SDFAbstractVertex vertex:demoGraph2.vertexSet()){
			if(clusters_fast.get(vertex.getPropertyBean().getValue(CLUSTER))==null){
				clusters_fast.put((Integer) vertex.getPropertyBean().getValue(CLUSTER),new Vector<SDFAbstractVertex>());
			}
			clusters_fast.get((Integer)vertex.getPropertyBean().getValue(CLUSTER)).add(vertex);	
		}
	
		HashMap<Integer, Integer> fast_vrb_cluster=new HashMap<Integer, Integer>();
		for(Integer key:clusters_fast.keySet()){
			int gcd=0;
			for(SDFAbstractVertex vertex :clusters_fast.get(key)){
				gcd=SDFMath.gcd(gcd,vertex.getNbRepeatAsInteger());
			}
			fast_vrb_cluster.put(key, gcd);
		}	
		
		int h_fast=0;
		//int cpt_fast=0;
		for(SDFEdge edge:demoGraph2.edgeSet()){
			if(edge.getSource().getPropertyBean().getValue(CLUSTER)!=edge.getTarget().getPropertyBean().getValue(CLUSTER)||(Integer)edge.getSource().getPropertyBean().getValue(CLUSTER)==0){
				h_fast+=edge.getProd().intValue()*edge.getSource().getNbRepeatAsInteger();
				//cpt_fast++;
			}
		}

		HashMap<Integer, Integer> size_cluster_fast=new HashMap<Integer, Integer>();
		for(Integer key : clusters_fast.keySet()){
			size_cluster_fast.put(key, clusters_fast.get(key).size());
		}
		int balanced_fast=0;
		for(SDFAbstractVertex vertexi:demoGraph2.vertexSet()){
			for(SDFAbstractVertex vertexj:demoGraph2.vertexSet()){
				if(vertexj!=vertexi && (vertexi.getPropertyBean().getValue(CLUSTER)!=vertexj.getPropertyBean().getValue(CLUSTER))||(Integer)vertexi.getPropertyBean().getValue(CLUSTER)==0){
					balanced_fast++;
				}
			}
		}		
		
		
		
		SDFGraph demoGraph3=SDFInternalisation.PartitionGraph(demoGraph);
		HashMap<Integer, Vector<SDFAbstractVertex>> clusters_internalisation=new HashMap<Integer, Vector<SDFAbstractVertex>>();
		for(SDFAbstractVertex vertex:demoGraph3.vertexSet()){
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
		//int cpt_internalisation=0;
		for(SDFEdge edge:demoGraph3.edgeSet()){
			if(edge.getSource().getPropertyBean().getValue(CLUSTER)!=edge.getTarget().getPropertyBean().getValue(CLUSTER)){
				h_internalisation+=edge.getProd().intValue()*edge.getSource().getNbRepeatAsInteger();
				//cpt_internalisation++;
			}
		}

		HashMap<Integer, Integer> size_cluster_internalisation=new HashMap<Integer, Integer>();
		for(Integer key : clusters_internalisation.keySet()){
			size_cluster_internalisation.put(key, clusters_internalisation.get(key).size());
		}
		int balanced_internalisation=0;
		for(SDFAbstractVertex vertexi:demoGraph3.vertexSet()){
			for(SDFAbstractVertex vertexj:demoGraph3.vertexSet()){
				if(vertexj!=vertexi && vertexi.getPropertyBean().getValue(CLUSTER)!=vertexj.getPropertyBean().getValue(CLUSTER)){
					balanced_internalisation++;
				}
			}
		}		
		
		
		
	//*	
		SDFGraph demoGraph4=demoGraph.clone();
		MFAPartitioning mfa = new MFAPartitioning(demoGraph4, clusters_internalisation.keySet().size(), 1, 1) ;
		mfa.compute(1);
		
		HashMap<Integer, Vector<SDFAbstractVertex>> clusters_mfa=new HashMap<Integer, Vector<SDFAbstractVertex>>();
		for(SDFAbstractVertex vertex:demoGraph4.vertexSet()){
			if(clusters_mfa.get(vertex.getPropertyBean().getValue(CLUSTER))==null){
				clusters_mfa.put((Integer) vertex.getPropertyBean().getValue(CLUSTER),new Vector<SDFAbstractVertex>());
			}
			clusters_mfa.get((Integer)vertex.getPropertyBean().getValue(CLUSTER)).add(vertex);	
		}
	
		
		
		HashMap<Integer, Integer> MFA_vrb_cluster=new HashMap<Integer, Integer>();
		for(Integer key:clusters_mfa.keySet()){
			int gcd=0;
			for(SDFAbstractVertex vertex :clusters_mfa.get(key)){
				gcd=SDFMath.gcd(gcd,vertex.getNbRepeatAsInteger());
			}
			MFA_vrb_cluster.put(key, gcd);
		}	
		int h_mfa=0;
		//int cpt_mfa=0;
		for(SDFEdge edge:demoGraph4.edgeSet()){
			if(edge.getSource().getPropertyBean().getValue(CLUSTER)!=edge.getTarget().getPropertyBean().getValue(CLUSTER)){
				h_mfa+=edge.getProd().intValue()*edge.getSource().getNbRepeatAsInteger();
				//cpt_mfa++;
			}
		}
		
		HashMap<Integer, Integer> size_cluster_mfa=new HashMap<Integer, Integer>();
		for(Integer key : clusters_mfa.keySet()){
			size_cluster_mfa.put(key, clusters_mfa.get(key).size());
		}
		
		int balanced_mfa=0;
		for(SDFAbstractVertex vertexi:demoGraph4.vertexSet()){
			for(SDFAbstractVertex vertexj:demoGraph4.vertexSet()){
				if(vertexj!=vertexi && vertexi.getPropertyBean().getValue(CLUSTER)!=vertexj.getPropertyBean().getValue(CLUSTER)){
					balanced_mfa++;
				}
			}
		}
		//*/
		System.out.println("\nClusters:");
		System.out.println("Fast:"+clusters_fast.toString());
		System.out.println("Internalisation:"+clusters_internalisation.toString());
		System.out.println("Mfa:"+clusters_mfa.toString());
		
		System.out.println("\nSize of clusters:");
		System.out.println("Fast:"+size_cluster_fast.toString());
		System.out.println("Internalisation:"+size_cluster_internalisation.toString());
		System.out.println("Mfa:"+size_cluster_mfa.toString());
		
		System.out.println("\nVrb of clusters:");
		System.out.println("Fast:"+fast_vrb_cluster.toString());
		System.out.println("Intenalisation:"+intenalisation_vrb_cluster.toString());
		System.out.println("MFA:"+MFA_vrb_cluster.toString());
		
		System.out.println("\nCommunications between clusters:");
		System.out.println("Fast:" + h_fast);
		System.out.println("Internalisation :" + h_internalisation);
		System.out.println("Mfa :"+ h_mfa);

		// the algorithm which has the most value is the most balanced
		System.out.println("\nBalance of clusters:");
		System.out.println("Fast:"+balanced_fast);
		System.out.println("Internalisation:"+balanced_internalisation);
		System.out.println("Mfa:"+balanced_mfa);
		
		//r is a repulsion coefficient
		double r=0.1;
		System.out.println("\nCost function:");
		System.out.println("Fast:"+(h_fast-r*balanced_fast));
		System.out.println("Internalisation:"+(h_internalisation-r*balanced_internalisation));
		System.out.println("Mfa:"+(h_mfa-r*balanced_mfa));
	}
	
	
}

