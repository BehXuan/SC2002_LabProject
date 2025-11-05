package src;
import src.entity.Student;
import src.entity.CareerCenterStaff;
import src.entity.CompanyRepresentative;
import java.util.ArrayList;


// change the required parameters
// change the init to copy the data from csv or smt

public class DataStore {
    private ArrayList<Student> studentList;
    private ArrayList<CompanyRepresentative> companyRepresentativeList;
    private ArrayList<CareerCenterStaff> careerCenterStaffList;

    public DataStore(){
        // Initialize empty ArrayLists
        this.studentList = new ArrayList<>();
        this.companyRepresentativeList = new ArrayList<>();
        this.careerCenterStaffList = new ArrayList<>();
    }
    
    public ArrayList<Student> getStudentList() {
        return this.studentList;
    }

    public ArrayList<CompanyRepresentative> getCompanyRepresentativeList() {
        return this.companyRepresentativeList;
    }

    public ArrayList<CareerCenterStaff> getCareerCenterStaffList() {
        return this.careerCenterStaffList;
    }
    
    public void studentAdd(String name) {
        this.studentList.add(new Student());
    }

    public void CompanyRepresentativeAdd(String name) {
        this.companyRepresentativeList.add(new CompanyRepresentative());
    }

    public void CareerCenterStaffAdd(String name) {
        this.careerCenterStaffList.add(new CareerCenterStaff());
    }
}
