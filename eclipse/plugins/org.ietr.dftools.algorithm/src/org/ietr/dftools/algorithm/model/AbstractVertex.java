package org.ietr.dftools.algorithm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.ietr.dftools.algorithm.model.parameters.Argument;
import org.ietr.dftools.algorithm.model.parameters.ArgumentSet;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;

/**
 * Abstract class for all vertex types
 * 
 * @author jpiat
 * @param <G>
 * 
 */
@SuppressWarnings("unchecked")
public abstract class AbstractVertex<G> extends Observable implements
		PropertySource, Observer, CloneableProperty {

	protected PropertyBean properties;

	/**
	 * Property name for property base
	 */
	public static final String BASE = "base";

	/**
	 * Property name for property graph_desc
	 */
	public static final String REFINEMENT = "graph_desc";

	/**
	 * Property name for property arguments
	 */
	public static final String ARGUMENTS = "arguments";
	/**
	 * Property name for property id
	 */
	public static final String ID = "id";
	/**
	 * Property name for property name
	 */
	public static final String NAME = "name";
	/**
	 * Property name for property name
	 */
	public static final String INFO = "info";
	/**
	 * Property kind for property name
	 */
	public static final String KIND = "kind";

	@SuppressWarnings("serial")
	protected static List<String> public_properties = new ArrayList<String>() {
		{
			add(ARGUMENTS);
			add(REFINEMENT);
			add(NAME);
			add(KIND);
		}
	};

	protected List<IInterface> interfaces;

	/**
	 * Creates a new Instance of Abstract vertex
	 * 
	 */
	public AbstractVertex() {
		properties = new PropertyBean();
		interfaces = new ArrayList<IInterface>();
	}

	public List<String> getPublicProperties() {
		return public_properties;
	}

	/**
	 * @param visitor
	 *            The visitor to accept
	 * @throws SDF4JException
	 */
	@SuppressWarnings("rawtypes")
	public void accept(IGraphVisitor visitor) throws SDF4JException {
		visitor.visit(this);
	}

	/**
	 * Add a list of interface to this vertex
	 * 
	 * @param interfaces
	 *            The list of interface to add
	 */
	public void addInterfaces(List<IInterface> interfaces) {
		interfaces.addAll(interfaces);
	}

	/**
	 * Add a list of interface to this vertex
	 * 
	 * @param interfaces
	 *            The list of interface to add
	 */
	public boolean addInterface(IInterface port) {
		interfaces.add(port);
		return true;
	}

	public List<IInterface> getInterfaces() {
		return interfaces;
	}

	/**
	 * Give this vertex parent graph
	 * 
	 * @return The parent graph of this vertex
	 */
	@SuppressWarnings("rawtypes")
	public AbstractGraph getBase() {
		if (properties.getValue(BASE) != null) {
			return (AbstractGraph) properties.getValue(BASE);
		}
		return null;
	}

	/**
	 * Give the SDGGrpah describing this vertex behavior
	 * 
	 * @return The graph implementing the vertex's behavior
	 */
	@SuppressWarnings("rawtypes")
	public AbstractGraph getGraphDescription() {
		return (AbstractGraph) properties.getValue(REFINEMENT,
				AbstractGraph.class);
	}

	/**
	 * Gives the vertex id
	 * 
	 * @return The id of the vertex
	 */
	public String getId() {
		return (String) properties.getValue(ID, String.class);
	}

	/**
	 * Gives this graph name
	 * 
	 * @return The name of this graph
	 */
	public String getName() {
		return (String) properties.getValue(NAME);
	}

	/**
	 * Gives this graph info property
	 * 
	 * @return The info property of this graph
	 */
	public String getInfo() {
		return (String) properties.getValue(INFO);
	}

	/**
	 * Gives this graph PropertyBean
	 * 
	 * @return This Graph PropertyBean
	 */
	public PropertyBean getPropertyBean() {
		return properties;
	}

	/**
	 * Gives this vertex's refinement
	 * 
	 * @return The vertex's refinement
	 */
	public IRefinement getRefinement() {
		return (IRefinement) properties.getValue(REFINEMENT, IRefinement.class);
	}

	/**
	 * Set this graph's base (parent) graph
	 * 
	 * @param base
	 */
	protected void setBase(G base) {
		properties.setValue(BASE, base);
	}

	/**
	 * Set this vertex's graph description
	 * 
	 * @param desc
	 */
	@SuppressWarnings("rawtypes")
	public void setGraphDescription(AbstractGraph desc) {
		properties.setValue(REFINEMENT, properties.getValue(REFINEMENT), desc);
		desc.setParentVertex(this);
	}

	/**
	 * Sets the id of the vertex
	 * 
	 * @param id
	 *            The id to set for this vertex
	 */
	public void setId(String id) {
		properties.setValue(ID, properties.getValue(ID), id);
	}

	/**
	 * Set this graph name
	 * 
	 * @param name
	 *            The name to set for this graph
	 */
	public void setName(String name) {
		properties.setValue(NAME, properties.getValue(NAME), name);
	}

	/**
	 * Set this graph info property
	 * 
	 * @param info
	 *            The info property to set for this graph
	 */
	public void setInfo(String info) {
		properties.setValue(INFO, properties.getValue(INFO), info);
	}

	/**
	 * Set this vertex's refinement description
	 * 
	 * @param desc
	 */
	public void setRefinement(IRefinement desc) {
		properties.setValue(REFINEMENT, properties.getValue(REFINEMENT), desc);
		this.setChanged();
		this.notifyObservers();
	}

	@SuppressWarnings("rawtypes")
	public void update(Observable o, Object arg) {
		if (arg != null) {
			if (arg instanceof String && o instanceof AbstractEdge) {
				Object property = ((AbstractVertex) o).getPropertyBean()
						.getValue((String) arg);
				if (property != null) {
					this.getPropertyBean().setValue((String) arg, property);
				}
			}
		}
	}

	public void copyProperties(PropertySource props) {
		List<String> keys = new ArrayList<String>(props.getPropertyBean()
				.keys());
		for (String key : keys) {
			if (!key.equals(AbstractVertex.BASE)) {
				if (props.getPropertyBean().getValue(key) instanceof CloneableProperty) {
					this.getPropertyBean().setValue(
							key,
							((CloneableProperty) props.getPropertyBean()
									.getValue(key)).clone());
				} else {
					this.getPropertyBean().setValue(key,
							props.getPropertyBean().getValue(key));
				}
			}
		}
	}

	/**
	 * Gives the argument of this graph with the given name
	 * 
	 * @param name
	 *            The name of the argument to get
	 * @return The argument with the given name
	 */
	public Argument getArgument(String name) {
		if (properties.getValue(ARGUMENTS) != null) {
			Argument arg = ((ArgumentSet) properties.getValue(ARGUMENTS))
					.getArgument(name);
			if (arg != null) {
				arg.setExpressionSolver(this.getBase());
			}
			return arg;
		}
		return null;
	}

	/**
	 * Gives the argument set of this graph
	 * 
	 * @return The set of argument of this graph
	 */
	public ArgumentSet getArguments() {
		if (properties.getValue(ARGUMENTS) != null) {
			((ArgumentSet) properties.getValue(ARGUMENTS))
					.setExpressionSolver(this.getBase());
			return ((ArgumentSet) properties.getValue(ARGUMENTS));
		}
		return null;
	}

	/**
	 * Set the arguments set for this vertex
	 * 
	 * @param arguments
	 *            The set of arguments for this graph
	 */
	public void setArgumentSet(ArgumentSet arguments) {
		properties.setValue(ARGUMENTS, properties.getValue(ARGUMENTS),
				arguments);
		arguments.setExpressionSolver(this.getBase());
	}

	@SuppressWarnings("rawtypes")
	public abstract AbstractVertex clone();

	/**
	 * Add the given argument to his graph argument set
	 * 
	 * @param arg
	 *            The argument to add
	 */
	public void addArgument(Argument arg) {
		if (properties.getValue(ARGUMENTS) == null) {
			setArgumentSet(new ArgumentSet());
		}
		((ArgumentSet) properties.getValue(ARGUMENTS)).addArgument(arg);
		arg.setExpressionSolver(this.getBase());
	}

	/**
	 * Sets this vertex kind
	 * 
	 * @param kind
	 *            The kind of the vertex (port, vertex)
	 */
	public void setKind(String kind) {
		properties.setValue(KIND, properties.getValue(KIND), kind);
	}

	/**
	 * gets this vertex kind
	 * 
	 * @return The string representation of the kind of this vertex
	 * 
	 */
	public String getKind() {
		return (String) properties.getValue(KIND, String.class);
	}

	/**
	 * Notify the vertex that it has been connected using the given edge
	 * 
	 * @param e
	 */
	public abstract void connectionAdded(AbstractEdge<?, ?> e);

	/**
	 * Notify the vertex that it has been disconnected froms the given edge
	 * 
	 * @param e
	 */
	public abstract void connectionRemoved(AbstractEdge<?, ?> e);

	@SuppressWarnings("rawtypes")
	public boolean equals(Object e) {
		if (e instanceof AbstractVertex) {
			return ((AbstractVertex) e).getName().equals(this.getName());
		} else {
			return false;
		}
	}

	/**
	 * Gives the edge this interface is associated to
	 * 
	 * @param port
	 *            The for which to look for edges
	 * @return The found edge, null if not edge match the given port
	 */
	@SuppressWarnings("rawtypes")
	public AbstractEdge getAssociatedEdge(IInterface port) {
		AbstractVertex portVertex = (AbstractVertex) port;
		for (Object edgeObj : getBase().incomingEdgesOf(this)) {
			AbstractEdge edge = (AbstractEdge) edgeObj;
			if ((edge.getTargetLabel() != null && edge.getTargetLabel().equals(
					portVertex.getName()))) {
				return edge;
			}
		}
		for (Object edgeObj : getBase().outgoingEdgesOf(this)) {
			AbstractEdge edge = (AbstractEdge) edgeObj;
			if (edge.getSourceLabel() != null
					&& edge.getSourceLabel().equals(portVertex.getName())) {
				return edge;
			}
		}
		return null;
	}

	public String getPropertyStringValue(String propertyName) {
		if (this.getPropertyBean().getValue(propertyName) != null) {
			return this.getPropertyBean().getValue(propertyName).toString();
		}
		return null;
	}

	public void setPropertyValue(String propertyName, Object value) {
		this.getPropertyBean().setValue(propertyName, value);
	}


}
