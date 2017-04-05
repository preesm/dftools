package org.ietr.dftools.algorithm.model.parameters;

import java.util.HashMap;

/**
 * Class used to represent a set of argument
 * @author jpiat
 *
 */
public class ArgumentSet extends HashMap<String, Argument>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8364762425793222529L;
	
	/**
	 * Add a argument to the set
	 * @param a The argument to add
	 */
	public void addArgument(Argument a){
		this.put(a.getName(),a);
	}
	
	/**
	 * Gives the argument with the given name
	 * @param n The name of the argument to return
	 * @return The argument with the given name
	 */
	public Argument getArgument(String n){
		return this.get(n);
	}
	
	/**
	 * Set the expression solver to use for this variable set
	 * @param solver
	 */
	public void setExpressionSolver(IExpressionSolver solver){
		for(Argument arg : this.values()){
			arg.setExpressionSolver(solver);
		}
	}
}
