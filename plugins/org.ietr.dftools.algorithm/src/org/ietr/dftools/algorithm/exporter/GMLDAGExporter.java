/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2011)
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
package org.ietr.dftools.algorithm.exporter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.dag.DAGEdge;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.ietr.dftools.algorithm.model.dag.types.DAGDefaultEdgePropertyType;
import org.w3c.dom.Element;

/**
 * GML exporter for dag
 * 
 * @author jpiat
 * 
 */
public class GMLDAGExporter extends GMLExporter<DAGVertex, DAGEdge> {

	/**
	 * Creates a graph to test the Explorer
	 * 
	 * @return The created Graph
	 */
	public static DirectedAcyclicGraph createTestComGraph() {

		DirectedAcyclicGraph graph = new DirectedAcyclicGraph();

		// test_com_basique
		DAGVertex sensorInt = new DAGVertex();
		sensorInt.setName("1");
		graph.addVertex(sensorInt);

		DAGVertex gen5 = new DAGVertex();
		gen5.setName("Gen5");
		graph.addVertex(gen5);

		DAGVertex recopie5 = new DAGVertex();
		recopie5.setName("recopie_5");
		graph.addVertex(recopie5);

		DAGVertex acqData = new DAGVertex();
		acqData.setName("acq_data");
		graph.addVertex(acqData);

		DAGEdge sensGen = graph.addEdge(sensorInt, gen5);
		sensGen.setWeight(new DAGDefaultEdgePropertyType(8));

		DAGEdge genRec = graph.addEdge(gen5, recopie5);
		genRec.setWeight(new DAGDefaultEdgePropertyType(100));

		DAGEdge genAcq = graph.addEdge(gen5, acqData);
		genAcq.setWeight(new DAGDefaultEdgePropertyType(2));

		DAGEdge recAcq = graph.addEdge(recopie5, acqData);
		recAcq.setWeight(new DAGDefaultEdgePropertyType(1000));
		return graph;
	}

	/**
	 * Tests this exporter behavior
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DirectedAcyclicGraph graph = createTestComGraph();
		GMLDAGExporter exporter = new GMLDAGExporter();
		try {
			exporter.exportGraph(graph);
			exporter.transform(new FileOutputStream("C:\\test_dag_gml.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Builds a new GMLDAGExporter
	 */
	public GMLDAGExporter() {
		super();
	}

	@Override
	public void export(AbstractGraph<DAGVertex, DAGEdge> graph, String path) {
		this.path = path;
		try {
			exportGraph(graph);
			transform(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Element exportEdge(DAGEdge edge, Element parentELement) {
		Element edgeElt = createEdge(parentELement, edge.getSource().getId(),
				edge.getTarget().getId());
		exportKeys(edge, "edge", edgeElt);
		return edgeElt;
	}

	@Override
	public Element exportGraph(AbstractGraph<DAGVertex, DAGEdge> graph) {
		try {
			addKeySet(rootElt);
			DirectedAcyclicGraph myGraph = (DirectedAcyclicGraph) graph;
			Element graphElt = createGraph(rootElt, true);
			graphElt.setAttribute("edgedefault", "directed");
			exportKeys(graph, "graph", graphElt);
			if (myGraph.getParameters() != null) {
				exportParameters(myGraph.getParameters(), graphElt);
			}
			if (myGraph.getVariables() != null) {
				exportVariables(myGraph.getVariables(), graphElt);
			}
			for (DAGVertex child : myGraph.vertexSet()) {
				exportNode(child, graphElt);
			}

			for (DAGEdge edge : myGraph.edgeSet()) {
				exportEdge(edge, graphElt);
			}
			return graphElt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected Element exportNode(DAGVertex vertex, Element parentELement) {
		Element vertexElt = createNode(parentELement, vertex.getId());
		exportKeys(vertex, "vertex", vertexElt);
		if (vertex.getArguments() != null) {
			exportArguments(vertex.getArguments(), vertexElt);
		}
		return vertexElt;
	}

	@Override
	protected Element exportPort(DAGVertex interfaceVertex,
			Element parentELement) {
		return null;
	}

}
