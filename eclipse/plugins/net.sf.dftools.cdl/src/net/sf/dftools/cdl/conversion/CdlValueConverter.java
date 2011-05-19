
package net.sf.dftools.cdl.conversion;


import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.impl.QualifiedNameValueConverter;

import com.google.inject.Inject;

/**
 * Converts "true" and "false" to booleans, and hexadecimal to integer.
 */
public class CdlValueConverter extends DefaultTerminalConverters {


	@Inject
	private QualifiedNameValueConverter qualifiedNameValueConverter;
	
	@ValueConverter(rule = "QualifiedName")
	public IValueConverter<String> QualifiedName() {
		return qualifiedNameValueConverter;
	}

	@ValueConverter(rule = "QualifiedNameWithWildCard")
	public IValueConverter<String> QualifiedNameWithWildCard() {
		return qualifiedNameValueConverter;
	}
	
}
