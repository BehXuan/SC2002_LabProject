package src.entity;

public class CareerCenterStaff extends User{
    
    private String staffDepartment;
    private String role;
    public CareerCenterStaff(){}

    public CareerCenterStaff(String userId, String password, String name, String email, String role, String staffDepartment) {
        super(userId, password, name, email);
        this.staffDepartment = staffDepartment;
        this.role = role;
    }
    // Getters

    public String getStaffDepartment() {
        return staffDepartment;
    }

    // Setters

    public void setStaffDepartment(String staffDepartment) {
        this.staffDepartment = staffDepartment;
    }

    public String getStaffRole(){
        return this.role;
    }

    public void setStaffRole(String role){
        this.role = role;
    }

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
