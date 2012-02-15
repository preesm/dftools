package net.sf.dftools.algorithm.model.psdf;

import java.util.Collection;
import java.util.HashMap;

import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.parameters.Argument;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.psdf.parameters.PSDFDynamicArgument;
import net.sf.dftools.algorithm.model.psdf.parameters.PSDFDynamicParameter;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;

public class PSDFInitVertex extends SDFAbstractVertex implements
		IPSDFSpecificVertex {

	public static final String INIT = "init";

	public static final String AFFECTED_PARAMETERS = "affected_parameters";

	public PSDFInitVertex() {
		super();
		this.setKind(INIT);
		this.setNbRepeat(1);
	}

	public void addArgument(Argument arg) {
		super.addArgument(arg);
		if (arg instanceof PSDFDynamicArgument) {
			if (((PSDFDynamicArgument) arg).getDynamicValue() instanceof PSDFDynamicParameter) {
				this.addAffectedParameter((PSDFDynamicParameter) ((PSDFDynamicArgument) arg)
						.getDynamicValue());
			}
		}
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

	public PSDFInitVertex clone() {
		PSDFInitVertex newVertex = new PSDFInitVertex();
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

	@Override
	public void connectionAdded(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionRemoved(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub

	}



}
