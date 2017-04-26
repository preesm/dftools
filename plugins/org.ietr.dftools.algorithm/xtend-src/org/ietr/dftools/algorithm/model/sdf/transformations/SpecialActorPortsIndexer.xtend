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

import java.util.List
import java.util.regex.Pattern
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex
import org.ietr.dftools.algorithm.model.sdf.SDFEdge
import org.ietr.dftools.algorithm.model.sdf.SDFGraph
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFBroadcastVertex
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFForkVertex
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFJoinVertex
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFRoundBufferVertex
import org.ietr.dftools.algorithm.model.AbstractGraph

/**
 * The purpose of this class is to automatically add indexes to all ordered
 * ports of special actors (Fork, Join, RoundBuffer, Broadcast).
 *
 * @author kdesnos
 */
class SpecialActorPortsIndexer {

	/**
	 * Regular expression used on the name of a port in order to extract its
	 * index(es). Explanation:<ul>
	 * <li><code>.*?</code>: matches as many characters as possible (0..*)
	 * corresponding to the name of the port</li>
	 * <li><code>(_([0-9]*))?</code>: matches an underscore followed by a
	 * number corresponding to the YY index. (optional match)</li>
	 * <li><code>_([0-9]*)\\z</code>: matches an underscore followed by a
	 * number corresponding to the XX index, immediately before the end of the
	 * matched string. (mandatory match)</li></ul>
	 */
	public static val String indexRegex = ".*?(_([0-9]*))?_([0-9]*)\\z"

	/**
	 * Group of the XX index in the {@link #indexRegex}
	 */
	public static val int groupXX = 3

	/**
	 * Group of the YY index in the {@link #indexRegex}
	 */
	public static val int groupYY = 2

	/**
	 * This method rename all ordered ports of special actors in the following
	 * fashion:
	 * <ul><li>Join and Fork: <code>original_port_name_XX</code> where XX is
	 * the index of the first data token accessed by this edge (out of the
	 * total number of data tokens fork/joined)</li>
	 * <li><li>Broadcast and RoundBuffer: <code>original_port_name_YY_XX</code
	 * > where XX is the index of the first data token accessed by this edge (
	 * out of the total number of data tokens broadcasted/roundbuffered) and
	 * YY is the absolute index of this first token if the total number of
	 * tokens distributed/kept by the broadcast/roundbuffer is not considered
	 * as a modulo.</li></ul>
	 *
	 * @param graph {@link SDFGraph} whose special actor ports are renamed.
	 * Subgraph associated to actors of this graph will also be processed.
	 */
	static def void addIndexes(SDFGraph sdfGraph) {
		// Initialize the list of processed graph with the given one.
		val processedGraphs = newArrayList
		processedGraphs.add(sdfGraph)

		for (var i = 0; i < processedGraphs.size(); i++) {
			val graph = processedGraphs.get(i)

			for (actor : graph.vertexSet()) {
				val AbstractGraph<?,?> actorGraphDesc = actor.graphDescription
				// If the actor is hierarchical, add its subgraph to the list of graphs to process
				if (actorGraphDesc !== null) {
					processedGraphs.add(actorGraphDesc as SDFGraph)
				}

				// Check if the ports already have indexed names
				// In such a case, index names are assumed to be valid and will
				// be used to sort the ports of the actor.
				// This should be documented somewhere (in a tutorial)
				val alreadyIndexed = checkIndexes(actor)

				// Add an index to the ports only if they are not already present
				if (!alreadyIndexed) {
					// Get ordered Fifos of special actors
					var isSource = true;
					var modulo = 0;
					val fifos = switch (actor) {
						SDFJoinVertex: {
							isSource = false
							actor.incomingConnections
						}
						SDFRoundBufferVertex: {
							isSource = false
							modulo = graph.outgoingEdgesOf(actor).get(0).prod.intValue
							actor.incomingConnections
						}
						SDFForkVertex:
							actor.outgoingConnections
						SDFBroadcastVertex: {
							modulo = graph.incomingEdgesOf(actor).get(0).cons.intValue
							actor.outgoingConnections
						}
						default:
							newArrayList
					}

					var indexX = 0
					var indexY = 0
					for (fifo : fifos) {
						val indexString = '''«IF modulo>0»«indexY»_«ENDIF»«indexX»'''
						if (isSource) {
							fifo.sourceInterface.name = '''«fifo.sourceInterface.name»_«indexString»'''
							indexY += fifo.prod.intValue
						} else {
							fifo.targetInterface.name = '''«fifo.targetInterface.name»_«indexString»'''
							indexY += fifo.cons.intValue
						}
						indexX = if(modulo > 0) indexY % modulo else indexY
					}
				}
			}
		}
	}

	/**
	 * Calls the checkIndexes(SDFAbstractVertex actor) method for all special
	 * actors of this graph and all actors of its subgraphs
	 */
	def static boolean checkIndexes(SDFGraph sdfGraph) {
		var result = true

		// Initialize the list of processed graph with the given one.
		val processedGraphs = newArrayList
		processedGraphs.add(sdfGraph)

		for (var i = 0; i < processedGraphs.size(); i++) {
			val graph = processedGraphs.get(i)

			for (actor : graph.vertexSet()) {
				val AbstractGraph<?,?> actorGraphDesc = actor.graphDescription
				// If the actor is hierarchical, add its subgraph to the list of graphs to process
				if (actorGraphDesc !== null) {
					processedGraphs.add(actorGraphDesc as SDFGraph)
				}

				// Check only special actors
				switch (actor) {
					SDFJoinVertex,
					SDFForkVertex,
					SDFRoundBufferVertex,
					SDFBroadcastVertex: {
						result = result && checkIndexes(actor)
						if (! result) {
							return result
						}
					}
				}

			}
		}
		return result
	}

