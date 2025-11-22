package src.view;

import src.controller.StudentController;
import src.entity.Internship;
import src.entity.InternshipApplication;
import src.enums.InternshipStatus;
import src.enums.LoginResult;
import src.report.ReportCriteria;
import src.interfaces.viewApplication;
import src.interfaces.viewInternship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * View layer for Student user interface.
 * <p>
 * Provides a menu-driven interface for students to browse internship opportunities, submit
 * applications, manage their applications, and generate filtered internship reports.
 * Extends {@link UserView} for authentication and implements {@link viewInternship} and
 * {@link viewApplication} for viewing internships and applications.
 * </p>
 */
public class StudentView extends UserView implements viewInternship, viewApplication {
    // The controller is casted to StudentController for student-specific methods
    private StudentController studentController;
    private Scanner sc = new Scanner(System.in);

    /**
     * Constructs a {@code StudentView} with the given controller.
     * <p>
     * Initializes the view with a reference to the {@link StudentController} for
     * delegating business logic operations.
     * </p>
     *
     * @param studentController the {@code StudentController} to handle student operations
     */
    public StudentView(StudentController studentController) {
        // Pass the generic controller up to UserView
        super(studentController);
        this.studentController = studentController;
    }

    /**
     * Starts the student view by displaying the login menu.
     */
    @Override
    public void start() {
        // Start login flow first
        loginMenu();
    }

    /**
     * Displays and handles the student login menu.
     * <p>
     * Prompts the user to enter username and password, validates credentials using the controller,
     * and transitions to the main student menu upon successful authentication. Handles various
     * login result enums to provide appropriate feedback messages.
     * </p>
     */
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
            LoginResult result = studentController.login(username, password);

