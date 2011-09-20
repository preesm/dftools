package net.sf.dftools.workflow.test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.sf.dftools.workflow.WorkflowException;
import net.sf.dftools.workflow.implement.AbstractScenarioImplementation;
import net.sf.dftools.workflow.tools.WorkflowLogger;

public class TestWorkflowScenario extends AbstractScenarioImplementation {

	@Override
	public Map<String, Object> extractData(String path)
			throws WorkflowException {
		Map<String, Object> outputs = new HashMap<String, Object>();
		WorkflowLogger.getLogger().log(Level.INFO,
				"Retrieving data from scenario");

		outputs.put("algo", "algo1");
		outputs.put("archi", "archi1");
		return outputs;
	}

	@Override
	public String monitorMessage() {
		return "Retrieving data from scenario";
	}

}