	/**
	 * This method checks if the ports the given {@link SDFAbstractVertex}
	 * passed as a parameter are already indexed.
	 *
	 * @return <code>true</code> if the actor is a special actor and its
	 * ports are already indexed, <code>false</code> otherwise.
	 */
	def static boolean checkIndexes(SDFAbstractVertex actor) {
		var isSource = true;

		val fifos = switch (actor) {
			SDFJoinVertex: {
				isSource = false
				actor.incomingConnections
			}
			SDFRoundBufferVertex: {
				isSource = false
				actor.incomingConnections
			}
			SDFForkVertex:
				actor.outgoingConnections
			SDFBroadcastVertex: {
				actor.outgoingConnections
			}
			default:
				newArrayList
		}

		val valIsSource = isSource
		return checkIndexes(fifos, valIsSource)
	}

	/**
	 * Check wether all {@link SDFEdge} in the given {@link List} have a valid
	 * index.
	 *
	 * @param valIsSource whether the source or target ports of SDFEdge are
	 * considered
	 *
	 * @return <code>true</code> if the list of SDFEdge is not empty and if its
	 * ports are already indexed, <code>false</code> otherwise.
	 */
	protected def static boolean checkIndexes(List<SDFEdge> fifos, boolean valIsSource) {
		return fifos.forall [
			val name = if(valIsSource) it.sourceInterface.name else it.targetInterface.name
			name.matches(indexRegex)
		] && !fifos.empty
	}

	/**
	 * Sort all indexed ports of the special actors of the graph.
	 */
	def static void sortIndexedPorts(SDFGraph sdfGraph) {

		// Initialize the list of processed graph with the given one.
		val processedGraphs = newArrayList
		processedGraphs.add(sdfGraph)

		for (var i = 0; i < processedGraphs.size(); i++) {
			val graph = processedGraphs.get(i)

			for (actor : graph.vertexSet()) {
				val AbstractGraph<?,?> actorGraphDesc = actor.graphDescription
				// If the actor is hierarchical, add its subgraph to the list of graphs to process
				if (actorGraphDesc !== null) {
					processedGraphs.add(actorGraphDesc as SDFGraph)
				}

				val alreadyIndexed = checkIndexes(actor)

				// If the actor is special, and its ports are indexed
				if (alreadyIndexed) {
					var isSource = true;

					val fifos = switch (actor) {
						SDFJoinVertex: {
							isSource = false
							actor.incomingConnections
						}
						SDFRoundBufferVertex: {
							isSource = false
							actor.incomingConnections
						}
						SDFForkVertex:
							actor.outgoingConnections
						SDFBroadcastVertex: {
							actor.outgoingConnections
						}
						default:
							newArrayList
					}

					val valIsSource = isSource
					// Sort the FIFOs according to their indexes
					sortFifoList(fifos, valIsSource)

					// Apply this new order to the edges
					var order = 0
					for (fifo : fifos) {
						// Switch and implicit cast for each type
						switch (actor) {
							SDFJoinVertex:
								actor.setEdgeIndex(fifo, order)
							SDFRoundBufferVertex:
								actor.setEdgeIndex(fifo, order)
							SDFForkVertex:
								actor.setEdgeIndex(fifo, order)
							SDFBroadcastVertex: {
								actor.setEdgeIndex(fifo, order)
							}
						}

						order++
					}
				}
			}
		}
	}

	/**
	 * Sort a {@link List} of {@link SDFEdge} according to their port index.
	 * The source or target ports will be considered depending on the valIs
	 * Source boolean.
	 */
	def static void sortFifoList(List<SDFEdge> fifos, boolean valIsSource) {
		// Check that all fifos have an index
		if (checkIndexes(fifos, valIsSource)) {
			// If indexes are valid, do the sort
			fifos.sort [ fifo0, fifo1 |
				{
					// Get the port names
					val p0Name = if(valIsSource) fifo0.sourceInterface.name else fifo0.targetInterface.name
					val p1Name = if(valIsSource) fifo1.sourceInterface.name else fifo1.targetInterface.name

					// Compile and apply the pattern
					val pattern = Pattern.compile(indexRegex)
					val m0 = pattern.matcher(p0Name)
					val m1 = pattern.matcher(p1Name)
					m0.find
					m1.find

					// Retrieve the indexes
					val yy0 = if(m0.group(groupYY) !== null) Integer.decode(m0.group(groupYY)) else 0
					val yy1 = if(m1.group(groupYY) !== null) Integer.decode(m1.group(groupYY)) else 0
					val xx0 = Integer.decode(m0.group(groupXX))
					val xx1 = Integer.decode(m1.group(groupXX))

					// Sort according to yy indexes if they are different,
					// and according to xx indexes otherwise
					if(yy0 != yy1) yy0 - yy1 else xx0 - xx1
				}
			]
		}
	}
}