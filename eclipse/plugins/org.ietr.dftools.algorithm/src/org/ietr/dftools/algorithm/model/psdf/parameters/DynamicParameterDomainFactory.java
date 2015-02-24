package org.ietr.dftools.algorithm.model.psdf.parameters;

public class DynamicParameterDomainFactory {

	public static ADynamicParameterDomain create(String value) throws DomainParsingException{
		if( value.contains("..")){
			int max = Integer.MAX_VALUE;
			int min = 0 ;
			String [] extremum = value.split("\\.\\.");
			if(extremum.length != 2){
				throw(new DomainParsingException());
			}
			
			if(extremum[0].contains("{")){
				min = Integer.decode(extremum[0].substring(extremum[0].indexOf("{") + 1).replaceAll(" ", ""));
			}
			if(extremum[1].contains("}")){
				max = Integer.decode(extremum[1].substring(0,extremum[1].indexOf("}")).replaceAll(" ", ""));
			}
			if(min > max){
				int buff = min ;
				min = max ;
				max = buff ;
			}
			return new DynamicParameterRange(min, max);
		}else if(value.contains(",")){
			String [] values ;
			value = value.replaceAll("\\{", "");
			value = value.replaceAll("\\}", "");
			values = value.split(",");
			DynamicParameterValues vals = new DynamicParameterValues();
			for(int i = 0 ; i < values.length ; i ++){
				String oneValue = values[i].replaceAll(" ", ""); 
				vals.addValue(Integer.decode(oneValue));
			}
			return vals ;
		}
		return null ;
	}
	
	public static void main(String [] args){
		try {
			System.out.println(DynamicParameterDomainFactory.create("{5,6,7,8,9}"));
			System.out.println(DynamicParameterDomainFactory.create("{5..9}"));
			new PSDFDynamicParameter("d{5..9}");
		} catch (DomainParsingException e) {
			e.printStackTrace();
		}
	}
}
