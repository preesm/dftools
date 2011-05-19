package net.sf.dftools.cdl.naming;

import net.sf.dftools.cdl.cdl.Component;
import net.sf.dftools.cdl.cdl.Library;
import net.sf.dftools.cdl.cdl.Module;
import net.sf.dftools.cdl.utils.Util;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;

/**
 * This class defines a qualified name provider for RVC-CAL.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CdlQualifiedNameProvider extends
		DefaultDeclarativeQualifiedNameProvider {

	public String qualifiedName(Module module) {
		return Util.getQualifiedName(module);
	}

	public String qualifiedName(Component component) {
		return Util.getQualifiedName((Module) component.eContainer());
	}

	public String qualifiedName(Library library) {
		return Util.getQualifiedName((Module) library.eContainer());
	}

}
