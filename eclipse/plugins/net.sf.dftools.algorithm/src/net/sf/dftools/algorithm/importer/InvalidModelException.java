package net.sf.dftools.algorithm.importer;

/**
 * Exception to return when the file to import is not from the desired type
 * 
 * @author jpiat
 * 
 */
public class InvalidModelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8589958989242269799L;

	public InvalidModelException(String msg) {
		super(msg);
	}

	public InvalidModelException() {
		super();
	}
}
