package org.ietr.dftools.algorithm.model.sdf.esdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.PropertySource;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;

/**
 * Class to represent join vertices (implode)
 * 
 * @author jpiat
 * 
 */
public class SDFJoinVertex extends SDFAbstractVertex {

	/**
	 * Kind of node
	 */
	public static final String JOIN = "join";

	/**
	 * String to access the property edges order
	 */
	public static final String EDGES_ORDER = "edges_order";

	/**
	 * Creates a new SDFInterfaceVertex with the default direction (SINK)
	 */
	public SDFJoinVertex() {
		super();
		setKind(JOIN);
		setNbRepeat(1);
	}

	@Override
	public SDFAbstractVertex clone() {
		// Copy the vertex properties
		SDFJoinVertex newVertex = new SDFJoinVertex();
		for (String key : this.getPropertyBean().keys()) {
			if (this.getPropertyBean().getValue(key) != null) {
				Object val = this.getPropertyBean().getValue(key);
				newVertex.getPropertyBean().setValue(key, val);
			}
		}

		// Copy the ports
		for (SDFInterfaceVertex sink : this.getSinks()) {
			if (newVertex.getGraphDescription() != null
					&& newVertex.getGraphDescription()
							.getVertex(sink.getName()) != null) {
				newVertex.addSink((SDFInterfaceVertex) this
						.getGraphDescription().getVertex(sink.getName()));
			} else {
				newVertex.addSink(sink.clone());
			}
		}
		for (SDFInterfaceVertex source : this.getSources()) {
			if (newVertex.getGraphDescription() != null
					&& newVertex.getGraphDescription().getVertex(
							source.getName()) != null) {
				newVertex.addSource((SDFInterfaceVertex) this
						.getGraphDescription().getVertex(source.getName()));
			} else {
				newVertex.addSource(source.clone());
			}
		}

		// Copy the nr of repetitions
		try {
			newVertex.setNbRepeat(this.getNbRepeat());
		} catch (InvalidExpressionException e) {
			e.printStackTrace();
		}

		// Remove the edge order
		newVertex.getPropertyBean().removeProperty(EDGES_ORDER);

		return newVertex;
	}

	private void addConnection(SDFEdge newEdge) {
		getConnections().put(getConnections().size(), newEdge);
	}

	private void removeConnection(SDFEdge newEdge) {
		Integer index = getEdgeIndex(newEdge);
		getConnections().remove(index);

		// update the indexes of remaining connections.
		for (int i = index; i < getConnections().size(); i++) {
			SDFEdge edge = getConnections().remove(i + 1);
			getConnections().put(i, edge);
		}
	}

	/**
	 * Gives the edge connection index
	 * 
	 * @param edge
	 *            The edge to get the connection index
	 * @return The connection index of the edge
	 */
	public Integer getEdgeIndex(SDFEdge edge) {
		for (Integer connIndex : getConnections().keySet()) {
			if (getConnections().get(connIndex).equals(edge)) {
				return connIndex;
			}
		}
		return null;
	}

	/**
	 * Swap two {@link SDFEdge} with given indexes in the ordered connection
	 * map.
	 * 
	 * @param index0
	 * @param index1
	 * @return <code>true</code> if both indices were valid and could be
	 *         swapped, <code>false</code> otherwise.
	 */
	public boolean swapEdges(int index0, int index1) {
		Map<Integer, SDFEdge> connections = getConnections();
		if (connections.containsKey(index0) && connections.containsKey(index1)) {
			SDFEdge buffer = connections.get(index0);
			connections.replace(index0, connections.get(index1));
			connections.replace(index1, buffer);
			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	protected Map<Integer, SDFEdge> getConnections() {
		Map<Integer, SDFEdge> connections;
		if ((connections = (Map<Integer, SDFEdge>) this.getPropertyBean()
				.getValue(EDGES_ORDER)) == null) {
			connections = new HashMap<Integer, SDFEdge>();
			this.getPropertyBean().setValue(EDGES_ORDER, connections);
		}
		return connections;
	}

	/**
	 * @return The incoming connection of this for in an ordered list
	 */
	public List<SDFEdge> getIncomingConnections() {
		List<SDFEdge> edges = new ArrayList<SDFEdge>(getConnections().size());
		for (int i = 0; i < getConnections().size(); i++) {
			if (getConnections().get(i) != null) {
				edges.add(getConnections().get(i));
			}
		}
		return edges;
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

	@Override
	public void copyProperties(PropertySource props) {
		super.copyProperties(props);
		Map<Integer, SDFEdge> connections = new HashMap<Integer, SDFEdge>();
		this.getPropertyBean().setValue(EDGES_ORDER, connections);
	}

}
