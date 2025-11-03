package src;
import src.entity.User;
import java.util.ArrayList;


// remember to refractor all user to the correct class
// change the required parameters

public class DataStore {
    private ArrayList<User> studentList;
    private ArrayList<User> companyRepresentativeList;
    private ArrayList<User> careerCenterStaffList;

    public DataStore(){
        // Initialize empty ArrayLists
        this.studentList = new ArrayList<>();
        this.companyRepresentativeList = new ArrayList<>();
        this.careerCenterStaffList = new ArrayList<>();
    }
    
    public ArrayList<User> getStudentList() {
        return this.studentList;
    }

    public ArrayList<User> getCompanyRepresentativeList() {
        return this.companyRepresentativeList;
    }

    public ArrayList<User> getCareerCenterStaffList() {
        return this.careerCenterStaffList;
    }
    
    public void studentAdd(String name) {
        this.studentList.add(new User(null, null, name));
    }

    public void CompanyRepresentativeAdd(String name) {
        this.companyRepresentativeList.add(new User(null, null, name));
    }

    public void CareerCenterStaffAdd(String name) {
        this.careerCenterStaffList.add(new User(null, null, name));
    }
}
