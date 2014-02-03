package org.ietr.dftools.algorithm.model.psdf.parameters;

public class DynamicParameterRange extends ADynamicParameterDomain{
	
	private Integer max ;
	private Integer min ;
	
	public DynamicParameterRange(Integer min, Integer max){
		this.max = max ;
		this.min = min ;
	}
	
	public DynamicParameterRange(int min, int max){
		this.max = max ;
		this.min = min ;
	}

	
	public Integer getMaxValue(){
		return max ;
	}
	
	public Integer getMinValue(){
		return min ;
	}
	
	public String toString(){
		return "{"+min+".."+max+"}";
	}

}
