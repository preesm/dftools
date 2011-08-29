/**
 * 
 */
package net.sf.dftools.architecture.slam.test;

import java.io.IOException;
import java.util.Map;

import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.SlamPackage;
import net.sf.dftools.architecture.slam.process.SlamFlattener;
import net.sf.dftools.architecture.slam.serialize.IPXACTResourceFactoryImpl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * A tester for the EMF generated System-Level Architecture Model
 * 
 * @author mpelcat
 */
public class SlamTester2 {

	/**
	 * Testing the S-LAM architecture model
	 * @param args
	 */
	public static void main(String[] args) {


		// check that the factory is registered
		// (only happens in command-line mode)
		// ...
		// duck you command line :)
		Map<String, Object> extToFactoryMap = Resource.Factory.Registry.INSTANCE
				.getExtensionToFactoryMap();
		Object instance = extToFactoryMap.get("slam");
		if (instance == null) {
			instance = new IPXACTResourceFactoryImpl();
			extToFactoryMap.put("slam", instance);
		}

		if (!EPackage.Registry.INSTANCE.containsKey(SlamPackage.eNS_URI)) {
			EPackage.Registry.INSTANCE.put(SlamPackage.eNS_URI,
					SlamPackage.eINSTANCE);
		}

		SlamTester2 tester = new SlamTester2();
		//tester.flatten("../../../test/SlamBeta/testArchi/top.slam", "../../../test/SlamBeta/testArchi/top_write.slam");
		tester.flatten("../../../test/SlamBeta/tci6488/top.slam", "../../../test/SlamBeta/tci6488/top_write.slam");

	}
	
	private void flatten(String inputTopPath, String outputPath){
		// Demand load the resource into the resource set.
		ResourceSet resourceSet = new ResourceSetImpl();

		//resourceSet.
		Resource resource = resourceSet.getResource(
				URI.createFileURI(inputTopPath), true);
		// Extract the root object from the resource.
		Design design = (Design) resource.getContents().get(0);
		System.out.println(design.getVlnv().getName());

		SlamFlattener flattener = new SlamFlattener();
		flattener.flattenAllLevels(design);
		
		ResourceSet resourceSet2 = new ResourceSetImpl();
		Resource resource2 = resourceSet2.createResource(URI
				.createFileURI(outputPath));
		resource2.getContents().add(design);
		try {
			resource2.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
