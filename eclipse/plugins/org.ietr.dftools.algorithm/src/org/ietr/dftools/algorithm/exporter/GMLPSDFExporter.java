package org.ietr.dftools.algorithm.exporter;

import java.io.OutputStream;
import java.util.Collection;

import org.ietr.dftools.algorithm.model.AbstractGraph;
import org.ietr.dftools.algorithm.model.parameters.Parameter;
import org.ietr.dftools.algorithm.model.parameters.ParameterSet;
import org.ietr.dftools.algorithm.model.psdf.PSDFGraph;
import org.ietr.dftools.algorithm.model.psdf.PSDFInitVertex;
import org.ietr.dftools.algorithm.model.psdf.PSDFSubInitVertex;
import org.ietr.dftools.algorithm.model.psdf.parameters.PSDFDynamicArgument;
import org.ietr.dftools.algorithm.model.psdf.parameters.PSDFDynamicParameter;
import org.ietr.dftools.algorithm.model.psdf.types.PSDFEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.w3c.dom.Element;

public class GMLPSDFExporter extends GMLSDFExporter {

	public GMLPSDFExporter() {
		super();
	}

	public void exportGraph(AbstractGraph<SDFAbstractVertex, SDFEdge> graph,
			OutputStream out) {
		Element graphElt = super.exportGraph(graph);
		graphElt.setAttribute("kind", "psdf");
		if (((PSDFGraph) graph).getParameters() != null) {
			exportDynamicParameters(((PSDFGraph) graph).getParameters(),
					graphElt);
		}
		transform(out);
	}

	@Override
	public Element exportGraph(AbstractGraph<SDFAbstractVertex, SDFEdge> graph) {
		Element graphElt = super.exportGraph(graph);
		if (((PSDFGraph) graph).getParameters() != null) {
			exportDynamicParameters(((PSDFGraph) graph).getParameters(),
					graphElt);
		}
		return graphElt;
	}

	@Override
	protected Element exportNode(SDFAbstractVertex vertex, Element parentELement) {
		Element nodeElt = super.exportNode(vertex, parentELement);
		if (vertex instanceof PSDFInitVertex) {

			exportAffectedParameters(
					((PSDFInitVertex) vertex).getAffectedParameters(), nodeElt);
		} else if (vertex instanceof PSDFSubInitVertex) {

			exportAffectedParameters(
					((PSDFSubInitVertex) vertex).getAffectedParameters(),
					nodeElt);
		}
		return nodeElt;
	}

	protected void exportDynamicParameters(ParameterSet parameters,
			Element parentELement) {
		Element dataElt = appendChild(parentELement, "data");
		dataElt.setAttribute("key", "dynamic_parameters");
		for (Parameter param : parameters.values()) {
			if (param instanceof PSDFDynamicParameter) {
				Element paramElt = appendChild(dataElt, "parameter");
				paramElt.setAttribute("name", param.getName());
				if (((PSDFDynamicParameter) param).getDomain() != null) {
					paramElt.setAttribute("value",
							((PSDFDynamicParameter) param).getDomain()
									.toString());
				}
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

	public static void main(String[] args) {
		PSDFGraph psdfGraph = new PSDFGraph();
		psdfGraph.setName("decimator");

		PSDFDynamicParameter rateParameter = new PSDFDynamicParameter("rate");
		PSDFDynamicParameter phaseParameter = new PSDFDynamicParameter("phase");

		psdfGraph.addParameter(rateParameter);
		psdfGraph.addParameter(phaseParameter);

		SDFSourceInterfaceVertex dataPort = new SDFSourceInterfaceVertex();
		dataPort.setName("data");

		SDFSourceInterfaceVertex phasePort = new SDFSourceInterfaceVertex();
		phasePort.setName("phase");

		SDFSinkInterfaceVertex outPort = new SDFSinkInterfaceVertex();
		outPort.setName("out");

		PSDFInitVertex init = new PSDFInitVertex();
		init.setName("init_rate");
		init.addAffectedParameter(rateParameter);

		PSDFSubInitVertex subInit = new PSDFSubInitVertex();
		subInit.setName("init_phase");
		subInit.addAffectedParameter(phaseParameter);
		subInit.addSink(new SDFSinkInterfaceVertex());

		psdfGraph.setSubInitVertex(subInit);
		psdfGraph.setInitVertex(init);

		SDFVertex dec = new SDFVertex();
		dec.setName("dec");

		dec.addArgument(new PSDFDynamicArgument("phase", phaseParameter));
		psdfGraph.addVertex(dec);

		SDFEdge edge1 = psdfGraph.addEdge(phasePort, subInit);
		SDFEdge edge2 = psdfGraph.addEdge(dataPort, dec);
		SDFEdge edge3 = psdfGraph.addEdge(dec, outPort);

		edge1.setCons(new SDFIntEdgePropertyType(1));
		edge1.setProd(new SDFIntEdgePropertyType(1));

		edge2.setCons(new PSDFEdgePropertyType("rate"));
		edge2.setProd(new SDFIntEdgePropertyType(1));

		edge3.setProd(new SDFIntEdgePropertyType(1));
		edge3.setCons(new SDFIntEdgePropertyType(1));

	}

}
