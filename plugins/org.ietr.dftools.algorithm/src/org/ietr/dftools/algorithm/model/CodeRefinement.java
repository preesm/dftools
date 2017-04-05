package org.ietr.dftools.algorithm.model;

import org.eclipse.core.runtime.IPath;

/**
 * Code to refine a vertex
 * 
 * @author jpiat
 * 
 */
public class CodeRefinement implements IRefinement, CloneableProperty {

	/**
	 * Describe the language that can be used as a code refinement
	 * 
	 * @author jpiat
	 * 
	 */
	public enum Language {
		/**
		 * CAL Actor Language
		 */
		CAL,
		/**
		 * C Language
		 */
		C,
		/**
		 * C++ Language
		 */
		CPP,
		/**
		 * Java Language
		 */
		JAVA,
		/**
		 * Interface Description Language
		 */
		IDL,
		/**
		 * Text refinement, not a path
		 */
		TEXT;

		/**
		 * Gives a Language corresponding to the given extension
		 * 
		 * @param s
		 *            The extension of the code refinement
		 * @return The Language instance
		 */
		public static Language fromExtension(String s) {
			if (s.equals(".c") || s.equals("c")) {
				return C;
			} else if (s.equals(".cal") || s.equals("cal")) {
				return CAL;
			} else if (s.equals(".cpp") || s.equals("cpp")) {
				return CPP;
			} else if (s.equals(".java") || s.equals("java")) {
				return JAVA;
			} else if (s.equals(".idl") || s.equals("idl")) {
				return IDL;
			} else {
				return TEXT;
			}
		}
	}

	private IPath filePath;
	private Language lang;

	/**
	 * Builds a new CodeRefinement instance
	 * 
	 * @param name
	 */
	public CodeRefinement(IPath path) {
		this.filePath = path;
		if (this.filePath != null) {
			String extension = this.filePath.getFileExtension();
			if (extension != null)
				lang = Language.fromExtension(extension);
			else
				lang = Language.TEXT;
		}
	}

	public IPath getPath() {
		return this.filePath;
	}

	/**
	 * Gives the code refinement language
	 * 
	 * @return The code refinement language
	 */
	public Language getLanguage() {
		return lang;
	}

	@Override
	public String toString() {
		if (this.filePath != null) return this.filePath.toString();
		else return "Empty refinement";
	}

	@Override
	public CodeRefinement clone() {
		CodeRefinement clone = new CodeRefinement(this.filePath);
		return clone;
	}

}
