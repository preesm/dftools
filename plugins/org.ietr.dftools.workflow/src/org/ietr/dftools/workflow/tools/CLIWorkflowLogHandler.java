package org.ietr.dftools.workflow.tools;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 *
 * @author anmorvan
 *
 */
public class CLIWorkflowLogHandler extends StreamHandler {

  private final boolean debugMode;

  public CLIWorkflowLogHandler(final boolean debugMode) {
    super(System.out, new DefaultPreesmFormatter());
    this.debugMode = debugMode;
  }

  @Override
  public synchronized void publish(LogRecord record) {
    if (!debugMode && record.getThrown() != null) {
      record.setThrown(null);
    }
    super.publish(record);
    flush();
  }

  @Override
  public void close() {
    flush();
  }
}
