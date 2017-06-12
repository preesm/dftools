package org.ietr.dftools.algorithm.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.ietr.dftools.algorithm.exporter.GMLGenericExporter;
import org.ietr.dftools.algorithm.importer.GMLSDFImporter;
import org.ietr.dftools.algorithm.importer.InvalidModelException;
import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class GMLSDFImporterTest {

  /**
   */
  @Test
  public void testImportStereo() throws InvalidModelException, IOException {
    new ArrayList<File>();
    final GMLSDFImporter importer = new GMLSDFImporter();
    final String filePath = "./resources/stereo_top.graphml";
    final SDFGraph graph = importer.parse(new File(filePath));
    System.out.println("Graph " + graph + " parsed \n");

    final File createTempFile = File.createTempFile("export_test_", ".graphml");
    createTempFile.deleteOnExit();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    final AbstractGraph<AbstractVertex<?>, AbstractEdge<?, ?>> graph2 = (AbstractGraph) graph;

    final GMLGenericExporter exporter = new GMLGenericExporter();
    exporter.export(graph2, createTempFile.getAbsolutePath());

    final List<String> readAllLines = Files.readAllLines(createTempFile.toPath());

    Assert.assertNotNull(readAllLines);
    Assert.assertNotEquals(0, readAllLines.size());

  }

  /**
   */
  @Test
  public void testImportStereoYUV() throws InvalidModelException, IOException {
    new ArrayList<File>();
    final GMLSDFImporter importer = new GMLSDFImporter();
    final String filePath = "./resources/yuv_stereo_top.graphml";
    final SDFGraph graph = importer.parse(new File(filePath));
    System.out.println("Graph " + graph + " parsed \n");

    final File createTempFile = File.createTempFile("export_test_", ".graphml");
    createTempFile.deleteOnExit();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    final AbstractGraph<AbstractVertex<?>, AbstractEdge<?, ?>> graph2 = (AbstractGraph) graph;

    final GMLGenericExporter exporter = new GMLGenericExporter();
    exporter.export(graph2, createTempFile.getAbsolutePath());

    final List<String> readAllLines = Files.readAllLines(createTempFile.toPath());

    Assert.assertNotNull(readAllLines);
    Assert.assertNotEquals(0, readAllLines.size());

  }

}
