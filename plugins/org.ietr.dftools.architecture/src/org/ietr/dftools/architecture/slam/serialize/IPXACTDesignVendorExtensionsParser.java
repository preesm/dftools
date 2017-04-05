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

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Class managing information stored in design vendor extensions
 *
 * @author mpelcat
 */
public class IPXACTDesignVendorExtensionsParser {

	/**
	 * Class storing a component description in vendor extensions
	 */
	public class ComponentDescription {
		/**
		 * ID representing the component
		 */
		private final String componentRef;

		/**
		 * Type (Operator, Mem...) of the ecore EClass representing the
		 * component
		 */
		private final String componentType;

		/**
		 * Eclipse path of the potential design refining the component
		 */
		private final String refinement;

		/**
		 * Parameters that vary depending on component type
		 */
		private Map<String, String> specificParameters = null;

		public ComponentDescription(final String componentRef, final String componentType, final String refinement) {
			super();
			this.componentRef = componentRef;
			this.componentType = componentType;
			this.refinement = refinement;
			this.specificParameters = new HashMap<>();
		}

		public String getComponentRef() {
			return this.componentRef;
		}

		public String getComponentType() {
			return this.componentType;
		}

		public String getRefinement() {
			return this.refinement;
		}

		public void addSpecificParameter(final String key, final String value) {
			this.specificParameters.put(key, value);
		}

		public String getSpecificParameter(final String key) {
			return this.specificParameters.get(key);
		}

		public Map<String, String> getSpecificParameters() {
			return this.specificParameters;
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

		public LinkDescription(final String uuid, final Boolean directed, final String type) {
			this.linkUuid = uuid;
			this.type = type;
			this.directed = directed;
			this.specificParameters = new HashMap<>();
		}

		public String getLinkUuid() {
			return this.linkUuid;
		}

		public Boolean isDirected() {
			return this.directed;
		}

		public String getType() {
			return this.type;
		}

		public void addSpecificParameter(final String key, final String value) {
			this.specificParameters.put(key, value);
		}

		public String getSpecificParameter(final String key) {
			return this.specificParameters.get(key);
		}

		public Map<String, String> getSpecificParameters() {
			return this.specificParameters;
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

	/**
	 * Parameters of the current design
	 */
	private Map<String, String> designParameters = null;

	public IPXACTDesignVendorExtensionsParser() {
		this.componentDescriptions = new HashMap<>();
		this.linkDescriptions = new HashMap<>();
		this.designParameters = new HashMap<>();
	}

	public Map<String, String> getDesignParameters() {
		return this.designParameters;
	}

	public ComponentDescription getComponentDescription(final String componentRef) {
		return this.componentDescriptions.get(componentRef);
	}

	public Map<String, ComponentDescription> getComponentDescriptions() {
		return this.componentDescriptions;
	}

	public LinkDescription getLinkDescription(final String linkUuid) {
		return this.linkDescriptions.get(linkUuid);
	}

	public Map<String, LinkDescription> getLinkDescriptions() {
		return this.linkDescriptions;
	}

	/**
	 * Parses vendor extensions from the design root element
	 */
	public void parse(final Element root) {
		Node node = root.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element element = (Element) node;
				final String nodeName = node.getNodeName();
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
	public void parseVendorExtensions(final Element parent) {
		Node node = parent.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element element = (Element) node;
				final String nodeName = node.getNodeName();
				if (nodeName.equals("slam:componentDescriptions")) {
					parseComponentDescriptions(element);
				} else if (nodeName.equals("slam:linkDescriptions")) {
					parseLinkDescriptions(element);
				} else if (nodeName.equals("slam:designDescription")) {
					parseDesignDescription(element);
				}
			}
			node = node.getNextSibling();
		}
	}

	/**
	 * Parses description of a graph
	 */
	public void parseDesignDescription(final Element parent) {
		Node node = parent.getFirstChild();

		// Parsing parameters
		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element element = (Element) node;
				final String nodeName = node.getNodeName();
				if (nodeName.equals("slam:parameters")) {
					parseDesignParameters(element);
				}
			}
			node = node.getNextSibling();
		}
	}

	/**
	 * Parses description of a graph
	 */
	public void parseDesignParameters(final Element parent) {
		Node node = parent.getFirstChild();

		// Parsing parameters
		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element element = (Element) node;
				final String nodeName = node.getNodeName();
				if (nodeName.equals("slam:parameter")) {
					parseDesignParameter(element);
				}
			}
			node = node.getNextSibling();
		}
	}

	/**
	 * Parses description of a graph
	 */
	public void parseDesignParameter(final Element parent) {
		final String key = parent.getAttribute("slam:key");
		final String value = parent.getAttribute("slam:value");

		if ((key != null) && !key.isEmpty() && (value != null) && !value.isEmpty()) {
			this.designParameters.put(key, value);
		}
	}

	/**
	 * Parses descriptions of links
	 */
	public void parseLinkDescriptions(final Element parent) {
		Node node = parent.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element element = (Element) node;
				final String nodeName = node.getNodeName();
				if (nodeName.equals("slam:linkDescription")) {
					final String uuid = element.getAttribute("slam:referenceId");
					parseLinkDescription(element, uuid);
				}
			}
			node = node.getNextSibling();
		}
	}

	/**
	 * Parses description of a link
	 */
	public void parseLinkDescription(final Element parent, final String uuid) {

		final Boolean directed = parent.getAttribute("slam:directedLink").equals("directed");
		final String type = parent.getAttribute("slam:linkType");
		final LinkDescription description = new LinkDescription(uuid, directed, type);

		this.linkDescriptions.put(uuid, description);

		// Retrieving known specific parameters
		final NamedNodeMap attributeMap = parent.getAttributes();
		for (int j = 0; j < attributeMap.getLength(); j++) {
			final String name = attributeMap.item(j).getNodeName();
			final String value = attributeMap.item(j).getNodeValue();

			if (name.equals("slam:setupTime")) {
				description.addSpecificParameter(name, value);
			}
		}
	}

	/**
	 * Parses descriptions of components
	 */
	public void parseComponentDescriptions(final Element parent) {
		Node node = parent.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element element = (Element) node;
				final String nodeName = node.getNodeName();
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
	public void parseComponentDescription(final Element parent) {
		final String componentRef = parent.getAttribute("slam:componentRef");
		String componentType = parent.getAttribute("slam:componentType");
		final String refinement = parent.getAttribute("slam:refinement");

		// Specific case of com nodes for which the contention property
		// is contatenated with the type
		String parallelOrContentionNode = "";
		if (componentType.contains("ComNode")) {
			parallelOrContentionNode = componentType.replace("ComNode", "");
			componentType = "ComNode";
		}

		final ComponentDescription description = new ComponentDescription(componentRef, componentType, refinement);

		if (componentType.contains("ComNode")) {
			description.addSpecificParameter("ComNodeType", parallelOrContentionNode);
		} else if (componentType.contains("Mem")) {
			description.addSpecificParameter("slam:size", parent.getAttribute("slam:size"));
		} else if (componentType.contains("Dma")) {
			description.addSpecificParameter("slam:setupTime", parent.getAttribute("slam:setupTime"));
		}

		// Retrieving known specific parameters
		final NamedNodeMap attributeMap = parent.getAttributes();
		for (int j = 0; j < attributeMap.getLength(); j++) {
			final String name = attributeMap.item(j).getNodeName();
			final String value = attributeMap.item(j).getNodeValue();

			if (name.equals("slam:speed")) {
				description.addSpecificParameter(name, value);
			}
		}

		this.componentDescriptions.put(description.getComponentRef(), description);
	}

}
