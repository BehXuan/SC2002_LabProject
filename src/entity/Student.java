package src.entity;

import java.util.ArrayList;
// import src.enums.InternshipWithdrawalStatus;

public class Student extends User {
    private int yearOfStudy;
    private String major;
    private Internship internshipAccepted;
    private ArrayList<InternshipApplication> internshipApplied;
    // private InternshipWithdrawalStatus internshipWithdrawalStatus;

    public Student(){}

    public Student(String userid, String pw, String name, String email, int yearOfStudy, String major) {
        super(userid, pw, name, email);
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        this.internshipApplied = new ArrayList<>();
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
}
