package net.sf.dftools.algorithm.factories;

import org.jgrapht.EdgeFactory;
import net.sf.dftools.algorithm.model.dag.DAGEdge;
import net.sf.dftools.algorithm.model.dag.DAGVertex;

/**
 * Factory to create DAG edges
 * 
 * @author jpiat
 * 
 */
public class DAGEdgeFactory implements EdgeFactory<DAGVertex, DAGEdge> {

	@Override
	public DAGEdge createEdge(DAGVertex arg0, DAGVertex arg1) {
		return new DAGEdge();
	}

}
