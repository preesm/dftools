/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014 - 2015)
 * Jonathan Piat <jpiat@laas.fr> (2011)
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.ietr.dftools.algorithm.iterators.SDFIterator;
import org.ietr.dftools.algorithm.model.listenable.SDFListenableGraph;
import org.ietr.dftools.algorithm.model.parameters.InvalidExpressionException;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;
import org.ietr.dftools.algorithm.model.sdf.SDFGraph;
import org.ietr.dftools.algorithm.model.sdf.SDFVertex;
import org.ietr.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import org.ietr.dftools.algorithm.model.sdf.visitors.ToHSDFVisitor;
import org.ietr.dftools.algorithm.model.sdf.visitors.TopologyVisitor;
import org.ietr.dftools.algorithm.model.visitors.SDF4JException;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

/**
 * Demo class to demonstrate the display features of this package
 * 
 * @author jpiat
 * 
 */
public class SDFAdapterDemo extends JApplet {
	// ~ Static fields/initializers
	// ---------------------------------------------

	/**
	 * Static field containing al the instances of this clas
	 */
	public static Vector<SDFAdapterDemo> adapters = new Vector<SDFAdapterDemo>();
	protected static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	protected static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
	
	/**
	 * Property name for the vertex color
	 */
	public static final String VERTEX_COLOR = "vertex_color" ;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 853642557926207725L;

	// ~ Instance fields
	// --------------------------------------------------------

	/**
	 * Statically creates a Hierarchical graph for testing purpose
	 * 
	 * @return The hierarchical graph created
	 */
	public static SDFGraph createHierachicalGraph() {

		SDFGraph graph = new SDFGraph();

		// test_com_basique
		SDFVertex sensorInt = new SDFVertex();
		sensorInt.setName("sensor_Int");
		graph.addVertex(sensorInt);

		SDFVertex gen5 = new SDFVertex();
		gen5.setName("Gen5");
		graph.addVertex(gen5);

		// hirearchical vertex
		SDFVertex recopie5Hier = new SDFVertex() ;
		recopie5Hier.setName("recopie_5_Hier");
		graph.addVertex(recopie5Hier);

		SDFVertex acqData = new SDFVertex();
		acqData.setName("acq_data");
		graph.addVertex(acqData);

		SDFVertex sensorIntBis = new SDFVertex();
		sensorIntBis.setName("sensor_Int_Bis");
		graph.addVertex(sensorIntBis);

		/*
		 * SDFAtomicVertex gen5Bis = new SDFAtomicVertex();
		 * gen5Bis.setName("Gen5"); graph.addVertex(gen5Bis);
		 */

		SDFEdge sensGenBis = graph.addEdge(sensorIntBis, gen5);
		sensGenBis.setProd(new SDFIntEdgePropertyType(3));
		sensGenBis.setCons(new SDFIntEdgePropertyType(2));

		SDFEdge sensGen = graph.addEdge(sensorInt, gen5);
		sensGen.setProd(new SDFIntEdgePropertyType(1));
		sensGen.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge genRec = graph.addEdge(gen5, recopie5Hier);
		genRec.setProd(new SDFIntEdgePropertyType(100));
		genRec.setCons(new SDFIntEdgePropertyType(-1));

		SDFEdge genAcq = graph.addEdge(gen5, acqData);
		genAcq.setProd(new SDFIntEdgePropertyType(1));
		genAcq.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge recAcq = graph.addEdge(recopie5Hier, acqData);
		recAcq.setProd(new SDFIntEdgePropertyType(-1));
		recAcq.setCons(new SDFIntEdgePropertyType(100));

		return graph;
	}

