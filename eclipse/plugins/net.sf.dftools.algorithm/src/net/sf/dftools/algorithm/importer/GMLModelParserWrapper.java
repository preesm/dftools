package net.sf.dftools.algorithm.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.sf.dftools.algorithm.model.AbstractGraph;
import net.sf.dftools.algorithm.model.sdf.SDFGraph;

public abstract class GMLModelParserWrapper<G extends AbstractGraph<?, ?>> {

	private GMLImporter<G, ?, ?> importer;

	public abstract SDFGraph parse(File f) throws InvalidModelException,
			FileNotFoundException;

	public abstract G parse(InputStream input, String path)
			throws InvalidModelException, FileNotFoundException;

}
