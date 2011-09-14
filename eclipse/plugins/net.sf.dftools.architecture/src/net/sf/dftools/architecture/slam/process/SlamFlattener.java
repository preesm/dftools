/**
 * 
 */
package net.sf.dftools.architecture.slam.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.dftools.architecture.slam.ComponentInstance;
import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.SlamFactory;
import net.sf.dftools.architecture.slam.attributes.AttributesFactory;
import net.sf.dftools.architecture.slam.attributes.Parameter;
import net.sf.dftools.architecture.slam.component.ComInterface;
import net.sf.dftools.architecture.slam.component.Component;
import net.sf.dftools.architecture.slam.component.HierarchyPort;
import net.sf.dftools.architecture.slam.link.ControlLink;
import net.sf.dftools.architecture.slam.link.DataLink;
import net.sf.dftools.architecture.slam.link.Link;
import net.sf.dftools.architecture.slam.link.LinkFactory;

import org.eclipse.emf.common.util.EList;

/**
 * Methods to flatten the hierarchy of a System-Level Architecture Model. If
 * multiple refinements are available for a component, the first is selected.
 * 
 * @author mpelcat
 */
public class SlamFlattener {

	/**
	 * Flattens all levels of a hierarchical architecture
	 */
	public void flattenAllLevels(Design design) {

		while (hasHierarchy(design)) {
			flattenUpperLevel(design);
		}
	}

	/**
	 * Flattens n levels of a hierarchical architecture
	 */
	public void flatten(Design design, int n) {
		int i = 0;
		while (i < n) {
			flattenUpperLevel(design);
			i++;
		}
	}

	/**
	 * Flattens the upper level of a hierarchical architecture
	 */
	public void flattenUpperLevel(Design design) {

		// Set of removed subdesigns
		Set<Design> removedSubdesigns = new HashSet<Design>();

		// Replace each instance by its content
		List<ComponentInstance> componentInstances = new ArrayList<ComponentInstance>(
				design.getComponentInstances());

		for (ComponentInstance instance : componentInstances) {
			if (!instance.getComponent().getRefinements().isEmpty()) {
				removedSubdesigns.add(instance.getComponent().getRefinements()
						.get(0));
				replaceInstanceByContent(design, instance);
			}
		}

		// Removing all references to components no more instanciated
		cleanComponentHolder(design, removedSubdesigns);
	}

	private void getAllInstances(Design design,
			Set<ComponentInstance> globalInstances) {

		for (ComponentInstance instance : design.getComponentInstances()) {
			globalInstances.add(instance);
			for (Design subDesign : instance.getComponent().getRefinements()) {
				getAllInstances(subDesign, globalInstances);
			}
		}
	}

	/**
	 * Removing all references to components no more instanciated
	 * 
	 * @param design
	 *            reference design
	 * @param removedSubdesigns
	 *            subdesigns containing instances to eliminate
	 */
	private void cleanComponentHolder(Design design,
			Set<Design> removedSubdesigns) {

		// Getting all instances and their components from the design and its
		// subdesigns
		Set<ComponentInstance> globalInstances = new HashSet<ComponentInstance>();
		Set<Component> globalComponents = new HashSet<Component>();

		getAllInstances(design, globalInstances);
		for (ComponentInstance instance : globalInstances) {
			globalComponents.add(instance.getComponent());
		}

		Set<Component> holderComponents = new HashSet<Component>(design
				.getComponentHolder().getComponents());
		for (Component component : holderComponents) {
			// Remove all references to instances of the removed hierarchy level
			if (!globalComponents.contains(component)) {
				design.getComponentHolder().getComponents().remove(component);
			}
		}
	}

	/**
	 * Replaces a component instance in a design by its content (components and
	 * links)
	 */
	private void replaceInstanceByContent(Design design,
			ComponentInstance instance) {
		// Associates a reference instance in the refinement to each cloned
		// instance in the design.
		Map<ComponentInstance, ComponentInstance> refMap = new HashMap<ComponentInstance, ComponentInstance>();
		Component component = instance.getComponent();
		Design subDesign = component.getRefinements().get(0);

		insertComponentInstancesClones(subDesign.getComponentInstances(),
				design, instance, refMap);
		insertInternalLinksClones(subDesign.getLinks(), design, refMap);

		// Before removing the instance, hierarchical connections are managed if
		// possible (moved from the instance to its content)
		manageHierarchicalLinks(instance, design, subDesign, refMap);

		// Remove the instance and, if needed, the component itself

		// We remove the replaced instance from the top level
		design.getComponentInstances().remove(instance);

		// We remove the replaced instance link from its component
		Component refComponent = instance.getComponent();
		refComponent.getInstances().remove(instance);

		// If the component has no more instance, it is also removed
		if (refComponent.getInstances().isEmpty()) {
			design.getComponentHolder().getComponents().remove(refComponent);
		}
	}

	/**
	 * Links the newly created instances appropriately to respect hierarchy
	 */
	private void manageHierarchicalLinks(ComponentInstance instance,
			Design design, Design subDesign,
			Map<ComponentInstance, ComponentInstance> refMap) {

		// Iterating the upper graph links
		Set<Link> links = new HashSet<Link>(design.getLinks());
		for (Link link : links) {
			if (link.getSourceComponentInstance().equals(instance)) {
				manageSourceHierarchicalLink(link, design, subDesign, refMap);
			} else if (link.getDestinationComponentInstance().equals(instance)) {
				manageDestinationHierarchicalLink(link, design, subDesign,
						refMap);
			}
		}
	}

