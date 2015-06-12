package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

import org.ietr.dftools.algorithm.demo.SDFAdapterDemo;
import org.ietr.dftools.algorithm.demo.SDFtoDAGDemo;
import org.ietr.dftools.algorithm.factories.DAGVertexFactory;
import org.ietr.dftools.algorithm.importer.GMLSDFImporter;
import org.ietr.dftools.algorithm.importer.InvalidModelException;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFForkVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFJoinVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.ietr.dftools.algorithm.model.visitors.VisitorOutput;

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
			Vector<SDFAbstractVertex> targetCopies, int targetIndex,
			int sourceIndex, int rest, int nbDelays, int sourceProd,
			int targetCons) throws InvalidExpressionException {
		SDFEdge newEdge = null;
		SDFInterfaceVertex inputVertex = null;
		SDFInterfaceVertex outputVertex = null;

		newEdge = getExistingEdge(output, edge, sourceCopies, targetCopies,
				sourceIndex, targetIndex, rest);

		SDFAbstractVertex source = sourceCopies.get(sourceIndex);
		SDFAbstractVertex target = targetCopies.get(targetIndex);

		if (newEdge == null) {
			newEdge = output.addEdge(sourceCopies.get(sourceIndex),
					targetCopies.get(targetIndex));
			newEdge.copyProperties(edge);
			newEdge.setProd(new SDFIntEdgePropertyType(rest));
			newEdge.setCons(new SDFIntEdgePropertyType(rest));
		}
		if (source.getSink(edge.getSourceInterface().getName()) != null) {

			newEdge.setSourceInterface(source.getSink(edge.getSourceInterface()
					.getName()));
		} else {
			// If this case is reached, the source is a new explode/broadcast

			// sourceProd represents the token Nr of the first token on the edge
			// (useful for explode)
			// the targetIndex serves to discriminate different sources with the
			// same production
			newEdge.setSourceInterface(edge.getSourceInterface().clone());
			newEdge.getSourceInterface().setName(
					"out" + "_" + sourceProd + "_" + targetIndex);
		}
		if (target.getSource(edge.getTargetInterface().getName()) != null) {
			newEdge.setTargetInterface(target.getSource(edge
					.getTargetInterface().getName()));
		} else {
			// If this case is reached, the source is a new implode/roundbuffer

			// targetCons represents the token Nr of the first token on the edge
			// (useful for implode)
			// the sourceIndex serves to discriminate different sources with the
			// same production
			newEdge.setTargetInterface(edge.getTargetInterface().clone());
			newEdge.getTargetInterface().setName(
					"in" + "_" + targetCons + "_" + sourceIndex);
		}

		if (target instanceof SDFVertex && !(source instanceof SDFForkVertex)) {
			if (((SDFVertex) target).getAssociatedInterface(edge) != null) {
				inputVertex = ((SDFVertex) target).getAssociatedInterface(edge);
				((SDFVertex) target).setInterfaceVertexExternalLink(newEdge,
						inputVertex);
			}
		}
		if (source instanceof SDFVertex && !(target instanceof SDFJoinVertex)) {
			if (((SDFVertex) source).getAssociatedInterface(edge) != null) {
				outputVertex = ((SDFVertex) source)
						.getAssociatedInterface(edge);
				((SDFVertex) source).setInterfaceVertexExternalLink(newEdge,
						outputVertex);
			}
		}
		if (targetIndex == 0 && nbDelays > 0) {
			newEdge.setDelay(new SDFIntEdgePropertyType(edge.getDelay()
					.intValue()));
		} else {
			newEdge.setDelay(new SDFIntEdgePropertyType(0));
		}
	}

	/**
	 * Return an existing edge in output between sourceCopis.get(sourceIndex)
	 * and targetCopies.get(targetIndex) and corresponding to edge
	 * 
	 * @param output
	 *            the SDFGraph in which we look for an edge
	 * @param edge
	 *            the edge to which we compare existing edges
	 * @param sourceCopies
	 *            the collection of copies of the source of edge
	 * @param targetCopies
	 *            the collections of copies of the target of edge
	 * @param sourceIndex
	 * @param targetIndex
	 * @param rest
	 * @return the existing edge if any, null otherwise
	 * @throws InvalidExpressionException
	 */
	private SDFEdge getExistingEdge(SDFGraph output, SDFEdge edge,
			Vector<SDFAbstractVertex> sourceCopies,
			Vector<SDFAbstractVertex> targetCopies, int sourceIndex,
			int targetIndex, int rest) throws InvalidExpressionException {
		SDFEdge newEdge = null;
		if (output.getAllEdges(sourceCopies.get(sourceIndex),
				targetCopies.get(targetIndex)) != null) {
			for (SDFEdge existingEdge : output.getAllEdges(
					sourceCopies.get(sourceIndex),
					targetCopies.get(targetIndex))) {
				if (existingEdge.getSourceInterface().equals(
						edge.getSourceInterface())
						&& existingEdge.getTargetInterface().equals(
								edge.getTargetInterface())) {
					newEdge = existingEdge;
					if (sourceCopies.size() == 1) {
						newEdge.setProd(new SDFIntEdgePropertyType(edge
								.getProd().intValue()));
					} else {
						newEdge.setProd(new SDFIntEdgePropertyType(newEdge
								.getProd().intValue() + rest));
					}
					if (targetCopies.size() == 1) {
						newEdge.setCons(new SDFIntEdgePropertyType(edge
								.getCons().intValue()));
					} else {
						newEdge.setCons(new SDFIntEdgePropertyType(newEdge
								.getCons().intValue() + rest));
					}
				}
			}
		}
		return newEdge;
	}

	private void linkRepetitions(SDFGraph sdf, SDFGraph output, SDFEdge edge,
			Vector<SDFAbstractVertex> sourceCopies,
			Vector<SDFAbstractVertex> targetCopies)
			throws InvalidExpressionException {
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
		while (totProd < (edge.getCons().intValue() * edge.getTarget()
				.getNbRepeatAsInteger())) {
			// testing this block for inserting explode and implode vertices
			if ((rest < (edge.getProd().intValue() * (edge.getSource()
					.getNbRepeatAsInteger() / sourceCopies.size())))
					&& !(sourceCopies.get(sourceIndex) instanceof SDFForkVertex)
			/* && !(sourceCopies.get(sourceIndex) instanceof SDFBroadcastVertex) */) {
				SDFAbstractVertex explodeVertex = new SDFForkVertex();

				output.addVertex(explodeVertex);
				SDFAbstractVertex originVertex = sourceCopies.get(sourceIndex);
				explodeVertex.setName("explode_" + originVertex.getName() + "_"
						+ edge.getSourceInterface().getName());
				sourceCopies.set(sourceIndex, explodeVertex);
				SDFEdge newEdge = output.addEdge(originVertex, explodeVertex);
				newEdge.copyProperties(edge);
				newEdge.setDelay(new SDFIntEdgePropertyType(0));
				newEdge.setProd(new SDFIntEdgePropertyType(edge.getProd()
						.intValue()));
				/*
				 * We multiply by the number of repetitions of the source to
				 * prevent the case where edge stand between two vertices with
				 * the same repetition number, but where only one is
				 * instantiated during flattening (see Judicael's stereo_matcher
				 * application with the edge between cost_construction and
				 * aggregate)
				 */
				newEdge.setCons(new SDFIntEdgePropertyType(edge.getProd()
						.intValue() * edge.getSource().getNbRepeatAsInteger()));
				newEdge.setDataType(edge.getDataType());
				newEdge.setSourceInterface(edge.getSourceInterface());
				SDFSinkInterfaceVertex in = new SDFSinkInterfaceVertex();
				in.setName("in");
				explodeVertex.addSink(in);
				newEdge.setTargetInterface(in);
			}
			if ((rest < (edge.getCons().intValue() * (edge.getTarget()
					.getNbRepeatAsInteger() / targetCopies.size())))
					&& !(targetCopies.get(targetIndex) instanceof SDFJoinVertex)) {
				SDFAbstractVertex implodeVertex = new SDFJoinVertex();
				output.addVertex(implodeVertex);
				SDFAbstractVertex originVertex = targetCopies.get(targetIndex);
				implodeVertex.setName("implode_" + originVertex.getName() + "_"
						+ edge.getTargetInterface().getName());
				targetCopies.set(targetIndex, implodeVertex);
				SDFEdge newEdge = output.addEdge(implodeVertex, originVertex);
				newEdge.copyProperties(edge);
				newEdge.setDelay(new SDFIntEdgePropertyType(0));
				newEdge.setProd(new SDFIntEdgePropertyType((edge.getCons()
						.intValue() / targetCopies.size())
						* sourceCopies.size()));
				newEdge.setCons(new SDFIntEdgePropertyType(edge.getCons()
						.intValue()));
				newEdge.setDataType(edge.getDataType());
				SDFSourceInterfaceVertex out = new SDFSourceInterfaceVertex();
				out.setName("out");
				newEdge.setSourceInterface(out);
				newEdge.setTargetInterface(edge.getTargetInterface());
			}
			// end of testing zone
			createEdge(sdf, output, edge, sourceCopies, targetCopies,
					targetIndex, sourceIndex, rest, nbDelays, sourceProd,
					targetCons);
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
			if (sourceCopies.size() == 1 && targetCopies.size() == 1) { // no
																		// copies
																		// !
				SDFEdge newEdge = output.addEdge(sourceCopies.get(0),
						targetCopies.get(0));
				newEdge.copyProperties(edge);
				newEdge.setDelay(new SDFIntEdgePropertyType(edge.getDelay()
						.intValue()));
				newEdge.setProd(new SDFIntEdgePropertyType(edge.getProd()
						.intValue()));
				newEdge.setCons(new SDFIntEdgePropertyType(edge.getCons()
						.intValue()));
				newEdge.setDataType(edge.getDataType());
				newEdge.setSourceInterface(edge.getSourceInterface());
				newEdge.setTargetInterface(edge.getTargetInterface());

			} else {
				linkRepetitions(sdf, output, edge, sourceCopies, targetCopies);
			}
			for (int i = 0; i < sourceCopies.size(); i++) {
				if (sourceCopies.get(i) instanceof SDFForkVertex
						&& sdf.getVertex(sourceCopies.get(i).getName()) == null) {
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
						&& sdf.getVertex(targetCopies.get(i).getName()) == null) {
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

		if (graph.isSchedulable()) {
			for (SDFAbstractVertex vertex : graph.vertexSet()) {
				Vector<SDFAbstractVertex> copies = new Vector<SDFAbstractVertex>();
				matchCopies.put(vertex, copies);
				if (vertex.getGraphDescription() != null) {
					boolean hasDelays = false;
					@SuppressWarnings("unchecked")
					Set<SDFEdge> edges = vertex.getGraphDescription().edgeSet();
					for (SDFEdge edge : edges) {
						if (edge.getDelay().intValue() != 0) {
							hasDelays = true;
							break;
						}
					}
					if (hasDelays && vertex.getNbRepeatAsInteger() > 1) {
						for (int i = 0; i < vertex.getNbRepeatAsInteger(); i++) {
							SDFAbstractVertex copy = vertex.clone();
							copy.setName(copy.getName() + "_" + i);
							copy.setNbRepeat(1);
							output.addVertex(copy);
							copies.add(copy);
						}
					} else {
						SDFAbstractVertex copy = vertex.clone();
						output.addVertex(copy);
						copies.add(copy);
					}
				} else {
					SDFAbstractVertex copy = vertex.clone();
					output.addVertex(copy);
					copies.add(copy);
				}
			}
			linkVerticesTop(graph, matchCopies, output);
			output.getPropertyBean().setValue("schedulable", true);
		} else {
			VisitorOutput.getLogger().log(Level.SEVERE,
					"graph " + graph.getName() + " is not schedulable");
		}
	}

	@Override
	public void visit(SDFEdge sdfEdge) {
	}

	@Override
	public void visit(SDFGraph sdf) throws SDF4JException {
		outputGraph = sdf.clone();
		outputGraph.clean();
		try {
			transformsTop(sdf, outputGraph);
		} catch (InvalidExpressionException e) {
			e.printStackTrace();
			throw (new SDF4JException(e.getMessage()));
		}
	}

	@Override
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
					new DirectedAcyclicGraph(), DAGVertexFactory.getInstance());
			visitor.flattenGraph(demoGraph, 1);
			visitor.getOutput().accept(dageur);
			applet2.init(visitor.getOutput());
			applet.init(demoGraph);
			applet3.init(dageur.getOutput());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidModelException e) {
			e.printStackTrace();
		} catch (SDF4JException e) {
			e.printStackTrace();
		}
	}

}