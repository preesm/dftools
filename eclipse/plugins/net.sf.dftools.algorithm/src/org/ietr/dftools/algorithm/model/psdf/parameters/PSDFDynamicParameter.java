package org.ietr.dftools.algorithm.model.psdf.parameters;

import org.ietr.dftools.algorithm.model.parameters.NoIntegerValueException;
import org.ietr.dftools.algorithm.model.parameters.Parameter;

import jscl.math.Expression;
import jscl.text.ParseException;

public class PSDFDynamicParameter extends Parameter implements DynamicValue {

	private ADynamicParameterDomain domain;
	private DynamicValue value ;

	public PSDFDynamicParameter(String name) {
		super(name);
		if(name.contains("{")){
			this.setName(name.substring(0, name.indexOf("{")));
			try {
				domain = DynamicParameterDomainFactory.create(name.substring(name.indexOf("{")));
			} catch (DomainParsingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public PSDFDynamicParameter(String name, DynamicValue value) {
		this(name);
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
