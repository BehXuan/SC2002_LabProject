package src.report;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import src.entity.Internship;
import src.DataStore;

public class ReportGenerator {

    private DataStore dataStore;

    public ReportGenerator() {
        this.dataStore = DataStore.getInstance();
    }

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
