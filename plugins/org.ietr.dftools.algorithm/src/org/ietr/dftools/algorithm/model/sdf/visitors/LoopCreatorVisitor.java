package org.ietr.dftools.algorithm.model.sdf.visitors;

import java.util.Vector;

import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.visitors.IGraphVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;

/**
 * Visitor used for loop transformation
 * 
 * @author jpiat
 * @deprecated
 * 
 */
@Deprecated
public class LoopCreatorVisitor implements
		IGraphVisitor<SDFGraph, SDFAbstractVertex, SDFEdge> {

	/**
	 * Create loop pattern in the given SDFAbstractGraph
	 * 
	 * @param graph
	 * @throws InvalidExpressionException
	 * @throws SDF4JException 
	 */
	public void createLoop(SDFGraph graph) throws InvalidExpressionException, SDF4JException {
		if (graph.isSchedulable()) {
			int loopingFactor;
			boolean hasUntreatedVertex = true;
			int untreatedIndex = 0;
			Vector<SDFAbstractVertex> loop = new Vector<SDFAbstractVertex>();
			Vector<SDFAbstractVertex> treated = new Vector<SDFAbstractVertex>();
			SDFAbstractVertex newSource = null;
			loop.add((new Vector<SDFAbstractVertex>(graph.vertexSet())).get(0));
			loopingFactor = loop.get(0).getNbRepeatAsInteger();

			while (!treated.contains(loop.get(0))
					|| !treated.contains(loop.get(loop.size() - 1))
					|| hasUntreatedVertex) {
				SDFAbstractVertex newVertex;
				if (!treated.contains(loop.get(0))) {
					newVertex = loop.get(0);
				} else if (!treated.contains(loop.get(loop.size() - 1))) {
					newVertex = loop.get(loop.size() - 1);
				} else {
					newVertex = loop.get(untreatedIndex);
				}
				treated.add(newVertex);

				int vertexVrb = newVertex.getNbRepeatAsInteger();
				for (SDFEdge edge : graph.edgesOf(newVertex)) {
					if (graph.getEdgeSource(edge) != newVertex
							&& (graph.getEdgeSource(edge).getNbRepeatAsInteger()
									% vertexVrb == 0)
							&& (graph.getEdgeSource(edge).getNbRepeatAsInteger()
									/ vertexVrb < (graph.getEdgeSource(edge)
									.getNbRepeatAsInteger()))) {
						if (!loop.contains(graph.getEdgeSource(edge))) {
							loop.insertElementAt(graph.getEdgeSource(edge),
									loop.indexOf(newVertex));
							if (graph.getEdgeSource(edge).getNbRepeatAsInteger() < loopingFactor) {
								loopingFactor = graph.getEdgeSource(edge)
										.getNbRepeatAsInteger();
							}
						} else {
							loop.remove(graph.getEdgeSource(edge));
							loop.insertElementAt(graph.getEdgeSource(edge),
									loop.indexOf(newVertex));
						}
					} else if (graph.getEdgeTarget(edge) != newVertex
							&& (graph.getEdgeTarget(edge).getNbRepeatAsInteger()
									% vertexVrb == 0)
							&& (graph.getEdgeTarget(edge).getNbRepeatAsInteger()
									/ vertexVrb < graph.getEdgeTarget(edge)
									.getNbRepeatAsInteger())) {
						if (!loop.contains(graph.getEdgeTarget(edge))) {
							loop.insertElementAt(graph.getEdgeTarget(edge),
									loop.indexOf(newVertex) + 1);
							if (graph.getEdgeTarget(edge).getNbRepeatAsInteger() < loopingFactor) {
								loopingFactor = graph.getEdgeTarget(edge)
										.getNbRepeatAsInteger();
							}
						}
					} else if (graph.getEdgeTarget(edge) != newVertex) {
						newSource = graph.getEdgeTarget(edge);
					} else if (graph.getEdgeSource(edge) != newVertex) {
						newSource = graph.getEdgeSource(edge);
					}
				}
				if (loop.size() == 1 && newSource != null) {
					treated.add(loop.get(0));
					if (!treated.contains(newSource)) {
						loop.setElementAt(newSource, 0);
						loopingFactor = loop.get(0).getNbRepeatAsInteger();
					}
					newSource = null;
				}
				hasUntreatedVertex = false;
				for (SDFAbstractVertex inLoop : loop) {
					if (!treated.contains(inLoop)) {
						hasUntreatedVertex = true;
						untreatedIndex = loop.indexOf(inLoop);
						break;
					}
				}
			}
			if (loop.size() > 1) {
				SDFEdge loopEdge = graph.addEdge(loop.get(loop.size() - 1),
						loop.get(0));
				loopEdge.setCons(new SDFIntEdgePropertyType(loop.get(
						loop.size() - 1).getNbRepeatAsInteger()
						/ loop.get(0).getNbRepeatAsInteger()));
				loopEdge.setProd(new SDFIntEdgePropertyType(1));
				loopEdge.setDelay(new SDFIntEdgePropertyType(1));
				System.out.println("loop is :" + loop
						+ " with looping factor : " + loopingFactor);
			}
		}
	}

	@Override
	public void visit(SDFEdge sdfEdge) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SDFGraph sdf) throws SDF4JException {
		TopologyVisitor checkTopo = new TopologyVisitor();
		sdf.accept(checkTopo);
		try {
			createLoop(sdf);
		} catch (InvalidExpressionException e) {
			throw(new SDF4JException(e.getMessage()));
		}
	}

	@Override
	public void visit(SDFAbstractVertex sdfVertex) throws SDF4JException {
		TopologyVisitor checkTopo = new TopologyVisitor();
		sdfVertex.accept(checkTopo);
		if (sdfVertex.getGraphDescription() != null) {
			try {
				createLoop((SDFGraph) sdfVertex.getGraphDescription());
			} catch (InvalidExpressionException e) {
				e.printStackTrace();
				throw(new SDF4JException(e.getMessage()));
			}
		}

	}
}
