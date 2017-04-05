/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
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
