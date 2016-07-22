package org.ietr.dftools.algorithm.model.sdf;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractEdgePropertyType;
import org.ietr.dftools.algorithm.model.InterfaceDirection;
import org.ietr.dftools.algorithm.model.PropertyFactory;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.types.SDFExpressionEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.types.SDFNumericalEdgePropertyTypeFactory;
import org.ietr.dftools.algorithm.model.sdf.types.SDFStringEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.types.SDFTextualEdgePropertyTypeFactory;

/**
 * Class representing an SDFEdge which is an edge with production and consuming
 * rates and length of delay specified
 * 
 * @author jpiat
 * @author kdesnos
 * 
 */
public class SDFEdge extends AbstractEdge<SDFGraph, SDFAbstractVertex> {

	/**
	 * Property name for property edge_cons
	 */
	public static final String EDGE_CONS = "edge_cons";
	/**
	 * Property name for property edge_delay
	 */
	public static final String EDGE_DELAY = "edge_delay";
	/**
	 * Property name for property edge_prod
	 */
	public static final String EDGE_PROD = "edge_prod";
	/**
	 * Property name for data type
	 */
	public static final String DATA_TYPE = "data_type";

	/**
	 * Property name for property source_port
	 */
	public static final String SOURCE_PORT = "source_port";

	/**
	 * Property name for property target_port
	 */
	public static final String TARGET_PORT = "target_port";

	/**
	 * Property name for property target_port_modifier
	 */
	public static final String TARGET_PORT_MODIFIER = "target_port_modifier";

	/**
	 * Property name for property source_port_modifier
	 */
	public static final String SOURCE_PORT_MODIFIER = "source_port_modifier";

	/**
	 * Modifier used to make a input port a pure input. <br>
	 * i.e. the corresponding actor will only read the corresponding data.
	 */
	public static final String MODIFIER_READ_ONLY = "read_only";

	/**
	 * Modifier used to make a input port an unused input. <br>
	 * i.e. the corresponding actor not use the corresponding input.
	 */
	 public static final String MODIFIER_UNUSED = "unused";
	
	/**
	 * Modifier used to make a input port a pure output. <br>
	 * i.e. the corresponding actor will only write the corresponding data but
	 * will not use the written data. In other terms, it does not matter if the
	 * written data is overwritten by another process, even during the execution
	 * of the producer actor.
	 */
	public static final String MODIFIER_WRITE_ONLY = "write_only";

	static {
		public_properties.add(EDGE_CONS);
		public_properties.add(EDGE_DELAY);
		public_properties.add(EDGE_PROD);
		public_properties.add(DATA_TYPE);
		public_properties.add(SOURCE_PORT_MODIFIER);
		public_properties.add(TARGET_PORT_MODIFIER);
	};

	/**
	 * Creates an SDFEdge with the default values (prod=0,delay=0,cons=0)
	 */
	public SDFEdge() {
		super();
		setProd(new SDFIntEdgePropertyType(0));
		setCons(new SDFIntEdgePropertyType(0));
		setDelay(new SDFIntEdgePropertyType(0));
		setDataType(new SDFStringEdgePropertyType("char"));
	}

	/**
	 * Constructs a new SDFEdge with its consuming and producing rates with a
	 * delay
	 * 
	 * @param prod
	 * @param cons
	 * @param delay
	 * @param dataType
	 */
	public SDFEdge(AbstractEdgePropertyType<?> prod,
			AbstractEdgePropertyType<?> cons,
			AbstractEdgePropertyType<?> delay,
			AbstractEdgePropertyType<?> dataType) {
		super();
		setProd(prod);
		setCons(cons);
		setDelay(delay);
		setDataType(dataType);
	}

	/**
	 * Getter of the property <tt>cons</tt>
	 * 
	 * @return Returns the cons.
	 * 
	 */
	public AbstractEdgePropertyType<?> getCons() {
		AbstractEdgePropertyType<?> cons = (AbstractEdgePropertyType<?>) getPropertyBean()
				.getValue(EDGE_CONS, AbstractEdgePropertyType.class);
		if (cons instanceof SDFExpressionEdgePropertyType) {
			((SDFExpressionEdgePropertyType) cons).setExpressionSolver(this
					.getBase());
		}
		return cons;
	}

	/**
	 * Getter of the property <tt>delay</tt>
	 * 
	 * @return Returns the delay.
	 * 
	 */
	public AbstractEdgePropertyType<?> getDelay() {
		AbstractEdgePropertyType<?> delay = (AbstractEdgePropertyType<?>) getPropertyBean()
				.getValue(EDGE_DELAY, AbstractEdgePropertyType.class);
		if (delay instanceof SDFExpressionEdgePropertyType) {
			((SDFExpressionEdgePropertyType) delay).setExpressionSolver(this
					.getBase());
		}
		return delay;
	}

	/**
	 * Getter of the property <tt>prod</tt>
	 * 
	 * @return Returns the prod.
	 * 
	 */
	public AbstractEdgePropertyType<?> getProd() {
		AbstractEdgePropertyType<?> prod = (AbstractEdgePropertyType<?>) getPropertyBean()
				.getValue(EDGE_PROD, AbstractEdgePropertyType.class);
		if (prod instanceof SDFExpressionEdgePropertyType) {
			((SDFExpressionEdgePropertyType) prod).setExpressionSolver(this
					.getBase());
		}
		return prod;
	}

	/**
	 * Getter of the property <tt>DATA_TYPE</tt>
	 * 
	 * @return Returns the prod.
	 * 
	 */
	public AbstractEdgePropertyType<?> getDataType() {
		return (AbstractEdgePropertyType<?>) getPropertyBean().getValue(
				DATA_TYPE, AbstractEdgePropertyType.class);
	}

