/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2013)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
package org.ietr.dftools.algorithm.model.parameters;


/**
 * Class used to represent variable
 * 
 * @author jpiat
 * 
 */
public class Variable{

	private String name ;
	private Value value ;
	/**
	 * Builds a new variable with the given name
	 * 
	 * @param name
	 *            The name of the variable
	 */
	public Variable(String name) {
		this.name = name;
		value = new ExpressionValue("");
	}
	
	/**
	 * Builds a new variable with the given name and value
	 * 
	 * @param name
	 *            The name of the variable
	 * @param value
	 *            The value of the variable
	 */
	public Variable(String name, String value) {
		this.name = name;
		try{
			int integerValue = Integer.decode(value);
			this.value = new ConstantValue(integerValue);
		}catch(NumberFormatException e){
			this.value = new ExpressionValue(value);
		}
	}

	/**
	 * Gives the name of the variable
	 * 
	 * @return The name of the variable
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name of the variable
	 * 
	 * @param name
	 *            The name to set for the variable
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Set the solver to use for this expression
	 * 
	 * @param solver
	 *            The solver to use to compute int value
	 */
	public void setExpressionSolver(IExpressionSolver solver) {
		value.setExpressionSolver(solver);
	}
	
	public int intValue() throws InvalidExpressionException, NoIntegerValueException{
		int val = value.intValue();
		this.value = new ConstantValue(val);
		return val;
	}
	
	public String getValue(){
		return value.getValue();
	}
	
	public void setValue(String value) {
		try{
			int integerValue = Integer.decode(value);
			this.value = new ConstantValue(integerValue);
		}catch(NumberFormatException e){
			this.value = new ExpressionValue(value);
		}
	}
	
	@Override
	public Variable clone(){
		Variable var = new Variable(this.name);
		var.setValue(this.value.getValue());
		return var ;
	}

}
