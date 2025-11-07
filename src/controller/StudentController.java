package src.controller;

import src.DataStore;
import src.entity.Student;

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
}
