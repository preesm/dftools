package org.ietr.dftools.algorithm.model.parameters;

import java.util.HashMap;

/**
 * Class o represent sets of variable
 * @author jpiat
 *
 */
public class VariableSet extends HashMap<String, Variable>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8364762425793222529L;

	/**
	 * Add a variable to the set
	 * @param v The variable to add
	 */
	public void addVariable(Variable v){
		this.put(v.getName(),v);
	}
	
	/**
	 * Gives the variable with the given name
	 * @param n The name of the variable to return
	 * @return The variable with the given name
	 */
	public Variable getVariable(String n){
		return this.get(n);
	}
	
	/**
	 * Set the expression solver to use for this variable set
	 * @param solver
	 */
	public void setExpressionSolver(IExpressionSolver solver){
		for(Variable var : this.values()){
			var.setExpressionSolver(solver);
		}
	}
}
