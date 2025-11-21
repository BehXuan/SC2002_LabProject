package src.controller;

import java.util.ArrayList;
import java.util.List;

import src.enums.*;
import src.interfaces.*;
import src.report.*;
import src.DataStore;
import src.entity.*;

public class CareerCenterStaffController implements AuthController, IReportGenerator {
    private CareerCenterStaff currentStaff;
    private DataStore dataStore;


    public CareerCenterStaffController() {this.dataStore = DataStore.getInstance();}

    public void setCurrentStaff(CareerCenterStaff c){this.currentStaff = c;}

    public CareerCenterStaff getCurrentStaff(){return this.currentStaff;}


    @Override
    public LoginResult login(String userName, String pw) {
        CareerCenterStaff c = dataStore.findCareerCenterStaff(userName);
        if (c == null) {
        return LoginResult.USER_NOT_FOUND;
        }
        if (!c.getPassword().equals(pw)) {
        return LoginResult.WRONG_PASSWORD;
        }

        setCurrentStaff(c);
        return LoginResult.SUCCESS;
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
    public boolean authoriseCompany(String companyRepId) {
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
            // company.setApproval(CompanyApprovalStatus.REJECTED);  // alternatively, we can delete the company from the list
            dataStore.getCompanyRepresentativeList().remove(company); 
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
    public boolean approveInternship(String internshipId) {
        Internship internship = dataStore.findInternship(internshipId);
        if (internship != null) {
            internship.setStatus(InternshipStatus.APPROVED);
            return true;
        }
        return false;
    }

    public boolean rejectInternship(String internshipId) {
        Internship internship = dataStore.findInternship(internshipId);
        if (internship != null) {
            // internship.setStatus(InternshipStatus.REJECTED); // alternatively, we can delete the internship from the list
            dataStore.getInternshipList().remove(internship);
            internship.getCompanyRep().removeInternship(internship);
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
        dataStore.getInternshipApplicationsList().remove(app);
        app.getStudent().removeInternship(app);
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

    private ReportGenerator reportGen = new ReportGenerator();

    @Override
    public List<Internship> generateReport(ReportCriteria criteria) {
        return reportGen.generateReport(criteria);
    }

    public void printReport(List<Internship> internships) {
        System.out.println("===== Career Center Staff Internship Report =====");
        if (internships.isEmpty()) {
            System.out.println("No internships found for the given criteria.");
            return;
        }
        for (Internship i : internships) {
            System.out.printf(
                "ID: %d | Title: %s | Major: %s | Level: %s | Slots left: %d | Company: %s | Status: %s\n",
                i.getInternshipId(),
                i.getTitle(),
                i.getMajor(),
                i.getLevel(),
                i.getNumberOfSlotsLeft(),
                i.getCompanyRep().getCompanyName(),
                i.getStatus()
            );
        }
    }     
            
}