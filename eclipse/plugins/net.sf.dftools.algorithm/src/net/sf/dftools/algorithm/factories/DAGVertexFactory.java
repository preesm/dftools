package net.sf.dftools.algorithm.factories;

import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.dag.DAGVertex;
import net.sf.dftools.algorithm.model.dag.edag.DAGBroadcastVertex;
import net.sf.dftools.algorithm.model.dag.edag.DAGEndVertex;
import net.sf.dftools.algorithm.model.dag.edag.DAGForkVertex;
import net.sf.dftools.algorithm.model.dag.edag.DAGInitVertex;
import net.sf.dftools.algorithm.model.dag.edag.DAGJoinVertex;

import org.w3c.dom.Element;

/**
 * Factory for DAGVertex creation
 * 
 * @author jpiat
 * 
 */
public class DAGVertexFactory extends ModelVertexFactory<DAGVertex> {

	private static DAGVertexFactory instance;

	private DAGVertexFactory() {

	}

	
	public static DAGVertexFactory getInstance() {
		if (instance == null) {
			instance = new DAGVertexFactory();
		}
		return instance;
	}

	public DAGVertex createVertex(Element vertexElt) {
		String kind = this.getProperty(vertexElt, AbstractVertex.KIND);
		return this.createVertex(kind);
	}
	
	public DAGVertex createVertex(String kind) {
		if (kind.equals(DAGVertex.DAG_VERTEX)) {
			return new DAGVertex();
		} else if (kind.equals(DAGBroadcastVertex.DAG_BROADCAST_VERTEX)) {
			return new DAGBroadcastVertex();
		} else if (kind.equals(DAGForkVertex.DAG_FORK_VERTEX)) {
			return new DAGForkVertex();
		} else if (kind.equals(DAGJoinVertex.DAG_JOIN_VERTEX)) {
			return new DAGJoinVertex();
		} else if (kind.equals(DAGInitVertex.DAG_INIT_VERTEX)) {
			return new DAGInitVertex();
		} else if (kind.equals(DAGEndVertex.DAG_END_VERTEX)) {
			return new DAGEndVertex();
		} else {
			return new DAGVertex();
		}
	}

}
