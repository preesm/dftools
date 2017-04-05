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
package org.ietr.dftools.algorithm.model.dag;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.AbstractEdgePropertyType;
import org.ietr.dftools.algorithm.model.PropertyFactory;

/**
 * Class used to represent an Edge in a Directed Acyclic Graph
 * 
 * @author jpiat
 * 
 */
public class DAGEdge extends AbstractEdge<DirectedAcyclicGraph, DAGVertex> {

	/**
	 * Key to access to property weight
	 */
	public static final String WEIGHT = "Weight";

	/**
	 * Key to access to property aggregate
	 */
	public static final String AGGREGATE = "aggregate";

	static {
		{
			public_properties.add(WEIGHT);
		}
	};

	/**
	 * Creates a new empty DAGEdge
	 */
	public DAGEdge() {
		super();
	}

	/**
	 * Creates a new DAGEdge with the given property
	 * 
	 * @param w
	 */
	public DAGEdge(AbstractEdgePropertyType<?> w) {
		super();
		setWeight(w);
	}

	/**
	 * Gives this DAGEdge weight
	 * 
	 * @return This DAGEdge weight
	 */
	public AbstractEdgePropertyType<?> getWeight() {
		if (getPropertyBean().getValue(WEIGHT) != null) {
			return (AbstractEdgePropertyType<?>) getPropertyBean().getValue(
					WEIGHT);
		}
		return null;
	}

	/**
	 * Set this DAGEdge weight
	 * 
	 * @param w
	 *            The weight to set for this DAGEdge
	 */
	public void setWeight(AbstractEdgePropertyType<?> w) {
		getPropertyBean().setValue(WEIGHT, w);
	}

	/**
	 * Gives this DAGEdge aggregate
	 * 
	 * @return This DAGEdge aggregate
	 */
	public EdgeAggregate getAggregate() {
		if (getPropertyBean().getValue(AGGREGATE) != null) {
			return (EdgeAggregate) getPropertyBean().getValue(AGGREGATE);
		} else {
			EdgeAggregate agg = new EdgeAggregate();
			setAggregate(agg);
			return agg;
		}
	}

	/**
	 * Set this DAGEdge weight
	 * 
	 * @param a
	 *            The weight to set for this DAGEdge
	 */
	public void setAggregate(EdgeAggregate a) {
		getPropertyBean().setValue(AGGREGATE, a);
	}

	@Override
	public String toString() {
		String result = new String();
		result += " w=" + getWeight();
		// result += "["+getAggregate()+"]";
		return result;
	}

	@Override
	public PropertyFactory getFactoryForProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}
}
