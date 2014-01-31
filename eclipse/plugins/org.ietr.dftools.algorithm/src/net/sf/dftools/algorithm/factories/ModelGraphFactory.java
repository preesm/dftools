package net.sf.dftools.algorithm.factories;

import java.util.HashMap;
import java.util.Map;

import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import net.sf.dftools.algorithm.model.generic.GenericGraph;
import net.sf.dftools.algorithm.model.psdf.PSDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;

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
