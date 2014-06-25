/**
 * 
 */
package org.ietr.dftools.ui.slam;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.ietr.dftools.architecture.slam.serialize.RefinementList;
import org.ietr.dftools.graphiti.model.Vertex;

/**
 * A dialog box used to select multiple refinements for a single vertex in a
 * Graphiti graph. It also gives a mean to chose one of the refinements.
 * 
 * @author mpelcat
 * 
 */
public class ChooseRefinementListDialog extends ListDialog {

	/**
	 * Listener used when the buttons are pressed
	 */
	private class RefinementListener implements SelectionListener {

		/**
		 * type of action of the listener
		 */
		private String type;

		/**
		 * the called dialog window
		 */
		private ChooseRefinementListDialog dialog;

		public RefinementListener(String type, ChooseRefinementListDialog dialog) {
			this.type = type;
			this.dialog = dialog;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			TableViewer tableViewer = dialog.getTableViewer();

			if (tableViewer.getSelection() instanceof IStructuredSelection) {
				IStructuredSelection tableSelection = (IStructuredSelection) tableViewer
						.getSelection();
				Object[] tableSelections = tableSelection.toArray();
				if (type.equals("add")) {
					IPath name = policy.useExistingFile(vertex, getShell(), "slam");
					tableViewer.add(name);
//					refinementList.addName(name);
				} else if (type.equals("remove")) {
					if ((tableSelections.length == 1)
							&& tableSelections[0] instanceof String) {
						String name = (String) tableSelections[0];
						tableViewer.remove(name);
						refinementList.removeName(name);
					}
				}
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}

	/**
	 * Listener used when the add button is pressed
	 */
	private RefinementListener addListener = null;

	/**
	 * Listener used when the remove button is pressed
	 */
	private RefinementListener removeListener = null;

	/**
	 * Refinement policy used to call refinement dialog
	 */
	private SlamRefinementPolicy policy = null;

	/**
	 * The vertex being refined
	 */
	private Vertex vertex = null;

	/**
	 * The returned refinement
	 */
	private RefinementList refinementList = null;

	/**
	 * Is this window used to edit the list or to choose one element
	 */
	private boolean edit = false;

	/**
	 * Creating A dialog box used to select multiple refinements for a single
	 * vertex in a Graphiti graph
	 * 
	 * @param vertex
	 *            the vertex being refined
	 * @param shell
	 *            the parent Shell
	 * @param policy
	 *            the refinement policy that initiated this window
	 * @param edit
	 *            is this window used to edit the list or to choose one element
	 */
	public ChooseRefinementListDialog(final Vertex vertex, Shell shell,
			SlamRefinementPolicy policy, boolean edit) {
		super(shell);
		// the cancel button is enabled only in case of choice
		setAddCancelButton(!edit);

		setHelpAvailable(false);
		this.policy = policy;
		this.vertex = vertex;
		this.edit = edit;

		ILabelProvider labelProvider = new LabelProvider();
		setLabelProvider(labelProvider);
		setContentProvider(new WorkbenchContentProvider() {
			@Override
			public Object[] getChildren(Object element) {
				if(element instanceof RefinementList){
					return ((RefinementList)element).toStringArray();
				}
				return super.getChildren(element);
			}
		});
	}

	@Override
	public void setInput(Object input) {
		super.setInput(input);

		if (input instanceof RefinementList) {
			refinementList = (RefinementList) input;
		}
	}

	public RefinementList openDialog() {
		open();
		return refinementList;
	}

	@Override
	protected Control createDialogArea(Composite container) {
		Control control = super.createDialogArea(container);

		addListener = new RefinementListener("add", this);
		removeListener = new RefinementListener("remove", this);

		return control;
	}

	/**
	 * Creating specific buttons additionnally to the original ones from
	 * {@link ListDialog}
	 * 
	 * @param parent
	 *            the parent Composite
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		if (edit) {
			Button addButton = createButton(parent, 5000, "Add", true);
			Button removeButton = createButton(parent, 5001, "Remove", true);

			addButton.addSelectionListener(addListener);
			removeButton.addSelectionListener(removeListener);
		}
		else{
			
		}
		
		super.createButtonsForButtonBar(parent);
	}

}
