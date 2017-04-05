package org.ietr.dftools.algorithm.model;

/**
 * A class to implements when th eproperty can be cloned
 * @author jpiat
 *
 */
public interface CloneableProperty extends Cloneable{

	/**
	 * The clone method to implements
	 * @return The cloned object
	 */
	public Object clone(); 
}
