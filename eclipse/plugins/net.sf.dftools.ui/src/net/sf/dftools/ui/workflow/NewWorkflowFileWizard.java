/**
 * 
 */
package net.sf.dftools.ui.workflow;

import net.sf.graphiti.GraphitiModelPlugin;
import net.sf.graphiti.model.Configuration;
import net.sf.graphiti.model.Graph;
import net.sf.graphiti.model.ObjectType;
import net.sf.graphiti.ui.wizards.WizardSaveGraphPage;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * This class provides a wizard to create a new workflow network.
 * 
 * @author Matthieu Wipliez
 * @author mpelcat
 */
public class NewWorkflowFileWizard extends Wizard implements INewWizard {

	private IStructuredSelection selection;

	private IWorkbench workbench;

	/**
	 * Creates a new wizard.
	 */
	public NewWorkflowFileWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("New Workflow");
	}

	@Override
	public void addPages() {
		WizardSaveGraphPage page = new WizardSaveGraphPage(selection);

		Configuration configuration = GraphitiModelPlugin.getDefault()
				.getConfiguration("Workflow");
		ObjectType type = configuration.getGraphType("DFTools Workflow");

		page.setGraph(new Graph(configuration, type, true));
		page.setDescription("Create a new Workflow.");
		addPage(page);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		this.workbench = workbench;
	}

	@Override
	public boolean performFinish() {
		final WizardSaveGraphPage page = (WizardSaveGraphPage) getPage("saveGraph");
		IFile file = page.createNewFile();
		if (file == null) {
			return false;
		}

		// Open editor on new file.
		IWorkbenchWindow dw = workbench.getActiveWorkbenchWindow();
		try {
			if (dw != null) {
				BasicNewResourceWizard.selectAndReveal(file, dw);
				IWorkbenchPage activePage = dw.getActivePage();
				if (activePage != null) {
					IDE.openEditor(activePage, file, true);
				}
			}
		} catch (PartInitException e) {
			MessageDialog.openError(dw.getShell(), "Problem opening editor",
					e.getMessage());
		}

		return true;
	}

}