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
package org.ietr.dftools.algorithm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;

/**
 * Abstract class common to all edges
 * 
 * @author jpiat
 * 
 * @param <G>
 * @param <V>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractEdge<G, V extends AbstractVertex> extends
		Observable implements PropertySource, Observer {

	/**
	 * 
	 */
	private PropertyBean property;
	/**
	 * Property name for property base
	 */
	public static final String BASE = "base";

	/**
	 * Property name for property source_port
	 */
	public static final String SOURCE_PORT = "source_port";

	/**
	 * Property name for property target_port
	 */
	public static final String TARGET_PORT = "target_port";

	@SuppressWarnings("serial")
	protected static List<String> public_properties = new ArrayList<String>() {
		{

		}
	};

	/**
	 * Creates a new AbstractEdge
	 */
	public AbstractEdge() {
		property = new PropertyBean();
	}

	@Override
	public List<String> getPublicProperties() {
		return public_properties;
	}

	/**
	 * @param visitor
	 *            The visitor to accept
	 */
	public void accept(IGraphVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Gives this edge parent graph
	 * 
	 * @return The parent graph of this edge
	 */
	public G getBase() {
		return (G) property.getValue(BASE);
	}

	/**
	 * Give this edge property bean
	 * 
	 * @return The edge property bean
	 */
	@Override
	public PropertyBean getPropertyBean() {
		return property;
	}

	/**
	 * Gives the source label
	 * 
	 * @return The label of the source, usually the name of the port this edge
	 *         is conected to
	 */
	public String getSourceLabel() {
		if (getPropertyBean().getValue(SOURCE_PORT) != null) {
			return getPropertyBean().getValue(SOURCE_PORT).toString();
		}
		return null;
	}

	/**
	 * Gives the target label
	 * 
	 * @return The label of the target, usually the name of the port this edge
	 *         is conected to
	 */
	public String getTargetLabel() {
		if (getPropertyBean().getValue(TARGET_PORT) != null) {
			return getPropertyBean().getValue(TARGET_PORT).toString();
		}
		return null;
	}

	/**
	 * Sets the target label
	 * 
	 * @param label
	 *            The label of the target, usually the name of the port this
	 *            edge is conected to
	 */
	public void setTargetLabel(String label) {
		getPropertyBean().setValue(TARGET_PORT, label);
	}

	/**
	 * Sets the source label
	 * 
	 * @param label
	 *            The label of the source, usually the name of the port this
	 *            edge is conected to
	 */
	public void setSourceLabel(String label) {
		getPropertyBean().setValue(SOURCE_PORT, label);
	}

	/**
	 * Gives this edge source
	 * 
	 * @return The source vertex of this edge
	 */
	public V getSource() {
		if (getBase() != null) {
			return (V) ((AbstractGraph) getBase()).getEdgeSource(this);
		}
		return null;

	}

	/**
	 * Gives this edge target
	 * 
	 * @return The target vertex of this edge
	 */
	public V getTarget() {
		if (getBase() != null) {
			return (V) ((AbstractGraph) getBase()).getEdgeTarget(this);
		}
		return null;
	}

	/**
	 * Set this edge parent graph
	 * 
	 * @param base
	 *            The parent graph to set for this edge
	 */
	protected void setBase(G base) {
		property.setValue(BASE, base);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			if (arg instanceof String && o instanceof AbstractEdge) {
				Object property = ((AbstractEdge) o).getPropertyBean()
						.getValue((String) arg);
				if (property != null) {
					this.getPropertyBean().setValue((String) arg, property);
				}
			}
		}
	}

	/**
	 * Test if the given edge has the same properties than this edge
	 * 
	 * @param edge
	 *            The edge to compare with
	 * @return True if the given edge has the same properties, false otherwise
	 */
	public boolean compare(AbstractEdge edge) {
		return edge.getSource().getName().equals(this.getSource().getName())
				&& edge.getTarget().getName()
						.equals(this.getTarget().getName());
	}

	@Override
	public void copyProperties(PropertySource props) {
		for (String key : props.getPropertyBean().keys()) {
			if (!key.equals(AbstractEdge.BASE)) {
				if (props.getPropertyBean().getValue(key) instanceof CloneableProperty) {
					this.getPropertyBean().setValue(
							key,
							((CloneableProperty) props.getPropertyBean()
									.getValue(key)).clone());
				} else {
					this.getPropertyBean().setValue(key,
							props.getPropertyBean().getValue(key));
				}
			}
		}
	}

	@Override
	public String getPropertyStringValue(String propertyName) {
		Object o = this.getPropertyBean().getValue(propertyName);
		if(o != null){
			return o.toString();
		}
		else{
			return "";
		}
	}

	@Override
	public void setPropertyValue(String propertyName, Object value) {
		this.getPropertyBean().setValue(propertyName, value);
	}

}
