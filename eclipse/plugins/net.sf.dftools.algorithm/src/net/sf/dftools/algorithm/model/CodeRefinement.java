package net.sf.dftools.algorithm.model;


/**
 * Code to refine a vertex
 * @author jpiat
 *
 */
public class CodeRefinement implements IRefinement, CloneableProperty{
	
	/**
	 * Describe the language that can be used as a code refinement
	 * @author jpiat
	 *
	 */
	public enum Language{
		/**
		 * CAL Actor Language
		 */
		CAL,
		/**
		 * C Language
		 */
		C,
		/**
		 * C++  Language
		 */
		CPP,
		/**
		 * Java  Language
		 */
		JAVA,
		/**
		 * Interface Description Language
		 */
		IDL;
		
		/**
		 * Gives a Language corresponding to the given extension
		 * @param s The extension of the code refinement
		 * @return The Language instance
		 */
		public static Language fromExtension(String s){
			if(s.equals(".c")){
				return C ;
			}else if(s.equals(".cal")){
				return CAL ;
			}else if(s.equals(".cpp")){
				return CPP ;
			}else if(s.equals(".java")){
				return JAVA ;
			}else if(s.equals(".idl")){
				return IDL ;
			}
			return null ;
		}
	}
	
	private String fileName ;
	private Language lang ;
	
	/**
	 * Builds a new CodeRefinement instance
	 * @param name
	 */
	public CodeRefinement(String name){
		fileName = name ;
		lang = Language.fromExtension(name.substring(name.lastIndexOf("."), name.length()));
	}
	
	
	
	/**
	 * Gives this Code refinement name
	 * @return The name of this Code refinement
	 */
	public String getName(){
		return fileName ;
	}
	
	/**
	 * Gives the code refinement language
	 * @return The code refinement language
	 */
	public Language getLanguage(){
		return lang ;
	}
	
	
	public String toString(){
		return fileName;
	}
	
	
	public CodeRefinement clone(){
		CodeRefinement clone = new CodeRefinement(this.getName());
		return clone ;
	}
	
	
}
