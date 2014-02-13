package net.sf.dftools.architecture.ui.properties;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;
import org.ietr.graphiti.model.Graph;
import org.ietr.graphiti.model.Vertex;

public class ArchitectureVertexFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof EditPart) {
			Object model = ((EditPart) toTest).getModel();
			if (model instanceof Vertex) {
				Vertex vertex = (Vertex) model;
				Graph graph = vertex.getParent();
				return graph.getType().getName().equals("IP-XACT design");
			}
		}
		return false;
	}

}
