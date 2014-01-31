package net.sf.dftools.algorithm.model.psdf.maths.symbolic;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.NotDivisibleException;
import jscl.text.ParseException;

public class DivisionFactory {

	
	public static Generic divide(Generic gen1 , Generic gen2){
		try{
			return gen1.divide(gen2) ;
		}catch(NotDivisibleException e){
			try {
				return Expression.valueOf("("+gen1.toString()+")"+"/("+gen2.toString()+")");
			} catch (ParseException e1) {
				e1.printStackTrace();
				System.exit(-1);
			}
		}
		return null ;
	}

}
