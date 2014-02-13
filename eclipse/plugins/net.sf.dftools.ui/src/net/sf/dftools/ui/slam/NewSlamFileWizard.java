/**
 * 
 */
package net.sf.dftools.ui.slam;

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
import org.ietr.graphiti.GraphitiModelPlugin;
import org.ietr.graphiti.model.Configuration;
import org.ietr.graphiti.model.Graph;
import org.ietr.graphiti.model.ObjectType;
import org.ietr.graphiti.ui.wizards.WizardSaveGraphPage;

/**
 * This class provides a wizard to create a new slam network.
 * 
 * @author Matthieu Wipliez
 * @author mpelcat
 */
public class NewSlamFileWizard extends Wizard implements INewWizard {

	private IStructuredSelection selection;

	private IWorkbench workbench;

	/**
	 * Creates a new wizard.
	 */
	public NewSlamFileWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("New Architecture (S-LAM)");
	}

	@Override
	public void addPages() {
		WizardSaveGraphPage page = new WizardSaveGraphPage(selection);

		Configuration configuration = GraphitiModelPlugin.getDefault()
				.getConfiguration("S-LAM Design");
		ObjectType type = configuration.getGraphType("S-LAM Design");

		page.setGraph(new Graph(configuration, type, true));
		page.setDescription("Create a new System-Level Architecture Model.");
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