package src;

// import src.entity.*;
import src.controller.*;
import src.view.*;
// import src.*;

public class ProjectApp {
    public static void main(String args[]) {
        System.out.println("This is the main program");

        // testing user login
        UserController uc = new UserController();
        UserView uw = new UserView(uc);
        uw.runMenuLoop();

    }
}
