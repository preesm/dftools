package org.ietr.dftools.algorithm.optimisations.clustering.internalisation;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.ietr.dftools.algorithm.demo.SDFAdapterDemo;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;

/**
 * Class to cluster vertices'SDFGraph with anInternalisation Algorithm
 * 
 * @author pthebault
 * 
 */
public class SDFInternalisation {

	private static final String CLUSTER = "cluster";
	private static final Color[] colorSet = { Color.blue, Color.cyan,
			Color.darkGray, Color.gray, Color.green, Color.lightGray,
			Color.magenta, Color.pink, Color.red, Color.yellow, Color.black,
			Color.blue.brighter(), Color.cyan.brighter(),
			Color.darkGray.brighter(), Color.gray.brighter(),
			Color.green.brighter(), Color.lightGray.brighter(),
			Color.magenta.brighter(), Color.orange.brighter(),
			Color.pink.brighter(), Color.red.brighter(),
			Color.yellow.brighter(), Color.black.brighter(),
			Color.blue.darker(), Color.cyan.darker(), Color.darkGray.darker(),
			Color.gray.darker(), Color.green.darker(),
			Color.lightGray.darker(), Color.magenta.darker(),
			Color.orange.darker(), Color.pink.darker(), Color.red.darker(),
			Color.yellow.darker(), Color.black.darker() };

	/**
	 * Function which calculate time of execution of an SDFGraph
	 * 
	 * @param graphin
	 *            The graph to compute execution time
	 * @param Time
	 *           The average execution time of each vertex
	 * @param Size
	 *            The size of data transfered on each edge for one execution
	 *            of the graph
	 * @param Prec
	 *            The vertices of the precedence graph
	 * @param ProcessorAssignement The processor on which the vertices are mapped
	 * @param TotalTime Total 
	 * @param EST
	 * @param LCT
	 * @param TotalOrder
	 * @return The parallel execution time
	 * @throws InvalidExpressionException 
	 */
	public static int DetermineTimes(SDFGraph graphin,
			HashMap<SDFAbstractVertex, Integer> Time,
			HashMap<SDFEdge, Integer> Size, Vector<SDFAbstractVertex> Prec,
			HashMap<SDFAbstractVertex, Integer> ProcessorAssignement,
			HashMap<SDFAbstractVertex, Integer> TotalTime,
			HashMap<SDFAbstractVertex, Integer> EST,
			HashMap<SDFAbstractVertex, Integer> LCT,
			HashMap<SDFAbstractVertex, Integer> TotalOrder) throws InvalidExpressionException {
		// COMMmin probleme li� aux ports (est ce la m�me donn�e?)
		Vector<SDFEdge> COMMmin = new Vector<SDFEdge>();
		for (SDFEdge edge : graphin.edgeSet()) {
			if (ProcessorAssignement.get(edge.getSource()) != ProcessorAssignement
					.get(edge.getTarget())) {
				COMMmin.add(edge);
			}
		}

		Vector<SDFEdge> Pairs = new Vector<SDFEdge>();
		for (SDFEdge edge : graphin.edgeSet()) {
			if (COMMmin.contains(edge)) {
				Pairs.add(edge);
			}
		}
		HashMap<SDFEdge, Integer> TotalSize = new HashMap<SDFEdge, Integer>();
		for (SDFEdge edge : Pairs) {
			int temp = 0;
			for (SDFEdge edge_temp : graphin.getAllEdges(edge.getSource(), edge
					.getTarget())) {
				temp += Size.get(edge_temp);
			}
			TotalSize.put(edge, temp);
		}
		int SumSize = 0;
		for (SDFEdge edge : Pairs) {
			SumSize += TotalSize.get(edge);
		}
		int SumTime = 0;
		for (SDFAbstractVertex vertex : graphin.vertexSet()) {
			SumTime += Time.get(vertex);
		}
		int Load = SumSize / SumTime;
		Load = 1;
		List<SDFAbstractVertex> List = new Vector<SDFAbstractVertex>();
		List = TopologicalSort(graphin);

		for (SDFAbstractVertex vertex : List) {
			int temp = 0;
			for (SDFEdge edge : graphin.incomingEdgesOf(vertex)) {
				if (Pairs.contains(edge)) {
					temp += Size.get(edge);
				}
			}
			for (SDFEdge edge : graphin.outgoingEdgesOf(vertex)) {
				if (Pairs.contains(edge)) {
					temp += Size.get(edge);
				}
			}
			temp = temp / Load;
			TotalTime.put(vertex, Time.get(vertex) + temp);// nous considerons
															// des communication
															// a temps nul
		}
		for (SDFAbstractVertex vertex : List) {
			int max = 0;
			for (SDFEdge edge : graphin.incomingEdgesOf(vertex)) {
				if (edge.getDelay().intValue() == 0) {
					if (EST.get(edge.getSource()) != null) {
						int temp = EST.get(edge.getSource())
								+ TotalTime.get(edge.getSource());
						if (temp > max) {
							max = temp;
						}
					}
				}
			}
			EST.put(vertex, max);
		}
		int ParTime = 0;
		for (SDFAbstractVertex vertex : graphin.vertexSet()) {
			if (EST.get(vertex) + TotalTime.get(vertex) > ParTime) {
				ParTime = EST.get(vertex) + TotalTime.get(vertex);
			}
		}
		int index = 0;
		for (SDFAbstractVertex vertex : List) {
			LCT.put(vertex, EST.get(vertex) + TotalTime.get(vertex));
			TotalOrder.put(vertex, index++);
		}
		return ParTime;
	}

