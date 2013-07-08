package net.sf.dftools.algorithm.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

/**
 * Defines properties for <code>Graph</code>s and <code>Port</code>s.
 * 
 * @author Matthieu Wipliez
 * @author kdesnos
 */
public class PropertyBean extends Observable implements Cloneable, Serializable {

	static final long serialVersionUID = 1;

	private Map<String, Object> properties;

	private PropertyChangeSupport propertyChange;

	/**
	 * Constructs a new property bean, with no initial properties set.
	 */
	public PropertyBean() {
		propertyChange = new PropertyChangeSupport(this);
		properties = new HashMap<String, Object>();
	}

	/**
	 * Add the listener <code>listener</code> to the registered listeners.
	 * 
	 * @param listener
	 *            The PropertyChangeListener to add.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.addPropertyChangeListener(listener);
	}

	/**
	 * Returns the value of the property whose name is <code>propertyName</code>
	 * .
	 * 
	 * @param propertyName
	 *            The name of the property to retrieve.
	 * @return The value of the property.
	 */
	public Object getValue(String propertyName) {
		return properties.get(propertyName);
	}

	/**
	 * Gives the value of the property whose name is <code>propertyName</code>
	 * if the value is an instance of the specified class
	 * <code>propertyClass</code>
	 * 
	 * @param propertyName
	 *            The property name
	 * @param propertyClass
	 *            The Class of the property
	 * @return The value of the given propertyName if the value belongs to the
	 *         given propertyClass,
	 */
	public Object getValue(String propertyName, Class<?> propertyClass) {
		if (propertyClass.isInstance(properties.get(propertyName))) {
			return properties.get(propertyName);
		}
		return null;
	}

	/**
	 * Gives all the keys used to store properties
	 * 
	 * @return A set of String representing the keys
	 */
	public Set<String> keys() {
		return properties.keySet();
	}

	/**
	 * Remove the listener listener from the registered listeners.
	 * 
	 * @param listener
	 *            The listener to remove.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChange.removePropertyChangeListener(listener);
	}

	/**
	 * Remove the property whose name is <code>propertyName</code> from the
	 * {@link PropertyBean}. Any value associated to this property will be lost.
	 * 
	 * @param propertyName
	 *            the name of the property to remove
	 */
	public void removeProperty(String propertyName) {
		Object o = properties.get(propertyName);
		properties.remove(propertyName);
		propertyChange.firePropertyChange(propertyName, o, null);
	}

	/**
	 * Sets the value of the property whose name is <code>propertyName</code> to
	 * value <code>newValue</code>, and report the property update to any
	 * registered listeners.
	 * 
	 * @param propertyName
	 *            The name of the property to set.
	 * @param newValue
	 *            The new value of the property.
	 */
	public void setValue(String propertyName, Object newValue) {
		Object oldValue = properties.get(propertyName);
		properties.put(propertyName, newValue);
		propertyChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * Sets the value of the property whose name is <code>propertyName</code> to
	 * value <code>newValue</code>, and report the property update to any
	 * registered listeners. This method allows the caller to specify the the
	 * property's <code>oldValue</code>, thus overriding the value stored in the
	 * properties map. This may be of use when a property should be fired
	 * regardless of the previous value (in case of undo/redo for example, when
	 * a same object is added, removed, and added again).
	 * 
	 * @param propertyName
	 *            The name of the property to set.
	 * @param oldValue
	 *            The old value of the property.
	 * @param newValue
	 *            The new value of the property.
	 */
	public void setValue(String propertyName, Object oldValue, Object newValue) {
		properties.put(propertyName, newValue);
		propertyChange.firePropertyChange(propertyName, oldValue, newValue);
	}
}
