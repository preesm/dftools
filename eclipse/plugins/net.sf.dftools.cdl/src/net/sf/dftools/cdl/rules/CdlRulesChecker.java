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

import net.sf.dftools.cdl.cdl.AstExpressionBinary;
import net.sf.dftools.cdl.cdl.AstField;
import net.sf.dftools.cdl.cdl.AstModule;
import net.sf.dftools.cdl.cdl.util.CdlSwitch;
import net.sf.dftools.cdl.errors.CdlError;
import net.sf.dftools.cdl.validation.CdlJavaValidator;

import org.eclipse.emf.ecore.EObject;

/**
 * Type checker for cdl modules
 * 
 * @author Thavot Richard
 * 
 */
public class CdlRulesChecker extends CdlSwitch<CdlError> {

	@SuppressWarnings("unused")
	private CdlJavaValidator validator;

	/**
	 * Creates a new type checker.
	 */
	public CdlRulesChecker(CdlJavaValidator validator) {
		this.validator = validator;
	}

	@Override
	public CdlError caseAstField(AstField f) {
		CdlError result = CdlRules.checkFieldDuplication(f);
		if (result == null) result = CdlRules.checkCompatibleType(f);
		return result;
	}

	@Override
	public CdlError caseAstModule(AstModule m) {
		return CdlRules.checkModuleName(m);
	}
	
	@Override
	public CdlError caseAstExpressionBinary(AstExpressionBinary e){
		CdlError result = CdlRules.checkCompatibleType(e);
		if(result == null) result = CdlRules.checkOperatorArgumentType(e);
		return result;
	}


	public CdlError getRulesError(EObject object) {
		return doSwitch(object);
	}

}
