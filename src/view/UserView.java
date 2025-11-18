package src.view;
import java.util.Scanner;
import src.controller.AuthController;



public abstract class UserView {

    protected AuthController controller;
    protected Scanner sc = new Scanner(System.in);

    public UserView(AuthController controller) {
        this.controller = controller;
    }

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

    public void logout() {
        controller.logout();
        System.out.println("Logged out.");
    }

    // ABSTRACT METHOD NEEDS TO BE IMPLEMENTED
    public abstract void start();
}
