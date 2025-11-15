package src.controller;

import java.util.ArrayList;

import src.DataStore;
import src.entity.Student;
import src.entity.Internship;
import src.entity.InternshipApplication;
import src.enums.InternshipStatus;


public class StudentController implements AuthController{
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
        for (Student s : dataStore.getStudentList()) {
            if (s.getName().equals(userName) && s.getPassword().equals(pw)) {
                setCurrentStudent(s);
                return true;
            }
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
            if (i.getVisibility() && i.getStatus() == InternshipStatus.APPROVED && i.isAvailable()) {
                visibleInternships.add(i);
            }
        }
        
        if (getCurrentStudent().getYearOfStudy() >= 3){
            return visibleInternships;
        }
        ArrayList<Internship> basicInternships = new ArrayList<Internship>();
        for (Internship i : visibleInternships) {
            if (i.getLevel().equals("Basic")) { //should change to enum?
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
        if (!internship.getMajor().equals(getCurrentStudent().getMajor())) {
            return false;
        }


        InternshipApplication newApplication = new InternshipApplication(dataStore.getInternshipApplicationsList().size() + 1, internship.getCompanyRep(), getCurrentStudent(), internship);
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
        // application.setStudentAccept("Accepted");
        getCurrentStudent().setInternshipAccepted(application.getInternship());
        for (InternshipApplication app : getCurrentStudent().getInternshipApplied()) {
            app.setApplicationId(-1); // mark other applications as void
        }
        getCurrentStudent().reset();
        return true;

    }
}
