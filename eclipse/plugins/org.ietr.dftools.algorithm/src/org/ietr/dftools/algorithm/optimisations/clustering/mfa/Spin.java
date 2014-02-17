package org.ietr.dftools.algorithm.optimisations.clustering.mfa;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;

/**
 * Class used to represent a spin in the MFA algorithm
 * 
 * @author jpiat
 * 
 */
public class Spin {

	protected boolean noChange;

	protected double probability;

	private Node node;

	/**
	 * Constructs a new Spin with the given initialization value and the given
	 * parent vertex
	 * 
	 * @param init The initialization value
	 * @param node The Node this spin belongs to
	 */
	public Spin(double init, Node node) {
		this.node = node;
		probability = init;
		noChange = true;
	}

	/**
	 * Gives the spin value
	 * @return The value of the spin
	 */
	public double getValue() {
		return probability;
	}

	/**
	 * Gives this node corresponding vertex 
	 * @return The vertex this node is based on
	 */
	public SDFAbstractVertex getVertex() {
		return node.getVertex();
	}

	/**
	 * Quantify the spin value
	 * @return The spin value
	 */
	protected int quantSpin() {
		if (probability > (1/node.getSpins().size()/10)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Set the Value of this Spin
	 * 
	 * @param val
	 */
	public void setValue(double val) {
		probability = val;
	}

	/**
	 * Sets the new value and compute the hold state
	 * 
	 * @param oldVal
	 *            The old value of the particle's spin
	 * @param newVal
	 *            The new value of the particle's spin
	 */
	public void setValue(double oldVal, double newVal) {
		if (Math.abs(oldVal - newVal) < 0.0001) {
			noChange = true;
		} else {
			noChange = false;
		}
		probability = newVal;
	}
	
	public String toString(){
		return ((Double) probability).toString() ;
	}
}
