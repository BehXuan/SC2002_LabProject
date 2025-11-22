package src.entity;

/**
 * Represents a career center staff member who extends the base `User`.
 *
 * <p>
 * Career center staff have a department and a role and are typically
 * responsible for administrative actions within the career center scope.
 */
public class CareerCenterStaff extends User {

    private String staffDepartment;
    private String role;

    /**
     * Constructs a CareerCenterStaff with the provided user and staff details.
     *
     * @param userId          unique user id
     * @param password        account password
     * @param name            full name
     * @param email           contact email
     * @param role            staff role/title
     * @param staffDepartment department the staff belongs to
     */
    public CareerCenterStaff(String userId, String password, String name, String email, String role,
            String staffDepartment) {
        super(userId, password, name, email);
        this.staffDepartment = staffDepartment;
        this.role = role;
    }

    /**
     * Returns the department of the staff member.
     *
     * @return staff department
     */
    public String getStaffDepartment() {
        return staffDepartment;
    }

    /**
     * Sets the department of the staff member.
     *
     * @param staffDepartment department to set
     */
    public void setStaffDepartment(String staffDepartment) {
        this.staffDepartment = staffDepartment;
    }

    /**
     * Returns the staff role/title.
     *
     * @return staff role
     */
    public String getStaffRole() {
        return this.role;
    }

    /**
     * Sets the staff role/title.
     *
     * @param role role/title to set
     */
    public void setStaffRole(String role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the CareerCenterStaff.
     *
     * @return human-readable representation including user id, name, email,
     *         department and role
     */
    @Override
    public String toString() {
        return "CareerCenterStaff{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", staffDepartment='" + getStaffDepartment() + '\'' +
                ", role='" + getStaffRole() + '\'' +
                '}';
    }
}
