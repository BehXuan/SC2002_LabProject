package src.view;

import src.controller.StudentController;
// import src.controller.AuthController; // Inherit basic view functionality
import src.entity.Internship;
import src.entity.InternshipApplication;
// import src.entity.Student;
import src.enums.InternshipStatus;
import src.report.ReportCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentView extends UserView {
    // The controller is casted to StudentController for student-specific methods
    private StudentController studentController;
    private Scanner sc = new Scanner(System.in);


    // Constructor chains up to UserView
    public StudentView(StudentController studentController) {
        // Pass the generic controller up to UserView
        super(studentController); 
        this.studentController = studentController;
    }
     @Override
    public void start() {
        // Start login flow first
        loginMenu();   
    }

    private void loginMenu() {
    while (true) {
        System.out.println("----- Student Login -----");
        System.out.println("Enter 0 at any time to return to the main menu.");
        System.out.println("Username: ");
        String username = sc.nextLine();
        if (username.equals("0")) {
            System.out.println("Returning to main menu...");
            return;
        }
        System.out.println("Password: ");
        String password = sc.nextLine();
        if (password.equals("0")) {
            System.out.println("Returning to main menu...");
            return;
        }

        // Authenticate via AuthController
        if (studentController.login(username, password)) {
            System.out.println("Login successful! Welcome " + studentController.getCurrentStudent().getName());
            runStudentMenuLoop(); // Enter student menu
            return; // exit login menu after student menu ends
        } else {
            System.out.println("Invalid username or password. Try again or enter 0 to go back.");
            }
        }
    }

    // Helper to display the main student menu options
    public int displayStudentMenu() {
        System.out.println("\n----- Student Menu (Logged in as: " + studentController.getCurrentStudent().getName() + ") -----");
        System.out.println("1. View Available Internship Opportunities");
        System.out.println("2. View My Applications");
        System.out.println("3. Apply for Internship");
        System.out.println("4. Accept Internship");
        System.out.println("5. Withdraw Application");
        System.out.println("6. Change Password");
        System.out.println("7. Logout");
       
        
        int choice = -1;
        while (true) {
            System.out.println("Choose an option (1-5): ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // Clear the newline
                if (choice >= 1 && choice <= 7) {
                    break;
                }
            } else {
                sc.nextLine(); // Clear invalid input
            }
            System.out.println("Invalid choice. Please try again.");
        }
        return choice;
    }

    public void runStudentMenuLoop() {
        while (true) {
            int choice = displayStudentMenu();
            switch (choice) {
                case 1:
                    generateReport();
                    break;
                case 2:
                    viewMyApplications();
                    break;
                case 3:
                    viewInternshipOpportunities();
                    System.out.println("Enter the number of the internship you want to apply for:");
                    int internChoice = sc.nextInt();
                    sc.nextLine(); // Clear newline
                    ArrayList<Internship> opportunities = studentController.getInternshipsOpportunities();
                    if (internChoice >= 1 && internChoice <= opportunities.size()) {
                        Internship selectedInternship = opportunities.get(internChoice - 1);
                        displayInternshipDetails(selectedInternship);
                        applyInternship(selectedInternship);
                    } else {
                        System.out.println("Invalid internship selection.");
                    }
                    break;
                case 4:
                    viewMyApplications();
                    ArrayList<InternshipApplication> applications = studentController.getMyApplications();
                    System.out.println("Enter the number of the application you want to accept:");
                    int appChoice = sc.nextInt();
                    sc.nextLine(); // Clear newline
                    if (appChoice >= 1 && appChoice <= applications.size()) {
                        InternshipApplication selectedApp = applications.get(appChoice - 1);
                        acceptInternship(selectedApp);
                    } else {
                        System.out.println("Invalid application selection.");
                    }
                    break;
                case 5:
                    System.out.println("Feature not implemented yet.");
                    break;
                case 6:
                    // Inherited from UserView
                    changePassword(); 
                    break; 
                case 7:
                    // Inherited from UserController/UserView
                    logout(); 
                    System.out.println("Logged out successfully.");
                    return; // Exit the student loop back to the main login prompt
            
                default:
                    System.out.println("Invalid Choice, please try again!");
            }
        }
    }

    private void viewInternshipOpportunities() {
        ArrayList<Internship> opportunities = studentController.getInternshipsOpportunities();
        if (opportunities.isEmpty()) {
            System.out.println("\nNo internship opportunities are currently visible or available for your profile.");
            return;
        }

        System.out.println("\n--- Available Internship Opportunities ---");
        for (int i = 0; i < opportunities.size(); i++) {
            Internship opp = opportunities.get(i);
            System.out.println(String.format("%d. ID: %d | Title: %s | Company: %s | Level: %s | Slots: %d", 
                i + 1, opp.getInternshipId(), opp.getTitle(), opp.getCompanyRep().getCompanyName(), opp.getLevel(), opp.getNumberOfSlotsLeft()));
        }
    }

    private void displayInternshipDetails(Internship internship) {
        System.out.println("\n--- Internship Details ---");
        System.out.println("Title: " + internship.getTitle());
        System.out.println("Company: " + internship.getCompanyRep().getCompanyName());
        System.out.println("Description: " + internship.getDescription());
        System.out.println("Level: " + internship.getLevel());
        System.out.println("Preferred Major: " + internship.getMajor());
        System.out.println("Application Close Date: " + internship.getCloseDate());
        System.out.println("Available Slots: " + internship.getNumberOfSlotsLeft());
        System.out.println("---");
    }

    private void applyInternship(Internship internship) {
        System.out.println("Do you want to apply for this internship? (Y/N)");
        String response = sc.nextLine().toUpperCase();
        
        if (response.equals("Y")) {
            boolean success = studentController.applyForInternship(internship);
            if (success) {
                System.out.println("Application successful! You can view the status under 'My Applications'.");
            } else {
                System.out.println("Application failed. Possible reasons: Max applications reached (3), already applied, or not your major.");
            }
        }
    }

    private void viewMyApplications() {
        ArrayList<InternshipApplication> applications = studentController.getMyApplications();
        if (applications.isEmpty()) {
            System.out.println("\nYou have no active internship applications.");
            return;
        }

        System.out.println("\n--- My Internship Applications ---");
        for (int i = 0; i < applications.size(); i++) {
            InternshipApplication app = applications.get(i);
            // Student status should be based on companyAccept [cite: 61, 62]
            String status = app.getCompanyAccept().toString(); 
            if (app.getStudentAccept() != null && app.getStudentAccept().toString().equals("ACCEPTED")) {
                status = "ACCEPTED (Confirmed)";
            }
            
            System.out.println(String.format("%d. Internship: %s | Company: %s | Status: %s", 
                i + 1, app.getInternship().getTitle(), app.getInternship().getCompanyRep().getCompanyName(), status));
        }
    }

    private void acceptInternship(InternshipApplication selectedApp) {
        boolean success = studentController.acceptInternshipOffer(selectedApp);
        if (success) {
            System.out.println("You have successfully accepted the internship offer for " + selectedApp.getInternship().getTitle() + ".");
        } else {
            System.out.println("Failed to accept the internship offer. Ensure the application is approved by the company.");
        }
    }

    private void generateReport() {
        ReportCriteria criteria = new ReportCriteria();

        System.out.print("Filter by Title (or leave blank): ");
        String title = sc.nextLine();
        if (!title.isBlank()) criteria.setTitle(title);

        System.out.print("Filter by Major (or leave blank): ");
        String major = sc.nextLine();
        if (!major.isBlank()) criteria.setMajor(major);

        System.out.print("Filter by Company ID (or leave blank): ");
        String companyId = sc.nextLine();
        if (!companyId.isBlank()) criteria.setCompanyRepId(companyId);

        criteria.setStatus(InternshipStatus.APPROVED); // Students only see approved internships

        System.out.print("Filter by Internship Level (BASIC/INTERMEDIATE/ADVANCED or leave blank): ");
        String level = sc.nextLine();
        if (!level.isBlank()) {
            try {
                criteria.setLevel(src.enums.InternshipLevel.valueOf(level.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Level. Ignoring.");
            }
        }

        System.out.print("Minimum Slots left (or leave blank): ");
        String minSlots = sc.nextLine();
        if (!minSlots.isBlank()) {
            try {
                criteria.setMinSlots(Integer.parseInt(minSlots));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Ignoring.");
            }
        }

        System.out.print("Sort by (TITLE, COMPANY, OPEN_DATE, CLOSE_DATE, SLOTS_LEFT): ");
        String sort = sc.nextLine();
        if (!sort.isBlank()) {
            try {
                criteria.setSortType(src.enums.ReportSortType.valueOf(sort.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid sort type. Defaulting to TITLE.");
            }
        }

        // Delegate report generation and printing to controller
        List<Internship> report = studentController.generateReport(criteria);
        studentController.printReport(report);
    }

}
