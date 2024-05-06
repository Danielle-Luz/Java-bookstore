package models;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookBorrowing {

  private User borrower;
  private Book borrowedBook;
  private Date startDate;
  private Date devolutionDate;
  private static List<BookBorrowing> allBorrowings = new ArrayList<>();

  public BookBorrowing(
    Book borrowedBook,
    User borrower,
    Date startDate,
    Date devolutionDate
  ) {
    this.borrowedBook = borrowedBook;
    this.borrower = borrower;
    this.startDate = startDate;
    this.devolutionDate = devolutionDate;

    BookBorrowing.allBorrowings.add(this);
  }

  /**
   * This method retrieves a specific book borrowing record based on the borrowed book.
   *
   * @param borrowedBook The book for which the borrowing record is being retrieved.
   * @return The BookBorrowing object associated with the given book.
   *         If no borrowing record is found, null is returned.
   */
  public static BookBorrowing getBookBorrowing(Book borrowedBook) {
    return BookBorrowing.allBorrowings
      .stream()
      .filter(bookBorrowing -> bookBorrowing.borrowedBook.equals(borrowedBook))
      .findFirst()
      .orElse(null);
  }

  /**
   * This method retrieves all borrowings associated with a specific user.
   *
   * @param user The user whose borrowings are being retrieved.
   * @return A list of BookBorrowing objects associated with the given user.
   *         If no borrowings are found, an empty list is returned.
   */
  public static List<BookBorrowing> getUserBorrowings(User user) {
    return BookBorrowing.allBorrowings
      .stream()
      .filter(bookBorrowing -> bookBorrowing.borrower.equals(user))
      .toList();
  }

  /**
   * This method checks if a user has any late borrowings.
   *
   * @param user The user whose borrowings are being checked.
   * @return True if the user has any late borrowings, false otherwise.
   */
  public static boolean hasLateBorrowings(User user) {
    // Use Stream API to filter borrowings of the given user and check if any of them are late
    return BookBorrowing
      .getUserBorrowings(user)
      .stream()
      .anyMatch(bookBorrowing -> bookBorrowing.devolutionDate.before(new Date())
      );
  }

  /**
   * This method is used to return a borrowed book.
   * It iterates through the list of all borrowings and removes the borrowing record
   * associated with the given book.
   *
   * @param borrowedBook The book that is being returned.
   */
  public void returnBook(Book borrowedBook) {
    for (int i = 0; i < BookBorrowing.allBorrowings.size(); i++) {
      boolean isReturnedBook = BookBorrowing.allBorrowings
        .get(i)
        .borrowedBook.equals(borrowedBook);

      if (isReturnedBook) {
        BookBorrowing.allBorrowings.remove(i);
        break;
      }
    }
  }

  /**
   * This method displays information about the book borrowing, including the book title, the borrower, the start date, and the due date.
   */
  public void displayInfo() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    System.out.println("----------------------------------------------------");
    System.out.println(
      MessageFormat.format(
        "Book: {0}\nBorrower: {1}\nStart Date: {2}\nDue Date: {3}\n",
        this.borrowedBook.getTitle(),
        this.borrower.getUsername(),
        dateFormat.format(this.startDate),
        dateFormat.format(this.devolutionDate)
      )
    );
    System.out.println("----------------------------------------------------");
  }
}
