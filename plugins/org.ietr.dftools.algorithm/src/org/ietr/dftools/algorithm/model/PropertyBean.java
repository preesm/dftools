/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Jonathan Piat <jpiat@laas.fr> (2011)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2013)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
package org.ietr.dftools.algorithm.model;

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
