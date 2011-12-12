package net.sf.dftools.algorithm.iterators;

import java.util.ArrayList;
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
	 *            THe graph to iterate over
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
		try{
		if (hasNext()) {
			SDFAbstractVertex next = stack.get(0);
			treated.add(next);
			Set<SDFEdge> outgoingEdges = graph.outgoingEdgesOf(next);
			for (SDFEdge edge : outgoingEdges) {
				if (graph.getEdgeTarget(edge) != next) {
					boolean prevTreated = true;
					SDFAbstractVertex fol = graph.getEdgeTarget(edge);
					for (SDFEdge incomingEdge : graph.incomingEdgesOf(fol)) {
						if (graph.getEdgeSource(incomingEdge) != fol
								&& graph.getEdgeSource(incomingEdge) != next) {
							prevTreated = prevTreated
									&& ((treated.contains(graph
											.getEdgeSource(incomingEdge))) || incomingEdge
											.getDelay().intValue() >= incomingEdge
											.getCons().intValue());
						}
					}
					if (prevTreated && !treated.contains(fol) && !stack.contains(fol)) {
						stack.add(fol);
					}
				}
			}
			stack.remove(0);
			return next;
		}
		}catch(InvalidExpressionException e){
			e.printStackTrace();
		}
		return null;
	}

	private List<SDFAbstractVertex> originOf(SDFAbstractVertex origin,
			List<SDFAbstractVertex> treated) throws InvalidExpressionException {
		List<SDFAbstractVertex> previous = new ArrayList<SDFAbstractVertex>();
		int added = 0;
		for (SDFEdge edge : graph.incomingEdgesOf(origin)) {
			if (treated.contains(graph.getEdgeSource(edge))) {
				treated.remove(graph.getEdgeSource(edge));
				if (graph.getEdgeSource(edge) != origin
						&& edge.getDelay().intValue() == 0) {
					added++;
					List<SDFAbstractVertex> prevOrig = originOf(graph
							.getEdgeSource(edge), treated);
					for (SDFAbstractVertex vertex : prevOrig) {
						if (!previous.contains(vertex)) {
							previous.add(vertex);
						}
					}
				}
			} else {
				added++;
			}
		}
		if (added == 0) {
			treated.remove(origin);
			if (!previous.contains(origin)) {
				previous.add(origin);
			}
		}
		return previous;
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
