/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Ghislain Roquier <ghislain.roquier@insa-rennes.fr> (2011)
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
package org.ietr.dftools.architecture.utils;

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

// TODO: Auto-generated Javadoc
/**
 * The Class ArchitectureUtil.
 */
public class ArchitectureUtil {

  /**
   * If it does not exist, creates the given folder. If the parent folders do not exist either, create them.
   *
   * @param folder
   *          a folder
   * @throws CoreException
   *           the core exception
   */
  public static void createFolder(IFolder folder) throws CoreException {
    final IPath path = folder.getFullPath();
    if (folder.exists()) {
      return;
    }

    final int n = path.segmentCount();
    if (n < 2) {
      throw new IllegalArgumentException("the path of the given folder " + "must have at least two segments");
    }

    // check the first folder
    final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
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
   * Returns the network in the given project that has the given qualified name.
   *
   * @param project
   *          project
   * @param designQualifiedName
   *          the design qualified name
   * @return if there is such a network, a file, otherwise <code>null</code>
   */
  public static IFile getDesign(final IProject project, final String designQualifiedName) {
    final String name = designQualifiedName.replace('.', '/');
    final IPath path = new Path(name).addFileExtension("design");
    for (final IFolder folder : ArchitectureUtil.getSourceFolders(project)) {
      final IFile inputFile = folder.getFile(path);
      if ((inputFile != null) && inputFile.exists()) {
        return inputFile;
      }
    }

    return null;
  }

  /**
   * Returns the qualified name of the given file, i.e. qualified.name.of.File for <code>/project/sourceFolder/qualified/name/of/File.fileExt</code>
   *
   * @param file
   *          a file
   * @return a qualified name, or <code>null</code> if the file is not in a source folder
   */
  public static String getQualifiedName(final IFile file) {
    final IProject project = file.getProject();
    final IPath filePath = file.getFullPath();
    for (final IFolder folder : ArchitectureUtil.getSourceFolders(project)) {
      final IPath folderPath = folder.getFullPath();
      if (folderPath.isPrefixOf(filePath)) {
        // yay we found the folder!
        final IPath qualifiedPath = filePath.removeFirstSegments(folderPath.segmentCount()).removeFileExtension();
        return qualifiedPath.toString();
      }
    }

    return null;
  }

  /**
   * Returns the list of source folders of the given project as a list of absolute workspace paths.
   *
   * @param project
   *          a project
   * @return a list of absolute workspace paths
   */
  public static List<IFolder> getSourceFolders(final IProject project) {
    final List<IFolder> srcFolders = new ArrayList<>();

    final IJavaProject javaProject = JavaCore.create(project);
    if (!javaProject.exists()) {
      return srcFolders;
    }

    // iterate over package roots
    try {
      for (final IPackageFragmentRoot root : javaProject.getPackageFragmentRoots()) {
        final IResource resource = root.getCorrespondingResource();
        if ((resource != null) && (resource.getType() == IResource.FOLDER)) {
          srcFolders.add((IFolder) resource);
        }
      }
    } catch (final CoreException e) {
      e.printStackTrace();
    }

    return srcFolders;
  }

  /**
   * Returns the list of ALL source folders of the required projects as well as of the given project as a list of absolute workspace paths.
   *
   * @param project
   *          a project
   * @return a list of absolute workspace paths
   */
  public static List<IFolder> getAllSourceFolders(final IProject project) {
    final List<IFolder> srcFolders = new ArrayList<>();

    final IJavaProject javaProject = JavaCore.create(project);
    if (!javaProject.exists()) {
      return srcFolders;
    }

    // add source folders of this project
    srcFolders.addAll(ArchitectureUtil.getSourceFolders(project));

    // add source folders of required projects
    final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    try {
      for (final String name : javaProject.getRequiredProjectNames()) {
        final IProject refProject = root.getProject(name);
        srcFolders.addAll(ArchitectureUtil.getAllSourceFolders(refProject));
      }
    } catch (final CoreException e) {
      e.printStackTrace();
    }

    return srcFolders;
  }

}
