/* Copyright (c) 2010-2011 - IETR/INSA de Rennes and EPFL
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA de Rennes and EPFL nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.dftools.architecture.design.serialize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import net.sf.dftools.architecture.VLNV;
import net.sf.dftools.architecture.design.ComponentInstance;
import net.sf.dftools.architecture.design.Connection;
import net.sf.dftools.architecture.design.Design;
import net.sf.dftools.architecture.design.Vertex;
import net.sf.dftools.architecture.utils.DomUtil;

import org.jgrapht.UndirectedGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Writes an IP-XACT design
 * 
 * @author ghislain roquier
 */
public class DesignWriter {

	private final Document document;

	private UndirectedGraph<Vertex, Connection> graph;

	public DesignWriter(File path, Design design) {

		this.graph = design.getGraph();

		document = DomUtil.createDocument(
				"http://www.spiritconsortium.org/XMLSchema/SPIRIT/1.4",
				"spirit:design");
		writeIpXact(document.getDocumentElement(), design);

		File file = new File(path, design.getVlnv().getName() + ".design");
		try {
			OutputStream os = new FileOutputStream(file);
			DomUtil.writeDocument(os, document);
			os.close();
		} catch (IOException e) {
			System.out.println("I/O error");
		}

		writeSubDesign(path);
	}

	private void writeSubDesign(File path) {
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isComponentInstance()) {
				ComponentInstance instance = vertex.getComponentInstance();
				if (instance.isDesign()) {
					Design child = instance.getDesign();
					new DesignWriter(path, child);
				}
			}
		}
	}

	public void writeIpXact(Element ipxact, Design design) {
		writeVLNV(ipxact, design);
		writeComponentInstances(ipxact, design.getComponentInstances());
		writeInterconnections(ipxact, design.getInterConnections());
		writeHierConnections(ipxact, design.getHierConnections());
	}

	private void writeHierConnections(Element parent,
			List<Connection> hierconnections) {
		if (!hierconnections.isEmpty()) {
			Element intsElt = document.createElement("spirit:hierConnections");
			parent.appendChild(intsElt);

			for (Connection connection : hierconnections) {
				writeHierConnection(intsElt, connection);
			}
		}
	}

	private void writeHierConnection(Element parent, Connection connection) {
		Vertex src = graph.getEdgeSource(connection);
		Vertex tgt = graph.getEdgeTarget(connection);

		Element intfElt = document.createElement("spirit:hierConnection");
		parent.appendChild(intfElt);
		if (src.isBusInterface() && tgt.isComponentInstance()) {
			intfElt.setAttribute("spirit:interfaceRef", src.getBusInterface()
					.getName());
			Element activeIntfElt = document
					.createElement("spirit:activeInterface");
			intfElt.appendChild(activeIntfElt);
			activeIntfElt.setAttribute("spirit:componentRef", tgt
					.getComponentInstance().getId());
			activeIntfElt.setAttribute("spirit:busRef", connection.getTarget()
					.getName());
		} else if (src.isComponentInstance() && tgt.isBusInterface()) {
			intfElt.setAttribute("spirit:interfaceRef", tgt.getBusInterface()
					.getName());
			Element activeIntfElt = document
					.createElement("spirit:activeInterface");
			intfElt.appendChild(activeIntfElt);
			activeIntfElt.setAttribute("spirit:componentRef", src
					.getComponentInstance().getId());
			activeIntfElt.setAttribute("spirit:busRef", connection.getSource()
					.getName());
		}
	}

	private void writeInterconnections(Element parent,
			List<Connection> interconnections) {
		if (!interconnections.isEmpty()) {
			Element intsElt = document.createElement("spirit:interconnections");
			parent.appendChild(intsElt);

			for (Connection connection : interconnections) {
				writeInterconnection(intsElt, connection);
			}
		}
	}

	private void writeInterconnection(Element parent, Connection connection) {
		Vertex src = graph.getEdgeSource(connection);
		Vertex tgt = graph.getEdgeTarget(connection);
		if (src.isComponentInstance() & tgt.isComponentInstance()) {
			Element intfElt = document.createElement("spirit:interconnection");
			parent.appendChild(intfElt);

			Element intf1Elt = document.createElement("spirit:activeInterface");
			intfElt.appendChild(intf1Elt);
			intf1Elt.setAttribute("spirit:componentRef", src
					.getComponentInstance().getId());
			intf1Elt.setAttribute("spirit:busRef", connection.getSource()
					.getName());

			Element intf2Elt = document.createElement("spirit:activeInterface");
			intfElt.appendChild(intf2Elt);
			intf2Elt.setAttribute("spirit:componentRef", tgt
					.getComponentInstance().getId());
			intf2Elt.setAttribute("spirit:busRef", connection.getTarget()
					.getName());
		}
	}

	private void writeVLNV(Element parent, Design design) {
		VLNV vlnv = design.getVlnv();
		Element child = document.createElement("spirit:vendor");
		parent.appendChild(child);
		child.setTextContent(vlnv.getVendor());
		child = document.createElement("spirit:library");
		parent.appendChild(child);
		child.setTextContent(vlnv.getLibrary());
		child = document.createElement("spirit:name");
		parent.appendChild(child);
		child.setTextContent(vlnv.getName());
		child = document.createElement("spirit:version");
		parent.appendChild(child);
		child.setTextContent(vlnv.getVersion());
	}

	private void writeVLNV(Element parent, ComponentInstance component) {
		VLNV vlnv = component.getVlnv();
		Element vlnvElt = document.createElement("spirit:componentRef");
		parent.appendChild(vlnvElt);
		vlnvElt.setAttribute("spirit:vendor", vlnv.getVendor());
		vlnvElt.setAttribute("spirit:library", vlnv.getLibrary());
		vlnvElt.setAttribute("spirit:name", vlnv.getName());
		vlnvElt.setAttribute("spirit:version", vlnv.getVersion());
	}

	private void writeComponentInstances(Element parent,
			List<ComponentInstance> instances) {
		Element cmpsElt = document.createElement("spirit:componentInstances");
		parent.appendChild(cmpsElt);
		for (ComponentInstance instance : instances) {
			addComponentInstance(cmpsElt, instance);
		}
	}

	private void addComponentInstance(Element parent, ComponentInstance instance) {
		Element cmpElt = document.createElement("spirit:componentInstance");
		parent.appendChild(cmpElt);

		Element nameElt = document.createElement("spirit:instanceName");
		cmpElt.appendChild(nameElt);
		nameElt.setTextContent(instance.getId());
		writeVLNV(cmpElt, instance);
		writeConfigurableElementValues(cmpElt, instance);
	}

	private void writeConfigurableElementValues(Element parent,
			ComponentInstance instance) {

		if (!instance.getConfigValues().isEmpty()) {
			Element confsElt = document
					.createElement("spirit:configurableElementValues");
			parent.appendChild(confsElt);
			writeConfigurableElementValue(confsElt, instance);
		}
	}

	private void writeConfigurableElementValue(Element parent,
			ComponentInstance instance) {
		for (Map.Entry<String, String> entry : instance.getConfigValues()
				.entrySet()) {
			Element paramElt = document
					.createElement("spirit:configurableElementValue");
			parent.appendChild(paramElt);
			paramElt.setAttribute("spirit:referenceId", entry.getKey());
			paramElt.setTextContent(entry.getValue());
		}
	}

}