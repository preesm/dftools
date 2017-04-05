/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2013 - 2015)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
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
	
	/**
	 * Remove the given {@link SDFEdge} from its current index and insert it
	 * just before the {@link SDFEdge} currently at the given index (or at the
	 * end of the list if index == connections.size).
	 * 
	 * @param edge
	 *            the {@link SDFEdge} to move
	 * @param index
	 *            the new index for the {@link SDFEdge}
	 * @return <code>true</code> if the edge was found and moved at an existing
	 *         index, <code>false</code> otherwise.
	 */
	public boolean setEdgeIndex(SDFEdge edge, int index) {
		Map<Integer, SDFEdge> connections = getConnections();
		if (index < connections.size() && connections.containsValue(edge)) {
			int oldIndex = getEdgeIndex(edge);
			removeConnection(edge);
			index = (oldIndex < index) ? index - 1 : index;
			// update the indexes of subsequent edges.
			for (int i = connections.size() -1 ; i >= index ; i--) {
				connections.put(i + 1, connections.remove(i));
			}
			// put the edge in it new place
			connections.put(index, edge);
			return true;
		}

		// Special case, put the edge at the end
		if (index == connections.size() && connections.containsValue(edge)) {
			removeConnection(edge);
			addConnection(edge);
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
