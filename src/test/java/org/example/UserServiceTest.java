package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
@DisplayName("Testing UserService Class")

public class UserServiceTest {

    @Mock
    private Map<String, User> userDatabase;
    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService();
    }
    @After
    public void tearDown() {
        reset(userDatabase);
    }

    @Test
    public void testRegisterUser_Positive() {
        // Arrange
        User user = new User("johnDoe", "password123", "john@example.com");

        // Act
        boolean result = userService.registerUser(user);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testRegisterUser_Negative_UserAlreadyExists() {
        // Arrange
        User user = new User("johnDoe", "password123", "john@example.com");
        userService.registerUser(user);

        // Act
        boolean result = userService.registerUser(user);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testRegisterUserEdge() throws Exception {
        // Create an instance of UserService
        UserService userService = new UserService();

        // Create a user for the test
        User newUser = new User("", "dobbyhass0ck", "dobby@roomba.com");

// Get the private userDatabase field from the UserService class
        Field userDatabaseField = UserService.class.getDeclaredField("userDatabase");
        userDatabaseField.setAccessible(true);

// Get the value of the userDatabase field in the userService instance
        Map<String, User> userDatabase = (Map<String, User>) userDatabaseField.get(userService);

// Verify that the user does not exist in the database
        assertFalse(userDatabase.containsKey(""));

// Call the registerUser method to register the new user
        boolean result = userService.registerUser(newUser);

// Verify that the result is true, indicating successful registration
        assertTrue(result);

// Verify that the user is correctly added to the database
        assertTrue(userDatabase.containsKey(""));
        assertEquals(newUser, userDatabase.get(""));
    }

    @Test
    public void testLoginUser_Positive() {
        // Arrange
        User user = new User("johnDoe", "password123", "john@example.com");
        userService.registerUser(user);

        // Act
        User loggedInUser = userService.loginUser("johnDoe", "password123");

        // Assert
        assertNotNull(loggedInUser);
        assertEquals(user.getUsername(), loggedInUser.getUsername());
    }

    @Test
    public void testLoginUser_Negative_UserNotFound() {
        // Arrange
        User user = new User("johnDoe", "password123", "john@example.com");
        userService.registerUser(user);

        // Act
        User loggedInUser = userService.loginUser("janeDoe", "password123");

        // Assert
        assertNull(loggedInUser);
    }

    @Test
    public void testLoginUser_EdgeCase_EmptyPassword() {
        // Arrange
        User user = new User("johnDoe", "password123", "john@example.com");
        userService.registerUser(user);

        // Act
        User loggedInUser = userService.loginUser("johnDoe", "");

        // Assert
        assertNull(loggedInUser);
    }

    @Test
    public void testUpdateUserProfile_Positive() {
        // Arrange
        User user = new User("johnDoe", "password123", "john@example.com");
        userService.registerUser(user);
        String newUsername = "jDoe";
        String newPassword = "newPassword";
        String newEmail = "jdoe@example.com";

        // Act
        boolean result = userService.updateUserProfile(user, newUsername, newPassword, newEmail);
        User updatedUser = userService.loginUser(newUsername, newPassword);

        // Assert
        assertTrue(result);
        assertNotNull(updatedUser);
        assertEquals(newUsername, updatedUser.getUsername());
        assertEquals(newPassword, updatedUser.getPassword());
        assertEquals(newEmail, updatedUser.getEmail());
    }

    @Test
    public void testUpdateUserProfile_Negative_NewUsernameAlreadyTaken() {
        // Arrange
        User user1 = new User("johnDoe", "password123", "john@example.com");
        User user2 = new User("jDoe", "newPassword", "jdoe@example.com");
        userService.registerUser(user1);
        userService.registerUser(user2);
        String newUsername = "jDoe";
        String newPassword = "updatedPassword";
        String newEmail = "updated@example.com";

        // Act
        boolean result = userService.updateUserProfile(user1, newUsername, newPassword, newEmail);

        // Assert
        assertFalse(result);
    }

    @Test
    public void updateProfileSetNewInfo() {
        User user = new User("johnDoe", "password123", "john@example.com");
        userService.registerUser(user);

        String newUsername = "Dobby";
        String newPassword = "dobbyhass0ck";
        String newEmail = "dobby@roomba.com";

        boolean result = userService.updateUserProfile(user, newUsername, newPassword, newEmail);
        User updatedUser = userService.loginUser(newUsername, newPassword);

        assertTrue(result);
        assertNotNull(updatedUser);
        assertEquals(newUsername, updatedUser.getUsername());
        assertEquals(newPassword, updatedUser.getPassword());
        assertEquals(newEmail, updatedUser.getEmail());
    }
}
