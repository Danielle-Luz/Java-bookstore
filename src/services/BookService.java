package services;

import enums.Genre;
import enums.UserType;
import java.util.List;
import models.Book;
import models.User;

public class BookService {

  /**
   * This method displays a menu for the user to choose an option for managing books.
   * It continuously prompts the user for input until a valid option is selected.
   * The options include creating a book, showing all books, showing only available books, and exiting the program.
   */
  public static void showOptions(User loggedUser) {
    while (true) {
      System.out.println(
        "\n----------------------------------------------------"
      );
      System.out.println("Choose an option:");

      if (loggedUser.getType() == UserType.EMPLOYEE) {
        System.out.println("1 - Create a book");
      }

      System.out.println("2 - Show all books");
      System.out.println("3 - Show only available books");
      System.out.println("4 - Exit");

      try {
        int optionChosen = Integer.parseInt(System.console().readLine());
        System.out.println(
          "----------------------------------------------------"
        );

        switch (optionChosen) {
          case 1 -> {
            if (loggedUser.getType() == UserType.EMPLOYEE) {
              createBook();
            }
          }
          case 2 -> showSearchedBooks();
          case 3 -> showAllAvailableBooks();
        }

        break;
      } catch (NumberFormatException e) {
        System.out.println("\nYou need to type a number value");
        System.out.println(
          "----------------------------------------------------"
        );
      }
    }
  }

  /**
   * This method creates a new book and adds it to the list of books.
   * It continuously prompts the user for input until valid data is entered.
   *
   * @throws Exception If any error occurs during the process of creating a book.
   */
  private static void createBook() {
    while (true) {
      try {
        System.out.print("Type the ISBN of the book: ");
        String isbn = System.console().readLine();

        System.out.print("Type the title of the book: ");
        String title = System.console().readLine();

        Genre bookGenre = chooseBookGenre();

        System.out.print("Type the author of the book: ");
        String author = System.console().readLine();

        System.out.print("Type the book's quantity available: ");
        int quantityAvailable = Integer.parseInt(System.console().readLine());

        // Create a new book with the provided data
        new Book(bookGenre, isbn, title, author, quantityAvailable);

        System.out.println("\nThe book was successfully created!");

        break;
      } catch (Exception e) {
        System.out.println(
          "\nYou've typed an invalid value. Please try again."
        );
      } finally {
        System.out.println(
          "----------------------------------------------------"
        );
      }
    }
  }

  /**
   * This method is used to choose a genre for a book from the available options.
   * It continuously prompts the user to select a genre until a valid option is chosen.
   *
   * @return The chosen genre.
   */
  private static Genre chooseBookGenre() {
    Genre[] bookGenres = Genre.values();
    int genreIndex;

    while (true) {
      System.out.println("Choose the genre of the book:");

      for (int i = 0; i < bookGenres.length; i++) {
        System.out.println(i + " - " + bookGenres[i].toString());
      }

      try {
        genreIndex = Integer.parseInt(System.console().readLine());
      } catch (Exception e) {
        System.out.println("\nYou need to type a number value");
        System.out.println(
          "----------------------------------------------------"
        );
        continue;
      }

      if (genreIndex < 0 && genreIndex >= bookGenres.length) {
        System.out.println(
          "\nChoose a value between 0 and " + bookGenres.length
        );
        System.out.println(
          "----------------------------------------------------"
        );
        continue;
      }

      break;
    }

    return bookGenres[genreIndex];
  }

  /**
   * This method is used to display a list of books that match the search term.
   * The search term can be the title, genre, or author of the book.
   * If no books are found that match the search term, an appropriate message is displayed.
   */
  private static void showSearchedBooks() {
    System.out.print(
      "Type the title, genre or author of the book(s) you are searching: "
    );

    String searchTerm = System.console().readLine();

    try {
      List<Book> foundBooks = Book.getBooks(searchTerm);
      foundBooks.forEach(Book::displayInfo);
    } catch (Exception e) {
      System.out.println("\nNo book compatible with the search term was found");
      System.out.println(
        "----------------------------------------------------"
      );
    }
  }

  /**
   * This method is used to display a list of all available books.
   * It retrieves the list of available books from the Book model and then displays their information.
   * If no available books are found, an appropriate message is displayed.
   */
  private static void showAllAvailableBooks() {
    List<Book> availableBooks = Book.getAvailableBooks();
    availableBooks.forEach(Book::displayInfo);

    if (availableBooks.size() == 0) {
      System.out.println("No available book was found");
    }
  }
}
