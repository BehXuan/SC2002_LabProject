package src.view;

import src.controller.CareerCenterStaffController;
import src.entity.*;
import src.enums.*;

import java.util.List;
import java.util.Scanner;

public class CareerCenterStaffView {
    private CareerCenterStaffController controller;
    private Scanner sc;

    public CareerCenterStaffView(CareerCenterStaffController controller) {
        this.controller = controller;
        this.sc = new Scanner(System.in);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n===== Career Center Staff Menu =====");
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
            System.out.println("12. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> changePassword();
                case 2 -> viewPendingCompanyReps();
                case 3 -> authorizeCompany();
                case 4 -> rejectCompany();
                case 5 -> viewPendingInternships();
                case 6 -> approveInternship();
                case 7 -> rejectInternship();
                case 8 -> viewPendingWithdrawals();
                case 9 -> approveWithdrawal();
                case 10 -> rejectWithdrawal();
                case 11 -> generateReport();
                case 12 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 12);
    }

    // ---- Password ----
    private void changePassword() {
        System.out.print("Enter old password: ");
        String oldPW = sc.nextLine();
        System.out.print("Enter new password: ");
        String newPW = sc.nextLine();

        if (controller.changePassword((CareerCenterStaff)controller.getCurrentUser(), oldPW, newPW)) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Old password incorrect.");
        }
    }

    // ---- Companies ----
    private void viewPendingCompanyReps() {
        List<CompanyRepresentative> pending = controller.getPendingCompanies();
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
        if (controller.authorizeCompany(id)) System.out.println("Company authorized.");
        else System.out.println("Company not found.");
    }

    private void rejectCompany() {
        System.out.print("Enter Company Rep ID to reject: ");
        String id = sc.nextLine();
        if (controller.rejectCompany(id)) System.out.println("Company rejected.");
        else System.out.println("Company not found.");
    }

    // ---- Internships ----
    private void viewPendingInternships() {
        List<Internship> pending = controller.getPendingInternships();
        System.out.println("\n===== Pending Internships =====");
        if (pending.isEmpty()) System.out.println("No pending internships.");
        else
            for (Internship i : pending) {
                System.out.println("ID: " + i.getInternshipId() + ", Title: " + i.getTitle() + ", Company: " + i.getCompanyName());
            }
    }

    private void approveInternship() {
        System.out.print("Enter Internship ID to approve: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (controller.approveInternship(id)) System.out.println("Internship approved.");
        else System.out.println("Internship not found.");
    }

    private void rejectInternship() {
        System.out.print("Enter Internship ID to reject: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (controller.rejectInternship(id)) System.out.println("Internship rejected.");
        else System.out.println("Internship not found.");
    }

    // ---- Student Withdrawals ----
    private void viewPendingWithdrawals() {
        List<Student> pending = controller.getPendingWithdrawals();
        System.out.println("\n===== Students with Pending Withdrawals =====");
        if (pending.isEmpty()) System.out.println("No pending withdrawals.");
        else
            for (Student s : pending) {
                System.out.println("ID: " + s.getUserId() + ", Name: " + s.getName());
            }
    }

    private void approveWithdrawal() {
        System.out.print("Enter Student ID to approve withdrawal: ");
        String id = sc.nextLine();
        if (controller.approveWithdrawal(id)) System.out.println("Withdrawal approved.");
        else System.out.println("Student not found or no pending request.");
    }

    private void rejectWithdrawal() {
        System.out.print("Enter Student ID to reject withdrawal: ");
        String id = sc.nextLine();
        if (controller.rejectWithdrawal(id)) System.out.println("Withdrawal rejected.");
        else System.out.println("Student not found or no pending request.");
    }

    // ---- Reports ----
    private void generateReport() {
        System.out.println("Generating report... (Functionality TBD)");
        controller.generateReports("", "", "", "", "", "", "");
        System.out.println("Report generated.");
    }
}
