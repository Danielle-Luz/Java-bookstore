package exceptions;

public class BookAlreadyBorrowedException extends Exception {
  public BookAlreadyBorrowedException() {
    super("This book has already been borrowed");
  }
}
