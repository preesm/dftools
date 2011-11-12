package net.sf.dftools.algorithm.importer;

import net.sf.dftools.algorithm.factories.SDFEdgeFactory;
import net.sf.dftools.algorithm.model.PropertySource;
import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;
import org.w3c.dom.Element;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GMLGenericImporter extends GMLImporter{

	private GMLImporter<SDFGraph,SDFAbstractVertex,SDFEdge> importer ; 
	
	public GMLGenericImporter(){
		super(new SDFEdgeFactory()) ;
	}

	@Override
	public void parseEdge(Element edgeElt, PropertySource parentGraph) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SDFGraph parseGraph(Element graphElt) {
		if(graphElt.getAttribute("kind") != null && graphElt.getAttribute("kind").equals("sdf")){
			importer = new GMLSDFImporter() ;
		}else if(graphElt.getAttribute("kind") != null && graphElt.getAttribute("kind").equals("psdf")){
			importer = new GMLPSDFImporter() ;
		}else{
			importer = new GMLSDFImporter() ;
		}
		importer.classKeySet = this.classKeySet ;
		importer.inputStream = this.inputStream ;
		importer.path = this.path ;
		importer.vertexFromId = this.vertexFromId ;
		return importer.parseGraph(graphElt);
	}

	@Override
	public SDFAbstractVertex parseNode(Element vertexElt) {
		return importer.parseNode(vertexElt);
	}

	@Override
	public SDFAbstractVertex parsePort(Element portElt) {
		// TODO Auto-generated method stub
		return importer.parsePort(portElt);
	}
	
	
}
