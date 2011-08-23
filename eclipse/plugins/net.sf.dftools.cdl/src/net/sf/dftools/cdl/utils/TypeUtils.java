/*
 * Copyright (c) 2011, EPFL
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
 *   * Neither the name of the ECOLE POLYTECHNIQUE FEDERALE DE LAUSANNE nor the  
 *     names of its contributors may be used to endorse or promote products 
 *     derived from this software without specific prior written permission.
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
package net.sf.dftools.cdl.utils;

import net.sf.dftools.cdl.cdl.AstAttribute;
import net.sf.dftools.cdl.cdl.AstBasicType;
import net.sf.dftools.cdl.cdl.AstClass;
import net.sf.dftools.cdl.cdl.AstDeclType;
import net.sf.dftools.cdl.cdl.AstEnum;
import net.sf.dftools.cdl.cdl.AstType;
import net.sf.dftools.cdl.cdl.CdlFactory;

/**
 * Utility functions for types
 * 
 * @author Thavot Richard
 *
 * 
 */
public class TypeUtils {

	/**
	 * Sets the type of a typed element as a class reference
	 * 
	 * @param typedElement
	 * @param cl
	 */
	public static void setTypeClassReference(AstAttribute attribute, AstClass cl) {
		AstDeclType type = createDeclType(cl);
		attribute.setType(type);
	}

	public static AstDeclType createDeclType(AstClass cl) {
		AstDeclType type = CdlFactory.eINSTANCE.createAstDeclType();
		type.setTypeRef(cl);
		return type;
	}

	public static AstDeclType createDeclType(AstEnum en) {
		AstDeclType type = CdlFactory.eINSTANCE.createAstDeclType();
		type.setTypeRef(en);
		return type;
	}

	public static AstBasicType createBasicType(String name) {
		AstBasicType type = CdlFactory.eINSTANCE.createAstBasicType();
		type.setName(name);
		return type;
	}

	/**
	 * @param type
	 * @return the string representation of the passed type (both for basic and
	 *         class types)
	 */
	public static String typeToString(AstType type) {
		if (type instanceof AstDeclType) {
			return typeToString((AstDeclType) type);
		}
		if (type instanceof AstBasicType) {
			return typeToString((AstBasicType) type);
		}
		return "Unknown type: " + type;
	}

	public static String typeToString(AstDeclType type) {
		return (type.getTypeRef() != null ? type.getTypeRef().getName() : "");
	}

	public static String typeToString(AstBasicType type) {
		return type.getName();
	}

}
