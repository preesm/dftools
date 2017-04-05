/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2011 - 2012)
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
public class FlatteningVisitor implements IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	/**
	 * Creates a known graph
	 *
	 * @return The created test_com graph
	 *
	 */
	public static SDFGraph createTestComGraph() {

		final SDFGraph graph = new SDFGraph();

		// test_com_basique
		final SDFInterfaceVertex sensorInt = new SDFSourceInterfaceVertex();
		sensorInt.setName("sensor_Int");
		graph.addVertex(sensorInt);

		final SDFVertex gen5 = new SDFVertex();
		gen5.setName("Gen5");
		graph.addVertex(gen5);

		final SDFVertex recopie5 = new SDFVertex();
		recopie5.setName("recopie_5");
		graph.addVertex(recopie5);

		final SDFInterfaceVertex acqData = new SDFSinkInterfaceVertex();
		acqData.setName("acq_data");
		graph.addVertex(acqData);

		// hierachy ...
		final SDFGraph subGraph = new SDFGraph();
		subGraph.setName("gen_5_Sub");

		final SDFInterfaceVertex add = new SDFSourceInterfaceVertex();
		add.setName("Add");

		final SDFVertex gen_sub1 = new SDFVertex();
		gen_sub1.setName("gen_sub1");
		subGraph.addVertex(gen_sub1);

		final SDFVertex gen_sub2 = new SDFVertex();
		gen_sub2.setName("gen_sub2");
		subGraph.addVertex(gen_sub2);

		final SDFInterfaceVertex times = new SDFSinkInterfaceVertex();
		times.setName("Times");

		gen5.setGraphDescription(subGraph);
		gen5.addArgument(new Argument("NB_COPY", "100"));
		gen5.addSink(times);
		gen5.addSource(add);

		final SDFEdge intern1 = subGraph.addEdge(add, gen_sub1);
		intern1.setProd(new SDFIntEdgePropertyType(1));
		intern1.setCons(new SDFIntEdgePropertyType(1));

		final SDFEdge intern2 = subGraph.addEdge(gen_sub2, times);
		intern2.setProd(new SDFIntEdgePropertyType(1));
		intern2.setCons(new SDFIntEdgePropertyType(1));

		final SDFEdge intern3 = subGraph.addEdge(gen_sub1, gen_sub2);
		intern3.setProd(new SDFIntEdgePropertyType(1));
		intern3.setCons(new SDFIntEdgePropertyType(1));

		// end of hierachy
		final SDFEdge sensGen = graph.addEdge(sensorInt, gen5);
		sensGen.setTargetInterface(add);
		sensGen.setProd(new SDFIntEdgePropertyType(1));
		sensGen.setCons(new SDFIntEdgePropertyType(1));

		final SDFEdge genRec = graph.addEdge(gen5, recopie5);
		genRec.setSourceInterface(times);
		genRec.setProd(new SDFIntEdgePropertyType(2));
		genRec.setCons(new SDFIntEdgePropertyType(3));

		final SDFEdge genAcq = graph.addEdge(gen5, acqData);
		genAcq.setSourceInterface(times);
		genAcq.setProd(new SDFIntEdgePropertyType(1));
		genAcq.setCons(new SDFIntEdgePropertyType(1));

		final SDFEdge recAcq = graph.addEdgeWithInterfaces(recopie5, acqData);
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
	public static void main(final String[] args) throws InvalidExpressionException {
		final SDFAdapterDemo applet = new SDFAdapterDemo();
		final SDFAdapterDemo applet2 = new SDFAdapterDemo();
		final GMLSDFImporter importer = new GMLSDFImporter();
		// SDFGraph demoGraph = createTestComGraph();
		SDFGraph demoGraph;
		try {
			demoGraph = importer.parse(new File("D:\\IDCT2D\\idct2dCadOptim.xml"));
			final FlatteningVisitor visitor = new FlatteningVisitor();
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
		return this.output;
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
	private void treatVertex(final SDFAbstractVertex vertex, final SDFGraph parentGraph) {
		Vector<SDFAbstractVertex> vertices = new Vector<SDFAbstractVertex>(vertex.getGraphDescription().vertexSet());
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getGraphDescription() != null) {
				treatVertex(vertices.get(i), (SDFGraph) vertex.getGraphDescription());
				vertex.getGraphDescription().removeVertex(vertices.get(i));
			}
		}
		vertices = new Vector<SDFAbstractVertex>(vertex.getGraphDescription().vertexSet());
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getGraphDescription() == null) {
				parentGraph.addVertex(vertices.get(i));
			}
		}
		final Vector<SDFEdge> edges = new Vector<SDFEdge>(vertex.getGraphDescription().edgeSet());
		for (int i = 0; i < edges.size(); i++) {
			SDFAbstractVertex sourceVertex;
			SDFAbstractVertex targetVertex;
			if (edges.get(i).getSource() instanceof SDFInterfaceVertex) {
				final SDFInterfaceVertex sourceInterface = (SDFInterfaceVertex) edges.get(i).getSource();
				sourceVertex = vertex.getAssociatedEdge(sourceInterface).getSource();
				edges.get(i).setSourceInterface(vertex.getAssociatedEdge(sourceInterface).getSourceInterface());
			} else {
				sourceVertex = edges.get(i).getSource();
			}
			if (edges.get(i).getTarget() instanceof SDFInterfaceVertex) {
				final SDFInterfaceVertex targetInterface = (SDFInterfaceVertex) edges.get(i).getTarget();
				targetVertex = vertex.getAssociatedEdge(targetInterface).getTarget();
				edges.get(i).setTargetInterface(vertex.getAssociatedEdge(targetInterface).getTargetInterface());
			} else {
				targetVertex = edges.get(i).getTarget();
			}
			final SDFEdge newEdge = parentGraph.addEdge(sourceVertex, targetVertex);
			for (final String key : edges.get(i).getPropertyBean().keys()) {
				newEdge.getPropertyBean().setValue(key, edges.get(i).getPropertyBean().getValue(key));
			}
		}
	}

	@Override
	public void visit(final SDFEdge sdfEdge) {

	}

	@Override
	public void visit(final SDFGraph sdf) throws SDF4JException {
		this.output = sdf.clone();
		final TopologyVisitor schedulability = new TopologyVisitor();
		this.output.accept(schedulability);
		if (!this.output.isSchedulable()) {
			return;
		}
		final Vector<SDFAbstractVertex> vertices = new Vector<>(this.output.vertexSet());
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getGraphDescription() != null) {
				treatVertex(vertices.get(i), this.output);
				this.output.removeVertex(vertices.get(i));
			}
		}
		final Vector<SDFEdge> edges = new Vector<>(this.output.edgeSet());
		for (int i = 0; i < edges.size(); i++) {
			final SDFEdge edge = edges.get(i);
			try {
				if ((edge.getCons().intValue() == 0) || (edge.getProd().intValue() == 0)) {
					this.output.removeEdge(edge);
				}
			} catch (final InvalidExpressionException e) {
				e.printStackTrace();
				throw (new SDF4JException(e.getMessage()));
			}
		}
	}

	@Override
	public void visit(final SDFAbstractVertex sdfVertex) throws SDF4JException {

	}

}
