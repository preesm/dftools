package org.ietr.dftools.algorithm.exceptions;

/**
 * Exception generated when adding a dependency in a DAG graph creates a Cycle
 * 
 * @author jpiat
 * @author kdesnos
 * 
 */
public class CreateCycleException extends Exception {
	
	public CreateCycleException()
	{
		super();
	}

	public CreateCycleException(String message) {
		super(message);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -6735424529007211737L;

}
