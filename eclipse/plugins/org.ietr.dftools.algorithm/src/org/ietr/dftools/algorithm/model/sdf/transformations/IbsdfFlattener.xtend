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
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFForkVertex
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFJoinVertex
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex
import java.util.LinkedHashMap
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex

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

	/**
	 * Each fifo with a delay will be replaced with:
	 * <ul><li>A fork with two outputs</li>
	 * <li>A join with two inputs</li>
	 * <li>The two outputs of the fork (o_0 and o_1) are respectively connected to 
	 * the two inputs (i_1 and i_0) of the join.</li>
	 * <li>Delays of the fifos between fork and join are computed to ensure 
	 * the correct single-rate transformation of the application.</li></ul>
	 */
	def addDelaySubstitutes(SDFGraph subgraph, int nbRepeat) {
		// Scan the fifos with delays in the subgraph
		for (fifo : subgraph.edgeSet.filter[it.delay != null].toList) {
			// Get the number of tokens produced and consumed during each
			// subgraph iteration for this fifo
			val tgtRepeat = fifo.target.nbRepeatAsInteger
			val tgtCons = fifo.cons.intValue
			val nbDelay = fifo.delay.intValue

			// Compute the prod and cons rate of the FIFOs between fork/join
			val rate1 = nbDelay % (tgtCons * tgtRepeat)
			val rate0 = (tgtCons * tgtRepeat) - rate1

			if (rate1 == 0) {
				// The number of delay is a perfect modulo of the number of 
				// tokens produced/consumed during an iteration, there is no 
				// need to add fork and join, only to set the correct number 
				// of delays
				fifo.delay = new SDFIntEdgePropertyType(nbDelay * nbRepeat)
			} else {
				// Minimum difference of iteration between the production and 
				// consumption of tokens
				val minIterDiff = nbDelay / (tgtCons * tgtRepeat)

				// Add fork and join
				val fork = new SDFForkVertex
				fork.name = '''exp_«fifo.source.name»_«fifo.sourceLabel»'''
				subgraph.addVertex(fork)

				val join = new SDFJoinVertex
				join.name = '''imp_«fifo.target.name»_«fifo.targetLabel»'''
				subgraph.addVertex(join)

				// Add connection between them
				val fifo0 = subgraph.addEdge(fork, join)
				val fifo1 = subgraph.addEdge(fork, join)
				join.swapEdges(0, 1)

				// Set fifo properties
				fifo0.copyProperties(fifo)
				fifo0.sourceInterface = new SDFSinkInterfaceVertex
				fifo0.sourceInterface.name = '''«fifo.sourceLabel»_0'''
				fifo0.targetInterface = new SDFSourceInterfaceVertex
				fifo0.targetInterface.name = '''«fifo.targetLabel»_«rate1»'''
				fifo0.prod = new SDFIntEdgePropertyType(rate0)
				fifo0.cons = new SDFIntEdgePropertyType(rate0)
				fifo0.delay = new SDFIntEdgePropertyType(rate0 * nbRepeat * minIterDiff)
				fifo0.dataType = fifo.dataType.clone
				fifo0.targetPortModifier = new SDFStringEdgePropertyType(SDFEdge.MODIFIER_READ_ONLY)
				fifo0.sourcePortModifier = new SDFStringEdgePropertyType(SDFEdge.MODIFIER_WRITE_ONLY)

				fifo1.copyProperties(fifo)
				fifo1.sourceInterface = new SDFSinkInterfaceVertex
				fifo1.sourceInterface.name = '''«fifo.sourceLabel»_«rate0»'''
				fifo1.targetInterface = new SDFSourceInterfaceVertex
				fifo1.targetInterface.name = '''«fifo.targetLabel»_0'''
				fifo1.prod = new SDFIntEdgePropertyType(rate1)
				fifo1.cons = new SDFIntEdgePropertyType(rate1)
				fifo1.delay = new SDFIntEdgePropertyType(rate1 * nbRepeat * (minIterDiff + 1))
				fifo1.dataType = fifo.dataType.clone
				fifo1.targetPortModifier = new SDFStringEdgePropertyType(SDFEdge.MODIFIER_READ_ONLY)
				fifo1.sourcePortModifier = new SDFStringEdgePropertyType(SDFEdge.MODIFIER_WRITE_ONLY)

				// Connect producers and consumers of original fifo to fork/join
				val fifoIn = subgraph.addEdge(fifo.source, fork)
				fifoIn.copyProperties(fifo)
				fifoIn.targetInterface = fifo.sourceInterface.clone
				fifoIn.propertyBean.removeProperty(SDFEdge.EDGE_DELAY)
				fifoIn.cons = new SDFIntEdgePropertyType((tgtCons * tgtRepeat))

				// Copy edge order if needed
				copyEdgeOrder(fifoIn, fifo, Side.SRC)

				val fifoOut = subgraph.addEdge(join, fifo.target)
				fifoOut.copyProperties(fifo)
				fifoOut.targetInterface = fifo.targetInterface.clone
				fifoOut.propertyBean.removeProperty(SDFEdge.EDGE_DELAY)
				fifoOut.prod = new SDFIntEdgePropertyType((tgtCons * tgtRepeat))

				// Copy edge order if needed
				copyEdgeOrder(fifoOut, fifo, Side.TGT)

				// Remove original FIFO from the graph
				subgraph.removeEdge(fifo)
			}
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
						'''Input interface «interface.name» in subgraph «subgraph.name» is connected to multiple FIFOs although this is strictly forbidden.''');
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
					edgeIn.cons = new SDFIntEdgePropertyType(prodRate)

					edgeOut.copyProperties(outEdge)
					edgeOut.prod = new SDFIntEdgePropertyType(consRate * nbRepeatCons)
					edgeOut.propertyBean.removeProperty(SDFEdge.SOURCE_PORT_MODIFIER);
					edgeOut.sourceInterface = new SDFSinkInterfaceVertex
					edgeOut.sourceInterface.name = interface.name + "_0_0"

					// Copy edge order if needed
					copyEdgeOrder(edgeOut, outEdge, Side.TGT)

					// Remove the original edge
					subgraph.removeEdge(outEdge)
				}
			} else { // interface instanceof SDFSinkInterfaceVertex
			// Get predecessor
				val inEdges = subgraph.incomingEdgesOf(interface)
				if (inEdges.size > 1) {
					throw new SDF4JException(
						'''Output interface «interface.name» in subgraph «subgraph.name» is connected to multiple FIFOs although this is strictly forbidden.''')
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
					edgeOut.sourcePortModifier = new SDFStringEdgePropertyType(SDFEdge.MODIFIER_WRITE_ONLY)
					edgeOut.prod = new SDFIntEdgePropertyType(consRate)
					edgeOut.delay = new SDFIntEdgePropertyType(0)

					edgeIn.copyProperties(inEdge)
					edgeIn.cons = new SDFIntEdgePropertyType(prodRate * nbRepeatProd)
					edgeIn.propertyBean.removeProperty(SDFEdge.TARGET_PORT_MODIFIER);
					edgeIn.targetInterface = new SDFSourceInterfaceVertex
					edgeIn.targetInterface.name = interface.name + "_0_0"

					// Copy edge order if needed
					copyEdgeOrder(edgeIn, inEdge, Side.SRC)

					// Remove the original edge
					subgraph.removeEdge(inEdge)
				}
			}
		}
	}

	enum Side {
		SRC,
		TGT,
		BOTH
	}

	def copyEdgeOrder(SDFEdge newEdge, SDFEdge originalEdge, Side side) {
		// Do the edges have the same source type
		if ((side == Side.SRC || side == Side.BOTH) && newEdge.source.class == originalEdge.source.class) {
			switch originalEdge.source {
				SDFBroadcastVertex: {
					val originalIndex = (originalEdge.source as SDFBroadcastVertex).getEdgeIndex(originalEdge)
					(newEdge.source as SDFBroadcastVertex).setEdgeIndex(newEdge, originalIndex)
				}
				SDFForkVertex: {
					val originalIndex = (originalEdge.source as SDFForkVertex).getEdgeIndex(originalEdge)
					(newEdge.source as SDFForkVertex).setEdgeIndex(newEdge, originalIndex)
				}
			}
		}

		// Do the edges have the same target type
		if ((side == Side.TGT || side == Side.BOTH) && newEdge.target.class == originalEdge.target.class) {
			switch originalEdge.target {
				SDFRoundBufferVertex: {
					val originalIndex = (originalEdge.target as SDFRoundBufferVertex).getEdgeIndex(originalEdge)
					(newEdge.target as SDFRoundBufferVertex).setEdgeIndex(newEdge, originalIndex)
				}
				SDFJoinVertex: {
					val originalIndex = (originalEdge.target as SDFJoinVertex).getEdgeIndex(originalEdge)
					(newEdge.target as SDFJoinVertex).setEdgeIndex(newEdge, originalIndex)
				}
			}
		}
	}

	def flattenGraph() throws SDF4JException{
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
			// - Add roundbuffers and broadcast actors next to interfaces 
			// - fork/join delays if needed
			addInterfaceSubstitutes(subgraph)
			if (!containsNoDelay && nbRepeat > 1) {
				addDelaySubstitutes(subgraph, nbRepeat)
			}

			// The subgraph is ready, put it in the top graph
			instantiateSubgraph(hierActor, subgraph)
			flattenedGraph.removeVertex(hierActor)
		}
	}

	def instantiateSubgraph(
		SDFAbstractVertex hierActor,
		SDFGraph subgraph
	) {
		// Rename actors of the subgraph
		renameSubgraphActors(hierActor, subgraph)

		// Clone all subgraph actors in the flattened graph (except interfaces)
		val clones = new LinkedHashMap
		subgraph.vertexSet.filter[!(it instanceof SDFInterfaceVertex)].forEach [
			{
				val clone = it.clone
				flattenedGraph.addVertex(clone)
				clones.put(it, clone)
			}
		]

		// Now, copy all fifos, except those connected to interfaces
		val fifoClones = new LinkedHashMap
		for (fifo : subgraph.edgeSet.filter [
			!(it.source instanceof SDFInterfaceVertex || it.target instanceof SDFInterfaceVertex)
		]) {
			val src = clones.get(fifo.source)
			val tgt = clones.get(fifo.target)
			val cloneFifo = flattenedGraph.addEdge(src, tgt)
			cloneFifo.copyProperties(fifo)

			fifoClones.put(fifo, cloneFifo)
		}

		// Connect FIFO that were connected to ports of the flattened actor
		// and those connected to interfaces in the subgraph
		for (interface : subgraph.vertexSet.filter(SDFInterfaceVertex)) {
			// Get the actor port
			val port = hierActor.getInterface(interface.name)
			val externalFifo = hierActor.getAssociatedEdge(port)

			// Connect the new FIFO
			val newFifo = if (interface instanceof SDFSourceInterfaceVertex) {
					val internalFifo = subgraph.outgoingEdgesOf(interface).get(0)
					val newFifo = flattenedGraph.addEdge(externalFifo.source, clones.get(internalFifo.target))
					newFifo.copyProperties(externalFifo)
					newFifo.cons = internalFifo.cons
					if (internalFifo.delay != null) {
						newFifo.delay = internalFifo.delay
					}
					newFifo.targetPortModifier = internalFifo.targetPortModifier
					newFifo
				} else { // if(interface instanceof SDFSinkInterfaceVertex)
					val internalFifo = subgraph.incomingEdgesOf(interface).get(0)
					val newFifo = flattenedGraph.addEdge(clones.get(internalFifo.source), externalFifo.target)
					newFifo.copyProperties(externalFifo)
					newFifo.prod = internalFifo.prod
					if (internalFifo.delay != null) {
						newFifo.delay = internalFifo.delay
					}
					newFifo.sourcePortModifier = internalFifo.sourcePortModifier
					newFifo
				}
			// Set delay of the new FIFO
			val externDelay = if(externalFifo.delay != null) externalFifo.delay.intValue else 0
			val internDelay = if(newFifo.delay != null) newFifo.delay.intValue else 0
			if (externDelay != 0) {
				newFifo.delay = new SDFIntEdgePropertyType(externDelay + internDelay)
			}
			// Copy edge order if needed
			copyEdgeOrder(newFifo, externalFifo, Side.BOTH)
		}

		// Now that all fifos are connected, check order
		fifoClones.forEach[fifo, fifoClone|copyEdgeOrder(fifoClone, fifo, Side.BOTH)]
	}

	/**
	 * Rename all actors (except interfaces) of the subgraph such that their 
	 * name is prefixed with the name of the hierarchical actor.
	 */
	def renameSubgraphActors(SDFAbstractVertex hierActor, SDFGraph subgraph) {
		for (actor : subgraph.vertexSet.filter[!(it instanceof SDFInterfaceVertex)]) {
			actor.name = '''«hierActor.name»_«actor.name»'''
		}
	}
}