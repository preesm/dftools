/**
 * 
 */
package net.sf.dftools.architecture.slam.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.dftools.architecture.slam.ComponentInstance;
import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.ParameterizedElement;
import net.sf.dftools.architecture.slam.SlamFactory;
import net.sf.dftools.architecture.slam.attributes.AttributesFactory;
import net.sf.dftools.architecture.slam.attributes.Parameter;
import net.sf.dftools.architecture.slam.attributes.VLNV;
import net.sf.dftools.architecture.slam.component.ComInterface;
import net.sf.dftools.architecture.slam.component.Component;
import net.sf.dftools.architecture.slam.component.ComponentFactory;
import net.sf.dftools.architecture.slam.component.ComponentPackage;
import net.sf.dftools.architecture.slam.component.HierarchyPort;
import net.sf.dftools.architecture.slam.link.Link;
import net.sf.dftools.architecture.slam.link.LinkFactory;
import net.sf.dftools.architecture.slam.serialize.IPXACTDesignVendorExtensions.LinkDescription;
import net.sf.dftools.architecture.utils.DomUtil;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Parser of a System-Level Architecture model from the IP-XACT format
 * 
 * @author mpelcat
 */
public class IPXACTDesignParser extends IPXACTParser {

	/**
	 * Information needed in the vendor extensions of the design
	 */
	private IPXACTDesignVendorExtensions vendorExtensions;

	/**
	 * IPXact parser constructor
	 */
	public IPXACTDesignParser() {
		vendorExtensions = new IPXACTDesignVendorExtensions();
	}

	public Design parse(InputStream inputStream) {
		// The topmost component is initialized to enable storing
		// the hierarchical external interfaces
		Component refinedComponent = ComponentFactory.eINSTANCE
				.createComponent();
		Design design = SlamFactory.eINSTANCE.createDesign();
		refinedComponent.setRefinement(design);

		Document document = DomUtil.parseDocument(inputStream);
		Element root = document.getDocumentElement();

		vendorExtensions.parse(root);
		parseDesign(root, design);

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return design;
	}

