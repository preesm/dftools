/**
 * 
 */
package org.ietr.dftools.architecture.slam.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.ietr.dftools.architecture.slam.Design;

/**
 * Resource implementation used to (de)serialize the System-Level Architecture
 * Model into IP-XACT.
 * 
 * @author mpelcat
 */
public class IPXACTResourceImpl extends ResourceImpl {

	/**
	 * Constructor for XMIResourceImpl.
	 * 
	 * @param uri
	 */
	public IPXACTResourceImpl(URI uri) {
		super(uri);
	}

	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {

		IPXACTDesignWriter designWriter = new IPXACTDesignWriter(uri);

		Design design = (Design) this.getContents().get(0);

		designWriter.write(design, outputStream);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {

		IPXACTDesignParser designParser = new IPXACTDesignParser(uri);

		Design design = designParser.parse(inputStream, null, null);
		if (design != null && !getContents().contains(design)) {
			this.getContents().add(design);
		}
	}
}