	/**
	 * Creates a graph representing the Test Com application
	 * 
	 * @return the created graph
	 */
	public static SDFGraph createTestComGraph() {

		SDFGraph graph = new SDFGraph();

		// test_com_basique
		SDFVertex sensorInt = new SDFVertex();
		sensorInt.setName("sensorInt");
		graph.addVertex(sensorInt);

		SDFVertex gen5 = new SDFVertex();
		gen5.setName("Gen");
		graph.addVertex(gen5);

		SDFVertex recopie5 = new SDFVertex();
		recopie5.setName("Copy");
		graph.addVertex(recopie5);

		SDFVertex acqData = new SDFVertex();
		acqData.setName("acqData");
		graph.addVertex(acqData);

		SDFEdge sensGen = graph.addEdgeWithInterfaces(sensorInt, gen5);
		sensGen.setProd(new SDFIntEdgePropertyType(1));
		sensGen.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge genRec = graph.addEdgeWithInterfaces(gen5, recopie5);
		genRec.setProd(new SDFIntEdgePropertyType(2));
		genRec.setCons(new SDFIntEdgePropertyType(3));

		SDFEdge genAcq = graph.addEdgeWithInterfaces(gen5, acqData);
		genAcq.setProd(new SDFIntEdgePropertyType(1));
		genAcq.setCons(new SDFIntEdgePropertyType(1));

		SDFEdge recAcq = graph.addEdgeWithInterfaces(recopie5, acqData);
		recAcq.setProd(new SDFIntEdgePropertyType(3));
		recAcq.setCons(new SDFIntEdgePropertyType(2));

		return graph;
	}

	/**
	 * Statically creates a Graph with multi-source for testing purposes
	 * 
	 * @return The test graph
	 */
	public static SDFGraph createTestMultiSourceGraph() {

		SDFGraph graph = new SDFGraph();

		// test_com_basique
		SDFVertex sensorInt = new SDFVertex();
		sensorInt.setName("sensor_Int");
		graph.addVertex(sensorInt);

		SDFVertex sensorIntBis = new SDFVertex();
		sensorIntBis.setName("sensor_Int_Bis");
		graph.addVertex(sensorIntBis);

		SDFVertex gen5 = new SDFVertex();
		gen5.setName("Gen5");
		graph.addVertex(gen5);

		SDFVertex acqData = new SDFVertex();
		acqData.setName("acq_data");
		graph.addVertex(acqData);

		SDFEdge sensGen = graph.addEdge(sensorInt, gen5);
		sensGen.setProd(new SDFIntEdgePropertyType(3));
		sensGen.setCons(new SDFIntEdgePropertyType(2));

		SDFEdge genAcq = graph.addEdge(gen5, acqData);
		genAcq.setProd(new SDFIntEdgePropertyType(1));
		genAcq.setCons(new SDFIntEdgePropertyType(1));

		return graph;
	}

	// ~ Methods
	// ----------------------------------------------------------------

	/**
	 * An alternative starting point for this demo, to also allow running this
	 * applet as an application.
	 * 
	 * @param args
	 *            ignored.
	 * @throws InvalidExpressionException 
	 */
	public static void main(String[] args){
		SDFAdapterDemo applet = new SDFAdapterDemo();
		SDFGraph demoGraph = createTestComGraph();
		TopologyVisitor topo = new TopologyVisitor();
		try {
			demoGraph.accept(topo);
		} catch (SDF4JException e) {
			e.printStackTrace();
		}
		applet.init(demoGraph);

		SDFAdapterDemo applet3 = new SDFAdapterDemo();
		ToHSDFVisitor visitor2 = new ToHSDFVisitor();
		try {
			demoGraph.accept(visitor2);
		} catch (SDF4JException e) {
			e.printStackTrace();
		}
		applet3.init(visitor2.getOutput());

	}

	//
	@SuppressWarnings("rawtypes")
	protected JGraphModelAdapter jgAdapter;

	private SDFListenableGraph model;

	/**
	 * Creates a new AdapterDemo
	 */
	public SDFAdapterDemo() {
		adapters.add(this);
	}

