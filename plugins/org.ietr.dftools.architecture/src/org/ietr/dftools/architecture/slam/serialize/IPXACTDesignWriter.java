/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
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
/**
 * 
 */
package org.ietr.dftools.architecture.slam.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.VLNVedElement;
import org.ietr.dftools.architecture.slam.attributes.Parameter;
import org.ietr.dftools.architecture.slam.attributes.VLNV;
import org.ietr.dftools.architecture.slam.component.ComInterface;
import org.ietr.dftools.architecture.slam.component.HierarchyPort;
import org.ietr.dftools.architecture.slam.link.Link;
import org.ietr.dftools.architecture.utils.DomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Writer of a System-Level Architecture model in the IP-XACT format
 * 
 * @author mpelcat
 */
public class IPXACTDesignWriter {

	/**
	 * URI of the last opened file
	 */
	public URI uri;

	/**
	 * Information needed in the vendor extensions of the design
	 */
	private IPXACTDesignVendorExtensionsWriter vendorExtensions = null;

	public IPXACTDesignWriter(URI uri) {
		this.uri = uri;
	}

	/**
	 * Writing a given design to a given output stream
	 * 
	 * @param design
	 *            design to write
	 * @param outputStream
	 *            output stream
	 */
	public void write(Design design, OutputStream outputStream) {

		vendorExtensions = new IPXACTDesignVendorExtensionsWriter(design);

		Document document = DomUtil.createDocument(
				"http://www.spiritconsortium.org/XMLSchema/SPIRIT/1.4",
				"spirit:design");
		Element root = document.getDocumentElement();

		// add additional namespace to the root element
		root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:slam",
				"http://sourceforge.net/projects/dftools/slam");

		// Writing elements and initializing vendor extensions
		writeVLNV(root, design, document);
		writeComponentInstances(root, design, document);
		writeLinks(root, design, document);
		writeHierarchyPorts(root, design, document);
		vendorExtensions.write(root, document);

		DomUtil.writeDocument(outputStream, document);

		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void writeCompactVLNV(Element parent, VLNVedElement vlnvedElement,
			Document document) {
		VLNV vlnv = vlnvedElement.getVlnv();
		Element vlnvElt = document.createElement("spirit:componentRef");
		parent.appendChild(vlnvElt);
		vlnvElt.setAttribute("spirit:vendor", vlnv.getVendor());
		vlnvElt.setAttribute("spirit:library", vlnv.getLibrary());
		vlnvElt.setAttribute("spirit:name", vlnv.getName());
		vlnvElt.setAttribute("spirit:version", vlnv.getVersion());
	}

	private void writeVLNV(Element parent, VLNVedElement vlnvedElement,
			Document document) {
		VLNV vlnv = vlnvedElement.getVlnv();
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

	private void writeComponentInstance(Element parent,
			ComponentInstance instance, Document document) {
		Element cmpElt = document.createElement("spirit:componentInstance");
		parent.appendChild(cmpElt);

		Element nameElt = document.createElement("spirit:instanceName");
		cmpElt.appendChild(nameElt);
		nameElt.setTextContent(instance.getInstanceName());
		writeCompactVLNV(cmpElt, instance.getComponent(), document);

		Element confsElt = document
				.createElement("spirit:configurableElementValues");
		cmpElt.appendChild(confsElt);

		writeParameters(confsElt, instance, document);
	}

	private void writeComponentInstances(Element parent, Design design,
			Document document) {

		EList<ComponentInstance> instances = design.getComponentInstances();

		Element cmpsElt = document.createElement("spirit:componentInstances");

		parent.appendChild(cmpsElt);
		for (ComponentInstance instance : instances) {
			writeComponentInstance(cmpsElt, instance, document);
		}
	}

	private void writeParameter(Element parent, String key, String value,
			Document document) {

		Element paramElt = document
				.createElement("spirit:configurableElementValue");
		parent.appendChild(paramElt);
		paramElt.setAttribute("spirit:referenceId", key);
		paramElt.setTextContent(value);
	}

	private void writeParameters(Element confsElt, ComponentInstance instance,
			Document document) {

		if (!instance.getParameters().isEmpty()) {
			for (Parameter param : instance.getParameters()) {
				writeParameter(confsElt, param.getKey(), param.getValue(),
						document);
			}
		}
	}

	private void writeInterconnection(Element parent, Link link,
			Document document) {
		ComponentInstance sourceComponentInstance = link
				.getSourceComponentInstance();
		ComponentInstance destinationComponentInstance = link
				.getDestinationComponentInstance();

		ComInterface sourceInterface = link.getSourceInterface();
		ComInterface destinationInterface = link.getDestinationInterface();

		// No link can be stored with empty uuid
		// It is generated if needed
		if (link.getUuid() == null) {
			link.setUuid(UUID.randomUUID().toString());
		}

		Element intfElt = document.createElement("spirit:interconnection");
		parent.appendChild(intfElt);

		Element nameElt = document.createElement("spirit:name");
		nameElt.setTextContent(link.getUuid());
		intfElt.appendChild(nameElt);

		Element intf1Elt = document.createElement("spirit:activeInterface");
		intfElt.appendChild(intf1Elt);
		intf1Elt.setAttribute("spirit:componentRef",
				sourceComponentInstance.getInstanceName());
		intf1Elt.setAttribute("spirit:busRef", sourceInterface.getName());

		Element intf2Elt = document.createElement("spirit:activeInterface");
		intfElt.appendChild(intf2Elt);
		intf2Elt.setAttribute("spirit:componentRef",
				destinationComponentInstance.getInstanceName());
		intf2Elt.setAttribute("spirit:busRef", destinationInterface.getName());
	}

	private void writeLinks(Element parent, Design design, Document document) {
		EList<Link> links = design.getLinks();

		Element intsElt = document.createElement("spirit:interconnections");
		parent.appendChild(intsElt);

		for (Link link : links) {
			writeInterconnection(intsElt, link, document);
		}
	}

	private void writeHierarchyPort(Element parent,
			HierarchyPort hierarchyPort, Document document) {

		Element intfElt = document.createElement("spirit:hierConnection");
		parent.appendChild(intfElt);

		intfElt.setAttribute("spirit:interfaceRef", hierarchyPort
				.getExternalInterface().getName());
		Element activeIntfElt = document
				.createElement("spirit:activeInterface");
		intfElt.appendChild(activeIntfElt);
		activeIntfElt.setAttribute("spirit:componentRef", hierarchyPort
				.getInternalComponentInstance().getInstanceName());
		activeIntfElt.setAttribute("spirit:busRef", hierarchyPort
				.getInternalInterface().getName());

	}

	private void writeHierarchyPorts(Element parent, Design design,
			Document document) {

		Element intsElt = document.createElement("spirit:hierConnections");
		parent.appendChild(intsElt);

		for (HierarchyPort hierarchyPort : design.getHierarchyPorts()) {
			writeHierarchyPort(intsElt, hierarchyPort, document);
		}
	}
}
