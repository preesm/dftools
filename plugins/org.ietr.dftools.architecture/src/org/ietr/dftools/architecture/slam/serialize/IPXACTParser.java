/**
 * 
 */
package org.ietr.dftools.architecture.slam.serialize;

import org.ietr.dftools.architecture.slam.attributes.AttributesFactory;
import org.ietr.dftools.architecture.slam.attributes.VLNV;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Parser of a System-Level Architecture model from the IP-XACT format.
 * Utilities common to component and design parsers.
 * 
 * @author mpelcat
 */
public abstract class IPXACTParser {

	public IPXACTParser() {
		super();
	}

	protected VLNV parseVLNV(Element parent) {
		Node node = parent.getFirstChild();

		VLNV vlnv = AttributesFactory.eINSTANCE.createVLNV();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals("spirit:vendor")) {
					vlnv.setVendor(element.getTextContent());
				} else if (nodeName.equals("spirit:name")) {
					vlnv.setName(element.getTextContent());
				} else if (nodeName.equals("spirit:library")) {
					vlnv.setLibrary(element.getTextContent());
				} else if (nodeName.equals("spirit:version")) {
					vlnv.setVersion(element.getTextContent());
				} else {

				}
			}
			node = node.getNextSibling();
		}

		return vlnv;
	}

	protected VLNV parseCompactVLNV(Element parent) {
		VLNV vlnv = AttributesFactory.eINSTANCE.createVLNV();

		vlnv.setVendor(parent.getAttribute("spirit:vendor"));
		vlnv.setLibrary(parent.getAttribute("spirit:library"));
		vlnv.setName(parent.getAttribute("spirit:name"));
		vlnv.setVersion(parent.getAttribute("spirit:version"));

		return vlnv;
	}

}
