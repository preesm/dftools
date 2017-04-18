/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
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
package org.ietr.dftools.algorithm.demo;

import org.ietr.dftools.algorithm.generator.SDFRandomGraph;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.visitors.ToHSDFVisitor;
import org.ietr.dftools.algorithm.model.sdf.visitors.TopologyVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;

// TODO: Auto-generated Javadoc
/**
 * Demo class to demonstrate the display features of a random graph.
 *
 * @author pthebault
 */
public class SDFGeneratorDemo {

  /**
   * An alternative starting point for this test, to also allow running this applet as an application.
   *
   * @param args
   *          ignored.
   * @throws InvalidExpressionException
   *           the invalid expression exception
   * @throws SDF4JException
   *           the SDF 4 J exception
   */

  public static void main(final String[] args) throws InvalidExpressionException, SDF4JException {
    final int nbVertex = 10;
    final int minInDegree = 1;
    final int maxInDegree = 5;
    final int minOutDegree = 1;
    final int maxOutDegree = 5;
    final SDFAdapterDemo applet = new SDFAdapterDemo();

    // Creates a random SDF graph
    final int minrate = 1;
    final int maxrate = 4;
    final SDFRandomGraph test = new SDFRandomGraph();
    final SDFGraph demoGraph = test.createRandomGraph(nbVertex, minInDegree, maxInDegree, minOutDegree, maxOutDegree, minrate, maxrate);

    final TopologyVisitor topo = new TopologyVisitor();
    try {
      demoGraph.accept(topo);
    } catch (final SDF4JException e) {
      e.printStackTrace();
    }
    applet.init(demoGraph);

    final SDFAdapterDemo applet3 = new SDFAdapterDemo();
    final ToHSDFVisitor visitor2 = new ToHSDFVisitor();
    try {
      demoGraph.accept(visitor2);
    } catch (final SDF4JException e) {
      e.printStackTrace();
    }
    applet3.init(visitor2.getOutput());

  }

}
