/* Copyright (c) 2010-2011 - EPFL
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
 *   * Neither the name of the EPFL nor the names of its contributors may be used 
 *     to endorse or promote products derived from this software without specific 
 *     prior written permission.
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
package net.sf.dftools.architecture.component;

import java.util.Map;

import net.sf.dftools.architecture.VLNV;
import net.sf.dftools.architecture.design.Design;

/**
 * This class defines an operator factory.
 * 
 * @author Ghislain Roquier
 * 
 */
public class OperatorFactory {

	private static final OperatorFactory instance = new OperatorFactory();

	public static OperatorFactory getInstance() {
		return instance;
	}

	private Component createFPGA(VLNV vlnv,
			Map<String, BusInterface> interfaces, Design design,
			Map<String, String> options) {
		return new FPGA(vlnv, interfaces, design, options);
	}

	public Component createOperator(VLNV vlnv,
			Map<String, BusInterface> interfaces, Design design,
			Map<String, String> options) {
		String operatorType = options.get("operatorType");

		Component component = null;
		if (operatorType.equals("processor")) {
			component = createProcessor(vlnv, interfaces, design, options);
		} else if (operatorType.equals("fpga")) {
			component = createFPGA(vlnv, interfaces, design, options);
		} else {
		}
		return component;
	}

	private Component createProcessor(VLNV vlnv,
			Map<String, BusInterface> interfaces, Design design,
			Map<String, String> options) {
		return new Processor(vlnv, interfaces, design, options);
	}
}
