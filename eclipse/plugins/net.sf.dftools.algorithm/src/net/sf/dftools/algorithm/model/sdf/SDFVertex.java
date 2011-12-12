package net.sf.dftools.algorithm.model.sdf;

import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;


/**
 * Class used to represent Hierachical vertices, meaning it is a vertex in the
 * parent graph, but is itself a graph
 * 
 * @author jpiat
 * 
 */
public class SDFVertex extends SDFAbstractVertex {

	/**
	 * 
	 */
	protected static final long serialVersionUID = -4281714330859590518L;
	
	/**
	 * Kind of node
	 */
	public static final String VERTEX ="vertex"; 


	/**
	 * Builds a new vertex
	 */
	public SDFVertex() {
		super();
		setKind(VERTEX);
	}

	/**
	 * Constructs a new SDFVertex given its base graph arg0
	 * 
	 * @param arg0
	 */
	public SDFVertex(SDFGraph arg0) {
		super();
		setKind(VERTEX);
		setBase(arg0);
	}



	/**
	 * Clone the vertex
	 */
	public SDFVertex clone() {
		SDFVertex newVertex = new SDFVertex(null);
		for (String key : this.getPropertyBean().keys()) {
			if (this.getPropertyBean().getValue(key) != null) {
				Object val = this.getPropertyBean().getValue(key);
				newVertex.getPropertyBean().setValue(key, val);
			} 
		}
		for (SDFInterfaceVertex sink : this.getSinks()) {
			if (newVertex.getGraphDescription() != null
					&& newVertex.getGraphDescription().getVertex(sink.getName()) != null) {
				newVertex.addSink((SDFInterfaceVertex) this.getGraphDescription().getVertex(sink.getName()));
			}else{
				newVertex.addSink(sink.clone());
			}
		}
		for (SDFInterfaceVertex source : this.getSources()) {
			if (newVertex.getGraphDescription() != null
					&& newVertex.getGraphDescription().getVertex(source.getName()) != null) {
				newVertex.addSource((SDFInterfaceVertex) this.getGraphDescription().getVertex(source.getName()));
			}else{
				newVertex.addSource(source.clone());
			}
		}
		try {
			newVertex.setNbRepeat(this.getNbRepeat());
		} catch (InvalidExpressionException e) {
			e.printStackTrace();
		}
		return newVertex;
	}



	@Override
	public void connectionAdded(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionRemoved(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub
		
	}



}
