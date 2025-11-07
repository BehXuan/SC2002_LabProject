package src.entity;

import java.util.ArrayList;

public class Student extends User {
    private int YearOfStudy;
    private String Major;
    private int applicationCount;
    private ArrayList<Internship> InternshipApplied;

    public Student(){}

    public Student(String userid, String pw, String name, int YearOfStudy, String Major) {
        super(userid, pw, name);
        this.YearOfStudy = YearOfStudy;
        this.Major = Major;
        this.applicationCount = 0;
        this.InternshipApplied = new ArrayList<>();
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

    public ArrayList<Internship> getInternshipApplied() {
        return this.InternshipApplied;
    }

    public void applyInternship(Internship internship) {
        this.InternshipApplied.add(internship);
        this.applicationCount++;
    }

    public void removeInternship(Internship internship) {
        this.InternshipApplied.remove(internship);
        this.applicationCount--;
    }

    public void reset() {
        this.applicationCount = 0;
        this.InternshipApplied.clear();
    }
}
