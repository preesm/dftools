package net.sf.dftools.algorithm.model.dag;

import java.util.logging.Logger;

import net.sf.dftools.algorithm.exceptions.CreateCycleException;
import net.sf.dftools.algorithm.exceptions.CreateMultigraphException;
import net.sf.dftools.algorithm.factories.DAGEdgeFactory;
import net.sf.dftools.algorithm.factories.DAGVertexFactory;
import net.sf.dftools.algorithm.factories.ModelVertexFactory;
import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.PropertyFactory;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

import org.jgrapht.EdgeFactory;
import org.jgrapht.alg.CycleDetector;

/**
 * Class used to represent a Directed Acyclic Graph
 * 
 * @author jpiat
 * 
 */
public class DirectedAcyclicGraph extends AbstractGraph<DAGVertex, DAGEdge>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3860891539321306793L;

	
	private final static String SDF = "sdf" ;
	
	/**
	 * Constructs a new DAG graph with the default Dag edge factory
	 */
	public DirectedAcyclicGraph() {
		super(new DAGEdgeFactory());
		this.getPropertyBean().setValue(AbstractGraph.MODEL, "dag");
	}

	/**
	 * Creates a new DirectedAcyclicGraph with the given Edge factory
	 * 
	 * @param arg0
	 *            The factory to use to create Edge in this graph
	 */
	public DirectedAcyclicGraph(EdgeFactory<DAGVertex, DAGEdge> arg0) {
		super(arg0);
		this.getPropertyBean().setValue(AbstractGraph.MODEL, "dag");
	}
	
	/**
	 * Add an Edge to this Graph
	 * 
	 * @param source
	 *            The source vertex of this edge
	 * @param target
	 *            The target vertex of this edge
	 * @return The created Edge
	 * @throws CreateMultigraphException
	 *             This Edge creates a Multi-graph
	 * @throws CreateCycleException
	 *             This Edge creates a cycle
	 */
	public DAGEdge addDAGEdge(DAGVertex source, DAGVertex target)
			throws CreateMultigraphException, CreateCycleException {
		if (this.getAllEdges(source, target).size() > 0) {
			throw (new CreateMultigraphException());
		} else {
			DAGEdge newEdge = addEdge(source, target);
			CycleDetector<DAGVertex, DAGEdge> detector = new CycleDetector<DAGVertex, DAGEdge>(
					this);
			if (detector.detectCyclesContainingVertex(source)) {
				this.removeEdge(newEdge);
				throw (new CreateCycleException());
			} else if (detector.detectCyclesContainingVertex(target)) {
				this.removeEdge(newEdge);
				throw (new CreateCycleException());
			}
			return newEdge;
		}
	}

	public DAGEdge addEdge(DAGVertex source, DAGVertex target) {
		DAGEdge edge = super.addEdge(source, target);
		return edge;
	}

	public boolean addVertex(DAGVertex vertex) {
		return super.addVertex(vertex);
	}


	/**
	 * Gives the DAGVertex with the given name in the graph
	 * 
	 * @param name
	 *            The name of the vertex we want to obtain
	 * @return The DAG vertex with the given name
	 */
	public DAGVertex getVertex(String name) {
		for (DAGVertex vertex : vertexSet()) {
			if (vertex.getName().equals(name)) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Test if this graph respect the DAG rules
	 * 
	 * @return trues if this graph is DAG compliant
	 */
	public boolean isDAG() {
		CycleDetector<DAGVertex, DAGEdge> detector = new CycleDetector<DAGVertex, DAGEdge>(
				this);
		return !detector.detectCycles();
	}

	@Override
	public void update(AbstractGraph<?, ?> observable, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public AbstractGraph clone() {
		return null;
	}

	@Override
	public boolean validateModel(Logger logger) throws SDF4JException {
		// TODO Auto-generated method stub
		return true;
	}
	
	public SDFGraph getCorrespondingSDFGraph(){
		return (SDFGraph) this.getPropertyBean().getValue(SDF) ;
	}
	
	public void setCorrespondingSDFGraph(SDFGraph graph){
		this.getPropertyBean().setValue(SDF, graph) ;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelVertexFactory getVertexFactory(){
		return DAGVertexFactory.getInstance();
	}

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

}
