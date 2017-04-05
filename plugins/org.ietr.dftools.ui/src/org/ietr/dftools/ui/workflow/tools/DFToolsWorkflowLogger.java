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

package org.ietr.dftools.ui.workflow.tools;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.ietr.dftools.ui.Activator;
import org.ietr.dftools.workflow.messages.WorkflowMessages;
import org.ietr.dftools.workflow.tools.CLIWorkflowLogger;
import org.ietr.dftools.workflow.tools.WorkflowLogger;

/**
 * Displaying information or error messages through a console initialized by the
 * initConsole method.
 * 
 * @author mwipliez
 * @author mpelcat
 */
public class DFToolsWorkflowLogger extends WorkflowLogger {

	private static final String LOGGER_NAME = "net.sf.dftools.log.WorkflowLogger";

	// Boolean used to know whether Preesm is running through command line
	// interface, in which case we should not use the logGUI method (it calls
	// getWorkbench, provoking an IllegalStateException, because Workbench is an
	// UI class), but logCLI which use the cli-friendly logger PreesmLogger
	private static boolean isRunningFromCLI = false;

	MessageConsole console = null;

	@Override
	public void setLevel(Level newLevel) throws SecurityException {
		// Enabling only info level
		super.setLevel(Level.INFO);
	}

	/**
	 * 
	 */
	public DFToolsWorkflowLogger() {
		super(LOGGER_NAME, null);
		LogManager.getLogManager().addLogger(this);

		if (!isRunningFromCLI) initConsole();
	}

	/**
	 * adds a log retrieved from a property file {@link WorkflowMessages} and
	 * parameterized with variables Each string "%VAR%" is replaced by a given
	 * variable
	 */
	@Override
	public void logFromProperty(Level level, String msgKey, String... variables) {
		log(level, WorkflowMessages.getString(msgKey, variables));
	}

	@Override
	public void log(LogRecord record) {
		if (isRunningFromCLI)
			logCLI(record);
		else
			logGUI(record);

	}

	private void logGUI(LogRecord record) {
		Level level = record.getLevel();
		final int levelVal = level.intValue();
		if (getLevel() == null || levelVal >= getLevel().intValue()) {

			// Writes a log in standard output
			if (console == null) {
				if (levelVal < Level.INFO.intValue()) {
					String msg = record.getMillis() + " " + level.toString()
							+ ": " + record.getMessage() + " (in "
							+ record.getSourceClassName() + "#"
							+ record.getSourceMethodName() + ")";
					System.out.println(msg);
				} else {
					Date date = new Date(record.getMillis());
					DateFormat df = DateFormat.getTimeInstance();
					String msg = df.format(date) + " " + level.toString()
							+ ": " + record.getMessage();

					if (levelVal < Level.WARNING.intValue())
						System.out.println(msg);
					else
						System.err.println(msg);
				}
			} else {
				// Writes a log in console
				final MessageConsoleStream stream = console.newMessageStream();

				Activator.getDefault().getWorkbench().getDisplay()
						.asyncExec(new Runnable() {
							@Override
							public void run() {
								if (levelVal < Level.WARNING.intValue())
									stream.setColor(new Color(null, 0, 0, 0));
								else if (levelVal == Level.WARNING.intValue())
									stream.setColor(new Color(null, 255, 150, 0));
								else if (levelVal > Level.WARNING.intValue())
									stream.setColor(new Color(null, 255, 0, 0));
							}
						});

				stream.println(getFormattedTime() + record.getMessage());

				if (getLevel().intValue() >= Level.SEVERE.intValue()) {
					// throw (new PreesmException(record.getMessage()));
				}
			}
		}
	}

	private void logCLI(LogRecord record) {
		CLIWorkflowLogger.log(record.getLevel(), record.getMessage());
	}

	public void initConsole() {
		setLevel(Level.INFO);
		IConsoleManager mgr = ConsolePlugin.getDefault().getConsoleManager();

		if (console == null) {
			console = new MessageConsole("DFTools Workflow console", null);
			mgr.addConsoles(new IConsole[] { console });
		}

		console.activate();
		console.setBackground(new Color(null, 230, 228, 252));

		mgr.refresh(console);
	}

	/**
	 * Method to call before the first log when running preesm through command
	 * line interface
	 * Basically called by CLIWorkflowExecutor
	 */
	public static void runFromCLI() {
		isRunningFromCLI = true;
	}

}
