package src.controller;

import java.util.ArrayList;
import java.util.List;

import src.DataStore;
import src.entity.Student;
import src.entity.Internship;
import src.entity.InternshipApplication;
import src.enums.InternshipStatus;
import src.enums.InternshipWithdrawalStatus;
import src.enums.LoginResult;
import src.interfaces.AuthController;
import src.interfaces.IReportGenerator;
import src.report.ReportCriteria;
import src.report.ReportGenerator;
import src.enums.InternshipLevel;

/**
 * Controller used by students to search internships, manage applications,
 * and accept/withdraw from offers.
 *
 * <p>Responsibilities include account authentication, browsing opportunities,
 * applying for internships, accepting offers, withdrawing from applications,
 * and generating reports.
 */
public class StudentController implements AuthController, IReportGenerator {
    private Student currentStudent;
    private DataStore dataStore;

    /**
     * Constructs the controller and acquires the shared `DataStore` instance.
     */
    public StudentController() {
        this.dataStore = DataStore.getInstance();
    }

    /**
     * Sets the currently authenticated student for this controller.
     *
     * @param s the `Student` to set as current
     */
    public void setCurrentStudent(Student s) {
        this.currentStudent = s;
    }

    /**
     * Returns the currently authenticated student.
     *
     * @return the current `Student`, or null if none authenticated
     */
    public Student getCurrentStudent() {
        return currentStudent;
    }

    /**
     * Attempts to authenticate a student user.
     *
     * @param userName student user id
     * @param pw       plaintext password to verify
     * @return a `LoginResult` indicating success or the type of failure
     */
    @Override
    public LoginResult login(String userName, String pw) {
        // check the userName and pw against dataStore
        Student s = dataStore.findStudent(userName);
        if (s == null) {
        return LoginResult.USER_NOT_FOUND;
        }
        if (!s.getPassword().equals(pw)) {
        return LoginResult.WRONG_PASSWORD;
        }

        setCurrentStudent(s);
        return LoginResult.SUCCESS;
    }

    /**
     * Logs out the currently authenticated student by clearing the current student reference.
     */
    @Override
    public void logout() {
        setCurrentStudent(null);
    }

    /**
     * Updates the password for the currently authenticated student.
     *
     * @param oldPW existing password for verification
     * @param newPW new password to set
     * @return true if the password was updated, false otherwise
     */
    @Override
    public boolean updatePassword(String oldPW, String newPW) {
        if (getCurrentStudent() == null) {
            return false;
        }

        if (getCurrentStudent().getPassword().equals(oldPW)) {
            getCurrentStudent().setPassword(newPW);
            return true;
        }
        return false;
    }

    /**
     * Returns a list of internship opportunities available to the current student.
     *
     * <p>Filtering criteria include visibility, approval status, availability,
     * major compatibility, and internship level (basic level required for first/second year students).
     *
     * @return list of applicable `Internship` objects
     */
    public ArrayList<Internship> getInternshipsOpportunities() {
        ArrayList<Internship> allInternships = dataStore.getInternshipList();

        ArrayList<Internship> visibleInternships = new ArrayList<Internship>();
        for (Internship i : allInternships) {
            if (i.getVisibility() && i.getStatus() == InternshipStatus.APPROVED && i.isAvailable()  && i.getMajor().equalsIgnoreCase(getCurrentStudent().getMajor())) {
                visibleInternships.add(i);
            }
        }

        if (getCurrentStudent().getYearOfStudy() >= 3) {
            return visibleInternships;
        }
        ArrayList<Internship> basicInternships = new ArrayList<Internship>();
        for (Internship i : visibleInternships) {
            if (i.getLevel().equals(InternshipLevel.BASIC)) { // should change to enum?
                basicInternships.add(i);
            }
        }
        return basicInternships;
    }

