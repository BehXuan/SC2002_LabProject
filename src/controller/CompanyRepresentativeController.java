package src.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.entity.*;
import src.enums.CompanyApprovalStatus;
import src.enums.InternshipStatus;
import src.enums.LoginResult;
import src.interfaces.AuthController;
import src.interfaces.IReportGenerator;
import src.report.ReportCriteria;
import src.report.ReportGenerator;
import src.enums.InternshipLevel;
import src.DataStore;

/**
 * Controller used by company representatives to manage internships and
 * applications.
 *
 * <p>
 * Responsibilities include account creation for company reps, authentication,
 * creating/editing/deleting internships, approving/rejecting applications, and
 * generating reports.
 */
public class CompanyRepresentativeController implements AuthController, IReportGenerator {
    private CompanyRepresentative currentRep;
    private DataStore dataStore;

    // init datastore
    /**
     * Constructs the controller and acquires the shared `DataStore` instance.
     */
    public CompanyRepresentativeController() {
        this.dataStore = DataStore.getInstance();
    }

    // Getter and Setter
    /**
     * Sets the currently authenticated company representative.
     *
     * @param c the `CompanyRepresentative` to set as current
     */
    public void setCurrentCompanyRepresentative(CompanyRepresentative c) {
        this.currentRep = c;
    }

    /**
     * Returns the currently authenticated company representative.
     *
     * @return the current `CompanyRepresentative`, or null if none
     */
    public CompanyRepresentative getCurrentCompayRepresentative() {
        return currentRep;
    }

    /**
     * Creates a new company representative account and registers it in the data
     * store.
     *
     * @param userId      desired user id
     * @param password    account password
     * @param name        full name
     * @param email       contact email
     * @param companyName company name
     * @param department  company department
     * @param position    position/title
     * @return true if account created, false if user id already exists
     */
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

    /**
     * Attempts to authenticate a company representative.
     *
     * @param userName representative user id
     * @param pw       plaintext password to verify
     * @return `LoginResult` indicating success or failure reason
     */
    @Override
    public LoginResult login(String userName, String pw) {
        // check the userName and pw against dataStore
        CompanyRepresentative rep = dataStore.findCompanyRep(userName);
        if (rep == null) {
            return LoginResult.USER_NOT_FOUND;
        }

        if (rep.getApproval() != CompanyApprovalStatus.APPROVED) {
            return LoginResult.USER_NOT_APPROVED;
        }

        if (!rep.getPassword().equals(pw)) {
            return LoginResult.WRONG_PASSWORD;
        }

        setCurrentCompanyRepresentative(rep);
        return LoginResult.SUCCESS;
    }

    /**
     * Logs out the currently authenticated company representative.
     */
    @Override
    public void logout() {
        setCurrentCompanyRepresentative(null);
    }

    /**
     * Updates the password for the currently authenticated company representative.
     *
     * @param oldPW existing password for verification
     * @param newPW new password to set
     * @return true if the password was updated, false otherwise
     */
    @Override
    public boolean updatePassword(String oldPW, String newPW) {
        if (getCurrentCompayRepresentative().getPassword().equals(oldPW)) {
            getCurrentCompayRepresentative().setPassword(newPW);
            return true;
        }
        return false;
    }

    /**
     * Returns all internship applications addressed to the currently
     * authenticated company representative.
     *
     * @return list of `InternshipApplication` objects for the current
     *         representative
     */
    public ArrayList<InternshipApplication> getApplications() {
        ArrayList<InternshipApplication> applications = new ArrayList<InternshipApplication>();
        for (InternshipApplication app : dataStore.getInternshipApplicationsList()) {
            if (app.getCompanyRep() == getCurrentCompayRepresentative()) {
                applications.add(app);
            }
        }
        return applications;
    }

