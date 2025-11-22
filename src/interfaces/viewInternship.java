package src.interfaces;

import src.entity.Internship;

/**
 * Interface for viewing internship information.
 *
 * <p>This interface defines the contract for displaying internships and
 * their associated details. Implementations provide the user interface
 * logic for listing and viewing individual internship records based on
 * user role and permissions.
 */
public interface viewInternship {
    /**
     * Displays a list of internships accessible to the current user.
     * The specific internships shown depend on the implementing class
     * and the user's role (e.g., students see available internships,
     * staff may see all internships).
     */
    void viewInternships();

    /**
     * Displays detailed information for a specific internship.
     *
     * @param internship the {@link src.entity.Internship} to display details for
     */
    void viewInternshipDetails(Internship internship);
}