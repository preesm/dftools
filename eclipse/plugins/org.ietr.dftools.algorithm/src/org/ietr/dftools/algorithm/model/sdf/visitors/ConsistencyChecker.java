package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.util.logging.Level;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.ietr.dftools.algorithm.model.visitors.VisitorOutput;


/**
 * Verifies that graph doesn't contains mistakes (port mismatch, case sensitivity)
 * @author jpiat
 *
 */
public class ConsistencyChecker implements IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	private boolean isConsistent ;
	@Override
	public void visit(SDFEdge sdfEdge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SDFGraph sdf) throws SDF4JException{
		for(SDFAbstractVertex vertex : sdf.vertexSet()){
			vertex.accept(this);
		}
		
	}

	@Override
	public void visit(SDFAbstractVertex sdfVertex) throws SDF4JException{
		if(sdfVertex.getGraphDescription() != null){
			SDFGraph graphDescription = (SDFGraph) sdfVertex.getGraphDescription();
			for(SDFEdge edge : ((SDFGraph) sdfVertex.getBase()).incomingEdgesOf(sdfVertex)){
				if(graphDescription.getVertex(edge.getTargetInterface().getName()) == null){
					VisitorOutput.getLogger().log(Level.SEVERE, "Interface "+edge.getTargetInterface().getName()+" does not exist in vertex "+sdfVertex.getName()+" hierarchy");
					isConsistent &= false ;
				}else if(graphDescription.getVertex(edge.getTargetInterface().getName()) != null){
					SDFAbstractVertex sourceNode = graphDescription.getVertex(edge.getTargetInterface().getName());
					if(graphDescription.outgoingEdgesOf(sourceNode).size() == 0){
						VisitorOutput.getLogger().log(Level.SEVERE, "Interface "+edge.getTargetInterface().getName()+" does not exist, or is not connect in vertex "+sdfVertex.getName()+" hierarchy");
						isConsistent &= false ;
					}
				}
			}for(SDFEdge edge : ((SDFGraph) sdfVertex.getBase()).outgoingEdgesOf(sdfVertex)){
				if(graphDescription.getVertex(edge.getSourceInterface().getName()) == null){
					VisitorOutput.getLogger().log(Level.SEVERE, "Interface "+edge.getSourceInterface().getName()+" does not exist in vertex "+sdfVertex.getName()+" hierarchy" );
					isConsistent &= false ;
				}else if(graphDescription.getVertex(edge.getSourceInterface().getName()) != null){
					SDFAbstractVertex  sinkNode = graphDescription.getVertex(edge.getSourceInterface().getName());
					if(graphDescription.incomingEdgesOf(sinkNode).size() == 0){
						VisitorOutput.getLogger().log(Level.SEVERE, "Interface "+edge.getSourceInterface().getName()+" does not exist, or is not connect in vertex "+sdfVertex.getName()+" hierarchy");
						isConsistent &= false ;
					}
				}
			}
			graphDescription.accept(this);
		}
	}
	
	/**
	 * Verify a given graph consistency
	 * @param toVerify  The graph on which to verify consistency
	 * @return  True if the graph is consistent, false otherwise
	 */
	public boolean verifyGraph(SDFGraph toVerify){
		isConsistent = true ;
		try {
			toVerify.accept(this);
		} catch (SDF4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false ;
		}
		return isConsistent ;
	}
	
	

}
