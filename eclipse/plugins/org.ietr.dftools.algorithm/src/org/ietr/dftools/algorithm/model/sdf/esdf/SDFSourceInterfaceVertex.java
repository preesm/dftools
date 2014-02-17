package org.ietr.dftools.algorithm.model.sdf.esdf;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.InterfaceDirection;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;

/**
 * Source Interface vertex, emit tokens on its output edge
 * @author jpiat
 *
 */
public class SDFSourceInterfaceVertex extends SDFInterfaceVertex{

	
	/**
	 * Builds a new Source interface
	 */
	public SDFSourceInterfaceVertex() {
		super();
		setKind(PORT);
		setDirection(InterfaceDirection.Input);
	}
	
	@Override
	public SDFInterfaceVertex clone() {
		SDFSourceInterfaceVertex copy  = new SDFSourceInterfaceVertex();
		copy.setName(this.getName());
		return copy ;
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