	/**
	 * Find all the Top and Bottom Vertices
	 * 
	 * @param graphin
	 * @param Top
	 * @param Bottom
	 * @throws InvalidExpressionException 
	 */
	public static void FindTopBottom(SDFGraph graphin,
			Vector<SDFAbstractVertex> Top, Vector<SDFAbstractVertex> Bottom) throws InvalidExpressionException {
		for (SDFAbstractVertex vertex : graphin.vertexSet()) {
			boolean isTop = true;
			for (SDFEdge edge : graphin.incomingEdgesOf(vertex)) {
				if (edge.getDelay().intValue() == 0) {
					isTop = false;
					break;
				}
			}
			if (isTop) {
				Top.add(vertex);
			}
			boolean isBottom = true;
			for (SDFEdge edge : graphin.outgoingEdgesOf(vertex)) {
				if (edge.getDelay().intValue() == 0) {
					isBottom = false;
					break;
				}
			}
			if (isBottom) {
				Bottom.add(vertex);
			}
		}
	}

	/**
	 * Function based on Intenalisation-Prepass algorithm to cluster vertices
	 * 
	 * @param graphin
	 *            graph to cluster
	 * @return Clustered graph
	 * @throws InvalidExpressionException 
	 */

	@SuppressWarnings("unchecked")
	public static SDFGraph PartitionGraph(SDFGraph graphin) throws InvalidExpressionException {
		SDFGraph graphout = graphin.clone();
		HashMap<Integer, Vector<SDFAbstractVertex>> node_sequence_infinit = new HashMap<Integer, Vector<SDFAbstractVertex>>();
		HashMap<SDFAbstractVertex, Integer> ProcessorAssignement_inf = new HashMap<SDFAbstractVertex, Integer>();
		Vector<SDFAbstractVertex> Top = new Vector<SDFAbstractVertex>();
		Vector<SDFAbstractVertex> Bottom = new Vector<SDFAbstractVertex>();
		FindTopBottom(graphout, Top, Bottom);
		int nb_clusters = 1;
		for (SDFAbstractVertex vertex : Top) {
			ProcessorAssignement_inf.put(vertex, nb_clusters);
		}
		for (SDFAbstractVertex vertex : Bottom) {
			ProcessorAssignement_inf.put(vertex, nb_clusters);
		}
		node_sequence_infinit.put(nb_clusters, (Vector<SDFAbstractVertex>) Top
				.clone());
		node_sequence_infinit.get(nb_clusters).addAll(Bottom);
		for (SDFAbstractVertex vertex : graphout.vertexSet()) {
			if (!Top.contains(vertex) && !Bottom.contains(vertex))
				ProcessorAssignement_inf.put(vertex, ++nb_clusters);
			node_sequence_infinit.put(nb_clusters,
					new Vector<SDFAbstractVertex>());
			node_sequence_infinit.get(nb_clusters).add(vertex);
		}
		HashMap<SDFAbstractVertex, Integer> Time = new HashMap<SDFAbstractVertex, Integer>();
		// 3 a completer
		for (SDFAbstractVertex vertex : graphout.vertexSet()) {
			Time.put(vertex, 100000);
		}

		HashMap<SDFEdge, Integer> Size = new HashMap<SDFEdge, Integer>();

		for (SDFEdge edge : graphout.edgeSet()) {
			Size.put(edge, edge.getProd().intValue()
					* edge.getSource().getNbRepeatAsInteger());
		}
		Vector<SDFAbstractVertex> Prec = new Vector<SDFAbstractVertex>();
		for (SDFAbstractVertex vertex : Top) {
			int vertexcluster = ProcessorAssignement_inf.get(vertex);
			for (SDFEdge edge : graphout.outgoingEdgesOf(vertex)) {
				if (ProcessorAssignement_inf.get(edge.getTarget()) == vertexcluster) {
					if (!Prec.contains(vertex)) {
						Prec.add(vertex);
					}
					if (!Prec.contains(edge.getTarget())) {
						Prec.add(edge.getTarget());
					}
				}
			}
		}
		HashMap<SDFAbstractVertex, Integer> TotalTime = new HashMap<SDFAbstractVertex, Integer>();
		HashMap<SDFAbstractVertex, Integer> EST = new HashMap<SDFAbstractVertex, Integer>();
		int ParTime = 0;
		HashMap<SDFAbstractVertex, Integer> LCT = new HashMap<SDFAbstractVertex, Integer>();
		HashMap<SDFAbstractVertex, Integer> TotalOrder = new HashMap<SDFAbstractVertex, Integer>();

		ParTime = DetermineTimes(graphout, Time, Size, Prec,
				ProcessorAssignement_inf, TotalTime, EST, LCT, TotalOrder);

		SDFEdge[] Edges = new SDFEdge[Size.size()];
		Vector<SDFEdge> temp = new Vector<SDFEdge>();
		temp.addAll(graphout.edgeSet());
		for (int index = 0; index < Size.size(); index++) {
			int max = 0;
			for (SDFEdge edge : temp) {
				if (Size.get(edge) > max) {
					max = Size.get(edge);
					Edges[index] = edge;
				}
			}
			temp.remove(Edges[index]);
		}
		HashMap<Integer, Vector<SDFAbstractVertex>> node_sequence_new = new HashMap<Integer, Vector<SDFAbstractVertex>>();

		for (int index = 0; index < Size.size(); index++) {
			if (ProcessorAssignement_inf.get(Edges[index].getSource()) != ProcessorAssignement_inf
					.get(Edges[index].getTarget())) {
				for (SDFEdge edge : graphout.edgeSet()) {
					if (ProcessorAssignement_inf.get(edge.getSource()) == ProcessorAssignement_inf
							.get(edge.getTarget())) {
						if (edge.getTarget() != edge.getSource()) {
							node_sequence_new.put(ProcessorAssignement_inf
									.get(edge.getSource()),
									node_sequence_infinit
											.get(ProcessorAssignement_inf
													.get(edge.getSource())));
						}
					}
				}
				Vector<SDFAbstractVertex> temp1 = new Vector<SDFAbstractVertex>();
				if (LCT.get(Edges[index].getSource())
						- TotalTime.get(Edges[index].getSource()) < LCT
						.get(Edges[index].getTarget())
						- TotalTime.get(Edges[index].getTarget())) {
					temp1.add(Edges[index].getSource());
					temp1.add(Edges[index].getTarget());
				} else if (LCT.get(Edges[index].getSource())
						- TotalTime.get(Edges[index].getSource()) > LCT
						.get(Edges[index].getTarget())
						- TotalTime.get(Edges[index].getTarget())) {
					temp1.add(Edges[index].getTarget());
					temp1.add(Edges[index].getSource());
				} else {
					if (TotalOrder.get(Edges[index].getSource()) < TotalOrder
							.get(Edges[index].getTarget())) {
						temp1.add(Edges[index].getSource());
						temp1.add(Edges[index].getTarget());
					} else {
						temp1.add(Edges[index].getTarget());
						temp1.add(Edges[index].getSource());
					}
				}
				node_sequence_new.put(ProcessorAssignement_inf.get(Edges[index]
						.getSource()), temp1);
				HashMap<SDFAbstractVertex, Integer> ProcessorAssignement_new = (HashMap<SDFAbstractVertex, Integer>) ProcessorAssignement_inf
						.clone();
				ProcessorAssignement_new.put(Edges[index].getTarget(),
						ProcessorAssignement_inf.get(Edges[index].getSource()));
				HashMap<SDFAbstractVertex, Integer> TotalTime_new = new HashMap<SDFAbstractVertex, Integer>();
				HashMap<SDFAbstractVertex, Integer> EST_new = new HashMap<SDFAbstractVertex, Integer>();
				int ParTime_new = 0;
				HashMap<SDFAbstractVertex, Integer> LCT_new = new HashMap<SDFAbstractVertex, Integer>();
				HashMap<SDFAbstractVertex, Integer> TotalOrder_new = new HashMap<SDFAbstractVertex, Integer>();
				Vector<SDFAbstractVertex> Prec_new = new Vector<SDFAbstractVertex>();
				for (SDFAbstractVertex vertex : Top) {
					int vertexcluster = ProcessorAssignement_new.get(vertex);
					for (SDFEdge edge : graphout.outgoingEdgesOf(vertex)) {
						if (ProcessorAssignement_new.get(edge.getTarget()) == vertexcluster) {
							if (!Prec_new.contains(vertex)) {
								Prec_new.add(vertex);
							}
							if (!Prec_new.contains(edge.getTarget())) {
								Prec_new.add(edge.getTarget());
							}
						}
					}
				}
				ParTime_new = DetermineTimes(graphout, Time, Size, Prec_new,
						ProcessorAssignement_new, TotalTime_new, EST_new,
						LCT_new, TotalOrder_new);
				if (ParTime_new <= ParTime) {
					node_sequence_infinit = (HashMap<Integer, Vector<SDFAbstractVertex>>) node_sequence_new
							.clone();
					ProcessorAssignement_inf = (HashMap<SDFAbstractVertex, Integer>) ProcessorAssignement_new
							.clone();
					EST = (HashMap<SDFAbstractVertex, Integer>) EST_new.clone();
					LCT = (HashMap<SDFAbstractVertex, Integer>) LCT_new.clone();
					ParTime = ParTime_new;
					TotalTime = (HashMap<SDFAbstractVertex, Integer>) TotalTime_new
							.clone();
					TotalOrder = (HashMap<SDFAbstractVertex, Integer>) TotalOrder_new
							.clone();
				}
			}
		}
		HashMap<Integer, Integer> clusters = new HashMap<Integer, Integer>();
		int index = 0;
		for (SDFAbstractVertex vertex : graphout.vertexSet()) {
			if (!clusters.keySet().contains(
					ProcessorAssignement_inf.get(vertex))) {
				clusters.put(ProcessorAssignement_inf.get(vertex), index++);
			}
			vertex.getPropertyBean().setValue(CLUSTER,
					clusters.get(ProcessorAssignement_inf.get(vertex)));
		}
		return graphout;
	}

