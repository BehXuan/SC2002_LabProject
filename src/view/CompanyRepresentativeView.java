package src.view;

import src.controller.CompanyRepresentativeController;
// import src.controller.AuthController;
import src.entity.*;
import src.enums.InternshipLevel;
// import src.enums.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompanyRepresentativeView extends UserView {
    private CompanyRepresentativeController repController;
    private Scanner sc = new Scanner(System.in);

    public CompanyRepresentativeView(CompanyRepresentativeController repController) {
        super(repController); // AuthController
        this.repController = repController;
    }

    @Override
    public void start() {
        System.out.println("1. Login as Company Representative\n2. Register as Company Representative");
        int choice = Integer.parseInt(sc.nextLine());
        if (choice == 2) {
            System.out.print("Enter Username: ");
            String username = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();
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
            boolean registered = repController.createCompanyRepresentative(username, password, name, email, companyName, department, position);
            if (registered) {
                System.out.println("Registration successful! Please wait for Career Staff approval before logging in.");
            } else {
                System.out.println("Registration failed. Please try again.");
            }
        }
        else if (choice != 1) {
            System.out.println("Invalid choice. Returning to main menu.");
        }
        loginMenu();
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
                System.out.println("Login successful! Welcome " + repController.getCurrentCompayRepresentative().getName());
                runMenuLoop();
                return; // exit login after menu
            } else {
                System.out.println("Invalid username or password or not approved yet. Try again or enter 0 to go back.");
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
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    changePassword();
                    break;
                case 2:
                    // View Internship Applications
                    List<InternshipApplication> applications = repController.getApplications();
                    if (applications == null || applications.isEmpty()) {
                        System.out.println("No internship applications found.");
                    } else {
                        System.out.println("Internship Applications:");
                        for (InternshipApplication app : applications) {
                            System.out.println(app);
                        }
                    }
                    break;

                case 3:
                    // View Internships
                    List<Internship> internships = repController.getInternships();
                    if (internships == null || internships.isEmpty()) {
                        System.out.println("No internships found.");
                    } else {
                        System.out.println("Internships:");
                        for (Internship internship : internships) {
                            System.out.println(internship);
                        }
                    }
                    break;
                case 4:
                    // Create Internships
                    System.out.print("Enter Internship Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Internship Description: ");
                    String description = sc.nextLine();
                    System.out.print("Enter Internship Level (Basic, Intermediate, Advanced): ");
                    InternshipLevel internshipLevel = InternshipLevel.valueOf(sc.nextLine().toUpperCase());
                    System.out.print("Enter Internship Major: ");
                    String major = sc.nextLine();
                    System.out.print("Enter Open Date (YYYY-MM-DD): ");
                    String openDateStr = sc.nextLine();
                    System.out.print("Enter Close Date (YYYY-MM-DD): ");
                    String closeDateStr = sc.nextLine();
                    System.out.print("Enter Number of Slots Left: ");
                    int slots = Integer.parseInt(sc.nextLine());
                    boolean created = repController.createInternship(0, title, description, internshipLevel, major,
                            java.time.LocalDate.parse(openDateStr), java.time.LocalDate.parse(closeDateStr), slots);
                    if (created) {
                        System.out.println("Internship created successfully.");
                    } else {
                        System.out.println("Failed to create internship.");
                    }
                    break;
                case 5:
                    // Approve Internships
                    List<InternshipApplication> pending_applications = repController.getApplications();
                    if (pending_applications == null || pending_applications.isEmpty()) {
                        System.out.println("No internship applications found.");
                    } else {
                        System.out.println("Internship Applications:");
                        
                        for (int i=0; i<pending_applications.size(); i++) {
                            InternshipApplication app = pending_applications.get(i);
                            System.out.println((i + 1) + ". " +
                                    ", Student: " + app.getStudent().getName() +
                                    ", Internship: " + app.getInternship().getTitle() +
                                    ", Status: " + app.getCompanyAccept());
                        } 
                    }

                    System.out.print("Enter Application ID to Approve: "); // need to do validation
                    int appIdToApprove = Integer.parseInt(sc.nextLine());
                    InternshipApplication appToApprove = repController.getApplications().get(appIdToApprove - 1);  // might have index out of range if user enter invalid id
                    
                    if (appToApprove != null) {  // might not be null check -> might throw error. need testing
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
                    List<InternshipApplication> apps = repController.getApplications();
                    if (apps == null || apps.isEmpty()) {
                        System.out.println("No internship applications found.");
                    } else {
                        System.out.println("Internship Applications:");
                        
                        for (int i=0; i<apps.size(); i++) {
                            InternshipApplication app = apps.get(i);
                            System.out.println((i + 1) + ". " +
                                    ", Student: " + app.getStudent().getName() +
                                    ", Internship: " + app.getInternship().getTitle() +
                                    ", Status: " + app.getCompanyAccept());
                        } 
                    }
                    System.out.print("Enter Application ID to Reject: "); // need to do validation
                    int appIdToReject = Integer.parseInt(sc.nextLine());
                    InternshipApplication appToReject = repController.getApplications().get(appIdToReject -1);  // might have index out of range if user enter invalid id\
                    
                    if (appToReject != null) {  // might not be null check -> might throw error. need testing
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
                
                
                    default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void viewInternshipApplications() {
        ArrayList<InternshipApplication> applications = repController.getApplications();
        if (applications == null || applications.isEmpty()) {
            System.out.println("No internship applications found.");
            return;
        }

        System.out.println("Internship Applications:");
        for (InternshipApplication app : applications) {
            // Print statement using getters directly as requested
            System.out.println("  > Student: " + app.getStudent().getName());
            System.out.println("  > Company Status: " + app.getCompanyAccept());
            System.out.println("-----------------------------------------");
        }
    }

    public void viewInternships() {
        ArrayList<Internship> internships = repController.getInternships();
        if (internships == null || internships.isEmpty()) {
            System.out.println("No internships found.");
            return;
        }

        System.out.println("Internships:");
        for (Internship internship : internships) {
            // Print statement using getters directly as requested
            System.out.println("  > Title: " + internship.getTitle());
            System.out.println("  > Description: " + internship.getDescription());
            System.out.println("  > Level: " + internship.getLevel());
            System.out.println("  > Major: " + internship.getMajor());
            System.out.println("  > Open Date: " + internship.getOpenDate());
            System.out.println("  > Close Date: " + internship.getCloseDate());
            System.out.println("  > Status: " + internship.getStatus());
            System.out.println("  > Slots Left: " + internship.getNumberOfSlotsLeft());
            
            System.out.println("-----------------------------------------");
        }
    }
}
