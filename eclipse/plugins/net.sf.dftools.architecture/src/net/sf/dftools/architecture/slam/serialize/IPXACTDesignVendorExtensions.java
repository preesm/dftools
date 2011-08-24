/**
 * 
 */
package net.sf.dftools.architecture.slam.serialize;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Class managing information stored in design vendor extensions
 * 
 * @author mpelcat
 */
public class IPXACTDesignVendorExtensions {

	/**
	 * Class storing a component description in vendor extensions
	 */
	public class ComponentDescription {
		/**
		 * ID representing the component
		 */
		private String componentRef;

		/**
		 * Type (Operator, Ram...) of the ecore EClass representing the
		 * component
		 */
		private String componentType;

		/**
		 * Eclipse path of the potential design refining the component
		 */
		private String refinement;

		/**
		 * Parameters that vary depending on component type
		 */
		private Map<String, String> specificParameters = null;

		public ComponentDescription(String componentRef, String componentType,
				String refinement) {
			super();
			this.componentRef = componentRef;
			this.componentType = componentType;
			this.refinement = refinement;
			specificParameters = new HashMap<String, String>();
		}

		public String getComponentRef() {
			return componentRef;
		}

		public String getComponentType() {
			return componentType;
		}

		public String getRefinement() {
			return refinement;
		}

		public void addSpecificParameter(String key, String value) {
			specificParameters.put(key, value);
		}

		public String getSpecificParameter(String key) {
			return specificParameters.get(key);
		}

		public Map<String, String> getSpecificParameters() {
			return specificParameters;
		}

	}

	/**
	 * Class storing a link description in vendor extensions
	 */
	public class LinkDescription {
		// Referencing the link
		private String linkUuid = "";

		private Boolean directed = false;

		private String type = "";

		/**
		 * Parameters that vary depending on component type
		 */
		private Map<String, String> specificParameters = null;

		public LinkDescription(String uuid, Boolean directed, String type) {
			linkUuid = uuid;
			this.type = type;
			this.directed = directed;
			specificParameters = new HashMap<String, String>();
		}

		public String getLinkUuid() {
			return linkUuid;
		}

		public Boolean isDirected() {
			return directed;
		}

		public String getType() {
			return type;
		}

		public void addSpecificParameter(String key, String value) {
			specificParameters.put(key, value);
		}

		public String getSpecificParameter(String key) {
			return specificParameters.get(key);
		}

		public Map<String, String> getSpecificParameters() {
			return specificParameters;
		}
	}

	/**
	 * Description associated to each component
	 */
	private Map<String, ComponentDescription> componentDescriptions = null;

	/**
	 * Description associated to each link referenced by uuid (unique id)
	 */
	private Map<String, LinkDescription> linkDescriptions = null;

	public IPXACTDesignVendorExtensions() {
		componentDescriptions = new HashMap<String, ComponentDescription>();
		linkDescriptions = new HashMap<String, LinkDescription>();
	}

	public ComponentDescription getComponentDescription(String componentRef) {
		return componentDescriptions.get(componentRef);
	}

	public Map<String, ComponentDescription> getComponentDescriptions() {
		return componentDescriptions;
	}

	public LinkDescription getLinkDescription(String linkUuid) {
		return linkDescriptions.get(linkUuid);
	}

	public Map<String, LinkDescription> getLinkDescriptions() {
		return linkDescriptions;
	}

