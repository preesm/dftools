package net.sf.dftools.algorithm.model.psdf.parameters;

import jscl.math.Expression;
import jscl.text.ParseException;
import net.sf.dftools.algorithm.model.parameters.IExpressionSolver;

public class DynamicExpression implements DynamicValue {

	private String value;
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
