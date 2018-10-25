package org.ietr.dftools.algorithm.model.sdf.esdf;

import org.ietr.dftools.algorithm.model.sdf.SDFAbstractVertex;
import org.ietr.dftools.algorithm.model.sdf.SDFEdge;

/**
 *
 * @author anmorvan
 *
 */
public abstract class SDFAbstractSpecialVertex extends SDFAbstractVertex {

  public abstract boolean setEdgeIndex(final SDFEdge edge, long index);
}
