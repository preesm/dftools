package net.sf.dftools.algorithm.model.psdf.parameters;

import jscl.math.Expression;


public interface DynamicValue {
	
	public String getName();
	public Expression getExpression();

}
