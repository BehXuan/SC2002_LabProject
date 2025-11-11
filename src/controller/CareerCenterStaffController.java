package src.controller;

import src.entity.*;

import java.util.ArrayList;
import java.util.List;
import src.enums.*;
import src.DataStore;

public class CareerCenterStaffController extends UserController {

    public CareerCenterStaffController() {
        super();
    }

    @Override
    public boolean login(String userId, String pw) {
        for (CareerCenterStaff staff : dataStore.getCareerCenterStaffList()) {
            if (staff.getUserId().equals(userId) && staff.getPassword().equals(pw)) {
                setCurrentUser(staff);
                return true;
            }
        }
        return false;
    }

    public boolean changePassword(CareerCenterStaff staff, String oldPW, String newPW) {
        if (staff.getPassword().equals(oldPW)) {
            staff.setPassword(newPW);
            return true;
        }
        return false;
    }

    //List of all pending companies
    public List<CompanyRepresentative> getPendingCompanies() {
    List<CompanyRepresentative> pending = new ArrayList<>();

    for (CompanyRepresentative rep : dataStore.getCompanyRepresentativeList()) {
        if (CompanyApprovalStatus.PENDING == (rep.getApproval())) {
            pending.add(rep);
        }
    }
    return pending;
    }

    //COMPANY REP AUTHORIZE/REJECT
    public boolean authorizeCompany(String companyRepId) {
        CompanyRepresentative company = dataStore.findCompanyRep(companyRepId);
        if (company != null) {
            company.setApproval(CompanyApprovalStatus.APPROVED);
            return true;
        }
        return false;
    }

    public boolean rejectCompany(String companyRepId) {
        CompanyRepresentative company = dataStore.findCompanyRep(companyRepId);
        if (company != null) {
            company.setApproval(CompanyApprovalStatus.REJECTED);
            return true;
        }
        return false;
    }

    //INTERNSHIP LIST
    public List<Internship> getPendingInternships() {
    List<Internship> pending = new ArrayList<>();
    for (Internship i : dataStore.getInternshipList()) {
        if (i.getStatus() == InternshipStatus.PENDING) {   // false = pending
            pending.add(i);
        }
    }
    return pending;
}
    //INTERNSHIP APPROVE/ REJECT
    public boolean approveInternship(int internshipId) {
        Internship internship = dataStore.findInternship(internshipId);
        if (internship != null) {
            internship.setStatus(InternshipStatus.APPROVED);
            return true;
        }
        return false;
    }

    public boolean rejectInternship(int internshipId) {
        Internship internship = dataStore.findInternship(internshipId);
        if (internship != null) {
            internship.setStatus(InternshipStatus.REJECTED);
            return true;
        }
        return false;
    }

    //STUDENT WITHDRAWALS LIST
    public List<Student> getPendingWithdrawals() {
    List<Student> pending = new ArrayList<>();

    for (Student s : dataStore.getStudentList()) {
        if ((s.getWithdrawalStatus) == InternshipWithdrawalStatus.PENDING) {   // false = pending
            pending.add(s);
        }
    }
    return pending;
    }

    //STUDENT WITHDRAWAL APPROVE/REJECT
    public boolean approveWithdrawal(String studentId) {
        Student student = dataStore.findStudent(studentId);
        if (student != null) {
            student.setWithdrawalApproved(true);
            return true;
        }
        return false;
    }

    public boolean rejectWithdrawal(String studentId) {
        Student student = dataStore.findStudent(studentId);
        if (student != null) {
            student.setWithdrawalApproved(false);
            return true;
        }
        return false;
    }

    //LIST GENERATION





    public Report generateReports(String status, String level, String major, String openDate,
                                  String closeDate, String companyName, String vacancy) {
        // TODO: Implement report generation logic
        return new Report(); 
                
                
    }
            
            
}