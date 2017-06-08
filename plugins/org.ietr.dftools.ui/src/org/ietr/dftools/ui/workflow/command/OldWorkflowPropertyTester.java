
package org.ietr.dftools.ui.workflow.command;

import java.io.File;
import java.io.IOException;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.ietr.dftools.workflow.converter.WorkflowConverter;

/**
 */
public class OldWorkflowPropertyTester extends PropertyTester {

  @Override
  public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {

    final IFile file = (IFile) receiver;
    final File file2 = file.getLocation().toFile();
    try {
      boolean newWorkflow = WorkflowConverter.isNewWorkflow(file2);
      return !newWorkflow;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

}
