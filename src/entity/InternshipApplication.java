package src.entity;

import src.enums.InternshipStatus;
import src.enums.InternshipWithdrawalStatus;

public class InternshipApplication {
    private CompanyRepresentative companyRep;
    private Student student;
    private Internship internship;
    private InternshipStatus studentAccept; // e.g., "Pending", "Accepted", "Rejected"
    private InternshipStatus companyAccept; // e.g., "Pending", "Accepted", "Rejected"
    private InternshipWithdrawalStatus studentWithdraw;
    private int applicationId;
    
    public InternshipApplication() {
    }

    public InternshipApplication(int applicationId, CompanyRepresentative companyRep, Student student, Internship internship) {
        this.applicationId = applicationId;
        this.companyRep = companyRep;
        this.student = student;
        this.internship = internship;
        this.studentAccept = InternshipStatus.PENDING; // Default status
        this.companyAccept = InternshipStatus.PENDING; // Default status
        this.studentWithdraw = InternshipWithdrawalStatus.NONE;
    }

    public CompanyRepresentative getCompanyRep() {
        return companyRep;
    }
    public void setCompanyRep(CompanyRepresentative companyRep) {
        this.companyRep = companyRep;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Internship getInternship() {
        return internship;
    }
    public void setInternship(Internship internship) {
        this.internship = internship;
    }
    public InternshipStatus getStudentAccept() {
        return studentAccept;
    }
    public void setStudentAccept(InternshipStatus applicationStatus) {
        this.studentAccept = applicationStatus;
    }
    public int getApplicationId() {
        return applicationId;
    }
    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
    public InternshipStatus getCompanyAccept() {
        return companyAccept;
    }
    public void setCompanyAccept(InternshipStatus companyAccept) {
        this.companyAccept = companyAccept;
    }

    public void setInternshipWithdrawalStatus(InternshipWithdrawalStatus studentWithdraw){
        this.studentWithdraw = studentWithdraw;
    }

    public InternshipWithdrawalStatus getInternshipWithdrawalStatus(){
        return this.studentWithdraw;
    }
}
