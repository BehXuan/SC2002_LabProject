package src.controller;

import src.entity.*;

import java.util.ArrayList;
import java.util.List;
import src.enums.*;
import src.DataStore;

public class CareerCenterStaffController implements AuthController {
    private CareerCenterStaff currentStaff;
    private DataStore dataStore;


    public CareerCenterStaffController() {
        this.dataStore = DataStore.getInstance();
    }

    public void setCurrentStaff(CareerCenterStaff c){
        this.currentStaff = c;
    }

    public CareerCenterStaff getCurrentStaff(){
        return this.currentStaff;
    }


    @Override
    public boolean login(String userName, String pw) {
        // check the userName and pw against dataStore
        for (CareerCenterStaff c : dataStore.getCareerCenterStaffList()) {
            if (c.getName().equals(userName) && c.getPassword().equals(pw)) {
                setCurrentStaff(c);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePassword(String oldPW, String newPW) {
        if (currentStaff.getPassword().equals(oldPW)) {
            currentStaff.setPassword(newPW);
            return true;
        }
        return false;
    }

    @Override
    public void logout(){
        setCurrentStaff(null);
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
  
    public List<InternshipApplication> getPendingWithdrawals() {
        List<InternshipApplication> pending = new ArrayList<>();

        for (Student s : dataStore.getStudentList()) {
            // check all applied internships for pending withdrawal
            for (InternshipApplication app : s.getInternshipApplied()) {
                if (app.getInternshipWithdrawalStatus() == InternshipWithdrawalStatus.PENDING) {
                    pending.add(app);
                    
            }
        }
    }
        return pending;
    
}


    public boolean approveWithdrawal(InternshipApplication app) {
    if (app.getInternshipWithdrawalStatus() == InternshipWithdrawalStatus.PENDING) {
        app.setInternshipWithdrawalStatus(InternshipWithdrawalStatus.APPROVED);
        return true;
    }
    return false;
    }

    public boolean rejectWithdrawal(InternshipApplication app) {
    if (app.getInternshipWithdrawalStatus() == InternshipWithdrawalStatus.PENDING) {
        app.setInternshipWithdrawalStatus(InternshipWithdrawalStatus.REJECTED);
        return true;
    }
    return false;
    }

    //LIST GENERATION





    /**public Report generateReports(String status, String level, String major, String openDate,
                                  String closeDate, String companyName, String vacancy) {
        // TODO: Implement report generation logic
        return new Report(); 
                
                
    }**/
            
            
}