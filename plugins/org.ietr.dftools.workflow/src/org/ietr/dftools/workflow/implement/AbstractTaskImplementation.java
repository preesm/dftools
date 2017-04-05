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

package org.ietr.dftools.workflow.implement;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.eclipse.core.runtime.IProgressMonitor;
import org.ietr.dftools.workflow.WorkflowException;
import org.ietr.dftools.workflow.elements.Workflow;
import org.ietr.dftools.workflow.tools.WorkflowLogger;

/**
 * This interface must be implemented by any workflow task element. The
 * prototype of the workflow task is specified in the plugin extension.
 * 
 * @author mpelcat
 */
public abstract class AbstractTaskImplementation extends
		AbstractWorkflowNodeImplementation {

	/**
	 * Id and fully qualified names of task input and output retrieved from the
	 * extension.
	 */
	private Map<String, String> inputPrototype;

	public AbstractTaskImplementation() {
		inputPrototype = new HashMap<String, String>();
	}

	/**
	 * Adds an input to the task prototype.
	 */
	final public void addInput(String id, String type) {
		inputPrototype.put(id, type);
	}

	/**
	 * Compares the prototype with the input edges id AND type. All inputs need
	 * to be initialized
	 */
	final public boolean acceptInputs(Map<String, String> graphInputPorts) {

		for (String protoInputPortName : inputPrototype.keySet()) {
			if (!graphInputPorts.keySet().contains(protoInputPortName)) {
				WorkflowLogger.getLogger().logFromProperty(
						Level.SEVERE, "Workflow.FalseInputEdge",
						protoInputPortName);
				return false;
			} else {
				String protoType = inputPrototype.get(protoInputPortName);
				String graphType = graphInputPorts.get(protoInputPortName);
				if (!protoType.equals(graphType)) {
					WorkflowLogger.getLogger().logFromProperty(
							Level.SEVERE, "Workflow.FalseInputType",
							protoInputPortName, graphType, protoType);
					return false;
				}
			}
		}

		if (graphInputPorts.keySet().size() > inputPrototype.keySet().size()) {
			WorkflowLogger.getLogger().logFromProperty(Level.SEVERE,
					"Workflow.TooManyInputEdges",
					String.valueOf(graphInputPorts.keySet().size()),
					String.valueOf(inputPrototype.keySet().size()));
			return false;
		}

		return true;
	}

	/**
	 * Returns the preferred prototype for the node in a workflow. Useful to
	 * give user information in the workflow
	 */
	@Override
	public final String displayPrototype() {
		return " inputs=" + inputPrototype.toString()
				+ super.displayPrototype();
	}

	/**
	 * The workflow task element implementation must have a execute method that
	 * is called by the workflow manager
	 * 
	 * @param inputs
	 *            a map associating input objects to their data type in the
	 *            graph
	 * @param parameters
	 *            a map containing the vertex parameters
	 * @param monitor
	 *            the progress monitor that can be checked to cancel a task if
	 *            requested
	 * @param nodeName
	 *            name of the graph node that triggered this execution
	 * @param workflow
	 * 			  the workflow that launched the task
	 * @return a map associating output objects to their data type in the graph
	 */
	public abstract Map<String, Object> execute(Map<String, Object> inputs,
			Map<String, String> parameters, IProgressMonitor monitor,
			String nodeName, Workflow workflow) throws WorkflowException;

	/**
	 * Returns the task parameters and their default values. These parameters
	 * are automatically added in the graph if not present.
	 */
	public abstract Map<String, String> getDefaultParameters();
}
