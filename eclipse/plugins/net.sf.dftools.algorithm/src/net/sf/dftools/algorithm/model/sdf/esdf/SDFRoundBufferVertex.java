package net.sf.dftools.algorithm.model.sdf.esdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;

/**
 * ROund buffer vertex
 * 
 * @author jpiat
 * 
 */
public class SDFRoundBufferVertex extends SDFBroadcastVertex {

	/**
	 * String to access the property edges order
	 */
	public static final String EDGES_ORDER = "edges_order";

	/**
	 * Kind of node
	 */
	public static final String ROUND_BUFFER = "RoundBuffer";

	/**
	 * Creates a new SDFInterfaceVertex with the default direction (SINK)
	 */
	public SDFRoundBufferVertex() {
		super();
		setNbRepeat(1);
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

	/**
	 * @return The incoming connection of this for in an ordered list
	 */
	public List<SDFEdge> getIncomingConnections() {
		List<SDFEdge> edges = new ArrayList<SDFEdge>(getConnections().size());
		for (SDFEdge edge : getConnections().keySet()) {
			edges.add(getConnections().get(edge), edge);
		}
		return edges;
	}

	/**
	 * Sets this edge connection index
	 * 
	 * @param edge
	 *            The edge connected to the vertex
	 * @param index
	 *            The index in the connections
	 */
	public void setConnectionIndex(SDFEdge edge, int index) {
		Map<SDFEdge, Integer> connections = getConnections();
		SDFEdge connectionToRemove = null;
		for (SDFEdge existingConnections : connections.keySet()) {
			if (connections.get(existingConnections) == index) {
				connectionToRemove = existingConnections;
			}
		}
		connections.remove(connectionToRemove);
		connections.put(edge, index);
	}

	@Override
	public SDFAbstractVertex clone() {
		SDFRoundBufferVertex copy = new SDFRoundBufferVertex();
		copy.setName(this.getName());
		try {
			copy.setNbRepeat(this.getNbRepeat());
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copy;
	}

}
