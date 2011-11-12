package net.sf.dftools.algorithm.model.sdf.types;

import net.sf.dftools.algorithm.model.AbstractEdgePropertyType;
import net.sf.dftools.algorithm.model.parameters.ExpressionValue;
import net.sf.dftools.algorithm.model.parameters.IExpressionSolver;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.parameters.NoIntegerValueException;

/**
 * Class used to represent the integer edge property type in a SDF
 * 
 * @author jpiat
 * 
 */
public class SDFExpressionEdgePropertyType extends
		AbstractEdgePropertyType<ExpressionValue> {

	private Integer computedValue;

	/**
	 * Creates a new SDFDefaultEdgePropertyType with the given graph value
	 * 
	 * @param val
	 *            The Integer value of this SDFDefaultEdgePropertyType
	 */
	public SDFExpressionEdgePropertyType(ExpressionValue val) {
		super(val);
		computedValue = null;
	}

	@Override
	public AbstractEdgePropertyType<ExpressionValue> clone() {
		SDFExpressionEdgePropertyType clone = new SDFExpressionEdgePropertyType(
				value);
		try {
			clone.computedValue = intValue();
		} catch (InvalidExpressionException e) {
			clone.computedValue = null;
		}
		return clone;
	}

	public void setValue(ExpressionValue val) {
		super.setValue(val);
		computedValue = null;
	}

	/**
	 * Sets the expression solver to use to compute intValue
	 * 
	 * @param solver
	 *            The solver to be used
	 */
	public void setExpressionSolver(IExpressionSolver solver) {
		this.getValue().setExpressionSolver(solver);
		computedValue = null;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public int intValue() throws InvalidExpressionException {
		if (computedValue == null) {
			try {
				computedValue = value.intValue();
				return computedValue;
			} catch (NoIntegerValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0 ;
			}
		}
		return computedValue;

	}

}
