package src.view;

import src.controller.CareerCenterStaffController;
//import src.controller.AuthController;
import src.entity.*;
//import src.enums.*;
import src.enums.InternshipStatus;
import src.enums.LoginResult;
import src.report.ReportCriteria;
import src.interfaces.*;


import java.util.List;
import java.util.Scanner;

/**
 * View layer for Career Center Staff user interface.
 * <p>
 * Provides a menu-driven interface for career center administrators to manage company representatives,
 * internship approvals, application withdrawals, and generate reports. Extends {@link UserView} to
 * inherit authentication functionality and implements {@link viewInternship} for internship viewing
 * capabilities.
 * </p>
 */
public class CareerCenterStaffView extends UserView implements viewInternship{
    private CareerCenterStaffController staffController;
    private Scanner sc = new Scanner(System.in);

    /**
     * Constructs a {@code CareerCenterStaffView} with the given controller.
     * <p>
     * Initializes the view with a reference to the {@link CareerCenterStaffController} for
     * delegating business logic operations.
     * </p>
     *
     * @param staffController the {@code CareerCenterStaffController} to handle staff operations
     */
    public CareerCenterStaffView(CareerCenterStaffController staffController) {
        super(staffController); // AuthController
        this.staffController = staffController;
    }

    /**
     * Starts the career center staff view by initiating the login menu.
     */
    @Override
    public void start() {
        loginMenu();
    }

