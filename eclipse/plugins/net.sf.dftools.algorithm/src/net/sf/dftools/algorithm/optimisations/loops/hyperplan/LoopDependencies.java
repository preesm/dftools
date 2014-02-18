package net.sf.dftools.algorithm.optimisations.loops.hyperplan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.dftools.algorithm.model.sdf.SDFAbstractVertex;
import net.sf.dftools.algorithm.model.sdf.SDFEdge;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;

public class LoopDependencies {
	private Map<SDFEdge, UUID> edgeToUuidData ;
	private Map<SDFAbstractVertex, List<DataDependency>> opToDataDependency ;
	private Map<SDFAbstractVertex, UUID> graphIndex ;
	
	public LoopDependencies(){
		edgeToUuidData = new HashMap<SDFEdge, UUID>() ;
		opToDataDependency = new HashMap<SDFAbstractVertex, List<DataDependency>>() ;
		graphIndex = new HashMap<SDFAbstractVertex, UUID>() ;
	}
	
	public void addIndex(SDFAbstractVertex v){
		graphIndex.put(v, UUID.randomUUID());
	}
	
	public UUID getIndex(SDFGraph graph){
		return graphIndex.get(graph);
	}
	
	public void addData(SDFEdge edge){
		if(edgeToUuidData.get(edge) == null){
			edgeToUuidData.put(edge, UUID.randomUUID());
		}
	}
	
	public void addData(SDFEdge edge, UUID uuid){
		edgeToUuidData.put(edge, UUID.randomUUID());
	}
	
	public UUID getUUID(SDFEdge edge){
		return edgeToUuidData.get(edge);
	}
	
	public void addReadDataDependency(SDFAbstractVertex vertex, SDFEdge edge, int offset, int size, UUID index){
		if(opToDataDependency.get(vertex) == null){
			opToDataDependency.put(vertex, new ArrayList<DataDependency>());
		}
		opToDataDependency.get(vertex).add(new DataDependency(getUUID(edge),index,  offset, size,false));
	}

	public void addWriteDataDependency(SDFAbstractVertex vertex, SDFEdge edge, int offset, int size,  UUID index){
		if(opToDataDependency.get(vertex) == null){
			opToDataDependency.put(vertex, new ArrayList<DataDependency>());
		}
		opToDataDependency.get(vertex).add(new DataDependency(getUUID(edge),index,  offset, size,true));
	}
	
			
}