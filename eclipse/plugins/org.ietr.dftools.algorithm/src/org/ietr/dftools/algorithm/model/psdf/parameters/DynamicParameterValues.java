package org.ietr.dftools.algorithm.model.psdf.parameters;

import java.util.ArrayList;
import java.util.List;

public class DynamicParameterValues extends ADynamicParameterDomain {
	private List<Integer> values;

	public DynamicParameterValues() {
		values = new ArrayList<Integer>();
	}

	public void addValue(Integer val) {
		values.add(val);
	}

	public List<Integer> getValues() {
		return values;
	}

	@Override
	public String toString() {
		String result = new String();
		result += "{";
		for (Integer val : values) {
			result += val + ", ";
		}
		result = result.substring(0, result.length() - 2) + "}";
		return result;
	}
}
