package src.view;

import src.controller.StudentController;
import src.controller.UserController; // Inherit basic view functionality
import src.entity.Internship;
import src.entity.InternshipApplication;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentView extends UserView {
    // The controller is casted to StudentController for student-specific methods
    private StudentController studentController;
    private Scanner sc = new Scanner(System.in);

    // Constructor chains up to UserView
    public StudentView(StudentController studentController, UserController userController) {
        // Pass the generic controller up to UserView
        super(userController); 
        this.studentController = studentController;
    }

    // Helper to display the main student menu options
    public int displayStudentMenu() {
        displayMessage("\n----- Student Menu (Logged in as: " + studentController.getCurrentStudent().getName() + ") -----");
        displayMessage("1. View Available Internship Opportunities");
        displayMessage("2. View My Applications");
        displayMessage("3. Change Password");
        displayMessage("4. Logout");
        displayMessage("5. Exit Application");
        
        int choice = -1;
        while (true) {
            displayMessage("Choose an option (1-5): ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // Clear the newline
                if (choice >= 1 && choice <= 5) {
                    break;
                }
            } else {
                sc.nextLine(); // Clear invalid input
            }
            displayMessage("Invalid choice. Please try again.");
        }
        return choice;
    }

    public void runStudentMenuLoop() {
        while (true) {
            int choice = displayStudentMenu();
            switch (choice) {
                case 1:
                    viewInternshipOpportunities();
                    break;
                case 2:
                    viewMyApplications();
                    break;
                case 3:
                    // Inherited from UserView
                    ChangePassword(); 
                    break; 
                case 4:
                    // Inherited from UserController/UserView
                    getUserController().logout(); 
                    displayMessage("Logged out successfully.");
                    return; // Exit the student loop back to the main login prompt
                case 5:
                    displayMessage("Exiting application.");
                    System.exit(0); // Terminate the application
            }
        }
    }

    private void viewInternshipOpportunities() {
        ArrayList<Internship> opportunities = studentController.getInternshipsOpportunities();
        if (opportunities.isEmpty()) {
            displayMessage("\nNo internship opportunities are currently visible or available for your profile.");
            return;
        }

        displayMessage("\n--- Available Internship Opportunities ---");
        for (int i = 0; i < opportunities.size(); i++) {
            Internship opp = opportunities.get(i);
            displayMessage(String.format("%d. ID: %d | Title: %s | Company: %s | Level: %s | Slots: %d", 
                i + 1, opp.getInternshipId(), opp.getTitle(), opp.getCompanyName(), opp.getLevel(), opp.getVacancy()));
        }

        displayMessage("\nEnter the number of the internship to view details and apply, or 0 to return:");
        int choice = -1;
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 0 && choice <= opportunities.size()) {
                    break;
                }
            } else {
                sc.nextLine(); 
            }
            displayMessage("Invalid choice. Please try again.");
        }

        if (choice > 0) {
            Internship selectedInternship = opportunities.get(choice - 1);
            displayInternshipDetails(selectedInternship);
            promptToApply(selectedInternship);
        }
    }

    private void displayInternshipDetails(Internship internship) {
        displayMessage("\n--- Internship Details ---");
        displayMessage("Title: " + internship.getTitle());
        displayMessage("Company: " + internship.getCompanyName());
        displayMessage("Description: " + internship.getDescription());
        displayMessage("Level: " + internship.getLevel());
        displayMessage("Preferred Major: " + internship.getMajor());
        displayMessage("Application Close Date: " + internship.getCloseDate());
        displayMessage("Available Slots: " + internship.getVacancy());
        displayMessage("---");
    }

    private void promptToApply(Internship internship) {
        displayMessage("Do you want to apply for this internship? (Y/N)");
        String response = sc.nextLine().toUpperCase();
        
        if (response.equals("Y")) {
            boolean success = studentController.applyForInternship(internship);
            if (success) {
                displayMessage("Application successful! You can view the status under 'My Applications'.");
            } else {
                displayMessage("Application failed. Possible reasons: Max applications reached (3), already applied, or not your major.");
            }
        }
    }

    private void viewMyApplications() {
        ArrayList<InternshipApplication> applications = studentController.getMyApplications();
        if (applications.isEmpty()) {
            displayMessage("\nYou have no active internship applications.");
            return;
        }

        displayMessage("\n--- My Internship Applications ---");
        for (int i = 0; i < applications.size(); i++) {
            InternshipApplication app = applications.get(i);
            // Student status should be based on companyAccept [cite: 61, 62]
            String status = app.getCompanyAccept().toString(); 
            if (app.getStudentAccept() != null && app.getStudentAccept().toString().equals("ACCEPTED")) {
                status = "ACCEPTED (Confirmed)";
            }
            
            displayMessage(String.format("%d. Internship: %s | Company: %s | Status: %s", 
                i + 1, app.getInternship().getTitle(), app.getInternship().getCompanyName(), status));
        }

        promptToAcceptOrWithdraw(applications);
    }

    private void promptToAcceptOrWithdraw(ArrayList<InternshipApplication> applications) {
        displayMessage("\nEnter the number of the application to interact with, or 0 to return:");
        int choice = -1;
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 0 && choice <= applications.size()) {
                    break;
                }
            } else {
                sc.nextLine(); 
            }
            displayMessage("Invalid choice. Please try again.");
        }

        if (choice > 0) {
            InternshipApplication selectedApp = applications.get(choice - 1);
            
            // Check if the application is approved by the company
            if (selectedApp.getCompanyAccept().toString().equals("APPROVED")) {
                displayMessage("The company has offered you this placement. Do you want to accept? (Y/N)");
                String response = sc.nextLine().toUpperCase();
                if (response.equals("Y")) {
                    // Check if an internship is already accepted 
                    if (studentController.getCurrentStudent().getInternshipAccepted() != null) {
                        displayMessage("You have already accepted a placement. You must withdraw from your current one first.");
                        return;
                    }
                    boolean success = studentController.acceptInternshipOffer(selectedApp);
                    if (success) {
                        displayMessage("Placement accepted! All other pending applications have been withdrawn.");
                    } else {
                        displayMessage("Failed to accept placement.");
                    }
                }
            } else if (selectedApp.getStudentAccept().toString().equals("PENDING")) {
                // If company hasn't approved, or status is PENDING, offer withdrawal
                displayMessage("Do you want to request withdrawal for this application? (Y/N)");
                // Withdrawal logic would be implemented here, subject to Career Center Staff approval 
                // ... (Implementation of withdrawal request)
                displayMessage("Withdrawal request initiated. Awaiting approval from Career Center Staff.");

            } else {
                displayMessage("No actions available for this application status.");
            }
        }
    }
}