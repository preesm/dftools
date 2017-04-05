/**
 * 
 */
package org.ietr.dftools.workflow.tools;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * The logger is used to display messages in the console. Its behavior is
 * delegated to the workflow ui plugin.
 * 
 * @author mpelcat
 */
public abstract class WorkflowLogger extends Logger {

	private static WorkflowLogger logger = null;

	protected WorkflowLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns this Logger singleton from extension point
	 * 
	 * @return a Logger
	 */
	public static WorkflowLogger getLogger() {

		if (logger == null) {
			try {
				IExtensionRegistry registry = Platform.getExtensionRegistry();

				IConfigurationElement[] elements = registry
						.getConfigurationElementsFor("org.ietr.dftools.workflow.loggers");
				for (int i = 0; i < elements.length; i++) {
					IConfigurationElement element = elements[i];
					if (element.getAttribute("id").equals(
							"net.sf.dftools.ui.workflow.logger")) {
						// Tries to create the transformation
						Object obj = element.createExecutableExtension("type");

						// and checks it actually is an ITransformation.
						if (obj instanceof WorkflowLogger) {
							logger = (WorkflowLogger) obj;

							return logger;
						}
					}
				}

				return null;
			} catch (CoreException e) {
				return null;
			}
		} else {
			return logger;
		}
	}

	/**
	 * adds a log retrieved from a property file {@link WorkflowMessages} and
	 * parameterized with variables Each string "%VAR%" is replaced by a given
	 * variable
	 */
	public abstract void logFromProperty(Level level, String msgKey,
			String... variables);
	
	public static String getFormattedTime() {
		Calendar cal = Calendar.getInstance();

		String time = String.format("%2d:%2d:%2d ",
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND));
		return time;
	}

}
