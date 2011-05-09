/* Copyright (c) 2010-2011 - EPFL
 *
 * The use of this software is defined in the consortium agreement of the
 * ACTORS European Project (Adaptivity and Control of Resources in Embedded Systems)
 * funded in part by the European Unions Seventh Framework Programme (FP7).
 * Grant agreement no 216586.
 */
package net.sf.dftools.architecture.component;

import java.util.HashMap;
import java.util.Map;

import net.sf.dftools.architecture.VLNV;

/**
 * This class defines a vertex in an IP-XACT design.
 * 
 * @author Ghislain Roquier
 * 
 */

public class Component {

	private String clasz;

	private VLNV vlnv;

	Map<String, BusInterface> interfaces = new HashMap<String, BusInterface>();

	public Map<String, BusInterface> getInterfaces() {
		return interfaces;
	}

	public BusInterface getInterface(String name) {
		return interfaces.get(name);
	}

	public Component(String clasz, VLNV vlnv,
			Map<String, BusInterface> interfaces) {
		this.clasz = clasz;
		this.vlnv = vlnv;
		this.interfaces = interfaces;
	}

	public VLNV getVlnv() {
		return vlnv;
	}

	public String getClasz() {
		return clasz;
	}

}
