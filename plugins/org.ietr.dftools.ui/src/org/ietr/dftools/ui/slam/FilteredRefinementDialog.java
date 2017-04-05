/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
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

	private static final String DIALOG_SETTINGS = "org.ietr.dftools.ui.slam.FilteredRefinementDialog"; //$NON-NLS-1$

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
