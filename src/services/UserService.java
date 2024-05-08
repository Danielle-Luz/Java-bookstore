package services;

import enums.UserType;
import models.User;

public class UserService {

  /**
   * This method is responsible for creating a new user in the system.
   * It prompts the user to enter a username and password, and checks if the username already exists.
   * If the username already exists, it displays an error message and prompts the user to enter a new username.
   * If the username is unique, it creates a new User object with the provided username, password, and UserType.CUSTOMER.
   */
  public static void createUser() {
    String username = "";

    // Loop until a unique username is entered
    while (true) {
      try {
        System.out.print("Type the username: ");
        username = System.console().readLine();

        // Check if the username already exists
        User.getUserByUsername(username);
        // If no exception was thrown, the username already exists
        System.out.println("A user with this username already exists.");
      } catch (Exception e) {
        // If an exception was thrown, the username does not exist
        // The user can continue with the registration
        break;
      }
    }

    System.out.print("Type the password: ");
    String password = System.console().readLine();

    // Create a new User object with the provided username, password, and UserType.CUSTOMER
    new User(username, password, UserType.CUSTOMER);
  }
}
