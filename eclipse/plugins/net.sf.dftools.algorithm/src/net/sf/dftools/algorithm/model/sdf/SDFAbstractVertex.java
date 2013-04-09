package net.sf.dftools.algorithm.model.sdf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import jscl.math.JSCLInteger;
import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.IInterface;
import net.sf.dftools.algorithm.model.InterfaceDirection;
import net.sf.dftools.algorithm.model.PropertyBean;
import net.sf.dftools.algorithm.model.PropertyFactory;
import net.sf.dftools.algorithm.model.PropertySource;
import net.sf.dftools.algorithm.model.parameters.Argument;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.parameters.NoIntegerValueException;
import net.sf.dftools.algorithm.model.psdf.parameters.PSDFDynamicArgument;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

/**
 * Abstract class representing SDF Vertices
 * 
 * @author jpiat
 * 
 */
public abstract class SDFAbstractVertex extends AbstractVertex<SDFGraph>
		implements PropertySource {

	/**
	 * Property nb repeat of the node
	 */
	public static final String NB_REPEAT = "nbRepeat";

	static {
		{
			public_properties.add(NB_REPEAT);
		}
	};

	protected List<SDFInterfaceVertex> sinks;

	protected List<SDFInterfaceVertex> sources;

	/**
	 * Constructs a new SDFAbstractVertex using the given Edge Factory ef
	 * 
	 */
	public SDFAbstractVertex() {
		super();
		sinks = new ArrayList<SDFInterfaceVertex>();
		sources = new ArrayList<SDFInterfaceVertex>();
		this.setId(UUID.randomUUID().toString());

		// TODO Auto-generated constructor stub
	}

	/**
	 * Add a list of interface to this vertex
	 * 
	 * @param interfaces
	 *            The list of interface to add
	 */
	public void addInterfaces(List<IInterface> interfaces) {
		super.addInterfaces(interfaces);
		for (IInterface vertex : interfaces) {
			if (vertex instanceof SDFInterfaceVertex
					&& vertex.getDirection() == InterfaceDirection.Input) {
				sources.add((SDFInterfaceVertex) vertex);
			} else if (vertex instanceof SDFInterfaceVertex
					&& vertex.getDirection() == InterfaceDirection.Output) {
				sinks.add((SDFInterfaceVertex) vertex);
			}
		}
	}

	public boolean addInterface(IInterface port) {
		if (port.getDirection().equals(InterfaceDirection.Input)) {
			return addSource((SDFInterfaceVertex) port);
		} else if (port.getDirection().equals(InterfaceDirection.Output)) {
			return addSink((SDFInterfaceVertex) port);
		}
		return false ;
	}

	/**
	 * Add a Sink interface vertex linked to the given edge in the base graph
	 * 
	 * @param sink
	 */
	@SuppressWarnings("unchecked")
	public boolean addSink(SDFInterfaceVertex sink) {
		if (sinks == null) {
			sinks = new ArrayList<SDFInterfaceVertex>();
		}
		super.addInterface(sink);
		sinks.add(sink);
		if (this.getGraphDescription() != null
				&& this.getGraphDescription().getVertex(sink.getName()) == null) {
			return this.getGraphDescription().addVertex(sink);
		} else {
			return false;
		}
	}

	/**
	 * Add a Source interface vertex linked to the given edge in the base graph
	 * 
	 * @param src
	 */
	@SuppressWarnings("unchecked")
	public boolean addSource(SDFInterfaceVertex src) {
		if (sources == null) {
			sources = new ArrayList<SDFInterfaceVertex>();
		}
		super.addInterface(src);
		sources.add(src);
		if (this.getGraphDescription() != null
				&& this.getGraphDescription().getVertex(src.getName()) == null) {
			return this.getGraphDescription().addVertex(src);
		} else {
			return false;
		}
	}

	/**
	 * Cleans the vertex by removing all its properties
	 */
	public void clean() {
		this.sinks.clear();
		this.sources.clear();
		properties = new PropertyBean();
	}

	public abstract SDFAbstractVertex clone();

	/**
	 * Gives the edge associated with the given interface
	 * 
	 * @param graphInterface
	 *            The interface the edge is connected to
	 * @return The Edge the given interface is connected to
	 */
	public SDFEdge getAssociatedEdge(SDFInterfaceVertex graphInterface) {
		for (SDFEdge edge : ((SDFGraph) getBase()).incomingEdgesOf(this)) {
			if ((edge.getTargetInterface() != null && edge.getTargetInterface()
					.equals(graphInterface))) {
				return edge;
			}
		}
		for (SDFEdge edge : ((SDFGraph) getBase()).outgoingEdgesOf(this)) {
			if (edge.getSourceInterface() != null
					&& edge.getSourceInterface().equals(graphInterface)) {
				return edge;
			}
		}
		return null;
	}

	/**
	 * Gives the interface vertex associated with the given edge
	 * 
	 * @param edge
	 *            The which is connected to the interface
	 * @return The Interface the given edge is connected to
	 */
	public SDFInterfaceVertex getAssociatedInterface(SDFEdge edge) {
		for (SDFInterfaceVertex source : sources) {
			if (source.equals(edge.getTargetInterface())) {
				return source;
			}
		}
		for (SDFInterfaceVertex sink : sinks) {
			if (sink.equals(edge.getSourceInterface())) {
				return sink;
			}
		}
		return null;
	}

	/**
	 * Gives the interface with the given name
	 * 
	 * @param name
	 *            The name of the interface
	 * @return The interface with the given name, null if the interface does not
	 *         exist
	 */
	public SDFInterfaceVertex getInterface(String name) {
		for (SDFInterfaceVertex port : sources) {
			if (port.getName().equals(name)) {
				return port;
			}
		}
		for (SDFInterfaceVertex port : sinks) {
			if (port.getName().equals(name)) {
				return port;
			}
		}
		return null;
	}

	/**
	 * Getter of the property <tt>sinks</tt>
	 * 
	 * @return Returns the sinks.
	 * 
	 */
	public List<SDFInterfaceVertex> getSinks() {
		return sinks;
	}

	/**
	 * Gives the sink with the given name
	 * 
	 * @param name
	 *            The name of the sink to return
	 * @return The Sink with the given name
	 */
	public SDFInterfaceVertex getSink(String name) {
		for (SDFInterfaceVertex sink : sinks) {
			if (sink.getName().equals(name)) {
				return sink;
			}
		}
		return null;
	}

	/**
	 * Gives the source with the given name
	 * 
	 * @param name
	 *            The name of the source to return
	 * @return The Source with the given name
	 */
	public SDFInterfaceVertex getSource(String name) {
		for (SDFInterfaceVertex source : sources) {
			if (source.getName().equals(name)) {
				return source;
			}
		}
		return null;
	}

	/**
	 * Getter of the property <tt>sources</tt>
	 * 
	 * @return Returns the sources.
	 * 
	 */
	public List<SDFInterfaceVertex> getSources() {
		return sources;
	}

	/**
	 * Remove the interface vertex connected to the given edge in the parent
	 * graph
	 * 
	 * @param edge
	 */
	public void removeSink(SDFEdge edge) {
		sinks.remove(edge.getSourceInterface());
	}

	/**
	 * Removes the interface vertex linked to the given edge in the base graph
	 * 
	 * @param edge
	 */
	public void removeSource(SDFEdge edge) {
		sources.remove(edge.getTargetInterface());
	}

	/**
	 * Set an interface vertex external edge
	 * 
	 * @param extEdge
	 *            The edge the given interface is to associate
	 * @param interfaceVertex
	 *            The interface vertex the edge is to associate
	 */
	public void setInterfaceVertexExternalLink(SDFEdge extEdge,
			SDFInterfaceVertex interfaceVertex) {
		if (interfaceVertex.getDirection() == InterfaceDirection.Output) {
			extEdge.setSourceInterface(interfaceVertex);
		} else {
			extEdge.setTargetInterface(interfaceVertex);
		}

	}

	/**
	 * Setter of the property <tt>sinks</tt>
	 * 
	 * @param sinks
	 *            The sinks to set.
	 * 
	 */
	public void setSinks(List<SDFInterfaceVertex> sinks) {
		this.sinks = sinks;
	}

	/**
	 * Setter of the property <tt>sources</tt>
	 * 
	 * @param sources
	 *            The sources to set.
	 * 
	 */
	public void setSources(List<SDFInterfaceVertex> sources) {
		this.sources = sources;
	}

	/**
	 * Gives this vertex Nb repeat
	 * 
	 * @return The number of time to repeat this vertex
	 * @throws InvalidExpressionException
	 */
	public Object getNbRepeat() throws InvalidExpressionException {
		if (getPropertyBean().getValue(NB_REPEAT) == null) {
			((SDFGraph) this.getBase()).computeVRB();
		}
		return getPropertyBean().getValue(NB_REPEAT);
	}

	/**
	 * Gives this vertex Nb repeat
	 * 
	 * @return The number of time to repeat this vertex
	 * @throws InvalidExpressionException
	 */
	public int getNbRepeatAsInteger() throws InvalidExpressionException {
		if (getPropertyBean().getValue(NB_REPEAT) == null) {
			((SDFGraph) this.getBase()).computeVRB();
		}
		if (getPropertyBean().getValue(NB_REPEAT) instanceof Integer) {
			return (Integer) getPropertyBean().getValue(NB_REPEAT);
		} else if (getPropertyBean().getValue(NB_REPEAT) instanceof JSCLInteger) {
			return ((JSCLInteger) getPropertyBean().getValue(NB_REPEAT))
					.intValue();
		} else {
			return 1;
		}
	}

	/**
	 * Set the number of time to repeat this vertex
	 * 
	 * @param nbRepeat
	 *            The number of time to repeat this vertex
	 */
	public void setNbRepeat(int nbRepeat) {
		getPropertyBean().setValue(NB_REPEAT, nbRepeat);
	}

	/**
	 * Set the number of time to repeat this vertex as a generic
	 * 
	 * @param nbRepeat
	 *            The number of time to repeat this vertex
	 */
	public void setNbRepeat(Object nbRepeat) {
		getPropertyBean().setValue(NB_REPEAT, nbRepeat);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean validateModel(Logger logger) throws SDF4JException,
			InvalidExpressionException {
		int i = 0;
		while (i < this.sources.size()) {
			SDFInterfaceVertex source = this.sources.get(i);
			SDFEdge outsideEdge = this.getAssociatedEdge(source);
			if (this.getGraphDescription() != null) {
				AbstractVertex truePort = this.getGraphDescription().getVertex(
						source.getName());
				if (this.getGraphDescription().outgoingEdgesOf(truePort).size() == 0) {
					if (logger != null) {
						logger.log(
								Level.INFO,
								"interface "
										+ source.getName()
										+ " has no inside connection and will be removed for further processing.\n Outside connection has been taken into account for reptition factor computation");
					}
					sources.remove(i);
					this.getGraphDescription().removeVertex(source);
					this.getBase().removeEdge(outsideEdge);
				} else {
					i++;
				}
			} else {
				i++;
			}
		}
		i = 0;
		for (SDFInterfaceVertex sink : sinks) {
			// SDFEdge outsideEdge = this.getAssociatedEdge(sink);
			if (this.getGraphDescription() != null) {
				AbstractVertex truePort = this.getGraphDescription().getVertex(
						sink.getName());
				if (this.getGraphDescription().incomingEdgesOf(truePort).size() == 0) {
					if (logger != null) {
						logger.log(
								Level.INFO,
								"interface "
										+ sink.getName()
										+ " has no inside connection, consider removing this interface if unused");
						throw (new SDF4JException(
								"interface "
										+ sink.getName()
										+ " has no inside connection, consider removing this interface if unused"));
					}
				}
			}
		}
		if (this.getArguments() != null) {
			for (Argument arg : this.getArguments().values()) {
				if (!(arg instanceof PSDFDynamicArgument)) {
					@SuppressWarnings("unused")
					int val;
					try {
						val = arg.intValue();
						//arg.setValue(String.valueOf(val));//TODO: was meant to solve arguments once for all ...
					} catch (NoIntegerValueException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}

	public String toString() {
		return getName();
	}

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

}
