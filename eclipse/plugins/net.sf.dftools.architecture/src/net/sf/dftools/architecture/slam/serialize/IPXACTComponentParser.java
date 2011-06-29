/**
 * 
 */
package net.sf.dftools.architecture.slam.serialize;

import java.io.InputStream;

import net.sf.dftools.architecture.slam.attributes.VLNV;
import net.sf.dftools.architecture.slam.component.ComInterface;
import net.sf.dftools.architecture.slam.component.ComInterfaceType;
import net.sf.dftools.architecture.slam.component.Component;
import net.sf.dftools.architecture.slam.component.ComponentFactory;
import net.sf.dftools.architecture.utils.DomUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Parser of a System-Level Architecture model from the IP-XACT format
 * 
 * @author mpelcat
 */
public class IPXACTComponentParser extends IPXACTParser {
	
	public IPXACTComponentParser() {
		super();
	}

	public void parse(InputStream inputStream, Component component) {

		Document document = DomUtil.parseDocument(inputStream);
		Element root = document.getDocumentElement();

		parseComponent(root, component);
	}

	private void parseComponent(Element parent, Component component) {

		VLNV vlnv = parseVLNV(parent);
		component.setVlnv(vlnv);

		Node node = parent.getFirstChild();

		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals("spirit:busInterfaces")) {
					parseComInterfaces(element, component);
				} else if (nodeName.equals("spirit:model")) {
					//parseSubDesign(element, component);
				} else if (nodeName.equals("spirit:vendorExtensions")) {
					//parseComponentType(element, component);
				} else {
					// manage exception;
				}
			}
			node = node.getNextSibling();
		}
	}

	private void parseComInterface(Element parent, Component component) {
		
		component.getInterfaces();
		VLNV busType = null;
		VLNV abstractionType = null;
		
		String interfaceName = null;
		ComInterfaceType interfaceType = ComInterfaceType.UNSPECIFIED;

		Node node = parent.getFirstChild();
		
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:name")) {
					interfaceName = elt.getTextContent();
				} else if (type.equals("spirit:busType")) {
					busType = parseCompactVLNV(parent);
				} else if (type.equals("spirit:abstractionType")) {
					abstractionType = parseCompactVLNV(parent);
				} else if (type.equals("spirit:master")) {
					interfaceType = ComInterfaceType.MASTER;
				} else if (type.equals("spirit:slave")) {
					interfaceType = ComInterfaceType.SLAVE;
				}
			}
			node = node.getNextSibling();
		}

		// Creates the interface if non-existent
		ComInterface itf = component.getInterface(interfaceName);
		if(itf == null){
			itf = ComponentFactory.eINSTANCE.createComInterface();
			component.getInterfaces().add(itf);
		}

		itf.setName(interfaceName);
		itf.setBusType(busType);
		itf.setAbstractionType(abstractionType);
		itf.setComponent(component);
		itf.setInterfaceType(interfaceType);
	}

	private void parseComInterfaces(Element parent, Component component) {
		Node node = parent.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element element = (Element) node;
				String type = element.getTagName();
				if (type.equals("spirit:busInterface")) {
					parseComInterface(element, component);
				}
			}
			node = node.getNextSibling();
		}
	}


}
