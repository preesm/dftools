/**
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Jonathan Piat <jpiat@laas.fr> (2011)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to help prototyping
 * parallel applications using dataflow formalism.
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
 */
package org.ietr.dftools.algorithm.demo;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.jgrapht.alg.CycleDetector;

// TODO: Auto-generated Javadoc
/**
 * Class for testing purposes.
 *
 * @author jpiat
 */
public class SDFTest {

  /**
   * Main method of the class.
   *
   * @param args
   *          the arguments
   * @throws SDF4JException
   *           the SDF 4 J exception
   */
  public static void main(final String[] args) throws SDF4JException {
    final SDFGraph graph = new SDFGraph();

    final SDFVertex vertex1 = new SDFVertex();
    vertex1.setName("V1");
    graph.addVertex(vertex1);

    final SDFVertex vertex2 = new SDFVertex();
    vertex2.setName("V2");
    graph.addVertex(vertex2);

    /*
     * SDFVertex vertex3 = new SDFVertex(); vertex3.setName("V3"); graph.addVertex(vertex3);
     */

    /*
     * SDFVertex vertex4 = new SDFVertex(); graph.addVertex(vertex4);
     */

    final SDFEdge edge1 = graph.addEdge(vertex1, vertex2);
    edge1.setProd(new SDFIntEdgePropertyType(999));
    edge1.setCons(new SDFIntEdgePropertyType(1000));

    /*
     * SDFEdge edge2 = graph.addEdge(vertex2, vertex3); edge2.setProd(3); edge2.setCons(2);
     */

    /*
     * SDFEdge edge3 = graph.addEdge(vertex3, vertex3); edge3.setProd(1); edge3.setCons(1);
     */

    /*
     * SDFEdge edge4 = graph.addEdge(vertex4, vertex1); edge4.setProd(3);
     */

    System.out.println("Is schedulable : " + graph.isSchedulable());
    final CycleDetector<SDFAbstractVertex, SDFEdge> detectCycles = new CycleDetector<>(graph);
    System.out.println("As cycles :" + detectCycles.detectCycles());

    System.out.println("As nb cycles :" + detectCycles.findCycles().size());

  }
}
