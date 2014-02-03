package org.ietr.dftools.algorithm.model;

import java.util.List;

/**
 * Interface for object using a property bean to store properties
 * 
 * @author jpiat
 * 
 */
public interface PropertySource {

	/**
	 * Gives the object's property bean
	 * 
	 * @return The objects property bean
	 */
	public PropertyBean getPropertyBean();

	/**
	 * Copy the properties of the given PropertySource to this PropertySource
	 * 
	 * @param props
	 *            The properties to be copied
	 */
	public void copyProperties(PropertySource props);

	
	public List<String> getPublicProperties();

	public PropertyFactory getFactoryForProperty(String propertyName);

	public String getPropertyStringValue(String propertyName);

	public void setPropertyValue(String propertyName, Object value);
}