	protected void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);

		Color c = DEFAULT_BG_COLOR;
		String colorStr = null;

		try {
			colorStr = getParameter("bgcolor");
		} catch (Exception e) {
		}

		if (colorStr != null) {
			c = Color.decode(colorStr);
		}

		jg.setBackground(c);
	}

	/**
	 * Initialize a created SDFAdpaterDemo with the given Graph to display
	 * 
	 * @param graphIn
	 *            The graph to display
	 * @throws InvalidExpressionException 
	 */
	public void init(SDFGraph graphIn){

		SDFGraph graph = graphIn.clone();
		// create a JGraphT graph
		model = new SDFListenableGraph();

		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<SDFAbstractVertex, SDFEdge>(model);

		JGraph jgraph = new JGraph(jgAdapter);

		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);
		System.out.println(" graph has " + graph.vertexSet().size()
				+ " vertice, including broadcast");
		for (SDFAbstractVertex vertex : graph.vertexSet()) {
			model.addVertex(vertex);
			if(vertex.getPropertyBean().getValue(VERTEX_COLOR) != null){
				this.setVertexColor(vertex, (Color) vertex.getPropertyBean().getValue(VERTEX_COLOR));
			}
		}

		for (SDFEdge edge : graph.edgeSet()) {
			SDFEdge newEdge = model.addEdge(graph.getEdgeSource(edge), graph
					.getEdgeTarget(edge));
			newEdge.setProd(edge.getProd());
			newEdge.setCons(edge.getCons());
			newEdge.setDelay(edge.getDelay());
		}

		CycleDetector<SDFAbstractVertex, SDFEdge> detector = new CycleDetector<SDFAbstractVertex, SDFEdge>(
				model);
		GraphIterator<SDFAbstractVertex, SDFEdge> order;
		if (detector.detectCycles()) {
			try {
				order = new SDFIterator(model);
			} catch (InvalidExpressionException e) {
				e.printStackTrace();
				order = new TopologicalOrderIterator<SDFAbstractVertex, SDFEdge>(
						model);
			}
		} else {
			order = new TopologicalOrderIterator<SDFAbstractVertex, SDFEdge>(
					model);
		}

		Vector<SDFAbstractVertex> vertices = new Vector<SDFAbstractVertex>();
		int x = 0;
		int y = 100;
		int ymax = y;
		SDFAbstractVertex previousVertex = null;
		while (order.hasNext()) {
			SDFAbstractVertex nextVertex = order.next();
			vertices.add(nextVertex);
			if (previousVertex != null
					&& model.getEdge(nextVertex, previousVertex) == null
					&& model.getEdge(previousVertex, nextVertex) == null) {
				y += 50;
				this.positionVertexAt(nextVertex, x, y);
				if (y > ymax) {
					ymax = y;
				}
			} else {
				y = 100;
				x += 200;
				this.positionVertexAt(nextVertex, x, 100);
				previousVertex = nextVertex;
			}
		}

		JFrame frame = new JFrame();
		jgraph.setPreferredSize(new Dimension(x + 200, ymax + 300));
		frame.setContentPane(new ScrollPane());
		frame.getContentPane().add(this);
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		if (adapters.size() == 1) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		}
		frame.pack();
		frame.setVisible(true);
	}

	
	@SuppressWarnings("unchecked")
	// FIXME hb 28-nov-05: See FIXME below
	protected void positionVertexAt(Object vertex, int x, int y) {
		DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
		
		AttributeMap attr = cell.getAttributes();
		Rectangle2D bounds = GraphConstants.getBounds(attr);


		Rectangle2D newBounds = new Rectangle2D.Double(x, y, bounds.getWidth(),
				bounds.getHeight());

		GraphConstants.setBounds(attr, newBounds);

		// FIXME: Clean up generics once JGraph goes generic
		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}
	
	/**
	 * Sets the given vertex's color
	 * @param vertex
	 * @param col
	 */
	public void setVertexColor(Object vertex, Color col){
		DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
		AttributeMap attr = cell.getAttributes();
		GraphConstants.setBackground(attr, col);
	}

}
