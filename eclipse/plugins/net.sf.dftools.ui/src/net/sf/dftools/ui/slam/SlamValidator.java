/**
 * 
 */
package net.sf.dftools.ui.slam;

import net.sf.graphiti.model.Graph;
import net.sf.graphiti.model.IValidator;

import org.eclipse.core.resources.IFile;

/**
 * Validating the S-LAM model
 * 
 * @author mpelcat
 */
public final class SlamValidator implements IValidator {

	@Override
	public boolean validate(Graph graph, IFile file) {
		return true;
	}

}
