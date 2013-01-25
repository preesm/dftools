package net.sf.dftools.algorithm.iterators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;

import org.jgrapht.event.TraversalListener;
import org.jgrapht.traverse.GraphIterator;

/**
 * Class used to iterate over a SDF following the dependencies order
 * 
 * @author jpiat
 * @author kdesnos
 * 
 */
public class SDFIterator implements GraphIterator<SDFAbstractVertex, SDFEdge> {

	private SDFGraph graph;
	private ArrayList<SDFAbstractVertex> stack;
	private Vector<SDFAbstractVertex> treated;

	/**
	 * Creates a new SDFIterator on the given SDFGraph
	 * 
	 * @param graph
	 *            The graph to iterate over
	 * @throws InvalidExpressionException
	 */
	public SDFIterator(SDFGraph graph) throws InvalidExpressionException {
		this.graph = graph;
		stack = new ArrayList<SDFAbstractVertex>();
		treated = new Vector<SDFAbstractVertex>();
		ArrayList<SDFAbstractVertex> treatedOrig = new ArrayList<SDFAbstractVertex>();
		treatedOrig.addAll(graph.vertexSet());
		for (int i = 0; i < treatedOrig.size(); i++) {
			SDFAbstractVertex vertex = treatedOrig.get(i);
			List<SDFAbstractVertex> origs = originOf(vertex, treatedOrig);
			for (SDFAbstractVertex orig : origs) {
				if (!stack.contains(orig)) {
					stack.add(orig);
				}
			}
		}
		System.out.println(stack);

		// Check if all vertices are reachable through this iterator
		// First, backup the stack
		ArrayList<SDFAbstractVertex> stackBackup = new ArrayList<SDFAbstractVertex>(
				stack);
		// Then iterate
		Set<SDFAbstractVertex> reached = new HashSet<SDFAbstractVertex>();
		while (this.hasNext()) {
			reached.add(this.next());
		}

		// Check if all vertices were reached
		if (reached.size() != graph.vertexSet().size()) {
			// Find the non-reacheable vertices
			List<SDFAbstractVertex> unreachable = new ArrayList<SDFAbstractVertex>(
					graph.vertexSet());
			unreachable.removeAll(reached);
			throw new RuntimeException(
					"Not all graph vertices are reachable with the SDFIterator.\n"
							+ "Possible cause: There is a cycle without delay.\n"
							+ " Unreached Vertices: " + unreachable);
		}

		// If the check was successful, restore the backed-up stack and clean
		// treated
		stack = stackBackup;
		treated = new Vector<SDFAbstractVertex>();
	}

	/**
	 * Creates a new graph iterator that iterates over the given graph, starting
	 * from the given seed
	 * 
	 * @param graph
	 *            The graph to iterate
	 * @param seed
	 *            The starting point of the iterator
	 */
	public SDFIterator(SDFGraph graph, SDFAbstractVertex seed) {
		this.graph = graph;
		stack = new ArrayList<SDFAbstractVertex>();
		treated = new Vector<SDFAbstractVertex>();
		stack.add(seed);
		System.out.println(stack);
	}

	@Override
	public void addTraversalListener(
			TraversalListener<SDFAbstractVertex, SDFEdge> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasNext() {
		if (stack.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isCrossComponentTraversal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReuseEvents() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SDFAbstractVertex next() {
		try {
			// If the iterator has a next
			if (hasNext()) {
				// Get the returned value from the stack
				SDFAbstractVertex next = stack.get(0);
				// Add it to the list of already treated vertices (so as not to
				// "treat" it twice)
				treated.add(next);

				// Check if the current vertex has a successor that was not yet
				// treated.
				Set<SDFEdge> outgoingEdges = graph.outgoingEdgesOf(next);
				for (SDFEdge edge : outgoingEdges) {
					// If the current outgoingEdge is not a self loop on the
					// current vertex
					if (graph.getEdgeTarget(edge) != next) {
						// Boolean indicating if all predecessors of the target
						// of the current edge were previously treated (in which
						// case the target of the current edge must be added to
						// the stack).
						boolean prevTreated = true;
						SDFAbstractVertex fol = graph.getEdgeTarget(edge);
						// Check if all predecessors of the target of the
						// current edge were already treated
						for (SDFEdge incomingEdge : graph.incomingEdgesOf(fol)) {
							// Ignore the incomingEdge if this is a self loop or
							// the edge coming from the current vertex (i.e. the
							// returned vertex)
							if (graph.getEdgeSource(incomingEdge) != fol
									&& graph.getEdgeSource(incomingEdge) != next) {
								// prevTreated stays true if:
								// The source of the incomingEdge has already
								// been treated OR
								// The delay of the incomingEdge is greater or
								// equal to the consumption rate of this edge
								prevTreated = prevTreated
										&& ((treated.contains(graph
												.getEdgeSource(incomingEdge))) || incomingEdge
												.getDelay().intValue() >= incomingEdge
												.getCons().intValue());
							}
						}
						if (prevTreated && !treated.contains(fol)
								&& !stack.contains(fol)) {
							stack.add(fol);
						}
					}
				}
				stack.remove(0);
				return next;
			}
		} catch (InvalidExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This recursive methods search the origin for a given
	 * {@link SDFAbstractVertex vertex}. Finding the "origin" of a vertex
	 * consist in searching recursively the origin of all predecessors of the
	 * given {@link SDFAbstractVertex vertex} until a {@link SDFAbstractVertex
	 * vertex} with no predecessor is found. One {@link SDFAbstractVertex} may
	 * have one or several "origins"
	 * 
	 * @param vertex
	 *            the {@link SDFAbstractVertex} whose origins are searched
	 * @param notTreated
	 *            the list of not treated {@link SDFAbstractVertex vertices}
	 *            (i.e. {@link SDFAbstractVertex vertices} not yet encountered
	 *            in recursive calls)
	 * @return list of {@link SDFAbstractVertex vertices} that are at the origin
	 *         of the given {@link SDFAbstractVertex vertex}.
	 * @throws InvalidExpressionException
	 */
	private List<SDFAbstractVertex> originOf(SDFAbstractVertex vertex,
			List<SDFAbstractVertex> notTreated)
			throws InvalidExpressionException {
		List<SDFAbstractVertex> origins = new ArrayList<SDFAbstractVertex>();
		int added = 0;
		// Scan the predecessor of the current vertex (if any)
		for (SDFEdge edge : graph.incomingEdgesOf(vertex)) {
			// If the current edge is not a self-loop and has an insufficient
			// delay to be a source
			if (graph.getEdgeSource(edge) != vertex
					&& edge.getDelay().intValue() < edge.getCons().intValue()) {
				// Then the current vertex is NOT an "origin", call originOf on
				// its the current predecessor.
				// If the predecessor was not yet encountered in recursive calls
				// to originOf.
				if (notTreated.contains(graph.getEdgeSource(edge))) {
					notTreated.remove(graph.getEdgeSource(edge));

					added++;
					List<SDFAbstractVertex> predecessorOrigins = originOf(
							graph.getEdgeSource(edge), notTreated);

					// Add the origins of the predecessor to the origins of the
					// current vertex.
					for (SDFAbstractVertex origin : predecessorOrigins) {
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

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTraversalListener(
			TraversalListener<SDFAbstractVertex, SDFEdge> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReuseEvents(boolean arg0) {
		// TODO Auto-generated method stub

	}

}
