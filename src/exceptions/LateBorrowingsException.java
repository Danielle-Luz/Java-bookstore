package exceptions;

public class LateBorrowingsException extends Exception {

  public LateBorrowingsException() {
    super(
      "The user can't borrow another book because he has borrowed books before and still didn't return them"
    );
  }
}
