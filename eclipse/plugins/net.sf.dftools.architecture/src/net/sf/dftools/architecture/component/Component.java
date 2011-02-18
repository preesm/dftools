/* Copyright (c) 2010-2011 - EPFL
 *
 * The use of this software is defined in the consortium agreement of the
 * ACTORS European Project (Adaptivity and Control of Resources in Embedded Systems)
 * funded in part by the European Unions Seventh Framework Programme (FP7).
 * Grant agreement no 216586.
 */
package net.sf.dftools.architecture.component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.dftools.architecture.VLNV;

/**
 * This class defines a vertex in an IP-XACT design.
 * 
 * @author Ghislain Roquier
 * 
 */

public class Component {

	private static final Set<String> processors = new HashSet<String>(
			Arrays.asList("x86", "PowerPC"));
	private static final Set<String> fpgas = new HashSet<String>(
			Arrays.asList("SP605"));
	private static final Set<String> buses = new HashSet<String>(Arrays.asList(
			"Ethernet", "RS232", "SharedMemory"));

	public enum Type {
		PROCESSOR, FPGA, BUS
	};

	private String clasz;

	private VLNV vlnv;

	Map<String, BusInterface> interfaces = new HashMap<String, BusInterface>();

	public Map<String, BusInterface> getInterfaces() {
		return interfaces;
	}

	public BusInterface getInterface(String name) {
		return interfaces.get(name);
	}

	private Type type;

	public Component(String clasz, VLNV vlnv,
			Map<String, BusInterface> interfaces) {
		this.clasz = clasz;
		this.vlnv = vlnv;
		this.interfaces = interfaces;

		if (processors.contains(clasz)) {
			type = Type.PROCESSOR;
		} else if (fpgas.contains(clasz)) {
			type = Type.FPGA;
		} else if (buses.contains(clasz)) {
			type = Type.BUS;
		}
	}

	public VLNV getVlnv() {
		return vlnv;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public boolean isProcessor() {
		return type == Type.PROCESSOR;
	}

	public boolean isFPGA() {
		return type == Type.FPGA;
	}

	public boolean isBus() {
		return type == Type.BUS;
	}

	public String getClasz() {
		return clasz;
	}

}
