
package org.ietr.dftools.architecture.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.ietr.dftools.architecture.slam.Design;
import org.ietr.dftools.architecture.slam.SlamPackage;
import org.ietr.dftools.architecture.slam.process.SlamFlattener;
import org.ietr.dftools.architecture.slam.serialize.IPXACTResourceFactoryImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class SlamTester2 {

  /**
   */
  @Test
  public void testSlam() throws IOException {

    // check that the factory is registered
    // (only happens in command-line mode)
    // ...
    // duck you command line :)
    final Map<String, Object> extToFactoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
    Object instance = extToFactoryMap.get("slam");
    if (instance == null) {
      instance = new IPXACTResourceFactoryImpl();
      extToFactoryMap.put("slam", instance);
    }

    if (!EPackage.Registry.INSTANCE.containsKey(SlamPackage.eNS_URI)) {
      EPackage.Registry.INSTANCE.put(SlamPackage.eNS_URI, SlamPackage.eINSTANCE);
    }

    final File createTempFile = File.createTempFile("output_", ".slam");
    createTempFile.deleteOnExit();

    flatten("./resources/4CoreX86.slam", createTempFile.getAbsolutePath());

    final List<String> readAllLines = Files.readAllLines(createTempFile.toPath());

    Assert.assertNotNull(readAllLines);
    Assert.assertNotEquals(0, readAllLines.size());
  }

  /**
   * Flatten.
   *
   * @param inputTopPath
   *          the input top path
   * @param outputPath
   *          the output path
   */
  private void flatten(final String inputTopPath, final String outputPath) {
    // Demand load the resource into the resource set.
    final ResourceSet resourceSet = new ResourceSetImpl();

    // resourceSet.
    final Resource resource = resourceSet.getResource(URI.createFileURI(inputTopPath), true);
    // Extract the root object from the resource.
    final Design design = (Design) resource.getContents().get(0);
    System.out.println(design.getVlnv().getName());

    final SlamFlattener flattener = new SlamFlattener();
    flattener.flattenAllLevels(design);

    final ResourceSet resourceSet2 = new ResourceSetImpl();
    final Resource resource2 = resourceSet2.createResource(URI.createFileURI(outputPath));
    resource2.getContents().add(design);
    try {
      resource2.save(null);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

}
