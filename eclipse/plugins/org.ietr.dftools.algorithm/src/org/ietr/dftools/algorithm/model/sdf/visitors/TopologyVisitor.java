package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.ietr.dftools.algorithm.model.visitors.VisitorOutput;


/**
 * Visitor used to determine whether a graph is schedulable or not
 * 
 * @author jpiat
 * @author jheulot
 * 
 */
public class TopologyVisitor implements IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	boolean schedulable = true ;

	/**
	 * Gives the result of the schedulability test
	 * @return True if the graph is schedulable, false if not
	 */
	public boolean result(){
		return schedulable ;
	}
	
	@Override
	public void visit(SDFEdge sdfEdge) {

	}

	@Override
	public void visit(SDFGraph sdf) throws SDF4JException{
		List<SDFAbstractVertex> vertices = new ArrayList<SDFAbstractVertex>();
		for (SDFAbstractVertex vertex : sdf.vertexSet()) {
			if(!(vertex instanceof SDFInterfaceVertex)){
				vertex.accept(this);
				vertices.add(vertex);
			}
			
		}
		if(sdf.isSchedulable()){
			schedulable &= true ;
		} else {
			schedulable &= false ;
			VisitorOutput.getLogger().log(Level.SEVERE, "Graph "+sdf.getName()+" is not schedulable");
		}
	}

	@Override
	public void visit(SDFAbstractVertex sdfVertex) throws SDF4JException{
		if(sdfVertex.getGraphDescription() != null){
			sdfVertex.getGraphDescription().accept(this);
		}
	}

}
