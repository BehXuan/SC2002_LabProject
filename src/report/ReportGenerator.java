package src.report;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import src.entity.Internship;
import src.DataStore;

/**
 * Generates internship reports by filtering and sorting based on specified criteria.
 *
 * <p>Uses Java streams to apply multiple filters (title, major, level, status,
 * visibility, company, dates, slots) and sorts results by various fields.
 */
public class ReportGenerator {

    private DataStore dataStore;

    /**
     * Constructs the ReportGenerator and acquires the shared `DataStore` instance.
     */
    public ReportGenerator() {
        this.dataStore = DataStore.getInstance();
    }

    /**
     * Generates a filtered and sorted report of internships based on the provided criteria.
     *
     * <p>Filters are applied for title (substring match), major, level, status,
     * visibility, company representative, open/close dates, and minimum slots.
     * Results are sorted according to the specified sort type (defaults to title).
     *
     * @param c the `ReportCriteria` specifying filters and sort order
     * @return a `List<Internship>` matching the criteria and sorted as requested
     */
    public List<Internship> generateReport(ReportCriteria c) {
        return dataStore.getInternshipList().stream()

            // Title contains
            .filter(i -> c.getTitle() == null ||
                         i.getTitle().toLowerCase().contains(c.getTitle().toLowerCase()))

            // Major filter
            .filter(i -> c.getMajor() == null ||
                         i.getMajor().equalsIgnoreCase(c.getMajor()))

            // Level filter
            .filter(i -> c.getLevel() == null ||
                         i.getLevel() == c.getLevel())

            // Status filter
            .filter(i -> c.getStatus() == null ||
                         i.getStatus() == c.getStatus())

            // Visibility filter
            .filter(i -> c.getVisibility() == null ||
                         i.getVisibility() == c.getVisibility())

            // Filter by CompanyRep ID
            .filter(i -> c.getCompanyRepId() == null ||
                (i.getCompanyRep() != null &&
                 i.getCompanyRep().getUserId().equals(c.getCompanyRepId())))

            // Open date filter
            .filter(i -> c.getOpenDate() == null ||
                         !i.getOpenDate().isBefore(c.getOpenDate()))

            // Close date filter
            .filter(i -> c.getCloseDate() == null ||
                         !i.getCloseDate().isAfter(c.getCloseDate()))

            // Slots >= minSlots
            .filter(i -> c.getMinSlots() == null ||
                         i.getNumberOfSlotsLeft() >= c.getMinSlots())

            // Sorting
            .sorted(getComparator(c))

            .collect(Collectors.toList());
    }

    /**
     * Provides a comparator for sorting internships based on the specified sort type.
     *
     * <p>Supports sorting by title (default), company name, open date, close date,
     * or remaining slots. Comparisons are case-insensitive for text fields.
     *
     * @param c the `ReportCriteria` specifying the desired sort type
     * @return a `Comparator<Internship>` for the requested sort order
     */
    private Comparator<Internship> getComparator(ReportCriteria c) {
        if (c.getSortType() == null) return Comparator.comparing(i -> i.getTitle().toLowerCase());

        return switch (c.getSortType()) {
            case TITLE -> Comparator.comparing(i -> i.getTitle().toLowerCase());
            case COMPANY -> Comparator.comparing(i -> i.getCompanyRep().getCompanyName().toLowerCase());
            case OPEN_DATE -> Comparator.comparing(Internship::getOpenDate);
            case CLOSE_DATE -> Comparator.comparing(Internship::getCloseDate);
            case SLOTS_LEFT -> Comparator.comparingInt(Internship::getNumberOfSlotsLeft);
        };
    }
}
