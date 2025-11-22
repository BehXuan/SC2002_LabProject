package src.interfaces;

import src.entity.InternshipApplication;

/**
 * Interface for viewing internship application information.
 *
 * <p>This interface defines the contract for displaying internship
 * applications and their associated details. Implementations provide
 * the user interface logic for listing and viewing individual application
 * records.
 */
public interface viewApplication {
    /**
     * Displays a list of all internship applications accessible to the
     * current user. The specific applications shown depend on the
     * implementing class and the user's role/permissions.
     */
    void viewApplications();

    /**
     * Displays detailed information for a specific internship application.
     *
     * @param application the {@link src.entity.InternshipApplication} to display
     */
    void viewApplicationDetails(InternshipApplication application);
}
