package org.ietr.dftools.algorithm.factories;

import org.ietr.dftools.algorithm.model.AbstractVertex;
import org.ietr.dftools.algorithm.model.IInterface;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Interface to create vertex in the given model
 * 
 * @author jpiat
 * 
 * @param <V>
 *            The model of vertex to create
 */
@SuppressWarnings("rawtypes")
public abstract class ModelVertexFactory<V extends AbstractVertex> {

	/**
	 * Creates a vertex with the given parameters
	 * 
	 * @param vertexElt The DOM element from which to create the vertex
	 *         
	 * @return The created vertex
	 */
	public abstract V createVertex(Element vertexElt);
	
	public abstract IInterface createInterface(String name, int dir);
	
	
	
	public abstract V  createVertex(String kind) ;
	public String getProperty(Element elt, String propertyName){
		NodeList childList = elt.getChildNodes();
		for (int i = 0; i < childList.getLength(); i++) {
			if (childList.item(i).getNodeName().equals("data")
					&& ((Element) childList.item(i)).getAttribute("key")
							.equals(propertyName)) {
				return childList.item(i).getTextContent();
			}
		}
		return null;
	}

}
