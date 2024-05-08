package services;

import enums.UserType;
import models.User;

public class UserService {

  /**
   * This method is responsible for handling the login process in the system.
   * It prompts the user to enter a username and password, and checks if the provided credentials match an existing user.
   * If the credentials are valid, it returns the corresponding User object.
   * If the credentials are invalid, it displays an error message and prompts the user to enter new credentials.
   *
   * @return The User object corresponding to the logged-in user.
   */
  public static User login() {
    while (true) {
      System.out.print("Type your username: ");
      String username = System.console().readLine();

      System.out.print("Type your password: ");
      String password = System.console().readLine();

      User loggedUser = User.login(username, password);

      if (loggedUser == null) {
        System.out.println(
          "\nNo existent user with the provided username and password was found"
        );
        System.out.println(
          "----------------------------------------------------"
        );
        continue;
      }

      System.out.println("\nLogin successful!");
      System.out.println(
        "----------------------------------------------------"
      );

      return loggedUser;
    }
  }

  /**
   * This method is responsible for creating a new user in the system.
   * It prompts the user to enter a username and password, and checks if the username already exists.
   * If the username already exists, it displays an error message and prompts the user to enter a new username.
   * If the username is unique, it creates a new User object with the provided username, password, and UserType.CUSTOMER.
   */
  public static void createUser() {
    String username = "";

    System.out.println(
      "\n----------------------------------------------------"
    );

    // Loop until a unique username is entered
    while (true) {
      try {
        System.out.print("Type the username: ");
        username = System.console().readLine();

        // Check if the username already exists
        User.getUserByUsername(username);
        // If no exception was thrown, the username already exists
        System.out.println("\nA user with this username already exists.");
        System.out.println(
          "----------------------------------------------------"
        );
      } catch (Exception e) {
        // If an exception was thrown, the username does not exist
        // The user can continue with the registration
        break;
      }
    }

    System.out.print("Type the password: ");
    String password = System.console().readLine();

    UserType userType = chooseUserType();

    new User(username, password, userType);
  }

  /**
   * This method is responsible for allowing the user to choose a user type.
   * It displays a list of available user types and prompts the user to select one.
   * The user's selection is validated to ensure it is a valid index within the user types array.
   *
   * @return The selected UserType.
   */
  private static UserType chooseUserType() {
    UserType[] userTypes = UserType.values();

    // Loop until a valid user type is selected
    while (true) {
      int userTypeIndex;

      // Display the list of available user types
      System.out.println("Select the user's type:");
      for (int i = 0; i < userTypes.length; i++) {
        System.out.println(i + " - " + userTypes[i].toString());
      }

      try {
        userTypeIndex = Integer.parseInt(System.console().readLine());
      } catch (Exception e) {
        // If the input is not a valid integer, display an error message and continue the loop
        System.out.println("\nYou need to type a number value");
        System.out.println(
          "----------------------------------------------------"
        );
        continue;
      }

      if (userTypeIndex < 0 || userTypeIndex >= userTypes.length) {
        // If the selection is not a valid index, display an error message and continue the loop
        System.out.println("\nYou need to select a valid index");
        System.out.println(
          "----------------------------------------------------"
        );
        continue;
      }

      // If the selection is valid, return the corresponding UserType
      return userTypes[userTypeIndex];
    }
  }
}
