package org.ietr.dftools.algorithm.model;

/**
 * Enumeration of the interface directions
 * 
 * @author jpiat
 * 
 */
public enum InterfaceDirection {
	/**
	 * The direction is Input
	 */
	Input,
	/**
	 * The direction is Output
	 */
	Output;

	public String toString() {
		switch (this) {
		case Input:
			return "Input";
		case Output:
			return "Output";
		default:
			return "";
		}
	}

	/**
	 * Gives the InterfaceDirection represented by the String dir
	 * 
	 * @param dir
	 *            The String representation of the InterfaceDirection
	 * @return The InterfaceDirection represented by the String dir
	 */
	public static InterfaceDirection fromString(String dir) {
		if (dir.equalsIgnoreCase("Input")) {
			return Input;
		} else if (dir.equalsIgnoreCase("Output")) {
			return Output;
		}
		return null;
	}

}
