package services;

import enums.Genre;
import java.util.List;
import models.Book;

public class BookService {

  public static void showBookOptions() {
    while (true) {
      System.out.println("Choose an option:");
      System.out.println("1 - Create a book");
      System.out.println("2 - Show all books");
      System.out.println("3 - Show only available books");
      System.out.println("4 - Exit");

      try {
        switch (Integer.parseInt(System.console().readLine())) {
          case 1:
            createBook();
            break;
          case 2:
            showSearchedBooks();
            break;
          case 3:
            showAllAvailableBooks();
            break;
          default:
            break;
        }

        break;
      } catch (Exception e) {
        System.out.println("You need to type a number value");
      }
    }
  }

  public static void createBook() {
    while (true) {
      try {
        System.out.print("Type the ISBN of the book: ");
        String isbn = System.console().readLine();

        System.out.print("Type the title of the book:");
        String title = System.console().readLine();

        Genre bookGenre = chooseBookGenre();

        System.out.print("Type the author of the book: ");
        String author = System.console().readLine();

        System.out.print("Type the book's quantity available: ");
        int quantityAvailable = Integer.parseInt(System.console().readLine());

        new Book(bookGenre, isbn, title, author, quantityAvailable);

        System.out.println("The book was successfully created!");
        break;
      } catch (Exception e) {
        System.out.println("You've typed a invalid value");
      }
    }
  }

  public static Genre chooseBookGenre() {
    Genre[] bookGenres = Genre.values();
    int genreIndex;

    while (true) {
      System.out.println("Choose the genre of the book:");

      for (int i = 0; i < bookGenres.length; i++) {
        System.out.println(i + " - " + bookGenres[i].toString());
      }

      genreIndex = Integer.parseInt(System.console().readLine());

      if (genreIndex < 0 && genreIndex >= bookGenres.length) {
        System.out.println("Choose a value between 0 and " + bookGenres.length);
        continue;
      }

      break;
    }

    return bookGenres[genreIndex];
  }

  public static void showSearchedBooks() {
    System.out.print(
      "Type the title, genre or author of the book(s) you are searching: "
    );
    String searchTerm = System.console().readLine();

    try {
      List<Book> foundBooks = Book.getBooks(searchTerm);
      foundBooks.forEach(Book::displayInfo);
    } catch (Exception e) {
      System.out.println("No book compatible with the search term was found");
    }
  }

  public static void showAllAvailableBooks() {
    List<Book> availableBooks = Book.getAvailableBooks();
    availableBooks.forEach(Book::displayInfo);
  }
}
