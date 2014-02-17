package org.ietr.dftools.algorithm.model.dag;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.AbstractVertexPropertyType;
import org.ietr.dftools.algorithm.model.PropertyBean;
import org.ietr.dftools.algorithm.model.PropertyFactory;
import org.ietr.dftools.algorithm.model.PropertySource;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;

/**
 * Class used to represent a Vertex in a DIrected Acyclic Graph
 * 
 * @author jpiat
 * 
 */
public class DAGVertex extends AbstractVertex<DirectedAcyclicGraph> implements PropertySource {

	/**
	 * Key to access to property time
	 */
	public static final String TIME = "time";

	/**
	 * Key to access to property nb repeat
	 */
	public static final String NB_REPEAT = "nb_repeat";
	
	/**
	 * The string representing the kind of this vertex
	 */
	public static final String DAG_VERTEX = "dag_vertex" ;

	/**
	 * Key to access to property sdf_vertex
	 */
	public static final String SDF_VERTEX = "sdf_vertex";

	
	static {
		{
			public_properties.add(TIME);
			public_properties.add(NB_REPEAT);
		}
	};

	/**
	 * Creates a new DAGVertex
	 */
	public DAGVertex() {
		super();
		setKind(DAG_VERTEX);
		properties = new PropertyBean();
		this.setId(UUID.randomUUID().toString());
	}

	/**
	 * Creates a new DAGVertex with the name "n", the execution time "t" and the
	 * number of repetition "nb"
	 * 
	 * @param n
	 *            This Vertex name
	 * @param t
	 *            This Vertex execution time
	 * @param nb
	 *            This Vertex number of repetition
	 */
	public DAGVertex(String n, AbstractVertexPropertyType<?> t,
			AbstractVertexPropertyType<?> nb) {
		super();
		setKind(DAG_VERTEX);
		properties = new PropertyBean();
		this.setId(UUID.randomUUID().toString());
		setNbRepeat(nb);
		setTime(t);
		setName(n);
	}


	/**
	 * Gives the vertex corresponding to this dag vertex
	 * 
	 * @return The SDFVertex corresponding to this DAG vertex from the SDF2Dag
	 *         translation
	 */
	public SDFAbstractVertex getCorrespondingSDFVertex() {
		Object vertex = getPropertyBean().getValue(SDF_VERTEX,
				SDFAbstractVertex.class);
		if (vertex != null) {
			return (SDFAbstractVertex) vertex;
		}
		return null;
	}
	

	/**
	 * Gives this vertex number of repetition
	 * 
	 * @return This vertex number of repetition
	 */
	public AbstractVertexPropertyType<?> getNbRepeat() {
		if (properties.getValue(NB_REPEAT) != null) {
			return (AbstractVertexPropertyType<?>) properties
					.getValue(NB_REPEAT);
		}
		return null;
	}

	/**
	 * Gives this Vertex Execution time
	 * 
	 * @return This vertex execution time
	 */
	public AbstractVertexPropertyType<?> getTime() {
		if (properties.getValue(TIME) != null) {
			return (AbstractVertexPropertyType<?>) properties.getValue(TIME);
		}
		return null;
	}

	/**
	 * Gives this vertex incoming Edges
	 * 
	 * @return The Set of incoming edges
	 */
	public Set<DAGEdge> incomingEdges() {
		if (properties.getValue(BASE) instanceof DirectedAcyclicGraph) {
			DirectedAcyclicGraph base = (DirectedAcyclicGraph) properties
					.getValue(BASE);
			return base.incomingEdgesOf(this);
		}
		return new TreeSet<DAGEdge>();
	}

	/**
	 * Gives this vertex outgoing Edges
	 * 
	 * @return The Set of outgoing edges
	 */
	public Set<DAGEdge> outgoingEdges() {
		if (properties.getValue(BASE) instanceof DirectedAcyclicGraph) {
			DirectedAcyclicGraph base = (DirectedAcyclicGraph) properties
					.getValue(BASE);
			return base.outgoingEdgesOf(this);
		}
		return new TreeSet<DAGEdge>();
	}

	/**
	 * Set the sdf vertex corresponding to this dag vertex
	 * 
	 * @param vertex
	 */
	public void setCorrespondingSDFVertex(SDFAbstractVertex vertex) {
		getPropertyBean().setValue(SDF_VERTEX, vertex);
	}

	/**
	 * Set this vertex number of repetition
	 * 
	 * @param nb
	 *            The repetition number of this vertex
	 */
	public void setNbRepeat(AbstractVertexPropertyType<?> nb) {
		properties.setValue(NB_REPEAT, nb);
	}

	/**
	 * Set this vertex execution time
	 * 
	 * @param t
	 *            The execution of this vertex
	 */
	public void setTime(AbstractVertexPropertyType<?> t) {
		properties.setValue(TIME, t);
	}

	public String toString() {
		return getName() + " x" + getNbRepeat();
	}

	@Override
	public DAGVertex clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionAdded(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionRemoved(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub
		
	}

}
