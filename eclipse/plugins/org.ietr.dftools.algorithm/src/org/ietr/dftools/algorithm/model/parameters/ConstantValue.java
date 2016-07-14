package org.ietr.dftools.algorithm.model.parameters;

public class ConstantValue implements Value{

	int value ;
	
	public ConstantValue(Integer value){
		this.value = value ;
	}
	
	@Override
	public String getValue() {
		return String.valueOf(value);
	}

	@Override
	public int intValue() throws InvalidExpressionException {
		return value;
	}

	@Override
	public void setExpressionSolver(IExpressionSolver solver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(String value) {
		try{
			int integerValue = Integer.decode(value);
			this.value = integerValue;
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return getValue();
	}
}
