package org.ietr.dftools.algorithm.model.visitors;
/**
 * Exception to be thrown when SDF4J encounters an error
 * @author jpiat
 *
*/
public class SDF4JException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1803030544045591261L;

	
	/**
	 * Creates a new SDF4JException  with the given error message
	 * @param message The error message
	 */
	public SDF4JException(String message){
		super(message);
	}
}
