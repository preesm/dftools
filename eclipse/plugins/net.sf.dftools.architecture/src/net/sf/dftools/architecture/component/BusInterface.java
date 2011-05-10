/* Copyright (c) 2010-2011 - EPFL
 *
 * The use of this software is defined in the consortium agreement of the
 * ACTORS European Project (Adaptivity and Control of Resources in Embedded Systems)
 * funded in part by the European Unions Seventh Framework Programme (FP7).
 * Grant agreement no 216586.
 */
package net.sf.dftools.architecture.component;

import net.sf.dftools.architecture.VLNV;

/**
 * @author ghislain roquier
 */

public class BusInterface {

	private String name;
	private VLNV vlnv;

	private boolean isServer;

	public BusInterface(String name, VLNV vlnv, boolean isServer) {
		this.name = name;
		this.vlnv = vlnv;
		this.isServer = isServer;

	}

	public String getName() {
		return name;
	}

	public VLNV getVlnv() {
		return vlnv;
	}

	public boolean isServer() {
		return isServer;
	}

	@Override
	public String toString() {
		return "busInterface " + name;
	}

}
