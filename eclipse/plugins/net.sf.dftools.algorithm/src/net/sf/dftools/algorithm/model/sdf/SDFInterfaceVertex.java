package net.sf.dftools.algorithm.model.sdf;

import net.sf.dftools.algorithm.model.IInterface;
import net.sf.dftools.algorithm.model.InterfaceDirection;

/**
 * Class used to represent the interfaces of a Hierarchical vertex
 * 
 * @author jpiat
 * 
 */
public abstract class SDFInterfaceVertex extends SDFAbstractVertex implements
		IInterface {

	/**
	 * Name of the property containing the direction
	 */
	public static final String PORT_DIRECTION = "port_direction";
	/**
	 * String representation of the type of data carried by this port
	 */
	public final static String DATA_TYPE = "data_type";

	/**
	 * Kind of node
	 */
	public static final String PORT = "port";

	static {
		{
			public_properties.add(PORT_DIRECTION);
			public_properties.add(DATA_TYPE);
		}
	};

	/**
	 * Creates a new SDFInterfaceVertex with the default direction (SINK)
	 */
	public SDFInterfaceVertex() {
		super();
		setKind(PORT);
		setDirection(InterfaceDirection.Output);
	}

	/**
	 * Construct a new SDFInterfaceVertex using the String dir to specifiy the
	 * direction Input=Source, Output=Sink
	 * 
	 * @param dir
	 */
	public SDFInterfaceVertex(String dir) {
		super();
		setKind(PORT);
		setDirection(dir);
	}

	public abstract SDFInterfaceVertex clone();

	public boolean equals(Object e) {
		if (e instanceof SDFInterfaceVertex) {
			return (((SDFInterfaceVertex) e).getName().equals(this.getName()) && ((SDFInterfaceVertex) e)
					.getDirection().equals(this.getDirection()));
		} else {
			return false;
		}
	}

	/**
	 * Gives this interface direction
	 * 
	 * @return The direction of this interface
	 */
	public InterfaceDirection getDirection() {
		return (InterfaceDirection) getPropertyBean().getValue(PORT_DIRECTION,
				InterfaceDirection.class);
	}

	/**
	 * Set this interface direction
	 * 
	 * @param direction
	 */
	public void setDirection(String direction) {
		getPropertyBean().setValue(PORT_DIRECTION,
				InterfaceDirection.fromString(direction));
	}

	/**
	 * Set this interface direction
	 * 
	 * @param direction
	 */
	public void setDirection(InterfaceDirection direction) {
		getPropertyBean().setValue(PORT_DIRECTION, direction);
	}

	/**
	 * Sets the type of data on this interface
	 * 
	 * @param type
	 */
	public void setDataType(String type) {
		getPropertyBean().setValue(DATA_TYPE, type);
	}

	public Object getNbRepeat() {
		return 1;
	}

	/**
	 * Gives the type of data on this interface
	 * 
	 * @return The string representation of the type of data on this interface
	 */
	public String getDataType() {
		return (String) getPropertyBean().getValue(DATA_TYPE, String.class);
	}

	public void setPropertyValue(String propertyName, Object value) {
		if(propertyName.equals(PORT_DIRECTION) && value instanceof String){
			super.setPropertyValue(propertyName, InterfaceDirection.fromString((String) value));
		}else{
			super.setPropertyValue(propertyName, value);
		}
		
	}
}
