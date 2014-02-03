package org.ietr.dftools.algorithm.model.psdf.parameters;

import org.ietr.dftools.algorithm.model.parameters.IExpressionSolver;

import jscl.math.Expression;
import jscl.text.ParseException;

public class DynamicExpression implements DynamicValue {

	private String value;
	@SuppressWarnings("unused")
	private IExpressionSolver solver;

	public DynamicExpression(String value, IExpressionSolver solver) {
		this.value = value;
		this.solver = solver;
	}

	@Override
	public String getName() {
		return value;
	}

	@Override
	public Expression getExpression() {
		try {
			if (value != null) {
				return Expression.valueOf(value.toString());
			} else {
				return Expression.valueOf(getName());
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
