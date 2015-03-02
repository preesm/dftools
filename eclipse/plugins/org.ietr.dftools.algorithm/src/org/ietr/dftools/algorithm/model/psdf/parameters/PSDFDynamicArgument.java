package org.ietr.dftools.algorithm.model.psdf.parameters;

import org.ietr.dftools.algorithm.model.parameters.Argument;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.parameters.NoIntegerValueException;

import jscl.math.Expression;
import jscl.text.ParseException;

public class PSDFDynamicArgument extends Argument implements DynamicValue{

	DynamicValue value ;
	
	public PSDFDynamicArgument(String name, DynamicValue  value) {
		super(name, "0");
		this.value = value ;
	}
	
	public DynamicValue getDynamicValue(){
		return value ;
	}
	
	@Override
	public String getValue(){
		return value.getName();
	}
	
	@Override
	public int intValue() throws InvalidExpressionException, NoIntegerValueException{
		throw(new NoIntegerValueException(this.getName()+" is a dynamic value"));
	}
	
	@Override
	public Expression getExpression() {
		try {
			return Expression.valueOf(value.toString());
		} catch (ParseException e) {
			try {
				return Expression.valueOf(getName());
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null ;
			}
		}
	}

}
