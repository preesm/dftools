package net.sf.dftools.algorithm.model;

/**
 * Interface that defines the method to update an observer object
 * @author jpiat
 *
 */
public interface IModelObserver {
	
	/**
	 * The update method from the MVC design pattern applied to graph model
	 * @param observable The model observed
	 * @param arg arguments to be passed
	 */
	public void update(AbstractGraph<?,?> observable, Object arg);

}
