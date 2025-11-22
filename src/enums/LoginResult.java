package src.enums;

/**
 * Enumeration representing the result of a login attempt.
 *
 * <p>This enum indicates the outcome of user authentication, providing
 * specific reason codes for success or failure scenarios.
 */
public enum LoginResult {
    /**
     * Login successful. Indicates the user was authenticated and logged
     * in successfully.
     */
    SUCCESS,

    /**
     * User not found. Indicates the provided username or user id does
     * not exist in the system.
     */
    USER_NOT_FOUND,

    /**
     * Wrong password. Indicates the username was found but the provided
     * password is incorrect.
     */
    WRONG_PASSWORD,

    /**
     * User not approved. Indicates the user exists and credentials are
     * correct, but the account has not been approved (e.g., company
     * representative awaiting approval).
     */
    USER_NOT_APPROVED
}
