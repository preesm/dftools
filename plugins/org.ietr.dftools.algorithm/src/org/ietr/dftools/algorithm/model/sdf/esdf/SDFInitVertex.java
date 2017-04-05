package org.ietr.dftools.algorithm.model.sdf.esdf;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;

/**
 * Special vertex to initialize data on looping edges
 * @author jpiat
 *
 */
public class SDFInitVertex extends SDFAbstractVertex{
	
	/**
	 * Kind of node
	 */
	public static final String INIT ="init"; 
	
	public static final String END_REFERENCE ="END_REFERENCE"; 
	public static final String INIT_SIZE ="INIT_SIZE"; 
	
	/**
	 * Creates a new SDFInterfaceVertex with the default direction (SINK)
	 */
	public SDFInitVertex() {
		super();
		setKind(INIT);
		setNbRepeat(1);
	}

	@Override
	public SDFAbstractVertex clone() {
		SDFInitVertex init = new SDFInitVertex();
		init.setName(this.getName());
		return init ;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionAdded(AbstractEdge e) {
		// Nothing to do for the moment
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionRemoved(AbstractEdge e) {
		// Nothing to do for the moment
	}
	
	public void setEndReference(SDFInitVertex ref){
		this.getPropertyBean().setValue(END_REFERENCE, ref);
	}
	
	public SDFInitVertex getEndReference(){
		return (SDFInitVertex) this.getPropertyBean().getValue(END_REFERENCE);
	}
	
	public void setInitSize(int size){
		this.getPropertyBean().setValue(INIT_SIZE, size);
	}
	
	public int getInitSize(){
		return (Integer) this.getPropertyBean().getValue(INIT_SIZE);
	}

}
