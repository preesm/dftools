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
package net.sf.dftools.cdl.rules;

import static net.sf.dftools.cdl.cdl.CdlPackage.eINSTANCE;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import net.sf.dftools.cdl.cdl.AstCore;
import net.sf.dftools.cdl.cdl.AstDomain;
import net.sf.dftools.cdl.cdl.AstExpressionBinary;
import net.sf.dftools.cdl.cdl.AstField;
import net.sf.dftools.cdl.cdl.AstModule;
import net.sf.dftools.cdl.errors.CdlError;
import net.sf.dftools.cdl.typing.CdlTypesystem;
import net.sf.dftools.cdl.typing.TypeResult;
import net.sf.dftools.cdl.utils.TypeUtils;

/**
 * 
 * @author Thavot Richard
 *
 */
public class CdlRules {

	private static CdlTypesystem typeSystem = new CdlTypesystem();

	public static CdlError checkFieldDuplication(AstField field) {
		AstCore core = (AstCore) field.eContainer();
		EList<AstField> fields = core.getFields();
		for (AstField otherField : fields) {
			boolean duplicateField = false;
			AstDomain current = field.getDomain();
			AstDomain other = otherField.getDomain();
			if (current != other) {
				do {
					String currentName = current.getLeft().getName();
					String otherName = other.getLeft().getName();
					duplicateField = currentName.equals(otherName);
					current = current.getRight();
					other = other.getRight();
				} while (duplicateField && (current != null) && (other != null));
				if (duplicateField) {
					current = field.getDomain();
					String name = "";
					do {
						name += (name.isEmpty() ? "" : ".")
								+ current.getLeft().getName();
						current = current.getRight();
					} while (current != null);
					return new CdlError("duplicate field: " + name,
							eINSTANCE.getAstField_Domain(),
							CdlError.ERROR_DEFAULT, "");
				}
			}
		}
		return null;
	}

	public static CdlError checkCompatibleType(AstField field) {
		TypeResult domainType = typeSystem.getType(field.getDomain());
		TypeResult valueType = typeSystem.getType(field.getValue());
		if (CdlTypesystemRules.compatibleTypeOrdered(domainType, valueType)
				.isEmpty()) {
			return new CdlError("Type mismatch: cannot convert from "
					+ valueType.toString() + " to " + domainType.toString(),
					eINSTANCE.getAstField_Value(), CdlError.ERROR_TYPE,
					TypeUtils.typeToString(domainType.getType()));
		}
		return null;
	}

	public static CdlError checkCompatibleType(AstExpressionBinary e) {
		TypeResult left = typeSystem.getType(e.getLeft());
		TypeResult right = typeSystem.getType(e.getRight());
		if (CdlTypesystemRules.compatibleTypeUnordered(left, right).isEmpty()) {
			return new CdlError("Type mismatch: cannot convert from "
					+ right.toString() + " to " + left.toString(),
					eINSTANCE.getAstExpressionBinary_Right(),
					CdlError.ERROR_TYPE, left.toString());
		}
		return null;
	}

	public static CdlError checkModuleName(AstModule m) {
		String path = m.eResource().getURI().path();
		String expectedName = new Path(path).removeFileExtension()
				.lastSegment();
		String entityName = m.getName();
		if (!expectedName.equals(entityName)) {
			return new CdlError("The qualified name " + entityName
					+ " does not match the expected name " + expectedName,
					eINSTANCE.getAstModule_Name(), CdlError.ERROR_NAME,
					expectedName);
		}
		return null;
	}

	public static CdlError checkOperatorArgumentType(AstExpressionBinary e) {
		TypeResult left = typeSystem.getType(e.getLeft());
		TypeResult right = typeSystem.getType(e.getRight());
		if ((left.toString().equals("String") || right.toString().equals(
				"String"))
				&& !(e.getOperator().equals("+") || CdlTypesystemRules
						.isComparaisonOperator(e.getOperator()))) {
			return new CdlError("The operator " + e.getOperator()
					+ " is undefined for the argument type(s) "
					+ left.toString() + "," + right.toString(),
					eINSTANCE.getAstExpressionBinary_Operator(),
					CdlError.ERROR_TYPE, "");
		}
		return null;
	}

}
