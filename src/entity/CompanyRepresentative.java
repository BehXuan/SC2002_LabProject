package src.entity;
import java.util.ArrayList;
import java.util.List;
import src.enums.CompanyApprovalStatus;

public class CompanyRepresentative extends User {
    private String companyName;
    private CompanyApprovalStatus companyApprovalStatus;  // "pending", "approved","rejected"
    private List<Internship> internships;  
    private int internshipCount; // max 5
    private String position;
    private String department;

    // public CompanyRepresentative(){}

    public CompanyRepresentative(String id, String pw, String name,String email, String companyName,String department,String position){
        super(id,pw,name, email);
        this.companyName = companyName;
        this.companyApprovalStatus = CompanyApprovalStatus.PENDING;
        this.internshipCount = 0;
        this.internships = new ArrayList<>();
        this.position = position;
        this.department = department;

    }

    //Getter functions
    public String getCompanyName(){
        return companyName;
    }

    public CompanyApprovalStatus getApproval(){
        return companyApprovalStatus;
    }

    public int getInternshipCount(){
        return internshipCount;
    }

    public List<Internship> getInternships(){
        return internships;
    }

    public String getDepartment(){
        return this.department;
    }

    public String getPosition(){
        return this.position;
    }

    //Setter functions
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public void setApproval(CompanyApprovalStatus approval){
        this.companyApprovalStatus = approval;
    }

    public void setInternshipCount(int count){
        this.internshipCount = count;
    }

    public void setInternships(List<Internship> internships) {
        this.internships = internships;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public void setPosition(String position){
        this.position = position;
    }

    @Override
    public String toString() {
        List<Internship> allInternships = getInternships();
        List<String> internshipTitles = new ArrayList<>();
        for(Internship internship : allInternships){
            internshipTitles.add(internship.getTitle());
        }

        return "CompanyRepresentative{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", companyName='" + getCompanyName() + '\'' +
                ", companyApprovalStatus='" + getApproval() + '\'' +
                ", internshipCount=" + getInternshipCount() + '\'' +
                ", internships=" + String.join(",", internshipTitles) + '\'' +
                ", position='" + getPosition() + '\'' +
                ", department='" + getDepartment() + '\'' +
                '}';
    }
}
