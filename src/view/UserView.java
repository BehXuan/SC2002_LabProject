package src.view;
import src.entity.User;
import src.controller.UserController;
import java.util.Scanner;

public class UserView {
    private UserController userController;
    private Scanner sc = new Scanner(System.in)

    public UserView(UserController userController){
        this.userController = userController;
    }

    public String[] displayLogin(){
        System.out.println("Login");
        System.out.print("Enter UserId: ");
        String userId = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        return new String[] {userId, password};
    }

    public int displayUserMenu(){};
    public String[] displayChangePassword(){
        System.out.println("Change Password");
        System.out.print("Enter Old Password: ");
        String oldPW = sc.nextLine();
        System.out.print("Enter New Password: ");
        String newPW = sc.nextLine();
        return new String[]{oldPW, newPW};
    }

    public void displayMessage(String msg){
        System.out.println(msg);
    }

}
