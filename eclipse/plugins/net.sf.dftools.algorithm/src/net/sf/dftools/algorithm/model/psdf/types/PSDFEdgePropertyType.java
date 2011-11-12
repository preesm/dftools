package net.sf.dftools.algorithm.model.psdf.types;

import net.sf.dftools.algorithm.model.AbstractEdgePropertyType;
import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.psdf.PSDFInitVertex;

public class PSDFEdgePropertyType extends AbstractEdgePropertyType<String>{

	
	private PSDFInitVertex solver ;
	
	public PSDFEdgePropertyType(String symbolicName) {
		super(symbolicName);
	} 
	
	@Override
	public AbstractEdgePropertyType<String> clone() {
		PSDFEdgePropertyType type = new PSDFEdgePropertyType(this.getValue());
		return type;
	}
	
	public String getSymbolicName(){
		return this.getValue();
	}

	public void setInitVertex(PSDFInitVertex init){
		solver = init ;
	}
	
	
	public PSDFInitVertex getInitVertex(){
		return solver ;
	}
	@Override
	public int intValue() throws InvalidExpressionException {
		
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getSymbolicName();
	}

}
