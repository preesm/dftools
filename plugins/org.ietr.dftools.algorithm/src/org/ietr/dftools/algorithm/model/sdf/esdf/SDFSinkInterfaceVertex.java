package org.ietr.dftools.algorithm.model.sdf.esdf;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.InterfaceDirection;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;

/**
 * Sink interface vertex receive tokens on its input edge
 * @author jpiat
 *
 */
public class SDFSinkInterfaceVertex extends SDFInterfaceVertex{

	
	/**
	 * Builds a new Sink interface
	 */
	public SDFSinkInterfaceVertex() {
		super();
		setKind(PORT);
		setDirection(InterfaceDirection.Output);
	}

	@Override
	public SDFInterfaceVertex clone() {
		SDFSinkInterfaceVertex copy  = new SDFSinkInterfaceVertex();
		copy.setName(this.getName());
		if (this.getSources().size() != 0) {
			SDFSourceInterfaceVertex so = new SDFSourceInterfaceVertex();
			so.setName(this.getName());
			copy.addSink(so);
		}
		return copy;
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
