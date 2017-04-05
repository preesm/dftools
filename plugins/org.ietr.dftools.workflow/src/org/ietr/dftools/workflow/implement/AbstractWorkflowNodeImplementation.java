/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (%%DATE%%) :
 *
 * %%AUTHORS%%
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
/**
 * 
 */
package org.ietr.dftools.workflow.implement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.ietr.dftools.workflow.WorkflowManager;
import org.ietr.dftools.workflow.tools.WorkflowLogger;

/**
 * Node implementation is the superclass of both scenario and task
 * implementation. Their outputs are handled the same way.
 * 
 * @author mpelcat
 */
public abstract class AbstractWorkflowNodeImplementation {
	/**
	 * Input/Output keys for workflow tasks
	 */
	protected final static String KEY_SCENARIO = "scenario"; // Should give a PreesmScenario object
	protected final static String KEY_PI_GRAPH = "PiMM"; // Should give a PiGraph object
	protected final static String KEY_ARCHITECTURE = "architecture"; //Should give a Design object
	protected final static String KEY_SDF_GRAPHS_SET = "SDFs"; // Should give a Set<SDFGraph> object
	protected final static String KEY_SDF_GRAPH = "SDF"; // Should give an SDFGraph object
	protected final static String KEY_SDF_DAG = "DAG"; // Should give a MapperDAG object
	protected final static String KEY_SDF_DAG_SET = "DAGs"; // Should give a Set<MapperDAG> object
	protected final static String KEY_SDF_ABC = "ABC"; // Should give a IAbc object
	protected final static String KEY_SDF_ABC_SET = "ABCs"; // Should give a Set<IAbc> object
	protected final static String KEY_MEM_EX = "MemEx"; // Should give a MemoryExclusionGraph object
	protected final static String KEY_MEM_EX_SET = "MemExs"; // Should give a Set<MemoryExclusionGraph> object
	protected final static String KEY_DAG_AND_MEM_EX_MAP = "DAGsAndMemExs"; // Should give a Map<DirectedAcyclicGraph, MemoryExclusionGraph> object
	protected final static String KEY_BOUND_MIN = "BoundMin"; // Should give an int
	protected final static String KEY_BOUND_MAX = "BoundMax"; // Should give an int
	protected final static String KEY_BOUND_MIN_SET = "BoundMin"; // Should give a Set<Integer> object
	protected final static String KEY_BOUND_MAX_SET = "BoundMax"; // Should give a Set<Integer> object

	/**
	 * Id and fully qualified names of node output retrieved from the extension.
	 */
	private Map<String, String> outputPrototype;

	public AbstractWorkflowNodeImplementation() {
		outputPrototype = new HashMap<String, String>();
	}

	/**
	 * Adds an input to the task prototype.
	 */
	final public void addOutput(String id, String type) {
		outputPrototype.put(id, type);
	}

	/**
	 * Gets the fully qualified name of the class associated to the given output
	 * port.
	 */
	final public String getOutputType(String id) {
		return outputPrototype.get(id);
	}

	/**
	 * Compares the prototype with the output edges. Not all outputs need to be
	 * used
	 */
	final public boolean acceptOutputs(Set<String> outputPortNames) {

		for (String outputPortName : outputPortNames) {
			if (!outputPrototype.keySet().contains(outputPortName)) {
				WorkflowLogger.getLogger().logFromProperty(
						Level.SEVERE, "Workflow.FalseOutputEdge",
						outputPortName, WorkflowManager.IGNORE_PORT_NAME);
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the preferred prototype for the node in a workflow. Useful to
	 * give user information in the workflow
	 */
	public String displayPrototype() {
		return " outputs=" + outputPrototype.toString();
	}

	/**
	 * Returns a message to display in the monitor.
	 */
	public abstract String monitorMessage();
}
