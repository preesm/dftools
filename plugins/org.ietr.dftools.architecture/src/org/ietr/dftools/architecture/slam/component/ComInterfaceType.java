/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
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
	private static final ComInterfaceType[] VALUES_ARRAY = new ComInterfaceType[] { UNSPECIFIED, MASTER, SLAVE, };

	/**
	 * A public read-only list of all the '<em><b>Com Interface Type</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<ComInterfaceType> VALUES = Collections.unmodifiableList(Arrays.asList(ComInterfaceType.VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Com Interface Type</b></em>' literal with the
	 * specified literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static ComInterfaceType get(final String literal) {
		for (final ComInterfaceType result : ComInterfaceType.VALUES_ARRAY) {
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
	public static ComInterfaceType getByName(final String name) {
		for (final ComInterfaceType result : ComInterfaceType.VALUES_ARRAY) {
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
	public static ComInterfaceType get(final int value) {
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
	private ComInterfaceType(final int value, final String name, final String literal) {
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
		return this.value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return this.literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		return this.literal;
	}

} // ComInterfaceType
