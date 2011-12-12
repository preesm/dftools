package net.sf.dftools.algorithm.model.dag;

import net.sf.dftools.algorithm.model.AbstractEdge;
import net.sf.dftools.algorithm.model.AbstractEdgePropertyType;
import net.sf.dftools.algorithm.model.PropertyFactory;

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
	 * Gives this DAGEdge weght
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
