package org.ietr.dftools.workflow;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import org.eclipse.core.runtime.IProgressMonitor;
import org.ietr.dftools.workflow.elements.AbstractWorkflowNode;
import org.ietr.dftools.workflow.elements.ScenarioNode;
import org.ietr.dftools.workflow.elements.TaskNode;
import org.ietr.dftools.workflow.elements.Workflow;
import org.ietr.dftools.workflow.elements.WorkflowEdge;
import org.ietr.dftools.workflow.implement.AbstractScenarioImplementation;
import org.ietr.dftools.workflow.implement.AbstractTaskImplementation;
import org.ietr.dftools.workflow.implement.AbstractWorkflowNodeImplementation;

/**
 * This abstract class provides methods to check and execute a workflow. A
 * workflow consists of several transformation plug-ins tasks applied to a
 * scenario.
 * 
 * Log method for check and execution are to be fixed by concrete subclasses,
 * depending on available interfaces
 * 
 * @author mpelcat
 * @author cguy
 *
 */
public abstract class AbstractWorkflowExecutor {

	/**
	 * Ports with this name are ignored when exchanging data. They just specify
	 * precedence.
	 */
	public static String IGNORE_PORT_NAME = "void";

	/**
	 * Checks the existence of all task and scenario classes and sets the
	 * classes in the workflow nodess
	 */
	public boolean check(Workflow workflow, IProgressMonitor monitor) {

		if (monitor != null)
			monitor.subTask("Checking workflow...");

		boolean workflowOk = true;
		for (AbstractWorkflowNode node : workflow.vertexSet()) {
			if (node.isScenarioNode()) {
				workflowOk = ((ScenarioNode) node).getExtensionInformation();

				// The plugin declaring the scenario class was not found
				if (!workflowOk) {
					log(Level.SEVERE, "Workflow.FailedFindScenarioPlugin", ((ScenarioNode) node).getScenarioId());
					return false;
				}
			} else if (node.isTaskNode()) {
				// Testing only connected nodes
				if (!workflow.edgesOf(node).isEmpty()) {
					workflowOk = ((TaskNode) node).getExtensionInformation();

					// The plugin declaring the task class was not found
					if (!workflowOk) {
						log(Level.SEVERE, "Workflow.FailedFindTaskPlugin", ((TaskNode) node).getTaskId());
						return false;
					}
				}
			}
		}

		// Check the workflow
		if (monitor != null)
			monitor.worked(1);

		return workflowOk;
	}

	/**
	 * Checks that the workflow scenario node edges fit the task prototype
	 */
	public boolean checkScenarioPrototype(ScenarioNode scenarioNode, Workflow workflow) {
		AbstractScenarioImplementation scenario = scenarioNode.getScenario();
		Set<String> outputPorts = new HashSet<String>();

		// There may be several output edges with same data type (sharing same
		// port). All output names are retrieved here.
		for (WorkflowEdge edge : workflow.outgoingEdgesOf(scenarioNode)) {
			if (!outputPorts.contains(edge.getSourcePort()) && !edge.getSourcePort().equals(IGNORE_PORT_NAME)) {
				outputPorts.add(edge.getSourcePort());
			}
		}

		// The scenario node prototype is compared to the output port names
		if (!scenario.acceptOutputs(outputPorts)) {
			log(Level.SEVERE, "Workflow.WrongScenarioPrototype", scenarioNode.getScenarioId(),
					scenario.displayPrototype());

			return false;
		}

		return true;
	}

	/**
	 * Checks that the workflow task node edges fit the task prototype
	 */
	public boolean checkTaskPrototype(TaskNode taskNode, Workflow workflow) {
		AbstractTaskImplementation task = taskNode.getTask();
		Map<String, String> inputs = new HashMap<String, String>();
		Set<String> outputs = new HashSet<String>();

		// input ports are retrieved as well as the data type
		// of the corresponding output port in the connected node
		for (WorkflowEdge edge : workflow.incomingEdgesOf(taskNode)) {
			AbstractWorkflowNode predecessor = workflow.getEdgeSource(edge);
			String type = predecessor.getImplementation().getOutputType(edge.getSourcePort());
			if (!edge.getSourcePort().equals(IGNORE_PORT_NAME)) {
				inputs.put(edge.getTargetPort(), type);
			}
		}

		// There may be several output edges with same data type (sharing same
		// port)
		for (WorkflowEdge edge : workflow.outgoingEdgesOf(taskNode)) {
			if (!outputs.contains(edge.getSourcePort()) && !edge.getSourcePort().equals(IGNORE_PORT_NAME)) {
				outputs.add(edge.getSourcePort());
			}
		}

		if (!task.acceptInputs(inputs)) {
			log(Level.SEVERE, "Workflow.WrongTaskPrototype", taskNode.getTaskId(), task.displayPrototype());

			return false;
		}

		// The task prototype is compared to the output port names
		if (!task.acceptOutputs(outputs)) {
			log(Level.SEVERE, "Workflow.WrongTaskPrototype", taskNode.getTaskId(), task.displayPrototype());

			return false;
		}

		return true;
	}

