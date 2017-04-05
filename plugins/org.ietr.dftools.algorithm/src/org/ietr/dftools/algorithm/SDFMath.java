/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (%%DATE%%) :
 *
 * %%AUTHORS%%
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
package org.ietr.dftools.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.math.array.DoubleArray;

/**
 * Provides static math method useful for SDF analysis
 * 
 * @author jpiat
 * @author jheulot
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
	public static Map<SDFAbstractVertex, Integer> computeRationnalVRB(
			List<SDFAbstractVertex> subgraph, SDFGraph graph) throws InvalidExpressionException {
		HashMap<SDFAbstractVertex, Integer> trueVrb = new HashMap<SDFAbstractVertex, Integer>();
		int i = 0;
		
		double[][] topology = graph.getTopologyMatrix(subgraph);		
		Vector<Rational> vrb = computeRationnalNullSpace(topology);
		
		List<Integer> result = Rational.toNatural(new Vector<Rational>(vrb));
		for (SDFAbstractVertex vertex : subgraph) {
			trueVrb.put(vertex, result.get(i));
			i++;
		}
		return trueVrb;
	}
	
	private static Vector<Rational> computeRationnalNullSpace(double[][] matrix){
		Vector<Rational> vrb = new Vector<Rational>();
		int li =  matrix.length;
		int col = 1;
		
		if(li!=0)
			col = matrix[0].length;		
		
		if(li == 0 || col == 1){
			for(int i=0; i<col; i++) {
				vrb.add(new Rational(1,1));
			}
			return vrb ;
		}

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
	 * Compute the graphs rational vrb with interfaces being taken into account
	 * @param graph The graph on which to perform the vrb
	 * @return The basic repetition vector of the graph
	 * @throws InvalidExpressionException 
	 */ 
	public static Map<SDFAbstractVertex, Integer> computeRationnalVRBWithInterfaces(
			List<SDFAbstractVertex> subgraph, SDFGraph graph) throws InvalidExpressionException {
		
		List<SDFAbstractVertex> subgraphWOInterfaces = new ArrayList<SDFAbstractVertex>();
		for(SDFAbstractVertex vertex : subgraph){
			if(!(vertex instanceof SDFInterfaceVertex))
				subgraphWOInterfaces.add(vertex);
		}

		Map<SDFAbstractVertex, Integer> vrb = computeRationnalVRB(subgraphWOInterfaces, graph) ;
		
		List<double[]> interfaceTopology = new ArrayList<double[]>();
		double interfaceArrayTopology[][];

		int nbInterfaceEdges = 0;
		int decal = 0 ;
		
		for(SDFAbstractVertex vertex : subgraph){
			if(vertex instanceof SDFInterfaceVertex){
				if(vertex instanceof SDFSinkInterfaceVertex){
					nbInterfaceEdges += graph.incomingEdgesOf(vertex).size();
				}else if(vertex instanceof SDFSourceInterfaceVertex){
					nbInterfaceEdges += graph.outgoingEdgesOf(vertex).size();					
				}
			}
		}
		
		for(SDFAbstractVertex vertex : subgraph){
			if(vertex instanceof SDFInterfaceVertex){
				if(vertex instanceof SDFSinkInterfaceVertex){
					for(SDFEdge edge : graph.incomingEdgesOf(vertex)){
						if(!(edge.getSource() instanceof SDFInterfaceVertex)){
							double line[] = DoubleArray.fill(nbInterfaceEdges+1, 0);
							line[decal] = -edge.getCons().intValue();
							line[nbInterfaceEdges] = edge.getProd().intValue()*(vrb.get(edge.getSource()));
							interfaceTopology.add(line);
							decal ++ ;
						}
					}
				}else if(vertex instanceof SDFSourceInterfaceVertex){
					for(SDFEdge edge : graph.outgoingEdgesOf(vertex)){
						if(!(edge.getTarget() instanceof SDFInterfaceVertex)){
							double line[] = DoubleArray.fill(nbInterfaceEdges+1, 0);
							line[decal] = edge.getProd().intValue();
							line[nbInterfaceEdges] = -edge.getCons().intValue()*(vrb.get(edge.getTarget()));
							interfaceTopology.add(line);
							decal ++ ;
						}
					}
				}
			}
		}
		
		if(interfaceTopology.size() == 0)
			interfaceArrayTopology = new double[0][0];
		else{
			interfaceArrayTopology = new double[interfaceTopology.size()][interfaceTopology.get(0).length];
			
			int i=0;
			for(double[] line : interfaceTopology){
				interfaceArrayTopology[i] = line;
				i++;
			}
		}
		
		Vector<Rational> nullSpace = computeRationnalNullSpace(interfaceArrayTopology);
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
