/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2012)
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
package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.util.logging.Level;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.ietr.dftools.algorithm.model.visitors.VisitorOutput;

/**
 * Verifies that graph doesn't contains mistakes (port mismatch, case
 * sensitivity)
 * 
 * @author jpiat
 * 
 */
public class ConsistencyChecker implements
		IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	private boolean isConsistent;

	@Override
	public void visit(SDFEdge sdfEdge) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SDFGraph sdf) throws SDF4JException {
		for (SDFAbstractVertex vertex : sdf.vertexSet()) {
			vertex.accept(this);
		}

	}

	@Override
	public void visit(SDFAbstractVertex sdfVertex) throws SDF4JException {
		SDFGraph graphDescription = (SDFGraph) sdfVertex.getGraphDescription();
		SDFGraph base = (SDFGraph) sdfVertex.getBase();
		if (graphDescription != null) {
			for (SDFEdge edge : base.incomingEdgesOf(sdfVertex)) {
				if (graphDescription.getVertex(edge.getTargetInterface()
						.getName()) == null) {
					VisitorOutput.getLogger().log(
							Level.SEVERE,
							"Interface " + edge.getTargetInterface().getName()
									+ " does not exist in vertex "
									+ sdfVertex.getName() + " hierarchy");
					isConsistent &= false;
				} else if (graphDescription.getVertex(edge.getTargetInterface()
						.getName()) != null) {
					SDFAbstractVertex sourceNode = graphDescription
							.getVertex(edge.getTargetInterface().getName());
					if (graphDescription.outgoingEdgesOf(sourceNode).size() == 0) {
						VisitorOutput
								.getLogger()
								.log(Level.SEVERE,
										"Interface "
												+ edge.getTargetInterface()
														.getName()
												+ " does not exist, or is not connect in vertex "
												+ sdfVertex.getName()
												+ " hierarchy");
						isConsistent &= false;
					}
				}
			}
			for (SDFEdge edge : base.outgoingEdgesOf(sdfVertex)) {
				if (graphDescription.getVertex(edge.getSourceInterface()
						.getName()) == null) {
					VisitorOutput.getLogger().log(
							Level.SEVERE,
							"Interface " + edge.getSourceInterface().getName()
									+ " does not exist in vertex "
									+ sdfVertex.getName() + " hierarchy");
					isConsistent &= false;
				} else if (graphDescription.getVertex(edge.getSourceInterface()
						.getName()) != null) {
					SDFAbstractVertex sinkNode = graphDescription
							.getVertex(edge.getSourceInterface().getName());
					if (graphDescription.incomingEdgesOf(sinkNode).size() == 0) {
						VisitorOutput
								.getLogger()
								.log(Level.SEVERE,
										"Interface "
												+ edge.getSourceInterface()
														.getName()
												+ " does not exist, or is not connect in vertex "
												+ sdfVertex.getName()
												+ " hierarchy");
						isConsistent &= false;
					}
				}
			}
			graphDescription.accept(this);
		}
	}

	/**
	 * Verify a given graph consistency
	 * 
	 * @param toVerify
	 *            The graph on which to verify consistency
	 * @return True if the graph is consistent, false otherwise
	 */
	public boolean verifyGraph(SDFGraph toVerify) {
		isConsistent = true;
		try {
			toVerify.accept(this);
		} catch (SDF4JException e) {
			e.printStackTrace();
			return false;
		}
		return isConsistent;
	}

}
