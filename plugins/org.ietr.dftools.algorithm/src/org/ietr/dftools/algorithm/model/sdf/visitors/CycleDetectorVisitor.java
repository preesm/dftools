/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2011 - 2012)
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

import java.util.Vector;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.jgrapht.alg.CycleDetector;

/**
 * Visitor to use to detect cycle in a hierarchical graph
 *
 * @author jpiat
 *
 */
public class CycleDetectorVisitor implements IGraphVisitor<SDFGraph, SDFVertex, SDFEdge> {

	private final Vector<SDFGraph>	containsCycles	= new Vector<>();
	private boolean					hasCycle		= true;

	/**
	 * Detect cycles in the given graph
	 *
	 * @param graph
	 *            The graph to visit
	 * @return true if the graph has cycles
	 */
	public boolean detectCyles(final SDFGraph graph) {
		try {
			graph.accept(this);
		} catch (final SDF4JException e) {
			e.printStackTrace();
			return false;
		}
		return this.hasCycle;
	}

	@Override
	public void visit(final SDFEdge sdfEdge) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final SDFGraph sdf) throws SDF4JException {
		boolean hasCycle;
		final CycleDetector<SDFAbstractVertex, SDFEdge> detector = new CycleDetector<>(sdf);
		hasCycle = detector.detectCycles();
		if (hasCycle) {
			this.containsCycles.add(sdf);
		}
		this.hasCycle = this.hasCycle && hasCycle;
		for (final SDFAbstractVertex vertex : sdf.vertexSet()) {
			vertex.accept(this);
		}
	}

	@Override
	public void visit(final SDFVertex sdfVertex) throws SDF4JException {
		if (sdfVertex.getGraphDescription() != null) {
			sdfVertex.getGraphDescription().accept(this);
		}
	}

}
