/*
 * Copyright (c) 2011, EPFL
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the EPFL nor the names of its contributors may be used 
 *     to endorse or promote products derived from this software without specific
 *     prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package org.ietr.dftools.architecture.ui.editor;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.ietr.dftools.graphiti.model.Graph;
import org.ietr.dftools.graphiti.model.IRefinementPolicy;
import org.ietr.dftools.graphiti.model.ObjectType;
import org.ietr.dftools.graphiti.model.Vertex;
import org.ietr.dftools.graphiti.validators.DataflowValidator;

/**
 * This class implements a model validator.
 * 
 * @author Ghislain Roquier
 * 
 */
public class IpXactValidator extends DataflowValidator {

	private boolean checkName(Graph graph, IFile file) {
		String name = (String) graph.getValue(ObjectType.PARAMETER_ID);
		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');
		if (index != -1) {
			fileName = fileName.substring(0, index);
		}

		if (fileName.equals(name)) {
			return true;
		} else {
			String message = "Invalid name of network: '" + name
					+ "', expected '" + fileName + "'";
			createMarker(file, message);
			return false;
		}
	}

	private boolean checkRefinements(Graph graph, IFile file) {
		IRefinementPolicy policy = graph.getConfiguration()
				.getRefinementPolicy();
		boolean res = true;

		Set<Vertex> vertices = graph.vertexSet();
		for (Vertex vertex : vertices) {
			if ("ComponentInstance".equals(vertex.getType().getName())) {
				if (policy.getRefinementFile(vertex) == null) {
					String message = "Invalid refinement of vertex '"
							+ vertex.getValue(ObjectType.PARAMETER_ID) + "': '"
							+ policy.getRefinement(vertex) + "'";
					createMarker(file, message);
					res = false;
				}
			}
		}

		return res;
	}

	@Override
	public boolean validate(Graph graph, IFile file) {
		boolean res = true;

		res &= checkName(graph, file);
		res &= checkRefinements(graph, file);
		res &= super.validate(graph, file);

		return res;
	}

}
