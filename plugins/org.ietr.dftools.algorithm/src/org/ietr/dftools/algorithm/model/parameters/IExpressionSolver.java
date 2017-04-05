package org.ietr.dftools.algorithm.model.parameters;



/**
 * Interface for expression solver
 * @author jpiat
 *
 */
public interface IExpressionSolver {
	
	/**
	 * Solves the given expression
	 * @param expression The expression to solve
	 * @param caller The Expression calling the solver
	 * @return The integer value of the solved expression
	 * @throws InvalidExpressionException 
	 * @throws NoIntegerValueException 
	 */
	public int solveExpression(String expression, Value caller) throws InvalidExpressionException, NoIntegerValueException;

}
