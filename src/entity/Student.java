package src.entity;

import java.util.ArrayList;
// import src.enums.InternshipWithdrawalStatus;

public class Student extends User {
    private int YearOfStudy;
    private String Major;
    private int applicationCount;
    private Internship InternshipAccepted;
    private ArrayList<InternshipApplication> InternshipApplied;
    // private InternshipWithdrawalStatus internshipWithdrawalStatus;

    public Student(){}

    public Student(String userid, String pw, String name, int YearOfStudy, String Major) {
        super(userid, pw, name);
        this.YearOfStudy = YearOfStudy;
        this.Major = Major;
        this.applicationCount = 0;  // do i really need this or can i just get size of arraylist
        this.InternshipApplied = new ArrayList<>();
        this.InternshipAccepted = null;
    }
    public void setYearOfStudy(int YearOfStudy) {
        this.YearOfStudy = YearOfStudy;
    }

    public void setMajor(String Major) {
        this.Major = Major;
    }

    public int getYearOfStudy() {
        return this.YearOfStudy;
    }

    public String getMajor() {
        return this.Major;
    }

    public int getApplicationCount() {
        return this.applicationCount;
    }

    public ArrayList<InternshipApplication> getInternshipApplied() {
        return this.InternshipApplied;
    }

    public void applyInternship(InternshipApplication internship) {
        this.InternshipApplied.add(internship);
        this.applicationCount++;
    }

    public void removeInternship(InternshipApplication internship) {
        this.InternshipApplied.remove(internship);
        this.applicationCount--;
    }

    public void reset() {
        this.applicationCount = 0;
        this.InternshipApplied.clear();
    }

    public Internship getInternshipAccepted() {
        return this.InternshipAccepted;
    }

    public void setInternshipAccepted(Internship internshipAccepted) {
        this.InternshipAccepted = internshipAccepted;
    }
}
