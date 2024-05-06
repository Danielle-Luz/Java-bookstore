package exceptions;

public class UsernameAlreadyUsedException extends Exception {
  public UsernameAlreadyUsedException(String message) {
    super(message);
  }
}
