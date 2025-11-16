
package src.controller;

import src.entity.*;
import src.enums.InternshipStatus;

import java.time.LocalDate;
import java.util.ArrayList;

import src.DataStore;

public class CompanyRepresentativeController implements AuthController{
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

    @Override
    public boolean login(String userName, String pw) {
        // check the userName and pw against dataStore
        for (CompanyRepresentative c : dataStore.getCompanyRepresentativeList()) {
            if (c.getUserId().equals(userName) && c.getPassword().equals(pw)) {
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

    public boolean createInternship(int internshipId, String title, String description, String level, String major,
            LocalDate openDate, LocalDate closeDate, int numberOfSlotsLeft) {
        if (getCurrentCompayRepresentative() == null) {
            return false;
        }
            
        Internship newInternship = new Internship(0, title, description, level, major,
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

}