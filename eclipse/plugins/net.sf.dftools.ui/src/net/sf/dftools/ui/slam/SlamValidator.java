/**
 * 
 */
package net.sf.dftools.ui.slam;

import java.util.HashMap;
import java.util.Map;

import net.sf.graphiti.model.Edge;
import net.sf.graphiti.model.Graph;
import net.sf.graphiti.model.IValidator;
import net.sf.graphiti.model.Vertex;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

/**
 * Validating the System-Level Architecture Model
 * 
 * @author mpelcat
 */
public final class SlamValidator implements IValidator {

	/**
	 * Validates the S-LAM graph.
	 * 
	 * @param graph
	 *            graph to validate
	 * @param file
	 *            file containing the serialized graph
	 */
	@Override
	public boolean validate(Graph graph, IFile file) {
		boolean valid = true;

		valid &= validateGraph(graph, file);
		valid &= validateEnablerEdges(graph, file);
		valid &= validateEdgePorts(graph, file);
		valid &= validateDataLinks(graph, file);
		valid &= validateComNodes(graph, file);
		valid &= validateDmas(graph, file);
		valid &= validateMems(graph, file);
		valid &= validateControlLinks(graph, file);
		valid &= validateHierarchicalPorts(graph, file);
		valid &= validateHierarchicalConnections(graph, file);
		valid &= validateComponentInstances(graph, file);

		return valid;
	}

	/**
	 * A graph should have VLNV data.
	 */
	private boolean validateGraph(Graph graph, IFile file) {

		boolean valid = true;
		boolean lackVLNVElement = false;

		if (graph.getValue("vendor") == null) {
			graph.setValue("vendor", "");
			lackVLNVElement = true;
		}

		if (graph.getValue("library") == null) {
			graph.setValue("library", "");
			lackVLNVElement = true;
		}

		if (graph.getValue("name") == null || graph.getValue("name").equals("")) {
			if (file.getName() != null && file.getName().lastIndexOf('.') > 0) {
				graph.setValue(
						"name",
						file.getName().substring(0,
								file.getName().lastIndexOf('.')));
			}
			lackVLNVElement = true;
		}

		if (graph.getValue("version") == null) {
			graph.setValue("version", "");
			lackVLNVElement = true;
		}

		if (lackVLNVElement) {
			createMarker(file,
					"A graph should have VLNV data. Default values set",
					(String) graph.getValue("id"), IMarker.PROBLEM,
					IMarker.SEVERITY_WARNING);
			valid = false;
		}

		return valid;
	}

