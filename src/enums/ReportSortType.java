package src.enums;

/**
 * Enumeration representing sorting options for internship reports.
 *
 * <p>This enum defines the available fields by which internship reports
 * can be sorted. Reports can be generated with results ordered by any
 * of these criteria.
 */
public enum ReportSortType {
    /**
     * Sort by internship title in alphabetical order.
     */
    TITLE,

    /**
     * Sort by company name in alphabetical order.
     */
    COMPANY,

    /**
     * Sort by internship opening date in chronological order.
     */
    OPEN_DATE,

    /**
     * Sort by internship closing date in chronological order.
     */
    CLOSE_DATE,

    /**
     * Sort by number of available internship slots in ascending order.
     */
    SLOTS_LEFT
}