
package src.controller;

import src.entity.*;
import src.enums.CompanyApprovalStatus;
import src.enums.InternshipStatus;
import src.report.ReportCriteria;
import src.report.ReportGenerator;
import src.enums.InternshipLevel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.DataStore;

public class CompanyRepresentativeController implements AuthController, IReportGenerator{
    private CompanyRepresentative currentRep;
    private DataStore dataStore;

    public CompanyRepresentativeController() {
        this.dataStore = DataStore.getInstance();
    }

    public void setCurrentCompanyRepresentative(CompanyRepresentative c) {
        this.currentRep = c;
    }

    public CompanyRepresentative getCurrentCompayRepresentative() {
        return currentRep;
    }

    public boolean createCompanyRepresentative(String userId, String password, String name, String email, String companyName, String department, String position) {
        CompanyRepresentative newRep = new CompanyRepresentative(userId, password, name, email,  companyName, department, position);
        dataStore.CompanyRepresentativeAdd(newRep);
        return true;
    }

    @Override
    public boolean login(String userName, String pw) {
        // check the userName and pw against dataStore
        for (CompanyRepresentative c : dataStore.getCompanyRepresentativeList()) {
            if (c.getUserId().equals(userName) && c.getPassword().equals(pw) && c.getApproval().equals(CompanyApprovalStatus.APPROVED)) {
                setCurrentCompanyRepresentative(c);
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout() {
        setCurrentCompanyRepresentative(null);
    }
    
    @Override
    public boolean updatePassword(String oldPW, String newPW) {
        if (getCurrentCompayRepresentative() == null) {
            return false;
        }

        if (getCurrentCompayRepresentative().getPassword().equals(oldPW)) {
            getCurrentCompayRepresentative().setPassword(newPW);
            return true;
        }
        return false;
    }

    public ArrayList<InternshipApplication> getApplications() {
        if (getCurrentCompayRepresentative() == null) {
            return null;
        }

        ArrayList<InternshipApplication> applications = new ArrayList<InternshipApplication>();
        for (InternshipApplication app : dataStore.getInternshipApplicationsList()) {
            if (app.getCompanyRep() == getCurrentCompayRepresentative()) {
                applications.add(app);
            }
        }
        return applications;
    }

    public ArrayList<Internship> getInternships() {
        if (getCurrentCompayRepresentative() == null) {
            return null;
        }

        ArrayList<Internship> internships = new ArrayList<Internship>();
        for (Internship internship : dataStore.getInternshipList()) {
            if (internship.getCompanyRep() == getCurrentCompayRepresentative()) {
                internships.add(internship);
            }
        }
        return internships;
    }

    public boolean createInternship(int internshipId, String title, String description, InternshipLevel internshipLevel, String major,
            LocalDate openDate, LocalDate closeDate, int numberOfSlotsLeft) {
        if (getCurrentCompayRepresentative() == null) {
            return false;
        }

        int newId = dataStore.getNextInternshipId();
            
        Internship newInternship = new Internship(newId, title, description, internshipLevel, major,
            openDate, closeDate, numberOfSlotsLeft, getCurrentCompayRepresentative());
        newInternship.setCompanyRep(getCurrentCompayRepresentative());
        dataStore.addInternship(newInternship);
        return true;
    }

    public boolean approveInternshipApplication(InternshipApplication app) {
        if (getCurrentCompayRepresentative() == null) {
            return false;
        }

        app.setCompanyAccept(InternshipStatus.APPROVED);
        return true;
    }

    public boolean rejectInternshipApplication(InternshipApplication app) {  // do i need to show students they got rejected or just delete applicaiton from list
        if (getCurrentCompayRepresentative() == null) {
            return false;
        }

        app.setCompanyAccept(InternshipStatus.REJECTED);
        return true;
    }

    private ReportGenerator reportGen = new ReportGenerator();

    @Override
    public List<Internship> generateReport(ReportCriteria criteria) {
        return reportGen.generateReport(criteria);
    }

    public void printReport(List<Internship> internships) {
        System.out.println("===== Company Representative Internship Report =====");
        if (internships.isEmpty()) {
            System.out.println("No internships found for the given criteria.");
            return;
        }
        for (Internship i : internships) {
            System.out.printf(
                "ID: %d | Title: %s | Level: %s | Slots left: %d | Status: %s | Major: %s\n",
                i.getInternshipId(),
                i.getTitle(),
                i.getLevel(),
                i.getNumberOfSlotsLeft(),
                i.getStatus(),
                i.getMajor()
            );
        }
    }

}