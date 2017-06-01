/**
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2012)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to help prototyping
 * parallel applications using dataflow formalism.
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
 */
package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.util.Vector;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;

// TODO: Auto-generated Javadoc
/**
 * Visitor used for loop transformation.
 *
 * @author jpiat
 * @deprecated Not intented to be used
 */
@Deprecated
public class LoopCreatorVisitor implements IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

  /**
   * Create loop pattern in the given SDFAbstractGraph.
   *
   * @param graph
   *          the graph
   * @throws InvalidExpressionException
   *           the invalid expression exception
   * @throws SDF4JException
   *           the SDF 4 J exception
   */
  public void createLoop(final SDFGraph graph) throws InvalidExpressionException, SDF4JException {
    if (graph.isSchedulable()) {
      int loopingFactor;
      boolean hasUntreatedVertex = true;
      int untreatedIndex = 0;
      final Vector<SDFAbstractVertex> loop = new Vector<>();
      final Vector<SDFAbstractVertex> treated = new Vector<>();
      SDFAbstractVertex newSource = null;
      loop.add((new Vector<>(graph.vertexSet())).get(0));
      loopingFactor = loop.get(0).getNbRepeatAsInteger();

      while (!treated.contains(loop.get(0)) || !treated.contains(loop.get(loop.size() - 1)) || hasUntreatedVertex) {
        SDFAbstractVertex newVertex;
        if (!treated.contains(loop.get(0))) {
          newVertex = loop.get(0);
        } else if (!treated.contains(loop.get(loop.size() - 1))) {
          newVertex = loop.get(loop.size() - 1);
        } else {
          newVertex = loop.get(untreatedIndex);
        }
        treated.add(newVertex);

        final int vertexVrb = newVertex.getNbRepeatAsInteger();
        for (final SDFEdge edge : graph.edgesOf(newVertex)) {
          if ((graph.getEdgeSource(edge) != newVertex) && ((graph.getEdgeSource(edge).getNbRepeatAsInteger() % vertexVrb) == 0)
              && ((graph.getEdgeSource(edge).getNbRepeatAsInteger() / vertexVrb) < (graph.getEdgeSource(edge).getNbRepeatAsInteger()))) {
            if (!loop.contains(graph.getEdgeSource(edge))) {
              loop.insertElementAt(graph.getEdgeSource(edge), loop.indexOf(newVertex));
              if (graph.getEdgeSource(edge).getNbRepeatAsInteger() < loopingFactor) {
                loopingFactor = graph.getEdgeSource(edge).getNbRepeatAsInteger();
              }
            } else {
              loop.remove(graph.getEdgeSource(edge));
              loop.insertElementAt(graph.getEdgeSource(edge), loop.indexOf(newVertex));
            }
          } else if ((graph.getEdgeTarget(edge) != newVertex) && ((graph.getEdgeTarget(edge).getNbRepeatAsInteger() % vertexVrb) == 0)
              && ((graph.getEdgeTarget(edge).getNbRepeatAsInteger() / vertexVrb) < graph.getEdgeTarget(edge).getNbRepeatAsInteger())) {
            if (!loop.contains(graph.getEdgeTarget(edge))) {
              loop.insertElementAt(graph.getEdgeTarget(edge), loop.indexOf(newVertex) + 1);
              if (graph.getEdgeTarget(edge).getNbRepeatAsInteger() < loopingFactor) {
                loopingFactor = graph.getEdgeTarget(edge).getNbRepeatAsInteger();
              }
            }
          } else if (graph.getEdgeTarget(edge) != newVertex) {
            newSource = graph.getEdgeTarget(edge);
          } else if (graph.getEdgeSource(edge) != newVertex) {
            newSource = graph.getEdgeSource(edge);
          }
        }
        if ((loop.size() == 1) && (newSource != null)) {
          treated.add(loop.get(0));
          if (!treated.contains(newSource)) {
            loop.setElementAt(newSource, 0);
            loopingFactor = loop.get(0).getNbRepeatAsInteger();
          }
          newSource = null;
        }
        hasUntreatedVertex = false;
        for (final SDFAbstractVertex inLoop : loop) {
          if (!treated.contains(inLoop)) {
            hasUntreatedVertex = true;
            untreatedIndex = loop.indexOf(inLoop);
            break;
          }
        }
      }
      if (loop.size() > 1) {
        final SDFEdge loopEdge = graph.addEdge(loop.get(loop.size() - 1), loop.get(0));
        loopEdge.setCons(new SDFIntEdgePropertyType(loop.get(loop.size() - 1).getNbRepeatAsInteger() / loop.get(0).getNbRepeatAsInteger()));
        loopEdge.setProd(new SDFIntEdgePropertyType(1));
        loopEdge.setDelay(new SDFIntEdgePropertyType(1));
        System.out.println("loop is :" + loop + " with looping factor : " + loopingFactor);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.ietr.dftools.algorithm.model.visitors.IGraphVisitor#visit(org.ietr.dftools.algorithm.model.AbstractEdge)
   */
  @Override
  public void visit(final SDFEdge sdfEdge) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.ietr.dftools.algorithm.model.visitors.IGraphVisitor#visit(org.ietr.dftools.algorithm.model.AbstractGraph)
   */
  @Override
  public void visit(final SDFGraph sdf) throws SDF4JException {
    final TopologyVisitor checkTopo = new TopologyVisitor();
    sdf.accept(checkTopo);
    try {
      createLoop(sdf);
    } catch (final InvalidExpressionException e) {
      throw (new SDF4JException(e.getMessage()));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.ietr.dftools.algorithm.model.visitors.IGraphVisitor#visit(org.ietr.dftools.algorithm.model.AbstractVertex)
   */
  @Override
  public void visit(final SDFAbstractVertex sdfVertex) throws SDF4JException {
    final TopologyVisitor checkTopo = new TopologyVisitor();
    sdfVertex.accept(checkTopo);
    if (sdfVertex.getGraphDescription() != null) {
      try {
        createLoop((SDFGraph) sdfVertex.getGraphDescription());
      } catch (final InvalidExpressionException e) {
        e.printStackTrace();
        throw (new SDF4JException(e.getMessage()));
      }
    }

  }
}
