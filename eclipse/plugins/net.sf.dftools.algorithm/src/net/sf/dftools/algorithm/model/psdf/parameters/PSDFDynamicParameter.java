package net.sf.dftools.algorithm.model.psdf.parameters;

import jscl.math.Expression;
import jscl.text.ParseException;

import net.sf.dftools.algorithm.model.parameters.NoIntegerValueException;
import net.sf.dftools.algorithm.model.parameters.Parameter;

public class PSDFDynamicParameter extends Parameter implements DynamicValue {

	private ADynamicParameterDomain domain;
	private DynamicValue value ;

	public PSDFDynamicParameter(String name) {
		super(name);
	}
	
	public PSDFDynamicParameter(String name, DynamicValue value) {
		super(name);
		this.value = value ;
	}

	public DynamicValue getDynamicValue(){
		return value ;
	}
	
	
	public void setDomain(ADynamicParameterDomain domain) {
		this.domain = domain;
	}
	
	public ADynamicParameterDomain getDomain(){
		return domain ;
	}
	
	public Integer getValue() throws NoIntegerValueException{
		throw(new NoIntegerValueException(this.getName()+" is a dynamic value"));
	}

	@Override
	public Expression getExpression() {
		try {
			if(value != null){
				return Expression.valueOf(value.toString());
			}else{
				return Expression.valueOf(getName());
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
}
