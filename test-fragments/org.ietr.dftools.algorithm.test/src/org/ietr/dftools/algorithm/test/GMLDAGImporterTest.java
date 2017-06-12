
package org.ietr.dftools.algorithm.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import org.ietr.dftools.algorithm.exporter.GMLDAGExporter;
import org.ietr.dftools.algorithm.exporter.Key;
import org.ietr.dftools.algorithm.importer.GMLDAGImporter;
import org.ietr.dftools.algorithm.importer.InvalidModelException;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class GMLDAGImporterTest {

  /**
   * Test method.
   *
   * @throws IOException
   *
   */
  @Test
  public void testImport() throws InvalidModelException, IOException {
    final String inputDagXMLPath = "./resources/outDAG.xml";

    final GMLDAGImporter importer = new GMLDAGImporter();
    final DirectedAcyclicGraph graph = importer.parse(new File(inputDagXMLPath));
    final Map<String, List<Key>> keySet = importer.getKeySet();

    final File createTempFile = File.createTempFile("export_test_", ".xml");
    createTempFile.deleteOnExit();

    final GMLDAGExporter exporter = new GMLDAGExporter();
    exporter.setKeySet(keySet);
    exporter.exportGraph(graph);
    final FileOutputStream out = new FileOutputStream(createTempFile.getAbsolutePath());
    exporter.transform(out);
    out.close();

    final List<String> readAllLines = Files.readAllLines(createTempFile.toPath());

    Assert.assertNotNull(readAllLines);
    Assert.assertNotEquals(0, readAllLines.size());
  }

}
