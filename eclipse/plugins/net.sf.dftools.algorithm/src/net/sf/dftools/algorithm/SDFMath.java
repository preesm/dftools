package net.sf.dftools.algorithm;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import net.sf.dftools.algorithm.model.parameters.InvalidExpressionException;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import net.sf.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import net.sf.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;

import org.math.array.DoubleArray;

/**
 * Provides static math method useful for SDF analysis
 * 
 * @author jpiat
 * 
 */
public class SDFMath {

	/**
	 * Computes the basic repetition vector of an SDFAbstractGraph using
	 * rational
	 * 
	 * @param graph
	 * @return the hash map mapping vertices to their repetition factor
	 * @throws InvalidExpressionException 
	 */
	public static HashMap<SDFAbstractVertex, Integer> computeRationnalVRB(
			SDFGraph graph) throws InvalidExpressionException {
		HashMap<SDFAbstractVertex, Integer> trueVrb = new HashMap<SDFAbstractVertex, Integer>();
		int i = 0;
		double[][] topology = graph.getTopologyMatrix();
		if(Array.getLength(topology) == 0 || Array.getLength(topology[0]) == 1){
			for (SDFAbstractVertex vertex : graph.vertexSet()) {
				if(!(vertex instanceof SDFInterfaceVertex)){
					trueVrb.put(vertex, 1);
				}
			}
			return trueVrb ;
		}
		Vector<Rational> vrb = computeRationnalNullSpace(topology);
		List<Integer> result = Rational.toNatural(new Vector<Rational>(vrb));
		for (SDFAbstractVertex vertex : graph.vertexSet()) {
			if(!(vertex instanceof SDFInterfaceVertex)){
				trueVrb.put(vertex, result.get(i));
				i++;
			}
		}
		return trueVrb;
	}
	
	private static Vector<Rational> computeRationnalNullSpace(double[][] matrix){
		int li = Array.getLength(matrix);
		int col = Array.getLength(matrix[0]);
		Rational rationnalTopology[][] = new Rational[li][col];
		for (int i = 0; i < li; i++) {
			for (int j = 0; j < col; j++) {
				rationnalTopology[i][j] = new Rational(
						((Double) matrix[i][j]).intValue(), 1);
			}
		}
		int switchIndices = 1;
		while (rationnalTopology[0][0].zero()) {
			Rational buffer[] = rationnalTopology[0];
			rationnalTopology[0] = rationnalTopology[switchIndices];
			rationnalTopology[switchIndices] = buffer;
			switchIndices++;
		}
		int pivot = 0;
		for (int i = 0; i < col; i++) {
			double pivotMax = 0;
			int maxIndex = i;
			for (int t = i; t < li; t++) {
				if (Math.abs(rationnalTopology[t][i].doubleValue()) > pivotMax) {
					maxIndex = t;
					pivotMax = Math.abs(rationnalTopology[t][i].doubleValue());
				}
			}
			if (pivotMax != 0 && maxIndex != i) {
				Rational buffer[] = rationnalTopology[i];
				rationnalTopology[i] = rationnalTopology[maxIndex];
				rationnalTopology[maxIndex] = buffer;
				pivot = i;
			} else if (maxIndex == i && pivotMax != 0) {
				pivot = i;
			} else {
				break;
			}
			Rational odlPivot = rationnalTopology[i][i].clone();
			for (int t = i; t < col; t++) {
				rationnalTopology[i][t] = Rational.div(rationnalTopology[i][t],
						odlPivot);
			}
			for (int j = i + 1; j < li; j++) {
				if (!rationnalTopology[j][i].zero()) {
					Rational oldji = new Rational(rationnalTopology[j][i]
							.getNum(), rationnalTopology[j][i].getDenum());
					for (int k = 0; k < col; k++) {
						rationnalTopology[j][k] = Rational
								.sub(
										rationnalTopology[j][k],
										Rational
												.prod(
														rationnalTopology[i][k],
														Rational
																.div(
																		oldji,
																		rationnalTopology[pivot][pivot])));
					}
				}
			}
		}
		Vector<Rational> vrb = new Vector<Rational>();
		for (int i = 0; i < col; i++) {
			vrb.add(new Rational(1, 1));
		}
		int i = li - 1;
		while (i >= 0) {
			Rational val = new Rational(0, 0);
			for (int k = i + 1; k < col; k++) {
				val = Rational.add(val, Rational.prod(rationnalTopology[i][k],
						vrb.get(k)));
			}
			if (!val.zero()) {
				if(rationnalTopology[i][i].zero()){
					System.out.println("elt diagonal zero");
				}
				vrb.set(i, Rational.div(val.abs(), rationnalTopology[i][i]));
			}
			i--;
		}
		return vrb ;
	}
	
