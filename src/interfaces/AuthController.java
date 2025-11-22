package src.interfaces;
import src.enums.LoginResult;

/**
 * Interface for authentication control across all user types.
 *
 * <p>This interface defines the contract for authentication operations
 * such as login, logout, and password updates. Implementations of this
 * interface are used by specific controller classes for Students,
 * Company Representatives, and Career Center Staff.
 */
public interface AuthController {
    /**
     * Authenticates a user with the provided credentials.
     *
     * @param userName the username or user id to authenticate
     * @param pw the password for authentication
     * @return a {@link src.enums.LoginResult} enum indicating the outcome
     *         of the login attempt (e.g., SUCCESS, FAILURE, INVALID_CREDENTIALS)
     */
    LoginResult login(String userName, String pw);

    /**
     * Logs out the currently authenticated user, clearing any active
     * session or authentication state.
     */
    void logout();

    /**
     * Updates the password for the currently authenticated user.
     *
     * @param oldPW the current password (for verification)
     * @param newPW the new password to set
     * @return {@code true} if the password was successfully updated,
     *         {@code false} if the old password was incorrect or the
     *         update failed for another reason
     */
    boolean updatePassword(String oldPW, String newPW);
}

