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
package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.ietr.dftools.algorithm.model.visitors.VisitorOutput;


/**
 * Visitor used to determine whether a graph is schedulable or not
 * 
 * @author jpiat
 * @author jheulot
 * 
 */
public class TopologyVisitor implements IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	boolean schedulable = true ;

	/**
	 * Gives the result of the schedulability test
	 * @return True if the graph is schedulable, false if not
	 */
	public boolean result(){
		return schedulable ;
	}
	
	@Override
	public void visit(SDFEdge sdfEdge) {

	}

	@Override
	public void visit(SDFGraph sdf) throws SDF4JException{
		List<SDFAbstractVertex> vertices = new ArrayList<SDFAbstractVertex>();
		for (SDFAbstractVertex vertex : sdf.vertexSet()) {
			if(!(vertex instanceof SDFInterfaceVertex)){
				vertex.accept(this);
				vertices.add(vertex);
			}
			
		}
		if(sdf.isSchedulable()){
			schedulable &= true ;
		} else {
			schedulable &= false ;
			VisitorOutput.getLogger().log(Level.SEVERE, "Graph "+sdf.getName()+" is not schedulable");
		}
	}

	@Override
	public void visit(SDFAbstractVertex sdfVertex) throws SDF4JException{
		if(sdfVertex.getGraphDescription() != null){
			sdfVertex.getGraphDescription().accept(this);
		}
	}

}
