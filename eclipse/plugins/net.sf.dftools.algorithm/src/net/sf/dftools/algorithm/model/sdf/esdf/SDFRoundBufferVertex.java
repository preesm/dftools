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
		for (Integer index : getConnections().keySet()) {
			if (getConnections().get(index).equals(edge)) {
				return index;
			}
		}
		return null;
	}

	/**
	 * @return The incoming connection of this for in an ordered list
	 */
	public List<SDFEdge> getIncomingConnections() {
		List<SDFEdge> edges = new ArrayList<SDFEdge>(getConnections().size());
		for (Integer index : getConnections().keySet()) {
			edges.add(index, getConnections().get(index));
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
		Map<Integer, SDFEdge> connections = getConnections();
		SDFEdge connectionToRemove = null;
		connectionToRemove = connections.get(index);
		connections.remove(connectionToRemove);
		connections.put(index, edge);
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
