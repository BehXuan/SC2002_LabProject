package src.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.entity.*;
import src.enums.CompanyApprovalStatus;
import src.enums.InternshipStatus;
import src.interfaces.AuthController;
import src.interfaces.IReportGenerator;
import src.report.ReportCriteria;
import src.report.ReportGenerator;
import src.enums.InternshipLevel;
import src.DataStore;

public class CompanyRepresentativeController implements AuthController, IReportGenerator {
    private CompanyRepresentative currentRep;
    private DataStore dataStore;

    // init datastore
    public CompanyRepresentativeController() {this.dataStore = DataStore.getInstance();}
    
    // Getter and Setter
    public void setCurrentCompanyRepresentative(CompanyRepresentative c) {this.currentRep = c;}
    public CompanyRepresentative getCurrentCompayRepresentative() {return currentRep;}

    public boolean createCompanyRepresentative(String userId, String password, String name, String email,
            String companyName, String department, String position) {
        CompanyRepresentative newRep = new CompanyRepresentative(userId, password, name, email, companyName, department,
                position);

        CompanyRepresentative existingRep = dataStore.findCompanyRep(userId);
        if (existingRep != null) {
            return false; // Username already exists
        }
        dataStore.CompanyRepresentativeAdd(newRep);
        return true;
    }

    @Override
    public boolean login(String userName, String pw) {
        // check the userName and pw against dataStore
        CompanyRepresentative rep = dataStore.findCompanyRep(userName);
        if (rep == null) {
            return false;
        }

        if (rep.getApproval() != CompanyApprovalStatus.APPROVED) {
            return false;
        }

        if (rep.getPassword().equals(pw)) {
            setCurrentCompanyRepresentative(rep);
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        setCurrentCompanyRepresentative(null);
    }

    @Override
    public boolean updatePassword(String oldPW, String newPW) {
        if (getCurrentCompayRepresentative().getPassword().equals(oldPW)) {
            getCurrentCompayRepresentative().setPassword(newPW);
            return true;
        }
        return false;
    }

    public ArrayList<InternshipApplication> getApplications() {
        ArrayList<InternshipApplication> applications = new ArrayList<InternshipApplication>();
        for (InternshipApplication app : dataStore.getInternshipApplicationsList()) {
            if (app.getCompanyRep() == getCurrentCompayRepresentative()) {
                applications.add(app);
            }
        }
        return applications;
    }

    public ArrayList<Internship> getInternships() {
        ArrayList<Internship> internships = new ArrayList<Internship>();
        for (Internship internship : dataStore.getInternshipList()) {
            if (internship.getCompanyRep() == getCurrentCompayRepresentative() && internship.getInternshipId() != null) {
                internships.add(internship);
            }
        }
        return internships;
    }

    public boolean createInternship(String title, String description, InternshipLevel internshipLevel,
            String major,
            LocalDate openDate, LocalDate closeDate, int numberOfSlotsLeft) {
        if (getCurrentCompayRepresentative().getInternshipCount() == 5) {
            return false; // limit to 5 internships
        }
        String newId = getCurrentCompayRepresentative().getUserId() + "_" + getCurrentCompayRepresentative().getInternships().size();
        Internship newInternship = new Internship(newId, title, description, internshipLevel, major,
                openDate, closeDate, numberOfSlotsLeft, getCurrentCompayRepresentative());
        dataStore.addInternship(newInternship);
        getCurrentCompayRepresentative().addInternship(newInternship);
        return true;
    }

    public boolean approveInternshipApplication(InternshipApplication app) {
        app.setCompanyAccept(InternshipStatus.APPROVED);
        return true;
    }

    public boolean rejectInternshipApplication(InternshipApplication app) {
        // app.setCompanyAccept(InternshipStatus.REJECTED);
        dataStore.getInternshipApplicationsList().remove(app);
        app.getStudent().removeInternship(app);
        return true;
    }

    public boolean editInternship(Internship internship, String title, String description,
            InternshipLevel internshipLevel, String major, LocalDate openDate, LocalDate closeDate,
            int numberOfSlotsLeft) {
        if (internship.getStatus() == InternshipStatus.APPROVED) {
            return false; // cannot edit approved internships
        }
        internship.setTitle(title);
        internship.setDescription(description);
        internship.setLevel(internshipLevel);
        internship.setMajor(major);
        internship.setOpenDate(openDate);
        internship.setCloseDate(closeDate);
        internship.setNumberOfSlotsLeft(numberOfSlotsLeft);
        return true;
    }

    public boolean deleteInternship(Internship internship) {
        if (internship.getStatus() == InternshipStatus.APPROVED) {
            return false; // cannot delete approved internships
        }
        internship.setInternshipId(null);
        getCurrentCompayRepresentative().removeInternship(internship);
        dataStore.getInternshipList().remove(internship);
        return true;
    }

    public boolean toggleVisibility(Internship internship) {
        internship.setVisibility(!internship.getVisibility());
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
                    i.getMajor());
        }
    }
}