    /**
     * Returns internships created by the currently authenticated company
     * representative.
     *
     * @return list of `Internship` objects owned by the current representative
     */
    public ArrayList<Internship> getInternships() {
        ArrayList<Internship> internships = new ArrayList<Internship>();
        for (Internship internship : dataStore.getInternshipList()) {
            if (internship.getCompanyRep() == getCurrentCompayRepresentative()
                    && internship.getInternshipId() != null) {
                internships.add(internship);
            }
        }
        return internships;
    }

    /**
     * Creates a new internship for the current company representative.
     *
     * @param title             internship title
     * @param description       internship description
     * @param internshipLevel   level of internship
     * @param major             targeted major
     * @param openDate          opening date for applications
     * @param closeDate         closing date for applications
     * @param numberOfSlotsLeft number of available slots
     * @return true if created, false when representative has reached allowed limit
     */
    public boolean createInternship(String title, String description, InternshipLevel internshipLevel,
            String major,
            LocalDate openDate, LocalDate closeDate, int numberOfSlotsLeft) {
        if (getCurrentCompayRepresentative().getInternshipCount() == 5) {
            return false; // limit to 5 internships
        }
        String newId = getCurrentCompayRepresentative().getUserId() + "_"
                + getCurrentCompayRepresentative().getInternships().size();
        Internship newInternship = new Internship(newId, title, description, internshipLevel, major,
                openDate, closeDate, numberOfSlotsLeft, getCurrentCompayRepresentative());
        dataStore.addInternship(newInternship);
        getCurrentCompayRepresentative().addInternship(newInternship);
        return true;
    }

    /**
     * Approves a student's internship application.
     *
     * @param app the `InternshipApplication` to approve
     * @return true when operation succeeds
     */
    public boolean approveInternshipApplication(InternshipApplication app) {
        app.setCompanyAccept(InternshipStatus.APPROVED);
        return true;
    }

    /**
     * Rejects a student's internship application and removes it from records.
     *
     * @param app the `InternshipApplication` to reject
     * @return true when operation succeeds
     */
    public boolean rejectInternshipApplication(InternshipApplication app) {
        // app.setCompanyAccept(InternshipStatus.REJECTED);
        dataStore.getInternshipApplicationsList().remove(app);
        app.getStudent().removeInternship(app);
        return true;
    }

    /**
     * Edits an existing internship's metadata. Approved internships cannot be
     * edited.
     *
     * @param internship        the `Internship` to edit
     * @param title             new title
     * @param description       new description
     * @param internshipLevel   new level
     * @param major             new major
     * @param openDate          new open date
     * @param closeDate         new close date
     * @param numberOfSlotsLeft new number of slots
     * @return true if the internship was edited, false if it was approved and thus
     *         locked
     */
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

    /**
     * Deletes an internship if it has not been approved. This removes it from
     * both the company representative and the data store.
     *
     * @param internship the `Internship` to delete
     * @return true if deletion succeeded, false if internship is approved
     */
    public boolean deleteInternship(Internship internship) {
        if (internship.getStatus() == InternshipStatus.APPROVED) {
            return false; // cannot delete approved internships
        }
        internship.setInternshipId(null);
        getCurrentCompayRepresentative().removeInternship(internship);
        dataStore.getInternshipList().remove(internship);
        return true;
    }

    /**
     * Toggles the visibility flag of the given internship.
     *
     * @param internship the `Internship` to toggle visibility for
     * @return true after toggling
     */
    public boolean toggleVisibility(Internship internship) {
        internship.setVisibility(!internship.getVisibility());
        return true;
    }

    private ReportGenerator reportGen = new ReportGenerator();

    /**
     * Generates a report of internships according to the provided criteria.
     *
     * @param criteria the `ReportCriteria` used to filter and sort internships
     * @return list of internships matching the criteria
     */
    @Override
    public List<Internship> generateReport(ReportCriteria criteria) {
        return reportGen.generateReport(criteria);
    }

    /**
     * Prints a simple textual report to standard output for the provided
     * internships.
     *
     * @param internships list of internships to print
     */
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