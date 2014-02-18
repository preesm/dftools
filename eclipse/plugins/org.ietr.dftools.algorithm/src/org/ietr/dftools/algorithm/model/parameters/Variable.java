package org.ietr.dftools.algorithm.model.parameters;


/**
 * Class used to represent variable
 * 
 * @author jpiat
 * 
 */
public class Variable{

	private String name ;
	private Value value ;
	/**
	 * Builds a new variable with the given name
	 * 
	 * @param name
	 *            The name of the variable
	 */
	public Variable(String name) {
		this.name = name;
		value = new ExpressionValue("");
	}
	
	/**
	 * Builds a new variable with the given name and value
	 * 
	 * @param name
	 *            The name of the variable
	 * @param value
	 *            The value of the variable
	 */
	public Variable(String name, String value) {
		this.name = name;
		try{
			int integerValue = Integer.decode(value);
			this.value = new ConstantValue(integerValue);
		}catch(NumberFormatException e){
			this.value = new ExpressionValue(value);
		}
	}

	/**
	 * Gives the name of the variable
	 * 
	 * @return The name of the variable
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name of the variable
	 * 
	 * @param name
	 *            The name to set for the variable
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Set the solver to use for this expression
	 * 
	 * @param solver
	 *            The solver to use to compute int value
	 */
	public void setExpressionSolver(IExpressionSolver solver) {
		value.setExpressionSolver(solver);
	}
	
	public int intValue() throws InvalidExpressionException, NoIntegerValueException{
		int val = value.intValue();
		this.value = new ConstantValue(val);
		return val;
	}
	
	public String getValue(){
		return value.getValue();
	}
	
	public void setValue(String value) {
		try{
			int integerValue = Integer.decode(value);
			this.value = new ConstantValue(integerValue);
		}catch(NumberFormatException e){
			this.value = new ExpressionValue(value);
		}
	}
	
	public Variable clone(){
		Variable var = new Variable(this.name);
		var.setValue(this.value.getValue());
		return var ;
	}

}
