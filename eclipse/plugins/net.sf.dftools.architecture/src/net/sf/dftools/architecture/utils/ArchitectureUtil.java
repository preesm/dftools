package net.sf.dftools.architecture.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;

public class ArchitectureUtil {
	
	/**
	 * If it does not exist, creates the given folder. If the parent folders do
	 * not exist either, create them.
	 * 
	 * @param folder
	 *            a folder
	 * @throws CoreException
	 */
	public static void createFolder(IFolder folder) throws CoreException {
		IPath path = folder.getFullPath();
		if (folder.exists()) {
			return;
		}

		int n = path.segmentCount();
		if (n < 2) {
			throw new IllegalArgumentException("the path of the given folder "
					+ "must have at least two segments");
		}

		// check the first folder
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		folder = root.getFolder(path.uptoSegment(2));
		if (!folder.exists()) {
			folder.create(true, false, null);
		}

		// and then check all the descendants
		for (int i = 2; i < n; i++) {
			folder = folder.getFolder(new Path(path.segment(i)));
			if (!folder.exists()) {
				folder.create(true, false, null);
			}
		}
	}
	

	/**
	 * Returns the network in the given project that has the given qualified
	 * name.
	 * 
	 * @param project
	 *            project
	 * @param networkName
	 *            qualified name of a network
	 * @return if there is such a network, a file, otherwise <code>null</code>
	 */
	public static IFile getDesign(IProject project, String designQualifiedName) {
		String name = designQualifiedName.replace('.', '/');
		IPath path = new Path(name).addFileExtension("design");
		for (IFolder folder : getSourceFolders(project)) {
			IFile inputFile = folder.getFile(path);
			if (inputFile != null && inputFile.exists()) {
				return inputFile;
			}
		}

		return null;
	}

	/**
	 * Returns the qualified name of the given file, i.e. qualified.name.of.File
	 * for <code>/project/sourceFolder/qualified/name/of/File.fileExt</code>
	 * 
	 * @param file
	 *            a file
	 * @return a qualified name, or <code>null</code> if the file is not in a
	 *         source folder
	 */
	public static String getQualifiedName(IFile file) {
		IProject project = file.getProject();
		IPath filePath = file.getFullPath();
		for (IFolder folder : getSourceFolders(project)) {
			IPath folderPath = folder.getFullPath();
			if (folderPath.isPrefixOf(filePath)) {
				// yay we found the folder!
				IPath qualifiedPath = filePath.removeFirstSegments(
						folderPath.segmentCount()).removeFileExtension();
				return qualifiedPath.toString();
			}
		}

		return null;
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

	/**
	 * Returns the network in the given project that has the given qualified
	 * name.
	 * 
	 * @param project
	 *            project
	 * @param qualifiedName
	 *            qualified name of a network
	 * @return if there is such a network, a file, otherwise <code>null</code>
	 */
	public static IFile getFile(IProject project, String qualifiedName,
			String extension) {
		String name = qualifiedName.replace('.', '/');
		IPath path = new Path(name).addFileExtension(extension);
		for (IFolder folder : getAllSourceFolders(project)) {
			IFile inputFile = folder.getFile(path);
			if (inputFile != null && inputFile.exists()) {
				return inputFile;
			}
		}

		return null;
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

}
