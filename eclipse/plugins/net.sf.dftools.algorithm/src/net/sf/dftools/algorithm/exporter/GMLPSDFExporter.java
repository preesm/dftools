package net.sf.dftools.algorithm.exporter;

import java.io.OutputStream;
import java.util.Collection;

import org.jgrapht.Graph;
import net.sf.dftools.algorithm.model.psdf.PSDFGraph;
import net.sf.dftools.algorithm.model.psdf.PSDFInitVertex;
import net.sf.dftools.algorithm.model.psdf.PSDFSubInitVertex;
import net.sf.dftools.algorithm.model.psdf.parameters.PSDFDynamicParameter;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import org.w3c.dom.Element;

public class GMLPSDFExporter extends GMLSDFExporter {

	public GMLPSDFExporter() {
		super();
		addKey(PSDFGraph.DYNAMIC_PARAMETERS, PSDFGraph.DYNAMIC_PARAMETERS,
				"graph", null, null);
		addKey(PSDFInitVertex.AFFECTED_PARAMETERS, PSDFInitVertex.AFFECTED_PARAMETERS,
				"node", null, null);
	}

	public void exportGraph(Graph<SDFAbstractVertex, SDFEdge> graph,
			OutputStream out) {
		Element graphElt = super.exportGraph(graph);
		graphElt.setAttribute("kind", "psdf");
		if (((PSDFGraph) graph).getDynamicParameters() != null) {
			exportDynamicParameters(((PSDFGraph) graph).getDynamicParameters(),
					graphElt);
		}
		transform(out);
	}

	public Element exportGraph(Graph<SDFAbstractVertex, SDFEdge> graph) {
		Element graphElt = super.exportGraph(graph);
		if (((PSDFGraph) graph).getDynamicParameters() != null) {
			exportDynamicParameters(((PSDFGraph) graph).getDynamicParameters(),
					graphElt);
		}
		return graphElt;
	}

	protected Element exportNode(SDFAbstractVertex vertex, Element parentELement) {
		Element nodeElt = super.exportNode(vertex, parentELement);
		if (vertex instanceof PSDFInitVertex) {

			exportAffectedParameters(((PSDFInitVertex) vertex)
					.getAffectedParameters(), nodeElt);
		} else if (vertex instanceof PSDFSubInitVertex) {

			exportAffectedParameters(((PSDFSubInitVertex) vertex)
					.getAffectedParameters(), nodeElt);
		}
		return nodeElt ;
	}

	protected void exportDynamicParameters(
			Collection<PSDFDynamicParameter> parameters, Element parentELement) {
		Element dataElt = appendChild(parentELement, "data");
		dataElt.setAttribute("key", "dynamic_parameters");
		for (PSDFDynamicParameter param : parameters) {
			Element paramElt = appendChild(dataElt, "parameter");
			paramElt.setAttribute("name", param.getName());
			if(param.getDomain() != null){
				paramElt.setAttribute("value", param.getDomain().toString());
			}
		}
	}

	protected void exportAffectedParameters(
			Collection<PSDFDynamicParameter> parameters, Element parentELement) {
		Element dataElt = appendChild(parentELement, "data");
		dataElt.setAttribute("key", "affected_parameters");
		for (PSDFDynamicParameter param : parameters) {
			Element paramElt = appendChild(dataElt, "parameter");
			paramElt.setAttribute("name", param.getName());
		}
	}

}
