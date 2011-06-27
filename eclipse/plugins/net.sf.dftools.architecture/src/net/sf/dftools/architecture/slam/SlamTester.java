/**
 * 
 */
package net.sf.dftools.architecture.slam;

import java.io.IOException;
import java.util.Map;

import net.sf.dftools.architecture.slam.attributes.AttributesFactory;
import net.sf.dftools.architecture.slam.attributes.VLNV;
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
			EPackage.Registry.INSTANCE.put(SlamPackage.eNS_URI, SlamPackage.eINSTANCE);
		}

		// Create a new empty resource.
		//ResourceSet resourceSet = new ResourceSetImpl();
		//Resource resource = resourceSet.createResource(URI
		//		.createFileURI("d:/test.design"));
		// Create and populate instances.
		Design design = SlamFactory.eINSTANCE.createDesign();
		VLNV vlnv = AttributesFactory.eINSTANCE.createVLNV();
		vlnv.setName("DualCore");
		design.setVlnv(vlnv);
/*
		if(!design.containsComponent("x86")){
			Component x86 = ComponentFactory.eINSTANCE.createOperator();
			design.getComponents().add(x86);
		}
		else{
			Component x86 = design.getComponent("x86");
		}
		
		if(!design.containsComponentInstance("uCore0")){
			ComponentInstance uCore0 = SlamFactory.eINSTANCE.createComponentInstance();
			design.getComponentInstances().add(uCore0);
		}
		else{
			ComponentInstance uCore0 = design.getComponentInstance("uCore0");
		}
		

		resource.getContents().add(design);
		try {
			resource.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Demand load the resource into the resource set.
		ResourceSet resourceSet2 = new ResourceSetImpl();
		//Resource resource2 = resourceSet2.getResource(
		//		URI.createFileURI("d:/test.design"), true);
		// Extract the root object from the resource.
		//Design design2 = (Design) resource2.getContents().get(0);
		//System.out.println(design2.getVlnv().getName());*/
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
