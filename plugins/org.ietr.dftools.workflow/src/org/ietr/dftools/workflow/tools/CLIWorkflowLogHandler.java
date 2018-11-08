package org.ietr.dftools.workflow.tools;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 *
 * @author anmorvan
 *
 */
public class CLIWorkflowLogHandler extends Handler {

  private final boolean debugMode;

  private final Handler stderrStreamHandler = new StreamHandler(System.err, new DefaultPreesmFormatter());
  private final Handler stdoutStreamHandler = new StreamHandler(System.out, new DefaultPreesmFormatter());

  public CLIWorkflowLogHandler(final boolean debugMode) {
    super();
    this.debugMode = debugMode;
  }

  @Override
  public synchronized void publish(LogRecord record) {
    if (!debugMode && record.getThrown() != null) {
      record.setThrown(null);
    }
    if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
      stderrStreamHandler.publish(record);
    } else {
      stdoutStreamHandler.publish(record);
    }
    flush();
  }

  @Override
  public void close() {
    flush();
  }

  @Override
  public void flush() {
    stderrStreamHandler.flush();
    stdoutStreamHandler.flush();
  }
}
