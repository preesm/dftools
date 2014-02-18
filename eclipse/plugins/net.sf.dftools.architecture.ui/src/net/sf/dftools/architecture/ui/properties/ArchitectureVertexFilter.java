package net.sf.dftools.architecture.ui.properties;

import net.sf.graphiti.model.Graph;
import net.sf.graphiti.model.Vertex;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

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
