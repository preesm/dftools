/**
 * 
 */
package net.sf.dftools.architecture.slam;

import java.io.IOException;
import java.util.Map;

import net.sf.dftools.architecture.slam.attributes.AttributesFactory;
import net.sf.dftools.architecture.slam.attributes.Parameter;
import net.sf.dftools.architecture.slam.attributes.VLNV;
import net.sf.dftools.architecture.slam.component.ComInterface;
import net.sf.dftools.architecture.slam.component.Component;
import net.sf.dftools.architecture.slam.component.ComponentFactory;
import net.sf.dftools.architecture.slam.component.HierarchyPort;
import net.sf.dftools.architecture.slam.component.Operator;
import net.sf.dftools.architecture.slam.link.Link;
import net.sf.dftools.architecture.slam.link.LinkFactory;
import net.sf.dftools.architecture.slam.serialize.IPXACTResourceFactoryImpl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * A tester for the EMF generated System-Level Architecture Model
 * 
 * @author mpelcat
 */
public class SlamTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// check that the factory is registered
		// (only happens in command-line mode)
		// ...
		// duck you command line :)
		Map<String, Object> extToFactoryMap = Resource.Factory.Registry.INSTANCE
				.getExtensionToFactoryMap();
		Object instance = extToFactoryMap.get("design");
		if (instance == null) {
			instance = new IPXACTResourceFactoryImpl();
			extToFactoryMap.put("design", instance);
		}

		if (!EPackage.Registry.INSTANCE.containsKey(SlamPackage.eNS_URI)) {
			EPackage.Registry.INSTANCE.put(SlamPackage.eNS_URI,
					SlamPackage.eINSTANCE);
		}

		// Create a new empty resource.
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI
				.createFileURI("d:/test.design"));
		// Create and populate instances.
		Design design = SlamFactory.eINSTANCE.createDesign();
		VLNV vlnv = AttributesFactory.eINSTANCE.createVLNV();
		vlnv.setName("DualCore");
		design.setVlnv(vlnv);

		if (design.getRefined() == null) {
			Component refined = ComponentFactory.eINSTANCE.createComponent();
			design.setRefined(refined);
		}

		Operator x86 = ComponentFactory.eINSTANCE.createOperator();
		design.getComponents().add(x86);
		vlnv = AttributesFactory.eINSTANCE.createVLNV();
		vlnv.setName("x86");
		x86.setVlnv(vlnv);
		ComInterface memItf = ComponentFactory.eINSTANCE.createComInterface();
		memItf.setName("Mem");
		x86.getInterfaces().add(memItf);
		ComInterface ethItf = ComponentFactory.eINSTANCE.createComInterface();
		ethItf.setName("Eth");
		x86.getInterfaces().add(ethItf);
		x86.setOperatorType("processor");

		ComponentInstance uCore0 = SlamFactory.eINSTANCE
				.createComponentInstance();
		design.getComponentInstances().add(uCore0);
		uCore0.setInstanceName("uCore0");
		uCore0.setComponent(x86);

		ComponentInstance uCore1 = SlamFactory.eINSTANCE
				.createComponentInstance();
		design.getComponentInstances().add(uCore1);
		uCore1.setInstanceName("uCore1");
		uCore1.setComponent(x86);

		Component sharedMemory = ComponentFactory.eINSTANCE.createRam();
		design.getComponents().add(sharedMemory);
		vlnv = AttributesFactory.eINSTANCE.createVLNV();
		vlnv.setName("SharedMemory");
		sharedMemory.setVlnv(vlnv);
		ComInterface memItf2 = ComponentFactory.eINSTANCE.createComInterface();
		memItf2.setName("Mem");
		x86.getInterfaces().add(memItf2);

		ComponentInstance uSharedMemory = SlamFactory.eINSTANCE
				.createComponentInstance();
		design.getComponentInstances().add(uSharedMemory);
		uSharedMemory.setInstanceName("uSharedMemory");
		uSharedMemory.setComponent(sharedMemory);

		Link link = LinkFactory.eINSTANCE.createDataLink();
		link.setSourceComponentInstance(uCore0);
		link.setSourceInterface(memItf);
		link.setDestinationComponentInstance(uSharedMemory);
		link.setDestinationInterface(memItf2);
		Parameter linkp = AttributesFactory.eINSTANCE.createParameter();
		linkp.setKey("tutu");
		linkp.setValue("10");
		link.getParameters().add(linkp);
		linkp = AttributesFactory.eINSTANCE.createParameter();
		linkp.setKey("tata");
		linkp.setValue("20");
		link.getParameters().add(linkp);
		design.getLinks().add(link);

		link = LinkFactory.eINSTANCE.createDataLink();
		link.setSourceComponentInstance(uCore1);
		link.setSourceInterface(memItf);
		link.setDestinationComponentInstance(uSharedMemory);
		link.setDestinationInterface(memItf2);
		design.getLinks().add(link);

		// Defining upper level interfaces
		ComInterface supEth0 = ComponentFactory.eINSTANCE.createComInterface();
		supEth0.setName("Eth0");
		design.getRefined().getInterfaces().add(supEth0);

		ComInterface supEth1 = ComponentFactory.eINSTANCE.createComInterface();
		supEth1.setName("Eth1");
		design.getRefined().getInterfaces().add(supEth1);

		HierarchyPort hp = ComponentFactory.eINSTANCE.createHierarchyPort();
		hp.setInternalComponentInstance(uCore0);
		hp.setInternalInterface(ethItf);
		hp.setExternalInterface(supEth0);
		design.getHierarchyPorts().add(hp);

		hp = ComponentFactory.eINSTANCE.createHierarchyPort();
		hp.setInternalComponentInstance(uCore1);
		hp.setInternalInterface(ethItf);
		hp.setExternalInterface(supEth1);
		design.getHierarchyPorts().add(hp);

		resource.getContents().add(design);
		try {
			resource.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Demand load the resource into the resource set.
		ResourceSet resourceSet2 = new ResourceSetImpl();
		Resource resource2 = resourceSet2.getResource(
				URI.createFileURI("d:/test.design"), true);
		// Extract the root object from the resource.
		Design design2 = (Design) resource2.getContents().get(0);
		System.out.println(design2.getVlnv().getName());

		ResourceSet resourceSet3 = new ResourceSetImpl();
		Resource resource3 = resourceSet3.createResource(URI
				.createFileURI("d:/test2.design"));
		resource3.getContents().add(design2);
		try {
			resource3.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main2(String[] args) {
		// Create a resource set to hold the resources.
		ResourceSet resourceSet = new ResourceSetImpl();

		// These two calls are not necessary during Eclipse plugin execution
		// Register the appropriate resource factory
		// to handle all file extensions.
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());

		// Register the package to make it available during loading.
		resourceSet.getPackageRegistry().put(SlamPackage.eNS_URI,
				SlamPackage.eINSTANCE);

		{
			// Create a new empty resource.
			Resource resource = resourceSet.createResource(URI
					.createFileURI("d:/test.txt"));
			// Create and populate instances.
			Design design = SlamFactory.eINSTANCE.createDesign();
			VLNV vlnv = AttributesFactory.eINSTANCE.createVLNV();
			vlnv.setName("test");
			design.setVlnv(vlnv);
			// EObject eo = vlnv.eContainer();

			resource.getContents().add(design);
			try {
				resource.save(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		{
			// Demand load the resource into the resource set.
			Resource resource = resourceSet.getResource(
					URI.createFileURI("d:/test.txt"), true);
			// Extract the root object from the resource.
			Design design = (Design) resource.getContents().get(0);
			design.getVlnv().getName();
		}

	}

}
