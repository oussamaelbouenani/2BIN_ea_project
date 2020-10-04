package be.ipl.pae.exception;

public class FatalException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public FatalException() {
    super();
  }

  public FatalException(String message) {
    super(message);
  }

}
