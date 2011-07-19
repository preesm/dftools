package net.sf.dftools.cdl.naming;

import net.sf.dftools.cdl.cdl.Component;
import net.sf.dftools.cdl.cdl.Library;
import net.sf.dftools.cdl.cdl.Module;
import net.sf.dftools.cdl.utils.Util;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * This class defines a qualified name provider for CDL.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CdlQualifiedNameProvider extends
		DefaultDeclarativeQualifiedNameProvider {

	public QualifiedName qualifiedName(Module module) {
		return getConverter().toQualifiedName(Util.getQualifiedName(module));
	}

	public QualifiedName qualifiedName(Component component) {
		return getConverter().toQualifiedName(
				Util.getQualifiedName((Module) component.eContainer()));
	}

	public QualifiedName qualifiedName(Library library) {
		return getConverter().toQualifiedName(
				Util.getQualifiedName((Module) library.eContainer()));
	}

}
