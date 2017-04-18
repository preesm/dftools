/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Ghislain Roquier <ghislain.roquier@insa-rennes.fr> (2011)
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
package org.ietr.dftools.architecture.ui;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

// TODO: Auto-generated Javadoc
/**
 * This class provides a wizard to create a new Architecture project.
 *
 * @author Matthieu Wipliez
 */
public class ArchitectureProjectWizard extends BasicNewProjectResourceWizard {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard#addPages()
   */
  @Override
  public void addPages() {
    super.addPages();
    super.setWindowTitle("New Architecture Project");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard#initializeDefaultPageImageDescriptor()
   */
  @Override
  protected void initializeDefaultPageImageDescriptor() {
    super.initializeDefaultPageImageDescriptor();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    final boolean finish = super.performFinish();
    try {
      final IProject project = getNewProject();
      IProjectDescription description = project.getDescription();
      final String[] natures = description.getNatureIds();
      final String[] newNatures = new String[natures.length + 1];

      // add new natures
      System.arraycopy(natures, 0, newNatures, 1, natures.length);
      newNatures[0] = JavaCore.NATURE_ID;
      description.setNatureIds(newNatures);
      project.setDescription(description, null);

      // retrieves the up-to-date description
      description = project.getDescription();

      // filters out the Java builder
      final ICommand[] commands = description.getBuildSpec();
      final List<ICommand> buildSpec = new ArrayList<>(commands.length);
      for (final ICommand command : commands) {
        if (!JavaCore.BUILDER_ID.equals(command.getBuilderName())) {
          buildSpec.add(command);
        }
      }

      // updates the description and replaces the existing description
      description.setBuildSpec(buildSpec.toArray(new ICommand[0]));
      project.setDescription(description, null);
    } catch (final CoreException e) {
      e.printStackTrace();
    }

    return finish;
  }

}
