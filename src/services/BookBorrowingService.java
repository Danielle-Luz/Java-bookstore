package services;

import exceptions.EntityNotFoundException;
import exceptions.LateBorrowingsException;
import java.util.Date;
import java.util.List;
import models.Book;
import models.BookBorrowing;
import models.User;

public class BookBorrowingService {

  /**
   * This method handles the process of borrowing a book.
   * It displays all available books to the user, prompts for the ISBN and username of the borrower,
   * creates a new BookBorrowing object, and updates the book's status to borrowed.
   * If any exceptions occur during the process, it catches them and prompts the user to try again.
   * The method also provides an option for the user to go back to the previous screen.
   */
  public static void borrowBook() {
    System.out.println("All the books available to borrow:");
    Book.getAvailableBooks().forEach(Book::displayInfo);

    while (true) {
      try {
        System.out.print(
          "Type the ISBN of the book that is going to be borrowed: "
        );
        String isbn = System.console().readLine();
        Book borrowedBook = Book.getBooks(isbn).get(0);

        System.out.print(
          "Type the username of the user that is going to borrow the book: "
        );
        String username = System.console().readLine();
        User borrower = User.getUserByUsername(username);

        new BookBorrowing(borrowedBook, borrower, new Date());
        System.out.println("The book was successfully borrowed!");
        break;
      } catch (EntityNotFoundException | LateBorrowingsException e) {
        System.out.println(e.getMessage());

        System.out.println(
          "You are going to type the ISBN of the book and the name of the user again, do you want to go back to the previous screen?"
        );

        String typedChoice = System.console().readLine();
        if (typedChoice.equalsIgnoreCase("yes")) break;
      }
    }
  }

  /**
   * This method handles the process of returning a borrowed book.
   * It prompts the user to enter the username of the borrower and the ISBN of the book to be returned.
   * It then retrieves the corresponding BookBorrowing object and calls the returnBook method on it.
   * If any exceptions occur during the process, it catches them and prompts the user to try again.
   * The method also provides an option for the user to go back to the previous screen.
   */
  public static void returnBook() {
    while (true) {
      try {
        System.out.print(
          "Type the username of the user that is going to return a book: "
        );
        String username = System.console().readLine();
        User borrower = User.getUserByUsername(username);

        List<BookBorrowing> userBorrowings = BookBorrowing.getUserBorrowings(
          borrower
        );
        System.out.println("Books that the user can return:");
        userBorrowings.forEach(BookBorrowing::displayInfo);

        System.out.print(
          "Type the ISBN of the book that is going to be returned: "
        );
        String isbn = System.console().readLine();

        BookBorrowing returnedBorrowing = BookBorrowing.getUserBorrowingByBookIsbn(
          borrower,
          isbn
        );

        returnedBorrowing.returnBook();

        System.out.println("The book was successfully returned!");

        return;
      } catch (EntityNotFoundException e) {
        System.out.println(e.getMessage());

        System.out.println(
          "You are going to type the ISBN of the book and the name of the user again, do you want to go back to the previous screen?"
        );

        String typedChoice = System.console().readLine();

        if (typedChoice.equalsIgnoreCase("yes")) return;
      }
    }
  }
}
