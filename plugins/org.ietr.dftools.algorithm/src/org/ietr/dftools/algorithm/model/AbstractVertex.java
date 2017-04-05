/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Hervé Yviquel <hyviquel@gmail.com> (2012)
 * Jonathan Piat <jpiat@laas.fr> (2011 - 2012)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2014)
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
public abstract class AbstractVertex<G> extends Observable implements PropertySource, Observer, CloneableProperty {

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
	public static final String	ARGUMENTS	= "arguments";
	/**
	 * Property name for property id
	 */
	public static final String	ID			= "id";
	/**
	 * Property name for property name
	 */
	public static final String	NAME		= "name";
	/**
	 * Property name for property name
	 */
	public static final String	INFO		= "info";
	/**
	 * Property kind for property name
	 */
	public static final String	KIND		= "kind";

	protected static List<String> public_properties = new ArrayList<String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1733980727793726816L;

		{
			add(AbstractVertex.ARGUMENTS);
			add(AbstractVertex.REFINEMENT);
			add(AbstractVertex.NAME);
			add(AbstractVertex.KIND);
		}
	};

	protected List<IInterface> interfaces;

	/**
	 * Creates a new Instance of Abstract vertex
	 *
	 */
	public AbstractVertex() {
		this.properties = new PropertyBean();
		this.interfaces = new ArrayList<>();
	}

	@Override
	public List<String> getPublicProperties() {
		return AbstractVertex.public_properties;
	}

	/**
	 * @param visitor
	 *            The visitor to accept
	 * @throws SDF4JException
	 */
	@SuppressWarnings("rawtypes")
	public void accept(final IGraphVisitor visitor) throws SDF4JException {
		visitor.visit(this);
	}

	/**
	 * Add a list of interface to this vertex
	 *
	 * @param interfaces
	 *            The list of interface to add
	 */
	public void addInterfaces(final List<IInterface> interfaces) {
		interfaces.addAll(interfaces);
	}

	/**
	 * Add a list of interface to this vertex
	 *
	 * @param interfaces
	 *            The list of interface to add
	 */
	public boolean addInterface(final IInterface port) {
		this.interfaces.add(port);
		return true;
	}

	public List<IInterface> getInterfaces() {
		return this.interfaces;
	}

	/**
	 * Give this vertex parent graph
	 *
	 * @return The parent graph of this vertex
	 */
	@SuppressWarnings("rawtypes")
	public AbstractGraph getBase() {
		if (this.properties.getValue(AbstractVertex.BASE) != null) {
			return (AbstractGraph) this.properties.getValue(AbstractVertex.BASE);
		}
		return null;
	}

	/**
	 * Gives the vertex id
	 *
	 * @return The id of the vertex
	 */
	public String getId() {
		return (String) this.properties.getValue(AbstractVertex.ID, String.class);
	}

	/**
	 * Gives this graph name
	 *
	 * @return The name of this graph
	 */
	public String getName() {
		return (String) this.properties.getValue(AbstractVertex.NAME);
	}

	/**
	 * Gives this graph info property
	 *
	 * @return The info property of this graph
	 */
	public String getInfo() {
		return (String) this.properties.getValue(AbstractVertex.INFO);
	}

	/**
	 * Gives this graph PropertyBean
	 *
	 * @return This Graph PropertyBean
	 */
	@Override
	public PropertyBean getPropertyBean() {
		return this.properties;
	}

	// Refinement of the vertex (can be an AbstractGraph describing the behavior
	// of the vertex, or a header file giving signatures of functions to call
	// when executing the vertex).
	private IRefinement refinement;

	public IRefinement getRefinement() {
		return this.refinement;
	}

	public void setRefinement(final IRefinement desc) {
		this.properties.setValue(AbstractVertex.REFINEMENT, this.properties.getValue(AbstractVertex.REFINEMENT), desc);
		this.refinement = desc;
		setChanged();
		this.notifyObservers();
	}

	@SuppressWarnings("rawtypes")
	public void setGraphDescription(final AbstractGraph desc) {
		this.properties.setValue(AbstractVertex.REFINEMENT, this.properties.getValue(AbstractVertex.REFINEMENT), desc);
		this.refinement = desc;
		desc.setParentVertex(this);
	}

	@SuppressWarnings("rawtypes")
	public AbstractGraph getGraphDescription() {
		if (this.refinement instanceof AbstractGraph) {
			return (AbstractGraph) this.refinement;
		} else {
			return null;
		}
	}

	/**
	 * Set this graph's base (parent) graph
	 *
	 * @param base
	 */
	protected void setBase(final G base) {
		this.properties.setValue(AbstractVertex.BASE, base);
	}

	/**
	 * Sets the id of the vertex
	 *
	 * @param id
	 *            The id to set for this vertex
	 */
	public void setId(final String id) {
		this.properties.setValue(AbstractVertex.ID, this.properties.getValue(AbstractVertex.ID), id);
	}

	/**
	 * Set this graph name
	 *
	 * @param name
	 *            The name to set for this graph
	 */
	public void setName(final String name) {
		this.properties.setValue(AbstractVertex.NAME, this.properties.getValue(AbstractVertex.NAME), name);
	}

	/**
	 * Set this graph info property
	 *
	 * @param info
	 *            The info property to set for this graph
	 */
	public void setInfo(final String info) {
		this.properties.setValue(AbstractVertex.INFO, this.properties.getValue(AbstractVertex.INFO), info);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void update(final Observable o, final Object arg) {
		if (arg != null) {
			if ((arg instanceof String) && (o instanceof AbstractEdge)) {
				final Object property = ((AbstractVertex) o).getPropertyBean().getValue((String) arg);
				if (property != null) {
					this.getPropertyBean().setValue((String) arg, property);
				}
			}
		}
	}

	@Override
	public void copyProperties(final PropertySource props) {
		final List<String> keys = new ArrayList<>(props.getPropertyBean().keys());
		for (final String key : keys) {
			if (!key.equals(AbstractVertex.BASE)) {
				if (props.getPropertyBean().getValue(key) instanceof CloneableProperty) {
					this.getPropertyBean().setValue(key, ((CloneableProperty) props.getPropertyBean().getValue(key)).clone());
				} else {
					this.getPropertyBean().setValue(key, props.getPropertyBean().getValue(key));
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
	public Argument getArgument(final String name) {
		if (this.properties.getValue(AbstractVertex.ARGUMENTS) != null) {
			final Argument arg = ((ArgumentSet) this.properties.getValue(AbstractVertex.ARGUMENTS)).getArgument(name);
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
		if (this.properties.getValue(AbstractVertex.ARGUMENTS) == null) {
			final ArgumentSet arguments = new ArgumentSet();
			this.setArgumentSet(arguments);
		}
		((ArgumentSet) this.properties.getValue(AbstractVertex.ARGUMENTS)).setExpressionSolver(this.getBase());
		return ((ArgumentSet) this.properties.getValue(AbstractVertex.ARGUMENTS));
	}

	/**
	 * Set the arguments set for this vertex
	 *
	 * @param arguments
	 *            The set of arguments for this graph
	 */
	public void setArgumentSet(final ArgumentSet arguments) {
		this.properties.setValue(AbstractVertex.ARGUMENTS, this.properties.getValue(AbstractVertex.ARGUMENTS), arguments);
		arguments.setExpressionSolver(this.getBase());
	}

	@Override
	@SuppressWarnings("rawtypes")
	public abstract AbstractVertex clone();

	/**
	 * Add the given argument to his graph argument set
	 *
	 * @param arg
	 *            The argument to add
	 */
	public void addArgument(final Argument arg) {
		if (this.properties.getValue(AbstractVertex.ARGUMENTS) == null) {
			setArgumentSet(new ArgumentSet());
		}
		((ArgumentSet) this.properties.getValue(AbstractVertex.ARGUMENTS)).addArgument(arg);
		arg.setExpressionSolver(this.getBase());
	}

	/**
	 * Sets this vertex kind
	 *
	 * @param kind
	 *            The kind of the vertex (port, vertex)
	 */
	public void setKind(final String kind) {
		this.properties.setValue(AbstractVertex.KIND, this.properties.getValue(AbstractVertex.KIND), kind);
	}

	/**
	 * gets this vertex kind
	 *
	 * @return The string representation of the kind of this vertex
	 *
	 */
	public String getKind() {
		return (String) this.properties.getValue(AbstractVertex.KIND, String.class);
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

	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(final Object e) {
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
	public AbstractEdge getAssociatedEdge(final IInterface port) {
		final AbstractVertex portVertex = (AbstractVertex) port;
		for (final Object edgeObj : getBase().incomingEdgesOf(this)) {
			final AbstractEdge edge = (AbstractEdge) edgeObj;
			if (((edge.getTargetLabel() != null) && edge.getTargetLabel().equals(portVertex.getName()))) {
				return edge;
			}
		}
		for (final Object edgeObj : getBase().outgoingEdgesOf(this)) {
			final AbstractEdge edge = (AbstractEdge) edgeObj;
			if ((edge.getSourceLabel() != null) && edge.getSourceLabel().equals(portVertex.getName())) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public String getPropertyStringValue(final String propertyName) {
		if (this.getPropertyBean().getValue(propertyName) != null) {
			return this.getPropertyBean().getValue(propertyName).toString();
		}
		return null;
	}

	@Override
	public void setPropertyValue(final String propertyName, final Object value) {
		this.getPropertyBean().setValue(propertyName, value);
	}

}
