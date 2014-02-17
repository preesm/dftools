package org.ietr.dftools.algorithm.optimisations.clustering.mfa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;

/**
 * A node in the MFA algorithm
 * @author jpiat
 *
 */
public class Node {
	
	protected SDFAbstractVertex vertex ;
	protected int i ;
	protected List<Spin> spins ;
	
	private static Logger log = Logger.getLogger(Node.class.toString()) ;
	
	/**
	 * COnstructs a new node given the corresponding vertex
	 * @param vertex The ertex this node is based on
	 */
	public Node(SDFAbstractVertex vertex, int id){
		this.vertex = vertex ;
		spins = new ArrayList<Spin>() ;
	}
	
	
	/**
	 * Gives the partition this node belongs to
	 * @return The index of the partition this node belongs to
	 * @throws NoMigrationException
	 */
	public int getBelongsToPartition(){
		double max = 0 ;
		int result = 0 ;
		for(int i = 0 ; i < spins.size() ; i ++){
			if(spins.get(i).getValue() > max){
				max = spins.get(i).getValue() ;
				result = i ;
			}
		}
		if(max-(1/(new Double(spins.size()))) < 1/(2*new Double(spins.size()))){
			log.warning("Node :"+this.vertex.getName()+" has not explicitaly migrate ...");
		}
		return result ;
	}
	
	/**
	 * Gives the spin for the partition i
	 * @param i The index of the spin partition
	 * @return The spin of the given partition
	 */
	public Spin getSpin(int i){
		return spins.get(i) ;
	}
	
	/**
	 * Gives this node spins
	 * @return The spins of this node
	 */
	public List<Spin> getSpins(){
		return spins ;
	}
	
	/**
	 * Gives this node vertex
	 * @return The vertex this node is based on
	 */
	public SDFAbstractVertex getVertex(){
		return vertex ;
	}
	
	/**
	 * Gives whether the node hold in a state or not
	 * @return True if the node state hasn't changed since the last update 
	 */
	public boolean hold(){
		boolean hold = true ;
		for(Spin spin : spins){
			hold &= spin.noChange;
		}
		if(!hold){
			System.out.println(this+" has changed");
		}
		return hold ;
	}
	
	/**
	 * Sets the number of partition the system needs to be partition in
	 * @param nb
	 */
	public void setNbPartitions(int nb){
		Random r = new Random(System.nanoTime());
		double dNb = new Double(nb);
		for(int i = 0 ; i < nb ; i ++ ){
			if(r.nextBoolean()){
				spins.add(new Spin((1/dNb)*(1-(r.nextDouble()*1/nb)), this));
			}else{
				spins.add(new Spin((1/dNb)*(1+(r.nextDouble()*1/nb)), this));
			}
			
		}
	}
	
	/**
	 * Set this node spin to one for the partition i
	 * @param i The partition index
	 */
	public void setOne(int i){
		for(int j = 0; j < spins.size() ; j ++){	
			if(j == i){
				spins.get(j).setValue(1);
			}else{
				spins.get(j).setValue(0);
			}
		}
	}
	
	public String toString(){
		String res = vertex.getName();
		res += " : [";
		for(Spin n : spins){
			res += " "+n.toString();
		}
		res += "]";
		return res ;
	}
	

}
