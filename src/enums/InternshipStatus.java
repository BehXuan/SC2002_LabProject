package src.enums;

/**
 * Enumeration representing the status of an internship or internship application.
 *
 * <p>This enum tracks the state progression of internships and related
 * decisions made by students and company representatives during the
 * application and acceptance process.
 */
public enum InternshipStatus {
    /**
     * Pending status. Indicates the internship or application is awaiting
     * a decision or response from the relevant party (e.g., company or student).
     */
    PENDING,

    /**
     * Approved status. Indicates the internship has been accepted or the
     * application has been approved by the company or student.
     */
    APPROVED,

    /**
     * Rejected status. Indicates the internship posting has been closed
     * or the application has been rejected by the company or declined
     * by the student.
     */
    REJECTED
}
