package src.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student user in the system.
 *
 * <p>
 * A Student extends `User` and maintains academic metadata (year, major)
 * and their internship application state: a list of up to three
 * `InternshipApplication` objects and at most one accepted `Internship`.
 */
public class Student extends User {
    private int yearOfStudy;
    private String major;
    private Internship internshipAccepted; // if not null, internship accplied should be empty/null
    private ArrayList<InternshipApplication> internshipApplied; // max 3

    /**
     * Constructs a Student with the provided user information and academic details.
     *
     * @param userid      unique user id
     * @param pw          password
     * @param name        student's full name
     * @param email       contact email
     * @param yearOfStudy student's year of study (numeric)
     * @param major       student's major
     */
    public Student(String userid, String pw, String name, String email, int yearOfStudy, String major) {
        super(userid, pw, name, email);
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        this.internshipApplied = new ArrayList<InternshipApplication>();
        this.internshipAccepted = null;
    }

    /**
     * Sets the student's year of study.
     *
     * @param yearOfStudy the year to set
     */
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    /**
     * Sets the student's major.
     *
     * @param major the major to set
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Returns the student's year of study.
     *
     * @return year of study
     */
    public int getYearOfStudy() {
        return this.yearOfStudy;
    }

    /**
     * Returns the student's major.
     *
     * @return major
     */
    public String getMajor() {
        return this.major;
    }

    /**
     * Returns the list of internship applications submitted by the student.
     * <p>
     * The application list is backed by an `ArrayList` and may contain up to
     * three applications according to business rules enforced elsewhere.
     *
     * @return list of `InternshipApplication` objects
     */
    public ArrayList<InternshipApplication> getInternshipApplied() {
        return this.internshipApplied;
    }

    /**
     * Adds an `InternshipApplication` to the student's applied list.
     *
     * @param internship the `InternshipApplication` to add
     */
    public void applyInternship(InternshipApplication internship) {
        this.internshipApplied.add(internship);
    }

    /**
     * Removes an `InternshipApplication` from the applied list.
     *
     * @param internship the `InternshipApplication` to remove
     */
    public void removeInternship(InternshipApplication internship) {
        this.internshipApplied.remove(internship);
    }

    /**
     * Clears all internship applications for the student.
     */
    public void reset() {
        this.internshipApplied.clear();
    }

    /**
     * Returns the internship that has been accepted for the student, if any.
     *
     * @return accepted `Internship` or null if none
     */
    public Internship getInternshipAccepted() {
        return this.internshipAccepted;
    }

    /**
     * Sets the accepted internship for the student.
     *
     * @param internshipAccepted the `Internship` to mark as accepted
     */
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