    /**
     * Displays and handles the login menu for career center staff.
     * <p>
     * Prompts the user to enter username and password, validates credentials using the controller,
     * and transitions to the main staff menu upon successful authentication. Handles login result
     * enums to provide appropriate feedback messages.
     * </p>
     */
    private void loginMenu() {
        
        while(true){
            System.out.println("----- Career Center Staff Login -----");
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
            LoginResult result = staffController.login(username, password);
            switch (result) {
            case SUCCESS:
                System.out.println("Login successful! Welcome " + staffController.getCurrentStaff().getName());
                runMenuLoop();  
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
     * Runs the main menu loop for authenticated career center staff.
     * <p>
     * Displays a menu with 12 options including password change, company authorization,
     * internship approval, withdrawal approval, and report generation. Delegates to specific
     * handler methods based on user selection.
     * </p>
     */
    private void runMenuLoop() {
        int choice;
        while (true) {
            System.out.println("\n===== Career Center Staff Menu (Logged in as: " +
                    staffController.getCurrentStaff().getName() + ") =====");
            System.out.println("1. Change Password");
            System.out.println("2. View Pending Company Representatives");
            System.out.println("3. Authorize Company");
            System.out.println("4. Reject Company");
            System.out.println("5. View Pending Internships");
            System.out.println("6. Approve Internship");
            System.out.println("7. Reject Internship");
            System.out.println("8. View Students with Pending Withdrawals");
            System.out.println("9. Approve Withdrawal");
            System.out.println("10. Reject Withdrawal");
            System.out.println("11. Generate Report");
            System.out.println("12. Logout / Return to Main Menu");

            System.out.print("Enter choice: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } else {
                sc.nextLine();
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1 -> {changePassword();logout();return;} // from UserView
                case 2 -> viewPendingCompanyReps();
                case 3 -> authorizeCompany();
                case 4 -> rejectCompany();
                case 5 -> viewInternships();
                case 6 -> approveInternship();
                case 7 -> rejectInternship();
                case 8 -> viewPendingWithdrawals();
                case 9 -> approveWithdrawal();
                case 10 -> rejectWithdrawal();
                case 11 -> generateReport();
                case 12 -> {
                    logout(); // from UserView
                    System.out.println("Logged out. Returning to main menu...");
                    return; // exit menu loop
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays all pending company representatives awaiting authorization.
     * <p>
     * Retrieves the list of pending companies from the controller and displays their user ID,
     * name, and company name. Shows a message if no pending companies exist.
     * </p>
     */
    private void viewPendingCompanyReps() {
        List<CompanyRepresentative> pending = staffController.getPendingCompanies();
        System.out.println("\n===== Pending Company Representatives =====");
        if (pending.isEmpty()) System.out.println("No pending companies.");
        else
            for (CompanyRepresentative c : pending) {
                System.out.println("ID: " + c.getUserId() + ", Name: " + c.getName() + ", Company: " + c.getCompanyName());
            }
    }

    /**
     * Prompts the user to authorize a company representative by ID.
     * <p>
     * Requests the company representative ID and delegates authorization to the controller.
     * Displays success or failure message based on the operation result.
     * </p>
     */
    private void authorizeCompany() {
        System.out.print("Enter Company Rep ID to authorize: ");
        String id = sc.nextLine();
        if (staffController.authoriseCompany(id)) System.out.println("Company authorized.");
        else System.out.println("Company not found.");
    }

    /**
     * Prompts the user to reject a company representative by ID.
     * <p>
     * Requests the company representative ID and delegates rejection to the controller.
     * Displays success or failure message based on the operation result.
     * </p>
     */
    private void rejectCompany() {
        System.out.print("Enter Company Rep ID to reject: ");
        String id = sc.nextLine();
        if (staffController.rejectCompany(id)) System.out.println("Company rejected.");
        else System.out.println("Company not found.");
    }

    /**
     * Displays all pending internships awaiting approval.
     * <p>
     * Retrieves the list of pending internships from the controller and displays their
     * details using {@link #viewInternshipDetails(Internship)}. Shows a message if no
     * pending internships exist.
     * </p>
     */
    @Override
    public void viewInternships() { // view pending internship
        List<Internship> pending = staffController.getPendingInternships();
        System.out.println("\n===== Pending Internships =====");
        if (pending.isEmpty()) System.out.println("No pending internships.");
        else
            for (Internship i : pending) {
                viewInternshipDetails(i);          
            }
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
     * Prompts the user to approve an internship by ID.
     * <p>
     * Requests the internship ID and delegates approval to the controller.
     * Displays success or failure message based on the operation result.
     * </p>
     */
    private void approveInternship() {
        System.out.print("Enter Internship ID to approve: ");
        String id = sc.nextLine();
        sc.nextLine();
        if (staffController.approveInternship(id)) System.out.println("Internship approved.");
        else System.out.println("Internship not found.");
    }

    /**
     * Prompts the user to reject an internship by ID.
     * <p>
     * Requests the internship ID and delegates rejection to the controller.
     * Displays success or failure message based on the operation result.
     * </p>
     */
    private void rejectInternship() {
        System.out.print("Enter Internship ID to reject: ");
        String id = sc.nextLine();
        sc.nextLine();
        if (staffController.rejectInternship(id)) System.out.println("Internship rejected.");
        else System.out.println("Internship not found.");
    }

    /**
     * Displays all pending application withdrawals for review.
     * <p>
     * Retrieves the list of pending withdrawals from the controller and displays each
     * application with the associated student and internship information. Shows a message
     * if no pending withdrawals exist.
     * </p>
     *
     * @return a list of {@link InternshipApplication} objects with pending withdrawals
     */
    private List<InternshipApplication> viewPendingWithdrawals() {
    List<InternshipApplication> pending = staffController.getPendingWithdrawals();

    System.out.println("\n===== Pending Withdrawals =====");
    if (pending.isEmpty()) {
        System.out.println("No pending withdrawals.");
        return pending;
    }
    for (int i = 0; i < pending.size(); i++) {
        InternshipApplication app = pending.get(i);
        Student s = app.getStudent();
        System.out.println((i + 1) + ". Student ID: " + s.getUserId() + ", Name: " + s.getName() +
                           ", Internship: " + app.getInternship().getTitle());
    }

    return pending;
}

    /**
     * Prompts the user to approve a pending withdrawal request.
     * <p>
     * Displays pending withdrawals and requests the user to select one by number.
     * Delegates approval to the controller and displays the result.
     * </p>
     */
    private void approveWithdrawal() {
    List<InternshipApplication> pending = viewPendingWithdrawals();
    if (pending.isEmpty()) return;

    System.out.print("Enter the number of the withdrawal to approve, or 0 to cancel: ");
    int choice = -1;
    while (true) {
        if (sc.hasNextInt()) {
            choice = sc.nextInt();
            sc.nextLine();
            if (choice >= 0 && choice <= pending.size()) break;
        } else {
            sc.nextLine();
        }
        System.out.print("Invalid input. Try again: ");
    }

    if (choice == 0) return;

    InternshipApplication selectedApp = pending.get(choice - 1);
    if (staffController.approveWithdrawal(selectedApp)) {
        System.out.println("Withdrawal approved.");
    } else {
        System.out.println("Failed to approve withdrawal.");
    }
}

    /**
     * Prompts the user to reject a pending withdrawal request.
     * <p>
     * Displays pending withdrawals and requests the user to select one by number.
     * Delegates rejection to the controller and displays the result.
     * </p>
     */
    private void rejectWithdrawal() {
    List<InternshipApplication> pending = viewPendingWithdrawals();
    if (pending.isEmpty()) return;

    System.out.print("Enter the number of the withdrawal to reject, or 0 to cancel: ");
    int choice = -1;
    while (true) {
        if (sc.hasNextInt()) {
            choice = sc.nextInt();
            sc.nextLine();
            if (choice >= 0 && choice <= pending.size()) break;
        } else {
            sc.nextLine();
        }
        System.out.print("Invalid input. Try again: ");
    }

    if (choice == 0) return;

    InternshipApplication selectedApp = pending.get(choice - 1);
    if (staffController.rejectWithdrawal(selectedApp)) {
        System.out.println("Withdrawal rejected.");
    } else {
        System.out.println("Failed to reject withdrawal.");
    }
}

    /**
     * Generates a filtered and sorted report of internships.
     * <p>
     * Prompts the user to specify filter criteria (title, major, company ID, status, level,
     * minimum slots) and sort type. Creates a {@link ReportCriteria} object with the specified
     * filters and delegates report generation and printing to the controller.
     * </p>
     */
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

        System.out.print("Filter by Internship Status (PENDING/APPROVED/REJECTED or leave blank): ");
        String status = sc.nextLine();
        if (!status.isBlank()) {
            try {
                criteria.setStatus(InternshipStatus.valueOf(status.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Ignoring.");
            }
        }

        System.out.print("Filter by Internship Level (BASIC/INTERMEDIATE/ADVANCED or leave blank): ");
        String level = sc.nextLine();
        if (!level.isBlank()) {
            try {
                criteria.setLevel(src.enums.InternshipLevel.valueOf(level.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid level. Ignoring.");
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

        // Delegate to controller to generate & print
        List<Internship> report = staffController.generateReport(criteria);
        staffController.printReport(report);
    }

}