	/**
	 * Set vertex color in function of the cluster number of them
	 * 
	 * @param graphin
	 *            is the graph to set vertices colors
	 * @return the graph with colored vertex
	 */
	public static SDFGraph SDFclusteringColor(SDFGraph graphin) {
		int cluster;
		for (SDFAbstractVertex vertex : graphin.vertexSet()) {
			cluster = (Integer) vertex.getPropertyBean().getValue(CLUSTER);
			if (cluster != 0) {
				vertex.getPropertyBean().setValue(SDFAdapterDemo.VERTEX_COLOR,
						colorSet[cluster]);
			}
		}
		return graphin;
	}

	/**
	 * Sort the vertices of the graph in the topological order
	 * 
	 * @param graphin
	 *            The graph to sort
	 * @return The list of the vertices of the raph sorted in the topological
	 *         order
	 * @throws InvalidExpressionException 
	 */
	public static List<SDFAbstractVertex> TopologicalSort(SDFGraph graphin) throws InvalidExpressionException {
		Vector<SDFAbstractVertex> List = new Vector<SDFAbstractVertex>();
		Vector<SDFAbstractVertex> Top = new Vector<SDFAbstractVertex>();
		Vector<SDFAbstractVertex> Bottom = new Vector<SDFAbstractVertex>();
		FindTopBottom(graphin, Top, Bottom);
		for (SDFAbstractVertex vertex : Top) {
			List.add(vertex);
		}
		Vector<SDFAbstractVertex> Begin = new Vector<SDFAbstractVertex>();
		Vector<SDFAbstractVertex> End = new Vector<SDFAbstractVertex>();
		Begin = Top;
		do {
			End.removeAllElements();
			for (SDFAbstractVertex vertex : Begin) {
				for (SDFEdge edge : graphin.outgoingEdgesOf(vertex)) {
					if (edge.getDelay().intValue() == 0) {
						boolean test = true;
						for (SDFEdge edgetemp : graphin.incomingEdgesOf(edge
								.getTarget())) {
							if (!List.contains(edgetemp.getSource())
									&& edgetemp.getDelay().intValue() == 0) {
								test = false;
							}
						}
						if (test && !End.contains(edge.getTarget())
								&& !List.contains(edge.getTarget())) {
							End.add(edge.getTarget());
						}
					}
				}
			}
			List.addAll(End);
			Begin.removeAllElements();
			Begin.addAll(End);
		} while (End.size() != 0);
		return List;

	}
}