            switch (result) {
                case SUCCESS:
                    System.out.println("Login successful! Welcome " + studentController.getCurrentStudent().getName());
                    runStudentMenuLoop();
                    return; // Leave login screen after student menu ends

                case USER_NOT_FOUND:
                    System.out.println("Error: Username does not exist. Try again or enter 0 to go back.");
                    break;

                case WRONG_PASSWORD:
                    System.out.println("Error: Incorrect password. Try again or enter 0 to go back.");
                    break;
                case USER_NOT_APPROVED:
                    System.out.println("Error: User has not been approved. Try again or enter 0 to go back.");
                    break;
            }

        }
    }

    /**
     * Displays the main student menu and retrieves the user's choice.
     * <p>
     * Presents 7 menu options including internship browsing, application management,
     * password change, and logout. Validates input and ensures a valid choice is returned.
     * </p>
     *
     * @return an integer (1-7) representing the user's menu selection
     */
    public int displayStudentMenu() {
        System.out.println(
                "\n----- Student Menu (Logged in as: " + studentController.getCurrentStudent().getName() + ") -----");
        System.out.println("1. View Available Internship Opportunities");
        System.out.println("2. View My Applications");
        System.out.println("3. Apply for Internship");
        System.out.println("4. Accept Internship");
        System.out.println("5. Withdraw Application");
        System.out.println("6. Change Password");
        System.out.println("7. Logout");

        int choice = -1;
        while (true) {
            System.out.println("Choose an option (1-7): ");
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

    /**
     * Runs the main menu loop for authenticated students.
     * <p>
     * Displays a menu with 7 options including viewing internships, managing applications,
     * changing password, and logging out. Routes each selection to appropriate handler methods.
     * </p>
     */
    public void runStudentMenuLoop() {
        while (true) {
            int choice = displayStudentMenu();
            switch (choice) {
                case 1:
                    generateReport();
                    break;
                case 2:
                    viewApplications();
                    break;
                case 3:
                    viewInternships();
                    if (studentController.getInternshipsOpportunities().size() <= 0) {
                        break;
                    }
                    int internChoice = -1;
                    boolean validInput3 = false;
                    while (!validInput3) {
                        System.out.print("Enter the index of the internship you want to apply for: ");
                        String input = sc.nextLine();
                        try {
                            internChoice = Integer.parseInt(input);
                            if (internChoice >= 1
                                    && internChoice <= studentController.getInternshipsOpportunities().size()) {
                                validInput3 = true;
                            } else {
                                System.out.println("Invalid index. Please enter a number between 1 and "
                                        + studentController.getInternshipsOpportunities().size() + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }

                    Internship selectedInternship = studentController.getInternshipsOpportunities()
                            .get(internChoice - 1);
                    viewInternshipDetails(selectedInternship);
                    applyInternship(selectedInternship);
                    break;
                case 4: // accept application
                    viewApplications();
                    if (studentController.getMyApplications().size() <= 0) {
                        break;
                    }
                    int indexToApprove = -1;
                    boolean validInput4 = false;
                    while (!validInput4) {
                        System.out.print("Enter the index of the application you want to accept: ");
                        String input = sc.nextLine();
                        try {
                            indexToApprove = Integer.parseInt(input);
                            if (indexToApprove >= 1 && indexToApprove <= studentController.getMyApplications().size()) {
                                validInput4 = true;
                            } else {
                                System.out.println("Invalid index. Please enter a number between 1 and "
                                        + studentController.getMyApplications().size() + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }

                    InternshipApplication appToAccept = studentController.getMyApplications().get(indexToApprove - 1);
                    if (appToAccept != null) {
                        acceptInternship(appToAccept);
                    } else {
                        System.out.println("Application ID not found.");
                    }
                    break;

                case 5:
                    viewApplications();
                    if (studentController.getMyApplications().size() <= 0) {
                        break;
                    }
                    int indexToWithdraw = -1;
                    boolean validInput5 = false;
                    while (!validInput5) {
                        System.out.print("Enter index of Application to Delete: ");
                        String input = sc.nextLine();
                        try {
                            indexToWithdraw = Integer.parseInt(input);
                            if (indexToWithdraw >= 1
                                    && indexToWithdraw <= studentController.getMyApplications().size()) {
                                validInput5 = true;
                            } else {
                                System.out.println("Invalid index. Please enter a number between 1 and "
                                        + studentController.getMyApplications().size() + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }

                    InternshipApplication appToWithdraw = studentController.getMyApplications()
                            .get(indexToWithdraw - 1);

                    if (appToWithdraw != null) {
                        if (appToWithdraw
                                .getInternshipWithdrawalStatus() == src.enums.InternshipWithdrawalStatus.PENDING) {
                            System.out.println(
                                    "You have already requested a withdrawal for this application. Please wait for processing.");
                            break;
                        }
                        boolean withdrawSuccess = studentController.wtihdraw(appToWithdraw);
                        if (withdrawSuccess) {
                            System.out.println("Application withdrawal request submitted successfully.");
                        } else {
                            System.out.println("Failed to submit withdrawal request. Please try again.");
                        }
                    } else {
                        System.out.println("Application not found.");
                    }
                    break;
                case 6:
                    // Inherited from UserView
                    changePassword();
                    logout();
                    return;
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

    /**
     * Prompts the student to confirm and submit an internship application.
     * <p>
     * Displays the selected internship details and requests confirmation from the student.
     * If confirmed, delegates the application to the controller and displays the outcome.
     * </p>
     *
     * @param internship the {@link Internship} to apply for
     */
    private void applyInternship(Internship internship) {
        System.out.println("Do you want to apply for this internship? (Y/N)");
        String response = sc.nextLine().toUpperCase();

        if (response.equals("Y")) {
            boolean success = studentController.applyForInternship(internship);
            if (success) {
                System.out.println("Application successful! You can view the status under 'My Applications'.");
            } else {
                System.out.println(
                        "Application failed. Possible reasons: Max applications reached (3), already applied, or not your major.");
            }
        }
    }

    /**
     * Prompts the student to accept an internship offer.
     * <p>
     * Delegates the acceptance to the controller and displays whether the operation
     * was successful or if any preconditions were not met (e.g., application not approved).
     * </p>
     *
     * @param selectedApp the {@link InternshipApplication} offer to accept
     */
    private void acceptInternship(InternshipApplication selectedApp) {
        boolean success = studentController.acceptInternshipOffer(selectedApp);
        if (success) {
            System.out.println("You have successfully accepted the internship offer for "
                    + selectedApp.getInternship().getTitle() + ".");
        } else {
            System.out.println(
                    "Failed to accept the internship offer. Ensure the application is approved by the company.");
        }
    }

    /**
     * Generates a filtered and sorted report of internships matching student criteria.
     * <p>
     * Prompts the student to specify filter criteria including title, company ID, and internship level
     * (if applicable based on year of study), as well as minimum available slots. Automatically filters
     * by the student's major and to only approved internships. Delegates report generation and printing
     * to the controller.
     * </p>
     */
    private void generateReport() {
        ReportCriteria criteria = new ReportCriteria();

        System.out.print("Filter by Title (or leave blank): ");
        String title = sc.nextLine();
        if (!title.isBlank())
            criteria.setTitle(title);

        // System.out.print("Filter by Major (or leave blank): ");
        // String major = sc.nextLine();
        criteria.setMajor(studentController.getCurrentStudent().getMajor());

        System.out.print("Filter by Company ID (or leave blank): ");
        String companyId = sc.nextLine();
        if (!companyId.isBlank())
            criteria.setCompanyRepId(companyId);

        criteria.setStatus(InternshipStatus.APPROVED); // Students only see approved internships

        if (studentController.getCurrentStudent().getYearOfStudy() < 3) {
            criteria.setLevel(src.enums.InternshipLevel.BASIC); // Year 1-2 can only see BASIC level
        } else {
            System.out.print("Filter by Internship Level (BASIC/INTERMEDIATE/ADVANCED or leave blank): ");
            String level = sc.nextLine();
            if (!level.isBlank()) {
                try {
                    criteria.setLevel(src.enums.InternshipLevel.valueOf(level.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Level. Ignoring.");
                }
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

        criteria.setVisibility(true);

        // Delegate report generation and printing to controller
        List<Internship> report = studentController.generateReport(criteria);
        studentController.printReport(report);
    }

    /**
     * Displays all internship applications submitted by the current student.
     * <p>
     * Retrieves the student's applications from the controller and displays each one
     * using {@link #viewApplicationDetails(InternshipApplication)}. Shows a message if
     * no applications exist.
     * </p>
     */
    @Override
    public void viewApplications() {
        ArrayList<InternshipApplication> applications = studentController.getMyApplications();
        if (applications == null || applications.isEmpty()) {
            System.out.println("No internship applications found.");
            return;
        }

        System.out.println("\n--- My Internship Applications ---");
        for (int i = 1; i <= applications.size(); i++) {
            System.out.print(i + ". ");
            viewApplicationDetails(applications.get(i - 1));

        }
    }

    /**
     * Displays detailed information for a single internship application.
     *
     * @param application the {@link InternshipApplication} to display
     */
    @Override
    public void viewApplicationDetails(InternshipApplication application) {
        System.out.println(application);
    }

    /**
     * Displays detailed information for a single internship.
     *
     * @param internship the {@link Internship} to display
     */
    @Override
    public void viewInternshipDetails(Internship internship) {
        System.out.println(internship);
    }

    /**
     * Displays all available internship opportunities for the current student.
     * <p>
     * Retrieves the student's available internship opportunities from the controller and
     * displays each one using {@link #viewInternshipDetails(Internship)}. Shows a message
     * if no internships are available.
     * </p>
     */
    @Override
    public void viewInternships() {
        ArrayList<Internship> internships = studentController.getInternshipsOpportunities();
        if (internships == null || internships.isEmpty()) {
            System.out.println("No internships found.");
            return;
        }
        System.out.println("\n--- Available Internship Opportunities ---");
        for (int i = 1; i <= internships.size(); i++) {
            System.out.print(i + ". ");
            viewInternshipDetails(internships.get(i - 1));
        }

    }

}
