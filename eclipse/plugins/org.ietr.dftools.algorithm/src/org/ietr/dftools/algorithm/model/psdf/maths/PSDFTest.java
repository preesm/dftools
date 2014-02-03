package org.ietr.dftools.algorithm.model.psdf.maths;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

import org.ietr.dftools.algorithm.factories.DAGVertexFactory;
import org.ietr.dftools.algorithm.importer.GMLGenericImporter;
import org.ietr.dftools.algorithm.importer.InvalidModelException;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.visitors.DAGTransformation;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;

public class PSDFTest {

	public static void main(String[] args) {
		GMLGenericImporter importer = new GMLGenericImporter();

		try {
			SDFGraph testGraph = (SDFGraph) importer.parse(new File(
					"D:\\Preesm\\trunk\\tests\\PSDF\\Algo\\decimate.graphml"));
			testGraph.validateModel(Logger.getLogger("error log"));
			DAGTransformation<DirectedAcyclicGraph> dageur = new DAGTransformation<DirectedAcyclicGraph>(
					new DirectedAcyclicGraph(), DAGVertexFactory.getInstance());
			try {
				testGraph.accept(dageur);
			} catch (SDF4JException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			@SuppressWarnings("unused")
			DirectedAcyclicGraph dag = dageur.getOutput();
		}catch (SDF4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
