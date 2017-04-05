package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.util.Vector;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.jgrapht.alg.CycleDetector;

/**
 * Visitor to use to detect cycle in a hierarchical graph
 * 
 * @author jpiat
 * 
 */
public class CycleDetectorVisitor implements IGraphVisitor<SDFGraph, SDFVertex, SDFEdge> {

	private Vector<SDFGraph> containsCycles = new Vector<SDFGraph>();
	private boolean hasCycle = true;

	/**
	 * Detect cycles in the given graph
	 * 
	 * @param graph
	 *            The graph to visit
	 * @return true if the graph has cycles
	 */
	public boolean detectCyles(SDFGraph graph) {
		try {
			graph.accept(this);
		} catch (SDF4JException e) {
			e.printStackTrace();
			return false; 
		}
		return hasCycle;
	}


	@Override
	public void visit(SDFEdge sdfEdge) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SDFGraph sdf) throws SDF4JException{
		boolean hasCycle;
		CycleDetector<SDFAbstractVertex, SDFEdge> detector = new CycleDetector<SDFAbstractVertex, SDFEdge>(
				sdf);
		hasCycle = detector.detectCycles();
		if (hasCycle) {
			containsCycles.add(sdf);
		}
		this.hasCycle = this.hasCycle && hasCycle;
		for (SDFAbstractVertex vertex : sdf.vertexSet()) {
				vertex.accept(this);
		}
	}

	@Override
	public void visit(SDFVertex sdfVertex) throws SDF4JException{
		if(sdfVertex.getGraphDescription() != null ){
			sdfVertex.getGraphDescription().accept(this);
		}
	}

}
