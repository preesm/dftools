package org.ietr.dftools.architecture.utils;

/**
 *
 * @author anmorvan
 *
 */
public class SlamException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public SlamException(final String message) {
    super(message);
  }

  public SlamException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
