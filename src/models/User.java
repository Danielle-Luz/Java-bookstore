package models;

import enums.UserType;
import exceptions.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {

  private String username;
  private String password;
  private UserType type;
  private static List<User> allUsers = new ArrayList<User>(
    Arrays.asList(new User("admin", "admin"))
  );

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.type = UserType.EMPLOYEE;
  }

  public User(String username, String password, UserType type) {
    this.username = username;
    this.password = password;
    this.type = type;

    User.allUsers.add(this);
  }

  /**
   * This method is used to authenticate a user based on their username and password.
   *
   * @param username The username of the user trying to log in.
   * @param password The password of the user trying to log in.
   * @return The User object if the username and password match an existing user, otherwise null.
   */
  public static User login(String username, String password) {
    return User.allUsers
      .stream()
      .filter(user ->
        user.username.equals(username) && user.password.equals(password)
      )
      .findFirst()
      .orElse(null);
  }

  /**
   * This method retrieves a user based on their username.
   *
   * @param username The username of the user to be retrieved.
   * @return The User object if a user with the given username is found, otherwise throws an EntityNotFoundException.
   * @throws EntityNotFoundException If no user is found with the given username.
   */
  public static User getUserByUsername(String username)
    throws EntityNotFoundException {
    User foundUser = User.allUsers
      .stream()
      .filter(user -> user.username.equals(username))
      .findFirst()
      .orElse(null);

    if (foundUser == null) {
      throw new EntityNotFoundException(
        "\nNo user found with username: " + username
      );
    }

    return foundUser;
  }

  public String getUsername() {
    return this.username;
  }

  public UserType getType() {
    return this.type;
  }
}
