/*******************************************************************************
 * Copyright or © or Copr. IETR/INSA - Rennes (2015 - 2017) :
 *
 * Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)
 * Clément Guy <clement.guy@insa-rennes.fr> (2015)
 * Karol Desnos <karol.desnos@insa-rennes.fr> (2015)
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
package org.ietr.dftools.workflow.tools;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * Define the singleton managing PREESM loggers. When using this helper class, your debug, info, warning and errors messages will be displayed in the right
 * eclipse console. If no Eclipse GUI plugin is loaded (i.e. executing a job in command line), all the messages will be sent to the system console.
 *
 * @author cguy
 *
 *         Code adapted from ORCC (net.sf.orcc.core, https://github.com/orcc/orcc)
 * @author Antoine Lorence
 *
 */
public class CLIWorkflowLogger {

  /** The Constant RAW_FLAG. */
  private static final String RAW_FLAG = "raw_record";

  /**
   * Define how text must be printed to logger (Eclipse or System console).
   *
   * @author Antoine Lorence
   */
  private static class DefaultPreesmFormatter extends Formatter {

    /*
     * (non-Javadoc)
     *
     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
     */
    @Override
    public String format(final LogRecord record) {

      final StringBuilder output = new StringBuilder();

      if (!hasRawFlag(record)) {
        final Date date = new Date(record.getMillis());
        final DateFormat df = DateFormat.getTimeInstance();

        output.append(df.format(date));
        // Default printing for warn & severe
        if (record.getLevel().intValue() > CLIWorkflowLogger.NOTICE.intValue()) {
          output.append(" ").append(record.getLevel());
        } else if (record.getLevel().intValue() == CLIWorkflowLogger.NOTICE.intValue()) {
          output.append(" NOTICE");
        } else if (record.getLevel().intValue() == CLIWorkflowLogger.DEBUG.intValue()) {
          output.append(" DEBUG");
        }
        output.append(": ");
      }
      output.append(record.getMessage());
      return output.toString();
    }

    /**
     * Checks for raw flag.
     *
     * @param record
     *          the record
     * @return true, if successful
     */
    private boolean hasRawFlag(final LogRecord record) {
      final Object[] params = record.getParameters();
      if (params == null) {
        return false;
      }

      for (final Object param : params) {
        if (CLIWorkflowLogger.RAW_FLAG.equals(param)) {
          return true;
        }
      }
      return false;
    }
  }

  /** The Constant SEVERE. */
  public static final Level SEVERE = Level.SEVERE;

  /** The Constant WARNING. */
  public static final Level WARNING = Level.WARNING;

  /** The Constant NOTICE. */
  public static final Level NOTICE = Level.INFO;

  /** The Constant TRACE. */
  public static final Level TRACE = Level.FINE;

  /** The Constant DEBUG. */
  public static final Level DEBUG = Level.FINER;

  /** The Constant ALL. */
  public static final Level ALL = Level.ALL;

  /** The logger. */
  private static Logger logger;

  /**
   * Return the current logger, or a newly created one if it doesn't exists. If it is created here, a default ConsoleHandler is used as Logger's Handler.
   *
   * @return the logger
   */
  private static Logger getLogger() {
    if (CLIWorkflowLogger.logger == null) {
      CLIWorkflowLogger.configureLoggerWithHandler(new ConsoleHandler());
    }
    return CLIWorkflowLogger.logger;
  }

  /**
   * This method is the same as {@link #configureLoggerWithHandler(Handler, Formatter)}, but the {@link DefaultPreesmFormatter} is used as default
   * {@link Formatter}.
   *
   * @param handler
   *          the handler
   */
  public static void configureLoggerWithHandler(final Handler handler) {
    CLIWorkflowLogger.configureLoggerWithHandler(handler, new DefaultPreesmFormatter());
  }

  /**
   * Register specific log Handler to display messages sent threw PreesmLogger with a given {@link Formatter}. If this method is never called, the default
   * Handler will be {@link java.util.logging.ConsoleHandler}.
   *
   * @param handler
   *          the handler
   * @param formatter
   *          the formatter
   */
  public static void configureLoggerWithHandler(final Handler handler, final Formatter formatter) {
    CLIWorkflowLogger.logger = null;

    final Logger newLog = Logger.getAnonymousLogger();
    newLog.addHandler(handler);
    newLog.setUseParentHandlers(false);
    handler.setFormatter(formatter);

    CLIWorkflowLogger.logger = newLog;

    CLIWorkflowLogger.setLevel(CLIWorkflowLogger.TRACE);
  }

  /**
   * Set the minimum level displayed. By default, PreesmLogger display messages from INFO level and highest. Call this method with DEBUG or ALL as argument to
   * display debug messages.
   *
   * @param level
   *          the new level
   */
  public static void setLevel(final Level level) {
    CLIWorkflowLogger.getLogger().setLevel(level);
    for (final Handler handler : CLIWorkflowLogger.getLogger().getHandlers()) {
      handler.setLevel(level);
    }
  }

