package net.sf.dftools.algorithm.factories;

import java.util.HashMap;

import net.sf.dftools.algorithm.model.AbstractVertex;
import net.sf.dftools.algorithm.model.psdf.PSDFInitVertex;
import net.sf.dftools.algorithm.model.psdf.PSDFSubInitVertex;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;

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
	public SDFAbstractVertex createVertex(HashMap<String, String> attributes) {
		String kind = attributes.get("kind");
		if (SDFVertexFactory.getInstance().createVertex(attributes) != null) {
			return SDFVertexFactory.getInstance().createVertex(attributes);
		}
		if (kind.equals(PSDFInitVertex.INIT)) {
			PSDFInitVertex newVertex = new PSDFInitVertex();
			newVertex.setName("default");
			return newVertex;
		} else if (kind.equals(PSDFSubInitVertex.SUB_INIT)) {
			PSDFSubInitVertex newVertex = new PSDFSubInitVertex();
			newVertex.setName("default");
			return newVertex;
		}
		return null;
	}

	public SDFAbstractVertex createVertex(Element vertexElt) {
		String kind = this.getProperty(vertexElt, AbstractVertex.KIND);
		if (SDFVertexFactory.getInstance().createVertex(vertexElt) != null) {
			return SDFVertexFactory.getInstance().createVertex(vertexElt);
		}
		if (kind.equals(PSDFInitVertex.INIT)) {
			PSDFInitVertex newVertex = new PSDFInitVertex();
			newVertex.setName("default");
			return newVertex;
		} else if (kind.equals(PSDFSubInitVertex.SUB_INIT)) {
			PSDFSubInitVertex newVertex = new PSDFSubInitVertex();
			newVertex.setName("default");
			return newVertex;
		}
		return null;
	}

	@Override
	public SDFAbstractVertex createVertex(String kind) {
		// TODO Auto-generated method stub
		return null;
	}

}
