/**
 * 
 */
package net.sf.dftools.architecture.slam.serialize;

import java.io.OutputStream;

import net.sf.dftools.architecture.slam.ComponentInstance;
import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.VLNVedElement;
import net.sf.dftools.architecture.slam.attributes.Parameter;
import net.sf.dftools.architecture.slam.attributes.VLNV;
import net.sf.dftools.architecture.slam.component.ComInterface;
import net.sf.dftools.architecture.slam.component.HierarchyPort;
import net.sf.dftools.architecture.slam.link.Link;
import net.sf.dftools.architecture.utils.DomUtil;

import org.eclipse.emf.common.util.EList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Writer of a System-Level Architecture model in the IP-XACT format
 * 
 * @author mpelcat
 */
public class IPXACTDesignWriter {

	public IPXACTDesignWriter() {
		super();
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

		Document document = DomUtil.createDocument(
				"http://www.accellera.org/XMLSchema/SPIRIT/1.5",
				"spirit:design");
		Element mainElement = document.getDocumentElement();

		writeDesignVLNV(mainElement, design, document);
		writeComponentInstances(mainElement, design, document);
		writeLinks(mainElement, design, document);
		writeHierarchyPorts(mainElement, design, document);

		DomUtil.writeDocument(outputStream, document);
	}

	private void writeVLNV(Element parent, VLNVedElement vlnvedElement,
			Document document) {
		VLNV vlnv = vlnvedElement.getVlnv();
		Element vlnvElt = document.createElement("spirit:componentRef");
		parent.appendChild(vlnvElt);
		vlnvElt.setAttribute("spirit:vendor", vlnv.getVendor());
		vlnvElt.setAttribute("spirit:library", vlnv.getLibrary());
		vlnvElt.setAttribute("spirit:name", vlnv.getName());
		vlnvElt.setAttribute("spirit:version", vlnv.getVersion());
	}

	private void writeDesignVLNV(Element parent, Design design,
			Document document) {
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

	private void writeComponentInstance(Element parent,
			ComponentInstance instance, Document document) {
		Element cmpElt = document.createElement("spirit:componentInstance");
		parent.appendChild(cmpElt);

		Element nameElt = document.createElement("spirit:instanceName");
		cmpElt.appendChild(nameElt);
		nameElt.setTextContent(instance.getInstanceName());
		writeVLNV(cmpElt, instance, document);
		addParametersTag(cmpElt, instance, document);
	}

	private void writeComponentInstances(Element parent, Design design,
			Document document) {

		EList<ComponentInstance> instances = design.getComponentInstances();

		if (!instances.isEmpty()) {
			Element cmpsElt = document
					.createElement("spirit:componentInstances");

			parent.appendChild(cmpsElt);
			for (ComponentInstance instance : instances) {
				writeComponentInstance(cmpsElt, instance, document);
			}
		}
	}

	private void writeParameters(Element parent, ComponentInstance instance,
			Document document) {
		for (Parameter param : instance.getParameters()) {
			Element paramElt = document
					.createElement("spirit:configurableElementValue");
			parent.appendChild(paramElt);
			paramElt.setAttribute("spirit:referenceId", param.getKey());
			paramElt.setTextContent(param.getValue());
		}
	}

	private void addParametersTag(Element parent, ComponentInstance instance,
			Document document) {

		if (!instance.getParameters().isEmpty()) {
			Element confsElt = document
					.createElement("spirit:configurableElementValues");
			parent.appendChild(confsElt);
			writeParameters(confsElt, instance, document);
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

		Element intfElt = document.createElement("spirit:interconnection");
		parent.appendChild(intfElt);

		Element intf1Elt = document.createElement("spirit:activeInterface");
		intfElt.appendChild(intf1Elt);
		intf1Elt.setAttribute("spirit:componentRef",
				sourceComponentInstance.getInstanceName());
		intf1Elt.setAttribute("spirit:busRef", sourceInterface.getName());

		Element intf2Elt = document.createElement("spirit:activeInterface");
		intfElt.appendChild(intf2Elt);
		intf2Elt.setAttribute("spirit:componentRef",
				destinationComponentInstance.getInstanceName());
		intf2Elt.setAttribute("spirit:busRef", destinationInterface
				.getName());

	}

	private void writeLinks(Element parent, Design design, Document document) {
		EList<Link> links = design.getLinks();

		if (!links.isEmpty()) {
			Element intsElt = document.createElement("spirit:interconnections");
			parent.appendChild(intsElt);

			for (Link link : links) {
				writeInterconnection(intsElt, link, document);
			}
		}
	}

	private void writeHierarchyPort(Element parent,
			HierarchyPort hierarchyPort, Document document) {

		Element intfElt = document.createElement("spirit:hierConnection");
		parent.appendChild(intfElt);

		intfElt.setAttribute("spirit:interfaceRef", hierarchyPort
				.getExternalInterface().getBusType().getName());
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

		EList<HierarchyPort> hierarchyPorts = design.getHierarchyPorts();
		if (!hierarchyPorts.isEmpty()) {

			Element intsElt = document.createElement("spirit:hierConnections");
			parent.appendChild(intsElt);

			for (HierarchyPort hierarchyPort : design.getHierarchyPorts()) {
				writeHierarchyPort(intsElt, hierarchyPort, document);
			}
		}
	}
}
