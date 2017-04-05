/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (%%DATE%%) :
 *
 * %%AUTHORS%%
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *******************************************************************************/
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
