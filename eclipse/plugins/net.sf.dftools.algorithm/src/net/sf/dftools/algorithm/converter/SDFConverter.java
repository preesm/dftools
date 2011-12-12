package net.sf.dftools.algorithm.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.dftools.algorithm.exporter.GMLGenericExporter;
import net.sf.dftools.algorithm.importer.GMLSDFImporter;
import net.sf.dftools.algorithm.importer.InvalidModelException;
import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;

public class SDFConverter {

	public static void main(String[] args) throws FileNotFoundException,
			InvalidModelException {
		List<File> dirs = new ArrayList<File>();
		List<File> files = new ArrayList<File>();
		List<String> convertedPath = new ArrayList<String>();
		String dirPath = "/home/jpiat/development/Method/Dataflow/preesm-tools/preesm/trunk/tests/";
		File root = new File(dirPath);
		dirs.add(root);
		while (dirs.size() > 0) {
			File dir = dirs.get(0);
			dirs.remove(0);
			for (String filePath : dir.list()) {
				File currentFile = new File(dir.getAbsolutePath()
						+ File.separator + filePath);
				if (currentFile.isDirectory()) {
					dirs.add(currentFile);
				} else if (filePath.endsWith(".graphml")) {
					files.add(currentFile);
				}
			}
			while (files.size() > 0) {
				File toTreat = files.get(0);
				GMLSDFImporter importer = new GMLSDFImporter();
				SDFGraph graph = importer.parse(toTreat);
				GMLGenericExporter exporter = new GMLGenericExporter();
				exporter.export((AbstractGraph) graph,
						toTreat.getAbsolutePath());
				files.remove(0);
				/*try {
					boolean hasRefinement = false;
					GMLSDFImporter importer = new GMLSDFImporter();
					SDFGraph graph = importer.parse(toTreat);
					for (AbstractVertex v : graph.vertexSet()) {
						if (v.getRefinement() instanceof AbstractGraph) {
							String rName = ((AbstractGraph) v.getRefinement())
									.getName();
							String rpath = dir.getAbsolutePath()
									+ File.separator + rName + ".graphml";
							if (!convertedPath.contains(rpath)) {
								File rFile = new File(rpath);
								files.add(0, rFile);
								hasRefinement = true;
							}
						}
					}
					if (!hasRefinement) {
						System.out.println("Converting file: "
								+ toTreat.getAbsolutePath());
						GMLGenericExporter exporter = new GMLGenericExporter();
						exporter.export((AbstractGraph) graph,
								toTreat.getAbsolutePath());

					}
				} catch (Exception e) {
					System.out
							.println("Fails to convert file or already converted file: "
									+ toTreat.getAbsolutePath());
					convertedPath.add(toTreat.getAbsolutePath());
					files.remove(0);
				}*/
			}

		}
	}
}
