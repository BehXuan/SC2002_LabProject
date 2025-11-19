package src.report;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import src.entity.Internship;

public class ReportGenerator {

    public List<Internship> generateReport(List<Internship> allInternships, ReportCriteria criteria) {

        return allInternships.stream()
                .filter(i -> !criteria.hasTitle() || i.getTitle().toLowerCase().contains(criteria.getTitle().toLowerCase()))
                .filter(i -> !criteria.hasMajor() || i.getMajor().equalsIgnoreCase(criteria.getMajor()))
                .filter(i -> !criteria.hasLevel() || i.getLevel() == criteria.getLevel())
                .filter(i -> !criteria.hasCompanyName() || i.getCompanyRep().getCompanyName().equalsIgnoreCase(criteria.getCompanyName()))
                .filter(i -> !criteria.hasInternshipStatus() || i.getStatus() == criteria.getInternshipStatus())
                .filter(i -> !criteria.hasOpenDate() || !i.getOpenDate().isBefore(criteria.getOpenDate()))
                .filter(i -> !criteria.hasCloseDate() || !i.getCloseDate().isAfter(criteria.getCloseDate()))
                .sorted(getComparator(criteria))
                .collect(Collectors.toList());
    }

    private Comparator<Internship> getComparator(ReportCriteria criteria) {
        return switch (criteria.getSortType()) {
            case TITLE -> Comparator.comparing(i -> i.getTitle().toLowerCase());
            case COMPANY -> Comparator.comparing(i -> i.getCompanyRep().getCompanyName().toLowerCase());
            case OPEN_DATE -> Comparator.comparing(i -> i.getOpenDate());
            case CLOSE_DATE -> Comparator.comparing(i -> i.getCloseDate());
        };
    }
}
