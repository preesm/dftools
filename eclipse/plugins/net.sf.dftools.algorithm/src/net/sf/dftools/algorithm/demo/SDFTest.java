package net.sf.dftools.algorithm.demo;

import org.jgrapht.alg.CycleDetector;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFVertex;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

/**
 * Class for testing purposes
 * 
 * @author jpiat
 * 
 */
public class SDFTest {

	/**
	 * Main method of the class
	 * 
	 * @param args
	 * @throws SDF4JException 
	 */
	public static void main(String[] args) throws SDF4JException {
		SDFGraph graph = new SDFGraph();

		SDFVertex vertex1 = new SDFVertex();
		vertex1.setName("V1");
		graph.addVertex(vertex1);

		SDFVertex vertex2 = new SDFVertex();
		vertex2.setName("V2");
		graph.addVertex(vertex2);

		/*
		 * SDFVertex vertex3 = new SDFVertex();
		 * vertex3.setName("V3"); graph.addVertex(vertex3);
		 */

		/*
		 * SDFVertex vertex4 = new SDFVertex();
		 * graph.addVertex(vertex4);
		 */

		SDFEdge edge1 = graph.addEdge(vertex1, vertex2);
		edge1.setProd(new SDFIntEdgePropertyType(999));
		edge1.setCons(new SDFIntEdgePropertyType(1000));

		/*
		 * SDFEdge edge2 = graph.addEdge(vertex2, vertex3); edge2.setProd(3);
		 * edge2.setCons(2);
		 */

		/*
		 * SDFEdge edge3 = graph.addEdge(vertex3, vertex3); edge3.setProd(1);
		 * edge3.setCons(1);
		 */

		/*
		 * SDFEdge edge4 = graph.addEdge(vertex4, vertex1); edge4.setProd(3);
		 */

		System.out.println("Is schedulable : "
				+ graph.isSchedulable());
		CycleDetector<SDFAbstractVertex, SDFEdge> detectCycles = new CycleDetector<SDFAbstractVertex, SDFEdge>(
				graph);
		System.out.println("As cycles :" + detectCycles.detectCycles());

		System.out.println("As nb cycles :" + detectCycles.findCycles().size());

	}
}
