/*********************************************************
 * Copyright or � or Copr. IETR/INSA: Karol Desnos, Maxime Pelcat, 
 * Jean-Francois Nezan, Julien Heulot, Clement Guy
 * 
 * [kdesnos,mpelcat,jnezan,jheulot,cguy]@insa-rennes.fr
 * 
 * This software is a computer program whose purpose is to prototype
 * parallel applications.
 * 
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-C
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
 * knowledge of the CeCILL-C license and that you accept its terms.
 *********************************************************/
package org.ietr.dftools.algorithm.model.sdf.transformations

import org.eclipse.xtend.lib.annotations.Accessors
import org.ietr.dftools.algorithm.model.sdf.SDFEdge
import org.ietr.dftools.algorithm.model.sdf.SDFGraph
import org.ietr.dftools.algorithm.model.sdf.SDFInterfaceVertex
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFBroadcastVertex
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFRoundBufferVertex
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType
import org.ietr.dftools.algorithm.model.sdf.types.SDFStringEdgePropertyType
import org.ietr.dftools.algorithm.model.visitors.SDF4JException

/**
 * @author kdesnos
 */
class IbsdfFlattener {
	/**
	 * The original {@link SDFGraph IBSDF graph} to flatten, must not be modified.
	 */
	val SDFGraph originalGraph

	/**
	 * Depth to which the graph will be flattened.
	 */
	val int depth

	@Accessors
	SDFGraph flattenedGraph

	new(SDFGraph sdf, int depth) {
		originalGraph = sdf
		this.depth = depth
	}

	def flattenGraph() {
		// Copy the original graph
		flattenedGraph = originalGraph.clone

		// Flatten depth times one hierarchy level of the graph
		for (i : 1 .. depth) {
			// Check the schedulability of the top level graph (this will also 
			// set the repetition vector for each actor).
			if (!flattenedGraph.schedulable) {
				throw new SDF4JException('''Graph «flattenedGraph.name» is not schedulable''')
			}

			// Check if there is anything to flatten
			val hasNoHierarchy = flattenedGraph.allVertices.forall [
				!(it.graphDescription != null && it.graphDescription instanceof SDFGraph)
			]

			// If there is nothing to flatten, leave the method
			if (hasNoHierarchy) {
				return
			}

			// Flatten one level of the graph
			flattenOneLevel
		}
	}

	protected def flattenOneLevel() {
		// Get the list of hierarchical actors
		val hierActors = flattenedGraph.allVertices.filter [
			it.graphDescription != null && it.graphDescription instanceof SDFGraph
		]

		// Process actors to flatten one by one
		for (hierActor : hierActors) {
			// Copy the orginal subgraph
			val subgraph = (hierActor.graphDescription as SDFGraph).clone

			// Check its schedulability (this will also 
			// set the repetition vector for each actor).
			if (!subgraph.schedulable) {
				throw new SDF4JException('''Subgraph «subgraph.name» is not schedulable''')
			}

			val nbRepeat = hierActor.nbRepeatAsInteger;
			val containsNoDelay = subgraph.edgeSet.forall[it.delay == null]

			// Prepare the subgraph for instantiation:
			// - replace interfaces if needed
			// - fork/join delays if needed
			addInterfaceSubstitutes(subgraph)

		}
	}

