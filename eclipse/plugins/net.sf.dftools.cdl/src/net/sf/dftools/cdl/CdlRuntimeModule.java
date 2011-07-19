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

package net.sf.dftools.cdl;

import net.sf.dftools.cdl.naming.CdlQualifiedNameProvider;

import org.eclipse.xtext.naming.IQualifiedNameProvider;


/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class CdlRuntimeModule extends net.sf.dftools.cdl.AbstractCdlRuntimeModule {

//	@Override
//	public Class<? extends ILinkingService> bindILinkingService() {
//		return CdlLinkingService.class;
//	}
	
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return CdlQualifiedNameProvider.class;
	}

//	public Class<? extends IResourceDescription.Manager> bindIResourceDescription$Manager() {
//		// this must be used so that the set of objects exported by an actor is
//		// minimal, namely input ports and output ports only
//
//		// this prevents the builder from building tens of actors each time an
//		// actor changes ever so slightly
//
//		// note that the NamesAreUniqueValidator only works on exported objects,
//		// so we have to do our own unique name validation, see the
//		// CalJavaValidator
//
//		return CdlResourceDescriptionManager.class;
//	}
//	
//	public Class<? extends ISyntaxErrorMessageProvider> bindISyntaxErrorMessageProvider() {
//		return CdlSyntaxErrorMessageProvider.class;
//	}
//
//	@Override
//	public Class<? extends IValueConverterService> bindIValueConverterService() {
//		return CdlValueConverter.class;
//	}
	
}