	private void parseDesign(Element parent, Design design) {

		VLNV vlnv = parseVLNV(parent);
		design.setVlnv(vlnv);

		Node node = parent.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals("spirit:componentInstances")) {
					parseComponentInstances(element, design);
				} else if (nodeName.equals("spirit:interconnections")) {
					parseLinks(element, design);
				} else if (nodeName.equals("spirit:hierConnections")) {
					parseHierarchicalPorts(element, design);
				} else {
					// ignore for the moment;
				}
			}
			node = node.getNextSibling();
		}
	}

	private void parseComponentInstances(Element parent, Design design) {
		Node node = parent.getFirstChild();

		while (node != null) {
			if (node instanceof Element) {
				Element element = (Element) node;
				String type = element.getTagName();
				if (type.equals("spirit:componentInstance")) {
					parseComponentInstance(element, design);
				}
			}
			node = node.getNextSibling();
		}
	}

	private void parseComponentInstance(Element parent, Design design) {

		ComponentInstance instance = SlamFactory.eINSTANCE
				.createComponentInstance();
		VLNV vlnv = null;
		String instanceName = parseInstanceName(parent);
		instance.setInstanceName(instanceName);

		Node node = parent.getFirstChild();

		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:configurableElementValues")) {
					parseParameters(elt, instance);
				} else if (type.equals("spirit:componentRef")) {
					vlnv = parseCompactVLNV(elt);
				}
			}
			node = node.getNextSibling();
		}

		// Component type is retrieved from vendor extensions if there are any.
		// Otherwise, a generic component is created
		IPXACTDesignVendorExtensions.ComponentDescription description = vendorExtensions
				.getComponentDescription(vlnv.getName());
		String componentType = "Component";
		if (description != null) {
			componentType = description.getComponentType();

		}

		// Creates the component if necessary
		// eClass is retrieved from the component type
		if (design.containsComponent(vlnv)) {
			instance.setComponent(design.getComponent(vlnv));
		} else {
			EPackage ePackage = ComponentPackage.eINSTANCE;
			EClass eClass = (EClass) ePackage.getEClassifier(componentType);
			Component component = (Component) ComponentFactory.eINSTANCE
					.create(eClass);
			component.setVlnv(vlnv);
			instance.setComponent(component);
		}

		design.getComponentInstances().add(instance);
	}

	private String parseInstanceName(Element parent) {
		Node node = parent.getFirstChild();
		String name = "";

		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:instanceName")) {
					name = elt.getTextContent();
				}
			}
			node = node.getNextSibling();
		}

		return name;
	}

	private void parseParameters(Element parent, ParameterizedElement paramElt) {
		Node node = parent.getFirstChild();

		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:configurableElementValue")) {
					String name = elt.getAttribute("spirit:referenceId");
					String value = elt.getTextContent();
					Parameter param = AttributesFactory.eINSTANCE
							.createParameter();
					param.setKey(name);
					param.setValue(value);
					paramElt.getParameters().add(param);
				}
			}
			node = node.getNextSibling();
		}
	}

	private void parseLink(Element parent, Design design) {
		List<String> comItfs = new ArrayList<String>(2);
		List<String> componentInstanceRefs = new ArrayList<String>(2);
		String linkUuid = "";

		Node node = parent.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:name")) {
					linkUuid = elt.getTextContent();
				} else if (type.equals("spirit:displayName")) {
				} else if (type.equals("spirit:description")) {
				} else if (type.equals("spirit:activeInterface")) {
					comItfs.add(elt.getAttribute("spirit:busRef"));
					componentInstanceRefs.add(elt
							.getAttribute("spirit:componentRef"));
				}
			}
			node = node.getNextSibling();
		}

		Link link = LinkFactory.eINSTANCE.createDataLink();
		link.setUuid(linkUuid);
		ComponentInstance sourceInstance = design
				.getComponentInstance(componentInstanceRefs.get(0));
		link.setSourceComponentInstance(sourceInstance);
		ComInterface sourceInterface = sourceInstance.getComponent()
				.getInterface(comItfs.get(0));

		// Creating source interface if necessary
		if (sourceInterface == null) {
			sourceInterface = ComponentFactory.eINSTANCE.createComInterface();
			sourceInterface.setName(comItfs.get(0));
			sourceInstance.getComponent().getInterfaces().add(sourceInterface);
		}
		link.setSourceInterface(sourceInterface);

		ComponentInstance destinationInstance = design
				.getComponentInstance(componentInstanceRefs.get(1));
		link.setDestinationComponentInstance(destinationInstance);
		ComInterface destinationInterface = destinationInstance.getComponent()
				.getInterface(comItfs.get(1));

		// Creating destination interface if necessary
		if (destinationInterface == null) {
			destinationInterface = ComponentFactory.eINSTANCE
					.createComInterface();
			destinationInterface.setName(comItfs.get(1));
			destinationInstance.getComponent().getInterfaces()
					.add(destinationInterface);
		}
		link.setDestinationInterface(destinationInterface);

		// Retrieving parameters from vendor extensions
		LinkDescription linkDescription = vendorExtensions
				.getLinkDescription(linkUuid);
		if (linkDescription != null) {
			for (String key : linkDescription.getParameters().keySet()) {
				Parameter p = AttributesFactory.eINSTANCE.createParameter();
				p.setKey(key);
				p.setValue(linkDescription.getParameters().get(key));
				link.getParameters().add(p);
			}
		}

		design.getLinks().add(link);
	}

	private void parseLinks(Element parent, Design design) {
		Node node = parent.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:interconnection")) {
					parseLink(elt, design);
				}
			}
			node = node.getNextSibling();
		}
	}

	private void parseHierarchicalPort(Element parent, Design design) {

		HierarchyPort port = ComponentFactory.eINSTANCE.createHierarchyPort();

		String externalInterfaceName = parent
				.getAttribute("spirit:interfaceRef");
		ComInterface externalInterface = design.getRefined().getInterface(
				externalInterfaceName);
		// Creating the external interface if nonexistent
		if (externalInterface == null) {
			externalInterface = ComponentFactory.eINSTANCE.createComInterface();
			externalInterface.setName(externalInterfaceName);
		}
		port.setExternalInterface(externalInterface);

		String internalInterfaceName = null;
		String internalComponentInstanceName = null;

		Node node = parent.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:name")) {
				} else if (type.equals("spirit:displayName")) {
				} else if (type.equals("spirit:description")) {
				} else if (type.equals("spirit:activeInterface")) {
					internalInterfaceName = elt.getAttribute("spirit:busRef");
					internalComponentInstanceName = elt
							.getAttribute("spirit:componentRef");
				}
			}
			node = node.getNextSibling();
		}

		ComponentInstance internalComponentInstance = design
				.getComponentInstance(internalComponentInstanceName);
		port.setInternalComponentInstance(internalComponentInstance);
		ComInterface internalInterface = internalComponentInstance
				.getComponent().getInterface(internalInterfaceName);
		// Creating internal interface if necessary
		if (internalInterface == null) {
			internalInterface = ComponentFactory.eINSTANCE.createComInterface();
			internalInterface.setName(internalInterfaceName);
			internalComponentInstance.getComponent().getInterfaces()
					.add(internalInterface);
		}
		port.setInternalInterface(internalInterface);

		design.getHierarchyPorts().add(port);
	}

	private void parseHierarchicalPorts(Element parent, Design design) {
		Node node = parent.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				String type = elt.getTagName();
				if (type.equals("spirit:hierConnection")) {
					parseHierarchicalPort(elt, design);
				}
			}
			node = node.getNextSibling();
		}

	}
}
