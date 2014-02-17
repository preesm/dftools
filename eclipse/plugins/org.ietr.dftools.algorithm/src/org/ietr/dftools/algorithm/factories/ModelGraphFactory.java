package org.ietr.dftools.algorithm.factories;

import java.util.HashMap;
import java.util.Map;

import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.ietr.dftools.algorithm.model.generic.GenericGraph;
import org.ietr.dftools.algorithm.model.psdf.PSDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;

public class ModelGraphFactory {

	private static Map<String, Class<?>> models = new HashMap<String, Class<?>>();

	static {
		models.put("sdf", SDFGraph.class);
		models.put("psdf", PSDFGraph.class);
		models.put("dag", DirectedAcyclicGraph.class);
	};

	@SuppressWarnings("rawtypes")
	public static AbstractGraph getModel(String model)
			throws InstantiationException, IllegalAccessException {
		Class modelClass = models.get(model);
		if (modelClass == null) {
			modelClass = GenericGraph.class;
		}
		return (AbstractGraph) modelClass.newInstance();
	}
}
