/**
 * 
 */
package net.sf.dftools.ui.slam;

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
		valid &= validateEnablerEdges(graph, file);
		valid &= validateEdgePorts(graph, file);
		valid &= validateEdges(graph, file);
		valid &= validateComNodes(graph, file);

		return valid;
	}

	/**
	 * An enabler must at least receive a data link and a control link.
	 */
	private boolean validateEnablerEdges(Graph graph, IFile file) {

		boolean valid = true;
		boolean receiveControlLink = false;
		boolean hasDataLink = false;

		for (Vertex v : graph.vertexSet()) {
			receiveControlLink = false;
			hasDataLink = false;

			String type = v.getType().getName();
			if (type.equals("Ram") || type.equals("Dma")) {
				for (Edge e : graph.incomingEdgesOf(v)) {
					String eType = e.getType().getName();
					if (eType.equals("ControlLink")) {
						receiveControlLink = true;
					} else if (eType.contains("DataLink")) {
						hasDataLink = true;
					}
				}

				for (Edge e : graph.outgoingEdgesOf(v)) {
					String eType = e.getType().getName();
					if (eType.contains("DataLink")) {
						hasDataLink = true;
					}
				}

				if (!receiveControlLink || !hasDataLink) {
					createMarker(
							file,
							"An enabler (Ram or Dma) must at least receive a data link and a control link.",
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
	private boolean validateEdges(Graph graph, IFile file) {
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
	 * A Communication Node must specify a speed a speed.
	 */
	private boolean validateComNodes(Graph graph, IFile file) {

		boolean valid = true;
		boolean hasSpeed = false;

		for (Vertex v : graph.vertexSet()) {
			hasSpeed = false;

			String type = v.getType().getName();
			if (type.contains("ComNode")) {

				String speed = (String) v.getValue("speed");
				if (speed != null && !speed.equals("")
						&& Integer.valueOf(speed) != 0) {
					hasSpeed = true;
				}

				if (!hasSpeed) {
					createMarker(
							file,
							"A ComNode must specify a non-zero integer-valued speed.",
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
