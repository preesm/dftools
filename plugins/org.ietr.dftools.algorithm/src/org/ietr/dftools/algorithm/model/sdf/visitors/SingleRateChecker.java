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

import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;

/**
 * Checks whether a visited graph is single-rate.
 * 
 * Conditions for a graph to be single-rate are:
 * <ul>
 * <li>Each edge has an identical production and consumption rate.</li>
 * <li>Each actor has a unit coefficient in the repetition vector.</li>
 * <li>The number of delay on each edge is 0 or a multiplier of the exchange
 * rate on this edge.</li>
 * </ul>
 * 
 * @author kdesnos
 *
 */
public class SingleRateChecker implements IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	public boolean isSingleRate = true;

	@Override
	public void visit(SDFEdge sdfEdge) {
		try {
			isSingleRate &= sdfEdge.getCons().intValue() == sdfEdge.getProd().intValue();
			isSingleRate &= ((sdfEdge.getDelay().intValue() % sdfEdge.getCons().intValue()) == 0);
		} catch (InvalidExpressionException e) {
			// Supposedly, will not happen, expressions were already parsed when
			// verifying actors number of repetition.
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void visit(SDFGraph sdf) throws SDF4JException {
		// Visit vertices
		for (SDFAbstractVertex vertex : sdf.vertexSet()) {
			vertex.accept(this);
		}

		// Visit edges
		for (SDFEdge edge : sdf.edgeSet()) {
			edge.accept(this);
		}

	}

	@Override
	public void visit(SDFAbstractVertex sdfVertex) throws SDF4JException {
		// Check number of repetitions
		try {
			isSingleRate &= (sdfVertex.getNbRepeatAsInteger() == 1);
		} catch (InvalidExpressionException e) {
			throw new SDF4JException(e.getMessage());
		}
	}
}