	/**
	 * Compute the graphs rationnal vrb with interfaces being taken into account
	 * @param graph The graph on which to perform the vrb
	 * @return The basic repetition vector of the graph
	 * @throws InvalidExpressionException 
	 */ 
	public static HashMap<SDFAbstractVertex, Integer> computeRationnalVRBWithInterfaces(
			SDFGraph graph) throws InvalidExpressionException {
		HashMap<SDFAbstractVertex, Integer> vrb = computeRationnalVRB(graph) ;
		int nbEdges = 0 ;
		for(SDFAbstractVertex vertex : graph.vertexSet()){
			if(vertex instanceof SDFInterfaceVertex){
				nbEdges += graph.incomingEdgesOf(vertex).size();
				nbEdges += graph.outgoingEdgesOf(vertex).size();
			}
		}
		double interfaceTopology [] [] = DoubleArray.fill(nbEdges, nbEdges+1, 0);
		int decal = 0 ;
		for(SDFAbstractVertex vertex : graph.vertexSet()){
			if(vertex instanceof SDFInterfaceVertex){
				if(vertex instanceof SDFSinkInterfaceVertex){
					for(SDFEdge edge : graph.incomingEdgesOf(vertex)){
						if(!(edge.getSource() instanceof SDFInterfaceVertex)){
							interfaceTopology[decal][decal] = -edge.getCons().intValue();
							interfaceTopology[decal][nbEdges] = edge.getProd().intValue()*(vrb.get(edge.getSource()));
							decal ++ ;
						}
					}
				}else if(vertex instanceof SDFSourceInterfaceVertex){
					for(SDFEdge edge : graph.outgoingEdgesOf(vertex)){
						if(!(edge.getTarget() instanceof SDFInterfaceVertex)){
							interfaceTopology[decal][decal] = edge.getProd().intValue();
							interfaceTopology[decal][nbEdges] = -edge.getCons().intValue()*(vrb.get(edge.getTarget()));
							decal ++ ;
						}
					}
				}
			}
		}
		Vector<Rational> nullSpace = computeRationnalNullSpace(interfaceTopology);
		List<Integer> result = Rational.toNatural(nullSpace);
		for(SDFAbstractVertex vertex : vrb.keySet()){
			vrb.put(vertex, vrb.get(vertex)*result.get(result.size()-1));
		}
		return vrb;
	}

	/**
	 * Computes the greater common divider of two integer
	 * 
	 * @param a
	 * @param b
	 * @return the gcd of a and b
	 */
	public static int gcd(int a, int b) {
		if (a < b)
			return (gcd(b, a));
		else if (b == 0)
			return (a);
		else
			return (gcd(b, a % b));
	}

	/**
	 * Computes the gcd (greatest common divider) of a list of integer
	 * @param valList The list of integer to compute
	 * @return The gcd (greatest common divider) of the list
	 */
	public static int gcd(List<Integer> valList){
		int gcd = 0 ;
		for(Integer val : valList){
			if(gcd == 0){
				gcd = val.intValue();
			}else{
				gcd = gcd(gcd, val.intValue());
			}
		}
		return gcd ;
	}
	/**
	 * Computes the
	 * 
	 * @param nbr1
	 * @param nbr2
	 * @return the least common multiple of nbr1 and nbr2
	 */
	public static int lcm(int nbr1, int nbr2) {
		int lePpcm;
		if ((0 == nbr1) || (0 == nbr2))
			return 0;
		for (lePpcm = (nbr1 < nbr2) ? nbr2 : nbr1; lePpcm < nbr1 * nbr2; lePpcm++)
			if ((0 == (lePpcm % nbr1)) && (0 == (lePpcm % nbr2)))
				break;
		return lePpcm;
	}

}