  /**
   * Display a debug message to current console.
   *
   * @param content
   *          the content
   */
  public static void debug(final Object content) {
    CLIWorkflowLogger.getLogger().log(CLIWorkflowLogger.DEBUG, content.toString());
  }

  /**
   * Display a debug message to current console, appended with a line separator character.
   *
   * @param content
   *          the content
   */
  public static void debugln(final Object content) {
    CLIWorkflowLogger.debug(content.toString() + System.getProperty("line.separator"));
  }

  /**
   * Display a debug message on the current console, without any prepended string (time or level info).
   *
   * @param content
   *          the content
   */
  public static void debugRaw(final Object content) {
    final LogRecord record = new LogRecord(CLIWorkflowLogger.DEBUG, content.toString());
    record.setParameters(new Object[] { CLIWorkflowLogger.RAW_FLAG });
    CLIWorkflowLogger.getLogger().log(record);
  }

  /**
   * Display a notice message to current console.
   *
   * @param content
   *          the content
   */
  public static void notice(final Object content) {
    CLIWorkflowLogger.getLogger().log(CLIWorkflowLogger.NOTICE, content.toString());
  }

  /**
   * Display a notice message to current console, appended with a line separator character.
   *
   * @param content
   *          the content
   */
  public static void noticeln(final Object content) {
    CLIWorkflowLogger.notice(content.toString() + System.getProperty("line.separator"));
  }

  /**
   * Display a notice message on the current console, without any prepended string (time or level info).
   *
   * @param content
   *          the content
   */
  public static void noticeRaw(final Object content) {
    final LogRecord record = new LogRecord(CLIWorkflowLogger.NOTICE, content.toString());
    record.setParameters(new Object[] { CLIWorkflowLogger.RAW_FLAG });
    CLIWorkflowLogger.getLogger().log(record);
  }

  /**
   * Display an error line on the current console.
   *
   * @param content
   *          the content
   */
  public static void severe(final Object content) {
    CLIWorkflowLogger.getLogger().log(CLIWorkflowLogger.SEVERE, content.toString());
  }

  /**
   * Display an error line on the current console, appended with a line separator character.
   *
   * @param content
   *          the content
   */
  public static void severeln(final Object content) {
    CLIWorkflowLogger.severe(content.toString() + System.getProperty("line.separator"));
  }

  /**
   * Display an error line on the current console, without any prepended string (time or level info).
   *
   * @param content
   *          the content
   */
  public static void severeRaw(final Object content) {
    final LogRecord record = new LogRecord(CLIWorkflowLogger.SEVERE, content.toString());
    record.setParameters(new Object[] { CLIWorkflowLogger.RAW_FLAG });
    CLIWorkflowLogger.getLogger().log(record);
  }

  /**
   * Display an information message on current console.
   *
   * @param content
   *          the content
   */
  public static void trace(final Object content) {
    CLIWorkflowLogger.getLogger().log(CLIWorkflowLogger.TRACE, content.toString());
  }

  /**
   * Display an information message on current console. The message will be appended with a line separator character.
   *
   * @param content
   *          the content
   */
  public static void traceln(final Object content) {
    CLIWorkflowLogger.trace(content.toString() + System.getProperty("line.separator"));
  }

  /**
   * Display an information message on the current console, without any prepended string (time or level info).
   *
   * @param content
   *          the content
   */
  public static void traceRaw(final Object content) {
    final LogRecord record = new LogRecord(CLIWorkflowLogger.TRACE, content.toString());
    record.setParameters(new Object[] { CLIWorkflowLogger.RAW_FLAG });
    CLIWorkflowLogger.getLogger().log(record);
  }

  /**
   * Display a warning line on the current console.
   *
   * @param content
   *          the content
   */
  public static void warn(final Object content) {
    CLIWorkflowLogger.getLogger().log(CLIWorkflowLogger.WARNING, content.toString());
  }

  /**
   * Display a warning line on the current console, appended with a line separator character.
   *
   * @param content
   *          the content
   */
  public static void warnln(final Object content) {
    CLIWorkflowLogger.warn(content.toString() + System.getProperty("line.separator"));
  }

  /**
   * Display a warning line on the current console, without any prepended string (time or level info).
   *
   * @param content
   *          the content
   */
  public static void warnRaw(final Object content) {
    final LogRecord record = new LogRecord(CLIWorkflowLogger.WARNING, content.toString());
    record.setParameters(new Object[] { CLIWorkflowLogger.RAW_FLAG });
    CLIWorkflowLogger.getLogger().log(record);
  }

  /**
   * Log.
   *
   * @param level
   *          the level
   * @param msg
   *          the msg
   */
  public static void log(final Level level, final String msg) {
    CLIWorkflowLogger.getLogger().log(level, msg + System.getProperty("line.separator"));
  }

  /**
   * Logln.
   *
   * @param level
   *          the level
   * @param msg
   *          the msg
   */
  public static void logln(final Level level, final String msg) {
    CLIWorkflowLogger.getLogger().log(level, msg + System.getProperty("line.separator"));
  }
}
