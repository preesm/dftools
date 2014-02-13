package org.ietr.dftools.algorithm.optimisations.loops.hyperplan;

import java.util.UUID;

public class DataDependency extends LoopDependencies{
	@SuppressWarnings("unused")
	private UUID uuid ;
	@SuppressWarnings("unused")
	private Integer offset ;
	@SuppressWarnings("unused")
	private UUID index ;
	@SuppressWarnings("unused")
	private Integer size ;
	@SuppressWarnings("unused")
	private boolean rw ;
	
	public DataDependency(UUID uuid, UUID index, Integer offset, Integer size, boolean rw){
		super();
		this.uuid = uuid ;
		this.offset = offset ;
		this.rw = rw ;
		this.index = index ;
		this.size = size ;
	}
}
