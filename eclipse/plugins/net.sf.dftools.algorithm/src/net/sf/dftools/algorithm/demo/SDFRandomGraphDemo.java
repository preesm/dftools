package net.sf.dftools.algorithm.demo;

import java.util.Vector;

import net.sf.dftools.algorithm.generator.SDFRandomGraph;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.visitors.TopologyVisitor;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;
import net.sf.dftools.algorithm.optimisations.clustering.internalisation.SDFInternalisation;

/**
 * Test class to test the creation of Random generating graph
 * 
 * @author pthebault
 * 
 */
public class SDFRandomGraphDemo{
	
	/**
	 * applet as an application.
	 * 
	 * @param args
	 *            ignored.
	 * @throws InvalidExpressionException 
	 */

	public static void main(String[] args) throws InvalidExpressionException {
		int nbVertex =50, minInDegree = 1, maxInDegree = 2, minOutDegree = 1, maxOutDegree =2, minrate = 1, maxrate = 2;
		SDFAdapterDemo applet = new SDFAdapterDemo();
		SDFRandomGraph test = new SDFRandomGraph();
		TopologyVisitor topo = new TopologyVisitor();
		SDFGraph demoGraph = test.createRandomGraph(nbVertex, minInDegree,
				maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate,15);
		try {
			demoGraph.accept(topo);
		} catch (SDF4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		applet.init(demoGraph);
		
		Vector<SDFAbstractVertex> Top=new Vector<SDFAbstractVertex>();
		Vector<SDFAbstractVertex> Bottom=new Vector<SDFAbstractVertex>();
		SDFInternalisation.FindTopBottom(demoGraph, Top, Bottom);
		System.out.println(Top);
/*		
 		SDFRandomGraphDemo applet3 = new SDFRandomGraphDemo();
		ToHSDFVisitor visitor2 = new ToHSDFVisitor();
		demoGraph.accept(visitor2);
		applet3.init(visitor2.getOutput());
//*/
	}

}
