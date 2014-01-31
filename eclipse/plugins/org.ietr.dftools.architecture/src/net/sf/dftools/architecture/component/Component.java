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
import net.sf.dftools.architecture.design.Design;

/**
 * This class defines a component. A component has a name, a VLNV. A component
 * may also be hierarchical and thus may include a design.
 * 
 * @author Ghislain Roquier
 * 
 */
public abstract class Component implements IComponent {

	private String name;

	private VLNV vlnv;

	private Design design;

	Map<String, BusInterface> interfaces = new HashMap<String, BusInterface>();

	public Component(VLNV vlnv, Map<String, BusInterface> interfaces,
			Design design) {
		this.name = vlnv.getName();
		this.vlnv = vlnv;
		this.interfaces = interfaces;
		this.design = design;
	}

	/**
	 * 
	 * @return the design included in the component
	 */
	public Design getDesign() {
		return design;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public BusInterface getInterface(String name) {
		return interfaces.get(name);
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, BusInterface> getInterfaces() {
		return interfaces;
	}

	/**
	 * 
	 * @return the class of the component
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return
	 */
	public VLNV getVlnv() {
		return vlnv;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isHierarchical() {
		return design != null;
	}

	@Override
	public boolean isMedium() {
		return false;
	}

	@Override
	public boolean isOperator() {
		return false;
	}

}