	/**
	 * Executes the workflow
	 */
	public boolean execute(String workflowPath, String scenarioPath, IProgressMonitor monitor) {

		Workflow workflow = new WorkflowParser().parse(workflowPath);

		// Initializing the workflow console
		log(Level.INFO, "Workflow.StartInfo", workflowPath);
		if (monitor != null)
			monitor.beginTask("Executing workflow", workflow.vertexSet().size());

		// Checking the existence of plugins and retrieving prototypess
		if (!check(workflow, monitor)) {
			if (monitor != null)
				monitor.setCanceled(true);
			return false;
		}

		if (workflow.vertexSet().size() == 0) {
			log(Level.SEVERE, "Workflow.EmptyVertexSet");

			return false;
		}

		if (!workflow.hasScenario()) {
			log(Level.SEVERE, "Workflow.OneScenarioNeeded");

			return false;
		}

		for (AbstractWorkflowNode node : workflow.vertexTopologicalList()) {
			if (workflow.edgesOf(node).isEmpty()) {
				log(Level.WARNING, "Workflow.IgnoredNonConnectedTask");
			} else {
				// Data outputs of the node
				Map<String, Object> outputs = null;
				String nodeId = null;

				if (node.isScenarioNode()) {
					// The scenario node is special because it gets a reference
					// path and generates the inputs of the rapid prototyping
					// process
					ScenarioNode scenarioNode = (ScenarioNode) node;
					nodeId = scenarioNode.getScenarioId();
					AbstractScenarioImplementation scenario = scenarioNode.getScenario();

					// Checks that the scenario node output edges fit the task
					// prototype
					if (!checkScenarioPrototype(scenarioNode, workflow)) {
						return false;
					}

					try {
						// updating monitor
						if (monitor != null)
							monitor.subTask(scenario.monitorMessage());
						outputs = scenario.extractData(scenarioPath);

						// Filter only outputs required in the workflow
						final Map<String, Object> outs = outputs; // final
																	// reference
																	// for
																	// predicate
						Set<WorkflowEdge> edges = new HashSet<WorkflowEdge>(workflow.outgoingEdgesOf(scenarioNode));
						edges.removeIf(edge -> !outs.containsKey(edge.getSourcePort()));

						final Map<String, Object> checkedOutputs = new HashMap<String, Object>();
						edges.forEach(edge -> checkedOutputs.put(edge.getSourcePort(), outs.get(edge.getSourcePort())));

						// Check the outputs have the right type.
						checkOutputType(checkedOutputs, scenario);

						// Each node execution is equivalent for the monitor
						if (monitor != null)
							monitor.worked(1);
					} catch (WorkflowException e) {
						log(Level.SEVERE, "Workflow.ScenarioExecutionException", e.getMessage());

						return false;
					}

				} else if (node.isTaskNode()) {
					TaskNode taskNode = (TaskNode) node;
					AbstractTaskImplementation task = taskNode.getTask();
					nodeId = taskNode.getTaskId();

					// Checks that the workflow task node edges fit the task
					// prototype
					if (!checkTaskPrototype(taskNode, workflow)) {
						return false;
					}

					// Preparing the input and output maps of the execute method
					Map<String, Object> inputs = new HashMap<String, Object>();

					// Retrieving the data from input edges
					for (WorkflowEdge edge : workflow.incomingEdgesOf(taskNode)) {
						inputs.put(edge.getTargetPort(), edge.getData());
					}

					// Executing the workflow task
					try {
						// updating monitor and console
						if (monitor != null)
							monitor.subTask(task.monitorMessage());
						log(Level.INFO, "Workflow.Step", task.monitorMessage());

						// Workflow cancellation was requested
						if (monitor != null && monitor.isCanceled()) {

							log(Level.SEVERE, "Workflow.CancellationRequested");
							return false;
						}

						// Checks that the requested parameters are available
						// for the task
						if (checkParameters(taskNode, task) == false) {
							return false;
						}

						// execution
						outputs = task.execute(inputs, taskNode.getParameters(), monitor, nodeId, workflow);

						// Filter only outputs required in the workflow
						final Map<String, Object> outs = outputs; // final
																	// reference
																	// for
																	// predicate
						Set<WorkflowEdge> edges = new HashSet<WorkflowEdge>(workflow.outgoingEdgesOf(taskNode));
						edges.removeIf(edge -> !outs.containsKey(edge.getSourcePort()));

						final Map<String, Object> checkedOutputs = new HashMap<String, Object>();
						edges.forEach(edge -> checkedOutputs.put(edge.getSourcePort(), outs.get(edge.getSourcePort())));

						// Check the outputs have the right type.
						checkOutputType(outputs, task);

						// Each node execution is equivalent for the monitor
						if (monitor != null)
							monitor.worked(1);
					} catch (WorkflowException e) {
						log(Level.SEVERE, "Workflow.ExecutionException", nodeId, e.getMessage());
						return false;
					} catch (Exception e) {
						StringWriter error = new StringWriter();
						e.printStackTrace(new PrintWriter(error));
						e.printStackTrace();
						log(Level.SEVERE,
								"Unexpected Exception: " + error.toString()
										+ "\n Contact Preesm developers if you cannot solve the problem.",
								nodeId, error.toString());
						return false;
					}
				}

				// If the execution incorrectly initialized the outputs
				if (outputs == null) {
					log(Level.SEVERE, "Workflow.NullNodeOutput", nodeId);
					return false;
				} else {
					// Retrieving output of the current node
					// Putting the data in output edges
					for (WorkflowEdge edge : workflow.outgoingEdgesOf(node)) {
						String type = edge.getSourcePort();
						// The same data may be transferred to several
						// successors
						if (type.equals(IGNORE_PORT_NAME)) {
							// Ignore data
						} else if (outputs.containsKey(type)) {
							edge.setData(outputs.get(type));
						} else {
							edge.setData(null);
							log(Level.SEVERE, "Workflow.IncorrectOutput", nodeId, type);
							return false;
						}
					}
				}
			}
		}

		log(Level.INFO, "Workflow.EndInfo", workflowPath);

		return true;
	}

