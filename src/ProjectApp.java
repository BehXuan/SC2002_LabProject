package src;

// import src.entity.*;
import src.controller.*;
import src.view.*;
// import src.*;

public class ProjectApp {
    public static void main(String args[]) {
        System.out.println("This is the main program");

        DataStore dataStore = DataStore.getInstance();
        System.out.println("DataStore instance obtained in ProjectApp.");
        System.out.println(dataStore.getStudentList());

    }
}
