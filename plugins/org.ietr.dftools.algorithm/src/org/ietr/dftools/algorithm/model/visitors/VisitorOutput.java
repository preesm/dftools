package org.ietr.dftools.algorithm.model.visitors;

import java.util.logging.Logger;

/**
 * Class for visitors that outputs logs
 * @author jpiat
 *
 */
public class VisitorOutput {

	protected Logger log ;
	protected static VisitorOutput instance = null;
	
	protected VisitorOutput(){
		log = Logger.getLogger(VisitorOutput.class.toString());
	}

	/**
	 * Gives an output in which to output messages
	 * @return The unique instance of VisitorOutput
	 */
	protected static VisitorOutput getOutput(){
		if(instance == null){
			instance = new VisitorOutput();
		}
		return instance ;
	}
	
	/**
	 * Set the logger in which to output messages 
	 * @param log The logger in which to output messages
	 */ 
	public static void setLogger(Logger log){
		getOutput().log = log ;
	}
	
	/**
	 * Gives the logger 
	 * @return The logger
	 */
	public static Logger getLogger(){
		return getOutput().log ;
	}
}
