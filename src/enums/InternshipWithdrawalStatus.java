package src.enums;

/**
 * Enumeration representing the status of an internship withdrawal request.
 *
 * <p>This enum tracks the state of a student's attempt to withdraw from
 * an accepted internship. Withdrawal requests follow an approval workflow
 * before they can be finalized.
 */
public enum InternshipWithdrawalStatus {
    /**
     * No withdrawal status. Indicates the student has not requested
     * to withdraw from the internship.
     */
    NONE,

    /**
     * Pending withdrawal status. Indicates the student has requested
     * to withdraw but the request is awaiting approval from the company.
     */
    PENDING,

    /**
     * Approved withdrawal status. Indicates the company has approved
     * the student's withdrawal request and the internship is officially
     * terminated.
     */
    APPROVED,

    /**
     * Rejected withdrawal status. Indicates the company has rejected
     * the student's withdrawal request and the internship commitment
     * remains in effect.
     */
    REJECTED
}
