package src.controller;

import java.util.ArrayList;
import java.util.List;

import src.enums.LoginResult;
import src.enums.CompanyApprovalStatus;
import src.enums.InternshipStatus;
import src.enums.InternshipWithdrawalStatus;
import src.interfaces.AuthController;
import src.interfaces.IReportGenerator;
import src.report.ReportGenerator;
import src.report.ReportCriteria;
import src.DataStore;
import src.entity.CareerCenterStaff;
import src.entity.CompanyRepresentative;
import src.entity.Internship;
import src.entity.InternshipApplication;
import src.entity.Student;

/**
 * Controller used by career center staff to perform administrative actions.
 *
 * <p>
 * Responsibilities include authenticating staff, managing company
 * approvals, reviewing and approving internships, handling student
 * withdrawal requests, and generating internship reports.
 */
public class CareerCenterStaffController implements AuthController, IReportGenerator {
    private CareerCenterStaff currentStaff;
    private DataStore dataStore;

    /**
     * Constructs the controller and acquires the shared `DataStore` instance.
     */
    public CareerCenterStaffController() {
        this.dataStore = DataStore.getInstance();
    }

    /**
     * Sets the currently authenticated career center staff user for this
     * controller.
     *
     * @param c the `CareerCenterStaff` to set as current
     */
    public void setCurrentStaff(CareerCenterStaff c) {
        this.currentStaff = c;
    }

    /**
     * Returns the currently authenticated career center staff user.
     *
     * @return the current `CareerCenterStaff`, or null if none authenticated
     */
    public CareerCenterStaff getCurrentStaff() {
        return this.currentStaff;
    }

    /**
     * Attempts to authenticate a career center staff user.
     *
     * @param userName staff user id
     * @param pw       plaintext password to verify
     * @return a `LoginResult` indicating success or the type of failure
     */
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

    /**
     * Updates the password for the currently authenticated staff user.
     *
     * @param oldPW existing password to verify
     * @param newPW new password to set when verification succeeds
     * @return true if password was updated, false otherwise
     */
    @Override
    public boolean updatePassword(String oldPW, String newPW) {
        if (currentStaff.getPassword().equals(oldPW)) {
            currentStaff.setPassword(newPW);
            return true;
        }
        return false;
    }

    /**
     * Logs out the currently authenticated staff by clearing the current staff
     * reference.
     */
    @Override
    public void logout() {
        setCurrentStaff(null);
    }

    /**
     * Returns a list of company representatives whose approval status is pending.
     *
     * @return list of pending `CompanyRepresentative` instances
     */
    public List<CompanyRepresentative> getPendingCompanies() {
        List<CompanyRepresentative> pending = new ArrayList<>();

        for (CompanyRepresentative rep : dataStore.getCompanyRepresentativeList()) {
            if (CompanyApprovalStatus.PENDING == (rep.getApproval())) {
                pending.add(rep);
            }
        }
        return pending;
    }

    /**
     * Authorises (approves) a company representative by id.
     *
     * @param companyRepId id of the company representative to approve
     * @return true if the representative was found and approved, false otherwise
     */
    public boolean authoriseCompany(String companyRepId) {
        CompanyRepresentative company = dataStore.findCompanyRep(companyRepId);
        if (company != null) {
            company.setApproval(CompanyApprovalStatus.APPROVED);
            return true;
        }
        return false;
    }

    /**
     * Rejects (removes) a company representative by id.
     *
     * Note: the current implementation removes the representative from the
     * data store rather than setting an explicit REJECTED state.
     *
     * @param companyRepId id of the company representative to remove
     * @return true if the representative was found and removed, false otherwise
     */
    public boolean rejectCompany(String companyRepId) {
        CompanyRepresentative company = dataStore.findCompanyRep(companyRepId);
        if (company != null) {
            // company.setApproval(CompanyApprovalStatus.REJECTED); // alternatively, we can
            // delete the company from the list
            dataStore.getCompanyRepresentativeList().remove(company);
            return true;
        }
        return false;
    }

    /**
     * Returns internships that are pending approval.
     *
     * @return list of pending `Internship` objects
     */
    public List<Internship> getPendingInternships() {
        List<Internship> pending = new ArrayList<>();
        for (Internship i : dataStore.getInternshipList()) {
            if (i.getStatus() == InternshipStatus.PENDING) { // false = pending
                pending.add(i);
            }
        }
        return pending;
    }

    /**
     * Approves the internship with the provided id.
     *
     * @param internshipId id of the internship to approve
     * @return true if the internship was found and approved, false otherwise
     */
    public boolean approveInternship(String internshipId) {
        Internship internship = dataStore.findInternship(internshipId);
        if (internship != null) {
            internship.setStatus(InternshipStatus.APPROVED);
            return true;
        }
        return false;
    }

    /**
     * Rejects (removes) the internship with the provided id.
     *
     * @param internshipId id of the internship to reject
     * @return true if the internship was found and removed, false otherwise
     */
    public boolean rejectInternship(String internshipId) {
        Internship internship = dataStore.findInternship(internshipId);
        if (internship != null) {
            // internship.setStatus(InternshipStatus.REJECTED); // alternatively, we can
            // delete the internship from the list
            dataStore.getInternshipList().remove(internship);
            internship.getCompanyRep().removeInternship(internship);
            return true;
        }
        return false;
    }

    /**
     * Returns a list of internship applications for which students have
     * requested withdrawal and the withdrawal is pending review.
     *
     * @return list of pending `InternshipApplication` withdrawal requests
     */
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

    /**
     * Approves a student's withdrawal request for a given application.
     * <p>
     * This will set the withdrawal status to APPROVED, remove the
     * application from the data store, and remove the application from the
     * student's applied list.
     *
     * @param app the `InternshipApplication` representing the withdrawal
     * @return true if the withdrawal was pending and is now approved, false
     *         otherwise
     */
    public boolean approveWithdrawal(InternshipApplication app) {
        if (app.getInternshipWithdrawalStatus() == InternshipWithdrawalStatus.PENDING) {
            app.setInternshipWithdrawalStatus(InternshipWithdrawalStatus.APPROVED);
            dataStore.getInternshipApplicationsList().remove(app);
            app.getStudent().removeInternship(app);
            return true;
        }
        return false;
    }

    /**
     * Rejects a student's withdrawal request for a given application.
     *
     * @param app the `InternshipApplication` representing the withdrawal
     * @return true if the withdrawal was pending and is now rejected, false
     *         otherwise
     */
    public boolean rejectWithdrawal(InternshipApplication app) {
        if (app.getInternshipWithdrawalStatus() == InternshipWithdrawalStatus.PENDING) {
            app.setInternshipWithdrawalStatus(InternshipWithdrawalStatus.REJECTED);
            return true;
        }
        return false;
    }

    // LIST GENERATION

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
                    i.getStatus());
        }
    }

}