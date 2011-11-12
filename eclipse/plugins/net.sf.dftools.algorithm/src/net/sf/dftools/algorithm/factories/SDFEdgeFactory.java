package net.sf.dftools.algorithm.factories;

import org.jgrapht.EdgeFactory;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.types.SDFIntEdgePropertyType;
import net.sf.dftools.algorithm.model.sdf.types.SDFStringEdgePropertyType;

/**
 * Class used as an EdgeFactory to provides SDFAbstractGraph with convenient
 * method to create Edges
 * 
 * @author jpiat
 * 
 */
public class SDFEdgeFactory implements EdgeFactory<SDFAbstractVertex, SDFEdge> {

	/**
	 * Create a new SDEdge
	 */
	@Override
	public SDFEdge createEdge(SDFAbstractVertex arg0, SDFAbstractVertex arg1) {
		return new SDFEdge(new SDFIntEdgePropertyType(1),
				new SDFIntEdgePropertyType(1),
				new SDFIntEdgePropertyType(0),
				new SDFStringEdgePropertyType("char"));
	}

}