	/**
	 * This method scans the {@link SDFInterfaceVertex} of an {@link 
	 * SDFGraph IBSDF} subgraph  and adds {@link SDFBroadcastVertex} and
	 * {@link SDFRoundBufferVertex}, if needed.
	 * 
	 * @param subgraph
	 * 		the {@link SDFGraph} whose {@link SDFInterfaceVertex} are to checked.
	 *      This graph will be modified within the method. The 
	 *      schedulability of this subgraph must have been tested before being 
	 *      given to this method.
	 * 
	 *  
	 * @throws SDF4JException if an interface is connected to several FIFOs.
	 */
	def addInterfaceSubstitutes(SDFGraph subgraph) {
		for (interface : subgraph.vertexSet.filter(SDFInterfaceVertex).toList) {
			if (interface instanceof SDFSourceInterfaceVertex) {
				// Get successors
				val outEdges = subgraph.outgoingEdgesOf(interface)
				if (outEdges.size > 1) {
					throw new SDF4JException(
						'''Input interface «interface.name» in ''' +
							'''subgraph «subgraph.name» is connected to multiple''' +
							'''FIFOs although this is strictly forbidden.''')
						}

						// Check if a broadcast is needed
						val outEdge = outEdges.get(0)
						val prodRate = outEdge.prod.intValue
						val consRate = outEdge.cons.intValue
						val nbRepeatCons = outEdge.target.nbRepeatAsInteger

						// If more token are consumed during an iteration of 
						// the subgraph than the number of available tokens 
						// => broadcast needed
						if (prodRate < consRate * nbRepeatCons) {
							// Add the broadcast and connect edges
							val broadcast = new SDFBroadcastVertex
							broadcast.name = '''br_«interface.name»'''
							subgraph.addVertex(broadcast)
							val edgeIn = subgraph.addEdge(outEdge.source, broadcast)
							val edgeOut = subgraph.addEdge(broadcast, outEdge.target)

							// Set edges properties
							edgeIn.copyProperties(outEdge)
							edgeIn.targetPortModifier = new SDFStringEdgePropertyType(SDFEdge.MODIFIER_READ_ONLY)
							edgeIn.delay = new SDFIntEdgePropertyType(0)

							edgeOut.copyProperties(outEdge)
							edgeOut.prod = new SDFIntEdgePropertyType(consRate * nbRepeatCons)
							edgeOut.propertyBean.removeProperty(SDFEdge.SOURCE_PORT_MODIFIER);

							// Remove the original edge
							subgraph.removeEdge(outEdge)
						}
					} else { // interface instanceof SDFSinkInterfaceVertex
					// Get predecessor
						val inEdges = subgraph.incomingEdgesOf(interface)
						if (inEdges.size > 1) {
							throw new SDF4JException(
								'''Output interface «interface.name» in ''' +
									'''subgraph «subgraph.name» is connected to multiple''' +
									'''FIFOs although this is strictly forbidden.''')
								}

								// Check if a roundbuffer is needed
								val inEdge = inEdges.get(0)
								val prodRate = inEdge.prod.intValue
								val consRate = inEdge.cons.intValue
								val nbRepeatProd = inEdge.source.nbRepeatAsInteger

								// If more token are produced during an iteration of 
								// the subgraph than the number of consumed tokens 
								// => roundbuffer needed
								if (prodRate * nbRepeatProd > consRate) {
									// Add the roundbuffer and connect edges
									val roundbuffer = new SDFRoundBufferVertex
									roundbuffer.name = '''rb_«interface.name»'''
									subgraph.addVertex(roundbuffer)
									val edgeIn = subgraph.addEdge(inEdge.source, roundbuffer)
									val edgeOut = subgraph.addEdge(roundbuffer, inEdge.target)

									// Set edges properties
									edgeOut.copyProperties(inEdge)
									edgeOut.sourcePortModifier = new SDFStringEdgePropertyType(
										SDFEdge.MODIFIER_WRITE_ONLY)
									edgeOut.prod = new SDFIntEdgePropertyType(consRate)
									edgeOut.delay = new SDFIntEdgePropertyType(0)

									edgeIn.copyProperties(inEdge)
									edgeIn.cons = new SDFIntEdgePropertyType(prodRate * nbRepeatProd)
									edgeIn.propertyBean.removeProperty(SDFEdge.TARGET_PORT_MODIFIER);

									// Remove the original edge
									subgraph.removeEdge(inEdge)
								}
							}
						}
					}
				}