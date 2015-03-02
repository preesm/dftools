package org.ietr.dftools.algorithm.optimisations.clustering.fast;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.ietr.dftools.algorithm.Rational;
import org.ietr.dftools.algorithm.SDFMath;
import org.ietr.dftools.algorithm.demo.SDFAdapterDemo;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.optimisations.clustering.ClusteringConditions;

/**
 *Cluster vertex on SDFGraph
 * 
 * @author pthebault
 *
 */
public class FastGraphClustering {
	private static final String CLUSTER = "cluster";
	
	private static final Color[] colorSet = { Color.blue,Color.cyan,Color.darkGray,Color.gray,
		Color.green,Color.lightGray,Color.magenta,Color.pink,Color.red,
		Color.yellow,Color.black,Color.blue.brighter(),Color.cyan.brighter(),Color.darkGray.brighter(),Color.gray.brighter(),
		Color.green.brighter(),Color.lightGray.brighter(),Color.magenta.brighter(),Color.orange.brighter(),Color.pink.brighter(),Color.red.brighter(),
		Color.yellow.brighter(),Color.black.brighter(),Color.blue.darker(),Color.cyan.darker(),Color.darkGray.darker(),Color.gray.darker(),
		Color.green.darker(),Color.lightGray.darker(),Color.magenta.darker(),Color.orange.darker(),Color.pink.darker(),Color.red.darker(),
		Color.yellow.darker(),Color.black.darker()};

	
	/**
	 * Cluster vertices from SDFGraph, make a new SDFGraph where the vertices to cluster are replace by a composite vertex 
	 * 
	 * @param vertices is a list of vertex to cluster
	 * @param graphin is the vertices'graph
	 * @param vrb is the repetion vector of graphin
	 * @param nbCluster is the index of the cluster to create
	 * @return graphout the new graph with cluster
	 * @throws InvalidExpressionException 
	 */
	public static SDFGraph SDFclustering(ArrayList<SDFAbstractVertex> vertices,SDFGraph graphin,HashMap<SDFAbstractVertex, Integer> vrb,int nbCluster) throws InvalidExpressionException{
		SDFGraph graphout = graphin.clone();
		SDFVertex composite= new SDFVertex();
		composite.getPropertyBean().setValue(CLUSTER,nbCluster);
		int pgcdvertices = 0;
		for (int i = 0; i < vertices.size(); i++) {
			graphout.removeVertex(graphout.getVertex(vertices.get(i).getName()));
			composite.setName(composite.getName()+vertices.get(i).getName()+"   ");
			pgcdvertices=SDFMath.gcd(pgcdvertices, vrb.get(vertices.get(i)));
		}
		graphout.addVertex(composite);
		
		
		for(SDFEdge edge: graphin.edgeSet()){
			if(vertices.contains(edge.getSource())&&!vertices.contains(edge.getTarget())){
				SDFEdge newEdge=new SDFEdge();
				newEdge=graphout.addEdge(composite, graphout.getVertex(edge.getTarget().getName()));
				newEdge.setCons(edge.getCons());
				newEdge.setDelay(edge.getDelay());
				newEdge.setProd(new SDFIntEdgePropertyType(edge.getProd().intValue()*vrb.get(graphin.getVertex(edge.getSource().getName()))/pgcdvertices));
			}
			else if(!vertices.contains(edge.getSource())&&vertices.contains(edge.getTarget())){
				SDFEdge newEdge=new SDFEdge();
				newEdge=graphout.addEdge(graphout.getVertex(edge.getSource().getName()),composite );
				newEdge.setProd(edge.getProd());
				newEdge.setDelay(edge.getDelay());
				newEdge.setCons(new SDFIntEdgePropertyType(edge.getCons().intValue()*vrb.get(graphin.getVertex(edge.getTarget().getName()))/pgcdvertices));
			}
		}
		
		return graphout;
		
	}
	/**
	 * Set vertex color in function of the cluster number of them
	 * 
	 * @param graphin is the graph to set vertices colors
	 * @return the graph with colored vertex
	 */
	public static SDFGraph SDFclusteringColor(SDFGraph graphin){
		int cluster;
		for (SDFAbstractVertex vertex : graphin.vertexSet()) {
			cluster = (Integer) vertex.getPropertyBean().getValue(CLUSTER);
			if(cluster!=0){
				vertex.getPropertyBean().setValue(SDFAdapterDemo.VERTEX_COLOR,
					colorSet[cluster]);
			}
		}
		return graphin;
	}
	/**
	 * Cluster Algorithm that select the vertices to cluster 
	 * @param graphin is the graph which is clustered 
	 * @param lightclustering  is a boolean to make the clusterisation less efficient for the graph which has few vertex 
	 * @param Cluster_repeat is the repeat vector of clusters
	 * @return A new graph where vertex are clustered
	 * @throws InvalidExpressionException 
	 */
	public static SDFGraph SDFVertexClustering(SDFGraph graphin,boolean lightclustering,HashMap<Integer, String> Cluster_repeat) throws InvalidExpressionException{
		SDFGraph graphtemp=graphin.clone();
		SDFGraph graphout=graphin.clone();
		HashMap<Integer, Vector<String>> Clusters=new HashMap<Integer, Vector<String>>();
		HashMap<SDFAbstractVertex, Integer> vrb=new HashMap<SDFAbstractVertex, Integer>();
		Rational maxbalanced ;
		Vector<SDFEdge> Cluster_impossible= new Vector<SDFEdge>();
		SDFEdge maxEdge;
		do{

			HashMap<SDFEdge, Rational> deltaQ = new HashMap<SDFEdge, Rational>();
			for (SDFEdge edge:graphtemp.edgeSet()) {
				//Choose cluster with balanced algorithm
				Rational ai =new Rational(0,1);
				Rational aj =new Rational(0,1);
				Rational eij=new Rational(0,1);
				for(SDFEdge other_edge:graphtemp.getAllEdges(edge.getSource(), edge.getTarget())){
					eij=Rational.add(eij,new Rational(other_edge.getCons().intValue(),other_edge.getProd().intValue()));
				}
				for(SDFEdge other_edge:graphtemp.getAllEdges(edge.getTarget(), edge.getSource())){
					eij=Rational.add(eij,new Rational(other_edge.getCons().intValue(),other_edge.getProd().intValue()));
				}						
				for (SDFAbstractVertex vertexk:graphtemp.vertexSet()) {
					if(graphtemp.containsEdge(edge.getSource(),vertexk)&& edge.getTarget()!= vertexk){
						Rational temp =new Rational(graphtemp.getEdge(edge.getSource(),vertexk).getCons().intValue(),graphtemp.getEdge(edge.getSource(),vertexk).getProd().intValue());
						ai=Rational.add(ai, temp);
					}
					else if(graphtemp.containsEdge(edge.getTarget(),vertexk)&& edge.getSource()!= vertexk){
						Rational temp =new Rational(graphtemp.getEdge(edge.getTarget(),vertexk).getCons().intValue(),graphtemp.getEdge(edge.getTarget(),vertexk).getProd().intValue());
						aj=Rational.add(aj, temp);
					}
				}
				if(aj.getNum()==0&&(aj.getNum()!=0||lightclustering)){
					aj=new Rational(1,1);
				}
				if(ai.getNum()==0){
					ai=new Rational(1,1);
				}
				ai=Rational.prod(ai, aj);
				ai=Rational.prod(new Rational(2,1), ai);
			//	Rational value= Rational.sub(eij , ai);
			//	deltaQ.put(edge, value);
				Rational valuebis= Rational.sub( Rational.prod(eij,new Rational(SDFMath.gcd(vrb.get(edge.getSource()), vrb.get(edge.getTarget())),1)) , ai);
				deltaQ.put(edge, valuebis);
			}	
			
			
			SDFEdge maxbalancedEdge=null;
			SDFEdge maxgcdEdge=null;
			boolean test;
			Cluster_impossible.removeAllElements();
			do{
				maxEdge=null;
				test=true;
				maxbalancedEdge = null;
				 maxgcdEdge=null;
				maxbalanced= new Rational(0,1);
				for(SDFEdge edge:graphtemp.edgeSet()){
					Rational temp=Rational.sub(deltaQ.get(edge), maxbalanced);
					if(temp.getNum()>0&&temp.getDenum()>0){
						maxbalanced=deltaQ.get(edge);
						maxbalancedEdge=edge;
					}
				}
				if(maxbalancedEdge!=null){
					if(ClusteringConditions.SDFClusteringConditions(maxbalancedEdge,graphtemp,vrb)){
						maxEdge=maxbalancedEdge;
					}
					else{
						deltaQ.put(maxbalancedEdge, new Rational(0,1));
						Cluster_impossible.add(maxbalancedEdge);
					}
				}
/*				else if(maxgcdEdge!=null){
					if(ClusteringConditions.SDFClusteringConditions(maxgcdEdge,graphtemp,vrb)){
						maxEdge=maxgcdEdge;
					}
					else
					{
						Cluster_impossible.add(maxgcdEdge);
					}
				}*/
				if(maxEdge==null&&(maxbalancedEdge!=null||maxgcdEdge!=null)){
						test=true;
				}	
				else{
					test=false;
				}
			}while(test);
			if(maxEdge!=null){
				
				ArrayList<SDFAbstractVertex> vertices =new ArrayList<SDFAbstractVertex>();
				vertices.add(maxEdge.getSource());
				vertices.add(maxEdge.getTarget());
				
				int nbClustertemp=0;
				if((Integer)maxEdge.getSource().getPropertyBean().getValue(CLUSTER)!=0){
					nbClustertemp=(Integer)maxEdge.getSource().getPropertyBean().getValue(CLUSTER);
				}
				if((Integer)maxEdge.getTarget().getPropertyBean().getValue(CLUSTER)!=0){
					if(nbClustertemp==0){
						nbClustertemp=(Integer)maxEdge.getTarget().getPropertyBean().getValue(CLUSTER);
						Clusters.get(nbClustertemp).add(maxEdge.getSource().getName());
					}
					else{
						Clusters.get(nbClustertemp).addAll(Clusters.get(maxEdge.getTarget().getPropertyBean().getValue(CLUSTER)));
						Clusters.get(maxEdge.getTarget().getPropertyBean().getValue(CLUSTER)).removeAllElements();
					}
				}
				else if(nbClustertemp!=0){
					Clusters.get(nbClustertemp).add(maxEdge.getTarget().getName());
				}
				if(nbClustertemp==0){
					for(int index=1;index<graphin.vertexSet().size()/2;index++){
						if(Clusters.containsKey(index)){
							if(Clusters.get(index).isEmpty()){
								nbClustertemp=index;
								break;
							}
						}
						else{
							nbClustertemp=index;
							break;
						}
					}
					if(Clusters.get(nbClustertemp)==null){
						Clusters.put(nbClustertemp, new Vector<String>());
					}
					Clusters.get(nbClustertemp).add(maxEdge.getSource().getName());
					Clusters.get(nbClustertemp).add(maxEdge.getTarget().getName());
				}
				graphtemp=SDFclustering(vertices, graphtemp,vrb,nbClustertemp);
				Cluster_impossible.removeAllElements();
			}		
		}while(maxEdge!=null);
		
		for(SDFAbstractVertex vertex:graphtemp.vertexSet()){
			if((Integer)vertex.getPropertyBean().getValue(CLUSTER)!=0){
				Cluster_repeat.put((Integer)vertex.getPropertyBean().getValue(CLUSTER), vertex.toString()+" x"+vrb.get(vertex).toString());
			}
		}
		for(int index:Clusters.keySet()){
			for(String vertex_name:Clusters.get(index)){
				graphout.getVertex(vertex_name).getPropertyBean().setValue(CLUSTER, index);
			}
		}
		
		return graphout;//SDFclusteringColor(graphout)
	}
}
