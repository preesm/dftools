package org.ietr.dftools.algorithm.generator;

/**
 *
 * @author anmorvan
 *
 */
public class GraphGeneratroException extends RuntimeException {

  private static final long serialVersionUID = -1880352570181672935L;

  public GraphGeneratroException(final String message) {
    super(message);
  }

  public GraphGeneratroException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
