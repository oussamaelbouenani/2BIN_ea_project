package be.ipl.pae.exception;

public class BizzException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BizzException() {
    super();
  }

  public BizzException(String message) {
    super(message);
  }

}
