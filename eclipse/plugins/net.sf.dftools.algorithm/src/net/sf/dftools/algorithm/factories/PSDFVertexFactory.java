package net.sf.dftools.algorithm.factories;

import java.util.HashMap;

import net.sf.dftools.algorithm.model.ModelVertexFactory;
import net.sf.dftools.algorithm.model.psdf.PSDFInitVertex;
import net.sf.dftools.algorithm.model.psdf.PSDFSubInitVertex;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;

public class PSDFVertexFactory  implements ModelVertexFactory<SDFAbstractVertex> {
	
	/**
	 * Creates a vertex with the given parameters
	 * 
	 * @param attributes
	 *            The attributes of the vertex
	 * @return The created vertex
	 */
	public static SDFAbstractVertex createVertex(
			HashMap<String, String> attributes) {
		String kind = attributes.get("kind");
		if(SDFVertexFactory.createVertex(attributes)!= null){
			return SDFVertexFactory.createVertex(attributes);
		}
		if (kind.equals(PSDFInitVertex.INIT)) {
			PSDFInitVertex newVertex = new PSDFInitVertex();
			newVertex.setName("default");
			return newVertex;
		}else if (kind.equals(PSDFSubInitVertex.SUB_INIT)) {
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
