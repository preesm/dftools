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
 *   <li>{@link net.sf.dftools.util.Attribute#getContainedValue <em>Contained Value</em>}</li>
 *   <li>{@link net.sf.dftools.util.Attribute#getPojoValue <em>Pojo Value</em>}</li>
 *   <li>{@link net.sf.dftools.util.Attribute#getReferencedValue <em>Referenced Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.util.UtilPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends EObject {

	/**
	 * Returns the value contained in this attribute. A value may be associated
	 * with this attribute but not necessarily contained in it, see
	 * {@link #getReferencedValue()} for that.
	 * 
	 * @return the value contained in this attribute
	 * @model containment="true"
	 */
	EObject getContainedValue();

	/**
	 * Returns the name of this attribute.
	 * 
	 * @return the name of this attribute
	 * @model
	 */
	String getName();

	/**
	 * Returns the POJO value contained in this attribute. Note: if this
	 * attribute is serialized, this value will be serialized as well.
	 * 
	 * @model
	 */
	Object getPojoValue();

	/**
	 * Returns the value referenced by this attribute. This value may or may not
	 * return the same value as {@link #getContainedValue()}.
	 * 
	 * @return the value referenced by this attribute
	 * @model
	 */
	EObject getReferencedValue();

	/**
	 * Returns the value contained in this attribute, looking in the following
	 * order: 1) POJO, 2) referenced 3) contained.
	 * 
	 * @return the value
	 */
	Object getValue();

	/**
	 * Sets the value contained in this attribute.
	 * 
	 * @param value
	 *            the new value contained in this attribute
	 */
	void setContainedValue(EObject value);

	/**
	 * Sets the name of this attribute.
	 * 
	 * @param name
	 *            the new name of this attribute
	 */
	void setName(String name);

	/**
	 * Sets the new value of this attribute. If the given value has no
	 * container, this attribute becomes its new container.
	 * 
	 * @param value
	 *            the new EObject value of this attribute
	 */
	void setValue(EObject value);

	/**
	 * Sets the new POJO value contained in this attribute.
	 * 
	 * @param value
	 *            the new POJO value contained in this attribute
	 */
	void setPojoValue(Object value);

	/**
	 * Sets the value referenced by this attribute.
	 * 
	 * @param value
	 *            the new value referenced by this attribute
	 */
	void setReferencedValue(EObject value);

}
