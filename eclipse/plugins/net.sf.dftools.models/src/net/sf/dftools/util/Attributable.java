/*
 * Copyright (c) 2012, Synflow
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.dftools.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an attributable object.
 * 
 * @author Matthieu Wipliez
 * @model abstract="true"
 */
public interface Attributable extends EObject {

	Attribute getAttribute(String name);

	/**
	 * Returns the attributes of this vertex.
	 * 
	 * @return the attributes of this vertex
	 * 
	 * @model containment="true"
	 */
	EList<Attribute> getAttributes();

	/**
	 * Sets the value of the attribute with the given name to the given value.
	 * If no attribute exists with the given name, a new attribute is created
	 * and inserted at the beginning of the attribute list. This makes it easier
	 * to reference attributes by their index (the last attribute that was added
	 * will be at index 0, not index getAttributes().size() - 1).
	 * 
	 * @param name
	 *            name of the attribute
	 * @param value
	 *            a POJO
	 */
	void setAttribute(String name, Object value);

	/**
	 * Sets the value of the attribute with the given name to the given value.
	 * If no attribute exists with the given name, a new attribute is created
	 * and inserted at the beginning of the attribute list. This makes it easier
	 * to reference attributes by their index (the last attribute that was added
	 * will be at index 0, not index getAttributes().size() - 1).
	 * 
	 * @param name
	 *            name of the attribute
	 * @param value
	 *            an EMF object
	 */
	void setAttribute(String name, EObject value);

}
