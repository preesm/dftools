package org.ietr.dftools.algorithm.model.parameters;

import java.util.HashMap;

/**
 * Class used to represent a set of parameters
 * @author jpiat
 *
 */
public class ParameterSet extends HashMap<String, Parameter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9089649660961260196L;

	/**
	 * Add a parameter to the set
	 * @param p The parameter to add
	 */
	public void addParameter(Parameter p){
		this.put(p.getName(), p);
	}
	
	/**
	 * Gives the parameter with the given name
	 * @param n The name of the parameter to return
	 * @return The parameter with the given name
	 */
	public Parameter getParameter(String n){
		return this.get(n);
	}
	
}
