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
package org.ietr.dftools.algorithm.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.w3c.dom.Element;

public class GMLGenericExporter extends
		GMLExporter<AbstractVertex<?>, AbstractEdge<?, ?>> {

	@Override
	public void export(
			AbstractGraph<AbstractVertex<?>, AbstractEdge<?, ?>> graph,
			String path) {
		this.path = path;
		try {
			exportGraph(graph);
			transform(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected Element exportEdge(AbstractEdge<?, ?> edge, Element parentELement) {
		String sourcePort = "";
		String targetPort = "";
		if (edge.getSourceLabel() != null) {
			sourcePort = edge.getSourceLabel();
		}
		if (edge.getTargetLabel() != null) {
			targetPort = edge.getTargetLabel();
		}
		Element edgeElt = createEdge(parentELement, edge.getSource().getName(),
				edge.getTarget().getName(), sourcePort, targetPort);
		exportKeys(edge, "edge", edgeElt);
		return edgeElt;
	}

	@Override
	public Element exportGraph(
			AbstractGraph<AbstractVertex<?>, AbstractEdge<?, ?>> graph) {
		addKeySet(rootElt);
		Element graphElt = createGraph(rootElt, true);
		AbstractGraph<?, ?> myGraph = (graph);
		graphElt.setAttribute("edgedefault", "directed");
		exportKeys(graph, "graph", graphElt);
		if (myGraph.getParameters() != null) {
			exportParameters(myGraph.getParameters(), graphElt);
		}
		if (myGraph.getVariables() != null) {
			exportVariables(myGraph.getVariables(), graphElt);
		}
		for (AbstractVertex<?> child : myGraph.vertexSet()) {
			exportNode(child, graphElt);
		}
		for (AbstractEdge<?, ?> edge : myGraph.edgeSet()) {
			exportEdge(edge, graphElt);
		}
		return graphElt;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Element exportNode(AbstractVertex<?> vertex, Element parentELement) {
		Element vertexElt = createNode(parentELement, vertex.getName());
		if (vertex.getGraphDescription() != null
				&& vertex.getGraphDescription().getName().length() > 0) {
			String filePath = vertex.getGraphDescription().getName();
			if (!filePath.contains(".graphml")) {
				filePath = filePath + ".graphml";
				vertex.getGraphDescription().setName(filePath);
			}
			filePath.replace(File.separator, "/");
			String thisPathPrefix = path.substring(0,
					path.lastIndexOf(File.separator) + 1);

			if (filePath.lastIndexOf("/") > 0
					&& filePath.contains(thisPathPrefix)) {
				if (filePath.compareTo(thisPathPrefix) > 0) {
					vertex.getGraphDescription().setName(
							filePath.substring(filePath.length()
									- filePath.compareTo(thisPathPrefix)));
					GMLGenericExporter decExporter = new GMLGenericExporter();
					decExporter.export(
							vertex.getGraphDescription(),
							filePath.substring(filePath.length()
									- filePath.compareTo(thisPathPrefix)));
				}
			} else {
				GMLGenericExporter decExporter = new GMLGenericExporter();
				decExporter.export(vertex.getGraphDescription(), thisPathPrefix
						+ filePath);
			}
		}
		exportKeys(vertex, "node", vertexElt);
		if (vertex.getArguments() != null) {
			exportArguments(vertex.getArguments(), vertexElt);
		}
		return vertexElt;
	}

	@Override
	protected Element exportPort(AbstractVertex<?> interfaceVertex,
			Element parentELement) {
		Element interfaceElt = createPort(parentELement,
				interfaceVertex.getName());
		exportKeys(interfaceVertex, "port", interfaceElt);
		return interfaceElt;
	}

}
