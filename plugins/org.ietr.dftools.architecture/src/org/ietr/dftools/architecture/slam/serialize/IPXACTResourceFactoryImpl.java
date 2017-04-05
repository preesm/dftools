/**
 * 
 */
package org.ietr.dftools.architecture.slam.serialize;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * Resource factory used to serialize the System-Level Architecture Model into
 * IP-XACT
 * 
 * @author mpelcat
 */
public class IPXACTResourceFactoryImpl extends ResourceFactoryImpl {

	/**
	 * Constructor for IPXACTResourceFactoryImpl.
	 */
	public IPXACTResourceFactoryImpl() {
		super();
	}

	@Override
	public Resource createResource(URI uri) {
		return new IPXACTResourceImpl(uri);
	}
}
