package src.controller;

import java.util.ArrayList;
import java.util.List;

import src.DataStore;
import src.entity.Student;
import src.entity.Internship;
import src.entity.InternshipApplication;
import src.enums.InternshipStatus;
import src.enums.InternshipWithdrawalStatus;
import src.interfaces.AuthController;
import src.interfaces.IReportGenerator;
import src.report.ReportCriteria;
import src.report.ReportGenerator;
import src.enums.InternshipLevel;

public class StudentController implements AuthController, IReportGenerator {
    private Student currentStudent;
    private DataStore dataStore;

    public StudentController() {
        this.dataStore = DataStore.getInstance();
    }

    public void setCurrentStudent(Student s) {
        this.currentStudent = s;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    @Override
    public boolean login(String userName, String pw) {
        // check the userName and pw against dataStore
        Student s = dataStore.findStudent(userName);
        if (s != null && s.getPassword().equals(pw)) {
            setCurrentStudent(s);
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        setCurrentStudent(null);
    }

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

    public ArrayList<Internship> getInternshipsOpportunities() {
        ArrayList<Internship> allInternships = dataStore.getInternshipList();

        ArrayList<Internship> visibleInternships = new ArrayList<Internship>();
        for (Internship i : allInternships) {
            if (i.getVisibility() && i.getStatus() == InternshipStatus.APPROVED && i.isAvailable()  && i.getMajor() == getCurrentStudent().getMajor()) {
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

    public boolean applyForInternship(Internship internship) {
        if (internship == null || getCurrentStudent() == null) {
            return false;
        }
        if (getCurrentStudent().getInternshipApplied().size() >= 3) {
            return false; // only can apply for 3 internships
        }

        for (InternshipApplication app : getCurrentStudent().getInternshipApplied()) {
            if (app.getInternship() == internship) {
                return false; // Already applied
            }
        }

        // Check major compatibility
        if (!internship.getMajor().toLowerCase().equals(getCurrentStudent().getMajor().toLowerCase())) {
            return false;
        }

        InternshipApplication newApplication = new InternshipApplication(
                getCurrentStudent().getUserId() + "_" + internship.getCompanyRep().getUserId(), internship.getCompanyRep(), getCurrentStudent(),
                internship);
        getCurrentStudent().applyInternship(newApplication);
        dataStore.getInternshipApplicationsList().add(newApplication);
        return true;
    }

    public ArrayList<InternshipApplication> getMyApplications() {
        return getCurrentStudent().getInternshipApplied();
    }

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

    public boolean wtihdraw(InternshipApplication application) {
        application.setInternshipWithdrawalStatus(InternshipWithdrawalStatus.PENDING);
        return true;
    }

    private ReportGenerator reportGen = new ReportGenerator();

    @Override
    public List<Internship> generateReport(ReportCriteria criteria) {
        return reportGen.generateReport(criteria);
    }

    // Helper to print a report // to print all internships?
    public void printReport(List<Internship> internships) {
        System.out.println("===== Internship Report =====");
        if (internships.isEmpty()) {
            System.out.println("No internships found for the given criteria.");
            return;
        }

        for (Internship i : internships) {
            System.out.println(i);
        }
    }
}
