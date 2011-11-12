package net.sf.dftools.algorithm.optimisations.clustering.stronglyconnected;

import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.alg.StrongConnectivityInspector;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;
import net.sf.dftools.algorithm.optimisations.clustering.Clusterize;

/**
 * Class to clusterize strongly connected components
 * @author jpiat
 *
 */
public class ClusterizeStronglyConnected {

	/**
	 * Clusterize strongly connected components
	 * @param graph The graph in which to look for clusters
	 * @throws InvalidExpressionException 
	 * @throws SDF4JException 
	 */
	public ClusterizeStronglyConnected(SDFGraph graph) throws InvalidExpressionException, SDF4JException{
		int i = 0 ;
		StrongConnectivityInspector<SDFAbstractVertex, SDFEdge> inspector = new StrongConnectivityInspector<SDFAbstractVertex, SDFEdge>(graph) ;
		for(Set<SDFAbstractVertex> strong : inspector.stronglyConnectedSets()){
			boolean noInterface = true ;
			for(SDFAbstractVertex vertex :strong){
				noInterface &= !(vertex instanceof SDFInterfaceVertex) ;
			}
			if(noInterface){
				Clusterize.culsterizeBlocks(graph, new ArrayList<SDFAbstractVertex>(strong), "cluster_"+i);
				i ++ ;
			}
			
		}
	}
}
