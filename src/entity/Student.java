package src.entity;

import java.util.ArrayList;
import java.util.List;
// import src.enums.InternshipWithdrawalStatus;

public class Student extends User {
    private int yearOfStudy;
    private String major;
    private Internship internshipAccepted;
    private ArrayList<InternshipApplication> internshipApplied;
    

    public Student(){}

    public Student(String userid, String pw, String name, String email, int yearOfStudy, String major) {
        super(userid, pw, name, email);
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        this.internshipApplied = new ArrayList<InternshipApplication>();
        this.internshipAccepted = null;
    }
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getYearOfStudy() {
        return this.yearOfStudy;
    }

    public String getMajor() {
        return this.major;
    }

    public ArrayList<InternshipApplication> getInternshipApplied() {
        return this.internshipApplied;
    }

    public void applyInternship(InternshipApplication internship) {
        this.internshipApplied.add(internship);
    }

    public void removeInternship(InternshipApplication internship) {
        this.internshipApplied.remove(internship);
    }

    public void reset() {
        this.internshipApplied.clear();
    }

    public Internship getInternshipAccepted() {
        return this.internshipAccepted;
    }

    public void setInternshipAccepted(Internship internshipAccepted) {
        this.internshipAccepted = internshipAccepted;
    }

    @Override
    public String toString() {
        List<String> internshipAppliedNames = new ArrayList<>();
        for (InternshipApplication ia : internshipApplied) {
            internshipAppliedNames.add(ia.getInternship().getTitle());
        }

        return "Student{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                ", major='" + major + '\'' +
                ", internshipAccepted=" + (internshipAccepted != null ? internshipAccepted.getTitle() : "None") +
                ", internshipApplied=" + String.join(",", internshipAppliedNames) +
                '}';
    }
}
