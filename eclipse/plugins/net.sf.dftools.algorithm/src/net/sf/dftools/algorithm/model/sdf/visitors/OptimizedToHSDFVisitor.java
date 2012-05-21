package net.sf.dftools.algorithm.model.sdf.visitors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import net.sf.dftools.algorithm.importer.GMLSDFImporter;
import net.sf.dftools.algorithm.importer.InvalidModelException;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.SDFVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFBroadcastVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFForkVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFJoinVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFRoundBufferVertex;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.visitors.IGraphVisitor;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;
import net.sf.dftools.algorithm.model.visitors.VisitorOutput;

/**
 * Visitor used to transform an SDF into a single-rate SDF (for all edges :
 * prod = cons)
 * 
 * @author jpiat
 * 
 */
public class OptimizedToHSDFVisitor implements
		IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	/**
	 * Test the visitor
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// SDFAdapterDemo applet1 = new SDFAdapterDemo();
		// SDFAdapterDemo applet2 = new SDFAdapterDemo();
		// SDFAdapterDemo applet3 = new SDFAdapterDemo();
		// SDFtoDAGDemo applet2 = new SDFtoDAGDemo();
		GMLSDFImporter importer = new GMLSDFImporter();
		// SDFGraph demoGraph = createTestComGraph();
		SDFGraph demoGraph;
		try {

			/*
			 * demoGraph = importer.parse(new File(
			 * "D:\\Preesm\\trunk\\tests\\IDCT2D\\idct2dCadOptim.graphml"));
			 */
			/*
			 * demoGraph = importer.parse(new File(
			 * "D:\\Preesm\\trunk\\tests\\UMTS\\Tx_UMTS.graphml"));
			 */
			/*
			 * demoGraph = importer.parse(new File(
			 * "D:\\Preesm\\trunk\\tests\\Confidential\\Usecase9_UL.graphml"));
			 */
			demoGraph = importer
					.parse(new File(
							"D:\\Preesm\\trunk\\tests\\RACH_Hierarchy\\RACH_Hierarchy\\Algo\\rach_hierarchy_top.graphml"));
			SDFHierarchyFlattening visitor = new SDFHierarchyFlattening();
			// DAGTransformation<DirectedAcyclicGraph> dageur = new
			// DAGTransformation<DirectedAcyclicGraph>(
			// new DirectedAcyclicGraph(), new DAGVertexFactory());
			visitor.flattenGraph(demoGraph, 2);
			OptimizedToHSDFVisitor hsdf1 = new OptimizedToHSDFVisitor();
			visitor.getOutput().accept(hsdf1);
			ToHSDFVisitor hsdf2 = new ToHSDFVisitor();
			visitor.getOutput().accept(hsdf2);
			// applet1.init(visitor.getOutput());
			// SDFGraph dag = visitor.getOutput().clone();
			// dag.accept(dageur);
			// applet2.init(dageur.getOutput());
			// GMLSDFExporter exporter = new GMLSDFExporter() ;
			// exporter.export(visitor.getOutput(),
			// "D:\\Preesm\\trunk\\tests\\RACH_Hierarchy\\RACH_Hierarchy\\testFlatten.graphml"
			// );
			// applet1.init(demoGraph);
			// applet2.init(hsdf1.getOutput());
			// applet3.init(hsdf2.getOutput());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SDF4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private SDFGraph outputGraph;

	/**
	 * GIves this visitor output
	 * 
	 * @return The output of the visitor
	 */
	public SDFGraph getOutput() {
		return outputGraph;
	}

	private void linkVerticesTop(SDFGraph sdf,
			HashMap<SDFAbstractVertex, Vector<SDFAbstractVertex>> matchCopies,
			SDFGraph output, List<SDFEdge> edges)
			throws InvalidExpressionException {

		for (SDFEdge edge : edges) {
			sdf.getEdgeSource(edge);
			sdf.getEdgeTarget(edge);
			SDFInterfaceVertex inputVertex = null;
			SDFInterfaceVertex outputVertex = null;
			Vector<SDFAbstractVertex> sourceCopies = matchCopies.get(sdf
					.getEdgeSource(edge));
			Vector<SDFAbstractVertex> targetCopies = matchCopies.get(sdf
					.getEdgeTarget(edge));
			int nbDelays = edge.getDelay().intValue();
			int totProd = 0;
			int sourceProd = 0;
			int targetCons = 0;
			int targetIndex = (nbDelays / edge.getCons().intValue())
					% targetCopies.size(), sourceIndex = 0;
			int rest = Math.min(edge.getProd().intValue(), edge.getCons()
					.intValue());
			int startIndex = targetIndex;
			while (totProd < (edge.getCons().intValue() * targetCopies.size())) {
				// testing this block for inserting explode and implode vertices
				if (rest < edge.getProd().intValue()
						&& !(sourceCopies.get(sourceIndex) instanceof SDFForkVertex)
						&& !(sourceCopies.get(sourceIndex) instanceof SDFBroadcastVertex)) {
					SDFAbstractVertex explodeVertex = new SDFForkVertex();
					output.addVertex(explodeVertex);
					SDFAbstractVertex originVertex = (SDFAbstractVertex) sourceCopies
							.get(sourceIndex);
					explodeVertex.setName("explode_" + originVertex.getName()
							+ "_" + edge.getSourceInterface().getName());
					sourceCopies.set(sourceIndex, explodeVertex);
					SDFEdge newEdge = output.addEdge(originVertex,
							explodeVertex);
					newEdge.setDelay(new SDFIntEdgePropertyType(0));
					newEdge.setProd(new SDFIntEdgePropertyType(edge.getProd()
							.intValue()));
					newEdge.setCons(new SDFIntEdgePropertyType(edge.getProd()
							.intValue()));
					newEdge.setDataType(edge.getDataType());
					newEdge.setSourceInterface(edge.getSourceInterface());
					newEdge.setTargetInterface(edge.getTargetInterface());
				}
				if (rest < edge.getCons().intValue()
						&& !(targetCopies.get(targetIndex) instanceof SDFJoinVertex)
						&& !(targetCopies.get(targetIndex) instanceof SDFRoundBufferVertex)) {
					SDFAbstractVertex implodeVertex = new SDFJoinVertex();
					output.addVertex(implodeVertex);
					SDFAbstractVertex originVertex = (SDFAbstractVertex) targetCopies
							.get(targetIndex);
					implodeVertex.setName("implode_" + originVertex.getName()
							+ "_" + edge.getTargetInterface().getName());
					targetCopies.set(targetIndex, implodeVertex);
					SDFEdge newEdge = output.addEdge(implodeVertex,
							originVertex);
					newEdge.setDelay(new SDFIntEdgePropertyType(0));
					newEdge.setProd(new SDFIntEdgePropertyType(edge.getCons()
							.intValue()));
					newEdge.setCons(new SDFIntEdgePropertyType(edge.getCons()
							.intValue()));
					newEdge.setDataType(edge.getDataType());
					newEdge.setSourceInterface(edge.getSourceInterface());
					newEdge.setTargetInterface(edge.getTargetInterface());
				}
				// end of testing zone
				SDFEdge newEdge = output.addEdge(sourceCopies.get(sourceIndex),
						targetCopies.get(targetIndex));
				if (sourceCopies.get(sourceIndex).getSink(
						edge.getSourceInterface().getName()) != null) {
					newEdge.setSourceInterface(sourceCopies.get(sourceIndex)
							.getSink(edge.getSourceInterface().getName()));
				} else {
					newEdge.setSourceInterface(edge.getSourceInterface()
							.clone());
				}
				if (targetCopies.get(targetIndex).getSource(
						edge.getTargetInterface().getName()) != null) {
					newEdge.setTargetInterface(targetCopies.get(targetIndex)
							.getSource(edge.getTargetInterface().getName()));
				} else {
					newEdge.setTargetInterface(edge.getTargetInterface()
							.clone());
				}
				newEdge.setTargetInterface(edge.getTargetInterface().clone());
				if (targetCopies.get(targetIndex) instanceof SDFVertex) {
					if (((SDFVertex) targetCopies.get(targetIndex))
							.getAssociatedInterface(edge) != null) {
						inputVertex = ((SDFVertex) targetCopies
								.get(targetIndex)).getAssociatedInterface(edge);
						((SDFVertex) targetCopies.get(targetIndex))
								.setInterfaceVertexExternalLink(newEdge,
										inputVertex);
					}
				}
				if (sourceCopies.get(sourceIndex) instanceof SDFVertex) {
					if (((SDFVertex) sourceCopies.get(sourceIndex))
							.getAssociatedInterface(edge) != null) {
						outputVertex = ((SDFVertex) sourceCopies
								.get(sourceIndex)).getAssociatedInterface(edge);
						((SDFVertex) sourceCopies.get(sourceIndex))
								.setInterfaceVertexExternalLink(newEdge,
										outputVertex);
					}
				}
				newEdge.copyProperties(edge);
				newEdge.setProd(new SDFIntEdgePropertyType(rest));
				newEdge.setCons(new SDFIntEdgePropertyType(rest));
				if ((targetIndex == 0 || targetIndex < startIndex)
						&& nbDelays > 0) {
					newEdge.setDelay(new SDFIntEdgePropertyType(edge.getCons()
							.intValue()));
					nbDelays = nbDelays - edge.getCons().intValue();
				} else {
					newEdge.setDelay(new SDFIntEdgePropertyType(0));
				}
				sourceProd += rest;
				targetCons += rest;
				totProd += rest;
				if (sourceProd == edge.getProd().intValue()
						&& targetCons == edge.getCons().intValue()) {
					sourceIndex = (sourceIndex + 1) % sourceCopies.size();
					sourceProd = 0;
					targetIndex = (targetIndex + 1) % targetCopies.size();
					targetCons = 0;
					rest = Math.min(edge.getProd().intValue(), edge.getCons()
							.intValue());
				} else if (sourceProd == edge.getProd().intValue()) {
					sourceIndex = (sourceIndex + 1) % sourceCopies.size();
					sourceProd = 0;
					rest = Math.min(edge.getCons().intValue() - targetCons,
							edge.getProd().intValue());
				} else if (targetCons == edge.getCons().intValue()) {
					targetIndex = (targetIndex + 1) % targetCopies.size();
					targetCons = 0;
					rest = Math.min(edge.getProd().intValue() - sourceProd,
							edge.getCons().intValue());
				}
				// next line should manage extended hierarchy interpretation
				if ((totProd == (edge.getCons().intValue() * targetCopies
						.size()))
						&& targetCopies.get(0) instanceof SDFInterfaceVertex
						&& sourceIndex < sourceCopies.size()) {
					totProd = 0;
				}

			}
			for (int i = 0; i < sourceCopies.size(); i++) {
				if (sourceCopies.get(i) instanceof SDFForkVertex
						&& !sourceCopies.get(i).equals(edge.getSource())) {
					SDFAbstractVertex trueSource = null;
					for (SDFEdge inEdge : output.incomingEdgesOf(sourceCopies
							.get(i))) {
						trueSource = inEdge.getSource();
					}
					sourceCopies.set(i, trueSource);
				}
			}
			for (int i = 0; i < targetCopies.size(); i++) {
				if (targetCopies.get(i) instanceof SDFJoinVertex
						&& !targetCopies.get(i).equals(edge.getTarget())) {
					SDFAbstractVertex trueTarget = null;
					for (SDFEdge inEdge : output.outgoingEdgesOf(targetCopies
							.get(i))) {
						trueTarget = inEdge.getTarget();
					}
					targetCopies.set(i, trueTarget);
				}
			}
		}
	}

	private void transformsTop(SDFGraph graph, SDFGraph output)
			throws InvalidExpressionException, SDF4JException {
		HashMap<SDFAbstractVertex, Vector<SDFAbstractVertex>> matchCopies = new HashMap<SDFAbstractVertex, Vector<SDFAbstractVertex>>();
		List<SDFEdge> deletedEdges = new ArrayList<SDFEdge>();
		if (graph.isSchedulable()) {
			for (SDFAbstractVertex vertex : graph.vertexSet()) {
				Vector<SDFAbstractVertex> copies = new Vector<SDFAbstractVertex>();
				matchCopies.put(vertex, copies);
				if (vertex instanceof SDFInterfaceVertex) {
					copies.add(output.getVertex(vertex.getName()));
				} else if (vertex.getNbRepeatAsInteger() == 1) {
					copies.add(output.getVertex(vertex.getName()));
				} else {
					VisitorOutput.getLogger().log(Level.INFO,
							vertex.getName() + " x" + vertex.getNbRepeat());
					SDFAbstractVertex existingVertex = output.getVertex(vertex
							.getName());
					for (SDFEdge toDelete : graph.incomingEdgesOf(vertex)) {
						if (!deletedEdges.contains(toDelete)) {
							deletedEdges.add(toDelete);
						}
					}
					for (SDFEdge toDelete : graph.outgoingEdgesOf(vertex)) {
						if (!deletedEdges.contains(toDelete)) {
							deletedEdges.add(toDelete);
						}
					}
					output.removeVertex(existingVertex);
					for (int i = 0; i < vertex.getNbRepeatAsInteger(); i++) {
						SDFAbstractVertex copy = ((SDFAbstractVertex) vertex)
								.clone();
						copy.setName(copy.getName() + "_" + i);
						copy.setNbRepeat(1);
						output.addVertex(copy);
						copies.add(copy);
					}

				}
			}
			linkVerticesTop(graph, matchCopies, output, deletedEdges);
			output.getPropertyBean().setValue("schedulable", true);

		} else {
			VisitorOutput.getLogger().log(Level.SEVERE,
					"graph " + graph.getName() + " is not schedulable");
		}
	}

	public void visit(SDFEdge sdfEdge) {
	}

	public void visit(SDFGraph sdf) throws SDF4JException {
		outputGraph = sdf.clone();
		boolean isHSDF = true;
		try {
			for (SDFAbstractVertex vertex : outputGraph.vertexSet()) {
				if (vertex instanceof SDFVertex && vertex.getNbRepeatAsInteger() > 1) {
					isHSDF = false;
					break;
				}
			}
			if (isHSDF) {
				return;
			}
			ArrayList<SDFAbstractVertex> vertices = new ArrayList<SDFAbstractVertex>(
					sdf.vertexSet());
			for (int i = 0; i < vertices.size(); i++) {
				if (vertices.get(i) instanceof SDFVertex) {
					vertices.get(i).accept(this);
				}
			}
			transformsTop(sdf, outputGraph);
			manageExplodeImplodePattern(outputGraph);
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw (new SDF4JException(e.getMessage()));
		}

	}

	public void visit(SDFAbstractVertex sdfVertex) throws SDF4JException {
		/*
		 * if(sdfVertex.getGraphDescription() != null){
		 * sdfVertex.getGraphDescription().accept(this); }
		 */
	}

	protected void manageExplodeImplodePattern(SDFGraph graph)
			throws InvalidExpressionException {

		List<SDFEdge> edges = new ArrayList<SDFEdge>(graph.edgeSet());
		for (int i = 0; i < edges.size(); i++) {
			SDFEdge edge = edges.get(i);
			if (edge.getSource() instanceof SDFJoinVertex
					&& edge.getTarget() instanceof SDFForkVertex) {
				SDFJoinVertex joinVertex = (SDFJoinVertex) edge.getSource();
				SDFForkVertex forkVertex = (SDFForkVertex) edge.getTarget();
				List<SDFAbstractVertex> targetVertices = new ArrayList<SDFAbstractVertex>();
				List<SDFAbstractVertex> sourceVertices = new ArrayList<SDFAbstractVertex>();
				for (SDFEdge inEdge : joinVertex.getIncomingConnections()) {
					if (graph.vertexSet().contains(inEdge.getSource())) {
						sourceVertices.add(inEdge.getSource());
					}
				}
				for (SDFEdge outEdge : forkVertex.getOutgoingConnections()) {
					if (graph.vertexSet().contains(outEdge.getTarget())) {
						targetVertices.add(outEdge.getTarget());
					}
				}
				if (sourceVertices.size() == targetVertices.size()) {
					int inc = 0;
					for (SDFAbstractVertex vertex : sourceVertices) {
						SDFEdge newEdge = graph.addEdge(vertex, targetVertices
								.get(inc));
						newEdge.copyProperties(joinVertex
								.getIncomingConnections().get(inc));
						if (edge.getDelay().intValue() > 0) {
							edge.setDelay(new SDFIntEdgePropertyType(newEdge
									.getProd().intValue()));
						}
						inc++;
					}
					graph.removeVertex(joinVertex);
					graph.removeVertex(forkVertex);
				}
			}
		}

	}

}