    /**
     * Submits an application for the given internship on behalf of the current student.
     *
     * <p>Validation checks include: student not already accepted for another internship,
     * student has fewer than 3 pending applications, student hasn't already applied to
     * this internship, and major compatibility.
     *
     * @param internship the `Internship` to apply for
     * @return true if application was created and added, false if any validation fails
     */
    public boolean applyForInternship(Internship internship) {
        if (internship == null || getCurrentStudent() == null) {
            return false;
        }

        if (getCurrentStudent().getInternshipAccepted() != null){
            return false; // already have internship
        }
        if (getCurrentStudent().getInternshipApplied().size() >= 3) {
            return false; // only can apply for 3 internships
        }

        for (InternshipApplication app : getCurrentStudent().getInternshipApplied()) {
            if (app.getInternship().getInternshipId().equals(internship.getInternshipId())) {
                return false; // Already applied
            }
        }

        // Check major compatibility
        if (!internship.getMajor().toLowerCase().equals(getCurrentStudent().getMajor().toLowerCase())) {
            return false;
        }

        InternshipApplication newApplication = new InternshipApplication(
                getCurrentStudent().getUserId() + "_" + internship.getInternshipId(), internship.getCompanyRep(), getCurrentStudent(),
                internship);
        getCurrentStudent().applyInternship(newApplication);
        dataStore.getInternshipApplicationsList().add(newApplication);
        return true;
    }

    /**
     * Returns all pending internship applications submitted by the current student.
     *
     * @return list of `InternshipApplication` objects for the current student
     */
    public ArrayList<InternshipApplication> getMyApplications() {
        return getCurrentStudent().getInternshipApplied();
    }

    /**
     * Accepts an internship offer by confirming the company's approval and
     * setting the internship as accepted for the current student.
     *
     * <p>This action withdraws all other pending applications and adds the
     * student to the internship's applicant list.
     *
     * @param application the `InternshipApplication` offer to accept
     * @return true if the offer was accepted, false if validation fails
     */
    public boolean acceptInternshipOffer(InternshipApplication application) {
        if (application == null || getCurrentStudent() == null) {
            return false;
        }

        // Check if this application belongs to the current student
        if (!getCurrentStudent().getInternshipApplied().contains(application)) {
            return false;
        }
        // Check if the company has approved the application
        if (application.getCompanyAccept() != InternshipStatus.APPROVED) {
            return false;
        }

        if (application.getInternship().getNumberOfSlotsLeft() <= 0) {
            return false; // No slots left
        }
        // application.setStudentAccept("Accepted");
        getCurrentStudent().setInternshipAccepted(application.getInternship());
        for (InternshipApplication app : getCurrentStudent().getInternshipApplied()) {
            // app.setApplicationId(null); // mark other applications as void
            dataStore.getInternshipApplicationsList().remove(app);
        }
        getCurrentStudent().reset();
        //
        // application.getInternship().setNumberOfSlotsLeft(application.getInternship().getNumberOfSlotsLeft() - 1)
        application.getInternship().addApplicant(getCurrentStudent());
        getCurrentStudent().reset();
        return true;

    }

    /**
     * Submits a withdrawal request for the given internship application.
     *
     * @param application the `InternshipApplication` for which to request withdrawal
     * @return true after marking the withdrawal as pending
     */
    public boolean wtihdraw(InternshipApplication application) {
        application.setInternshipWithdrawalStatus(InternshipWithdrawalStatus.PENDING);
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
     * Prints a textual report to standard output for the provided internships.
     *
     * @param internships list of internships to print
     */
    public void printReport(List<Internship> internships) {
        System.out.println("===== Internship Report =====");
        if (internships.isEmpty()) {
            System.out.println("No internships found for the given criteria.");
            return;
        }
        for (int i = 1; i<=internships.size();i++){
            System.out.print(i+". ");
            System.out.println(internships.get(i-1));
        }
        // for (Internship i : internships) {
        //     System.out.println(i);
        // }
    }
}
