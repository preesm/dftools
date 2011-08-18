package net.sf.dftools.cdl.typing;

import net.sf.dftools.cdl.cdl.AstClass;
import net.sf.dftools.cdl.cdl.AstDecl;
import net.sf.dftools.cdl.cdl.AstType;
import net.sf.dftools.cdl.utils.TypeUtils;

public class TypeResult {
	AstType type;

	String diagnostic;

	public TypeResult(AstType type) {
		super();
		this.type = type;
	}

	public TypeResult(String diagnostic) {
		super();
		this.diagnostic = diagnostic;
	}

	public TypeResult() {
	}

	public TypeResult(AstClass containingClass) {
		if (containingClass == null) {
			this.type = null;
		} else {
			this.type = TypeUtils.createClassType(containingClass);
		}
	}

	public AstType getType() {
		return type;
	}

	public void setType(AstType type) {
		this.type = type;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public void addDiagnostic(String diagnostic) {
		if (this.diagnostic == null) {
			this.diagnostic = diagnostic;
		} else {
			this.diagnostic.concat("\n" + diagnostic);
		}
	}

	@Override
	public String toString() {
		if (type != null) {
			return TypeUtils.typeToString(type);
		} else {
			return diagnostic;
		}
	}

	/**
	 * If the type is not null then returns the class type, otherwise returns
	 * null
	 * 
	 * @return
	 */
	public AstDecl getClassref() {
		return TypeUtils.getTypeRef(type);
	}

	/**
	 * If the type is not null then returns the basic type, otherwise returns
	 * null
	 * 
	 * @return
	 */
	public String getBasicType() {
		return TypeUtils.typeToString(type);
	}
}
