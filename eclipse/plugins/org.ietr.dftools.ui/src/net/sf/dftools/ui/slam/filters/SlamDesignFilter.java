package net.sf.dftools.ui.slam.filters;

import net.sf.graphiti.model.Graph;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

/**
 * This class filters s-lam elements to enable the correct property
 * tabs
 * 
 * @author mpelcat
 * 
 */
public class SlamDesignFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof EditPart) {
			Object model = ((EditPart) toTest).getModel();
			
			// Designs have parameters
			if (model instanceof Graph) {
				return ((Graph) model).getType().getName()
						.equals("S-LAM Design");
			}
		}
		return false;
	}

}
