package org.ietr.dftools.algorithm.model.parameters;

/**
 * Class representing parameters that can be used to configure a graph ...
 * @author jpiat
 *
 */
public class Parameter{

	
	private String name ;
	private Integer value = null ;	
	/**
	 * Builds a parameter with the given name
	 * @param name The name of the parameter
	 */
	public Parameter(String name){
		this.name = name ;
	}
	
	
	/**
	 * Gives this parameter name
	 * @return The name of this parameter
	 */
	public String getName(){
		return name ;
	}
	
	/**
	 * Set this parameter name
	 * @param name The name to set for this parameter
	 */
	public void setName(String name){
		this.name = name ;
	}
	
	
	/**
	 * @return The value of the parameter
	 */
	public Integer getValue() throws NoIntegerValueException{
		return value ;
	}
	
	/**
	 * @param value The value of this parameter
	 */
	public void setValue(Integer value){
		this.value = value ;
	}
	
	@Override
	public Parameter clone(){
		Parameter par = new Parameter(this.name);
		par.setValue(this.value);
		return par ;
	}
	
	
}
