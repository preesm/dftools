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

package net.sf.dftools.cdl.scoping;

import java.util.ArrayList;
import java.util.List;

import net.sf.dftools.cdl.cdl.AstAttribute;
import net.sf.dftools.cdl.cdl.AstClass;
import net.sf.dftools.cdl.cdl.AstCore;
import net.sf.dftools.cdl.cdl.AstDecl;
import net.sf.dftools.cdl.cdl.AstField;
import net.sf.dftools.cdl.cdl.AstType;
import net.sf.dftools.cdl.cdl.AstDeclType;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;

/**
 * This class contains custom scoping provider.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 * 
 * @param <IScopedElement>
 * 
 * @author Thavot Richard
 * 
 */
public class CdlScopeProvider<IScopedElement> extends
		AbstractDeclarativeScopeProvider {

	public IScope scope_AstDomain_attribute(AstField field, EReference reference) {
		AstCore core = (AstCore) field.eContainer();

		return Scopes.scopeFor(getAstClassAttributes(core.getType()));
	}

	private List<AstAttribute> getAstClassAttributes(AstClass clasz) {
		List<AstAttribute> attributes = new ArrayList<AstAttribute>();

		for (AstAttribute attr : clasz.getAttributes()) {
			AstType attrType = attr.getType();
			if (attrType instanceof AstDeclType) {
				AstDeclType typeDecl = (AstDeclType) attrType;
				AstDecl decl = typeDecl.getType();
				if (decl instanceof AstClass) {
					attributes.addAll(getAstClassAttributes((AstClass) decl));
				}
			}
		}
		attributes.addAll(clasz.getAttributes());

		return attributes;
	}
}
