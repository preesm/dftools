package net.sf.dftools.algorithm.model.parameters;


/**
 * Class used to represent Argument
 * @author jpiat
 *
 */
public class Argument{


	private String name ;
	private Value value ;
	
	/**
	 * Builds a new argument with the given name
	 * @param name The name of the argument
	 */
	public Argument(String name){
		this.name = name ;
		value = new ExpressionValue("");
	}
	
	/**
	 * Builds a new argument with the given name and value
	 * @param name The name of the argument
	 * @param value The value of the argument
	 */
	public Argument(String name, String value){
		this.name = name ;
		try{
			int integerValue = Integer.decode(value);
			this.value = new ConstantValue(integerValue);
		}catch(NumberFormatException e){
			this.value = new ExpressionValue(value);
		}
	}
	
	/**
	 * Builds a new argument with the given name and value
	 * @param name The name of the argument
	 * @param value The value of the argument
	 */
	public Argument(String name, Value value){
		this.name = name ;
		this.value = value ;
	}
	
	/**
	 * Gives the name of the argument
	 * @return The name of the argument
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sets the name of the argument
	 * @param name The name to set for the argument
	 */
	public void setName(String name){
		this.name = name ;
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
	
	public Value getObjectValue(){
		return value;
	}
	
	public void setValue(String value) {
		try{
			int integerValue = Integer.decode(value);
			this.value = new ConstantValue(integerValue);
		}catch(NumberFormatException e){
			this.value = new ExpressionValue(value);
		}
	}

}
