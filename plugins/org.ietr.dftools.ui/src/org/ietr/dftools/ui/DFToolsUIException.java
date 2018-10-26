package org.ietr.dftools.ui;

/**
 *
 * @author anmorvan
 *
 */
public class DFToolsUIException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DFToolsUIException(final String message) {
    super(message);
  }

  public DFToolsUIException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
