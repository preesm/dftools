package net.sf.dftools.algorithm.model.sdf.visitors;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;
import net.sf.dftools.algorithm.model.visitors.VisitorOutput;

import org.math.array.LinearAlgebra;

/**
 * Visitor used to determine whether a graph is schedulable or not
 * 
 * @author jpiat
 * 
 */
public class TopologyVisitor implements GraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

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
				vertices.add((SDFAbstractVertex) vertex);
			}
			
		}
		int rank;
		if(vertices.size() == 1 ){
			schedulable &= true ;
			
			return ;
		}
		try {
			if (Array.getLength(sdf.getTopologyMatrix()) > 0) {
				rank = LinearAlgebra.rank(sdf.getTopologyMatrix());
			} else {
				rank = sdf.vertexSet().size() - 1;
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw(new SDF4JException(e.getMessage()));
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw(new SDF4JException(e.getMessage()));
		}
		if (rank == vertices.size()-1) {
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
