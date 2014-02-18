package net.sf.dftools.algorithm.model.generic;

import net.sf.dftools.algorithm.model.IInterface;
import net.sf.dftools.algorithm.model.InterfaceDirection;

public class GenericInterface implements IInterface {

	private InterfaceDirection dir;
	private String name;

	@Override
	public InterfaceDirection getDirection() {
		return dir;
	}

	@Override
	public void setDirection(String direction) {
		dir = InterfaceDirection.fromString(direction);
	}

	@Override
	public void setDirection(InterfaceDirection direction) {
		dir = direction;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
