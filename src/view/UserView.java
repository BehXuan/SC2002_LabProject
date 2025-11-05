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

    public int displayUserMenu() {
        return 0;
    }

    public String[] displayChangePassword() {
        System.out.println("Change Password");
        System.out.print("Enter Old Password: ");
        String oldPW = sc.nextLine();
        System.out.print("Enter New Password: ");
        String newPW = sc.nextLine();
        return new String[] { oldPW, newPW };
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }

}