	/**
	 * An enabler must at least receive a data link.
	 */
	private boolean validateEnablerEdges(Graph graph, IFile file) {

		boolean valid = true;
		boolean hasDataLink = false;

		for (Vertex v : graph.vertexSet()) {
			hasDataLink = false;

			String type = v.getType().getName();
			if (type.equals("Mem") || type.equals("Dma")) {
				for (Edge e : graph.incomingEdgesOf(v)) {
					String eType = e.getType().getName();
					if (eType.contains("DataLink")) {
						hasDataLink = true;
					}
				}

				for (Edge e : graph.outgoingEdgesOf(v)) {
					String eType = e.getType().getName();
					if (eType.contains("DataLink")) {
						hasDataLink = true;
					}
				}

				if (!hasDataLink) {
					createMarker(
							file,
							"An enabler (Mem or Dma) must at least receive a data link",
							(String) v.getValue("id"), IMarker.PROBLEM,
							IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
	}

	/**
	 * Each edge must have port names.
	 */
	private boolean validateEdgePorts(Graph graph, IFile file) {
		Boolean valid = true;
		Boolean hasPortNames = true;
		for (Edge e : graph.edgeSet()) {
			if (!e.getType().getName().equals("hierConnection")) {
				hasPortNames = true;
				String sourcePort = (String) e.getValue("source port");
				String targetPort = (String) e.getValue("target port");

				if (sourcePort == null || sourcePort.equals("")) {
					hasPortNames = false;
				}

				if (targetPort == null || targetPort.equals("")) {
					hasPortNames = false;
				}

				if (!hasPortNames) {
					createMarker(
							file,
							"Each link must have source and target port names.",
							(String) e.getSource().getValue("id") + "->"
									+ (String) e.getTarget().getValue("id"),
							IMarker.PROBLEM, IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
	}

	/**
	 * Each data link must connect a communication node to a node of any type. A
	 * link without a communication node (either parallel or with contention) is
	 * not valid
	 */
	private boolean validateDataLinks(Graph graph, IFile file) {
		Boolean valid = true;
		Boolean hasComNode = false;
		for (Edge e : graph.edgeSet()) {
			if (e.getType().getName().contains("DataLink")) {
				hasComNode = false;

				if (e.getSource() != null
						&& e.getSource().getType().getName()
								.contains("ComNode")) {
					hasComNode = true;
				}

				if (e.getTarget() != null
						&& e.getTarget().getType().getName()
								.contains("ComNode")) {
					hasComNode = true;
				}

				if (!hasComNode) {
					createMarker(
							file,
							"Each data link must have at least one ComNode in its source/target components.",
							(String) e.getSource().getValue("id") + "->"
									+ (String) e.getTarget().getValue("id"),
							IMarker.PROBLEM, IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
	}

	/**
	 * A Communication Node must specify a speed.
	 */
	private boolean validateComNodes(Graph graph, IFile file) {

		boolean valid = true;
		boolean hasSpeed = false;
		boolean isNotDefault = false;

		for (Vertex v : graph.vertexSet()) {
			hasSpeed = false;
			isNotDefault = false;

			String type = v.getType().getName();
			if (type.contains("ComNode")) {

				String definition = (String) v.getValue("definition");
				if (definition != null && !definition.equals("")
						&& !definition.equals("default")) {
					isNotDefault = true;				
				}
				
				String speed = (String) v.getValue("speed");
				if (speed != null && !speed.equals("")
						&& Float.valueOf(speed) > 0) {
					hasSpeed = true;
				}

				if (!hasSpeed) {
					createMarker(
							file,
							"A ComNode must specify a non-zero float-valued speed.",
							(String) v.getValue("id"), IMarker.PROBLEM,
							IMarker.SEVERITY_ERROR);
					valid = false;
				}
				
				if (!isNotDefault) {
					createMarker(
							file,
							"A ComNode type must not be default.",
							(String) v.getValue("id"), IMarker.PROBLEM,
							IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
	}

	/**
	 * A Dma must specify a setup time.
	 */
	private boolean validateDmas(Graph graph, IFile file) {

		boolean valid = true;
		boolean hasSetupTime = false;

		for (Vertex v : graph.vertexSet()) {
			hasSetupTime = false;

			String type = v.getType().getName();
			if (type.contains("Dma")) {

				String setupTime = (String) v.getValue("setupTime");
				if (setupTime != null && !setupTime.equals("")
						&& Integer.valueOf(setupTime) >= 0) {
					hasSetupTime = true;
				}

				if (!hasSetupTime) {
					createMarker(
							file,
							"A Dma must specify a positive integer-valued setup time.",
							(String) v.getValue("id"), IMarker.PROBLEM,
							IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
	}

	/**
	 * A memory Node must specify a size. Two memories with the same definition
	 * must have the same size.
	 */
	private boolean validateMems(Graph graph, IFile file) {

		boolean valid = true;
		boolean hasSize = false;
		boolean conflictedSizes = false;
		Map<String, String> definitionToSize = new HashMap<String, String>();

		for (Vertex v : graph.vertexSet()) {
			hasSize = false;
			conflictedSizes = false;

			String type = v.getType().getName();
			if (type.contains("Mem")) {

				String size = (String) v.getValue("memSize");
				String definition = (String) v.getValue("definition");

				if (size != null && !size.equals("")
						&& Integer.valueOf(size) > 0) {
					hasSize = true;
				}

				// Testing if instances with the same definition have different
				// sizes
				if (definition != null && !definition.equals("")
						&& !definition.equals("default")) {

					if (definitionToSize.containsKey(definition)) {
						boolean emptySize = (size == null) || size.isEmpty();
						String storedSize = definitionToSize.get(definition);
						boolean emptySSize = (storedSize == null)
								|| storedSize.isEmpty();
						if ((emptySize && !emptySSize)
								|| (!emptySize && emptySSize)
								|| (!emptySize && !emptySSize && !size
										.equals(storedSize))) {
							conflictedSizes = true;
						}
					}
					definitionToSize.put(definition, size);
				}

				if (!hasSize) {
					createMarker(
							file,
							"A memory must specify a non-zero integer-valued size.",
							(String) v.getValue("id"), IMarker.PROBLEM,
							IMarker.SEVERITY_ERROR);
					valid = false;
				}

				if (conflictedSizes) {
					createMarker(
							file,
							"Two memories with the same definition must have the same size.",
							(String) v.getValue("id"), IMarker.PROBLEM,
							IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
	}

	/**
	 * A Control link must be between an operator and an enabler (Mem or Dma)
	 * and specify a setupTime.
	 */
	private boolean validateControlLinks(Graph graph, IFile file) {
		Boolean valid = true;
		Boolean hasOperatorSource = false;
		Boolean hasEnablerTarget = false;
		for (Edge e : graph.edgeSet()) {
			if (e.getType().getName().equals("ControlLink")) {
				hasOperatorSource = false;
				hasEnablerTarget = false;

				if (e.getSource() != null
						&& e.getSource().getType().getName()
								.contains("Operator")) {
					hasOperatorSource = true;
				}

				if (e.getTarget() != null
						&& (e.getTarget().getType().getName().contains("Mem") || e
								.getTarget().getType().getName()
								.contains("Dma"))) {
					hasEnablerTarget = true;
				}

				if (!hasOperatorSource || !hasEnablerTarget) {
					createMarker(
							file,
							"Each control link must link an operator to an enabler (Mem or Dma).",
							(String) e.getSource().getValue("id") + "->"
									+ (String) e.getTarget().getValue("id"),
							IMarker.PROBLEM, IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
	}

	/**
	 * A hierarchical connection link must link a commmunication node or an
	 * operator and a hierarchical connection node
	 */
	private boolean validateHierarchicalConnections(Graph graph, IFile file) {
		Boolean valid = true;
		Boolean hasComNodeOrOpSource = false;
		Boolean hasHierConnectionTarget = false;

		for (Edge e : graph.edgeSet()) {
			if (e.getType().getName().equals("hierConnection")) {
				hasComNodeOrOpSource = false;
				hasHierConnectionTarget = false;

				Vertex source = e.getSource();
				if (source != null) {
					String sourceType = source.getType().getName();
					if (sourceType.contains("ComNode")
							|| sourceType.contains("Operator")) {
						hasComNodeOrOpSource = true;
					}
				}

				Vertex target = e.getTarget();
				if (target != null) {
					String targetType = target.getType().getName();
					if (targetType.contains("hierConnection")) {
						hasHierConnectionTarget = true;
					}
				}

				if (!(hasComNodeOrOpSource && hasHierConnectionTarget)) {

					// Remove the edge that cannot be saved.
					graph.removeEdge(e);

					createMarker(
							file,
							"A hierarchical connection link must link a commmunication node or an operator and a hierarchical connection node. It is undirected.",
							(String) e.getSource().getValue("id") + "->"
									+ (String) e.getTarget().getValue("id"),
							IMarker.PROBLEM, IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
	}

	/**
	 * Each component instance must specify a definition id that identifies the
	 * instanciated component.
	 */
	private boolean validateComponentInstances(Graph graph, IFile file) {

		boolean valid = true;
		boolean hasRefName = false;
		boolean conflictedRefinements = false;
		Map<String, String> definitionToRefinement = new HashMap<String, String>();

		for (Vertex v : graph.vertexSet()) {
			hasRefName = false;
			conflictedRefinements = false;

			String type = v.getType().getName();
			if (!type.equals("hierConnection")) {
				String definition = (String) v.getValue("definition");

				// Testing if instances with the same definition have different
				// refinements
				if (definition != null && !definition.equals("")
						&& !definition.equals("default")) {
					hasRefName = true;

					String refinement = (String) v.getValue("refinement");
					if (definitionToRefinement.containsKey(definition)) {
						boolean emptyRef = (refinement == null)
								|| refinement.isEmpty();
						String storedRefinement = definitionToRefinement
								.get(definition);
						boolean emptySRef = (storedRefinement == null)
								|| storedRefinement.isEmpty();
						if ((emptyRef && !emptySRef)
								|| (!emptyRef && emptySRef)
								|| (!emptyRef && !emptySRef && !refinement
										.equals(storedRefinement))) {
							conflictedRefinements = true;
						}
					}
					definitionToRefinement.put(definition, refinement);
				}

				if (!hasRefName) {
					v.setValue("definition", "default");

					createMarker(
							file,
							"Each component instance must specify a definition id that identifies the instanciated component. By default, it is set to \"default\"",
							(String) v.getValue("id"), IMarker.PROBLEM,
							IMarker.SEVERITY_ERROR);
					valid = false;
				}

				if (conflictedRefinements) {

					createMarker(
							file,
							"Two components with the same definition must have the same refinement",
							(String) v.getValue("id"), IMarker.PROBLEM,
							IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
	}

	/**
	 * Each hierarchy port must have exactly one hierarchical connection.
	 */
	private boolean validateHierarchicalPorts(Graph graph, IFile file) {

		boolean valid = true;

		for (Vertex v : graph.vertexSet()) {
			String type = v.getType().getName();
			if (type.contains("hierConnection")) {

				int nbEdges = graph.incomingEdgesOf(v).size()
						+ graph.outgoingEdgesOf(v).size();

				if (nbEdges != 1) {
					graph.removeVertex(v);

					createMarker(
							file,
							"Each hierarchy port must have exactly one hierarchical connection.",
							(String) v.getValue("id"), IMarker.PROBLEM,
							IMarker.SEVERITY_ERROR);
					valid = false;
				}
			}
		}

		return valid;
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
