package src.entity;

public class CareerCenterStaff extends User{
    
    private String staffDepartment;

    public CareerCenterStaff(){}

    public CareerCenterStaff(String userId, String password, String staffDepartment) {
        super(userId, password, null);
        this.staffDepartment = staffDepartment;
    }
    // Getters

    public String getStaffDepartment() {
        return staffDepartment;
    }

    // Setters

    public void setStaffDepartment(String staffDepartment) {
        this.staffDepartment = staffDepartment;
    }
}
