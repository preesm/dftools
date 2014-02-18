package net.sf.dftools.algorithm.model.parameters;

import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

/**
 * Represents an numerical expression
 * 
 * @author jpiat
 * 
 */
public class ExpressionValue implements Value{

	/**
	 * The parent graph to use to solve this expresion
	 */
	private IExpressionSolver solver;
	private Integer value;
	private String expression;

	/**
	 * Constructs a new Expression
	 * 
	 * @param expression
	 *            The string representation of the expression
	 */
	public ExpressionValue(String expression) {
		this.expression = expression;
		value = null;
	}

	/**
	 * Gives the integer value of this expression
	 * 
	 * @return The integer value of the expression
	 * @throws InvalidExpressionException When expression can't be solved
	 */
	public int intValue() throws InvalidExpressionException,  NoIntegerValueException{
		if (value != null) {
			return value;
		}
		if (solver != null) {
			value = solver.solveExpression(expression, this);
			return value;
		} else {
			Object result;
			try {
				JEP jep = new JEP();
				Node mainExpressionNode = jep.parse(expression);
				result = jep.evaluate(mainExpressionNode);
				if (result instanceof Double) {
					value = ((Double) result).intValue();
					return value;
				} else {
					throw(new InvalidExpressionException("Not a numéric expression"));
				}
			} catch (ParseException e) {
				throw(new InvalidExpressionException("Can't parse expresion :"+expression));
			}
		}
	}

	/**
	 * Set the solver to use for this expression
	 * 
	 * @param solver
	 *            The solver to use to compute int value
	 */
	public void setExpressionSolver(IExpressionSolver solver) {
		this.solver = solver;
		value = null ;
	}

	/**
	 * Gives the value of the variable
	 * 
	 * @return The value of the variable
	 */
	public String getValue() {
		return expression;
	}

	/**
	 * Sets the value of the variable
	 * 
	 * @param value
	 *            The value to set for the variable
	 */
	public void setValue(String value) {
		this.value = null ;
		expression = value;
	}
	

	public String toString() {
		return expression;
	}
}
