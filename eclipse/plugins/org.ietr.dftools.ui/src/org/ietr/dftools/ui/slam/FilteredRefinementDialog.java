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
package org.ietr.dftools.ui.slam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.dialogs.SearchPattern;
import org.ietr.dftools.ui.Activator;

/**
 * This class defines a custom filtered items selection dialog.
 * 
 * @author Matthieu Wipliez
 * @author mpelcat
 */
public class FilteredRefinementDialog extends FilteredItemsSelectionDialog {

	/**
	 * This class defines a comparator.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ResourceComparator implements Comparator<Object> {

		@Override
		public int compare(Object o1, Object o2) {
			return getElementName(o1).compareTo(getElementName(o2));
		}

	}

	/**
	 * This class defines a filter.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ResourceFilter extends ItemsFilter {

		public ResourceFilter() {
			super(new SearchPattern(SearchPattern.RULE_PATTERN_MATCH));

			// update pattern to look for anything before and after the original
			// pattern
			String pattern = patternMatcher.getPattern();
			pattern = "*" + pattern + "*";
			patternMatcher.setPattern(pattern);
		}

		@Override
		public boolean isConsistentItem(Object item) {
			return true;
		}

		@Override
		public boolean matchItem(Object item) {
			String name = getElementName(item);
			return matches(name);
		}
	}

	private static final String DIALOG_SETTINGS = "net.sf.dftools.ui.slam.FilteredRefinementDialog"; //$NON-NLS-1$

	private ResourceComparator comparator;

	private IJavaProject project;

	private String fileExt;

	/**
	 * Creates a new filtered actors dialog.
	 * 
	 * @param project
	 * @param shell
	 */
	public FilteredRefinementDialog(IProject project, Shell shell,
			String fileExt) {
		super(shell);
		this.project = JavaCore.create(project);
		comparator = new ResourceComparator();
		this.fileExt = fileExt;
	}

	private void addChildren(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, String path, IResource resource)
			throws CoreException {
		if (resource.getType() == IResource.FOLDER) {
			IFolder folder = (IFolder) resource;
			path = path + resource.getName() + ".";
			for (IResource member : folder.members()) {
				addChildren(contentProvider, itemsFilter, path, member);
			}
		} else if (fileExt.equals(resource.getFileExtension())) {
			// remove file extension
			String resourceName = resource.getFullPath().removeFileExtension()
					.lastSegment();
			contentProvider.add(path + resourceName, itemsFilter);
		}
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		// do nothing here
		return null;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new ResourceFilter();
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
			throws CoreException {
		List<IFolder> srcFolders = getAllSourceFolders(project
				.getProject());
		for (IFolder srcFolder : srcFolders) {
			for (IResource member : srcFolder.members()) {
				addChildren(contentProvider, itemsFilter, "", member);
			}
		}
	}
	
	/**
	 * Returns the list of ALL source folders of the required projects as well
	 * as of the given project as a list of absolute workspace paths.
	 * 
	 * @param project
	 *            a project
	 * @return a list of absolute workspace paths
	 * @throws CoreException
	 */
	public static List<IFolder> getAllSourceFolders(IProject project) {
		List<IFolder> srcFolders = new ArrayList<IFolder>();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return srcFolders;
		}

		// add source folders of this project
		srcFolders.addAll(getSourceFolders(project));

		// add source folders of required projects
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			for (String name : javaProject.getRequiredProjectNames()) {
				IProject refProject = root.getProject(name);
				srcFolders.addAll(getAllSourceFolders(refProject));
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return srcFolders;
	}
	
	/**
	 * Returns the list of source folders of the given project as a list of
	 * absolute workspace paths.
	 * 
	 * @param project
	 *            a project
	 * @return a list of absolute workspace paths
	 */
	public static List<IFolder> getSourceFolders(IProject project) {
		List<IFolder> srcFolders = new ArrayList<IFolder>();

		IJavaProject javaProject = JavaCore.create(project);
		if (!javaProject.exists()) {
			return srcFolders;
		}

		// iterate over package roots
		try {
			for (IPackageFragmentRoot root : javaProject
					.getPackageFragmentRoots()) {
				IResource resource = root.getCorrespondingResource();
				if (resource != null && resource.getType() == IResource.FOLDER) {
					srcFolders.add((IFolder) resource);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return srcFolders;
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault()
				.getDialogSettings().getSection(DIALOG_SETTINGS);

		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings()
					.addNewSection(DIALOG_SETTINGS);
		}

		return settings;
	}

	@Override
	public String getElementName(Object item) {
		return String.valueOf(item);
	}

	@Override
	protected Comparator<?> getItemsComparator() {
		return comparator;
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

}
