/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Jonathan Piat <jpiat@laas.fr> (2011)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2013)
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
package org.ietr.dftools.algorithm.iterators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.traverse.GraphIterator;

// TODO: Auto-generated Javadoc
/**
 * Class used to iterate over a SDF following the dependencies order.
 *
 * @author jpiat
 * @author kdesnos
 */
public class SDFIterator implements GraphIterator<SDFAbstractVertex, SDFEdge> {

  /** The graph. */
  private final SDFGraph graph;

  /** The stack. */
  private ArrayList<SDFAbstractVertex> stack;

  /** The treated. */
  private Vector<SDFAbstractVertex> treated;

  /**
   * Creates a new SDFIterator on the given SDFGraph.
   *
   * @param graph
   *          The graph to iterate over
   * @throws InvalidExpressionException
   *           the invalid expression exception
   * @throws RuntimeException
   *           the runtime exception
   */
  public SDFIterator(final SDFGraph graph) throws InvalidExpressionException, RuntimeException {
    this.graph = graph;
    this.stack = new ArrayList<>();
    this.treated = new Vector<>();
    final ArrayList<SDFAbstractVertex> treatedOrig = new ArrayList<>();
    treatedOrig.addAll(graph.vertexSet());
    for (int i = 0; i < treatedOrig.size(); i++) {
      final SDFAbstractVertex vertex = treatedOrig.get(i);
      final List<SDFAbstractVertex> origs = originOf(vertex, treatedOrig);
      for (final SDFAbstractVertex orig : origs) {
        if (!this.stack.contains(orig)) {
          this.stack.add(orig);
        }
      }
    }
    System.out.println(this.stack);

    // Check if all vertices are reachable through this iterator
    // First, backup the stack
    final ArrayList<SDFAbstractVertex> stackBackup = new ArrayList<>(this.stack);
    // Then iterate
    final Set<SDFAbstractVertex> reached = new HashSet<>();
    while (hasNext()) {
      reached.add(next());
    }

    // Check if all vertices were reached
    if (reached.size() != graph.vertexSet().size()) {
      // Find the non-reacheable vertices
      final List<SDFAbstractVertex> unreachable = new ArrayList<>(graph.vertexSet());
      unreachable.removeAll(reached);
      throw new RuntimeException("Not all graph vertices are reachable with the SDFIterator.\n" + "Possible cause: There is a cycle without delay.\n"
          + "Unreachable Vertices: " + unreachable);
    }

    // If the check was successful, restore the backed-up stack and clean
    // treated
    this.stack = stackBackup;
    this.treated = new Vector<>();
  }

