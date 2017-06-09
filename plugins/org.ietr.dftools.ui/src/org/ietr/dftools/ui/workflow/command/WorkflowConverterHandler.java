package org.ietr.dftools.ui.workflow.command;

import java.io.File;
import java.io.IOException;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.ietr.dftools.workflow.converter.WorkflowConverter;

/**
 *
 */
public class WorkflowConverterHandler extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {

    final ISelection activeSelection = HandlerUtil.getActiveMenuSelection(event);

    IFile workflowFile = null;
    if (activeSelection instanceof ITreeSelection) {
      final ITreeSelection treeSelection = (ITreeSelection) activeSelection;
      final Object firstElement = treeSelection.getFirstElement();
      if (firstElement instanceof IFile) {
        // get there when right clicking on a workflow file in the file tree explorer:
        // that is a TreeSelection and active element is an IFile
        workflowFile = (IFile) firstElement;
      }
    }

    if (workflowFile == null) {
      final String message = "Could not locate Workflow file from active selection [" + activeSelection + "] of type [" + activeSelection.getClass() + "]";
      throw new UnsupportedOperationException(message);
    }
    File file = workflowFile.getLocation().toFile();
    try {
      boolean newWorkflow = WorkflowConverter.isNewWorkflow(file);
      if (!newWorkflow) {

        WorkflowConverter.convert(file);
        workflowFile.getParent().refreshLocal(IResource.DEPTH_ONE, null);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (CoreException e) {
      e.printStackTrace();
    }

    return null;
  }

}
