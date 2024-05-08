package models;

import exceptions.EntityNotFoundException;
import exceptions.LateBorrowingsException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookBorrowing {

  private User borrower;
  private Book borrowedBook;
  private Date startDate;
  private Date devolutionDate;
  private static List<BookBorrowing> allBorrowings = new ArrayList<>();

  public BookBorrowing(Book borrowedBook, User borrower, Date startDate)
    throws LateBorrowingsException {
    BookBorrowing.hasLateBorrowings(borrower);

    this.borrowedBook = borrowedBook;
    this.borrower = borrower;
    this.startDate = startDate;
    this.borrowedBook.setQuantityAvailable(
        this.borrowedBook.getQuantityAvailable() - 1
      );

    Calendar calendar = Calendar.getInstance();
    int daysUntilDevolution = 14;
    calendar.setTime(startDate);
    calendar.add(Calendar.DAY_OF_MONTH, daysUntilDevolution);
    this.devolutionDate = calendar.getTime();

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
   * This method retrieves a specific book borrowing record based on the borrowed book's ISBN.
   *
   * @param user The user whose borrowings are being searched.
   * @param isbn The ISBN of the book for which the borrowing record is being retrieved.
   * @return The BookBorrowing object associated with the given user and book ISBN.
   *         If no borrowing record is found, null is returned.
   */
  public static BookBorrowing getUserBorrowingByBookIsbn(
    User user,
    String isbn
  ) throws EntityNotFoundException {
    // Use Stream API to filter the list of borrowings associated with the given user
    // and find the first borrowing record where the book's ISBN matches the given ISBN (case-insensitive)
    BookBorrowing borrowing = BookBorrowing
      .getUserBorrowings(user)
      .stream()
      .filter(bookBorrowing ->
        bookBorrowing.borrowedBook.getIsbn().equalsIgnoreCase(isbn)
      )
      .findFirst()
      .orElse(null);

    if (borrowing == null) {
      throw new EntityNotFoundException(
        MessageFormat.format(
          "No borrowing record found for user {0} and ISBN {1}.",
          user.getUsername(),
          isbn
        )
      );
    }

    return borrowing;
  }

  /**
   * This method is used to check if a user has any late book borrowings.
   * It iterates through the list of all borrowings of the given user and checks if the due date of any borrowing is before the current date.
   * If a late borrowing is found, a LateBorrowingsException is thrown.
   *
   * @param user The user whose borrowings are being checked.
   * @return True if the user has any late borrowings, false otherwise.
   * @throws LateBorrowingsException If a late borrowing is found.
   */
  public static boolean hasLateBorrowings(User user)
    throws LateBorrowingsException {
    boolean hasLateBorrowings = BookBorrowing
      .getUserBorrowings(user)
      .stream()
      .anyMatch(bookBorrowing -> bookBorrowing.devolutionDate.before(new Date())
      );

    if (hasLateBorrowings) {
      throw new LateBorrowingsException();
    }

    return hasLateBorrowings;
  }

  /**
   * This method is used to return a borrowed book.
   * It iterates through the list of all borrowings and removes the borrowing record
   * associated with the given book.
   *
   * @param borrowedBook The book that is being returned.
   */
  public void returnBook() throws EntityNotFoundException {
    for (int i = 0; i < BookBorrowing.allBorrowings.size(); i++) {
      boolean isReturnedBook = BookBorrowing.allBorrowings
        .get(i)
        .borrowedBook.equals(this.borrowedBook);

      if (isReturnedBook) {
        int incrementedBookQuantity =
          this.borrowedBook.getQuantityAvailable() + 1;
        this.borrowedBook.setQuantityAvailable(incrementedBookQuantity);
        BookBorrowing.allBorrowings.remove(i);
        return;
      }
    }

    throw new EntityNotFoundException(
      "The returned book was not found as one of the books the user borrowed"
    );
  }

  /**
   * This method displays information about the book borrowing, including the book title, the borrower, the start date, and the due date.
   */
  public void displayInfo() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    System.out.println("----------------------------------------------------");
    System.out.println(
      MessageFormat.format(
        "Book ISBN: {0}\nBook title: {1}\nBorrower: {2}\nStart Date: {3}\nDevolution Date: {4}\n",
        this.borrowedBook.getTitle(),
        this.borrowedBook.getIsbn(),
        this.borrower.getUsername(),
        dateFormat.format(this.startDate),
        dateFormat.format(this.devolutionDate)
      )
    );
    System.out.println("----------------------------------------------------");
  }
}
