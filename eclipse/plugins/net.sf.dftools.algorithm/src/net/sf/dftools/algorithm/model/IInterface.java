package net.sf.dftools.algorithm.model;


/**
 * An abstract class representing interfaces to the outside world
 * @author jpiat
 *
 */
public interface IInterface {



	/**
	 * Gives this interface direction
	 * 
	 * @return The direction of this interface
	 */
	public InterfaceDirection getDirection();

	/**
	 * Set this interface direction
	 * 
	 * @param direction
	 */
	public void setDirection(String direction);

	/**
	 * Set this interface direction
	 * 
	 * @param direction
	 */
	public void setDirection(InterfaceDirection direction);
	
	public String getName();
	
	public void setName(String name);


}
