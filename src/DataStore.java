package src;

import src.entity.Student;
import src.entity.CareerCenterStaff;
import src.entity.CompanyRepresentative;
import src.entity.Internship;
import src.entity.InternshipApplication;
import java.util.ArrayList;

// change the required parameters
// change the init to copy the data from csv or smt

public class DataStore {
    private static DataStore instance;
    private ArrayList<Student> studentList;
    private ArrayList<CompanyRepresentative> companyRepresentativeList;
    private ArrayList<CareerCenterStaff> careerCenterStaffList;
    private ArrayList<Internship> internshipList;
    private ArrayList<InternshipApplication> internshipApplicationsList;

    private DataStore() {
        // Initialize empty ArrayLists
        this.studentList = new ArrayList<>();
        this.companyRepresentativeList = new ArrayList<>();
        this.careerCenterStaffList = new ArrayList<>();
        this.internshipList = new ArrayList<>();
        this.internshipApplicationsList = new ArrayList<>();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }
    // GETTERS

    public ArrayList<Student> getStudentList() {
        return this.studentList;
    }

    public ArrayList<CompanyRepresentative> getCompanyRepresentativeList() {
        return this.companyRepresentativeList;
    }

    public ArrayList<CareerCenterStaff> getCareerCenterStaffList() {
        return this.careerCenterStaffList;
    }

    public ArrayList<Internship> getInternshipList() {
        return this.internshipList;
    }

    public ArrayList<InternshipApplication> getInternshipApplicationsList() {
        return this.internshipApplicationsList;
    }

    // SETTERS
    public void studentAdd(String name) {
        this.studentList.add(new Student());
    }

    public void CompanyRepresentativeAdd(String name) {
        this.companyRepresentativeList.add(new CompanyRepresentative());
    }

    public void CareerCenterStaffAdd(String name) {
        this.careerCenterStaffList.add(new CareerCenterStaff());
    }

    public void InternshipAdd() {
        this.internshipList.add(new Internship());
    }

    public void InternshipApplicationAdd() {
        this.internshipApplicationsList.add(new InternshipApplication());
    }

    public void addInternship(Internship internship) {
        internshipList.add(internship);
    }

    // FINDERS: USED TO FIND WHETHER USERID EXISTs WITHIN RESP DATASTORE

    public CompanyRepresentative findCompanyRep(String repId) {
        for (CompanyRepresentative rep : companyRepresentativeList) {
            if (rep.getUserId().equals(repId)) {
                return rep;
            }
        }
        return null;
    }

    public Student findStudent(String studentId) {
        for (Student s : studentList) {
            if (s.getUserId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }

    public Internship findInternship(int internshipId) {
        for (Internship i : internshipList) {
            if (i.getInternshipId() == internshipId) {
                return i;
            }
        }
        return null;
    }

}
