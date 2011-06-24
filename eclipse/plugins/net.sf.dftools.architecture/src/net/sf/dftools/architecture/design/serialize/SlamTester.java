/**
 * 
 */
package net.sf.dftools.architecture.design.serialize;

import java.io.IOException;

import net.sf.dftools.architecture.slam.Design;
import net.sf.dftools.architecture.slam.SlamFactory;
import net.sf.dftools.architecture.slam.SlamPackage;
import net.sf.dftools.architecture.slam.attributes.AttributesFactory;
import net.sf.dftools.architecture.slam.attributes.VLNV;
import org.eclipse.emf.common.util.URI;
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
			//EObject eo = vlnv.eContainer();

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
