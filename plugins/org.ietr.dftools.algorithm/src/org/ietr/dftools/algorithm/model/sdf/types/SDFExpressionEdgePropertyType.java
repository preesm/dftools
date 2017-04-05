/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (%%DATE%%) :
 *
 * %%AUTHORS%%
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
package org.ietr.dftools.algorithm.model.sdf.types;

import org.ietr.dftools.algorithm.model.AbstractEdgePropertyType;
import org.ietr.dftools.algorithm.model.parameters.IExpressionSolver;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.parameters.NoIntegerValueException;
import org.ietr.dftools.algorithm.model.parameters.Value;

/**
 * Class used to represent the integer edge property type in a SDF
 * 
 * @author jpiat
 * 
 */
public class SDFExpressionEdgePropertyType extends
		AbstractEdgePropertyType<Value> {

	private Integer computedValue;

	/**
	 * Creates a new SDFDefaultEdgePropertyType with the given graph value
	 * 
	 * @param val
	 *            The Integer value of this SDFDefaultEdgePropertyType
	 */
	public SDFExpressionEdgePropertyType(Value val) {
		super(val);
		computedValue = null;
	}

	@Override
	public AbstractEdgePropertyType<Value> clone() {
		SDFExpressionEdgePropertyType clone = new SDFExpressionEdgePropertyType(
				value);
		try {
			clone.computedValue = intValue();
		} catch (InvalidExpressionException e) {
			clone.computedValue = null;
		}
		return clone;
	}

	@Override
	public void setValue(Value val) {
		super.setValue(val);
		computedValue = null;
	}

	/**
	 * Sets the expression solver to use to compute intValue
	 * 
	 * @param solver
	 *            The solver to be used
	 */
	public void setExpressionSolver(IExpressionSolver solver) {
		this.getValue().setExpressionSolver(solver);
		computedValue = null;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public int intValue() throws InvalidExpressionException {
		if (computedValue == null) {
			try {
				computedValue = value.intValue();
				return computedValue;
			} catch (NoIntegerValueException e) {
				e.printStackTrace();
				return 0 ;
			}
		}
		return computedValue;

	}

}
