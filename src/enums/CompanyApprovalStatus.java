package src.enums;

/**
 * Enumeration representing the approval status of a company representative.
 *
 * <p>This enum tracks whether a company representative account has been
 * approved by career center staff to participate in the internship system.
 * New company representative registrations must be approved before they
 * can post internships or access the system.
 */
public enum CompanyApprovalStatus {
    /**
     * Pending approval status. Indicates the company representative account
     * is awaiting review and approval by career center staff.
     */
    PENDING,

    /**
     * Approved status. Indicates the company representative has been approved
     * and may post internships and participate in the system.
     */
    APPROVED,

    /**
     * Rejected status. Indicates the company representative application has
     * been rejected and the account cannot access the system.
     */
    REJECTED
}
