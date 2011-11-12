package net.sf.dftools.algorithm.model.sdf.esdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.PropertySource;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;

/**
 * ROund buffer vertex 
 * @author jpiat
 *
 */
public class SDFRoundBufferVertex extends SDFBroadcastVertex{	

	/**
	 * String to access the property edges order
	 */
	public static final String EDGES_ORDER ="edges_order"; 
	
	/**
	 * Kind of node
	 */
	public static final String ROUND_BUFFER ="RoundBuffer";
	
	/**
	 * Creates a new SDFInterfaceVertex with the default direction (SINK)
	 */
	public SDFRoundBufferVertex() {
		super();
		setNbRepeat(1);
	}
	
	private void addConnection(SDFEdge newEdge){
		getConnections().put(newEdge, getConnections().size());
	}
	
	private void removeConnection(SDFEdge newEdge){
		getConnections().remove(newEdge);
	}
	
	/**
	 * Gives the edge connection index
	 * @param edge The edge to get the connection index
	 * @return  The connection index of the edge
	 */
	public Integer getEdgeIndex(SDFEdge edge){
		return getConnections().get(edge);
	}
	
	@SuppressWarnings("unchecked" )
	private Map<SDFEdge, Integer> getConnections(){
		Map<SDFEdge, Integer> connections ;
		if((connections = (Map<SDFEdge, Integer> )this.getPropertyBean().getValue(EDGES_ORDER)) == null){
			connections = new HashMap<SDFEdge, Integer>();
			this.getPropertyBean().setValue(EDGES_ORDER, connections);
		}
		return connections;
	}
	
	/**
	 * @return The incoming connection of this for in an ordered list
	 */
	public List<SDFEdge> getIncomingConnections(){
		List<SDFEdge> edges = new ArrayList<SDFEdge>(getConnections().size());
		for(SDFEdge edge : getConnections().keySet()){
			edges.add(getConnections().get(edge),edge);
		}
		return edges;
	}
	
	/** Sets this edge connection index 
	 * @param edge The edge connected to the vertex
	 * @param index The index in the connections
	 */
	public void setConnectionIndex(SDFEdge edge, int index){
		Map<SDFEdge, Integer> connections  = getConnections();
		SDFEdge connectionToRemove = null;
		for(SDFEdge existingConnections : connections.keySet()){
			if(connections.get(existingConnections) == index){
				connectionToRemove = existingConnections ;
			}
		}
		connections.remove(connectionToRemove);
		connections.put(edge, index);
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
	public SDFAbstractVertex clone() {
		SDFRoundBufferVertex copy = new SDFRoundBufferVertex();
		copy.setName(this.getName());
		try {
			copy.setNbRepeat(this.getNbRepeat());
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copy ;
	}
	
	public void copyProperties(PropertySource props){
		super.copyProperties(props);
		Map<SDFEdge, Integer> connections = new HashMap<SDFEdge, Integer>();
		this.getPropertyBean().setValue(EDGES_ORDER, connections);
	}
	
}
