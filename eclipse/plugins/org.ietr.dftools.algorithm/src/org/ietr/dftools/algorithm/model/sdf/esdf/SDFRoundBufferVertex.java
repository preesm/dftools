package org.ietr.dftools.algorithm.model.sdf.esdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;

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
			e.printStackTrace();
		}

		// Copy the ports
		for (SDFInterfaceVertex sink : this.getSinks()) {
			if (copy.getGraphDescription() != null
					&& copy.getGraphDescription().getVertex(sink.getName()) != null) {
				copy.addSink((SDFInterfaceVertex) this.getGraphDescription()
						.getVertex(sink.getName()));
			} else {
				copy.addSink(sink.clone());
			}
		}
		for (SDFInterfaceVertex source : this.getSources()) {
			if (copy.getGraphDescription() != null
					&& copy.getGraphDescription().getVertex(source.getName()) != null) {
				copy.addSource((SDFInterfaceVertex) this.getGraphDescription()
						.getVertex(source.getName()));
			} else {
				copy.addSource(source.clone());
			}
		}

		return copy;
	}

}