	/**
	 * Links the newly created instances appropriately to respect hierarchy
	 */
	private void manageSourceHierarchicalLink(Link link, Design design,
			Design subDesign, Map<ComponentInstance, ComponentInstance> refMap) {
		HierarchyPort foundPort = null;

		// Looking for the hierarchy port corresponding to the current upper
		// level link
		for (HierarchyPort port : subDesign.getHierarchyPorts()) {
			if (port.getExternalInterface().equals(link.getSourceInterface())) {
				foundPort = port;
			}
		}

		// In case we found the internal hierarchy port corresponding to the
		// port in the upper graph
		if (foundPort != null) {
			ComponentInstance instanceToConnect = refMap.get(foundPort
					.getInternalComponentInstance());
			ComInterface itf = foundPort.getInternalInterface();
			link.setSourceComponentInstance(instanceToConnect);
			link.setSourceInterface(itf);
		} else {
			// TODO: display error: hierarchy port not found
			design.getLinks().remove(link);
		}
	}

	/**
	 * Links the newly created instances appropriately to respect hierarchy
	 */
	private void manageDestinationHierarchicalLink(Link link, Design design,
			Design subDesign, Map<ComponentInstance, ComponentInstance> refMap) {
		HierarchyPort foundPort = null;

		// Looking for the hierarchy port corresponding to the current upper
		// level link
		for (HierarchyPort port : subDesign.getHierarchyPorts()) {
			if (port.getExternalInterface().equals(
					link.getDestinationInterface())) {
				foundPort = port;
			}
		}

		// In case we found the internal hierarchy port corresponding to the
		// port in the upper graph
		if (foundPort != null) {
			ComponentInstance instanceToConnect = refMap.get(foundPort
					.getInternalComponentInstance());
			ComInterface itf = foundPort.getInternalInterface();
			link.setDestinationComponentInstance(instanceToConnect);
			link.setDestinationInterface(itf);
		} else {
			// TODO: display error: hierarchy port not found
			design.getLinks().remove(link);
		}
	}

	/**
	 * Inserts clones of the given instances in a given design
	 */
	private void insertComponentInstancesClones(
			EList<ComponentInstance> instances, Design design,
			ComponentInstance processedInstance,
			Map<ComponentInstance, ComponentInstance> refMap) {
		for (ComponentInstance originalInstance : instances) {
			String originalName = originalInstance.getInstanceName();
			ComponentInstance newInstance = SlamFactory.eINSTANCE
					.createComponentInstance();
			String newName = getUniqueInstanceName(originalName, design,
					processedInstance.getInstanceName());
			design.getComponentInstance(newName);
			newInstance.setInstanceName(newName);
			newInstance.setComponent(originalInstance.getComponent());
			design.getComponentInstances().add(newInstance);
			refMap.put(originalInstance, newInstance);

			// Duplicates instance parameters
			for (Parameter param : originalInstance.getParameters()) {
				Parameter newParam = AttributesFactory.eINSTANCE
						.createParameter();
				newParam.setKey(param.getKey());
				newParam.setValue(param.getValue());
				newInstance.getParameters().add(newParam);
			}
		}
	}

	/**
	 * Inserts clones of the given links in a given design
	 */
	private void insertInternalLinksClones(EList<Link> links, Design design,
			Map<ComponentInstance, ComponentInstance> refMap) {

		for (Link originalLink : links) {
			Link newLink = null;

			if (originalLink instanceof DataLink) {
				newLink = LinkFactory.eINSTANCE.createDataLink();
			} else if (originalLink instanceof ControlLink) {
				newLink = LinkFactory.eINSTANCE.createControlLink();
				((ControlLink) newLink)
						.setSetupTime(((ControlLink) originalLink)
								.getSetupTime());
			}

			newLink.setDirected(originalLink.isDirected());
			// Choosing a new unique Uuid
			newLink.setUuid(UUID.randomUUID().toString());
			ComponentInstance source = originalLink
					.getSourceComponentInstance();
			ComponentInstance destination = originalLink
					.getDestinationComponentInstance();
			newLink.setSourceComponentInstance(refMap.get(source));
			newLink.setSourceInterface(originalLink.getSourceInterface());
			newLink.setDestinationComponentInstance(refMap.get(destination));
			newLink.setDestinationInterface(originalLink
					.getDestinationInterface());
			design.getLinks().add(newLink);
		}
	}

	/**
	 * Creates a unique name by prefixing the name by the upper design name
	 * 
	 * @param originalName
	 *            the name in the original design
	 * @param design
	 *            the upper desing in which the component is instanciated
	 * @param path
	 *            the path to append to the name
	 */
	private String getUniqueInstanceName(String originalName, Design design,
			String path) {
		String name = path + "/" + originalName;
		int i = 2;

		while (design.getComponentInstance(name) != null) {
			name = originalName + "_" + i;
			i++;
		}

		return name;
	}

	private boolean hasHierarchy(Design design) {

		for (Component component : design.getComponentHolder().getComponents()) {
			if (!component.getRefinements().isEmpty()) {
				return true;
			}
		}

		return false;
	}
}
