package org.ietr.dftools.algorithm.factories;

import java.util.HashMap;

import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.IInterface;
import org.ietr.dftools.algorithm.model.psdf.PSDFInitVertex;
import org.ietr.dftools.algorithm.model.psdf.PSDFSubInitVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSinkInterfaceVertex;
import org.ietr.dftools.algorithm.model.sdf.esdf.SDFSourceInterfaceVertex;
import org.w3c.dom.Element;

public class PSDFVertexFactory extends ModelVertexFactory<SDFAbstractVertex> {

	private static PSDFVertexFactory instance;

	private PSDFVertexFactory() {

	}

	public static PSDFVertexFactory getInstance() {
		if (instance == null) {
			instance = new PSDFVertexFactory();
		}
		return instance;
	}

	/**
	 * @deprecated
	 * 
	 *             Creates a vertex with the given parameters Used when kind was
	 *             a node attribute, now a property
	 * 
	 * @param attributes
	 *            The attributes of the vertex
	 * @return The created vertex
	 */
	@SuppressWarnings("deprecation")
	public SDFAbstractVertex createVertex(HashMap<String, String> attributes) {
		String kind = attributes.get("kind");
		if (kind.equals(PSDFInitVertex.INIT)) {
			PSDFInitVertex newVertex = new PSDFInitVertex();
			newVertex.setName("default");
			return newVertex;
		} else if (kind.equals(PSDFSubInitVertex.SUB_INIT)) {
			PSDFSubInitVertex newVertex = new PSDFSubInitVertex();
			newVertex.setName("default");
			return newVertex;
		} else {
			return SDFVertexFactory.getInstance().createVertex(attributes);
		}
	}

	public SDFAbstractVertex createVertex(Element vertexElt) {
		String kind = this.getProperty(vertexElt, AbstractVertex.KIND);
		if (kind.equals(PSDFInitVertex.INIT)) {
			PSDFInitVertex newVertex = new PSDFInitVertex();
			newVertex.setName("default");
			return newVertex;
		} else if (kind.equals(PSDFSubInitVertex.SUB_INIT)) {
			PSDFSubInitVertex newVertex = new PSDFSubInitVertex();
			newVertex.setName("default");
			return newVertex;
		} else {
			return SDFVertexFactory.getInstance().createVertex(vertexElt);
		}
	}

	@Override
	public SDFAbstractVertex createVertex(String kind) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IInterface createInterface(String name, int dir) {
		IInterface port;
		if (dir == 1) {
			port = new SDFSinkInterfaceVertex();
		} else {
			port = new SDFSourceInterfaceVertex();
		}
		port.setName(name);
		return port;
	}

}
