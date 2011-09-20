package net.sf.dftools.workflow.test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.sf.dftools.workflow.implement.AbstractTaskImplementation;
import net.sf.dftools.workflow.tools.WorkflowLogger;

import org.eclipse.core.runtime.IProgressMonitor;

public class TestWorkflowTask1 extends AbstractTaskImplementation {

	@Override
	public Map<String, Object> execute(Map<String, Object> inputs,
			Map<String, String> parameters, IProgressMonitor monitor,
			String nodeName) {
		Map<String, Object> outputs = new HashMap<String, Object>();
		WorkflowLogger.getLogger().log(Level.INFO,
				"Executing TestWorkflowTask1; node: " + nodeName);
		outputs.put("superData", "superData1");
		return outputs;
	}

	@Override
	public Map<String, String> getDefaultParameters() {
		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("size", "25");
		parameters.put("duration", "short");
		return parameters;
	}

	@Override
	public String monitorMessage() {
		return "Executing TestWorkflowTask1";
	}

}
