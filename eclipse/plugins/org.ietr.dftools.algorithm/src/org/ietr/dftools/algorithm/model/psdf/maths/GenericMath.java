package org.ietr.dftools.algorithm.model.psdf.maths;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.text.ParseException;

import org.ietr.dftools.algorithm.model.psdf.IPSDFSpecificVertex;
import org.ietr.dftools.algorithm.model.psdf.PSDFGraph;
import org.ietr.dftools.algorithm.model.psdf.maths.symbolic.DivisionFactory;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.jgrapht.alg.ConnectivityInspector;

/**
 * Provides static math method useful for SDF analysis
 * 
 * @author jpiat
 * 
 */
public class GenericMath {

	/**
	 * Computes the basic repetition vector of an SDFAbstractGraph using
	 * rational
	 * 
	 * @param graph
	 * @return the hash map mapping vertices to their repetition factor
	 * @throws InvalidGenericException
	 * @throws ParseException
	 * @throws NotSchedulableException
	 */
	public static HashMap<SDFAbstractVertex, Generic> computeRationnalVRB(
			PSDFGraph graph) throws ParseException, NotSchedulableException {
		HashMap<SDFAbstractVertex, Generic> trueVrb = new HashMap<SDFAbstractVertex, Generic>();
		int i = 0;
		Generic[][] topology = graph.getSymbolicTopologyMatrix();
		if (Array.getLength(topology) == 0 || Array.getLength(topology[0]) == 1) {
			for (SDFAbstractVertex vertex : graph.vertexSet()) {
				if (!(vertex instanceof SDFInterfaceVertex)) {
					trueVrb.put(vertex, Expression.valueOf("1"));
				}
			}
			return trueVrb;
		}
		Vector<Generic> vrb = computeRationnalNullSpace(topology);
		for (SDFAbstractVertex vertex : graph.vertexSet()) {
			if (!(vertex instanceof SDFInterfaceVertex)
					&& !(vertex instanceof IPSDFSpecificVertex)) {
				trueVrb.put(vertex, vrb.get(i).simplify());
				i++;
			}
		}
		return trueVrb;
	}

	private static Vector<Generic> computeRationnalNullSpace(Generic[][] matrix)
			throws ParseException, NotSchedulableException {
		int li = Array.getLength(matrix);
		int col = Array.getLength(matrix[0]);
		Generic rationnalTopology[][] = new Generic[li][col];
		for (int i = 0; i < li; i++) {
			for (int j = 0; j < col; j++) {
				rationnalTopology[i][j] = matrix[i][j];
			}
		}
		int switchIndices = 1;
		while (rationnalTopology[0][0].equals(Expression.valueOf("0"))) {
			Generic buffer[] = rationnalTopology[0];
			rationnalTopology[0] = rationnalTopology[switchIndices];
			rationnalTopology[switchIndices] = buffer;
			switchIndices++;
		}
		int pivot = 0;
		for (int i = 0; i < col; i++) {
			Generic pivotMax = Expression.valueOf("0");
			int maxIndex = i;
			for (int t = i; t < li; t++) {
				if (rationnalTopology[t][i].abs().compareTo(pivotMax) > 0) {
					maxIndex = t;
					pivotMax = rationnalTopology[t][i].abs();
				}
			}
			if (!pivotMax.equals(Expression.valueOf("0")) && maxIndex != i) {
				Generic buffer[] = rationnalTopology[i];
				rationnalTopology[i] = rationnalTopology[maxIndex];
				rationnalTopology[maxIndex] = buffer;
				pivot = i;
			} else if (maxIndex == i
					&& !pivotMax.equals(Expression.valueOf("0"))) {
				pivot = i;
			} else {
				break;
			}
			Generic odlPivot = rationnalTopology[i][i];
			for (int t = i; t < col; t++) {
				rationnalTopology[i][t] = DivisionFactory.divide(
						rationnalTopology[i][t], odlPivot).simplify();
			}
			for (int j = i + 1; j < li; j++) {
				if (!rationnalTopology[j][i].equals(Expression.valueOf("0"))) {
					Generic oldji = rationnalTopology[j][i];
					for (int k = 0; k < col; k++) {
						rationnalTopology[j][k] = rationnalTopology[j][k]
								.subtract(
										rationnalTopology[i][k]
												.multiply(DivisionFactory
														.divide(
																oldji,
																rationnalTopology[pivot][pivot])))
								.simplify();
					}
				}
			}
		}
		int rank = 0;
		for (int i = 0; i < li; i++) {
			Generic colSum = Expression.valueOf("0");
			for (int j = 0; j < col; j++) {
				colSum = colSum.add(rationnalTopology[i][j].simplify().abs())
						.simplify();
			}
			if (!colSum.equals(Expression.valueOf("0"))) {
				rank++;
			}
		}
		if (rank != col - 1) {
			throw (new NotSchedulableException(" rank is : " + rank
					+ " must be " + (col - 1) + " to be schedulable"));
		}

		Vector<Generic> vrb = new Vector<Generic>();
		for (int i = 0; i < col; i++) {
			vrb.add(Expression.valueOf("1"));
		}
		int i = li - 1;
		while (i >= 0) {
			Generic val = Expression.valueOf("0");
			for (int k = i + 1; k < col; k++) {
				val = val.add(rationnalTopology[i][k].multiply(vrb.get(k)))
						.abs();
			}
			if (!val.equals(Expression.valueOf("0"))) {
				if (rationnalTopology[i][i].equals(Expression.valueOf("0"))) {
					System.out.println("elt diagonal zero");
				}
				vrb
						.set(i, DivisionFactory.divide(val,
								rationnalTopology[i][i]));
			}
			i--;
		}
		return computeNatural(vrb);
	}

