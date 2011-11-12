package net.sf.dftools.algorithm.model.sdf.esdf;

import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;

/**
 * Special vertex that supports broadcast
 * @author jpiat
 *
 */
public class SDFBroadcastVertex extends SDFAbstractVertex{

	/**
	 * Kind of node
	 */
	public static final String BROADCAST ="Broadcast"; 
	

	/**
	 * Creates a new SDFInterfaceVertex with the default direction (SINK)
	 */
	public SDFBroadcastVertex() {
		super();
		setKind(BROADCAST);
		setNbRepeat(1);
	}

	@Override
	public SDFAbstractVertex clone() {
		SDFBroadcastVertex copy = new SDFBroadcastVertex();
		copy.setName(this.getName());
		try {
			copy.setNbRepeat(this.getNbRepeat());
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
