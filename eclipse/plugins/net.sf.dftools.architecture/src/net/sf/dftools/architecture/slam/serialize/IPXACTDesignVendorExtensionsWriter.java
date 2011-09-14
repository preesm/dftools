/**
 * 
 */
package net.sf.dftools.architecture.slam.serialize;

import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.attributes.Parameter;
import net.sf.dftools.architecture.slam.component.ComNode;
import net.sf.dftools.architecture.slam.component.Component;
import net.sf.dftools.architecture.slam.component.Mem;
import net.sf.dftools.architecture.slam.link.ControlLink;
import net.sf.dftools.architecture.slam.link.Link;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class managing information stored in design vendor extensions
 * 
 * @author mpelcat
 */
public class IPXACTDesignVendorExtensionsWriter {

	private Design design;
	
	public IPXACTDesignVendorExtensionsWriter(Design design) {
		this.design = design;
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

		design.getComponentHolder().getComponents();
		for (Component component : design.getComponentHolder().getComponents()) {
			writeComponentDescription(componentDescriptionsElt, component,
					document);
		}

		Element linkDescriptionsElt = document
				.createElement("slam:linkDescriptions");
		vendorExtensionsElt.appendChild(linkDescriptionsElt);

		for (Link link : design.getLinks()) {
			writeLinkDescription(linkDescriptionsElt, link, document);
		}

		Element designDescriptionElt = document
				.createElement("slam:designDescription");
		vendorExtensionsElt.appendChild(designDescriptionElt);

		Element parametersElt = document.createElement("slam:parameters");
		designDescriptionElt.appendChild(parametersElt);

		for (Parameter p : design.getParameters()) {
			writeDesignParameter(parametersElt, p.getKey(),
					p.getValue(), document);
		}
	}

	/**
	 * Writes a parameter of the design
	 */
	public void writeDesignParameter(Element parent, String key, String value,
			Document document) {
		Element parameterElt = document.createElement("slam:parameter");
		parent.appendChild(parameterElt);

		parameterElt.setAttribute("slam:key", key);
		parameterElt.setAttribute("slam:value", value);
	}

	/**
	 * Writes a component description inside a dom element
	 */
	public void writeComponentDescription(Element parent,
			Component component, Document document) {
		
		// Adding as component type the name of the component ecore EClass.
		String componentRef = component.getVlnv().getName();
		String componentType = component.eClass().getName();

		// Communication node type is concatenated if necessary
		if (componentType.equals("ComNode")) {
			if (((ComNode) component).isParallel())
				componentType = "parallel" + componentType;
			else
				componentType = "contention" + componentType;

		}
		
		Element componentElt = document
				.createElement("slam:componentDescription");
		parent.appendChild(componentElt);

		componentElt.setAttribute("slam:componentRef",
				componentRef);
		componentElt.setAttribute("slam:componentType",
				componentType);
		
		String refinementPath = "";
		Design refinement = component.getRefinement();
		if (refinement != null) {
			refinementPath = refinement.getPath();
		}
		
		componentElt.setAttribute("slam:refinement",
				refinementPath);


		// Managing specific component properties
		if (component instanceof ComNode) {
			componentElt.setAttribute("slam:speed", Float.toString(((ComNode) component).getSpeed()));
		} else if (component instanceof Mem) {
			componentElt.setAttribute("slam:size",
							Integer.toString(((Mem) component)
									.getSize()));
		}
	}

	/**
	 * Writes a link description inside a dom element
	 */
	public void writeLinkDescription(Element parent,
			Link link, Document document) {
		Element linkElt = document.createElement("slam:linkDescription");
		parent.appendChild(linkElt);

		linkElt.setAttribute("slam:referenceId", link.getUuid());
		String directed = link.isDirected() ? "directed" : "undirected";
		linkElt.setAttribute("slam:directedLink", directed);
		linkElt.setAttribute("slam:linkType", link.eClass().getName());

		// Managing specific link properties
		if (link instanceof ControlLink) {
			linkElt.setAttribute("slam:setupTime",
					Integer.toString(((ControlLink) link).getSetupTime()));
		}
	}
}