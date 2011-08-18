package net.sf.dftools.cdl.utils;

import net.sf.dftools.cdl.cdl.AstAttribute;
import net.sf.dftools.cdl.cdl.AstBasicType;
import net.sf.dftools.cdl.cdl.AstClass;
import net.sf.dftools.cdl.cdl.AstDecl;
import net.sf.dftools.cdl.cdl.AstDeclType;
import net.sf.dftools.cdl.cdl.AstEnum;
import net.sf.dftools.cdl.cdl.AstType;
import net.sf.dftools.cdl.cdl.CdlFactory;

/**
 * Utility functions for types
 * 
 * 
 * 
 */
public class TypeUtils {

	/**
	 * Sets the type of a typed element as a class reference
	 * 
	 * @param typedElement
	 * @param cl
	 */
	public static void setTypeClassReference(AstAttribute attribute, AstClass cl) {
		AstDeclType type = createClassType(cl);
		attribute.setType(type);
	}

	public static AstDeclType createClassType(AstClass cl) {
		AstDeclType type = CdlFactory.eINSTANCE.createAstDeclType();
		type.setTypeRef(cl);
		return type;
	}

	public static AstDeclType createClassType(AstEnum en) {
		AstDeclType type = CdlFactory.eINSTANCE.createAstDeclType();
		type.setTypeRef(en);
		return type;
	}

	public static AstBasicType createBasicType(String name) {
		AstBasicType type = CdlFactory.eINSTANCE.createAstBasicType();
		type.setName(name);
		return type;
	}

	/**
	 * @param type
	 * @return the string representation of the passed type (both for basic and
	 *         class types)
	 */
	public static String typeToString(AstType type) {
		if (type instanceof AstDeclType) {
			return typeToString((AstDeclType) type);
		}
		if (type instanceof AstBasicType) {
			return typeToString((AstBasicType) type);
		}
		return "Unknown type: " + type;
	}

	public static String typeToString(AstDeclType type) {
		return (type.getTypeRef() != null ? type.getTypeRef().getName() : "");
	}

	public static String typeToString(AstBasicType type) {
		return type.getName();
	}

	public static AstDecl getTypeRef(AstType type) {
		if (type instanceof AstDeclType) {
			return ((AstDeclType) type).getTypeRef();
		}
		return null;
	}

}
