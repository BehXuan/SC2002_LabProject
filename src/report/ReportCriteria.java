package src.report;

import java.time.LocalDate;
import src.enums.InternshipLevel;
import src.enums.InternshipStatus;
import src.enums.ReportSortType;

/**
 * Encapsulates criteria for filtering and sorting internship reports.
 *
 * <p>Allows specifying optional filters for title, major, level, company,
 * status, dates, visibility, and minimum slots. Also supports specifying
 * a sort order for results.
 */
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
    /**
     * Sets the title filter (substring match).
     *
     * @param title title to filter by
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Sets the major filter.
     *
     * @param major major to filter by
     */
    public void setMajor(String major) { this.major = major; }

    /**
     * Sets the internship level filter.
     *
     * @param level `InternshipLevel` to filter by
     */
    public void setLevel(InternshipLevel level) { this.level = level; }

    /**
     * Sets the company name filter.
     *
     * @param companyName company name to filter by
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    /**
     * Sets the company representative id filter.
     *
     * @param companyRepId company representative id to filter by
     */
    public void setCompanyRepId(String companyRepId) { this.companyRepId = companyRepId; }

    /**
     * Sets the internship status filter.
     *
     * @param status `InternshipStatus` to filter by
     */
    public void setStatus(InternshipStatus status) { this.status = status; }

    /**
     * Sets the opening date filter (inclusive).
     *
     * @param openDate opening date to filter by
     */
    public void setOpenDate(LocalDate openDate) { this.openDate = openDate; }

    /**
     * Sets the closing date filter (inclusive).
     *
     * @param closeDate closing date to filter by
     */
    public void setCloseDate(LocalDate closeDate) { this.closeDate = closeDate; }

    /**
     * Sets the visibility filter.
     *
     * @param visibility true to include only visible internships, false for hidden, null for all
     */
    public void setVisibility(Boolean visibility) { this.visibility = visibility; }

    /**
     * Sets the minimum slots filter.
     *
     * @param minSlots minimum number of remaining slots
     */
    public void setMinSlots(Integer minSlots) { this.minSlots = minSlots; }

    /**
     * Sets the sort order for report results.
     *
     * @param sortType the `ReportSortType` for sorting
     */
    public void setSortType(ReportSortType sortType) { this.sortType = sortType; }

    // ---------- Helper methods ----------
    /**
     * Checks whether a title filter is set.
     *
     * @return true if title is not null and not empty
     */
    public boolean hasTitle() { return title != null && !title.isEmpty(); }

    /**
     * Checks whether a major filter is set.
     *
     * @return true if major is not null and not empty
     */
    public boolean hasMajor() { return major != null && !major.isEmpty(); }

    /**
     * Checks whether a level filter is set.
     *
     * @return true if level is not null
     */
    public boolean hasLevel() { return level != null; }

    /**
     * Checks whether a company name filter is set.
     *
     * @return true if companyName is not null and not empty
     */
    public boolean hasCompanyName() { return companyName != null && !companyName.isEmpty(); }

    /**
     * Checks whether a company representative id filter is set.
     *
     * @return true if companyRepId is not null and not empty
     */
    public boolean hasCompanyRepId() { return companyRepId != null && !companyRepId.isEmpty(); }

    /**
     * Checks whether an internship status filter is set.
     *
     * @return true if status is not null
     */
    public boolean hasInternshipStatus() { return status != null; }

    /**
     * Checks whether an opening date filter is set.
     *
     * @return true if openDate is not null
     */
    public boolean hasOpenDate() { return openDate != null; }

    /**
     * Checks whether a closing date filter is set.
     *
     * @return true if closeDate is not null
     */
    public boolean hasCloseDate() { return closeDate != null; }

    /**
     * Checks whether a visibility filter is set.
     *
     * @return true if visibility is not null
     */
    public boolean hasVisibility() { return visibility != null; }

    /**
     * Checks whether a minimum slots filter is set.
     *
     * @return true if minSlots is not null
     */
    public boolean hasMinSlots() { return minSlots != null; }

    /**
     * Returns the sort type, defaulting to TITLE if not set.
     *
     * @return the `ReportSortType` or `ReportSortType.TITLE` if none specified
     */
    public ReportSortType getSortType() { return sortType != null ? sortType : ReportSortType.TITLE; }

    // ---------- Getters ----------
    /**
     * Returns the title filter.
     *
     * @return the title to filter by, or null if not set
     */
    public String getTitle() { return title; }

    /**
     * Returns the major filter.
     *
     * @return the major to filter by, or null if not set
     */
    public String getMajor() { return major; }

    /**
     * Returns the level filter.
     *
     * @return the `InternshipLevel` to filter by, or null if not set
     */
    public InternshipLevel getLevel() { return level; }

    /**
     * Returns the company name filter.
     *
     * @return the company name to filter by, or null if not set
     */
    public String getCompanyName() { return companyName; }

    /**
     * Returns the company representative id filter.
     *
     * @return the company representative id to filter by, or null if not set
     */
    public String getCompanyRepId() { return companyRepId; }

    /**
     * Returns the internship status filter.
     *
     * @return the `InternshipStatus` to filter by, or null if not set
     */
    public InternshipStatus getStatus() { return status; }

    /**
     * Returns the opening date filter.
     *
     * @return the opening date to filter by, or null if not set
     */
    public LocalDate getOpenDate() { return openDate; }

    /**
     * Returns the closing date filter.
     *
     * @return the closing date to filter by, or null if not set
     */
    public LocalDate getCloseDate() { return closeDate; }

    /**
     * Returns the visibility filter.
     *
     * @return true for visible only, false for hidden only, null for all
     */
    public Boolean getVisibility() { return visibility; }

    /**
     * Returns the minimum slots filter.
     *
     * @return the minimum number of remaining slots, or null if not set
     */
    public Integer getMinSlots() { return minSlots; }
}
