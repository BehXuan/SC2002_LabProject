package src.report;

import java.time.LocalDate;
import src.enums.InternshipLevel;
import src.enums.InternshipStatus;
import src.enums.ReportSortType;

public class ReportCriteria {

    private String title;
    private String major;
    private InternshipLevel level;
    private String companyName;
    private String companyRepId;
    private InternshipStatus status;
    private LocalDate openDate;
    private LocalDate closeDate;
    private Boolean visibility;
    private Integer minSlots; // Minimum number of slots left
    private ReportSortType sortType;

    // ---------- Setters ----------
    public void setTitle(String title) { this.title = title; }
    public void setMajor(String major) { this.major = major; }
    public void setLevel(InternshipLevel level) { this.level = level; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setCompanyRepId(String companyRepId) { this.companyRepId = companyRepId; }
    public void setStatus(InternshipStatus status) { this.status = status; }
    public void setOpenDate(LocalDate openDate) { this.openDate = openDate; }
    public void setCloseDate(LocalDate closeDate) { this.closeDate = closeDate; }
    public void setVisibility(Boolean visibility) { this.visibility = visibility; }
    public void setMinSlots(Integer minSlots) { this.minSlots = minSlots; }
    public void setSortType(ReportSortType sortType) { this.sortType = sortType; }

    // ---------- Helper methods ----------
    public boolean hasTitle() { return title != null && !title.isEmpty(); }
    public boolean hasMajor() { return major != null && !major.isEmpty(); }
    public boolean hasLevel() { return level != null; }
    public boolean hasCompanyName() { return companyName != null && !companyName.isEmpty(); }
    public boolean hasCompanyRepId() { return companyRepId != null && !companyRepId.isEmpty(); }
    public boolean hasInternshipStatus() { return status != null; }
    public boolean hasOpenDate() { return openDate != null; }
    public boolean hasCloseDate() { return closeDate != null; }
    public boolean hasVisibility() { return visibility != null; }
    public boolean hasMinSlots() { return minSlots != null; }

    public ReportSortType getSortType() { return sortType != null ? sortType : ReportSortType.TITLE; }

    // ---------- Getters ----------
    public String getTitle() { return title; }
    public String getMajor() { return major; }
    public InternshipLevel getLevel() { return level; }
    public String getCompanyName() { return companyName; }
    public String getCompanyRepId() { return companyRepId; }
    public InternshipStatus getStatus() { return status; }
    public LocalDate getOpenDate() { return openDate; }
    public LocalDate getCloseDate() { return closeDate; }
    public Boolean getVisibility() { return visibility; }
    public Integer getMinSlots() { return minSlots; }
}
