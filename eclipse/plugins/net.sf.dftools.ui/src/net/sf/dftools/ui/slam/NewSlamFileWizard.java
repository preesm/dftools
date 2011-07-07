/**
 * 
 */
package net.sf.dftools.ui.slam;

import org.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;

/**
 * Creating a new S-LAM file
 * 
 * @author mpelcat
 */
public class NewSlamFileWizard extends BasicNewFileResourceWizard {

	@Override
	public void addPages() {
		super.addPages();
		super.setWindowTitle("New S-LAM File");
	}

	@Override
	protected void initializeDefaultPageImageDescriptor() {
		super.initializeDefaultPageImageDescriptor();
	}

}
