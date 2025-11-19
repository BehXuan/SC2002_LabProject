package src.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import src.enums.InternshipStatus;
import src.enums.InternshipLevel;

public class Internship {
    private int internshipId;
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

    public Internship() {
    }

    public Internship(int internshipId, String title, String description, InternshipLevel internshipLevel, String major,
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

    public CompanyRepresentative getCompanyRep() {
        return this.companyRep;
    }

    public void setCompanyRep(CompanyRepresentative companyRep) {
        this.companyRep = companyRep;
    }

    public int getInternshipId() {
        return this.internshipId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InternshipLevel getLevel() {
        return this.internshipLevel;
    }

    public void setLevel(InternshipLevel internshipLevel) {
        this.internshipLevel = internshipLevel;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public LocalDate getOpenDate() {
        return this.openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getCloseDate() {
        return this.closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public InternshipStatus getStatus() {
        return this.internshipStatus;
    }

    public void setStatus(InternshipStatus internshipStatus) {
        this.internshipStatus = internshipStatus;
    }

    public int getNumberOfSlotsLeft() {
        return this.numberOfSlotsLeft;
    }

    public void setNumberOfSlotsLeft(int numberOfSlotsLeft) {
        this.numberOfSlotsLeft = numberOfSlotsLeft;
    }

    public boolean getVisibility() {
        return this.visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isAvailable() {
        if (LocalDate.now().isAfter(openDate) && LocalDate.now().isBefore(closeDate) && numberOfSlotsLeft > 0) {
            return true;
        }

        return false;
    }

    public List<Student> getApplicants() {
        return applicants;
    }

    public void addApplicant(Student student) {
        applicants.add(student);
        this.numberOfSlotsLeft--;
    }

    public void removeApplicant(Student student) {
        applicants.remove(student);
        this.numberOfSlotsLeft++;
    }
}
