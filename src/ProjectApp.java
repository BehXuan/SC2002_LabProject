package src;

import java.util.Scanner;

import src.controller.*;
import src.interfaces.AuthController;
import src.view.*;

/**
 * Entry point for the internship-management project CLI.
 *
 * <p>This class provides a simple text-based main menu which
 * allows users to choose between Student, Company Representative,
 * and Career Center Staff workflows. Selected controllers and
 * views are instantiated and executed. On choosing exit the
 * application persists in-memory data back to CSV files via
 * {@link src.DataStore#saveAll()}.
 */
public class ProjectApp {
    /**
     * Main program to run the project CLI. Displays the main menu,
     * routes to the appropriate controller/view based on user
     * selection, and persists data when the user exits.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String args[]) {
        System.out.println("This is the main program");

        DataStore dataStore = DataStore.getInstance();
        System.out.println("DataStore instance obtained in ProjectApp.");

        // take inputs
        Scanner sc = new Scanner(System.in);
        String choice;

        do {
            System.out.println("========== MAIN MENU ==========");
            System.out.println("Select user type:");
            System.out.println("1. Student");
            System.out.println("2. Company Representative");
            System.out.println("3. Career Center Staff");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextLine();

            if (choice.equals("0")) {  // exit
                System.out.println("Saving Data");
                dataStore.saveAll();
                System.out.println("Exiting system... Goodbye!");
                break;
            }

            AuthController controller = null;
            switch (choice) {
                case "1":
                    controller = new StudentController();
                    break;
                case "2":
                    controller = new CompanyRepresentativeController();
                    break;
                case "3":
                    controller = new CareerCenterStaffController();
                    break;
                default:
                    System.out.println("Invalid option.\n");
                    controller = null;
            }

            if (controller == null)
                continue;

            // Route to correct view
            if (controller instanceof StudentController) {
                StudentController ctrl = (StudentController) controller;
                new StudentView(ctrl).start();
            } else if (controller instanceof CompanyRepresentativeController) {
                CompanyRepresentativeController ctrl = (CompanyRepresentativeController) controller;
                new CompanyRepresentativeView(ctrl).start();
            } else if (controller instanceof CareerCenterStaffController) {
                CareerCenterStaffController ctrl = (CareerCenterStaffController) controller;
                new CareerCenterStaffView(ctrl).start();
            }

            System.out.println("\nReturning to main menu...\n");
        } while (choice != "0");

        sc.close();
    }
}