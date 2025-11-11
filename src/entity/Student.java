package src.entity;

import java.util.ArrayList;
import src.enums.InternshipWithdrawalStatus;

public class Student extends User {

    private int YearOfStudy;
    private String Major;
    private int applicationCount;
    private ArrayList<Internship> InternshipApplied;
    private InternshipWithdrawalStatus internshipWithdrawalStatus;

    public Student(){}

    public Student(int YearOfStudy, String Major) {
        this.YearOfStudy = YearOfStudy;
        this.Major = Major;
        this.applicationCount = 0;
        this.InternshipApplied = new ArrayList<>();
        this.internshipWithdrawalStatus = InternshipWithdrawalStatus.NONE;
    }


}
