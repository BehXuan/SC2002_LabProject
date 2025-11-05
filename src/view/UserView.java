package src.view;

import src.controller.UserController;
import java.util.Scanner;

public class UserView {
    private UserController userController;
    private Scanner sc = new Scanner(System.in);

    public UserView(UserController userController) {
        setUserController(userController);
    }

    public UserController getUserController() {
        return this.userController;
    }

    public void setUserController(UserController uc) {
        this.userController = uc;
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    public int displayUserMenu() {
        displayMessage("----- User Menu -----");
        displayMessage("1. Login");
        displayMessage("2. Logout");
        displayMessage("3. Change Password");
        displayMessage("4. Exit");
        int choice = -1;
        while (true) {
            displayMessage("Choose an option (1-4): ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // Clear the newline
                if (choice >= 1 && choice <= 4) {
                    break;
                }
            } else {
                sc.nextLine(); // Clear invalid input
            }
            displayMessage("Invalid choice. Please try again.");
        }
        return choice;
    }

    public void runMenuLoop() {
        while (true) {
            int choice = displayUserMenu();
            switch (choice) {
                case 1:
                    login();
                    break; // In view
                case 2:
                    userController.logout();
                    displayMessage("Logged out.");
                    break; // In view
                case 3:
                    ChangePassword();
                    break; // In view
                case 4:
                    displayMessage("Exiting.");
                    return; // In view
            }
        }
    }

    public void login() {
        // Show Login and ask for login credentials
        System.out.println("Login");
        System.out.print("Enter UserId: ");
        String userId = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        boolean loginSuccess = getUserController().login(userId, password);
        if (loginSuccess) {
            displayMessage("Login Successful!");
        } else {
            displayMessage("Login Failed! Please check your UserId and Password.");
        }

    }

    public void ChangePassword() {
        System.out.println("Change Password");
        System.out.print("Enter Old Password: ");
        String oldPW = sc.nextLine();
        System.out.print("Enter New Password: ");
        String newPW = sc.nextLine();
        boolean changeSuccess = this.userController.updatePassword(oldPW, newPW);
        if (changeSuccess) {
            displayMessage("Password changed successfully!");
        } else {
            displayMessage("Password change failed!");
        }
    }

}
