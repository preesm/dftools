/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (%%DATE%%) :
 *
 * %%AUTHORS%%
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
package org.ietr.dftools.algorithm.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.ietr.dftools.algorithm.exporter.GMLDAGExporter;
import org.ietr.dftools.algorithm.factories.DAGEdgeFactory;
import org.ietr.dftools.algorithm.model.dag.DAGEdge;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Importer for DAG graphs
 * 
 * @author jpiat
 * 
 */
public class GMLDAGImporter extends
		GMLImporter<DirectedAcyclicGraph, DAGVertex, DAGEdge> {

	/**
	 * Test method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GMLDAGImporter importer = new GMLDAGImporter();
			DirectedAcyclicGraph graph = importer.parse(new File(
					"C:\\test_dag_gml.xml"));
			GMLDAGExporter exporter = new GMLDAGExporter();
			exporter.setKeySet(importer.getKeySet());
			exporter.exportGraph(graph);
			exporter.transform(new FileOutputStream("C:\\test_dag_gml_2.xml"));
		} catch (FileNotFoundException | InvalidModelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs a new DAG importer with the specified factories
	 */
	public GMLDAGImporter() {
		super(new DAGEdgeFactory());
	}

	@Override
	public void parseEdge(Element edgeElt, DirectedAcyclicGraph parentGraph) {
		DAGVertex vertexSource = vertexFromId.get(edgeElt
				.getAttribute("source"));
		DAGVertex vertexTarget = vertexFromId.get(edgeElt
				.getAttribute("target"));

		DAGEdge edge = parentGraph.addEdge(vertexSource, vertexTarget);

		parseKeys(edgeElt, edge);
	}

	@Override
	public DirectedAcyclicGraph parseGraph(Element graphElt) {
		DirectedAcyclicGraph graph = new DirectedAcyclicGraph(
				edgeFactory);
		parseKeys(graphElt, graph);
		NodeList childList = graphElt.getChildNodes();
		parseParameters(graph, graphElt);
		parseVariables(graph, graphElt);
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("node")) {
				Element vertexElt = (Element) childList.item(i);
				parseNode(vertexElt, graph);
			}
		}
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("edge")) {
				Element edgeElt = (Element) childList.item(i);
				parseEdge(edgeElt, graph);
			}
		}
		return graph;
	}

	@Override
	public DAGVertex parseNode(Element vertexElt,
			DirectedAcyclicGraph parentGraph) {
		DAGVertex vertex = new DAGVertex();
		parentGraph.addVertex(vertex);
		vertex.setId(vertexElt.getAttribute("id"));
		vertexFromId.put(vertex.getId(), vertex);
		parseKeys(vertexElt, vertex);
		parseArguments(vertex, vertexElt);
		return vertex;
	}

	@Override
	public DAGVertex parsePort(Element portElt, DirectedAcyclicGraph parentGraph) {
		// TODO Auto-generated method stub
		return null;
	}

}