	/**
	 * Compute the graphs rationnal vrb with interfaces being taken into account
	 * 
	 * @param graph
	 *            The graph on which to perform the vrb
	 * @return The basic repetition vector of the graph
	 * @throws ParseException
	 * @throws NotSchedulableException
	 * @throws InvalidGenericException
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static HashMap<SDFAbstractVertex, Generic> computeRationnalVRBWithInterfaces(
			PSDFGraph graph) throws ParseException, NotSchedulableException {
		HashMap<SDFAbstractVertex, Generic> vrb = computeRationnalVRB(graph);
		ConnectivityInspector inspector = new ConnectivityInspector<SDFAbstractVertex, SDFEdge>(
				graph);
		List<Set<SDFAbstractVertex>> sets = inspector.connectedSets();
		for (Set<SDFAbstractVertex> set : sets) {
			if (set.size() > 1) {
				int nbEdges = 0;
				for (SDFAbstractVertex vertex : set) {
					if (vertex instanceof SDFInterfaceVertex) {
						nbEdges += graph.incomingEdgesOf(vertex).size();
						nbEdges += graph.outgoingEdgesOf(vertex).size();
					}
				}
				Generic interfaceTopology[][] = new Generic[nbEdges][nbEdges + 1];
				for (int k = 0; k < nbEdges; k++) {
					for (int t = 0; t < nbEdges + 1; t++) {
						interfaceTopology[k][t] = Expression.valueOf("0");
					}
				}
				int decal = 0;
				for (SDFAbstractVertex vertex : set) {
					if (vertex instanceof SDFInterfaceVertex) {
						if (vertex instanceof SDFSinkInterfaceVertex) {
							for (SDFEdge edge : graph.incomingEdgesOf(vertex)) {
								if (!(edge.getSource() instanceof SDFInterfaceVertex)) {
									interfaceTopology[decal][decal] = Expression
											.valueOf(edge.getCons().toString())
											.negate();
									interfaceTopology[decal][nbEdges] = Expression
											.valueOf(edge.getProd().toString())
											.multiply(vrb.get(edge.getSource()));
									decal++;
								}
							}
						} else if (vertex instanceof SDFSourceInterfaceVertex) {
							for (SDFEdge edge : graph.outgoingEdgesOf(vertex)) {
								if (!(edge.getTarget() instanceof SDFInterfaceVertex)) {
									interfaceTopology[decal][decal] = Expression
											.valueOf(edge.getProd().toString());
									interfaceTopology[decal][nbEdges] = Expression
											.valueOf(edge.getCons().toString())
											.multiply(vrb.get(edge.getTarget()))
											.negate();
									decal++;
								}
							}
						}
					}
				}
				Vector<Generic> nullSpace = computeRationnalNullSpace(interfaceTopology);
				for (SDFAbstractVertex vertex : vrb.keySet()) {
					vrb.put(vertex, vrb.get(vertex).multiply(
							nullSpace.get(nullSpace.size() - 1)));
				}
			}
		}
		return vrb;
	}

	private static Vector<Generic> computeNatural(Vector<Generic> values) {
		Generic gcd;
		try {
			Vector<Generic> ratValues = new Vector<Generic>();
			gcd = Expression.valueOf("1");

			for (Generic value : values) {
				gcd = gcd.scm(extractDenum(value.simplify()));
			}
			for (Generic value : values) {
				ratValues.add(value.multiply(gcd).simplify());
			}
			return ratValues;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Generic extractDenum(Generic g) throws ParseException {
		if (g.toString().contains("/")) {
			return Expression.valueOf(g.toString().split("/")[1]);
		} else {
			return Expression.valueOf("1");
		}
	}
}
