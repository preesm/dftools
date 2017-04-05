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
package org.ietr.dftools.algorithm.model.sdf;

import org.ietr.dftools.algorithm.model.AbstractEdge;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;


/**
 * Class used to represent Hierachical vertices, meaning it is a vertex in the
 * parent graph, but is itself a graph
 * 
 * @author jpiat
 * 
 */
public class SDFVertex extends SDFAbstractVertex {
	
	/**
	 * Property memory script
	 */
	public static final String MEMORY_SCRIPT = "memory_script";
	
	static {
		{
			public_properties.add(MEMORY_SCRIPT);
		}
	};

	/**
	 * 
	 */
	protected static final long serialVersionUID = -4281714330859590518L;
	
	/**
	 * Kind of node
	 */
	public static final String VERTEX ="vertex"; 


	/**
	 * Builds a new vertex
	 */
	public SDFVertex() {
		super();
		setKind(VERTEX);
	}

	/**
	 * Constructs a new SDFVertex given its base graph arg0
	 * 
	 * @param arg0
	 */
	public SDFVertex(SDFGraph arg0) {
		super();
		setKind(VERTEX);
		setBase(arg0);
	}



	/**
	 * Clone the vertex
	 */
	@Override
	public SDFVertex clone() {
		SDFVertex newVertex = new SDFVertex(null);
		for (String key : this.getPropertyBean().keys()) {
			if (this.getPropertyBean().getValue(key) != null) {
				Object val = this.getPropertyBean().getValue(key);
				newVertex.getPropertyBean().setValue(key, val);
			} 
		}
		// Copy refinement properties
		newVertex.setRefinement(this.getRefinement());
		for (SDFInterfaceVertex sink : this.getSinks()) {
			if (newVertex.getGraphDescription() != null
					&& newVertex.getGraphDescription().getVertex(sink.getName()) != null) {
				newVertex.addSink((SDFInterfaceVertex) this.getGraphDescription().getVertex(sink.getName()));
			}else{
				newVertex.addSink(sink.clone());
			}
		}
		for (SDFInterfaceVertex source : this.getSources()) {
			if (newVertex.getGraphDescription() != null
					&& newVertex.getGraphDescription().getVertex(source.getName()) != null) {
				newVertex.addSource((SDFInterfaceVertex) this.getGraphDescription().getVertex(source.getName()));
			}else{
				newVertex.addSource(source.clone());
			}
		}
		try {
			newVertex.setNbRepeat(this.getNbRepeat());
		} catch (InvalidExpressionException e) {
			e.printStackTrace();
		}
		
		return newVertex;
	}



	@Override
	public void connectionAdded(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionRemoved(AbstractEdge<?, ?> e) {
		// TODO Auto-generated method stub
		
	}



}
