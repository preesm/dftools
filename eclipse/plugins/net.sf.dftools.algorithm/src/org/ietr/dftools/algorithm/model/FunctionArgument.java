package org.ietr.dftools.algorithm.model;

/**
 * @author jpiat
 *
 */
public class FunctionArgument {

	private String name ;
	@SuppressWarnings("unused")
	private String type ;
	@SuppressWarnings("unused")
	private int size ;
	
	/**
	 * Builds a new FunctionArgument with the given name, type, size
	 * @param name The name of the function argument
	 * @param type The type of the function argument
	 * @param size The size of the argument
	 */
	public FunctionArgument(String name, String type, int size){
		this.name = name ;
		this.type = type ;
		this.size = size ;
	}
	
	/**
	 * Gives this function argument name
	 * @return The name of the Function argument
	 */
	public String getName(){
		return name ;
	}
	
}
