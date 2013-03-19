package net.sf.dftools.algorithm.model.sdf;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.dftools.algorithm.SDFMath;
import net.sf.dftools.algorithm.factories.ModelVertexFactory;
import net.sf.dftools.algorithm.factories.SDFEdgeFactory;
import net.sf.dftools.algorithm.factories.SDFVertexFactory;
import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.IInterface;
import net.sf.dftools.algorithm.model.PropertyBean;
import net.sf.dftools.algorithm.model.PropertyFactory;
import net.sf.dftools.algorithm.model.dag.DAGEdge;
import net.sf.dftools.algorithm.model.dag.DAGVertex;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.parameters.NoIntegerValueException;
import net.sf.dftools.algorithm.model.parameters.Variable;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFBroadcastVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFForkVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFJoinVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFRoundBufferVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.sdf.visitors.TopologyVisitor;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;
import net.sf.dftools.algorithm.model.visitors.VisitorOutput;

import org.jgrapht.EdgeFactory;
import org.math.array.DoubleArray;
import org.math.array.LinearAlgebra;

/**
 * Abstract Class representing an SDF graph
 * 
 * @author jpiat
 * 
 */
public class SDFGraph extends AbstractGraph<SDFAbstractVertex, SDFEdge> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final String TOPOLOGY = "topology";
	protected static final String SCHEDULABLE = "schedulable";
	protected static final String VALID_MODEL = "valid_model";

	private HashMap<SDFEdge, SDFEdge> oldRef = new HashMap<SDFEdge, SDFEdge>();

	/**
	 * Construct a new SDFGraph with the default edge factory
	 * 
	 */
	public SDFGraph() {
		super(new SDFEdgeFactory());
		this.getPropertyBean().setValue(AbstractGraph.MODEL, "sdf");
	}

	/**
	 * COnstruct a new SDFAbstractGraph using the given EdgeFactory ef
	 * 
	 * @param ef
	 */
	public SDFGraph(EdgeFactory<SDFAbstractVertex, SDFEdge> ef) {
		super(ef);
		setName("");
		this.getPropertyBean().setValue(AbstractGraph.MODEL, "sdf");
	}

	/**
	 * Creates a new SDFAbstractGraph with the given factory
	 * 
	 * @param factory
	 *            The factory used to create edges
	 */
	public SDFGraph(SDFEdgeFactory factory) {
		super(factory);
		setName("");
		this.getPropertyBean().setValue(AbstractGraph.MODEL, "sdf");
	}

	public SDFEdge addEdge(SDFAbstractVertex source, SDFAbstractVertex target) {
		SDFEdge newEdge = super.addEdge(source, target);
		// properties.setValue(PropertyBean.PROPERTY_ADD, null, newEdge);
		this.getPropertyBean().setValue(TOPOLOGY, null);
		this.getPropertyBean().setValue(SCHEDULABLE, null);
		if (source instanceof SDFForkVertex
				|| source instanceof SDFBroadcastVertex) {
			source.connectionAdded(newEdge);
		} else if (target instanceof SDFJoinVertex
				|| target instanceof SDFRoundBufferVertex) {
			target.connectionAdded(newEdge);
		}
		return newEdge;
	}

	public SDFEdge addEdge(SDFAbstractVertex source, IInterface sourcePort,
			SDFAbstractVertex target, IInterface targetPort) {
		SDFEdge edge = this.addEdge(source, target);
		edge.setSourceInterface((SDFInterfaceVertex) sourcePort);
		source.setInterfaceVertexExternalLink(edge,
				(SDFInterfaceVertex) sourcePort);
		edge.setTargetInterface((SDFInterfaceVertex) targetPort);
		target.setInterfaceVertexExternalLink(edge,
				(SDFInterfaceVertex) targetPort);
		return edge;
	}

	/**
	 * Add an edge an creates default interfaces on the source and target
	 * vertices
	 * 
	 * @param sourceVertex
	 * @param targetVertex
	 * @return The created edge
	 */
	public SDFEdge addEdgeWithInterfaces(SDFAbstractVertex sourceVertex,
			SDFAbstractVertex targetVertex) {
		SDFEdge edge = addEdge(sourceVertex, targetVertex);
		if (edge != null) {
			SDFInterfaceVertex sinkInterface = new SDFSinkInterfaceVertex();
			sinkInterface.setName("O_" + sourceVertex.getName() + "_"
					+ sourceVertex.getSinks().size());
			sourceVertex.addSink(sinkInterface);
			edge.setSourceInterface(sinkInterface);

			SDFInterfaceVertex sourceInterface = new SDFSourceInterfaceVertex();
			sourceInterface.setName("I_" + targetVertex.getName() + "_"
					+ targetVertex.getSources().size());
			targetVertex.addSource(sourceInterface);
			edge.setTargetInterface(sourceInterface);
		}
		return edge;
	}

	public boolean addVertex(SDFAbstractVertex vertex) {
		if (super.addVertex(vertex)) {
			this.getPropertyBean().setValue("topology", null);
			return true;
		}
		return false;

	}

	/**
	 * Check the graph schedulability
	 * 
	 * @return true if the graph is statically schedulable
	 */
	public boolean checkSchedulability() {
		TopologyVisitor visitor = new TopologyVisitor();
		try {
			this.accept(visitor);
		} catch (SDF4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return visitor.result();
	}

	/**
	 * Clean the graph, removes all edges and vertices
	 */
	public void clean() {
		ArrayList<SDFEdge> edges = new ArrayList<SDFEdge>(this.edgeSet());
		for (int i = 0; i < edges.size(); i++) {
			this.removeEdge(edges.get(i));
		}
		ArrayList<SDFAbstractVertex> vertices = new ArrayList<SDFAbstractVertex>(
				this.vertexSet());
		for (int i = 0; i < vertices.size(); i++) {
			this.removeVertex(vertices.get(i));
		}
	}

	@Override
	public SDFGraph clone() {
		SDFGraph newGraph = new SDFGraph();
		HashMap<SDFAbstractVertex, SDFAbstractVertex> matchCopies = new HashMap<SDFAbstractVertex, SDFAbstractVertex>();
		for (SDFAbstractVertex vertices : vertexSet()) {
			SDFAbstractVertex newVertex = vertices.clone();
			newGraph.addVertex(newVertex);
			matchCopies.put(vertices, newVertex);
		}
		for (SDFEdge edge : edgeSet()) {
			SDFEdge newEdge = newGraph.addEdge(
					matchCopies.get(edge.getSource()),
					matchCopies.get(edge.getTarget()));
			for (SDFInterfaceVertex sink : matchCopies.get(edge.getSource())
					.getSinks()) {
				if (edge.getTargetInterface() != null
						&& edge.getTargetInterface().getName()
								.equals(sink.getName())) {
					matchCopies.get(edge.getSource())
							.setInterfaceVertexExternalLink(newEdge, sink);
				}
			}
			for (SDFInterfaceVertex source : matchCopies.get(edge.getTarget())
					.getSources()) {
				if (edge.getSourceInterface() != null
						&& edge.getSourceInterface().getName()
								.equals(source.getName())) {
					matchCopies.get(edge.getTarget())
							.setInterfaceVertexExternalLink(newEdge, source);
				}
			}
			newEdge.copyProperties(edge);
		}
		newGraph.copyProperties(this);
		newGraph.getPropertyBean().setValue("topology", null);
		newGraph.getPropertyBean().setValue("vrb", null);
		return newGraph;
	}

	/**
	 * Fill this graph object with the given graph content
	 * 
	 * @param content
	 *            The content to fill in this graph
	 */
	public void fill(SDFGraph content) {
		SDFGraph cleanGraph = content.clone();
		for (SDFAbstractVertex vertex : cleanGraph.vertexSet()) {
			this.addVertex(vertex);
		}
		for (SDFEdge edge : cleanGraph.edgeSet()) {
			SDFAbstractVertex source = cleanGraph.getEdgeSource(edge);
			SDFAbstractVertex target = cleanGraph.getEdgeTarget(edge);
			SDFEdge newEdge = this.addEdge(source, target);
			newEdge.setSourceInterface(edge.getSourceInterface());
			newEdge.setTargetInterface(edge.getTargetInterface());
			target.setInterfaceVertexExternalLink(newEdge,
					edge.getTargetInterface());
			source.setInterfaceVertexExternalLink(newEdge,
					edge.getSourceInterface());

			newEdge.setCons(edge.getCons().clone());
			newEdge.setProd(edge.getProd().clone());
			newEdge.setDelay(edge.getDelay().clone());

		}

		for (String propertyKey : cleanGraph.getPropertyBean().keys()) {
			Object property = cleanGraph.getPropertyBean()
					.getValue(propertyKey);
			this.getPropertyBean().setValue(propertyKey, property);
		}

	}

	public SDFAbstractVertex getEdgeSource(SDFEdge edge) {
		try {
			return super.getEdgeSource(edge);

		} catch (Exception e) {
			if (oldRef.get(edge) != null) {
				return this.getEdgeSource(oldRef.get(edge));
			}
		}
		return null;
	}

	public SDFAbstractVertex getEdgeTarget(SDFEdge edge) {
		try {
			return super.getEdgeTarget(edge);

		} catch (Exception e) {
			if (oldRef.get(edge) != null) {
				return this.getEdgeTarget(oldRef.get(edge));
			}
		}
		return null;
	}

	/**
	 * Gives this graph's topology matrix as an array of array of double
	 * 
	 * @return the two dimensionnal array of double representing the topology
	 *         matrix
	 * @throws InvalidExpressionException
	 */
	public double[][] getTopologyMatrix() throws InvalidExpressionException {
		if (this.getPropertyBean().getValue(TOPOLOGY) != null) {
			return (double[][]) this.getPropertyBean().getValue(TOPOLOGY);
		}
		int nbLi = 0;
		HashMap<SDFAbstractVertex, Integer> associateIndex = new HashMap<SDFAbstractVertex, Integer>();
		int i = 0;
		for (SDFAbstractVertex vertex : this.vertexSet()) {
			if (!(vertex instanceof SDFInterfaceVertex)) {
				associateIndex.put(vertex, i);
				i++;
			}
		}
		for (SDFEdge edge : this.edgeSet()) {
			if (!(edge.getSource() instanceof SDFInterfaceVertex || edge
					.getTarget() instanceof SDFInterfaceVertex)) {
				nbLi++;
			}
		}
		double[][] topo = DoubleArray.fill(nbLi, i, 0);
		i = 0;
		for (SDFEdge edge : this.edgeSet()) {
			if (!(edge.getSource() instanceof SDFInterfaceVertex || edge
					.getTarget() instanceof SDFInterfaceVertex)) {
				topo[i][associateIndex.get(this.getEdgeSource(edge))] += edge
						.getProd().intValue();
				topo[i][associateIndex.get(this.getEdgeTarget(edge))] += -edge
						.getCons().intValue();
				i++;
			}

		}
		this.getPropertyBean().setValue(TOPOLOGY, topo);
		return topo;
	}

	/**
	 * Compute the vrb of this graph and affect the nbRepeat property to
	 * vertices
	 * 
	 * @throws InvalidExpressionException
	 */
	protected boolean computeVRB() throws InvalidExpressionException {
		HashMap<SDFAbstractVertex, Integer> vrb;
		double[][] topo = this.getTopologyMatrix();
		if (this.getParentVertex() != null) {
			vrb = SDFMath.computeRationnalVRBWithInterfaces(this);
		} else if (Array.getLength(topo) == 0) {
			vrb = new HashMap<SDFAbstractVertex, Integer>();
			for (SDFAbstractVertex vertex : this.vertexSet()) {
				vrb.put(vertex, 1);
			}
		} else {
			vrb = SDFMath.computeRationnalVRB(this);
		}
		for (SDFAbstractVertex vertex : vrb.keySet()) {
			vertex.setNbRepeat(vrb.get(vertex));
		}
		return true;
	}

	/**
	 * Check the schedulability of the graph
	 * 
	 * @return True if the graph is schedulable
	 * @throws SDF4JException
	 */
	public boolean isSchedulable() throws SDF4JException {
		if (this.getPropertyBean().getValue(SCHEDULABLE) != null) {
			return (Boolean) this.getPropertyBean().getValue(SCHEDULABLE);
		}
		TopologyVisitor checkTopo = new TopologyVisitor();
		boolean result;
		try {
			this.accept(checkTopo);
			result = checkTopo.result();
		} catch (SDF4JException e) {
			throw (e);
		}
		this.getPropertyBean().setValue(SCHEDULABLE, result);
		return result;
	}

	/**
	 * Check the schedulability of the graph
	 * 
	 * @return True if the graph is schedulable
	 * @throws SDF4JException
	 */
	public boolean isSchedulable(Logger log) throws SDF4JException {
		if (this.getPropertyBean().getValue(SCHEDULABLE) != null) {
			return (Boolean) this.getPropertyBean().getValue(SCHEDULABLE);
		}
		boolean schedulable = true;
		List<SDFAbstractVertex> vertices = new ArrayList<SDFAbstractVertex>();
		for (SDFAbstractVertex vertex : this.vertexSet()) {
			if (!(vertex instanceof SDFInterfaceVertex)) {
				if (vertex.getGraphDescription() != null
						&& vertex.getGraphDescription() instanceof SDFGraph) {
					schedulable &= ((SDFGraph) vertex.getGraphDescription())
							.isSchedulable();
				}
				vertices.add((SDFAbstractVertex) vertex);
			}

		}
		int rank;
		if (vertices.size() == 1) {
			schedulable &= true;
		}
		try {
			if (Array.getLength(this.getTopologyMatrix()) > 0) {
				rank = LinearAlgebra.rank(this.getTopologyMatrix());
			} else {
				schedulable &= true;
				this.getPropertyBean().setValue(SCHEDULABLE, schedulable);
				return schedulable;
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw (new SDF4JException(e.getMessage()));
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw (new SDF4JException(e.getMessage()));
		}
		if (rank == vertices.size() - 1) {
			schedulable &= true;
		} else {
			schedulable &= false;
			VisitorOutput.getLogger().log(Level.SEVERE,
					"Graph " + this.getName() + " is not schedulable");
		}
		this.getPropertyBean().setValue(SCHEDULABLE, schedulable);
		return schedulable;
	}

	/**
	 * Gives a Set of all this graph child property beans
	 * 
	 * @return The properties Set
	 */
	public List<PropertyBean> propertiesSet() {
		List<PropertyBean> properties = new ArrayList<PropertyBean>();
		for (SDFAbstractVertex child : vertexSet()) {
			properties.add(child.getPropertyBean());
		}
		return properties;
	}

	public SDFEdge removeEdge(SDFAbstractVertex sourceVertex,
			SDFAbstractVertex targetVertex) {
		SDFEdge edge = super.removeEdge(sourceVertex, targetVertex);
		if (edge != null) {
			if (sourceVertex instanceof SDFVertex) {
				((SDFVertex) sourceVertex).removeSink(edge);
			}
			if (targetVertex instanceof SDFVertex) {
				((SDFVertex) targetVertex).removeSource(edge);
			}

			if (sourceVertex instanceof SDFForkVertex) {
				((SDFForkVertex) sourceVertex).connectionRemoved(edge);
			} else if (targetVertex instanceof SDFJoinVertex) {
				((SDFJoinVertex) targetVertex).connectionRemoved(edge);
			}
		}
		return edge;
	}

	public String toString() {
		return this.getName();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void update(AbstractGraph<?, ?> observable, Object arg) {
		if (arg != null) {
			if (arg instanceof AbstractVertex) {
				if (observable.vertexSet().contains(arg)) {
					SDFVertex newVertex = new SDFVertex();
					newVertex.setName(((AbstractVertex) arg).getName());
					newVertex.setId(((AbstractVertex) arg).getId());
					newVertex.setRefinement(((AbstractVertex) arg)
							.getRefinement());
					this.addVertex(newVertex);
				} else {
					this.removeVertex(this.getVertex(((AbstractVertex) arg)
							.getName()));
				}
			} else if (arg instanceof AbstractEdge) {
				if (observable.edgeSet().contains(arg)) {
					if (arg instanceof SDFEdge) {
						SDFAbstractVertex source = ((SDFEdge) arg).getSource();
						SDFAbstractVertex target = ((SDFEdge) arg).getTarget();
						SDFAbstractVertex newSource = this.getVertex(source
								.getName());
						SDFAbstractVertex newTarget = this.getVertex(target
								.getName());
						this.addEdge(newSource, newTarget, (SDFEdge) arg);
					} else if (arg instanceof DAGEdge) {
						DAGVertex source = ((DAGEdge) arg).getSource();
						DAGVertex target = ((DAGEdge) arg).getTarget();
						SDFAbstractVertex newSource = this.getVertex(source
								.getName());
						SDFAbstractVertex newTarget = this.getVertex(target
								.getName());
						for (AbstractEdge edge : ((DAGEdge) arg).getAggregate()) {
							SDFEdge newEdge = this
									.addEdge(newSource, newTarget);
							newEdge.copyProperties(edge);
						}
					}
				} else {
					if (arg instanceof SDFEdge) {
						SDFAbstractVertex source = ((SDFEdge) arg).getSource();
						SDFAbstractVertex target = ((SDFEdge) arg).getTarget();
						SDFAbstractVertex newSource = this.getVertex(source
								.getName());
						SDFAbstractVertex newTarget = this.getVertex(target
								.getName());
						for (SDFEdge edge : this.getAllEdges(newSource,
								newTarget)) {
							if (edge.getSourceInterface()
									.getName()
									.equals(((SDFEdge) arg)
											.getSourceInterface().getName())
									&& edge.getTargetInterface()
											.getName()
											.equals(((SDFEdge) arg)
													.getTargetInterface()
													.getName())) {
								this.removeEdge(edge);
								break;
							}
						}
					} else if (arg instanceof DAGEdge) {
						DAGVertex source = ((DAGEdge) arg).getSource();
						DAGVertex target = ((DAGEdge) arg).getTarget();
						SDFAbstractVertex newSource = this.getVertex(source
								.getName());
						SDFAbstractVertex newTarget = this.getVertex(target
								.getName());
						this.removeAllEdges(newSource, newTarget);
					}
				}
			} else if (arg instanceof String) {
				Object property = observable.getPropertyBean().getValue(
						(String) arg);
				if (property != null) {
					this.getPropertyBean().setValue((String) arg, property);
				}
			}
		}

	}

	/**
	 * Validate the model's schedulability
	 * 
	 * @return True if the model is valid, false otherwise ...
	 * @throws SDF4JException
	 * @throws InvalidExpressionException
	 */
	public boolean validateModel(Logger logger) throws SDF4JException {
		try {
			if (this.getPropertyBean().getValue(VALID_MODEL) != null) {
				return (Boolean) this.getPropertyBean().getValue(VALID_MODEL);
			}
			if (this.isSchedulable(logger)) {
				this.computeVRB();
				/*
				 * if (this.getVariables() != null) { for (Variable var :
				 * this.getVariables().values()) { int val; try { val =
				 * var.intValue(); var.setValue(String.valueOf(val)); } catch
				 * (NoIntegerValueException e) { // TODO Auto-generated catch
				 * block e.printStackTrace(); } } }
				 */// TODO: variable should only need to be resolved once, but
					// keep memory of their integer value
				for (SDFAbstractVertex child : vertexSet()) {
					validateChild(child, logger);
				}
				// solving all the parameter for the rest of the processing ...
				/*
				 * for (SDFEdge edge : edgeSet()) { edge.setDelay(new
				 * SDFIntEdgePropertyType(edge.getDelay() .intValue()));
				 * edge.setCons(new SDFIntEdgePropertyType(edge.getCons()
				 * .intValue())); edge.setProd(new
				 * SDFIntEdgePropertyType(edge.getProd() .intValue())); }
				 */
				int i = 0;
				while (i < this.vertexSet().size()) {
					SDFAbstractVertex vertex = (SDFAbstractVertex) (this
							.vertexSet().toArray()[i]);
					if (this.outgoingEdgesOf(vertex).size() == 0
							&& this.incomingEdgesOf(vertex).size() == 0) {
						this.removeVertex(vertex);
						if (logger != null) {
							logger.log(
									Level.INFO,
									vertex.getName()
											+ " has been removed because it doesn't produce or consume data. \n This vertex has been used for repetition factor computation");
						}
					} else {
						if (vertex instanceof SDFVertex) {
							insertBroadcast((SDFVertex) vertex);
						}
						i++;
					}
				}

				this.getPropertyBean().setValue(VALID_MODEL, true);
				return true;
			}
			return false;
		} catch (InvalidExpressionException e) {
			throw new SDF4JException(this.getName() + ": " + e.getMessage());
		}
	}

	private void insertBroadcast(SDFVertex vertex) {
		HashMap<SDFInterfaceVertex, ArrayList<SDFEdge>> connections = new HashMap<SDFInterfaceVertex, ArrayList<SDFEdge>>();
		for (SDFEdge edge : this.outgoingEdgesOf(vertex)) {
			if (connections.get(edge.getSourceInterface()) == null) {
				connections.put(edge.getSourceInterface(),
						new ArrayList<SDFEdge>());
			}
			connections.get(edge.getSourceInterface()).add(edge);
		}
		for (SDFInterfaceVertex port : connections.keySet()) {
			if (connections.get(port).size() > 1) {
				SDFBroadcastVertex broadcastPort = new SDFBroadcastVertex();
				broadcastPort.setName("br_" + vertex.getName() + "_"
						+ port.getName());
				SDFSourceInterfaceVertex inPort = new SDFSourceInterfaceVertex();
				inPort.setName("in");
				SDFSinkInterfaceVertex outPort = new SDFSinkInterfaceVertex();
				outPort.setName("out");
				broadcastPort.addSink(outPort);
				broadcastPort.addSource(inPort);
				this.addVertex(broadcastPort);
				SDFEdge baseEdge = this.addEdge(vertex, broadcastPort);
				baseEdge.setSourceInterface(port);
				baseEdge.setTargetInterface(inPort);
				for (SDFEdge oldEdge : connections.get(port)) {
					SDFEdge newEdge = this.addEdge(broadcastPort,
							oldEdge.getTarget());
					newEdge.setSourceInterface(outPort);
					newEdge.setTargetInterface(oldEdge.getTargetInterface());
					newEdge.setProd(oldEdge.getProd());
					newEdge.setCons(oldEdge.getCons());
					newEdge.setDelay(oldEdge.getDelay());
					newEdge.setDataType(oldEdge.getDataType());
					baseEdge.setProd(oldEdge.getProd().clone());
					baseEdge.setCons(oldEdge.getProd().clone());
					baseEdge.setDelay(new SDFIntEdgePropertyType(0));
					baseEdge.setDataType(oldEdge.getDataType());
					this.removeEdge(oldEdge);
				}
			}
		}
	}

	private void validateChild(SDFAbstractVertex child, Logger logger)
			throws InvalidExpressionException, SDF4JException {

		System.out.println(child.getName() + " x" + child.getNbRepeat());
		if (!child.validateModel(logger)) {
			throw new SDF4JException(child.getName()
					+ " is not a valid vertex, verify arguments");
		}
		if (child.getGraphDescription() != null) {
			SDFGraph descritption = ((SDFGraph) child.getGraphDescription());
			if (!((SDFGraph) child.getGraphDescription()).validateModel(logger)) {
				throw (new SDF4JException(child.getGraphDescription().getName()
						+ " is not schedulable"));
			}
			List<SDFAbstractVertex> treatedInterfaces = new ArrayList<SDFAbstractVertex>();
			for (SDFEdge edge : this.incomingEdgesOf(child)) {
				SDFSourceInterfaceVertex sourceInterface = (SDFSourceInterfaceVertex) edge
						.getTargetInterface();
				if (treatedInterfaces.contains(sourceInterface)) {
					throw new SDF4JException(
							sourceInterface.getName()
									+ " is multiply connected, consider using broadcast ");
				} else {
					treatedInterfaces.add(sourceInterface);
				}
				if (descritption.getVertex(sourceInterface.getName()) != null) {
					SDFAbstractVertex trueSourceInterface = descritption
							.getVertex(sourceInterface.getName());
					for (SDFEdge edgeIn : descritption
							.outgoingEdgesOf(trueSourceInterface)) {
						if (edgeIn.getProd().intValue() != edge.getCons()
								.intValue()) {
							throw new SDF4JException(
									sourceInterface.getName()
											+ " in "
											+ child.getName()
											+ " has incompatible outside consumption and inside production "
											+ edgeIn.getProd().intValue()
											+ " != "
											+ edge.getCons().intValue());
						}
					}
				}
			}

			for (SDFEdge edge : this.outgoingEdgesOf(child)) {
				SDFSinkInterfaceVertex sinkInterface = (SDFSinkInterfaceVertex) edge
						.getSourceInterface();
				if (treatedInterfaces.contains(sinkInterface)) {
					throw new SDF4JException(
							sinkInterface.getName()
									+ " is multiply connected, consider using broadcast ");
				} else {
					treatedInterfaces.add(sinkInterface);
				}
				if (descritption.getVertex(sinkInterface.getName()) != null) {
					SDFAbstractVertex trueSinkInterface = descritption
							.getVertex(sinkInterface.getName());
					for (SDFEdge edgeIn : descritption
							.incomingEdgesOf(trueSinkInterface)) {
						if (edgeIn.getCons().intValue() != edge.getProd()
								.intValue()) {
							throw new SDF4JException(
									sinkInterface.getName()
											+ " in "
											+ child.getName()
											+ " has incompatible outside production and inside consumption "
											+ edgeIn.getProd().intValue()
											+ " != "
											+ edge.getCons().intValue());
						}
					}
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelVertexFactory getVertexFactory() {
		return SDFVertexFactory.getInstance();
	}

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

}
