package org.ietr.dftools.algorithm.model.listenable;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Set;

import org.ietr.dftools.algorithm.model.dag.DAGEdge;
import org.ietr.dftools.algorithm.model.dag.DAGVertex;
import org.ietr.dftools.algorithm.model.dag.DirectedAcyclicGraph;
import org.jgraph.graph.Edge;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphListener;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.event.VertexSetListener;

/**
 * Class used to represent a listenable DAG
 * 
 * @author pthebault
 * @author kdesnos
 */
public class DAGListenableGraph extends DirectedAcyclicGraph implements
		ListenableGraph<DAGVertex, DAGEdge> {

	/**
	 * A reuseable edge event.
	 * 
	 * @author Barak Naveh
	 * @since Aug 10, 2003
	 */
	private static class FlyweightEdgeEvent<VV, EE> extends
			GraphEdgeChangeEvent<VV, EE> {
		private static final long serialVersionUID = 3907207152526636089L;

		/**
		 * @see GraphEdgeChangeEvent#GraphEdgeChangeEvent(Object, int, Edge)
		 */
		private FlyweightEdgeEvent(Object eventSource, int type, EE e) {
			super(eventSource, type, e);
		}

		/**
		 * Sets the edge of this event.
		 * 
		 * @param e
		 *            the edge to be set.
		 */
		protected void setEdge(EE e) {
			this.edge = e;
		}

		/**
		 * Set the event type of this event.
		 * 
		 * @param type
		 *            the type to be set.
		 */
		protected void setType(int type) {
			this.type = type;
		}
	}

	/**
	 * A reuseable vertex event.
	 * 
	 * @author Barak Naveh
	 * @since Aug 10, 2003
	 */
	private static class FlyweightVertexEvent<VV> extends
			GraphVertexChangeEvent<VV> {
		private static final long serialVersionUID = 3257848787857585716L;

		/**
		 * @see GraphVertexChangeEvent#GraphVertexChangeEvent(Object, int,
		 *      Object)
		 */
		private FlyweightVertexEvent(Object eventSource, int type, VV vertex) {
			super(eventSource, type, vertex);
		}

		/**
		 * Set the event type of this event.
		 * 
		 * @param type
		 *            type to be set.
		 */
		protected void setType(int type) {
			this.type = type;
		}

		/**
		 * Sets the vertex of this event.
		 * 
		 * @param vertex
		 *            the vertex to be set.
		 */
		protected void setVertex(VV vertex) {
			this.vertex = vertex;
		}
	}

	/**
		 * 
		 */
	private static final long serialVersionUID = -7651455929185604666L;

	private static <L extends EventListener> void addToListenerList(
			List<L> list, L l) {
		if (!list.contains(l)) {
			list.add(l);
		}
	}

	private ArrayList<GraphListener<DAGVertex, DAGEdge>> graphListeners = new ArrayList<GraphListener<DAGVertex, DAGEdge>>();
	private FlyweightEdgeEvent<DAGVertex, DAGEdge> reuseableEdgeEvent;

	private FlyweightVertexEvent<DAGVertex> reuseableVertexEvent;

	// ~ Methods
	// ----------------------------------------------------------------

	private boolean reuseEvents;

	private ArrayList<VertexSetListener<DAGVertex>> vertexSetListeners = new ArrayList<VertexSetListener<DAGVertex>>();

	/**
	 * Creates a new DAGListenableGraph
	 */
	public DAGListenableGraph() {
		super();
	}

	public DAGEdge addEdge(DAGVertex sourceVertex, DAGVertex targetVertex) {
		DAGEdge e = super.addEdge(sourceVertex, targetVertex);

		if (e != null) {
			fireEdgeAdded(e);
		}

		return e;
	}

	/**
	 * @see Graph#addEdge(Object, Object, Object)
	 */
	public boolean addEdge(DAGVertex sourceVertex, DAGVertex targetVertex,
			DAGEdge e) {
		boolean added = super.addEdge(sourceVertex, targetVertex, e);

		if (added) {
			fireEdgeAdded(e);
		}

		return added;
	}

	/**
	 * @see Graph#addEdge(Object, Object)
	 */
	/*
	 * public DAGEdge addEdgeWithLink(DAGVertex sourceVertex, DAGVertex
	 * targetVertex) { DAGEdge e = super.addEdgeWithLink(sourceVertex,
	 * targetVertex);
	 * 
	 * if (e != null) { fireEdgeAdded(e); }
	 * 
	 * return e; } //
	 */
	/**
	 * @see ListenableGraph#addGraphListener(GraphListener)
	 */
	public void addGraphListener(GraphListener<DAGVertex, DAGEdge> l) {
		addToListenerList(graphListeners, l);
	}

	/**
	 * @see Graph#addVertex(Object)
	 */
	public boolean addVertex(DAGVertex v) {
		boolean modified = super.addVertex(v);

		if (modified) {
			fireVertexAdded(v);
		}

		return modified;
	}

	/**
	 * @see ListenableGraph#addVertexSetListener(VertexSetListener)
	 */
	public void addVertexSetListener(VertexSetListener<DAGVertex> l) {
		addToListenerList(vertexSetListeners, l);
	}

	private GraphEdgeChangeEvent<DAGVertex, DAGEdge> createGraphEdgeChangeEvent(
			int eventType, DAGEdge edge) {
		if (reuseEvents) {
			reuseableEdgeEvent.setType(eventType);
			reuseableEdgeEvent.setEdge(edge);

			return reuseableEdgeEvent;
		} else {
			return new GraphEdgeChangeEvent<DAGVertex, DAGEdge>(this,
					eventType, edge);
		}
	}

	private GraphVertexChangeEvent<DAGVertex> createGraphVertexChangeEvent(
			int eventType, DAGVertex vertex) {
		if (reuseEvents) {
			reuseableVertexEvent.setType(eventType);
			reuseableVertexEvent.setVertex(vertex);

			return reuseableVertexEvent;
		} else {
			return new GraphVertexChangeEvent<DAGVertex>(this, eventType,
					vertex);
		}
	}

	/**
	 * Notify listeners that the specified edge was added.
	 * 
	 * @param edge
	 *            the edge that was added.
	 */
	protected void fireEdgeAdded(DAGEdge edge) {
		GraphEdgeChangeEvent<DAGVertex, DAGEdge> e = createGraphEdgeChangeEvent(
				GraphEdgeChangeEvent.EDGE_ADDED, edge);

		for (int i = 0; i < graphListeners.size(); i++) {
			GraphListener<DAGVertex, DAGEdge> l = graphListeners.get(i);

			l.edgeAdded(e);
		}
	}

	/**
	 * Notify listeners that the specified edge was removed.
	 * 
	 * @param edge
	 *            the edge that was removed.
	 */
	protected void fireEdgeRemoved(DAGEdge edge) {
		GraphEdgeChangeEvent<DAGVertex, DAGEdge> e = createGraphEdgeChangeEvent(
				GraphEdgeChangeEvent.EDGE_REMOVED, edge);

		for (int i = 0; i < graphListeners.size(); i++) {
			GraphListener<DAGVertex, DAGEdge> l = graphListeners.get(i);

			l.edgeRemoved(e);
		}
	}

	/**
	 * Notify listeners that the specified vertex was added.
	 * 
	 * @param vertex
	 *            the vertex that was added.
	 */
	protected void fireVertexAdded(DAGVertex vertex) {
		GraphVertexChangeEvent<DAGVertex> e = createGraphVertexChangeEvent(
				GraphVertexChangeEvent.VERTEX_ADDED, vertex);

		for (int i = 0; i < vertexSetListeners.size(); i++) {
			VertexSetListener<DAGVertex> l = vertexSetListeners.get(i);

			l.vertexAdded(e);
		}

		for (int i = 0; i < graphListeners.size(); i++) {
			GraphListener<DAGVertex, DAGEdge> l = graphListeners.get(i);

			l.vertexAdded(e);
		}
	}

	/**
	 * Notify listeners that the specified vertex was removed.
	 * 
	 * @param vertex
	 *            the vertex that was removed.
	 */
	protected void fireVertexRemoved(DAGVertex vertex) {
		GraphVertexChangeEvent<DAGVertex> e = createGraphVertexChangeEvent(
				GraphVertexChangeEvent.VERTEX_REMOVED, vertex);

		for (int i = 0; i < vertexSetListeners.size(); i++) {
			VertexSetListener<DAGVertex> l = vertexSetListeners.get(i);

			l.vertexRemoved(e);
		}

		for (int i = 0; i < graphListeners.size(); i++) {
			GraphListener<DAGVertex, DAGEdge> l = graphListeners.get(i);

			l.vertexRemoved(e);
		}
	}

	/**
	 * Tests whether the <code>reuseEvents</code> flag is set. If the flag is
	 * set to <code>true</code> this class will reuse previously fired events
	 * and will not create a new object for each event. This option increases
	 * performance but should be used with care, especially in multithreaded
	 * environment.
	 * 
	 * @return the value of the <code>reuseEvents</code> flag.
	 */
	public boolean isReuseEvents() {
		return reuseEvents;
	}

	/**
	 * @see Graph#removeEdge(Object)
	 */
	public boolean removeEdge(DAGEdge e) {
		boolean modified = super.removeEdge(e);

		if (modified) {
			fireEdgeRemoved(e);
		}

		return modified;
	}

	/**
	 * @see Graph#removeEdge(Object, Object)
	 */
	@Deprecated
	public DAGEdge removeEdge(DAGVertex sourceVertex, DAGVertex targetVertex) {
		@SuppressWarnings("deprecation")
		DAGEdge e = super.removeEdge(sourceVertex, targetVertex);

		if (e != null) {
			fireEdgeRemoved(e);
		}

		return e;
	}

	/**
	 * @see ListenableGraph#removeGraphListener(GraphListener)
	 */
	public void removeGraphListener(GraphListener<DAGVertex, DAGEdge> l) {
		graphListeners.remove(l);
	}

	/**
	 * @see Graph#removeVertex(Object)
	 */
	public boolean removeVertex(DAGVertex v) {
		if (containsVertex(v)) {
			Set<DAGEdge> touchingEdgesList = edgesOf(v);

			// copy set to avoid ConcurrentModificationException
			removeAllEdges(new ArrayList<DAGEdge>(touchingEdgesList));

			super.removeVertex(v); // remove the vertex itself

			fireVertexRemoved(v);

			return true;
		} else {
			return false;
		}
	}

	// ~ Inner Classes
	// ----------------------------------------------------------

	/**
	 * @see ListenableGraph#removeVertexSetListener(VertexSetListener)
	 */
	public void removeVertexSetListener(VertexSetListener<DAGVertex> l) {
		vertexSetListeners.remove(l);
	}

	/**
	 * If the <code>reuseEvents</code> flag is set to <code>true</code> this
	 * class will reuse previously fired events and will not create a new object
	 * for each event. This option increases performance but should be used with
	 * care, especially in multithreaded environment.
	 * 
	 * @param reuseEvents
	 *            whether to reuse previously fired event objects instead of
	 *            creating a new event object for each event.
	 */
	public void setReuseEvents(boolean reuseEvents) {
		this.reuseEvents = reuseEvents;
	}
}
