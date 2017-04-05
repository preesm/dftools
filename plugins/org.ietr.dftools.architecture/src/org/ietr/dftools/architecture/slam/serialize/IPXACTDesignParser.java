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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.ietr.dftools.architecture.slam.ComponentHolder;
import org.ietr.dftools.architecture.slam.ComponentInstance;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.ParameterizedElement;
import org.ietr.dftools.architecture.slam.SlamFactory;
import org.ietr.dftools.architecture.slam.attributes.AttributesFactory;
import org.ietr.dftools.architecture.slam.attributes.Parameter;
import org.ietr.dftools.architecture.slam.attributes.VLNV;
import org.ietr.dftools.architecture.slam.component.ComInterface;
import org.ietr.dftools.architecture.slam.component.ComNode;
import org.ietr.dftools.architecture.slam.component.Component;
import org.ietr.dftools.architecture.slam.component.ComponentFactory;
import org.ietr.dftools.architecture.slam.component.ComponentPackage;
import org.ietr.dftools.architecture.slam.component.Dma;
import org.ietr.dftools.architecture.slam.component.HierarchyPort;
import org.ietr.dftools.architecture.slam.component.Mem;
import org.ietr.dftools.architecture.slam.link.Link;
import org.ietr.dftools.architecture.slam.link.LinkFactory;
import org.ietr.dftools.architecture.slam.link.LinkPackage;
import org.ietr.dftools.architecture.slam.serialize.IPXACTDesignVendorExtensionsParser.LinkDescription;
import org.ietr.dftools.architecture.utils.DomUtil;
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
	 * URI of the last opened file
	 */
	private final URI uri;

	/**
	 * Information needed in the vendor extensions of the design
	 */
	private final IPXACTDesignVendorExtensionsParser vendorExtensions;

	/**
	 * parsed input stream
	 */
	public FileInputStream fileInputStream;

	/**
	 * IPXact parser constructor
	 */
	public IPXACTDesignParser(final URI uri) {
		this.uri = uri;
		this.vendorExtensions = new IPXACTDesignVendorExtensionsParser();
	}

	/**
	 * Parsing a design from and IP XACT design file
	 *
	 * @param inputStream
	 *            the stream obtained from the IP-XACT file
	 * @param componentHolder
	 *            a component holder if inherited from a design upper in the
	 *            hierarchy. null otherwise.
	 * @param refinedComponent
	 *            component refined by the current design
	 *
	 * @return the parsed design
	 */
	public Design parse(final InputStream inputStream, ComponentHolder componentHolder, Component refinedComponent) {
		// The topmost component is initialized to enable storing
		// the hierarchical external interfaces

		if (refinedComponent == null) {
			refinedComponent = ComponentFactory.eINSTANCE.createComponent();
		}

		final Design design = SlamFactory.eINSTANCE.createDesign();
		refinedComponent.getRefinements().add(design);

		// Creates a component holder in case of a top design.
		// It is inherited in the case of a subdesign.
		if (componentHolder == null) {
			componentHolder = SlamFactory.eINSTANCE.createComponentHolder();
		}
		design.setComponentHolder(componentHolder);

		final Document document = DomUtil.parseDocument(inputStream);
		final Element root = document.getDocumentElement();

		// Parsing vendor extensions that will parameterize the model
		this.vendorExtensions.parse(root);

		// Retrieving custom design parameters from vendor extensions
		setDesignParameters(design);

		// Parsing the file content to fill the design
		parseDesign(root, design);

		// Managing the hierarchy: the refinement of the components are set.
		manageRefinements(design);

		try {
			inputStream.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return design;
	}

	private void setDesignParameters(final Design design) {
		final Map<String, String> designParameters = this.vendorExtensions.getDesignParameters();
		for (final String key : designParameters.keySet()) {
			final Parameter p = AttributesFactory.eINSTANCE.createParameter();
			p.setKey(key);
			p.setValue(designParameters.get(key));
			design.getParameters().add(p);
		}
	}

	private void parseDesign(final Element parent, final Design design) {

		final VLNV vlnv = parseVLNV(parent);
		design.setVlnv(vlnv);

		Node node = parent.getFirstChild();

		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element element = (Element) node;
				final String nodeName = node.getNodeName();
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

	private void parseComponentInstances(final Element parent, final Design design) {
		Node node = parent.getFirstChild();

		while (node != null) {
			if (node instanceof Element) {
				final Element element = (Element) node;
				final String type = element.getTagName();
				if (type.equals("spirit:componentInstance")) {
					parseComponentInstance(element, design);
				}
			}
			node = node.getNextSibling();
		}
	}

	private void parseComponentInstance(final Element parent, final Design design) {

		final ComponentInstance instance = SlamFactory.eINSTANCE.createComponentInstance();

		design.getComponentInstances().add(instance);

		VLNV vlnv = null;
		final String instanceName = parseInstanceName(parent);
		instance.setInstanceName(instanceName);

		Node node = parent.getFirstChild();

		while (node != null) {
			if (node instanceof Element) {
				final Element elt = (Element) node;
				final String type = elt.getTagName();
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
		final IPXACTDesignVendorExtensionsParser.ComponentDescription description = this.vendorExtensions.getComponentDescription(vlnv.getName());
		String componentType = "Component";
		if (description != null) {
			componentType = description.getComponentType();
		}

		// Creates the component if necessary
		// eClass is retrieved from the component type
		if (design.containsComponent(vlnv)) {
			instance.setComponent(design.getComponent(vlnv, null));
		} else {
			final EPackage ePackage = ComponentPackage.eINSTANCE;
			final EClass eClass = (EClass) ePackage.getEClassifier(componentType);
			final Component component = design.getComponent(vlnv, eClass);
			instance.setComponent(component);

			try {
				// Special component cases
				if (component instanceof ComNode) {
					((ComNode) component).setSpeed(Float.valueOf(description.getSpecificParameter("slam:speed")));
					if ("contention".equals(description.getSpecificParameter("ComNodeType"))) {
						((ComNode) component).setParallel(false);
					} else {
						((ComNode) component).setParallel(true);
					}
				} else if (component instanceof Mem) {
					((Mem) component).setSize(Integer.valueOf(description.getSpecificParameter("slam:size")));
				} else if (component instanceof Dma) {
					((Dma) component).setSetupTime(Integer.valueOf(description.getSpecificParameter("slam:setupTime")));
				}
			} catch (final NumberFormatException e) {
				e.printStackTrace();
			}

		}

	}

	private void manageRefinements(final Design design) {
		final Set<Component> components = new HashSet<>(design.getComponentHolder().getComponents());
		for (final Component component : components) {
			final IPXACTDesignVendorExtensionsParser.ComponentDescription description = this.vendorExtensions
					.getComponentDescription(component.getVlnv().getName());

			// Looking for a refinement design in the project
			if ((description != null) && !description.getRefinement().isEmpty()) {
				final RefinementList list = new RefinementList(description.getRefinement());

				for (final String refinementStringPath : list.toStringArray()) {

					final String base = this.uri.trimSegments(1).toFileString();
					final Path refinementPath = new Path(base + "/" + refinementStringPath);
					refinementPath.toString();
					final URI refinementURI = URI.createFileURI(refinementPath.toString());
					final File file = new File(refinementURI.toFileString());

					if (file != null) {
						// Read from an input stream
						final IPXACTDesignParser subParser = new IPXACTDesignParser(refinementURI);
						InputStream stream = null;

						try {
							stream = new FileInputStream(file.getPath());
						} catch (final FileNotFoundException e) {
							e.printStackTrace();
						}

						if (stream != null) {
							final Design subDesign = subParser.parse(stream, design.getComponentHolder(), component);

							// A design shares its component holder with its
							// subdesigns
							subDesign.setPath(refinementStringPath);
							component.getRefinements().add(subDesign);

						}
					}
				}
			}
		}
	}

	/**
	 * Returns the list of source folders of the given project as a list of
	 * absolute workspace paths.
	 *
	 * @param container
	 *            a container (a project for instance)
	 * @return a set of folders
	 */
	public static Set<IFolder> getFolders(final IContainer container) throws CoreException {
		final Set<IFolder> folders = new HashSet<>();

		for (final IResource resource : container.members()) {
			if (resource instanceof IFolder) {
				folders.add((IFolder) resource);
			}

			if (resource instanceof IContainer) {
				folders.addAll(IPXACTDesignParser.getFolders((IContainer) resource));
			}
		}

		return folders;
	}

	private String parseInstanceName(final Element parent) {
		Node node = parent.getFirstChild();
		String name = "";

		while (node != null) {
			if (node instanceof Element) {
				final Element elt = (Element) node;
				final String type = elt.getTagName();
				if (type.equals("spirit:instanceName")) {
					name = elt.getTextContent();
				}
			}
			node = node.getNextSibling();
		}

		return name;
	}

	private void parseParameters(final Element parent, final ParameterizedElement paramElt) {
		Node node = parent.getFirstChild();

		while (node != null) {
			if (node instanceof Element) {
				final Element elt = (Element) node;
				final String type = elt.getTagName();
				if (type.equals("spirit:configurableElementValue")) {
					final String name = elt.getAttribute("spirit:referenceId");
					final String value = elt.getTextContent();

					final Parameter param = AttributesFactory.eINSTANCE.createParameter();
					param.setKey(name);
					param.setValue(value);
					paramElt.getParameters().add(param);
				}
			}
			node = node.getNextSibling();
		}
	}

	private void parseLink(final Element parent, final Design design) {
		final List<String> comItfs = new ArrayList<>(2);
		final List<String> componentInstanceRefs = new ArrayList<>(2);
		String linkUuid = "";

		Node node = parent.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				final Element elt = (Element) node;
				final String type = elt.getTagName();
				if (type.equals("spirit:name")) {
					linkUuid = elt.getTextContent();
				} else if (type.equals("spirit:displayName")) {
				} else if (type.equals("spirit:description")) {
				} else if (type.equals("spirit:activeInterface")) {
					comItfs.add(elt.getAttribute("spirit:busRef"));
					componentInstanceRefs.add(elt.getAttribute("spirit:componentRef"));
				}
			}
			node = node.getNextSibling();
		}

		// Retrieving parameters from vendor extensions
		final LinkDescription linkDescription = this.vendorExtensions.getLinkDescription(linkUuid);

		if (linkDescription != null) {

			String linkType = "DataLink";

			if (!linkDescription.getType().isEmpty()) {
				linkType = linkDescription.getType();
			}

			final EPackage eLinkPackage = LinkPackage.eINSTANCE;
			final EClass _class = (EClass) eLinkPackage.getEClassifier(linkType);

			// Creating the link with appropriate type
			final Link link = (Link) LinkFactory.eINSTANCE.create(_class);

			link.setDirected(linkDescription.isDirected());
			link.setUuid(linkUuid);
			final ComponentInstance sourceInstance = design.getComponentInstance(componentInstanceRefs.get(0));
			link.setSourceComponentInstance(sourceInstance);
			ComInterface sourceInterface = sourceInstance.getComponent().getInterface(comItfs.get(0));

			// Creating source interface if necessary
			if (sourceInterface == null) {
				sourceInterface = ComponentFactory.eINSTANCE.createComInterface();
				sourceInterface.setName(comItfs.get(0));
				sourceInstance.getComponent().getInterfaces().add(sourceInterface);
			}
			link.setSourceInterface(sourceInterface);

			final ComponentInstance destinationInstance = design.getComponentInstance(componentInstanceRefs.get(1));
			link.setDestinationComponentInstance(destinationInstance);
			ComInterface destinationInterface = destinationInstance.getComponent().getInterface(comItfs.get(1));

			// Creating destination interface if necessary
			if (destinationInterface == null) {
				destinationInterface = ComponentFactory.eINSTANCE.createComInterface();
				destinationInterface.setName(comItfs.get(1));
				destinationInstance.getComponent().getInterfaces().add(destinationInterface);
			}
			link.setDestinationInterface(destinationInterface);

			design.getLinks().add(link);
		}

	}

	private void parseLinks(final Element parent, final Design design) {
		Node node = parent.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				final Element elt = (Element) node;
				final String type = elt.getTagName();
				if (type.equals("spirit:interconnection")) {
					parseLink(elt, design);
				}
			}
			node = node.getNextSibling();
		}
	}

	private void parseHierarchicalPort(final Element parent, final Design design) {

		final HierarchyPort port = ComponentFactory.eINSTANCE.createHierarchyPort();

		final String externalInterfaceName = parent.getAttribute("spirit:interfaceRef");
		ComInterface externalInterface = design.getRefined().getInterface(externalInterfaceName);
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
				final Element elt = (Element) node;
				final String type = elt.getTagName();
				if (type.equals("spirit:name")) {
				} else if (type.equals("spirit:displayName")) {
				} else if (type.equals("spirit:description")) {
				} else if (type.equals("spirit:activeInterface")) {
					internalInterfaceName = elt.getAttribute("spirit:busRef");
					internalComponentInstanceName = elt.getAttribute("spirit:componentRef");
				}
			}
			node = node.getNextSibling();
		}

		final ComponentInstance internalComponentInstance = design.getComponentInstance(internalComponentInstanceName);
		port.setInternalComponentInstance(internalComponentInstance);
		ComInterface internalInterface = internalComponentInstance.getComponent().getInterface(internalInterfaceName);

		// Creating internal interface if necessary
		if (internalInterface == null) {
			internalInterface = ComponentFactory.eINSTANCE.createComInterface();
			internalInterface.setName(internalInterfaceName);
			internalComponentInstance.getComponent().getInterfaces().add(internalInterface);
		}
		port.setInternalInterface(internalInterface);

		design.getHierarchyPorts().add(port);
	}

	private void parseHierarchicalPorts(final Element parent, final Design design) {
		Node node = parent.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				final Element elt = (Element) node;
				final String type = elt.getTagName();
				if (type.equals("spirit:hierConnection")) {
					parseHierarchicalPort(elt, design);
				}
			}
			node = node.getNextSibling();
		}

	}
}
