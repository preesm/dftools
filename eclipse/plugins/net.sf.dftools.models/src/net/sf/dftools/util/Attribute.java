/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.util;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Attribute</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.util.Attribute#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.dftools.util.Attribute#getValue <em>Value</em>}</li>
 *   <li>{@link net.sf.dftools.util.Attribute#getRuntimeValue <em>Runtime Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.util.UtilPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends EObject {

	/**
	 * Returns the name of this attribute.
	 * 
	 * @return the name of this attribute
	 * @model
	 */
	String getName();

	/**
	 * Returns the runtime value associated with this attribute. This field is
	 * reserved for runtime-only values, as it is not serialized.
	 * 
	 * @model transient="true"
	 */
	Object getRuntimeValue();

	/**
	 * Returns the value contained in this attribute. To use a runtime value,
	 * use the {@link #getRuntimeValue()} method.
	 * 
	 * @model containment="true"
	 */
	EObject getValue();

	/**
	 * Sets the name of this attribute.
	 * 
	 * @param name
	 *            the new name of this attribute
	 */
	void setName(String name);

	/**
	 * Sets the new runtime value associated with this attribute.
	 * 
	 * @param value
	 *            the new runtime value
	 */
	void setRuntimeValue(Object value);

	/**
	 * Sets the value contained in this attribute.
	 * 
	 * @param value
	 *            the new value of this attribute.
	 */
	void setValue(EObject value);

}
