package org.ietr.dftools.algorithm.model.sdf.esdf;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;

/**
 * Special vertex to initialize data on looping edges
 * @author jpiat
 *
 */
public class SDFEndVertex extends SDFInitVertex{
	
	/**
	 * Kind of node
	 */
	public static final String END ="END"; 
	
	
	/**
	 * Creates a new SDFInterfaceVertex with the default direction (SINK)
	 */
	public SDFEndVertex() {
		super();
		setKind(END);
		setNbRepeat(1);
	}

	@Override
	public SDFAbstractVertex clone() {
		SDFEndVertex init = new SDFEndVertex();
		init.setName(this.getName());
		return init ;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionAdded(AbstractEdge e) {
		// Nothing to do for the moment
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionRemoved(AbstractEdge e) {
		// Nothing to do for the moment
	}

}
