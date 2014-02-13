package net.sf.dftools.ui.slam.filters;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;
import org.ietr.graphiti.model.Graph;
import org.ietr.graphiti.model.Vertex;

/**
 * This class filters s-lam elements to enable the correct property
 * tabs
 * 
 * @author mpelcat
 * 
 */
public class SlamComponentFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof EditPart) {
			Object model = ((EditPart) toTest).getModel();

			// Hierarchical connection vertices and edges have no custom
			// parameters.
			// Elements of other types have.
			if (model instanceof Vertex) {
				Vertex vertex = (Vertex) model;
				if (!vertex.getType().toString().equals("hierConnection")) {
					Graph graph = vertex.getParent();
					return graph.getType().getName().equals("S-LAM Design");
				}
			}
		}
		return false;
	}

}
