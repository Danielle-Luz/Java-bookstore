package exceptions;

public class UsernameAlreadyUsedException extends Exception {
  public UsernameAlreadyUsedException() {
    super("Another user already has this username");
  }
}
