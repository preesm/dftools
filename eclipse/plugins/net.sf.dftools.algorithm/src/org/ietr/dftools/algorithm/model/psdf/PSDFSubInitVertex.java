package org.ietr.dftools.algorithm.model.psdf;

import java.util.Collection;
import java.util.HashMap;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.psdf.parameters.PSDFDynamicParameter;
import org.ietr.dftools.algorithm.model.psdf.types.PSDFEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;

public class PSDFSubInitVertex extends SDFAbstractVertex implements
		IPSDFSpecificVertex {

	public static final String SUB_INIT = "sub_init";

	public static final String AFFECTED_PARAMETERS = "affected_parameters";

	public PSDFSubInitVertex() {
		super();
		this.setKind(SUB_INIT);
	}

	@SuppressWarnings("unchecked")
	public void addAffectedParameter(PSDFDynamicParameter p) {
		if (this.getPropertyBean().getValue(AFFECTED_PARAMETERS, HashMap.class) == null) {
			this.getPropertyBean().setValue(AFFECTED_PARAMETERS,
					new HashMap<String, PSDFDynamicParameter>());
		}
		((HashMap<String, PSDFDynamicParameter>) this.getPropertyBean()
				.getValue(AFFECTED_PARAMETERS)).put(p.getName(), p);
	}

	@SuppressWarnings("unchecked")
	public PSDFDynamicParameter getAffectedParameter(String p) {
		if (this.getPropertyBean().getValue(AFFECTED_PARAMETERS, HashMap.class) == null) {
			this.getPropertyBean().setValue(AFFECTED_PARAMETERS,
					new HashMap<String, PSDFDynamicParameter>());
		}
		return ((HashMap<String, PSDFDynamicParameter>) this.getPropertyBean()
				.getValue(AFFECTED_PARAMETERS)).get(p);
	}

	@SuppressWarnings("unchecked")
	public Collection<PSDFDynamicParameter> getAffectedParameters() {
		if (this.getPropertyBean().getValue(AFFECTED_PARAMETERS) == null) {
			this.getPropertyBean().setValue(AFFECTED_PARAMETERS,
					new HashMap<String, PSDFDynamicParameter>());
		}
		return ((HashMap<String, PSDFDynamicParameter>) this.getPropertyBean()
				.getValue(AFFECTED_PARAMETERS)).values();
	}

	public int computeTypeValue(PSDFEdgePropertyType type) {
		return 0;
	}

	@Override
	public void connectionAdded(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionRemoved(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub

	}

	@Override
	public PSDFSubInitVertex clone() {
		PSDFSubInitVertex newVertex = new PSDFSubInitVertex();
		for (String key : this.getPropertyBean().keys()) {
			if (this.getPropertyBean().getValue(key) != null) {
				Object val = this.getPropertyBean().getValue(key);
				newVertex.getPropertyBean().setValue(key, val);
			}
		}
		try {
			newVertex.setNbRepeat(this.getNbRepeat());
		} catch (InvalidExpressionException e) {
			e.printStackTrace();
		}
		return newVertex;
	}

}
