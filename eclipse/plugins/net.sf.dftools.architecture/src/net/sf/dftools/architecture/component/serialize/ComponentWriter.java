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
package net.sf.dftools.architecture.component.serialize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.dftools.architecture.VLNV;
import net.sf.dftools.architecture.component.BusInterface;
import net.sf.dftools.architecture.component.Component;
import net.sf.dftools.architecture.design.serialize.DesignWriter;
import net.sf.dftools.architecture.utils.DomUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ComponentWriter {

	Document document;

	Component component;

	private File path;

	public ComponentWriter(File path, Component component) {
		this.component = component;
		this.path = path;

		document = DomUtil.createDocument(
				"http://www.spiritconsortium.org/XMLSchema/SPIRIT/1.4",
				"spirit:component");
		writeComponent(document.getDocumentElement());

		File file = new File(path, component.getVlnv().getName() + ".component");
		try {
			OutputStream os = new FileOutputStream(file);
			DomUtil.writeDocument(os, document);
			os.close();
		} catch (IOException e) {
			System.out.println("I/O error");
		}
	}

	private void writeBusInterface(Element parent, BusInterface intf) {
		Element cmpElt = document.createElement("spirit:busInterface");
		parent.appendChild(cmpElt);

		Element nameElt = document.createElement("spirit:name");
		cmpElt.appendChild(nameElt);
		nameElt.setTextContent(intf.getName());
	}

	private void writeBusInterfaces(Element parent) {
		Element cmpsElt = document.createElement("spirit:busInterfaces");
		parent.appendChild(cmpsElt);

		for (BusInterface intf : component.getInterfaces().values()) {
			writeBusInterface(cmpsElt, intf);
		}
	}

	private void writeComponent(Element documentElt) {
		writeVLNV(documentElt);
		writeBusInterfaces(documentElt);
		writeSubDesign(documentElt);
	}

	private void writeSubDesign(Element parent) {
		if (component.isHierarchical()) {
			Element cmpsElt = document.createElement("spirit:model");
			parent.appendChild(cmpsElt);
			writeViews(cmpsElt);
		}
	}

	private void writeView(Element parent) {
		Element cmpsElt = document.createElement("spirit:view");
		parent.appendChild(cmpsElt);

		Element hierElt = document.createElement("spirit:hierarchyRef");
		cmpsElt.appendChild(hierElt);
		hierElt.setAttribute("spirit:name", component.getDesign().getName());

		new DesignWriter(path, component.getDesign());
	}

	private void writeViews(Element parent) {
		Element cmpsElt = document.createElement("spirit:views");
		parent.appendChild(cmpsElt);

		// support only one view right now
		writeView(cmpsElt);
	}

	private void writeVLNV(Element parent) {
		VLNV vlnv = component.getVlnv();
		Element child = document.createElement("spirit:vendor");
		parent.appendChild(child);
		child.setTextContent(vlnv.getVendor());
		child = document.createElement("spirit:library");
		parent.appendChild(child);
		child.setTextContent(vlnv.getLibrary());
		child = document.createElement("spirit:name");
		parent.appendChild(child);
		child.setTextContent(vlnv.getName());
		child = document.createElement("spirit:version");
		parent.appendChild(child);
		child.setTextContent(vlnv.getVersion());
	}

}
