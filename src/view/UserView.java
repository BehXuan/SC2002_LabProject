package src.view;
import java.util.Scanner;

import src.interfaces.AuthController;


/**
 * Abstract base view class for all user types.
 * <p>
 * Provides common functionality for user authentication and interaction including password
 * management and logout operations. This abstract class enforces all concrete user views
 * to implement the {@link #start()} method for their specific menu workflows.
 * </p>
 */
public abstract class UserView {

    protected AuthController controller;
    protected Scanner sc = new Scanner(System.in);

    /**
     * Constructs a {@code UserView} with the given authentication controller.
     * <p>
     * Initializes the view with a reference to an {@link AuthController} for handling
     * authentication operations such as login, logout, and password updates.
     * </p>
     *
     * @param controller the {@link AuthController} to handle authentication
     */
    public UserView(AuthController controller) {
        this.controller = controller;
    }

    /**
     * Prompts the user to change their password.
     * <p>
     * Requests the current password and new password, then delegates validation and
     * update to the authentication controller. Displays success or failure message based
     * on whether the current password was correct.
     * </p>
     */
    public void changePassword() {
        System.out.print("Enter current password: ");
        String oldPw = sc.nextLine();

        System.out.print("Enter new password: ");
        String newPw = sc.nextLine();

        if (controller.updatePassword(oldPw, newPw)) {
            System.out.println("Password updated!");
        } else {
            System.out.println("Incorrect password.");
        }
    }

    /**
     * Logs out the current user.
     * <p>
     * Delegates logout to the authentication controller and displays a logout confirmation message.
     * </p>
     */
    public void logout() {
        controller.logout();
        System.out.println("Logged out.");
    }

    /**
     * Starts the user view workflow.
     * <p>
     * Abstract method that must be implemented by concrete user view subclasses.
     * This method represents the entry point for each user type's specific menu flow
     * and interaction pattern.
     * </p>
     */
    public abstract void start();
}
