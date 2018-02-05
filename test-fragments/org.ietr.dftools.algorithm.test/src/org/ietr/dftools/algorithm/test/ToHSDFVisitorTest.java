/**
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2018) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017 - 2018)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2012)
 * Julien Heulot <julien.heulot@insa-rennes.fr> (2015)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2012 - 2016)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011 - 2014)
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
package org.ietr.dftools.algorithm.test;

import org.ietr.dftools.algorithm.model.parameters.ConstantValue;
import org.ietr.dftools.algorithm.model.parameters.ExpressionValue;
import org.ietr.dftools.algorithm.model.parameters.Variable;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFExpressionEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.visitors.ToHSDFVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.junit.Test;

/**
 */
public class ToHSDFVisitorTest {

  /**
   * Test the visitor.
   */
  @Test
  public void testConvert() {
    final SDFGraph demoGraph = createTestComGraph();
    final ToHSDFVisitor visitor = new ToHSDFVisitor();
    try {
      demoGraph.accept(visitor);
    } catch (final SDF4JException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates the test com graph.
   *
   * @return the SDF graph
   */
  public static SDFGraph createTestComGraph() {

    final SDFGraph graph = new SDFGraph();
    graph.setName("'test graph'");

    final SDFVertex sensorInt = new SDFVertex();
    sensorInt.setName("sensorInt");
    graph.addVertex(sensorInt);

    final SDFVertex gen5 = new SDFVertex();
    gen5.setName("gen5");
    graph.addVertex(gen5);

    final SDFVertex recopie5 = new SDFVertex();
    recopie5.setName("recopie5");
    graph.addVertex(recopie5);

    final SDFVertex acqData = new SDFVertex();
    acqData.setName("acqData");
    graph.addVertex(acqData);

    final SDFEdge sensGen = graph.addEdge(sensorInt, gen5);
    // sensGen.setTargetInterface(add);
    sensGen.setProd(new SDFIntEdgePropertyType(1));
    sensGen.setCons(new SDFIntEdgePropertyType(1));

    final SDFEdge genRec = graph.addEdge(gen5, recopie5);
    // genRec.setSourceInterface(times);
    genRec.setProd(new SDFExpressionEdgePropertyType(new ExpressionValue("SIZE")));
    genRec.setCons(new SDFExpressionEdgePropertyType(new ConstantValue(3)));

    final SDFEdge genAcq = graph.addEdge(gen5, acqData);
    // genAcq.setSourceInterface(times);
    genAcq.setProd(new SDFIntEdgePropertyType(1));
    genAcq.setCons(new SDFIntEdgePropertyType(1));

    final SDFEdge recAcq = graph.addEdgeWithInterfaces(recopie5, acqData);
    recAcq.setProd(new SDFIntEdgePropertyType(3));
    recAcq.setCons(new SDFIntEdgePropertyType(2));

    graph.addVariable(new Variable("a", "5"));
    graph.addVariable(new Variable("b", "10"));
    graph.addVariable(new Variable("SIZE", "2"));

    return graph;
  }
}
