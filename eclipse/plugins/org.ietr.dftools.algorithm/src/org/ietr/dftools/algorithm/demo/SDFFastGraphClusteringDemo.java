package org.ietr.dftools.algorithm.demo;

import java.util.HashMap;

import org.ietr.dftools.algorithm.generator.SDFRandomGraph;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.ietr.dftools.algorithm.optimisations.clustering.fast.FastGraphClustering;

/**
 * Test class for FastGraphClustering, generate a random graph and cluster possible vertices
 * 
 * @author pthebault
 *
 */
public class SDFFastGraphClusteringDemo{


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
				int nbVertex =10, minInDegree = 1, maxInDegree = 2, minOutDegree = 1, maxOutDegree = 2,
				minrate = 1, maxrate =10;
				SDFAdapterDemo applet = new SDFAdapterDemo() ;
				SDFRandomGraph test1=new SDFRandomGraph();
				
				SDFGraph demoGraph=new SDFGraph();
				SDFGraph demoGraph2=new SDFGraph();
				demoGraph = test1.createRandomGraph(nbVertex, minInDegree,
					maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate);
				
				HashMap<Integer, String> Cluster_repeat=new HashMap<Integer, String>();
				demoGraph2=FastGraphClustering.SDFVertexClustering(demoGraph,false,Cluster_repeat);
				
				applet.init(demoGraph2);

				System.out.println(Cluster_repeat.toString());

			}

}
