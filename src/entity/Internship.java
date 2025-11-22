package src.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import src.enums.InternshipStatus;
import src.enums.InternshipLevel;

/**
 * Represents an internship posting created by a company representative.
 *
 * <p>
 * An Internship contains metadata such as title, description, level,
 * major, open/close dates, available slots, visibility flag and the list
 * of applicants. Instances are created by company representatives and
 * consumed by students when applying.
 */
public class Internship {
    private String internshipId;
    private String title;
    private String description;
    private InternshipLevel internshipLevel;
    private String major;
    private LocalDate openDate;
    private LocalDate closeDate;
    private InternshipStatus internshipStatus;
    private int numberOfSlotsLeft;
    private boolean visibility;
    private CompanyRepresentative companyRep;
    private ArrayList<Student> applicants;

    /**
     * Constructs a new Internship with the provided metadata.
     *
     * @param internshipId      unique identifier for the internship
     * @param title             human-readable title
     * @param description       detailed description of the internship
     * @param internshipLevel   level/grade of the internship (e.g. JUNIOR, SENIOR)
     * @param major             targeted major for applicants
     * @param openDate          date when applications open
     * @param closeDate         date when applications close
     * @param numberOfSlotsLeft number of available slots for the internship
     * @param companyRep        the company representative who created the
     *                          internship
     */
    public Internship(String internshipId, String title, String description, InternshipLevel internshipLevel,
            String major,
            LocalDate openDate, LocalDate closeDate, int numberOfSlotsLeft,
            CompanyRepresentative companyRep) {
        this.internshipId = internshipId;
        this.title = title;
        this.description = description;
        this.internshipLevel = internshipLevel;
        this.major = major;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.internshipStatus = InternshipStatus.PENDING;
        this.companyRep = companyRep;
        this.numberOfSlotsLeft = numberOfSlotsLeft;
        this.visibility = true;
        this.applicants = new ArrayList<>();

    }

    /**
     * Returns the company representative who created or manages this internship.
     *
     * @return the `CompanyRepresentative` for this internship
     */
    public CompanyRepresentative getCompanyRep() {
        return this.companyRep;
    }

    /**
     * Sets the company representative responsible for this internship.
     *
     * @param companyRep the `CompanyRepresentative` to assign
     */
    public void setCompanyRep(CompanyRepresentative companyRep) {
        this.companyRep = companyRep;
    }

    /**
     * Returns the internship's unique identifier.
     *
     * @return the internship id
     */
    public String getInternshipId() {
        return this.internshipId;
    }

    /**
     * Sets the internship's unique identifier.
     *
     * @param internshipId the id to set
     */
    public void setInternshipId(String internshipId) {
        this.internshipId = internshipId;
    }

    /**
     * Returns the internship title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the internship title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the internship description.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the internship description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the internship level.
     *
     * @return the `InternshipLevel` of this internship
     */
    public InternshipLevel getLevel() {
        return this.internshipLevel;
    }

    /**
     * Sets the internship level.
     *
     * @param internshipLevel the `InternshipLevel` to set
     */
    public void setLevel(InternshipLevel internshipLevel) {
        this.internshipLevel = internshipLevel;
    }

    /**
     * Returns the targeted major for this internship.
     *
     * @return the major
     */
    public String getMajor() {
        return this.major;
    }

    /**
     * Sets the targeted major for this internship.
     *
     * @param major the major to set
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Returns the date applications open for this internship.
     *
     * @return the open date
     */
    public LocalDate getOpenDate() {
        return this.openDate;
    }

    /**
     * Sets the open date for applications.
     *
     * @param openDate the open date to set
     */
    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    /**
     * Returns the date applications close for this internship.
     *
     * @return the close date
     */
    public LocalDate getCloseDate() {
        return this.closeDate;
    }

    /**
     * Sets the close date for applications.
     *
     * @param closeDate the close date to set
     */
    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * Returns the current status of the internship (e.g. PENDING, APPROVED).
     *
     * @return the `InternshipStatus`
     */
    public InternshipStatus getStatus() {
        return this.internshipStatus;
    }

    /**
     * Sets the status of the internship.
     *
     * @param internshipStatus the `InternshipStatus` to set
     */
    public void setStatus(InternshipStatus internshipStatus) {
        this.internshipStatus = internshipStatus;
    }

    /**
     * Returns the number of remaining slots left for this internship.
     *
     * @return remaining slots
     */
    public int getNumberOfSlotsLeft() {
        return this.numberOfSlotsLeft;
    }

    /**
     * Sets the number of remaining slots for this internship.
     *
     * @param numberOfSlotsLeft the number of slots to set
     */
    public void setNumberOfSlotsLeft(int numberOfSlotsLeft) {
        this.numberOfSlotsLeft = numberOfSlotsLeft;
    }

    /**
     * Returns whether this internship is visible to students.
     *
     * @return true if visible, false otherwise
     */
    public boolean getVisibility() {
        return this.visibility;
    }

    /**
     * Sets the visibility flag for this internship.
     *
     * @param visibility true to make visible to students, false to hide
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Returns whether this internship currently has availability.
     * <p>
     * Note: the current implementation checks only `numberOfSlotsLeft > 0`.
     * Date-based checks (open/close) are intentionally commented out in code.
     *
     * @return true if there are available slots, false otherwise
     */
    public boolean isAvailable() {
        if (numberOfSlotsLeft > 0) {
            return true;
        }

        return false;
    }

    /**
     * Returns the list of students who have applied to this internship.
     *
     * @return unmodifiable view of applicants as a `List<Student>` (backed by
     *         internal list)
     */
    public List<Student> getApplicants() {
        return applicants;
    }

    /**
     * Adds a student to the list of applicants and decrements available slots.
     *
     * @param student the `Student` applying to this internship
     */
    public void addApplicant(Student student) {
        applicants.add(student);
        this.numberOfSlotsLeft--;
    }

    /**
     * Removes a student from the list of applicants and increments available slots.
     *
     * @param student the `Student` to remove
     */
    public void removeApplicant(Student student) {
        applicants.remove(student);
        this.numberOfSlotsLeft++;
    }

    @Override
    public String toString() {
        List<String> applicantsName = new ArrayList<>();
        for (Student s : applicants) {
            applicantsName.add(s.getName());
        }

        return "Internship{" +
                "internshipId=" + internshipId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", internshipLevel=" + internshipLevel +
                ", major='" + major + '\'' +
                ", openDate=" + openDate +
                ", closeDate=" + closeDate +
                ", internshipStatus=" + internshipStatus +
                ", numberOfSlotsLeft=" + numberOfSlotsLeft +
                ", visibility=" + visibility +
                ", companyRep=" + companyRep.getCompanyName() +
                ", applicants=" + String.join(", ", applicantsName) +
                '}';
    }
}
