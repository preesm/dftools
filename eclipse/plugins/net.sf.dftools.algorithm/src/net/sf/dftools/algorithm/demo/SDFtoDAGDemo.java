package net.sf.dftools.algorithm.demo;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.Vector;

import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;
import net.sf.dftools.algorithm.factories.DAGVertexFactory;
import net.sf.dftools.algorithm.generator.SDFRandomGraph;
import net.sf.dftools.algorithm.iterators.DAGIterator;
import net.sf.dftools.algorithm.model.dag.DAGEdge;
import net.sf.dftools.algorithm.model.dag.DAGVertex;
import net.sf.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import net.sf.dftools.algorithm.model.listenable.DAGListenableGraph;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.visitors.DAGTransformation;
import net.sf.dftools.algorithm.model.sdf.visitors.TopologyVisitor;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;

/**
 * Test class to test the translation of an SDF graph to a Directed Acyclic
 * Graph
 * 
 * @author pthebault
 * 
 */
public class SDFtoDAGDemo extends SDFAdapterDemo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381611894567369046L;

	/**
	 * applet as an application.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(String [] args){
		int nbVertex = 10, minInDegree = 1, maxInDegree = 3, minOutDegree = 1, maxOutDegree = 3;
		SDFAdapterDemo applet1 = new SDFAdapterDemo();
		SDFtoDAGDemo applet2 = new SDFtoDAGDemo();

		// Creates a random SDF graph
		int minrate = 1, maxrate = 15;
		try{
		SDFRandomGraph test = new SDFRandomGraph();
		SDFGraph demoGraph = test.createRandomGraph(nbVertex, minInDegree,
				maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate);
		//SDFGraph demoGraph =createTestComGraph();
		TopologyVisitor topo = new TopologyVisitor();
		try {
			demoGraph.accept(topo);
		} catch (SDF4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		applet1.init(demoGraph);
		
		
		
		DAGTransformation<DirectedAcyclicGraph> visitor = new DAGTransformation<DirectedAcyclicGraph>(new DirectedAcyclicGraph(), new DAGVertexFactory()) ;
		try {
			demoGraph.accept(visitor);
		} catch (SDF4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		applet2.init(visitor.getOutput());
		visitor.getOutput();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private DAGListenableGraph model;

	/**
	 * Initialize a created SDFAdpaterDemo with the given Graph to display
	 * 
	 * @param graphIn
	 *            The graph to display
	 */
	public void init(DirectedAcyclicGraph graphIn) {

		DirectedAcyclicGraph graph = (DirectedAcyclicGraph) graphIn.clone();
		// create a JGraphT graph
		model = new DAGListenableGraph();

		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<DAGVertex, DAGEdge>(model);

		JGraph jgraph = new JGraph(jgAdapter);

		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);
		System.out.println(" graph has " + graph.vertexSet().size()
				+ " vertice, including broadcast");
		for (DAGVertex vertex : graph.vertexSet()) {
			model.addVertex(vertex);
		}

		for (DAGEdge edge : graph.edgeSet()) {
			DAGEdge newEdge = model.addEdge(graph.getEdgeSource(edge), graph
					.getEdgeTarget(edge));
			for (String propertyKey : edge.getPropertyBean().keys()) {
				Object property = edge.getPropertyBean().getValue(propertyKey);
				newEdge.getPropertyBean().setValue(propertyKey, property);
			}
		}

		CycleDetector<DAGVertex, DAGEdge> detector = new CycleDetector<DAGVertex, DAGEdge>(
				model);
		GraphIterator<DAGVertex, DAGEdge> order;
		if (detector.detectCycles()) {
			order = new DAGIterator(model);
		} else {
			order = new TopologicalOrderIterator<DAGVertex, DAGEdge>(model);
		}

		Vector<DAGVertex> vertices = new Vector<DAGVertex>();
		int x = 0;
		int y = 100;
		int ymax = y;
		DAGVertex previousVertex = null;
		while (order.hasNext()) {
			DAGVertex nextVertex = order.next();
			vertices.add(nextVertex);
			if (previousVertex != null
					&& model.getEdge(nextVertex, previousVertex) == null
					&& model.getEdge(previousVertex, nextVertex) == null) {
				y += 50;
				this.positionVertexAt(nextVertex, x, y);
				if (y > ymax) {
					ymax = y;
				}
			} else {
				y = 100;
				x += 200;
				this.positionVertexAt(nextVertex, x, 100);
				previousVertex = nextVertex;
			}
		}

		JFrame frame = new JFrame();
		jgraph.setPreferredSize(new Dimension(x + 200, ymax + 300));
		frame.setContentPane(new ScrollPane());
		frame.getContentPane().add(this);
		frame.setTitle("DAG Transformation");
		if (adapters.size() == 1) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		}
		frame.pack();
		frame.setVisible(true);
	}

}
