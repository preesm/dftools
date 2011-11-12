package net.sf.dftools.algorithm.model.sdf.esdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.PropertySource;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;

/**
 * Class to represent join vertices (implode)
 * @author jpiat
 *
 */
public class SDFJoinVertex extends SDFAbstractVertex{

	/**
	 * Kind of node
	 */
	public static final String JOIN ="join"; 
	
	/**
	 * String to access the property edges order
	 */
	public static final String EDGES_ORDER ="edges_order"; 
	

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
		SDFJoinVertex join = new SDFJoinVertex();
		join.setName(this.getName());
		return join ;
	}
	
	private void addConnection(SDFEdge newEdge){
		getConnections().put(getConnections().size(), newEdge);
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
		for(Integer connIndex : getConnections().keySet()){
			if(getConnections().get(connIndex).equals(edge)){
				return connIndex;
			}
		}
		return null ;
	}
	
	@SuppressWarnings("unchecked" )
	protected Map<Integer, SDFEdge> getConnections(){
		Map<Integer, SDFEdge> connections ;
		if((connections = (Map<Integer, SDFEdge> )this.getPropertyBean().getValue(EDGES_ORDER)) == null){
			connections = new HashMap<Integer, SDFEdge>();
			this.getPropertyBean().setValue(EDGES_ORDER, connections);
		}
		return connections;
	}
	
	/**
	 * @return The incoming connection of this for in an ordered list
	 */
	public List<SDFEdge> getIncomingConnections(){
		List<SDFEdge> edges = new ArrayList<SDFEdge>(getConnections().size());
		for(int i = 0 ; i < getConnections().size() ; i ++){
			if(getConnections().get(i) != null){
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
	
	public void copyProperties(PropertySource props){
		super.copyProperties(props);
		Map<Integer, SDFEdge> connections = new HashMap<Integer, SDFEdge>();
		this.getPropertyBean().setValue(EDGES_ORDER, connections);
	}

}
