package net.sf.dftools.algorithm.model.sdf.esdf;

import java.util.HashMap;
import java.util.Map;

import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.PropertySource;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;

/**
 * Special vertex that supports broadcast
 * 
 * @author jpiat
 * 
 */
public class SDFBroadcastVertex extends SDFAbstractVertex {

	/**
	 * Kind of node
	 */
	public static final String BROADCAST = "Broadcast";

	/**
	 * String to access the property edges order
	 */
	public static final String EDGES_ORDER = "edges_order";

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
		return copy;
	}

	protected void addConnection(SDFEdge newEdge) {
		getConnections().put(newEdge, getConnections().size());
	}

	protected void removeConnection(SDFEdge newEdge) {
		getConnections().remove(newEdge);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionAdded(AbstractEdge e) {
		addConnection((SDFEdge) e);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionRemoved(AbstractEdge e) {
		removeConnection((SDFEdge) e);
	}

	/**
	 * Gives the edge connection index
	 * 
	 * @param edge
	 *            The edge to get the connection index
	 * @return The connection index of the edge
	 */
	public Integer getEdgeIndex(SDFEdge edge) {
		return getConnections().get(edge);
	}

	@SuppressWarnings("unchecked")
	protected Map<SDFEdge, Integer> getConnections() {
		Map<SDFEdge, Integer> connections;
		if ((connections = (Map<SDFEdge, Integer>) this.getPropertyBean()
				.getValue(EDGES_ORDER)) == null) {
			connections = new HashMap<SDFEdge, Integer>();
			this.getPropertyBean().setValue(EDGES_ORDER, connections);
		}
		return connections;
	}

	public void copyProperties(PropertySource props) {
		super.copyProperties(props);
		Map<SDFEdge, Integer> connections = new HashMap<SDFEdge, Integer>();
		this.getPropertyBean().setValue(EDGES_ORDER, connections);
	}

}
