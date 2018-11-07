package org.ietr.dftools.workflow.tools;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Define how text must be printed to logger (Eclipse or System console).
 *
 * @author Antoine Lorence
 */
class DefaultPreesmFormatter extends Formatter {

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
      if (record.getLevel().intValue() > Level.INFO.intValue()) {
        output.append(" ").append(record.getLevel());
      } else if (record.getLevel().intValue() == Level.INFO.intValue()) {
        output.append(" NOTICE");
      } else if (record.getLevel().intValue() == Level.FINER.intValue()) {
        output.append(" DEBUG");
      }
      output.append(": ");
    }

    output.append(record.getMessage());

    final Throwable thrown = record.getThrown();
    if (thrown != null) {
      output.append("\n" + ExceptionUtils.getStackTrace(thrown));
    }

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