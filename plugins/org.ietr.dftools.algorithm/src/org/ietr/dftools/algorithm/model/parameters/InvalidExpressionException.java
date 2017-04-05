package org.ietr.dftools.algorithm.model.parameters;

/**
 * Class representing exception while solving expressions
 * @author jpiat
 *
 */
public class InvalidExpressionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8877519082638596867L;

	/**
	 * Builds a new InvalidExpressionException
	 * @param expression The message to pass to the expression
	 */
	public InvalidExpressionException(String expression) {
		super(expression);
	}

}
