package net.sf.dftools.algorithm.model.dag;

import java.util.ArrayList;

import net.sf.dftools.algorithm.model.AbstractEdge;

/**
 * Class to represent the edges aggregate while transforming SDF to DAG
 * @author jpiat
 *
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class EdgeAggregate extends ArrayList<AbstractEdge>{
	
	/**
	 * Creates a new EdgeAggregate
	 */
	public EdgeAggregate(){
		super() ;
	}
	
	/**
	 * Add an edge to this aggregate
	 * @param edge
	 */
	public void addEdge(AbstractEdge edge){
		super.add(edge);
	}


}
