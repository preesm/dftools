package net.sf.dftools.algorithm.factories;

import net.sf.dftools.algorithm.model.psdf.PSDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.sdf.types.SDFStringEdgePropertyType;

import org.jgrapht.EdgeFactory;

/**
 * Class used as an EdgeFactory to provides SDFAbstractGraph with convenient
 * method to create Edges
 * 
 * @author jpiat
 * 
 */
public class PSDFEdgeFactory implements EdgeFactory<SDFAbstractVertex, SDFEdge> {

	/**
	 * Create a new SDEdge
	 */
	@Override
	public PSDFEdge createEdge(SDFAbstractVertex arg0, SDFAbstractVertex arg1) {
		return new PSDFEdge(new SDFIntEdgePropertyType(1),
				new SDFIntEdgePropertyType(1),
				new SDFIntEdgePropertyType(0),
				new SDFStringEdgePropertyType("char"));
	}

}
