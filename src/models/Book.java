package models;

import enums.Genre;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Book {

  private Genre genre;
  private String isbn;
  private String title;
  private String author;
  private int quantityAvailable;

  private static List<Book> allBooks = new ArrayList<>();

  public Book(
    Genre genre,
    String isbn,
    String title,
    String author,
    int quantityAvailable
  ) {
    this.genre = genre;
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.quantityAvailable = quantityAvailable;

    Book.allBooks.add(this);
  }

  /**
 * This method retrieves a list of books that match the search term.
 * The search term is case-insensitive and matches against the author, title, and genre of the books.
 *
 * @param searchTerm the term to search for in the books.
 * @return a list of books that match the search term.
 */
public static List<Book> getBooks(String searchTerm) {
    String lowerCasedSearchTerm = searchTerm.toLowerCase();

    return Book.allBooks
     .stream()
     .filter(book ->
        book.author.toLowerCase().contains(lowerCasedSearchTerm) ||
        book.title.toLowerCase().contains(lowerCasedSearchTerm) ||
        book.genre.toString().toLowerCase().contains(lowerCasedSearchTerm)
      )
     .toList();
}

  /**
 * This method retrieves a list of all available books.
 * An available book is one that has a quantity greater than 0.
 *
 * @return a list of available books.
 */
public static List<Book> getAvailableBooks() {
    return Book.allBooks
     .stream()
     .filter(book -> book.quantityAvailable > 0)
     .toList();
}

  /**
   * This method displays information about a book in a formatted manner.
   */
  public void displayInfo() {
    System.out.println("----------------------------------------------------");
    System.out.println(
      MessageFormat.format(
        "Título: {0}\nAutor: {1}\nISBN: {2}\nGênero: {3}\nQuantidade disponível: {4}\n",
        this.title,
        this.author,
        this.isbn,
        this.genre.toString(),
        this.quantityAvailable
      )
    );
    System.out.println("----------------------------------------------------");
  }

  public String getTitle() {
    return title;
  }
}
