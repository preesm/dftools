package net.sf.dftools.algorithm.model.sdf.visitors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

import net.sf.dftools.algorithm.demo.SDFAdapterDemo;
import net.sf.dftools.algorithm.demo.SDFtoDAGDemo;
import net.sf.dftools.algorithm.factories.DAGVertexFactory;
import net.sf.dftools.algorithm.importer.GMLSDFImporter;
import net.sf.dftools.algorithm.importer.InvalidModelException;
import net.sf.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.SDFVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFForkVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFJoinVertex;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.visitors.IGraphVisitor;
import net.sf.dftools.algorithm.model.visitors.SDF4JException;
import net.sf.dftools.algorithm.model.visitors.VisitorOutput;

import org.jgrapht.alg.CycleDetector;

/**
 * Visitor used to transform an SDF into an Homogeneous SDF (for all edges :
 * prod = cons)
 * 
 * @author jpiat
 * 
 */
public class SDFHierarchyInstanciation implements
		IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	private SDFGraph outputGraph;

	
	/**
	 * GIves this visitor output
	 * 
	 * @return The output of the visitor
	 */
	public SDFGraph getOutput() {
		return outputGraph;
	}

	private void createEdge(SDFGraph sdf, SDFGraph output, SDFEdge edge,
			Vector<SDFAbstractVertex> sourceCopies,
			Vector<SDFAbstractVertex> targetCopies, int targetIndex, int sourceIndex, int rest, int nbDelays) throws InvalidExpressionException{
		SDFEdge newEdge = null;
		SDFInterfaceVertex inputVertex = null;
		SDFInterfaceVertex outputVertex = null;
		if(output.getAllEdges(sourceCopies.get(sourceIndex), targetCopies.get(targetIndex)) != null){
			for(SDFEdge existingEdge : output.getAllEdges(sourceCopies.get(sourceIndex), targetCopies.get(targetIndex))){
				if(existingEdge.getSourceInterface().equals(edge.getSourceInterface()) && existingEdge.getTargetInterface().equals(edge.getTargetInterface())){
					newEdge = existingEdge ;
					if(sourceCopies.size() == 1){
						newEdge.setProd(new SDFIntEdgePropertyType(edge.getProd().intValue()));
					}else{
						newEdge.setProd(new SDFIntEdgePropertyType(newEdge.getProd().intValue()+rest)) ;
					}
					if(targetCopies.size() == 1){
						newEdge.setCons(new SDFIntEdgePropertyType(edge.getCons().intValue()));
					}else{
						newEdge.setCons(new SDFIntEdgePropertyType(newEdge.getCons().intValue()+rest)) ;
					}
				}
			}
		}
		if(newEdge == null){
			newEdge = output.addEdge(sourceCopies.get(sourceIndex),
					targetCopies.get(targetIndex));
			newEdge.copyProperties(edge);
			newEdge.setProd(new SDFIntEdgePropertyType(rest));
			newEdge.setCons(new SDFIntEdgePropertyType(rest));
		}
		if (sourceCopies.get(sourceIndex).getSink(
				edge.getSourceInterface().getName()) != null) {
			newEdge.setSourceInterface(sourceCopies.get(sourceIndex)
					.getSink(edge.getSourceInterface().getName()));
		} else {
			newEdge.setSourceInterface(edge.getSourceInterface().clone());
		}
		if (targetCopies.get(targetIndex).getSource(
				edge.getTargetInterface().getName()) != null) {
			newEdge.setTargetInterface(targetCopies.get(targetIndex)
					.getSource(edge.getTargetInterface().getName()));
		} else {
			newEdge.setTargetInterface(edge.getTargetInterface().clone());
		}
		newEdge.setTargetInterface(edge.getTargetInterface().clone());
		if (targetCopies.get(targetIndex) instanceof SDFVertex) {
			if (((SDFVertex) targetCopies.get(targetIndex))
					.getAssociatedInterface(edge) != null) {
				inputVertex = ((SDFVertex) targetCopies.get(targetIndex))
						.getAssociatedInterface(edge);
				((SDFVertex) targetCopies.get(targetIndex))
						.setInterfaceVertexExternalLink(newEdge,
								inputVertex);
			}
		}
		if (sourceCopies.get(sourceIndex) instanceof SDFVertex) {
			if (((SDFVertex) sourceCopies.get(sourceIndex))
					.getAssociatedInterface(edge) != null) {
				outputVertex = ((SDFVertex) sourceCopies.get(sourceIndex))
						.getAssociatedInterface(edge);
				((SDFVertex) sourceCopies.get(sourceIndex))
						.setInterfaceVertexExternalLink(newEdge,
								outputVertex);
			}
		}
		if (targetIndex == 0 && nbDelays > 0) {
			newEdge.setDelay(new SDFIntEdgePropertyType(edge.getDelay().intValue()));
		}else{
			newEdge.setDelay(new SDFIntEdgePropertyType(0));
		}
	}
	private void linkRepetitions(SDFGraph sdf, SDFGraph output, SDFEdge edge,
			Vector<SDFAbstractVertex> sourceCopies,
			Vector<SDFAbstractVertex> targetCopies) throws InvalidExpressionException {
		sdf.getEdgeSource(edge);
		sdf.getEdgeTarget(edge);
		int nbDelays = edge.getDelay().intValue();
		int totProd = 0;
		int sourceProd = 0;
		int targetCons = 0;
		int targetIndex = (nbDelays / edge.getCons().intValue())
				% targetCopies.size(), sourceIndex = 0;
		int rest = Math.min(edge.getProd().intValue(), edge.getCons()
				.intValue());
		while (totProd < (edge.getCons().intValue() * edge.getTarget().getNbRepeatAsInteger())) {
			// testing this block for inserting explode and implode vertices
			if ((rest < (edge.getProd().intValue() * (edge.getSource().getNbRepeatAsInteger()/sourceCopies.size())))
					&& !(sourceCopies.get(sourceIndex) instanceof SDFForkVertex)
					/*&& !(sourceCopies.get(sourceIndex) instanceof SDFBroadcastVertex)*/) {
				SDFAbstractVertex explodeVertex = new SDFForkVertex();
				
				output.addVertex(explodeVertex);
				SDFAbstractVertex originVertex = (SDFAbstractVertex) sourceCopies
						.get(sourceIndex);
				explodeVertex.setName("explode_"+originVertex.getName()+"_"+edge.getSourceInterface().getName());
				sourceCopies.set(sourceIndex, explodeVertex);
				SDFEdge newEdge = output.addEdge(originVertex, explodeVertex);
				newEdge.copyProperties(edge);
				newEdge.setDelay(new SDFIntEdgePropertyType(0));
				newEdge.setProd(new SDFIntEdgePropertyType(edge.getProd()
						.intValue()));
				newEdge.setCons(new SDFIntEdgePropertyType(edge.getProd().intValue()));
				newEdge.setDataType(edge.getDataType());
				newEdge.setSourceInterface(edge.getSourceInterface());
				newEdge.setTargetInterface(edge.getTargetInterface());
			}
			if ((rest < (edge.getCons().intValue() * (edge.getTarget().getNbRepeatAsInteger()/targetCopies.size())) )
					&& !(targetCopies.get(targetIndex) instanceof SDFJoinVertex)) {
				SDFAbstractVertex implodeVertex = new SDFJoinVertex();
				output.addVertex(implodeVertex);
				SDFAbstractVertex originVertex = (SDFAbstractVertex) targetCopies
						.get(targetIndex);
				implodeVertex.setName("implode_"+originVertex.getName()+"_"+edge.getTargetInterface().getName());
				targetCopies.set(targetIndex, implodeVertex);
				SDFEdge newEdge = output.addEdge(implodeVertex, originVertex);
				newEdge.copyProperties(edge);
				newEdge.setDelay(new SDFIntEdgePropertyType(0));
				newEdge.setProd(new SDFIntEdgePropertyType((edge.getCons()
						.intValue()/targetCopies.size()) * sourceCopies.size()));
				newEdge.setCons(new SDFIntEdgePropertyType(edge.getCons()
						.intValue()));
				newEdge.setDataType(edge.getDataType());
				newEdge.setSourceInterface(edge.getSourceInterface());
				newEdge.setTargetInterface(edge.getTargetInterface());
			}
			// end of testing zone
			createEdge(sdf, output, edge,
					sourceCopies,
					targetCopies, targetIndex,sourceIndex, rest,nbDelays);
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
				rest = Math.min(edge.getCons().intValue() - targetCons, edge
						.getProd().intValue());
			} else if (targetCons == edge.getCons().intValue()) {
				targetIndex = (targetIndex + 1) % targetCopies.size();
				targetCons = 0;
				rest = Math.min(edge.getProd().intValue() - sourceProd, edge
						.getCons().intValue());
			}
			// next line should manage extended hierarchy interpretation
			if ((totProd == (edge.getCons().intValue() * targetCopies.size()))
					&& targetCopies.get(0) instanceof SDFInterfaceVertex
					&& sourceIndex < sourceCopies.size()) {
				totProd = 0;
			}
		}
	}

	private void linkVerticesTop(SDFGraph sdf,
			HashMap<SDFAbstractVertex, Vector<SDFAbstractVertex>> matchCopies,
			SDFGraph output) throws InvalidExpressionException {
		for (SDFEdge edge : sdf.edgeSet()) {
			Vector<SDFAbstractVertex> sourceCopies = matchCopies.get(sdf
					.getEdgeSource(edge));
			Vector<SDFAbstractVertex> targetCopies = matchCopies.get(sdf
					.getEdgeTarget(edge));
			linkRepetitions(sdf, output, edge, sourceCopies, targetCopies);
			for (int i = 0; i < sourceCopies.size(); i++) {
				if (sourceCopies.get(i) instanceof SDFForkVertex && sdf.getVertex(sourceCopies.get(i).getName()) == null) {
					SDFAbstractVertex trueSource = null;
					for (SDFEdge inEdge : output.incomingEdgesOf(sourceCopies
							.get(i))) {
						trueSource = inEdge.getSource();
					}
					sourceCopies.set(i, trueSource);
				}
			}
			for (int i = 0; i < targetCopies.size(); i++) {
				if (targetCopies.get(i) instanceof SDFJoinVertex && sdf.getVertex(targetCopies.get(i).getName()) == null) {
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

	private void transformsTop(SDFGraph graph, SDFGraph output) throws InvalidExpressionException, SDF4JException {
		HashMap<SDFAbstractVertex, Vector<SDFAbstractVertex>> matchCopies = new HashMap<SDFAbstractVertex, Vector<SDFAbstractVertex>>();
		CycleDetector<SDFAbstractVertex, SDFEdge> detector = new CycleDetector<SDFAbstractVertex, SDFEdge>(
				graph);
		List<SDFAbstractVertex> needToBeRepeated = new ArrayList<SDFAbstractVertex>() ;
		List<SDFAbstractVertex> conccurentList = new ArrayList<SDFAbstractVertex>(graph.vertexSet()) ;
		for (SDFAbstractVertex vertex : conccurentList) {
			if(vertex.getGraphDescription() != null){
				Set<SDFAbstractVertex> cycle = detector.findCyclesContainingVertex(vertex);
				needToBeRepeated.addAll(cycle);
			}
		}
		if (graph.isSchedulable()) {
			for (SDFAbstractVertex vertex : graph.vertexSet()) {
				Vector<SDFAbstractVertex> copies = new Vector<SDFAbstractVertex>();
				matchCopies.put(vertex, copies);
				if (vertex instanceof SDFInterfaceVertex) {
					SDFAbstractVertex copy = ((SDFAbstractVertex) vertex)
							.clone();
					copies.add(copy);
					output.addVertex(copy);
				} else if (vertex.getGraphDescription() != null) {
					for (int i = 0; i < vertex.getNbRepeatAsInteger(); i++) {
						SDFAbstractVertex copy = ((SDFAbstractVertex) vertex)
								.clone();
						copy.setName(copy.getName() + "_" + i);
						copy.setNbRepeat(1);
						output.addVertex(copy);
						copies.add(copy);
					}
				}else if (needToBeRepeated.contains(vertex)) {
					for (int i = 0; i < vertex.getNbRepeatAsInteger(); i++) {
						SDFAbstractVertex copy = ((SDFAbstractVertex) vertex)
								.clone();
						copy.setName(copy.getName() + "_" + i);
						copy.setNbRepeat(1);
						output.addVertex(copy);
						copies.add(copy);
					}
				}else {
					SDFAbstractVertex copy = ((SDFAbstractVertex) vertex)
							.clone();
					output.addVertex(copy);
					copies.add(copy);
				}
			}
			linkVerticesTop(graph, matchCopies, output);
			output.getPropertyBean().setValue("schedulable", true);
		} else {
			VisitorOutput.getLogger().log(Level.SEVERE, "graph "+graph.getName()+" is not schedulable");
		}
	}

	public void visit(SDFEdge sdfEdge) {
	}

	public void visit(SDFGraph sdf) throws SDF4JException {
		outputGraph = sdf.clone();
		outputGraph.clean();
		try {
			transformsTop(sdf, outputGraph);
		} catch (InvalidExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw(new SDF4JException(e.getMessage()));
		}
	}

	public void visit(SDFAbstractVertex sdfVertex) {
		/*
		 * if(sdfVertex.getGraphDescription() != null){
		 * sdfVertex.getGraphDescription().accept(this); }
		 */
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
		SDFtoDAGDemo applet3 = new SDFtoDAGDemo();
		GMLSDFImporter importer = new GMLSDFImporter();
		SDFGraph demoGraph;
		try {
			demoGraph = importer.parse(new File(
					"D:\\IDCT2D\\idct2dCadOptim.graphml"));
			SDFHierarchyFlattening visitor = new SDFHierarchyFlattening();
			DAGTransformation<DirectedAcyclicGraph> dageur = new DAGTransformation<DirectedAcyclicGraph>(
					new DirectedAcyclicGraph(),DAGVertexFactory.getInstance());
			visitor.flattenGraph(demoGraph, 1);
			visitor.getOutput().accept(dageur);
			applet2.init(visitor.getOutput());
			applet.init(demoGraph);
			applet3.init(dageur.getOutput());
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

}