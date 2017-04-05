/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package org.ietr.dftools.architecture.utils;

import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

/**
 * This class defines utility methods to create DOM documents and print them to
 * an output stream using DOM 3 Load Save objects.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DomUtil {

	private static DOMImplementation impl;

	private static DOMImplementationRegistry registry;

	/**
	 * Creates a new DOM document.
	 * 
	 * @param docElt
	 *            name of the document element
	 * @return a new DOM document if something goes wrong
	 */
	public static Document createDocument(String docElt) {
		getImplementation();
		return impl.createDocument("", docElt, null);
	}

	public static Document createDocument(String namespaceURI, String docElt) {
		getImplementation();
		return impl.createDocument(namespaceURI, docElt, null);
	}

	/**
	 * Creates a new instance of the DOM registry and get an implementation of
	 * DOM 3 with Load Save objects.
	 * 
	 */
	private static void getImplementation() {
		if (registry == null) {
			try {
				registry = DOMImplementationRegistry.newInstance();
			} catch (ClassCastException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		if (impl == null) {
			impl = registry.getDOMImplementation("Core 3.0 XML 3.0 LS");
		}
	}

	/**
	 * Parses the given input stream as XML and returns the corresponding DOM
	 * document.
	 * 
	 * @param is
	 *            an input stream
	 * @return a DOM document if something goes wrong
	 */
	public static Document parseDocument(InputStream is) {
		getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl;

		// serialize to XML
		LSInput input = implLS.createLSInput();
		input.setByteStream(is);

		// parse without comments and whitespace
		LSParser builder = implLS.createLSParser(
				DOMImplementationLS.MODE_SYNCHRONOUS, null);
		DOMConfiguration config = builder.getDomConfig();
		config.setParameter("comments", false);
		config.setParameter("element-content-whitespace", false);

		return builder.parse(input);
	}

	/**
	 * Writes the given document to the given output stream.
	 * 
	 * @param os
	 *            an output stream
	 * @param document
	 *            a DOM document created by
	 *            {@link #writeDocument(OutputStream, Document)} if something
	 *            goes wrong
	 */
	public static void writeDocument(OutputStream os, Document document) {
		getImplementation();
		DOMImplementationLS implLS = (DOMImplementationLS) impl;

		// serialize to XML
		LSOutput output = implLS.createLSOutput();
		output.setByteStream(os);

		// serialize the document, close the stream
		LSSerializer serializer = implLS.createLSSerializer();
		serializer.getDomConfig().setParameter("format-pretty-print", true);
		serializer.write(document, output);
	}

}
