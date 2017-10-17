/**
 * Copyright or © or Copr. IETR/INSA - Rennes (2011 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2014)
 * Maxime Pelcat <maxime.pelcat@insa-rennes.fr> (2011)
 *
 * This software is a computer program whose purpose is to help prototyping
 * parallel applications using dataflow formalism.
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
 */
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
import org.ietr.dftools.workflow.messages.WorkflowMessages;

// TODO: Auto-generated Javadoc
/**
 * The logger is used to display messages in the console. Its behavior is delegated to the workflow ui plugin.
 *
 * @author mpelcat
 */
public abstract class WorkflowLogger extends Logger {

  /** The logger. */
  private static WorkflowLogger logger = null;

  /**
   * Instantiates a new workflow logger.
   *
   * @param name
   *          the name
   * @param resourceBundleName
   *          the resource bundle name
   */
  protected WorkflowLogger(final String name, final String resourceBundleName) {
    super(name, resourceBundleName);
    // TODO Auto-generated constructor stub
  }

  /**
   * Returns this Logger singleton from extension point.
   *
   * @return a Logger
   */
  public static WorkflowLogger getLogger() {

    if (WorkflowLogger.logger == null) {
      try {
        final IExtensionRegistry registry = Platform.getExtensionRegistry();

        final IConfigurationElement[] elements = registry.getConfigurationElementsFor("org.ietr.dftools.workflow.loggers");
        for (final IConfigurationElement element : elements) {
          if (element.getAttribute("id").equals("net.sf.dftools.ui.workflow.logger")) {
            // Tries to create the transformation
            final Object obj = element.createExecutableExtension("type");

            // and checks it actually is an ITransformation.
            if (obj instanceof WorkflowLogger) {
              WorkflowLogger.logger = (WorkflowLogger) obj;

              return WorkflowLogger.logger;
            }
          }
        }

        return null;
      } catch (final CoreException e) {
        return null;
      }
    } else {
      return WorkflowLogger.logger;
    }
  }

  /**
   * adds a log retrieved from a property file {@link WorkflowMessages} and parameterized with variables Each string "%VAR%" is replaced by a given variable.
   *
   * @param level
   *          the level
   * @param msgKey
   *          the msg key
   * @param variables
   *          the variables
   */
  public abstract void logFromProperty(Level level, String msgKey, String... variables);

  /**
   * Gets the formatted time.
   *
   * @return the formatted time
   */
  public static String getFormattedTime() {
    final Calendar cal = Calendar.getInstance();

    final String time = String.format("%02d:%02d:%02d ", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    return time;
  }

}