  /**
   * Creates a new graph iterator that iterates over the given graph, starting from the given seed.
   *
   * @param graph
   *          The graph to iterate
   * @param seed
   *          The starting point of the iterator
   */
  public SDFIterator(final SDFGraph graph, final SDFAbstractVertex seed) {
    this.graph = graph;
    this.stack = new ArrayList<>();
    this.treated = new Vector<>();
    this.stack.add(seed);
    System.out.println(this.stack);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jgrapht.traverse.GraphIterator#addTraversalListener(org.jgrapht.event.TraversalListener)
   */
  @Override
  public void addTraversalListener(final TraversalListener<SDFAbstractVertex, SDFEdge> arg0) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext() {
    if (this.stack.size() == 0) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jgrapht.traverse.GraphIterator#isCrossComponentTraversal()
   */
  @Override
  public boolean isCrossComponentTraversal() {
    // TODO Auto-generated method stub
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jgrapht.traverse.GraphIterator#isReuseEvents()
   */
  @Override
  public boolean isReuseEvents() {
    // TODO Auto-generated method stub
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#next()
   */
  @Override
  public SDFAbstractVertex next() {
    try {
      // If the iterator has a next
      if (hasNext()) {
        // Get the returned value from the stack
        final SDFAbstractVertex next = this.stack.get(0);
        // Add it to the list of already treated vertices (so as not to
        // "treat" it twice)
        this.treated.add(next);

        // Check if the current vertex has a successor that was not yet
        // treated.
        final Set<SDFEdge> outgoingEdges = this.graph.outgoingEdgesOf(next);
        for (final SDFEdge edge : outgoingEdges) {
          // If the current outgoingEdge is not a self loop on the
          // current vertex
          if (this.graph.getEdgeTarget(edge) != next) {
            // Boolean indicating if all predecessors of the target
            // of the current edge were previously treated (in which
            // case the target of the current edge must be added to
            // the stack).
            boolean prevTreated = true;
            final SDFAbstractVertex fol = this.graph.getEdgeTarget(edge);
            // Check if all predecessors of the target of the
            // current edge were already treated
            for (final SDFEdge incomingEdge : this.graph.incomingEdgesOf(fol)) {
              // Ignore the incomingEdge if this is a self loop or
              // the edge coming from the current vertex (i.e. the
              // returned vertex)
              if ((this.graph.getEdgeSource(incomingEdge) != fol) && (this.graph.getEdgeSource(incomingEdge) != next)) {
                // prevTreated stays true if:
                // The source of the incomingEdge has already
                // been treated OR
                // The delay of the incomingEdge is greater or
                // equal to the consumption rate of this edge
                prevTreated = prevTreated && ((this.treated.contains(this.graph.getEdgeSource(incomingEdge)))
                    || (incomingEdge.getDelay().intValue() >= incomingEdge.getCons().intValue()));
              }
            }
            if (prevTreated && !this.treated.contains(fol) && !this.stack.contains(fol)) {
              this.stack.add(fol);
            }
          }
        }
        this.stack.remove(0);
        return next;
      }
    } catch (final InvalidExpressionException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * This recursive methods search the origin for a given {@link SDFAbstractVertex vertex}. Finding the "origin" of a vertex consist in searching recursively
   * the origin of all predecessors of the given {@link SDFAbstractVertex vertex} until a {@link SDFAbstractVertex vertex} with no predecessor is found. One
   * {@link SDFAbstractVertex} may have one or several "origins"
   *
   * @param vertex
   *          the {@link SDFAbstractVertex} whose origins are searched
   * @param notTreated
   *          the list of not treated {@link SDFAbstractVertex vertices} (i.e. {@link SDFAbstractVertex vertices} not yet encountered in recursive calls)
   * @return list of {@link SDFAbstractVertex vertices} that are at the origin of the given {@link SDFAbstractVertex vertex}.
   * @throws InvalidExpressionException
   *           the invalid expression exception
   */
  private List<SDFAbstractVertex> originOf(final SDFAbstractVertex vertex, final List<SDFAbstractVertex> notTreated) throws InvalidExpressionException {
    final List<SDFAbstractVertex> origins = new ArrayList<>();
    int added = 0;
    // Scan the predecessor of the current vertex (if any)
    for (final SDFEdge edge : this.graph.incomingEdgesOf(vertex)) {
      // If the current edge is not a self-loop and has an insufficient
      // delay to be a source
      if ((this.graph.getEdgeSource(edge) != vertex) && (edge.getDelay().intValue() < edge.getCons().intValue())) {
        // Then the current vertex is NOT an "origin", call originOf on
        // its the current predecessor.
        // If the predecessor was not yet encountered in recursive calls
        // to originOf.
        if (notTreated.contains(this.graph.getEdgeSource(edge))) {
          notTreated.remove(this.graph.getEdgeSource(edge));

          added++;
          final List<SDFAbstractVertex> predecessorOrigins = originOf(this.graph.getEdgeSource(edge), notTreated);

          // Add the origins of the predecessor to the origins of the
          // current vertex.
          for (final SDFAbstractVertex origin : predecessorOrigins) {
            if (!origins.contains(origin)) {
              origins.add(origin);
            }
          }

        } else {
          // The predecessor was already encountered in recursive
          // calls to originOf
          // ignore it but increment added to know that the current
          // vertex is not an origin
          added++;
        }
      }
    }

    // If added is still equal to 0 after scanning all predecessors of the
    // vertex, this means that the current vertex is an origin
    if (added == 0) {
      notTreated.remove(vertex);
      if (!origins.contains(vertex)) { // Probably useless check
        origins.add(vertex);
      }
    }
    return origins;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jgrapht.traverse.GraphIterator#remove()
   */
  @Override
  public void remove() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jgrapht.traverse.GraphIterator#removeTraversalListener(org.jgrapht.event.TraversalListener)
   */
  @Override
  public void removeTraversalListener(final TraversalListener<SDFAbstractVertex, SDFEdge> arg0) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jgrapht.traverse.GraphIterator#setReuseEvents(boolean)
   */
  @Override
  public void setReuseEvents(final boolean arg0) {
    // TODO Auto-generated method stub

  }

}
