package src.entity;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepresentative extends User{
    private String companyName;
    private String approved;
    private List<Internship> internships; // "pending", "approved","rejected" 
    private int internshipCount; // max 5

    public CompanyRepresentative(){}

    public CompanyRepresentative(String id, String pw, String name,String companyName){
        super(id,pw,name);
        this.companyName = companyName;
        this.approved = "Pending";
        this.internshipCount = 0;
        this.internships = new ArrayList<>();

    }

    //Getter functions
    public String getCompanyName(){
        return companyName;
    }

    public String getApproval(){
        return approved;
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

    public void setApproval(String approval){
        this.approved = approval;
    }

    public void setInternshipCount(int count){
        this.internshipCount = count;
    }

    public void setInternships(List<Internship> internships) {
        this.internships = internships;
    }

}
