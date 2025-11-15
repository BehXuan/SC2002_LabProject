package src.entity;
import java.util.ArrayList;
import java.util.List;
import src.enums.CompanyApprovalStatus;

public class CompanyRepresentative extends User{
    private String companyName;
    private CompanyApprovalStatus companyApprovalStatus;
    private List<Internship> internships; // "pending", "approved","rejected" 
    private int internshipCount; // max 5

    public CompanyRepresentative(){}

    public CompanyRepresentative(String id, String pw, String name,String email, String companyName){
        super(id,pw,name, email);
        this.companyName = companyName;
        this.companyApprovalStatus = CompanyApprovalStatus.PENDING;
        this.internshipCount = 0;
        this.internships = new ArrayList<>();

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

}
