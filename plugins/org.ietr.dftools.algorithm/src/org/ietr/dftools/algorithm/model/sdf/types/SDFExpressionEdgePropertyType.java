package org.ietr.dftools.algorithm.model.sdf.types;

import org.ietr.dftools.algorithm.model.AbstractEdgePropertyType;
import org.ietr.dftools.algorithm.model.parameters.IExpressionSolver;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.parameters.NoIntegerValueException;
import org.ietr.dftools.algorithm.model.parameters.Value;

/**
 * Class used to represent the integer edge property type in a SDF
 * 
 * @author jpiat
 * 
 */
public class SDFExpressionEdgePropertyType extends
		AbstractEdgePropertyType<Value> {

	private Integer computedValue;

	/**
	 * Creates a new SDFDefaultEdgePropertyType with the given graph value
	 * 
	 * @param val
	 *            The Integer value of this SDFDefaultEdgePropertyType
	 */
	public SDFExpressionEdgePropertyType(Value val) {
		super(val);
		computedValue = null;
	}

	@Override
	public AbstractEdgePropertyType<Value> clone() {
		SDFExpressionEdgePropertyType clone = new SDFExpressionEdgePropertyType(
				value);
		try {
			clone.computedValue = intValue();
		} catch (InvalidExpressionException e) {
			clone.computedValue = null;
		}
		return clone;
	}

	@Override
	public void setValue(Value val) {
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
				e.printStackTrace();
				return 0 ;
			}
		}
		return computedValue;

	}

}
