import enums.UserType;
import models.User;
import services.BookBorrowingService;
import services.BookService;
import services.UserService;

public class App {

  public static void main(String[] args) throws Exception {
    System.out.println(
      "Welcome to the bookstore!\nYou need to login to continue:"
    );

    User loggedUser = UserService.login();

    showMenu(loggedUser);
  }

  /**
   * This method shows the menu to the user and allows them to perform different actions.
   *
   * @param loggedUser the user who is currently logged in
   */
  public static void showMenu(User loggedUser) {
    int optionChosen;

    System.out.println("Welcome " + loggedUser.getUsername() + "!");

    while (true) {
      System.out.println("\nSelect an option:");
      System.out.println("1 - See books");

      if (loggedUser.getType() == UserType.EMPLOYEE) {
        System.out.println("2 - Create user");
      }

      System.out.println("3 - Borrow or return books");
      System.out.println("4 - Exit");

      try {
        optionChosen = Integer.parseInt(System.console().readLine());
      } catch (NumberFormatException e) {
        System.out.println("\nYou need to type a number value");
        System.out.println(
          "----------------------------------------------------"
        );
        continue;
      }

      switch (optionChosen) {
        case 1 -> BookService.showOptions(loggedUser);
        case 2 -> {
          if (loggedUser.getType() == UserType.EMPLOYEE) {
            UserService.createUser();
          }
        }
        case 3 -> BookBorrowingService.showOptions();
        case 4 -> System.exit(0);
        default -> {
          System.out.println("\nSelect an option between 1 and 4");
          System.out.println(
            "----------------------------------------------------"
          );
        }
      }
    }
  }
}
