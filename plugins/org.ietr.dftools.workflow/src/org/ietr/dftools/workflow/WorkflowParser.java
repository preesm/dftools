/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Matthieu Wipliez <matthieu.wipliez@insa-rennes.fr> (2011)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011 - 2012)
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

package org.ietr.dftools.workflow;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.ietr.dftools.workflow.elements.AbstractWorkflowNode;
import org.ietr.dftools.workflow.elements.ScenarioNode;
import org.ietr.dftools.workflow.elements.TaskNode;
import org.ietr.dftools.workflow.elements.Workflow;
import org.ietr.dftools.workflow.elements.WorkflowEdge;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * This class provides a workflow parser, allowing to parse a .workflow file and
 * to obtain a Workflow java object.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class WorkflowParser extends DefaultHandler2 {
	/**
	 * The last parsed transformation node is saved to receive its variables.
	 */
	TaskNode lastTransformationNode = null;

	private Map<String, AbstractWorkflowNode> nodes;

	private Workflow workflow = null;

	public WorkflowParser() {
		this.nodes = new HashMap<String, AbstractWorkflowNode>();
		this.workflow = new Workflow();
	}

	/**
	 * Parse a workflow source file with the given fileName and returns the
	 * corresponding Workflow.
	 * 
	 * @param fileName
	 *            The source file name.
	 * @param workflow
	 *            The workflow represented as a graph.
	 */
	public Workflow parse(String fileName) {

		Path relativePath = new Path(fileName);
		workflow.setPath(relativePath);
		IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(relativePath);
		return parse(file);
	}

	/**
	 * Parse a workflow source file and returns the corresponding Workflow.
	 * 
	 * @param file
	 *            The source file.
	 * @param workflow
	 *            The Workflow represented as a graph.
	 */
	public Workflow parse(IFile file) {
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(this);
			reader.parse(new InputSource(file.getContents()));
		} catch (SAXException | IOException | CoreException e) {
			e.printStackTrace();
		}
		return workflow;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {

		if (qName.equals("dftools:scenario")) {
			String pluginId = attributes.getValue("pluginId");
			AbstractWorkflowNode node = new ScenarioNode(pluginId);
			workflow.addVertex(node);
			nodes.put("scenario", node);
		} else if (qName.equals("dftools:task")) {
			String taskId = attributes.getValue("taskId");
			String pluginId = attributes.getValue("pluginId");
			lastTransformationNode = new TaskNode(pluginId, taskId);
			AbstractWorkflowNode node = lastTransformationNode;
			workflow.addVertex(node);
			nodes.put(taskId, node);
		} else if (qName.equals("dftools:dataTransfer")) {
			AbstractWorkflowNode source = nodes
					.get(attributes.getValue("from"));
			AbstractWorkflowNode target = nodes.get(attributes.getValue("to"));
			String sourcePort = attributes.getValue("sourceport");
			String targetPort = attributes.getValue("targetport");
			WorkflowEdge edge = workflow.addEdge(source, target);
			edge.setSourcePort(sourcePort);
			edge.setTargetPort(targetPort);
		} else if (qName.equals("dftools:variable")) {
			if (lastTransformationNode != null) {
				lastTransformationNode.addParameter(
						attributes.getValue("name"),
						attributes.getValue("value"));
			}
		}
	}

	public Workflow getWorkflow() {
		return workflow;
	}
}