	/**
	 * Parses vendor extensions from the design root element
	 */
	public void parse(Element root) {
		Node node = root.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals("spirit:vendorExtensions")) {
					parseVendorExtensions(element);
				}
			}
			node = node.getNextSibling();
		}
	}

	/**
	 * Parses vendor extensions from the vendor extensions element
	 */
	public void parseVendorExtensions(Element parent) {
		Node node = parent.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals("slam:componentDescriptions")) {
					parseComponentDescriptions(element);
				} else if (nodeName.equals("slam:linkDescriptions")) {
					parseLinkDescriptions(element);
				}
			}
			node = node.getNextSibling();
		}
	}

	/**
	 * Parses descriptions of links
	 */
	public void parseLinkDescriptions(Element parent) {
		Node node = parent.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals("slam:linkDescription")) {
					String uuid = element.getAttribute("slam:referenceId");
					parseLinkDescription(element, uuid);
				}
			}
			node = node.getNextSibling();
		}
	}

	/**
	 * Parses description of a link
	 */
	public void parseLinkDescription(Element parent, String uuid) {

		Boolean directed = parent.getAttribute("slam:directedLink").equals(
				"directed");
		String type = parent.getAttribute("slam:linkType");
		LinkDescription description = new LinkDescription(uuid, directed, type);

		linkDescriptions.put(uuid, description);
		
		// Retrieving known specific parameters
		NamedNodeMap attributeMap = parent.getAttributes();
		for (int j = 0; j < attributeMap.getLength(); j++) {
	        String name = attributeMap.item(j).getNodeName();
	        String value = attributeMap.item(j).getNodeValue();
	        
	        if(name.equals("slam:setupTime")){
	        	description.addSpecificParameter(name, value);
	        }
	    }
	}

	/**
	 * Parses descriptions of components
	 */
	public void parseComponentDescriptions(Element parent) {
		Node node = parent.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals("slam:componentDescription")) {
					parseComponentDescription(element);
				}
			}
			node = node.getNextSibling();
		}
	}

	/**
	 * Parses descriptions of a component
	 */
	public void parseComponentDescription(Element parent) {
		String componentRef = parent.getAttribute("slam:componentRef");
		String componentType = parent.getAttribute("slam:componentType");
		String refinement = parent.getAttribute("slam:refinement");

		// Specific case of com nodes for which the contention property
		// is contatenated with the type
		String parallelOrContentionNode = "";
		if (componentType.contains("ComNode")) {
			parallelOrContentionNode = componentType.replace("ComNode", "");
			componentType = "ComNode";
		}

		ComponentDescription description = new ComponentDescription(
				componentRef, componentType, refinement);

		if (componentType.contains("ComNode")) {
			description.addSpecificParameter("ComNodeType",
					parallelOrContentionNode);
		}

		// Retrieving known specific parameters
		NamedNodeMap attributeMap = parent.getAttributes();
		for (int j = 0; j < attributeMap.getLength(); j++) {
	        String name = attributeMap.item(j).getNodeName();
	        String value = attributeMap.item(j).getNodeValue();
	        
	        if(name.equals("slam:speed")){
	        	description.addSpecificParameter(name, value);
	        }
	    }

		componentDescriptions.put(description.getComponentRef(), description);
	}

	/**
	 * Writes the vendor extension inside a dom element
	 */
	public void write(Element parent, Document document) {
		Element vendorExtensionsElt = document
				.createElement("spirit:vendorExtensions");
		parent.appendChild(vendorExtensionsElt);

		Element componentDescriptionsElt = document
				.createElement("slam:componentDescriptions");
		vendorExtensionsElt.appendChild(componentDescriptionsElt);

		for (ComponentDescription description : componentDescriptions.values()) {
			writeComponentDescription(componentDescriptionsElt, description,
					document);
		}

		Element linkDescriptionsElt = document
				.createElement("slam:linkDescriptions");
		vendorExtensionsElt.appendChild(linkDescriptionsElt);

		for (LinkDescription description : linkDescriptions.values()) {
			writeLinkDescription(linkDescriptionsElt, description, document);
		}
	}

	/**
	 * Writes a component description inside a dom element
	 */
	public void writeComponentDescription(Element parent,
			ComponentDescription description, Document document) {
		Element componentElt = document
				.createElement("slam:componentDescription");
		parent.appendChild(componentElt);

		componentElt.setAttribute("slam:componentRef",
				description.getComponentRef());
		componentElt.setAttribute("slam:componentType",
				description.getComponentType());
		componentElt.setAttribute("slam:refinement",
				description.getRefinement());

		Map<String, String> parameters = description.getSpecificParameters();
		if (!parameters.isEmpty()) {
			for (String key : parameters.keySet()) {
				componentElt.setAttribute(key, parameters.get(key));
			}
		}
	}

	/**
	 * Writes a link description inside a dom element
	 */
	public void writeLinkDescription(Element parent,
			LinkDescription description, Document document) {
		Element linkElt = document.createElement("slam:linkDescription");
		parent.appendChild(linkElt);

		linkElt.setAttribute("slam:referenceId", description.getLinkUuid());
		String directed = description.isDirected() ? "directed" : "undirected";
		linkElt.setAttribute("slam:directedLink", directed);
		linkElt.setAttribute("slam:linkType", description.getType());

		Map<String, String> parameters = description.getSpecificParameters();
		if (!parameters.isEmpty()) {
			for (String key : parameters.keySet()) {
				linkElt.setAttribute(key, parameters.get(key));
			}
		}
	}
}