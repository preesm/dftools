package net.sf.dftools.algorithm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.sf.dftools.algorithm.model.sdf.visitors.GraphVisitor;

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

	public List<String> getPublicProperties() {
		return public_properties;
	}

	/**
	 * @param visitor
	 *            The visitor to accept
	 */
	public void accept(GraphVisitor visitor) {
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
		if(getPropertyBean().getValue(SOURCE_PORT) != null){
			return (String) getPropertyBean().getValue(SOURCE_PORT).toString();
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
		if(getPropertyBean().getValue(TARGET_PORT) != null){
			return (String) getPropertyBean().getValue(TARGET_PORT).toString();
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
	
	
	public String getPropertyStringValue(String propertyName){
		return this.getPropertyBean().getValue(propertyName).toString();
	}

}
