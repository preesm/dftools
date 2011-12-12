package net.sf.dftools.algorithm.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.dftools.algorithm.exporter.GMLGenericExporter;
import net.sf.dftools.algorithm.importer.old.GMLSDFImporterV1;
import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;

/**
 * wrapper for different versions
 * 
 * @author jpiat
 * 
 */
public class GMLSDFImporter extends GMLModelParserWrapper<SDFGraph> {

	private GMLImporter<?, ?, ?> trueImporter;

	/**
	 * COnstructs a new importer for SDF graphs
	 */
	public GMLSDFImporter() {
		trueImporter = new GMLGenericImporter();
	}

	public SDFGraph parse(File f) throws InvalidModelException,
			FileNotFoundException {
		try {
			return (SDFGraph) trueImporter.parse(f);
		} catch (Exception e1) {
			trueImporter = new GMLSDFImporterV1();
			try {
				System.out
						.println("Parsing using generic parser failed, trying specialized parser\n");
				return (SDFGraph) trueImporter.parse(f);
			} catch (Exception e2) {
				throw new InvalidModelException(
						"Cannot parse file. Parsing failed with exception "
								+ e2.getMessage());
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException,
			InvalidModelException {
		List<File> dirs = new ArrayList<File>();
		GMLSDFImporter importer = new GMLSDFImporter();
		SDFGraph graph = importer
				.parse(new File(
						"/home/jpiat/development/Method/Dataflow/preesm-tools/preesm/trunk/tests/IDCT2D/Algo/IDCT2D_basic.graphml"));
		System.out.println("Graph " + graph + " parsed \n");
		GMLGenericExporter exporter = new GMLGenericExporter();
		exporter.export((AbstractGraph) graph, "/home/jpiat/development/Method/Dataflow/preesm-tools/preesm/trunk/tests/IDCT2D/Algo/IDCT2D_basic.graphml");
	}

	@Override
	public SDFGraph parse(InputStream input, String path)
			throws InvalidModelException, FileNotFoundException {
		try {
			return (SDFGraph) trueImporter.parse(input, path);
		} catch (Exception e1) {
			trueImporter = new GMLSDFImporterV1();
			try {
				System.out
						.println("Parsing using generic parser failed, trying specialized parser\n");
				return (SDFGraph) trueImporter.parse(input, path);
			} catch (Exception e2) {
				throw new InvalidModelException(
						"Cannot parse file. Parsing failed with exception "
								+ e2.getMessage());
			}
		}
	}

}
