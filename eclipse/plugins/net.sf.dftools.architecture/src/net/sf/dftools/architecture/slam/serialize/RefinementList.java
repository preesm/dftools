package net.sf.dftools.architecture.slam.serialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
/**
 * A complex refinement made of a list of files.
 * Used to serialize multiple refinements.
 * 
 * @author mpelcat
 */
public class RefinementList {

	/**
	 * Storing relative paths to the refinements
	 */
	List<String> nameList = new ArrayList<String>();

	public RefinementList() {
	}
	
	public RefinementList(String stringList) {
		fromString(stringList);
	}

	public void addName(String name) {
		nameList.add(name);
	}

	public void removeName(String name) {
		Iterator<String> iterator = nameList.listIterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			if (next.equals(name)) {
				iterator.remove();
			}
		}
	}

	/**
	 * Parsing the refinement paths from a comma-separated list of strings
	 */
	public void fromString(String stringList) {
		nameList.addAll(Arrays.asList(stringList.split(",")));
	}

	public int size() {
		return nameList.size();
	}

	public String[] toStringArray() {
		return nameList.toArray(new String[0]);
	}

	/**
	 * Exporting the refinement paths as a comma-separated list of strings
	 */
	@Override
	public String toString() {
		String stringList = "";
		for (String fileName : nameList) {
			stringList += fileName + ",";
		}
		if (!stringList.isEmpty()) {
			stringList = stringList.substring(0, stringList.length() - 1);
		}
		return stringList;
	}
}
