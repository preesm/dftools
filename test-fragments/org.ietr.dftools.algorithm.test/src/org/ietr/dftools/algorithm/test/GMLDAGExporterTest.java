package org.ietr.dftools.algorithm.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.ietr.dftools.algorithm.exporter.GMLDAGExporter;
import org.ietr.dftools.algorithm.model.dag.DAGEdge;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.ietr.dftools.algorithm.model.dag.types.DAGDefaultEdgePropertyType;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class GMLDAGExporterTest {

  /**
   * Creates a graph to test the Explorer.
   *
   * @return The created Graph
   */
  private DirectedAcyclicGraph createTestComGraph() {

    final DirectedAcyclicGraph graph = new DirectedAcyclicGraph();

    // test_com_basique
    final DAGVertex sensorInt = new DAGVertex();
    sensorInt.setName("1");
    graph.addVertex(sensorInt);

    final DAGVertex gen5 = new DAGVertex();
    gen5.setName("Gen5");
    graph.addVertex(gen5);

    final DAGVertex recopie5 = new DAGVertex();
    recopie5.setName("recopie_5");
    graph.addVertex(recopie5);

    final DAGVertex acqData = new DAGVertex();
    acqData.setName("acq_data");
    graph.addVertex(acqData);

    final DAGEdge sensGen = graph.addEdge(sensorInt, gen5);
    sensGen.setWeight(new DAGDefaultEdgePropertyType(8));

    final DAGEdge genRec = graph.addEdge(gen5, recopie5);
    genRec.setWeight(new DAGDefaultEdgePropertyType(100));

    final DAGEdge genAcq = graph.addEdge(gen5, acqData);
    genAcq.setWeight(new DAGDefaultEdgePropertyType(2));

    final DAGEdge recAcq = graph.addEdge(recopie5, acqData);
    recAcq.setWeight(new DAGDefaultEdgePropertyType(1000));
    return graph;
  }

  /**
   */
  @Test
  public void testExport() throws IOException {
    final DirectedAcyclicGraph graph = createTestComGraph();
    final GMLDAGExporter exporter = new GMLDAGExporter();
    exporter.exportGraph(graph);

    final File createTempFile = File.createTempFile("export_test_", ".graphml");
    createTempFile.deleteOnExit();

    exporter.transform(new FileOutputStream(createTempFile));

    final List<String> readAllLines = Files.readAllLines(createTempFile.toPath());

    Assert.assertNotNull(readAllLines);
    Assert.assertNotEquals(0, readAllLines.size());
  }

}
