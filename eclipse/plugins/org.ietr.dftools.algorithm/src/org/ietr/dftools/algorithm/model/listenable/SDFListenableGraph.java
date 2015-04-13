package org.ietr.dftools.algorithm.model.listenable;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Set;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphListener;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.event.VertexSetListener;

/**
 * Class used to represent a listenable SDFGraph
 * 
 * @author jpiat
 * @author kdesnos
 */
public class SDFListenableGraph extends SDFGraph implements
		ListenableGraph<SDFAbstractVertex, SDFEdge> {

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

	private ArrayList<GraphListener<SDFAbstractVertex, SDFEdge>> graphListeners = new ArrayList<GraphListener<SDFAbstractVertex, SDFEdge>>();
	private FlyweightEdgeEvent<SDFAbstractVertex, SDFEdge> reuseableEdgeEvent;

	private FlyweightVertexEvent<SDFAbstractVertex> reuseableVertexEvent;

	// ~ Methods
	// ----------------------------------------------------------------

	private boolean reuseEvents;

	private ArrayList<VertexSetListener<SDFAbstractVertex>> vertexSetListeners = new ArrayList<VertexSetListener<SDFAbstractVertex>>();

	/**
	 * Creates a new SDFListenableGraph
	 */
	public SDFListenableGraph() {
		super();
	}

	@Override
	public SDFEdge addEdge(SDFAbstractVertex sourceVertex,
			SDFAbstractVertex targetVertex) {
		SDFEdge e = super.addEdge(sourceVertex, targetVertex);

		if (e != null) {
			fireEdgeAdded(e);
		}

		return e;
	}

	/**
	 * @see Graph#addEdge(Object, Object, Object)
	 */
	@Override
	public boolean addEdge(SDFAbstractVertex sourceVertex,
			SDFAbstractVertex targetVertex, SDFEdge e) {
		boolean added = super.addEdge(sourceVertex, targetVertex, e);

		if (added) {
			fireEdgeAdded(e);
		}

		return added;
	}

	/**
	 * @see Graph#addEdge(Object, Object)
	 */
	@Override
	public SDFEdge addEdgeWithInterfaces(SDFAbstractVertex sourceVertex,
			SDFAbstractVertex targetVertex) {
		SDFEdge e = super.addEdgeWithInterfaces(sourceVertex, targetVertex);

		if (e != null) {
			fireEdgeAdded(e);
		}

		return e;
	}

	/**
	 * @see ListenableGraph#addGraphListener(GraphListener)
	 */
	@Override
	public void addGraphListener(GraphListener<SDFAbstractVertex, SDFEdge> l) {
		addToListenerList(graphListeners, l);
	}

	/**
	 * @see Graph#addVertex(Object)
	 */
	@Override
	public boolean addVertex(SDFAbstractVertex v) {
		boolean modified = super.addVertex(v);

		if (modified) {
			fireVertexAdded(v);
		}

		return modified;
	}

	/**
	 * @see ListenableGraph#addVertexSetListener(VertexSetListener)
	 */
	@Override
	public void addVertexSetListener(VertexSetListener<SDFAbstractVertex> l) {
		addToListenerList(vertexSetListeners, l);
	}

	private GraphEdgeChangeEvent<SDFAbstractVertex, SDFEdge> createGraphEdgeChangeEvent(
			int eventType, SDFEdge edge) {
		if (reuseEvents) {
			reuseableEdgeEvent.setType(eventType);
			reuseableEdgeEvent.setEdge(edge);

			return reuseableEdgeEvent;
		} else {
			return new GraphEdgeChangeEvent<SDFAbstractVertex, SDFEdge>(this,
					eventType, edge);
		}
	}

	private GraphVertexChangeEvent<SDFAbstractVertex> createGraphVertexChangeEvent(
			int eventType, SDFAbstractVertex vertex) {
		if (reuseEvents) {
			reuseableVertexEvent.setType(eventType);
			reuseableVertexEvent.setVertex(vertex);

			return reuseableVertexEvent;
		} else {
			return new GraphVertexChangeEvent<SDFAbstractVertex>(this,
					eventType, vertex);
		}
	}

	/**
	 * Notify listeners that the specified edge was added.
	 * 
	 * @param edge
	 *            the edge that was added.
	 */
	protected void fireEdgeAdded(SDFEdge edge) {
		GraphEdgeChangeEvent<SDFAbstractVertex, SDFEdge> e = createGraphEdgeChangeEvent(
				GraphEdgeChangeEvent.EDGE_ADDED, edge);

		for (int i = 0; i < graphListeners.size(); i++) {
			GraphListener<SDFAbstractVertex, SDFEdge> l = graphListeners.get(i);

			l.edgeAdded(e);
		}
	}

	/**
	 * Notify listeners that the specified edge was removed.
	 * 
	 * @param edge
	 *            the edge that was removed.
	 */
	protected void fireEdgeRemoved(SDFEdge edge) {
		GraphEdgeChangeEvent<SDFAbstractVertex, SDFEdge> e = createGraphEdgeChangeEvent(
				GraphEdgeChangeEvent.EDGE_REMOVED, edge);

		for (int i = 0; i < graphListeners.size(); i++) {
			GraphListener<SDFAbstractVertex, SDFEdge> l = graphListeners.get(i);

			l.edgeRemoved(e);
		}
	}

	/**
	 * Notify listeners that the specified vertex was added.
	 * 
	 * @param vertex
	 *            the vertex that was added.
	 */
	protected void fireVertexAdded(SDFAbstractVertex vertex) {
		GraphVertexChangeEvent<SDFAbstractVertex> e = createGraphVertexChangeEvent(
				GraphVertexChangeEvent.VERTEX_ADDED, vertex);

		for (int i = 0; i < vertexSetListeners.size(); i++) {
			VertexSetListener<SDFAbstractVertex> l = vertexSetListeners.get(i);

			l.vertexAdded(e);
		}

		for (int i = 0; i < graphListeners.size(); i++) {
			GraphListener<SDFAbstractVertex, SDFEdge> l = graphListeners.get(i);

			l.vertexAdded(e);
		}
	}

	/**
	 * Notify listeners that the specified vertex was removed.
	 * 
	 * @param vertex
	 *            the vertex that was removed.
	 */
	protected void fireVertexRemoved(SDFAbstractVertex vertex) {
		GraphVertexChangeEvent<SDFAbstractVertex> e = createGraphVertexChangeEvent(
				GraphVertexChangeEvent.VERTEX_REMOVED, vertex);

		for (int i = 0; i < vertexSetListeners.size(); i++) {
			VertexSetListener<SDFAbstractVertex> l = vertexSetListeners.get(i);

			l.vertexRemoved(e);
		}

		for (int i = 0; i < graphListeners.size(); i++) {
			GraphListener<SDFAbstractVertex, SDFEdge> l = graphListeners.get(i);

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
	 * @see Graph#removeEdge(Object, Object)
	 */
	@Override
	@Deprecated
	public SDFEdge removeEdge(SDFAbstractVertex sourceVertex,
			SDFAbstractVertex targetVertex) {
		checkMultipleEdges(sourceVertex, targetVertex);
		SDFEdge e = super.removeEdge(sourceVertex, targetVertex);

		if (e != null) {
			fireEdgeRemoved(e);
		}

		return e;
	}

	/**
	 * @see Graph#removeEdge(Object)
	 */
	@Override
	public boolean removeEdge(SDFEdge e) {
		boolean modified = super.removeEdge(e);

		if (modified) {
			fireEdgeRemoved(e);
		}

		return modified;
	}

	/**
	 * @see ListenableGraph#removeGraphListener(GraphListener)
	 */
	@Override
	public void removeGraphListener(GraphListener<SDFAbstractVertex, SDFEdge> l) {
		graphListeners.remove(l);
	}

	/**
	 * @see Graph#removeVertex(Object)
	 */
	@Override
	public boolean removeVertex(SDFAbstractVertex v) {
		if (containsVertex(v)) {
			Set<SDFEdge> touchingEdgesList = edgesOf(v);

			// copy set to avoid ConcurrentModificationException
			removeAllEdges(new ArrayList<SDFEdge>(touchingEdgesList));

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
	@Override
	public void removeVertexSetListener(VertexSetListener<SDFAbstractVertex> l) {
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
