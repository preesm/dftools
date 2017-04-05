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
package org.ietr.dftools.ui.workflow;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.ietr.dftools.graphiti.model.Graph;
import org.ietr.dftools.graphiti.model.IValidator;
import org.ietr.dftools.graphiti.model.Vertex;

/**
 * This class implements a Workflow model validator.
 * This validator checks that the workflow is executable.
 * 
 * @author mpelcat
 * 
 */
public class WorkflowValidator implements IValidator {

	/**
	 * Validates the workflow by checking that every tasks are declared in
	 * loaded plug-ins. Each task implementation must additionally accept the
	 * textual input and output edges connected to it.
	 */
	@Override
	public boolean validate(Graph graph, IFile file) {

		/**
		 * Testing each task independently.
		 */
		Set<Vertex> vertices = graph.vertexSet();
		for (Vertex vertex : vertices) {
			if ("Task".equals(vertex.getType().getName())) {
				// Getting the plugin ID and the associated class name.
				String pluginId = (String) vertex.getValue("plugin identifier");
				
				if(pluginId == null){
					createMarker(
							file,
							"Enter a plugin identifier for each plugin.",
							"Any plugin", IMarker.PROBLEM, IMarker.SEVERITY_ERROR);
					return false;
				}
				
				IExtensionRegistry registry = Platform.getExtensionRegistry();
				IConfigurationElement[] elements = registry
						.getConfigurationElementsFor("org.ietr.dftools.workflow.tasks");

				boolean foundClass = false;

				// Looking for the Id of the workflow task among the registry
				// elements
				for (IConfigurationElement element : elements) {
					String taskId = element.getAttribute("id");
					if (pluginId.equals(taskId)) {
						try {
							String taskType = element.getAttribute("type");
							/**
							 * Getting the class corresponding to the taskType
							 * string. This is only possible because of
							 * "Eclipse-BuddyPolicy: global" in the manifest:
							 * the Graphiti configuration class loader has a
							 * global knowledge of classes
							 */
		
							Class<?> vertexTaskClass = Class.forName(taskType);

							Object vertexTaskObj = vertexTaskClass
									.newInstance();

							// Adding the default parameters if necessary
							addDefaultParameters(vertex, vertexTaskObj, graph,
									file);

							foundClass = true;

						} catch (Exception e) {
							createMarker(
									file,
									"Class associated to the workflow task not found. Is the class path exported?",
									pluginId, IMarker.PROBLEM,
									IMarker.SEVERITY_ERROR);
							return true;
						}
					}
				}

				if (foundClass == false) {
					createMarker(
							file,
							"Plugin associated to the workflow task not found.",
							pluginId, IMarker.PROBLEM, IMarker.SEVERITY_ERROR);
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Getting the default parameters from the task class and adding them in the
	 * graph if they were not present. A warning giving the possible parameter
	 * values is displayed
	 */
	@SuppressWarnings("unchecked")
	private void addDefaultParameters(Vertex vertex, Object object,
			Graph graph, IFile file) {
		Map<String, String> parameterDefaults = null;
		try {
			Method prototypeMethod = object.getClass().getDeclaredMethod(
					"getDefaultParameters");
			Object obj = prototypeMethod.invoke(object);
			if (obj instanceof Map<?, ?>) {
				parameterDefaults = (Map<String, String>) obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (parameterDefaults != null) {

			Object var = vertex.getValue("variable declaration");
			Class<?> clasz = var.getClass();
			if (clasz == TreeMap.class) {
				TreeMap<String, String> varMap = (TreeMap<String, String>) var;

				for (String key : parameterDefaults.keySet()) {
					if (!varMap.containsKey(key)) {
						varMap.put(key, parameterDefaults.get(key));

						createMarker(file, "Added default parameter value: "
								+ key + ", " + parameterDefaults.get(key),
								(String) vertex.getValue("id"),
								IMarker.MESSAGE, IMarker.SEVERITY_INFO);
					}
				}
			}
		}
	}

	/**
	 * Displays an error
	 */
	protected void createMarker(IFile file, String message, String location,
			String type, int severity) {
		try {
			IMarker marker = file.createMarker(type);
			marker.setAttribute(IMarker.LOCATION, location);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.MESSAGE, message);
		} catch (CoreException e) {
		}
	}

}
