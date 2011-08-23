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
package net.sf.dftools.cdl.typing;

import net.sf.dftools.cdl.cdl.AstAttribute;
import net.sf.dftools.cdl.cdl.AstBoolLiteral;
import net.sf.dftools.cdl.cdl.AstDomain;
import net.sf.dftools.cdl.cdl.AstDoubleLiteral;
import net.sf.dftools.cdl.cdl.AstEnum;
import net.sf.dftools.cdl.cdl.AstExpression;
import net.sf.dftools.cdl.cdl.AstExpressionBinary;
import net.sf.dftools.cdl.cdl.AstIntegerLiteral;
import net.sf.dftools.cdl.cdl.AstParenthesis;
import net.sf.dftools.cdl.cdl.AstStringLiteral;
import net.sf.dftools.cdl.cdl.AstSymbolRef;
import net.sf.dftools.cdl.cdl.util.CdlSwitch;
import net.sf.dftools.cdl.rules.CdlTypesystemRules;
import net.sf.dftools.cdl.utils.TypeUtils;

import org.eclipse.emf.ecore.EObject;

/**
 * 
 * @author Thavot Richard
 * 
 */
public class CdlTypesystem extends CdlSwitch<TypeResult> {

	@Override
	public TypeResult caseAstBoolLiteral(AstBoolLiteral object) {
		return new TypeResult(TypeUtils.createBasicType("bool"));
	}

	@Override
	public TypeResult caseAstStringLiteral(AstStringLiteral object) {
		return new TypeResult(TypeUtils.createBasicType("String"));
	}

	@Override
	public TypeResult caseAstDoubleLiteral(AstDoubleLiteral object) {
		return new TypeResult(TypeUtils.createBasicType("double"));
	}

	@Override
	public TypeResult caseAstIntegerLiteral(AstIntegerLiteral object) {
		return new TypeResult(TypeUtils.createBasicType("int"));
	}

	@Override
	public TypeResult caseAstDomain(AstDomain object) {
		AstDomain d = object;
		while (d.getRight() != null) {
			d = d.getRight();
		}
		return getType(d.getLeft());
	}

	@Override
	public TypeResult caseAstAttribute(AstAttribute object) {
		return new TypeResult(object.getType());
	}

	@Override
	public TypeResult caseAstSymbolRef(AstSymbolRef object) {
		AstEnum container = (AstEnum) object.getSymbol().eContainer();
		return new TypeResult(TypeUtils.createDeclType(container));
	}

	@Override
	public TypeResult caseAstExpression(AstExpression object) {
		if (object instanceof AstExpressionBinary) {
			return doSwitch(((AstExpressionBinary) object));
		} else if (object instanceof AstParenthesis) {
			return doSwitch(((AstParenthesis) object).getExpression());
		}
		return defaultCase(object);
	}

	@Override
	public TypeResult caseAstExpressionBinary(AstExpressionBinary object) {
		TypeResult left = doSwitch(object.getLeft());
		TypeResult right = doSwitch(object.getRight());
		if (!CdlTypesystemRules.isComparaisonOperator(object.getOperator()))
			return CdlTypesystemRules.compatibleTypeUnordered(left, right);
		else
			return new TypeResult(TypeUtils.createBasicType("bool"));
	}

	@Override
	public TypeResult caseAstParenthesis(AstParenthesis object) {
		return doSwitch(object.getExpression());
	}

	@Override
	public TypeResult defaultCase(EObject object) {
		return new TypeResult();
	}

	public TypeResult getType(EObject object) {
		return doSwitch(object);
	}

}
