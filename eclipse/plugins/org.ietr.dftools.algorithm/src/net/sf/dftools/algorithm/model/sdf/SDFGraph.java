package net.sf.dftools.algorithm.model.sdf;

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
import net.sf.dftools.algorithm.model.sdf.esdf.SDFBroadcastVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFForkVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFJoinVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFRoundBufferVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;
import net.sf.dftools.algorithm.model.visitors.VisitorOutput;

import org.jgrapht.EdgeFactory;
import org.math.array.DoubleArray;
import org.math.array.LinearAlgebra;

/**
 * Abstract Class representing an SDF graph
 * 
 * @author jpiat
 * @author kdesnos
 * @author jheulot
 * 
 */
public class SDFGraph extends AbstractGraph<SDFAbstractVertex, SDFEdge> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final String TOPOLOGY = "topology";
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

	public SDFEdge addEdge(SDFAbstractVertex source, SDFAbstractVertex target) {
		SDFEdge newEdge = super.addEdge(source, target);
		// properties.setValue(PropertyBean.PROPERTY_ADD, null, newEdge);
		if (source instanceof SDFForkVertex
				|| source instanceof SDFBroadcastVertex) {
			source.connectionAdded(newEdge);
		} 
		
		if (target instanceof SDFJoinVertex
				|| target instanceof SDFRoundBufferVertex) {
			target.connectionAdded(newEdge);
		}
		return newEdge;
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
	 * Compute the vrb of this graph and affect the nbRepeat property to
	 * vertices
	 * 
	 * @throws InvalidExpressionException
	 */
	protected boolean computeVRB() throws InvalidExpressionException {
		HashMap<SDFAbstractVertex, Integer> vrb = new HashMap<SDFAbstractVertex, Integer>();
		List<List<SDFAbstractVertex>> subgraphs = this.getAllSubGraphs();
		
		for(List<SDFAbstractVertex> subgraph : subgraphs){
			boolean hasInterface = false;
			for(SDFAbstractVertex vertex : subgraph){
				hasInterface |= vertex instanceof SDFInterfaceVertex;
			}

			if (hasInterface) {
				vrb.putAll(SDFMath.computeRationnalVRBWithInterfaces(subgraph, this));
			} else {
				vrb.putAll(SDFMath.computeRationnalVRB(subgraph, this));
			}
		}
		for (SDFAbstractVertex vertex : vrb.keySet()) {
			vertex.setNbRepeat(vrb.get(vertex));
		}
		return true;
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

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Iterative function of getAllSubGraphs
	 * @param vertex	the current vertex
	 * @param subgraph	the current subgraph
	 */
	private void getSubGraph(SDFAbstractVertex vertex, List<SDFAbstractVertex> subgraph){
		for(SDFEdge edge : this.outgoingEdgesOf(vertex)){
			if(!subgraph.contains(this.getEdgeTarget(edge))){
				subgraph.add(this.getEdgeTarget(edge));
				getSubGraph(this.getEdgeTarget(edge), subgraph);
			}
		}
		for(SDFEdge edge : this.incomingEdgesOf(vertex)){
			if(!subgraph.contains(this.getEdgeSource(edge))){
				subgraph.add(this.getEdgeSource(edge));
				getSubGraph(this.getEdgeSource(edge), subgraph);
			}
		}
	}

	/**
	 * Divide the current graph into a list of subgraph
	 * @return the list of subgraph
	 */
	public List<List<SDFAbstractVertex>> getAllSubGraphs(){		
		List<List<SDFAbstractVertex>> subgraphs = new ArrayList<List<SDFAbstractVertex>>();

		for(SDFAbstractVertex vertex : vertexSet()){
			boolean notAssignedToASubgraph = true;
			for(List<SDFAbstractVertex> subgraph : subgraphs){
				if(subgraph.contains(vertex)){
					notAssignedToASubgraph = false;
					break;
				}
			}
			if(notAssignedToASubgraph){
				List<SDFAbstractVertex> subgraph = new ArrayList<SDFAbstractVertex>();
				subgraph.add(vertex);
				
				getSubGraph(vertex, subgraph);

				subgraphs.add(subgraph);
			}
		}

		return subgraphs;
	}

	/**
	 * Gives the topology matrix of a subgraph of this graph as an array of double
	 * The subgraph must not contain InterfaceVertex
	 * 
	 * @param subgraph the subgraph
	 * @return the topology matrix
	 * 
	 * @throws InvalidExpressionException
	 */
	public double[][] getTopologyMatrix(List<SDFAbstractVertex> subgraph) throws InvalidExpressionException{
		List<double[]> topologyListMatrix = new ArrayList<double[]>();
		double[][] topologyArrayMatrix;
		
		for(SDFAbstractVertex vertex : subgraph){
			if(vertex instanceof SDFInterfaceVertex)
				throw new IllegalArgumentException("Cannot get topology matrix "
						+ "from a subgraph with interface vertices");
		}

		for (SDFEdge edge : this.edgeSet()) {
			SDFAbstractVertex source = this.getEdgeSource(edge);
			SDFAbstractVertex target = this.getEdgeTarget(edge);
			if(subgraph.contains(source) 
					&& subgraph.contains(target)
					&& ! source.equals(target)){
				double line[] = DoubleArray.fill(subgraph.size(), 0);
				line[subgraph.indexOf(source)] += edge.getProd().intValue();
				line[subgraph.indexOf(target)] -= edge.getCons().intValue();
				topologyListMatrix.add(line);
			}
		}
		
		if(topologyListMatrix.size() == 0)
			topologyArrayMatrix = new double[0][0];
		else{
			topologyArrayMatrix = new double[topologyListMatrix.size()][topologyListMatrix.get(0).length];
			
			for(int i=0; i<topologyListMatrix.size() ; i++)
				topologyArrayMatrix[i] = topologyListMatrix.get(i);
			
		}

		return topologyArrayMatrix;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelVertexFactory getVertexFactory() {
		return SDFVertexFactory.getInstance();
	}

	/**
	 * Insert Broadcast where is needed.
	 * Multiple edges connected to one output for example
	 * 
	 * @param vertex the current vertex.
	 * @param logger the logger where display a warning.
	 */
	private void insertBroadcast(SDFVertex vertex, Logger logger) {
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
				logger.log(	Level.WARNING,
						"Warning: Implicit Broadcast added in graph "+this.getName()
						+" at port "+ vertex + "." + port.getName());
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
					newEdge.setTargetPortModifier(oldEdge.getTargetPortModifier());
					newEdge.setProd(oldEdge.getProd());
					newEdge.setCons(oldEdge.getCons());
					newEdge.setDelay(oldEdge.getDelay());
					newEdge.setDataType(oldEdge.getDataType());
					baseEdge.setSourcePortModifier(oldEdge.getSourcePortModifier());
					baseEdge.setProd(oldEdge.getProd().clone());
					baseEdge.setCons(oldEdge.getProd().clone());
					baseEdge.setDelay(new SDFIntEdgePropertyType(0));
					baseEdge.setDataType(oldEdge.getDataType());
					this.removeEdge(oldEdge);
				}
			}
		}
	}

	/**
	 * Check the schedulability of the graph
	 * 
	 * @return True if the graph is schedulable
	 * @throws InvalidExpressionException 
	 */
	public boolean isSchedulable() throws SDF4JException {
		boolean schedulable = true;
		for (SDFAbstractVertex vertex : this.vertexSet()) {
			if (!(vertex instanceof SDFInterfaceVertex)) {
				if (vertex.getGraphDescription() != null
						&& vertex.getGraphDescription() instanceof SDFGraph) {
					schedulable &= ((SDFGraph) vertex.getGraphDescription())
							.isSchedulable();
				}
			}

		}
		List<List<SDFAbstractVertex>> subgraphs = this.getAllSubGraphs();
			
		try{
			for(List<SDFAbstractVertex> subgraph : subgraphs){
				
				List<SDFAbstractVertex> subgraphWOInterfaces = new ArrayList<SDFAbstractVertex>();
				for(SDFAbstractVertex vertex : subgraph){
					if(!(vertex instanceof SDFInterfaceVertex))
						subgraphWOInterfaces.add(vertex);
				}

				double[][] topologyMatrix = getTopologyMatrix(subgraphWOInterfaces);
		
				if(topologyMatrix.length > 0){
					int rank = LinearAlgebra.rank(topologyMatrix);
					if(rank == subgraphWOInterfaces.size()-1){
						schedulable &= true;
					}else{
						schedulable &= false;
						VisitorOutput.getLogger().log(Level.SEVERE,
								"Graph " + this.getName() + " is not schedulable");
					}
				}
			}
		} catch (InvalidExpressionException e) {
			throw new SDF4JException(this.getName() + ": " + e.getMessage());
		}
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

	/**
	 * This method is used to remove an {@link SDFEdge} from a {@link SDFGraph}.
	 * Side effects are: the deletion of the {@link SDFSourceInterfaceVertex}
	 * and {@link SDFSinkInterfaceVertex} associated to this {@link SDFEdge}
	 * (unless several vertices are linked to this interface). For
	 * {@link SDFForkVertex} and {@link SDFJoinVertex}, the ordered list of
	 * input/output edges is updated.
	 * 
	 * @param sourceVertex
	 *            the source {@link SDFVertex} of the removed {@link SDFEdge}
	 * @param targetVertex
	 *            the target {@link SDFVertex} of the removed {@link SDFEdge}
	 * @return the removed {@link SDFEdge}
	 * 
	 * @see AbstractGraph#removeEdge(AbstractVertex, AbstractVertex)
	 * 
	 * @deprecated The method is deprecated.
	 *             {@link AbstractGraph#removeEdge(AbstractEdge)} should be used
	 *             instead. Indeed, if several edges link the source and the
	 *             target vertex, a random edge will be removed.
	 * 
	 */
	@Deprecated
	public SDFEdge removeEdge(SDFAbstractVertex sourceVertex,
			SDFAbstractVertex targetVertex) {
		checkMultipleEdges(sourceVertex, targetVertex);

		@SuppressWarnings("deprecation")
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

	/**
	 * This method is used to remove an {@link SDFEdge} from a {@link SDFGraph}.
	 * Side effects are: the deletion of the {@link SDFSourceInterfaceVertex}
	 * and {@link SDFSinkInterfaceVertex} associated to this {@link SDFEdge}
	 * (unless several vertices are linked to this interface). For
	 * {@link SDFForkVertex} {@link SDFJoinVertex}, {@link SDFBroadcastVertex}
	 * and {@link SDFRoundBufferVertex}, the ordered list of input/output edges
	 * is updated.
	 * 
	 * @param edge
	 *            the removed {@link SDFEdge}
	 * @return <code>true</code> if the edge was correctly removed,
	 *         <code>false</code> else.
	 * 
	 * @see AbstractGraph#removeEdge(SDFEdge)
	 * 
	 * 
	 */
	public boolean removeEdge(SDFEdge edge) {
		SDFAbstractVertex sourceVertex = edge.getSource();
		SDFAbstractVertex targetVertex = edge.getTarget();
		boolean res = super.removeEdge(edge);
		if (res) {
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

			if (sourceVertex instanceof SDFBroadcastVertex) {
				((SDFBroadcastVertex) sourceVertex).connectionRemoved(edge);
			} else if (targetVertex instanceof SDFRoundBufferVertex) {
				((SDFRoundBufferVertex) targetVertex).connectionRemoved(edge);
			}

		}
		return res;
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

	private void validateChild(SDFAbstractVertex child, Logger logger)
			throws InvalidExpressionException, SDF4JException {

		//System.out.println(child.getName() + " x" + child.getNbRepeat());
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

	/**
	 * Validate the model's schedulability
	 * 
	 * @return True if the model is valid, false otherwise ...
	 * @throws SDF4JException
	 * @throws InvalidExpressionException
	 */
	public boolean validateModel(Logger logger) throws SDF4JException {
		try {
			if (this.isSchedulable()) {
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
					/*
					 *  (15/01/14) Removed by jheulot: allowing unconnected actor 
					 */
					/*
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
 					*/
						if (vertex instanceof SDFVertex) {
							insertBroadcast((SDFVertex) vertex, logger);
						}
						i++;
					/*}*/
				}

				return true;
			}
			return false;
		} catch (InvalidExpressionException e) {
			throw new SDF4JException(this.getName() + ": " + e.getMessage());
		}
	}

}
