/* Copyright (c) 2010-2011 - IETR/INSA de Rennes and EPFL
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
 *   * Neither the name of the IETR/INSA de Rennes and EPFL nor the names of its
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
package net.sf.dftools.architecture.design;

import java.util.Map;

import net.sf.dftools.architecture.VLNV;
import net.sf.dftools.architecture.component.Component;

public class ComponentInstance {

	private enum Type {
		COMPONENT, DESIGN
	}

	private String id;

	private Object contents;

	private Type type;

	private Map<String, String> configValues;

	private VLNV vlnv;
	
	public ComponentInstance(String id, VLNV vlnv, Component contents,
			Map<String, String> configValues) {
		this.id = id;
		this.vlnv = vlnv;
		this.contents = contents;
		this.configValues = configValues;
		type = Type.COMPONENT;
	}

	public ComponentInstance(String id, VLNV vlnv, Design contents,
			Map<String, String> configValues) {
		this.id = id;
		this.vlnv = vlnv;
		this.contents = contents;
		this.configValues = configValues;
		type = Type.DESIGN;
	}

	public Component getComponent() {
		if (isComponent()) {
			return (Component) contents;
		} else {
			return null;
		}
	}

	public Design getDesign() {
		if (isDesign()) {
			return (Design) contents;
		} else {
			return null;
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public boolean isComponent() {
		return type == Type.COMPONENT;
	}

	public boolean isDesign() {
		return type == Type.DESIGN;
	}

	public Map<String, String> getConfigValues() {
		return configValues;
	}

	@Override
	public String toString() {
		return "instance " + id;
	}

	public VLNV getVlnv() {
		return vlnv;
	}
}
