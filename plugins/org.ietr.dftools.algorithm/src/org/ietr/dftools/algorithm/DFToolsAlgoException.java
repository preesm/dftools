package org.ietr.dftools.algorithm;

/**
 *
 * @author anmorvan
 *
 */
public class DFToolsAlgoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DFToolsAlgoException(final String message) {
    super(message);
  }

  public DFToolsAlgoException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
