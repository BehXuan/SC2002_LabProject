package src.interfaces;

import java.util.List;

import src.entity.Internship;
import src.report.ReportCriteria;

/**
 * Interface for generating internship reports based on specified criteria.
 *
 * <p>This interface defines the contract for report generation operations.
 * Implementations provide the ability to filter and retrieve internships
 * based on user-defined criteria such as status, level, major, or other
 * attributes.
 */
public interface IReportGenerator {
    /**
     * Generates a report of internships matching the provided criteria.
     *
     * @param criteria a {@link src.report.ReportCriteria} object containing
     *                 filtering and sorting parameters
     * @return a {@link java.util.List} of {@link src.entity.Internship}
     *         objects that match the specified criteria, or an empty list
     *         if no matches are found
     */
    public List<Internship> generateReport(ReportCriteria criteria);
}
