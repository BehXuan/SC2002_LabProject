package src.entity;

public class InternshipApplication {
    private CompanyRepresentative companyRep;
    private Student student;
    private Internship internship;
    private String studentAccept; // e.g., "Pending", "Accepted", "Rejected"
    private String companyAccept; // e.g., "Pending", "Accepted", "Rejected"
    private int applicationId;
    
    public InternshipApplication() {
    }

    public InternshipApplication(int applicationId, CompanyRepresentative companyRep, Student student, Internship internship) {
        this.applicationId = applicationId;
        this.companyRep = companyRep;
        this.student = student;
        this.internship = internship;
        this.studentAccept = "Pending"; // Default status
        this.companyAccept = "Pending"; // Default status
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
    public String getStudentAccept() {
        return studentAccept;
    }
    public void setStudentAccept(String applicationStatus) {
        this.studentAccept = applicationStatus;
    }
    public int getApplicationId() {
        return applicationId;
    }
    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
    public String getCompanyAccept() {
        return companyAccept;
    }
    public void setCompanyAccept(String companyAccept) {
        this.companyAccept = companyAccept;
    }
}
