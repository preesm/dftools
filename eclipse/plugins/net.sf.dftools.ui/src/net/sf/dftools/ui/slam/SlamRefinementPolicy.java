/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
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
package net.sf.dftools.ui.slam;

import net.sf.dftools.ui.util.FileUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.ietr.dftools.architecture.slam.serialize.RefinementList;
import org.ietr.graphiti.model.DefaultRefinementPolicy;
import org.ietr.graphiti.model.Vertex;

/**
 * This class extends the default refinement policy with S-LAM-specific policy.
 * 
 * @author Matthieu Wipliez
 * @author mpelcat
 */
public class SlamRefinementPolicy extends DefaultRefinementPolicy {

	/**
	 * Ask the user to choose an existing S-LAM file to refine the selected
	 * vertex.
	 * 
	 * @param shell
	 *            The active window's {@link Shell}.
	 */
	public String useExistingFile(final Vertex vertex, Shell shell, String extension) {
		String filePath = null;
				
		if(extension.equals("slam")){
			filePath = FileUtils.browseFiles(shell,
					"Please select an existing S-LAM network file:", extension);
		}else if(extension.equals("cdl")){
			filePath = FileUtils.browseFiles(shell,
					"Please select an existing CDL file:", extension);
		}
		
		// Getting relative path
		IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(filePath));

		String fileRelativeName = getRefinementValue(vertex, file);
		return fileRelativeName;
	}

	/**
	 * Asking the user for a new refinement
	 * 
	 * @param vertex
	 *            the vertex being refined
	 */
	@Override
	public String getNewRefinement(Vertex vertex) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		Shell shell = window.getShell();

		// prompts the user to choose a file
		final String message = "The selected instance can be refined by an existing "
				+ "S-LAM network, by a list of S-LAM networks or by a Component Description" +
				"Language (CDL) file.";
		MessageDialog dialog = new MessageDialog(shell,
				"Set/Update Refinement", null, message, MessageDialog.QUESTION,
				new String[] { "Select network", "Select list of networks", "Select CDL file" }, 0);

		int index = dialog.open();
		String newRefinement = null;

		// The user can select either a single new network or a list of networks
		if (index == 0) {
			newRefinement = useExistingFile(vertex, shell, "slam");
		} else if (index == 1) {
			newRefinement = selectListOfNetworks(vertex, shell);
		} else if (index == 2) {
			newRefinement = useExistingFile(vertex, shell, "cdl");
		}

		return newRefinement;
	}

	/**
	 * Asking the user to choose a list of refinements for a vertex
	 * 
	 * @param vertex
	 *            the vertex being refined
	 * @param shell
	 *            the current shell
	 */
	protected String selectListOfNetworks(final Vertex vertex, Shell shell) {

		// Retrieving original list
		RefinementList originalList = new RefinementList(getRefinement(vertex));

		// Updating the refinement
		ChooseRefinementListDialog dialog = new ChooseRefinementListDialog(
				vertex, shell, this, true);
		dialog.setInput(originalList);

		dialog.setMessage("Please choose refinement networks for "
				+ vertex.toString());
		dialog.setTitle(vertex.toString());

		RefinementList modifiedList = dialog.openDialog();
		return modifiedList.toString();
	}

	@Override
	public IFile getRefinementFile(Vertex vertex) {
		// Getting the refinement string that contains either a list of
		// refinements or a single one
		String refinement = getRefinement(vertex);

		if (refinement == null) {
			return null;
		}
		IFile file = null;
		RefinementList list = new RefinementList(refinement);

		// Case of a simple refinement
		if (list.size() == 1) {
			file = super.getRefinementFile(vertex);
		}
		// Case of a list refinement: prompt for choice
		else if (list.size() > 1) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			Shell shell = window.getShell();

			// Choosing the refinement in a dialog window
			ChooseRefinementListDialog dialog = new ChooseRefinementListDialog(
					vertex, shell, this, false);
			dialog.setInput(list);

			dialog.setMessage("Please choose one of the multiple refinements");
			dialog.setTitle(vertex.toString());

			String chosenRefinement = null;

			int returnCode = dialog.open();
			if (returnCode == Window.OK && dialog.getResult() != null
					&& dialog.getResult().length != 0) {
				chosenRefinement = (String) dialog.getResult()[0];
			}

			if (chosenRefinement != null) {
				IPath path = getAbsolutePath(vertex.getParent().getFileName(),
						chosenRefinement);
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IResource resource = root.findMember(path);
				if (resource instanceof IFile) {
					file = (IFile) resource;
				}
			}
		}

		return file;
	}

}
