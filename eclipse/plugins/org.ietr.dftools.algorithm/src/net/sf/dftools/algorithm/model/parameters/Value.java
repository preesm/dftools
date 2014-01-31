package net.sf.dftools.algorithm.model.parameters;



public interface Value {
	/**
	 * Gives the integer value of this expression
	 * 
	 * @return The integer value of the expression
	 * @throws InvalidExpressionException When expression can't be solved
	 */
	public int intValue() throws InvalidExpressionException, NoIntegerValueException;

	/**
	 * Set the solver to use for this expression
	 * 
	 * @param solver
	 *            The solver to use to compute int value
	 */
	public void setExpressionSolver(IExpressionSolver solver);
	
	public String getValue();
	public void setValue(String value);

}
