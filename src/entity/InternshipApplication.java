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
    private String applicationId;

    /**
     * Constructs a new InternshipApplication with the provided information.
     *
     * @param applicationId unique identifier for the application
     * @param companyRep    the `CompanyRepresentative` that owns the internship
     * @param student       the `Student` applying
     * @param internship    the `Internship` being applied for
     */
    public InternshipApplication(String applicationId, CompanyRepresentative companyRep, Student student,
            Internship internship) {
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

    /**
     * Returns the company representative associated with this application.
     *
     * @return the `CompanyRepresentative` for this application
     */
    public void setCompanyRep(CompanyRepresentative companyRep) {
        this.companyRep = companyRep;
    }

    /**
     * Sets the company representative for this application.
     *
     * @param companyRep the `CompanyRepresentative` to assign
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Returns the student who submitted this application.
     *
     * @return the `Student` applicant
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Sets the student for this application.
     *
     * @param student the `Student` to associate
     */
    public Internship getInternship() {
        return internship;
    }

    /**
     * Returns the internship referenced by this application.
     *
     * @return the `Internship` applied for
     */
    public void setInternship(Internship internship) {
        this.internship = internship;
    }

    /**
     * Sets the internship referenced by this application.
     *
     * @param internship the `Internship` to set
     */
    public InternshipStatus getStudentAccept() {
        return studentAccept;
    }

    /**
     * Returns the student's acceptance status for this application.
     *
     * @return the student's `InternshipStatus`
     */
    public void setStudentAccept(InternshipStatus applicationStatus) {
        this.studentAccept = applicationStatus;
    }

    /**
     * Sets the student's acceptance status for this application.
     *
     * @param applicationStatus the `InternshipStatus` to set for the student
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Returns the unique identifier for this application.
     *
     * @return the application id
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Sets the unique identifier for this application.
     *
     * @param applicationId the id to set
     */
    public InternshipStatus getCompanyAccept() {
        return companyAccept;
    }

    /**
     * Returns the company's acceptance status for this application.
     *
     * @return the company's `InternshipStatus`
     */
    public void setCompanyAccept(InternshipStatus companyAccept) {
        this.companyAccept = companyAccept;
    }

    /**
     * Sets the company's acceptance status for this application.
     *
     * @param companyAccept the `InternshipStatus` to set for the company
     */

    public void setInternshipWithdrawalStatus(InternshipWithdrawalStatus studentWithdraw) {
        this.studentWithdraw = studentWithdraw;
    }

    /**
     * Sets the withdrawal status for the student on this application.
     *
     * @param studentWithdraw the `InternshipWithdrawalStatus` to set
     */

    public InternshipWithdrawalStatus getInternshipWithdrawalStatus() {
        return this.studentWithdraw;
    }

    /**
     * Returns the student's withdrawal status for this application.
     *
     * @return the `InternshipWithdrawalStatus`
     */

    @Override
    public String toString() {
        return "InternshipApplication{" +
                "applicationId=" + applicationId +
                ", companyRep=" + companyRep.getCompanyName() +
                ", student=" + student.getName() +
                ", internship=" + internship.getTitle() +
                ", studentAccept=" + studentAccept +
                ", companyAccept=" + companyAccept +
                ", studentWithdraw=" + studentWithdraw +
                '}';
    }
}
