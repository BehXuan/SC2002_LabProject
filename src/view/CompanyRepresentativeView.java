package src.view;

import src.controller.CompanyRepresentativeController;
// import src.controller.AuthController;
import src.entity.*;
import src.enums.InternshipLevel;
// import src.enums.*;
import src.interfaces.viewApplication;
import src.interfaces.viewInternship;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class CompanyRepresentativeView extends UserView implements viewInternship, viewApplication {
    private CompanyRepresentativeController repController;
    private Scanner sc = new Scanner(System.in);

    public CompanyRepresentativeView(CompanyRepresentativeController repController) {
        super(repController); // AuthController
        this.repController = repController;
    }

    @Override
    public void start() {
        String choice = "";
        while (true) {
            System.out.println("1. Login as Company Representative\n2. Register as Company Representative");
            choice = sc.nextLine().trim();
            if (choice.equals("1") || choice.equals("2")) {
                break;
            }
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }

        if (choice.equals("2")) { // create company rep
            registerCompanyRepresentative();
        } else if (choice.equals("1")) { // login
            loginMenu();
        }
    }

    private void registerCompanyRepresentative() {
        System.out.println("----- Company Representative Registration -----");
        System.out.print("Enter Username: ");

        String username = "";
        while (true) {
            username = sc.nextLine().trim();

            if (username.isEmpty()) {
                System.out.print("Username cannot be empty. Please enter a valid Username: ");
                continue;
            }
            break;
        }

        String password = "";
        while (true) {
            System.out.print("Enter Password: ");
            password = sc.nextLine();
            if (password.isEmpty()) {
                System.out.print("Password cannot be empty. Please enter a valid Password: ");
                continue;
            }

            System.out.print("Confirm Password: ");
            String confirmPassword = sc.nextLine();
            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
                continue;
            }
            break;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Company Name: ");
        String companyName = sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.nextLine();

        System.out.print("Enter Position: ");
        String position = sc.nextLine();

        boolean success = repController.createCompanyRepresentative(username, password, name, email, companyName,
                department, position);
        if (success) {
            System.out.println("Registration successful! Please wait for admin approval before logging in.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }

    }

    private void loginMenu() {
        while (true) {
            System.out.println("----- Company Representative Login -----");
            System.out.println("Enter 0 at any time to return to main menu.");
            System.out.print("Username: ");
            String username = sc.nextLine();
            if (username.equals("0")) {
                System.out.println("Returning to main menu...");
                return;
            }

            System.out.print("Password: ");
            String password = sc.nextLine();
            if (password.equals("0")) {
                System.out.println("Returning to main menu...");
                return;
            }

            if (repController.login(username, password)) {
                System.out.println(
                        "Login successful! Welcome " + repController.getCurrentCompayRepresentative().getName());
                runMenuLoop();
                return; // exit login after menu
            } else {
                System.out
                        .println("Invalid username or password or not approved yet. Try again or enter 0 to go back.");
            }
        }
    }

    private void runMenuLoop() {
        int choice;
        while (true) {
            System.out.println("\n===== Company Representative Menu (Logged in as: " +
                    repController.getCurrentCompayRepresentative().getName() + ") =====");
            System.out.println("1. Change Password");
            System.out.println("2. View Internship Applications");
            System.out.println("3. View Internships");
            System.out.println("4. Create Internships");
            System.out.println("5. Approve Internships");
            System.out.println("6. Reject Internships");
            System.out.println("7. Logout / Return to Main Menu");
            System.out.println("8. Delete Internship");
            System.out.println("9. Edit Internship");
            System.out.println("10. Toggle Internship Visibility");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    changePassword();
                    logout();
                    return;
                case 2:
                    // View Internship Applications
                    viewApplications();
                    break;

                case 3:
                    // View Internships
                    viewInternships();
                    break;
                case 4:
                    // Create Internships
                    System.out.print("Enter Internship Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Internship Description: ");
                    String description = sc.nextLine();
                    // Internship Level with enum validation
                    InternshipLevel internshipLevel = null;
                    while (true) {
                        System.out.print("Enter Internship Level (Basic, Intermediate, Advanced): ");
                        String levelInput = sc.nextLine().trim();
                        try {
                            internshipLevel = InternshipLevel.valueOf(levelInput.toUpperCase());
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid level. Please enter: Basic, Intermediate, or Advanced.");
                        }
                    }
                    System.out.print("Enter Internship Major: ");
                    String major = sc.nextLine();

                    // Open Date with date validation
                    LocalDate openDate = null;
                    while (true) {
                        System.out.print("Enter Open Date (YYYY-MM-DD): ");
                        String openDateStr = sc.nextLine().trim();
                        try {
                            openDate = LocalDate.parse(openDateStr);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use YYYY-MM-DD format (e.g., 2024-01-15).");
                        }
                    }

                    LocalDate closeDate = null;
                    while (true) {
                        System.out.print("Enter Close Date (YYYY-MM-DD): ");
                        String closeDateStr = sc.nextLine().trim();
                        try {
                            closeDate = LocalDate.parse(closeDateStr);
                            // Check if close date is after open date
                            if (closeDate.isBefore(openDate) || closeDate.isEqual(openDate)) {
                                System.out
                                        .println("Close date must be after the open date. Please enter a later date.");
                                continue;
                            }
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use YYYY-MM-DD format (e.g., 2024-12-31).");
                        }
                    }

                    int slots = 0;
                    while (true) {
                        System.out.print("Enter Number of Slots: ");
                        String slotsStr = sc.nextLine().trim();
                        try {
                            slots = Integer.parseInt(slotsStr);
                            if (slots >= 1) {
                                break;
                            } else {
                                System.out.println("Number of slots must be more then 0. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Please enter a valid number bigger than 0.");
                        }
                    }

                    boolean created = repController.createInternship(title, description, internshipLevel, major,
                            openDate, closeDate, slots);

                    if (created) {
                        System.out.println("Internship created successfully.");
                    } else {
                        System.out.println("Failed to create internship.");
                    }
                    break;

                case 5:
                    // Approve Internships
                    viewApplications();
                    int indexToApprove = -1;
                    boolean validInput = false;

                    while (!validInput) {
                        System.out.print("Enter index to Approve: ");
                        String input = sc.nextLine();
                        try {
                            indexToApprove = Integer.parseInt(input);
                            if (indexToApprove >= 1 && indexToApprove <= repController.getApplications().size()) {
                                validInput = true;
                            } else {
                                System.out.println("Invalid index. Please enter a number between 1 and "
                                        + repController.getApplications().size() + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }

                    InternshipApplication appToApprove = repController.getApplications().get(indexToApprove - 1);

                    if (appToApprove != null) {
                        boolean approved = repController.approveInternshipApplication(appToApprove);
                        if (approved) {
                            System.out.println("Internship application approved.");
                        } else {
                            System.out.println("Failed to approve internship application.");
                        }
                    } else {
                        System.out.println("Application ID not found.");
                    }
                    break;

                case 6:
                    // Reject Internships
                    viewApplications();
                    int indexToReject = -1;
                    validInput = false;
                    while (!validInput) {
                        System.out.print("Enter index to Reject: ");
                        String input = sc.nextLine();
                        try {
                            indexToReject = Integer.parseInt(input);
                            if (indexToReject >= 1 && indexToReject <= repController.getApplications().size()) {
                                validInput = true;
                            } else {
                                System.out.println("Invalid index. Please enter a number between 1 and "
                                        + repController.getApplications().size() + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }
                    
                    InternshipApplication appToReject = repController.getApplications().get(indexToReject - 1); 

                    if (appToReject != null) {
                        boolean approved = repController.rejectInternshipApplication(appToReject);
                        if (approved) {
                            System.out.println("Internship application rejected.");
                        } else {
                            System.out.println("Failed to reject internship application.");
                        }
                    } else {
                        System.out.println("Application ID not found.");
                    }
                    break;

                case 7:
                    logout();
                    System.out.println("Logged out. Returning to main menu...");
                    return;

                case 8:
                    // Delete Internship
                    viewInternships();
                    int indexToDelete = -1;
                    validInput = false;
                    while (!validInput) {
                        System.out.print("Enter index of Internship to Delete: ");
                        String input = sc.nextLine();
                        try {
                            indexToDelete = Integer.parseInt(input);
                            if (indexToDelete >= 1 && indexToDelete <= repController.getInternships().size()) {
                                validInput = true;
                            } else {
                                System.out.println("Invalid index. Please enter a number between 1 and "
                                        + repController.getInternships().size() + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }
                    Internship internshipToDelete = repController.getInternships().get(indexToDelete - 1);
                    if (internshipToDelete != null) {
                        boolean deleted = repController.deleteInternship(internshipToDelete);
                        if (deleted) {
                            System.out.println("Internship deleted successfully.");
                        } else {
                            System.out.println("Failed to delete internship. It may be approved already.");
                        }
                    } else {
                        System.out.println("Internship ID not found.");
                    }

                    break;

                case 9:
                    // Edit Internship
                    viewInternships();
                    int indexToEdit = -1;
                    validInput = false;
                    while (!validInput) {
                        System.out.print("Enter index of Internship to Edit: ");
                        String input = sc.nextLine();
                        try {
                            indexToEdit = Integer.parseInt(input);
                            if (indexToEdit >= 1 && indexToEdit <= repController.getInternships().size()) {
                                validInput = true;
                            } else {
                                System.out.println("Invalid index. Please enter a number between 1 and "
                                        + repController.getInternships().size() + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }
                    Internship internshipToEdit = repController.getInternships().get(indexToEdit - 1);
                    if (internshipToEdit != null) {
                        // Gather new details
                        System.out.print("Enter new Internship Title: ");
                        String newTitle = sc.nextLine();
                        System.out.print("Enter new Internship Description: ");
                        String newDescription = sc.nextLine();
                        // Internship Level with enum validation
                        InternshipLevel newInternshipLevel = null;
                        while (true) {
                            System.out.print("Enter new Internship Level (Basic, Intermediate, Advanced): ");
                            String levelInput = sc.nextLine().trim();
                            try {
                                newInternshipLevel = InternshipLevel.valueOf(levelInput.toUpperCase());
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid level. Please enter: Basic, Intermediate, or Advanced.");
                            }
                        }
                        System.out.print("Enter new Internship Major: ");
                        String newMajor = sc.nextLine();

                        // Open Date with date validation
                        LocalDate newOpenDate = null;
                        while (true) {
                            System.out.print("Enter new Open Date (YYYY-MM-DD): ");
                            String openDateStr = sc.nextLine().trim();
                            try {
                                newOpenDate = LocalDate.parse(openDateStr);
                                break;
                            } catch (DateTimeParseException e) {
                                System.out
                                        .println("Invalid date format. Please use YYYY-MM-DD format (e.g., 2024-01-15).");
                            }
                        }

                        LocalDate newCloseDate = null;
                        while (true) {
                            System.out.print("Enter new Close Date (YYYY-MM-DD): ");
                            String closeDateStr = sc.nextLine().trim();
                            try {
                                newCloseDate = LocalDate.parse(closeDateStr);
                                // Check if close date is after open date
                                if (newCloseDate.isBefore(newOpenDate) || newCloseDate.isEqual(newOpenDate)) {
                                    System.out.println(
                                            "Close date must be after the open date. Please enter a later date.");
                                    continue;
                                }
                                break;
                            } catch (DateTimeParseException e) {
                                System.out.println(
                                        "Invalid date format. Please use YYYY-MM-DD format (e.g., 2024-12-31).");
                            }
                        }

                        int newSlots = 0;
                        while (true) {
                            System.out.print("Enter new Number of Slots: ");
                            String slotsStr = sc.nextLine().trim();
                            try {
                                newSlots = Integer.parseInt(slotsStr);
                                if (newSlots >= 1) {
                                    break;
                                } else {
                                    System.out.println("Number of slots must be more then 0. Please try again.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number. Please enter a valid number bigger than 0.");
                            }
                        }
                        boolean edited = repController.editInternship(internshipToEdit, newTitle, newDescription,
                                newInternshipLevel, newMajor, newOpenDate, newCloseDate, newSlots);
                        if (edited) {
                            System.out.println("Internship edited successfully.");
                        } else {
                            System.out.println("Failed to edit internship. It may be approved already.");
                        }
                    } else {
                        System.out.println("Internship ID not found.");
                    }
                    break;

                case 10:
                    // Toggle Internship Visibility
                    viewInternships();
                    int indexToToggle = -1;
                    validInput = false;
                    while (!validInput) {
                        System.out.print("Enter index of Internship to Toggle Visibility: ");
                        String input = sc.nextLine();
                        try {
                            indexToToggle = Integer.parseInt(input);
                            if (indexToToggle >= 1 && indexToToggle <= repController.getInternships().size()) {
                                validInput = true;
                            } else {
                                System.out.println("Invalid index. Please enter a number between 1 and "
                                        + repController.getInternships().size() + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    } 
                    Internship internshipToToggle = repController.getInternships().get(indexToToggle - 1);
                    if (internshipToToggle != null) {
                        boolean toggled = repController.toggleVisibility(internshipToToggle);
                        if (toggled) {
                            System.out.println("Internship visibility toggled successfully.");
                        } else {
                            System.out.println("Failed to toggle internship visibility.");
                        }
                    } else {
                        System.out.println("Internship ID not found.");
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    @Override
    public void viewApplications() {
        ArrayList<InternshipApplication> applications = repController.getApplications();
        if (applications == null || applications.isEmpty()) {
            System.out.println("No internship applications found.");
            return;
        }

        System.out.println("Internship Applications:");
        for (int i = 1; i <= applications.size(); i++) {
            System.out.print(i + ". ");
            viewApplicationDetails(applications.get(i-1));
            
        }
    }

    @Override
    public void viewApplicationDetails(InternshipApplication application) {
        System.out.println(application);
    }

    @Override
    public void viewInternships() {
        ArrayList<Internship> internships = repController.getInternships();
        if (internships == null || internships.isEmpty()) {
            System.out.println("No internships found.");
            return;
        }

        System.out.println("Internships:");

        for (int i=1; i<=internships.size(); i++) {
            System.out.print(i + ". ");
            viewInternshipDetails(internships.get(i-1));
        }
    }

    @Override
    public void viewInternshipDetails(Internship internship) {
        System.out.println(internship);
    }
}
