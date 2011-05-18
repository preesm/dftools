
package net.sf.dftools.cdl;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class CdlStandaloneSetup extends CdlStandaloneSetupGenerated{

	public static void doSetup() {
		new CdlStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

