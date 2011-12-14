package net.sf.dftools.algorithm.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import net.sf.dftools.algorithm.factories.ModelVertexFactory;
import net.sf.dftools.algorithm.model.parameters.IExpressionSolver;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.parameters.NoIntegerValueException;
import net.sf.dftools.algorithm.model.parameters.Parameter;
import net.sf.dftools.algorithm.model.parameters.ParameterSet;
import net.sf.dftools.algorithm.model.parameters.Value;
import net.sf.dftools.algorithm.model.parameters.Variable;
import net.sf.dftools.algorithm.model.parameters.VariableSet;
import net.sf.dftools.algorithm.model.sdf.visitors.GraphVisitor;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DirectedMultigraph;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

/**
 * Abstract class common to all graphs
 * 
 * @author jpiat
 * 
 * @param <V>
 * @param <E>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractGraph<V extends AbstractVertex, E extends AbstractEdge>
		extends DirectedMultigraph<V, E> implements PropertySource,
		IRefinement, IExpressionSolver, IModelObserver, CloneableProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1381565218127310945L;

	/**
	 * Property name for property name
	 */
	public static final String NAME = "name";

	/**
	 * Property name for property parameters
	 */
	public static final String PARAMETERS = "parameters";

	/**
	 * Property name for property variables
	 */
	public static final String VARIABLES = "variables";

	/**
	 * Property name for property variables
	 */
	public static final String MODEL = "kind";

	/**
	 * This graph parent vertex if it exist
	 */
	public static final String PARENT_VERTEX = "parent_vertex";

	@SuppressWarnings("serial")
	protected static List<String> public_properties = new ArrayList<String>() {
		{
			add(NAME);
			add(PARAMETERS);
			add(VARIABLES);
			add(MODEL);
		}
	};

	protected PropertyBean properties;
	protected List<IModelObserver> observers;
	protected boolean hasChanged;

	/**
	 * Creates a new Instance of Abstract graph with the given factory
	 * 
	 * @param factory
	 */
	public AbstractGraph(EdgeFactory<V, E> factory) {
		super(factory);
		properties = new PropertyBean();
		observers = new ArrayList<IModelObserver>();
		hasChanged = false;
	}

	public List<String> getPublicProperties() {
		return public_properties;
	}

	public boolean addVertex(V vertex) {
		int number = 0;
		String name = vertex.getName();
		while (this.getVertex(name) != null) {
			name = vertex.getName() + "_" + number;
			number++;
		}
		vertex.setName(name);
		boolean result = super.addVertex(vertex);
		vertex.setBase(this);
		this.setChanged();
		this.notifyObservers(vertex);
		return result;
	}

	public E addEdge(V source, V target) {
		E edge = super.addEdge(source, target);
		edge.setBase(this);
		this.setChanged();
		this.notifyObservers(edge);
		return edge;
	}

	/**
	 * Edge adding method used by parser
	 * 
	 * @param source
	 *            The source vertex of the edge
	 * @param sourcePort
	 *            The source port of the edge
	 * @param target
	 *            The target vertex of the edge
	 * @param targetPort
	 *            The target port of the edge
	 * @return
	 */
	public E addEdge(V source, IInterface sourcePort, V target,
			IInterface targetPort) {
		E edge = super.addEdge(source, target);
		edge.setSourceLabel(sourcePort.getName());
		edge.setTargetLabel(targetPort.getName());
		edge.setBase(this);
		this.setChanged();
		this.notifyObservers(edge);
		return edge;
	}

	public boolean removeVertex(V vertex) {
		boolean result = super.removeVertex(vertex);
		this.setChanged();
		this.notifyObservers(vertex);
		return result;
	}

	public E removeEdge(V source, V target) {
		E edge = super.removeEdge(source, target);
		this.setChanged();
		this.notifyObservers(edge);
		return edge;
	}

	/**
	 * @param visitor
	 *            The visitor to accept
	 * @throws SDF4JException
	 */
	public void accept(GraphVisitor visitor) throws SDF4JException {
		visitor.visit(this);
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
	 * Gives this graph PropertyBean
	 * 
	 * @return This Graph PropertyBean
	 */
	public PropertyBean getPropertyBean() {
		return properties;
	}

	/**
	 * Gives the vertex with the given name
	 * 
	 * @param name
	 *            The vertex name
	 * @return The vertex with the given name, null, if the vertex does not
	 *         exist
	 */
	public V getVertex(String name) {
		for (V vertex : vertexSet()) {
			if (vertex.getName().equals(name)) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Gives the vertex with the given name
	 * 
	 * @param name
	 *            The vertex name
	 * @return The vertex with the given name, null, if the vertex does not
	 *         exist
	 */
	public V getHierarchicalVertex(String name) {
		for (V vertex : vertexSet()) {
			if (vertex.getName().equals(name)) {
				return vertex;
			} else if (vertex.getGraphDescription() != null) {
				AbstractVertex result = ((AbstractVertex) vertex)
						.getGraphDescription().getHierarchicalVertex(name);
				if (result != null) {
					return (V) result;
				}
			}
		}
		return null;
	}

	/**
	 * Gives the path of a given vertex
	 * 
	 * @param vertex
	 *            The vertex
	 * @return The vertex path in the graph hierarchy
	 */
	public String getHierarchicalPath(V vertex) {
		return getHierarchicalPath(vertex, "");
	}

	/**
	 * Gives the path of a given vertex
	 * 
	 * @param vertex
	 *            The vertex
	 * @param pathAlreadyRead
	 *            The path that we are already in. For example, if we are in the
	 *            MyParentVertex refinement,
	 *            pathAlreadyRead=MyGrandParentVertex/MyParentVertex.
	 * @return The vertex path in the graph hierarchy
	 */
	private String getHierarchicalPath(V vertex, String currentPath) {

		for (Object v : vertexSet()) {
			V castV = (V) v;
			String newPath = currentPath + castV.getName();

			if (castV == vertex)
				return newPath;
			newPath += "/";
			if (vertex.getGraphDescription() != null) {
				newPath = vertex.getGraphDescription().getHierarchicalPath(
						vertex, newPath);
				if (newPath != null)
					return newPath;
			}
		}

		return null;
	}

	/**
	 * Gives the vertex set of current graph merged with the vertex set of all
	 * its subgraphs
	 * 
	 * @param name
	 *            The vertex name
	 * @return The vertex with the given name, null, if the vertex does not
	 *         exist
	 */
	public Set<V> getHierarchicalVertexSet() {

		Set<V> vset = new HashSet<V>(vertexSet());

		for (V vertex : vertexSet()) {
			if (vertex.getGraphDescription() != null) {
				vset.addAll(vertex.getGraphDescription()
						.getHierarchicalVertexSet());
			}
		}
		return vset;
	}

	/**
	 * Gives the vertex with the given path
	 * 
	 * @param path
	 *            The vertex path in format
	 *            MyGrandParentVertex/MyParentVertex/Myself
	 * @return The vertex with the given name, null, if the vertex does not
	 *         exist
	 */
	public V getHierarchicalVertexFromPath(String path) {
		return getHierarchicalVertexFromPath(path, "");
	}

	/**
	 * Gives the vertex with the given path
	 * 
	 * @param path
	 *            The vertex path in format
	 *            MyGrandParentVertex/MyParentVertex/Myself
	 * @param pathAlreadyRead
	 *            The path that we are already in. For example, if we are in the
	 *            MyParentVertex refinement,
	 *            pathAlreadyRead=MyGrandParentVertex/MyParentVertex.
	 * @return The vertex with the given name, null, if the vertex does not
	 *         exist
	 */
	public V getHierarchicalVertexFromPath(String path, String pathAlreadyRead) {

		String vertexToFind = path; // The name of the vertex to find in the
									// current level of hierarchy
		boolean isPathRead = true; // true if we have already read the hierarchy
									// and we are looking for a vertex here

		// Removing the path already read from the path
		if (!pathAlreadyRead.isEmpty()) {
			vertexToFind = vertexToFind.replaceFirst(pathAlreadyRead + "/", "");
			pathAlreadyRead += "/";
		}

		// Selecting the first vertex name to find
		if (vertexToFind.indexOf("/") != -1) {
			vertexToFind = vertexToFind.substring(0, vertexToFind.indexOf("/"));
			isPathRead = false;
		}

		V vertex = getVertex(vertexToFind);
		if (vertex != null) {
			if (isPathRead) {
				return vertex;
			} else if (vertex.getGraphDescription() != null) {
				return (V) ((AbstractVertex) vertex).getGraphDescription()
						.getHierarchicalVertexFromPath(path,
								pathAlreadyRead + vertexToFind);
			}
		}

		return null;
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

	public V getParentVertex() {
		return ((V) properties.getValue(PARENT_VERTEX));
	}

	protected void setParentVertex(V parentVertex) {
		properties.setValue(PARENT_VERTEX, parentVertex);
	}

	/**
	 * Add the given parameter to his graph parameter set
	 * 
	 * @param param
	 *            The parameter to add
	 */
	public void addParameter(Parameter param) {
		if (properties.getValue(PARAMETERS) == null) {
			setParameterSet(new ParameterSet());
		}
		((ParameterSet) properties.getValue(PARAMETERS)).addParameter(param);
	}

	/**
	 * Add the given variable to his graph parameter set
	 * 
	 * @param var
	 *            The variable to add
	 */
	public void addVariable(Variable var) {
		if (properties.getValue(VARIABLES) == null) {
			setVariableSet(new VariableSet());
		}
		((VariableSet) properties.getValue(VARIABLES)).addVariable(var);
		var.setExpressionSolver(this);
	}

	/**
	 * Gives the parameter of this graph with the given name
	 * 
	 * @param name
	 *            The name of the parameter to get
	 * @return The parameter with the given name
	 */
	public Parameter getParameter(String name) {
		if (properties.getValue(PARAMETERS) != null) {
			return ((ParameterSet) properties.getValue(PARAMETERS))
					.getParameter(name);
		}
		return null;
	}

	/**
	 * Gives the parameter set of this graph
	 * 
	 * @return The set of parameter of this graph
	 */
	public ParameterSet getParameters() {
		if (properties.getValue(PARAMETERS) != null) {
			return ((ParameterSet) properties.getValue(PARAMETERS));
		}
		return null;
	}

	/**
	 * Gives the variable of this graph with the given name
	 * 
	 * @param name
	 *            The name of the variable to get
	 * @return The variable with the given name
	 */
	public Variable getVariable(String name) {
		if (properties.getValue(VARIABLES) != null) {
			return ((VariableSet) properties.getValue(VARIABLES))
					.getVariable(name);
		}
		return null;
	}

	/**
	 * Gives the variables set of this graph
	 * 
	 * @return The set of variables of this graph
	 */
	public VariableSet getVariables() {
		if (properties.getValue(VARIABLES) != null) {
			return ((VariableSet) properties.getValue(VARIABLES));
		}
		return null;
	}

	/**
	 * Set the parameter set for this graph
	 * 
	 * @param parameters
	 *            The set of parameters for this graph
	 */
	public void setParameterSet(ParameterSet parameters) {
		properties.setValue(PARAMETERS, properties.getValue(PARAMETERS),
				parameters);
	}

	/**
	 * Set the variables set for this graph
	 * 
	 * @param variables
	 *            The set of variables for this graph
	 */
	public void setVariableSet(VariableSet variables) {
		properties.setValue(VARIABLES, properties.getValue(VARIABLES),
				variables);
	}

	@Override
	public int solveExpression(String expression, Value caller)
			throws InvalidExpressionException, NoIntegerValueException {
		try {
			JEP jep = new JEP();
			if (this.getVariables() != null /* && !(caller instanceof Argument) */) {
				for (String var : this.getVariables().keySet()) {
					if (this.getVariable(var) == caller
							|| this.getVariable(var).getValue()
									.equals(expression)) {
						break;
					} else {
						jep.addVariable(var, this.getVariable(var).intValue());
					}
				}
			}
			if (this.getParameters() != null && this.getParentVertex() != null) {
				for (String arg : this.getParameters().keySet()) {
					try {
						Integer paramValue = this.getParameters().get(arg)
								.getValue();
						if (paramValue == null) {
							paramValue = this.getParentVertex()
									.getArgument(arg).intValue();
							this.getParameters().get(arg).setValue(paramValue);
						}
						jep.addVariable(arg, paramValue);
					} catch (NoIntegerValueException e) {
						e.printStackTrace();
					}
				}
			}
			Node expressionMainNode = jep.parse(expression);
			Object result = jep.evaluate(expressionMainNode);
			if (result instanceof Double) {
				// System.out.println(expression+"="+result);
				return ((Double) result).intValue();
			} else if (result instanceof Integer) {
				// System.out.println(expression+"="+result);
				return ((Integer) result).intValue();
			} else {
				throw (new InvalidExpressionException(
						"Not a numerical expression"));
			}
		} catch (ParseException e) {
			throw (new InvalidExpressionException("Could not parse expresion :"
					+ expression));
		} catch (Exception e) {
			throw (new InvalidExpressionException("Could not parse expresion :"
					+ expression));
		}
	}

	/**
	 * Add an observer to this graph model
	 * 
	 * @param o
	 *            Te observer to be added
	 */
	public void addObserver(IModelObserver o) {
		observers.add(o);
	}

	/**
	 * Marks this Observable object as having been changed the hasChanged method
	 * will now return true.
	 */
	public void setChanged() {
		hasChanged = true;
	}

	/**
	 * Indicates that this object has no longer changed, or that it has already
	 * notified all of its observers of its most recent change, so that the
	 * hasChanged method will now return false.
	 */
	public void clearChanged() {
		hasChanged = true;
	}

	/**
	 * Clear the list of observers
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Delete the given observer from the observers list
	 * 
	 * @param o
	 */
	public void deleteObserver(IModelObserver o) {
		observers.remove(o);
	}

	/**
	 * Tests if this object has changed.
	 * 
	 * @return True if the object has changed, false otherwise
	 */
	public boolean hasChanged() {
		return hasChanged;
	}

	/**
	 * If this object has changed, as indicated by the hasChanged method, then
	 * notify all of its observers and then call the clearChanged method to
	 * indicate that this object has no longer changed.
	 */
	public void notifyObservers() {
		if (hasChanged) {
			for (IModelObserver o : observers) {
				o.update(this, null);
			}
			clearChanged();
		}
	}

	/**
	 * If this object has changed, as indicated by the hasChanged method, then
	 * notify all of its observers and then call the clearChanged method to
	 * indicate that this object has no longer changed.
	 * 
	 * @param arg
	 *            Arguments to be passe to the update method
	 */
	public void notifyObservers(Object arg) {
		if (hasChanged) {
			for (IModelObserver o : observers) {
				o.update(this, arg);
			}
			clearChanged();
		}
	}

	public abstract AbstractGraph<V, E> clone();

	public abstract ModelVertexFactory<V> getVertexFactory();

	public abstract boolean validateModel(Logger logger) throws SDF4JException;

	public void copyProperties(PropertySource props) {
		for (String key : props.getPropertyBean().keys()) {
			if (props.getPropertyBean().getValue(key) instanceof CloneableProperty) {
				this.getPropertyBean().setValue(
						key,
						((CloneableProperty) props.getPropertyBean().getValue(
								key)).clone());
			} else {
				this.getPropertyBean().setValue(key,
						props.getPropertyBean().getValue(key));
			}
		}
	}

	public String getPropertyStringValue(String propertyName) {
		return this.getPropertyBean().getValue(propertyName).toString();
	}

	public void setPropertyValue(String propertyName, Object value) {
		this.getPropertyBean().setValue(propertyName, value);
	}
}
