package src.view;

import src.controller.CareerCenterStaffController;
//import src.controller.AuthController;
import src.entity.*;
//import src.enums.*;
import src.enums.InternshipStatus;
import src.report.ReportCriteria;
import src.interfaces.*;


import java.util.List;
import java.util.Scanner;

public class CareerCenterStaffView extends UserView implements viewInternship{
    private CareerCenterStaffController staffController;
    private Scanner sc = new Scanner(System.in);

    public CareerCenterStaffView(CareerCenterStaffController staffController) {
        super(staffController); // AuthController
        this.staffController = staffController;
    }

    @Override
    public void start() {
        loginMenu();
    }

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

            if (staffController.login(username, password)) {
                System.out.println("Login successful! Welcome " + staffController.getCurrentStaff().getName());
                runMenuLoop();
                return; // exit login after menu
            } else {
                System.out.println("Invalid username or password. Try again or enter 0 to go back.");
            }
        }
    }

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
                case 1 -> changePassword(); // from UserView
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

    // ---- Companies ----
    private void viewPendingCompanyReps() {
        List<CompanyRepresentative> pending = staffController.getPendingCompanies();
        System.out.println("\n===== Pending Company Representatives =====");
        if (pending.isEmpty()) System.out.println("No pending companies.");
        else
            for (CompanyRepresentative c : pending) {
                System.out.println("ID: " + c.getUserId() + ", Name: " + c.getName() + ", Company: " + c.getCompanyName());
            }
    }

    private void authorizeCompany() {
        System.out.print("Enter Company Rep ID to authorize: ");
        String id = sc.nextLine();
        if (staffController.authorizeCompany(id)) System.out.println("Company authorized.");
        else System.out.println("Company not found.");
    }

    private void rejectCompany() {
        System.out.print("Enter Company Rep ID to reject: ");
        String id = sc.nextLine();
        if (staffController.rejectCompany(id)) System.out.println("Company rejected.");
        else System.out.println("Company not found.");
    }

    // ---- Internships ----
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

    @Override
    public void viewInternshipDetails(Internship internship) {
        System.out.println(internship);
    }


    private void approveInternship() {
        System.out.print("Enter Internship ID to approve: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (staffController.approveInternship(id)) System.out.println("Internship approved.");
        else System.out.println("Internship not found.");
    }

    private void rejectInternship() {
        System.out.print("Enter Internship ID to reject: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (staffController.rejectInternship(id)) System.out.println("Internship rejected.");
        else System.out.println("Internship not found.");
    }

    // ---- Student Withdrawals ----
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
