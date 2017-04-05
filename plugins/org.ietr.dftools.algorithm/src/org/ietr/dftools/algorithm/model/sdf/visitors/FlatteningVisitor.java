package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

import org.ietr.dftools.algorithm.demo.SDFAdapterDemo;
import org.ietr.dftools.algorithm.importer.GMLSDFImporter;
import org.ietr.dftools.algorithm.importer.InvalidModelException;
import org.ietr.dftools.algorithm.model.parameters.Argument;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.parameters.Parameter;
import org.ietr.dftools.algorithm.model.parameters.Variable;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;

/**
 * Visitor used to flatten the hierarchy of a graph
 * 
 * @author jpiat
 * 
 */
public class FlatteningVisitor implements
		IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	/**
	 * Creates a known graph
	 * 
	 * @return The created test_com graph
	 * 
	 */
	public static SDFGraph createTestComGraph() {

		SDFGraph graph = new SDFGraph();

		// test_com_basique
		SDFInterfaceVertex sensorInt = new SDFSourceInterfaceVertex();
		sensorInt.setName("sensor_Int");
		graph.addVertex(sensorInt);

		SDFVertex gen5 = new SDFVertex();
		gen5.setName("Gen5");
		graph.addVertex(gen5);

		SDFVertex recopie5 = new SDFVertex();
		recopie5.setName("recopie_5");
		graph.addVertex(recopie5);

		SDFInterfaceVertex acqData = new SDFSinkInterfaceVertex();
		acqData.setName("acq_data");
		graph.addVertex(acqData);

		// hierachy ...
		SDFGraph subGraph = new SDFGraph();
		subGraph.setName("gen_5_Sub");

		SDFInterfaceVertex add = new SDFSourceInterfaceVertex();
		add.setName("Add");

		SDFVertex gen_sub1 = new SDFVertex();
		gen_sub1.setName("gen_sub1");
		subGraph.addVertex(gen_sub1);

		SDFVertex gen_sub2 = new SDFVertex();
		gen_sub2.setName("gen_sub2");
		subGraph.addVertex(gen_sub2);

		SDFInterfaceVertex times = new SDFSinkInterfaceVertex();
		times.setName("Times");

		gen5.setGraphDescription(subGraph);
		gen5.addArgument(new Argument("NB_COPY", "100"));
		gen5.addSink(times);
		gen5.addSource(add);

		SDFEdge intern1 = subGraph.addEdge(add, gen_sub1);
		intern1.setProd(new SDFIntEdgePropertyType(1));
		intern1.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge intern2 = subGraph.addEdge(gen_sub2, times);
		intern2.setProd(new SDFIntEdgePropertyType(1));
		intern2.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge intern3 = subGraph.addEdge(gen_sub1, gen_sub2);
		intern3.setProd(new SDFIntEdgePropertyType(1));
		intern3.setCons(new SDFIntEdgePropertyType(1));

		// end of hierachy
		SDFEdge sensGen = graph.addEdge(sensorInt, gen5);
		sensGen.setTargetInterface(add);
		sensGen.setProd(new SDFIntEdgePropertyType(1));
		sensGen.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge genRec = graph.addEdge(gen5, recopie5);
		genRec.setSourceInterface(times);
		genRec.setProd(new SDFIntEdgePropertyType(2));
		genRec.setCons(new SDFIntEdgePropertyType(3));

		SDFEdge genAcq = graph.addEdge(gen5, acqData);
		genAcq.setSourceInterface(times);
		genAcq.setProd(new SDFIntEdgePropertyType(1));
		genAcq.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge recAcq = graph.addEdgeWithInterfaces(recopie5, acqData);
		recAcq.setProd(new SDFIntEdgePropertyType(3));
		recAcq.setCons(new SDFIntEdgePropertyType(2));

		graph.addParameter(new Parameter("SIZE"));
		graph.addParameter(new Parameter("NB_COPY"));

		graph.addVariable(new Variable("a", "5"));
		graph.addVariable(new Variable("b", "10"));

		return graph;
	}

	/**
	 * Main method for debug purposes
	 * 
	 * @param args
	 * @throws InvalidExpressionException
	 */
	public static void main(String[] args) throws InvalidExpressionException {
		SDFAdapterDemo applet = new SDFAdapterDemo();
		SDFAdapterDemo applet2 = new SDFAdapterDemo();
		GMLSDFImporter importer = new GMLSDFImporter();
		// SDFGraph demoGraph = createTestComGraph();
		SDFGraph demoGraph;
		try {
			demoGraph = importer.parse(new File(
					"D:\\IDCT2D\\idct2dCadOptim.xml"));
			FlatteningVisitor visitor = new FlatteningVisitor();
			demoGraph.accept(visitor);
			applet2.init(demoGraph);
			applet.init(visitor.getOutput());
		} catch (InvalidModelException | FileNotFoundException | SDF4JException e) {
			e.printStackTrace();
		}
	}

	private SDFGraph output;

	/**
	 * Gives this visitor output (The flattened graph)
	 * 
	 * @return The output of the visitor
	 */
	public SDFGraph getOutput() {
		return output;
	}

	/**
	 * Flatten one vertex given it's parent
	 * 
	 * @param vertex
	 *            The vertex to flatten
	 * @param parentGraph
	 *            The new parent graph
	 */
	@SuppressWarnings("unchecked")
	private void treatVertex(SDFAbstractVertex vertex, SDFGraph parentGraph) {
		Vector<SDFAbstractVertex> vertices = new Vector<SDFAbstractVertex>(
				vertex.getGraphDescription().vertexSet());
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getGraphDescription() != null) {
				treatVertex(vertices.get(i),
						(SDFGraph) vertex.getGraphDescription());
				vertex.getGraphDescription().removeVertex(vertices.get(i));
			}
		}
		vertices = new Vector<SDFAbstractVertex>(vertex.getGraphDescription()
				.vertexSet());
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getGraphDescription() == null) {
				parentGraph.addVertex(vertices.get(i));
			}
		}
		Vector<SDFEdge> edges = new Vector<SDFEdge>(vertex
				.getGraphDescription().edgeSet());
		for (int i = 0; i < edges.size(); i++) {
			SDFAbstractVertex sourceVertex;
			SDFAbstractVertex targetVertex;
			if (edges.get(i).getSource() instanceof SDFInterfaceVertex) {
				SDFInterfaceVertex sourceInterface = (SDFInterfaceVertex) edges
						.get(i).getSource();
				sourceVertex = vertex.getAssociatedEdge(sourceInterface)
						.getSource();
				edges.get(i).setSourceInterface(
						vertex.getAssociatedEdge(sourceInterface)
								.getSourceInterface());
			} else {
				sourceVertex = edges.get(i).getSource();
			}
			if (edges.get(i).getTarget() instanceof SDFInterfaceVertex) {
				SDFInterfaceVertex targetInterface = (SDFInterfaceVertex) edges
						.get(i).getTarget();
				targetVertex = vertex.getAssociatedEdge(targetInterface)
						.getTarget();
				edges.get(i).setTargetInterface(
						vertex.getAssociatedEdge(targetInterface)
								.getTargetInterface());
			} else {
				targetVertex = edges.get(i).getTarget();
			}
			SDFEdge newEdge = parentGraph.addEdge(sourceVertex, targetVertex);
			for (String key : edges.get(i).getPropertyBean().keys()) {
				newEdge.getPropertyBean().setValue(key,
						edges.get(i).getPropertyBean().getValue(key));
			}
		}
	}

	@Override
	public void visit(SDFEdge sdfEdge) {

	}

	@Override
	public void visit(SDFGraph sdf) throws SDF4JException {
		output = sdf.clone();
		TopologyVisitor schedulability = new TopologyVisitor();
		output.accept(schedulability);
		if (!output.isSchedulable()) {
			return;
		}
		Vector<SDFAbstractVertex> vertices = new Vector<SDFAbstractVertex>(
				output.vertexSet());
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getGraphDescription() != null) {
				treatVertex(vertices.get(i), output);
				output.removeVertex(vertices.get(i));
			}
		}
		Vector<SDFEdge> edges = new Vector<SDFEdge>(output.edgeSet());
		for (int i = 0; i < edges.size(); i++) {
			SDFEdge edge = edges.get(i);
			try {
				if (edge.getCons().intValue() == 0
						|| edge.getProd().intValue() == 0) {
					output.removeEdge(edge);
				}
			} catch (InvalidExpressionException e) {
				e.printStackTrace();
				throw (new SDF4JException(e.getMessage()));
			}
		}
	}

	@Override
	public void visit(SDFAbstractVertex sdfVertex) throws SDF4JException {

	}

}
