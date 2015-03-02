/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ietr.dftools.architecture.slam.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Com Interface Type</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * 
 * @see org.ietr.dftools.architecture.slam.component.ComponentPackage#getComInterfaceType()
 * @model
 * @generated
 */
public enum ComInterfaceType implements Enumerator {
	/**
	 * The '<em><b>UNSPECIFIED</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #UNSPECIFIED_VALUE
	 * @generated
	 * @ordered
	 */
	UNSPECIFIED(0, "UNSPECIFIED", "UNSPECIFIED"),

	/**
	 * The '<em><b>MASTER</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #MASTER_VALUE
	 * @generated
	 * @ordered
	 */
	MASTER(1, "MASTER", "MASTER"),

	/**
	 * The '<em><b>SLAVE</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #SLAVE_VALUE
	 * @generated
	 * @ordered
	 */
	SLAVE(2, "SLAVE", "SLAVE");

	/**
	 * The '<em><b>UNSPECIFIED</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UNSPECIFIED</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #UNSPECIFIED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNSPECIFIED_VALUE = 0;

	/**
	 * The '<em><b>MASTER</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MASTER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #MASTER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MASTER_VALUE = 1;

	/**
	 * The '<em><b>SLAVE</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SLAVE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SLAVE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SLAVE_VALUE = 2;

	/**
	 * An array of all the '<em><b>Com Interface Type</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final ComInterfaceType[] VALUES_ARRAY = new ComInterfaceType[] {
			UNSPECIFIED, MASTER, SLAVE, };

	/**
	 * A public read-only list of all the '<em><b>Com Interface Type</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<ComInterfaceType> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Com Interface Type</b></em>' literal with the
	 * specified literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static ComInterfaceType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ComInterfaceType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Com Interface Type</b></em>' literal with the
	 * specified name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static ComInterfaceType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ComInterfaceType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Com Interface Type</b></em>' literal with the
	 * specified integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static ComInterfaceType get(int value) {
		switch (value) {
		case UNSPECIFIED_VALUE:
			return UNSPECIFIED;
		case MASTER_VALUE:
			return MASTER;
		case SLAVE_VALUE:
			return SLAVE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private ComInterfaceType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // ComInterfaceType