	/**
	 * Give the source interface of this edge
	 * 
	 * @return The source vertex interface of this edge
	 */
	public SDFInterfaceVertex getSourceInterface() {
		return (SDFInterfaceVertex) getPropertyBean().getValue(SOURCE_PORT,
				SDFInterfaceVertex.class);
	}

	/**
	 * Give the target interface of this edge
	 * 
	 * @return The interface vertex target of this edge
	 */
	public SDFInterfaceVertex getTargetInterface() {
		return (SDFInterfaceVertex) getPropertyBean().getValue(TARGET_PORT,
				SDFInterfaceVertex.class);
	}

	public SDFStringEdgePropertyType getSourcePortModifier() {
		return (SDFStringEdgePropertyType) getPropertyBean().getValue(
				SOURCE_PORT_MODIFIER, SDFStringEdgePropertyType.class);
	}

	public SDFStringEdgePropertyType getTargetPortModifier() {
		return (SDFStringEdgePropertyType) getPropertyBean().getValue(
				TARGET_PORT_MODIFIER, SDFStringEdgePropertyType.class);
	}

	/**
	 * Setter of the property <tt>cons</tt>
	 * 
	 * @param cons
	 *            The cons to set.
	 * 
	 */
	public void setCons(AbstractEdgePropertyType<?> cons) {
		getPropertyBean().setValue(EDGE_CONS, null, cons);
		if (cons instanceof SDFExpressionEdgePropertyType) {
			((SDFExpressionEdgePropertyType) cons).setExpressionSolver(this
					.getBase());
		}
	}

	/**
	 * Setter of the property <tt>delay</tt>
	 * 
	 * @param delay
	 *            The delay to set.
	 * 
	 */
	public void setDelay(AbstractEdgePropertyType<?> delay) {
		getPropertyBean().setValue(EDGE_DELAY, null, delay);
		if (delay instanceof SDFExpressionEdgePropertyType) {
			((SDFExpressionEdgePropertyType) delay).setExpressionSolver(this
					.getBase());
		}
	}

	public void setTargetPortModifier(AbstractEdgePropertyType<?> modifier) {
		if (modifier != null) {
			getPropertyBean().setValue(TARGET_PORT_MODIFIER, null, modifier);
		} else {
			getPropertyBean().removeProperty(TARGET_PORT_MODIFIER);
		}
	}

	public void setSourcePortModifier(AbstractEdgePropertyType<?> modifier) {
		if (modifier != null) {
			getPropertyBean().setValue(SOURCE_PORT_MODIFIER, null, modifier);
		} else {
			getPropertyBean().removeProperty(SOURCE_PORT_MODIFIER);
		}
	}

	/**
	 * Setter of the property <tt>prod</tt>
	 * 
	 * @param prod
	 *            The prod to set.
	 * 
	 */
	public void setProd(AbstractEdgePropertyType<?> prod) {
		getPropertyBean().setValue(EDGE_PROD, null, prod);
		if (prod instanceof SDFExpressionEdgePropertyType) {
			((SDFExpressionEdgePropertyType) prod).setExpressionSolver(this
					.getBase());
		}
	}

	/**
	 * Setter of the property <tt>DATA_TYPE</tt>
	 * 
	 * @param type
	 *            The type to set.
	 * 
	 */
	public void setDataType(AbstractEdgePropertyType<?> type) {
		getPropertyBean().setValue(DATA_TYPE, null, type);
	}

	/**
	 * Set this edge source interface
	 * 
	 * @param source
	 *            The source interface to set for this edge
	 */
	public void setSourceInterface(SDFInterfaceVertex source) {
		getPropertyBean().setValue(SOURCE_PORT, null, source);
		if (source != null) {
			source.setDirection(InterfaceDirection.Output);
		}
	}

	/**
	 * Set this edge target interface
	 * 
	 * @param target
	 *            The target interface to set for this edge
	 */
	public void setTargetInterface(SDFInterfaceVertex target) {
		getPropertyBean().setValue(TARGET_PORT, null, target);
		if (target != null) {
			target.setDirection(InterfaceDirection.Input);
		}
	}

	/**
	 * Test if the given edge has the same properties than this edge
	 * 
	 * @param edge
	 *            The edge to compare with
	 * @return True if the given edge has the same properties, false otherwise
	 */
	public boolean compare(SDFEdge edge) {

		try {
			return super.compare(edge)
					&& edge.getSourceInterface().getName()
							.equals(this.getSourceInterface().getName())
					&& edge.getTargetInterface().getName()
							.equals(this.getTargetInterface().getName())
					&& (this.getCons().intValue() == edge.getCons().intValue())
					&& (this.getProd().intValue() == edge.getProd().intValue())
					&& (this.getDelay().intValue() == edge.getDelay()
							.intValue());
		} catch (InvalidExpressionException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String toString() {
		return getSource().toString() + " > "+ getTarget().toString() + " {d=" + getDelay() + ", p=" + getProd() + ", c=" + getCons()
				+ "}";
	}

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		if (propertyName.equals(EDGE_CONS) || propertyName.equals(EDGE_PROD)
				|| propertyName.equals(EDGE_DELAY)) {
			return SDFNumericalEdgePropertyTypeFactory.getInstance();
		} else if (propertyName.equals(DATA_TYPE)
				|| propertyName.equals(SOURCE_PORT_MODIFIER)
				|| propertyName.equals(TARGET_PORT_MODIFIER)) {
			return SDFTextualEdgePropertyTypeFactory.getInstance();
		}
		return null;
	}
}
