package src.entity;

import java.util.ArrayList;
import java.util.List;
import src.enums.CompanyApprovalStatus;

/**
 * Represents a company representative user who can post and manage internships.
 *
 * <p>
 * A company representative belongs to a company and can post internships,
 * manage applications, and track posted internships. New representatives start
 * with {@link src.enums.CompanyApprovalStatus#PENDING} approval status and must
 * be approved by career center staff before they can post internships. Each
 * representative can post up to 5 internships.
 */
public class CompanyRepresentative extends User {
    private String companyName;
    private CompanyApprovalStatus companyApprovalStatus; // "pending", "approved","rejected"
    private List<Internship> internships;
    private int internshipCount; // max 5
    private String position;
    private String department;

    /**
     * Constructs a CompanyRepresentative with the specified credentials and company
     * information.
     *
     * @param id          the unique user identifier
     * @param pw          the user's password
     * @param name        the representative's full name
     * @param email       the representative's email address
     * @param companyName the name of the company
     * @param department  the department within the company
     * @param position    the representative's position/title
     */
    public CompanyRepresentative(String id, String pw, String name, String email, String companyName, String department,
            String position) {
        super(id, pw, name, email);
        this.companyName = companyName;
        this.companyApprovalStatus = CompanyApprovalStatus.PENDING;
        this.internshipCount = 0;
        this.internships = new ArrayList<>();
        this.position = position;
        this.department = department;

    }

    // Getter functions

    /**
     * Gets the name of the company.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Gets the approval status of this company representative.
     *
     * @return the {@link src.enums.CompanyApprovalStatus}
     */
    public CompanyApprovalStatus getApproval() {
        return companyApprovalStatus;
    }

    /**
     * Gets the number of internships currently posted by this representative.
     *
     * @return the internship count (max 5)
     */
    public int getInternshipCount() {
        return internshipCount;
    }

    /**
     * Gets the list of internships posted by this representative.
     *
     * @return a {@link java.util.List} of {@link src.entity.Internship} objects
     */
    public List<Internship> getInternships() {
        return internships;
    }

    /**
     * Gets the department within the company.
     *
     * @return the department name
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * Gets the representative's position or title.
     *
     * @return the position/title
     */
    public String getPosition() {
        return this.position;
    }

    // Setter functions

    /**
     * Sets the company name.
     *
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Sets the approval status of this company representative.
     *
     * @param approval the {@link src.enums.CompanyApprovalStatus} to set
     */
    public void setApproval(CompanyApprovalStatus approval) {
        this.companyApprovalStatus = approval;
    }

    /**
     * Sets the number of internships posted by this representative.
     *
     * @param count the internship count (should not exceed 5)
     */
    public void setInternshipCount(int count) {
        this.internshipCount = count;
    }

    /**
     * Increments the internship count by one.
     */
    public void incrementInternshipCount() {
        this.internshipCount += 1;
    }

    /**
     * Sets the list of internships for this representative.
     *
     * @param internships the list of {@link src.entity.Internship} objects
     */
    public void setInternships(List<Internship> internships) {
        this.internships = internships;
    }

    /**
     * Sets the department within the company.
     *
     * @param department the department name to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Sets the representative's position or title.
     *
     * @param position the position/title to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Adds an internship to this representative's list and increments the count.
     *
     * @param internship the {@link src.entity.Internship} to add
     */
    public void addInternship(Internship internship) {
        this.internships.add(internship);
        this.incrementInternshipCount();
        ;
    }

    /**
     * Removes an internship from this representative's list and decrements the
     * count.
     *
     * @param internship the {@link src.entity.Internship} to remove
     */
    public void removeInternship(Internship internship) {
        this.internships.remove(internship);
        this.internshipCount -= 1;
    }

    /**
     * Returns a string representation of the CompanyRepresentative including all
     * key attributes and posted internship titles.
     *
     * @return a string containing representative and company information
     */
    @Override
    public String toString() {
        List<Internship> allInternships = getInternships();
        List<String> internshipTitles = new ArrayList<>();
        for (Internship internship : allInternships) {
            internshipTitles.add(internship.getTitle());
        }

        return "CompanyRepresentative{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", companyName='" + getCompanyName() + '\'' +
                ", companyApprovalStatus='" + getApproval() + '\'' +
                ", internshipCount=" + getInternshipCount() + '\'' +
                ", internships=" + String.join(";", internshipTitles) + '\'' +
                ", position='" + getPosition() + '\'' +
                ", department='" + getDepartment() + '\'' +
                '}';
    }
}
