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

package net.sf.dftools.cdl.validation;

import net.sf.dftools.cdl.cdl.AstDecl;
import net.sf.dftools.cdl.cdl.AstModule;
import net.sf.dftools.cdl.cdl.CdlPackage;
import net.sf.dftools.cdl.errors.CdlError;
import net.sf.dftools.cdl.rules.CdlRulesChecker;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

/**
 * 
 * @author Thavot Richard
 *
 */
public class CdlJavaValidator extends AbstractCdlJavaValidator {

	@Check(CheckType.FAST)
	public void checkClassNameStartsWithCapital(AstDecl d) {
		if (!Character.isUpperCase(d.getName().charAt(0))) {
			warning(d.getIs() + " name should start with a capital",
					CdlPackage.Literals.AST_DECL__NAME, CdlError.ERROR_NAME,
					d.getName());
		}
	}

	@Check(CheckType.FAST)
	public void checkModuleNameStartsWithCapital(AstModule m) {
		if (!Character.isUpperCase(m.getName().charAt(0))) {
			warning(m.getIs() + " name should start with a capital",
					CdlPackage.Literals.AST_MODULE__NAME, CdlError.ERROR_NAME,
					m.getName());
		}
	}

	@Check(CheckType.NORMAL)
	public void checkEObject(EObject o) {
		CdlError e = new CdlRulesChecker(this).getRulesError(o);
		if (e != null) {
			error(e.getMessage(), o, e.getFeature(), e.getCode(),
					e.getIssueData());
		}
	}

}
