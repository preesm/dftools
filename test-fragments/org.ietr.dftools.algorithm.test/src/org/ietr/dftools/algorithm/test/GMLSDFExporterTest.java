package org.ietr.dftools.algorithm.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.ietr.dftools.algorithm.exporter.GMLGenericExporter;
import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.parameters.Argument;
import org.ietr.dftools.algorithm.model.parameters.Parameter;
import org.ietr.dftools.algorithm.model.parameters.Variable;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class GMLSDFExporterTest {

  /**
   * Creates a graph to test the Explorer.
   *
   * @return The created Graph
   */
  private SDFGraph createTestComGraph() {

    final SDFGraph graph = new SDFGraph();

    // test_com_basique
    final SDFInterfaceVertex sensorInt = new SDFSourceInterfaceVertex();
    sensorInt.setName("sensor_Int");
    graph.addVertex(sensorInt);

    final SDFVertex gen5 = new SDFVertex();
    gen5.setName("Gen5");
    graph.addVertex(gen5);

    final SDFVertex recopie5 = new SDFVertex();
    recopie5.setName("recopie_5");
    graph.addVertex(recopie5);

    final SDFInterfaceVertex acqData = new SDFSinkInterfaceVertex();
    acqData.setName("acq_data");
    graph.addVertex(acqData);

    gen5.addArgument(new Argument("NB_COPY", "100"));

    final SDFEdge sensGen = graph.addEdge(sensorInt, gen5);
    sensGen.setProd(new SDFIntEdgePropertyType(1));
    sensGen.setCons(new SDFIntEdgePropertyType(1));

    final SDFEdge genRec = graph.addEdge(gen5, recopie5);
    genRec.setProd(new SDFIntEdgePropertyType(2));
    genRec.setCons(new SDFIntEdgePropertyType(3));

    final SDFEdge genAcq = graph.addEdge(gen5, acqData);
    genAcq.setProd(new SDFIntEdgePropertyType(1));
    genAcq.setCons(new SDFIntEdgePropertyType(1));

    final SDFEdge recAcq = graph.addEdgeWithInterfaces(recopie5, acqData);
    recAcq.setProd(new SDFIntEdgePropertyType(3));
    recAcq.setCons(new SDFIntEdgePropertyType(2));

    graph.addParameter(new Parameter("SIZE"));
    graph.addParameter(new Parameter("NB_COPY"));

    graph.addVariable(new Variable("a", "5"));
    graph.addVariable(new Variable("b", "10"));

    return graph;
  }

  /**
   */
  @Test
  public void testExport() throws IOException {
    final SDFGraph sdfGraph = createTestComGraph();
    final GMLGenericExporter exporter = new GMLGenericExporter();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    final AbstractGraph<AbstractVertex<?>, AbstractEdge<?, ?>> graph = (AbstractGraph) sdfGraph;

    final File createTempFile = File.createTempFile("export_test_", ".graphml");
    createTempFile.deleteOnExit();
    exporter.export(graph, createTempFile.getAbsolutePath());

    final List<String> readAllLines = Files.readAllLines(createTempFile.toPath());

    Assert.assertNotNull(readAllLines);
    Assert.assertNotEquals(0, readAllLines.size());
  }

}