	private void checkOutputType(Map<String, Object> outputs, AbstractWorkflowNodeImplementation task)
			throws WorkflowException {
		// Check outputs one by one
		for (Entry<String, Object> e : outputs.entrySet()) {
			String name = e.getKey();
			Object output = e.getValue();

			String type = task.getOutputType(name);
			String givenType = "Null";
			try {
				if (output != null) {
					Class<?> c = Class.forName(type, true, output.getClass().getClassLoader());
					if (c.isInstance(output)) {
						continue;
					}
					givenType = output.getClass().getName();
				}

				// Something went wrong !
				throw new WorkflowException("\nOutput \"" + name + "\" of workflow task \"" + task.getClass()
						+ "\" is null or has an invalid type.\n(expected: \"" + type + "\" given: \"" + givenType
						+ "\")");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Checks that all necessary parameters are provided to the workflow task
	 * 
	 * @param taskNode
	 *            workflow task node with the provided parameters
	 * @param taskImplementation
	 *            the task implementation requiring specific parameters
	 */
	private boolean checkParameters(TaskNode taskNode, AbstractTaskImplementation taskImplementation) {
		Map<String, String> defaultParameters = taskImplementation.getDefaultParameters();
		Map<String, String> parameters = taskNode.getParameters();

		if (defaultParameters != null) {
			if (parameters == null) {
				if (defaultParameters.size() > 0) {
					log(Level.SEVERE, "Workflow.MissingAllParameters", taskNode.getTaskId(),
							defaultParameters.keySet().toString());
					return false;
				}
			} else {
				for (String param : defaultParameters.keySet()) {
					if (!parameters.containsKey(param)) {
						log(Level.SEVERE, "Workflow.MissingParameter", taskNode.getTaskId(),
								defaultParameters.keySet().toString());
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Log method for workflow executions, depending on the available UI
	 * 
	 * @param level
	 * @param msgKey
	 * @param variables
	 */
	abstract protected void log(Level level, String msgKey, String... variables);
}
