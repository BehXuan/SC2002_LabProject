package src.controller;

import java.util.ArrayList;

import src.DataStore;
import src.entity.Student;
import src.entity.Internship;
import src.entity.InternshipApplication;

public class StudentController {
    private Student currentStudent;
    private DataStore dataStore;

    public StudentController() {
        this.dataStore = new DataStore();  // im thinking if i should pass datastore from outside
    }

    public void setCurrentStudent(Student s) {
        this.currentStudent = s;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public boolean login(String userName, String pw) {
        // check the userName and pw against dataStore
        for (Student s : dataStore.getStudentList()) {
            if (s.getName() == userName && s.getPassword() == pw) {
                setCurrentStudent(s);
                return true;
            }
        }
        return false;
    }

    public void logout() {
        setCurrentStudent(null);
    }

    public boolean updatePassword(String oldPW, String newPW) {
        if (getCurrentStudent() == null) {
            return false;
        }

        if (getCurrentStudent().getPassword().equals(oldPW)) {
            getCurrentStudent().setUserId(newPW);
            return true;
        }
        return false;
    }

    public ArrayList<Internship> getInternshipsOpportunities() {
        ArrayList<Internship> allInternships = dataStore.getInternshipList();

        ArrayList<Internship> visibleInternships = new ArrayList<Internship>();
        for (Internship i : allInternships) {
            if (i.getVisibility()) {
                visibleInternships.add(i);
            }
        }
        
        if (getCurrentStudent().getYearOfStudy() >= 3){
            return visibleInternships;
        }
        ArrayList<Internship> availableInternships = new ArrayList<Internship>();
        for (Internship i : visibleInternships) {
            if (i.getLevel() == "Basic") { //remember to check with internship class
                availableInternships.add(i);
            }
        }
        return availableInternships;
    }

    public void applyForInternship(Internship internship) {
        if (internship == null || getCurrentStudent() == null) {
            return;
        }
        if (getCurrentStudent().getInternshipApplied().size() > 3) {
            return; // only can apply for 3 internships
        }
        InternshipApplication newApplication = new InternshipApplication(dataStore.getInternshipApplicationsList().size(), internship.getCompanyRep(), getCurrentStudent(), internship);
        getCurrentStudent().applyInternship(newApplication);
        dataStore.getInternshipApplicationsList().add(newApplication);
    }

    public ArrayList<InternshipApplication> getMyApplications() {
        return getCurrentStudent().getInternshipApplied();
    }

    public void acceptInternshipOffer(InternshipApplication application) {
        if (application == null || getCurrentStudent() == null) {
            return;
        }
        // application.setStudentAccept("Accepted");
        getCurrentStudent().setInternshipAccepted(application.getInternship());
        for (InternshipApplication app : getCurrentStudent().getInternshipApplied()) {
            app.setApplicationId(-1); // mark other applications as void
        }
        getCurrentStudent().reset();

    }
}
