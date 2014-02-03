/* Copyright (c) 2010-2011 - EPFL
 *
 * The use of this software is defined in the consortium agreement of the
 * ACTORS European Project (Adaptivity and Control of Resources in Embedded Systems)
 * funded in part by the European Unions Seventh Framework Programme (FP7).
 * Grant agreement no 216586.
 */
package org.ietr.dftools.architecture.component;

import org.ietr.dftools.architecture.VLNV;

/**
 * This class defines a bus interfaces.
 * 
 * @author Ghislain Roquier
 * 
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

	public BusInterface(String name) {
		this.name = name;
		vlnv = new VLNV(name);
		isServer = false;
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
