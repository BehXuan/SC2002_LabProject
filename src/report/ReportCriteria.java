package src.report;

import java.time.LocalDate;
import src.enums.InternshipStatus;

public class ReportCriteria {
    public enum SortType{TITLE, COMPANY, OPEN_DATE, CLOSE_DATE}

    private String title;
    private String major;
    private String level;
    private LocalDate openDate;
    private LocalDate closeDate;
    private InternshipStatus internshipStatus;
    private String companyName;
    private SortType sortType = SortType.TITLE; 
    public ReportCriteria(){}

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getMajor(){
        return this.major;
    }

    public void setMajor(String major){
        this.major = major;
    }

    public String getLevel(){
        return this.level;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public LocalDate getOpenDate(){
        return this.openDate;
    }
    public void setOpenDate(LocalDate openDate){
        this.openDate = openDate;
    }

    public LocalDate getCloseDate(){
        return this.closeDate;
    }

    public void setCloseDate(LocalDate closeDate){
        this.closeDate = closeDate;
    }

    public InternshipStatus getInternshipStatus(){
        return this.internshipStatus;
    }

    public void setInternshipStatus(InternshipStatus internshipStatus){
        this.internshipStatus = internshipStatus;
    }

    public String getCompanyName(){
        return this.companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public boolean hasTitle(){
        return this.title != null && !title.isEmpty();
    }

    public boolean hasMajor(){
        return this.major != null && !major.isEmpty() ;
    }

    public boolean hasLevel(){
        return this.level != null && !level.isEmpty();
    }

    public boolean hasOpenDate(){
        return this.openDate != null;
    }

    public boolean hasCloseDate(){
        return this.closeDate != null;
    }

    public boolean hasInternshipStatus(){
        return this.internshipStatus != null;
    }

    public boolean hasCompanyName(){
        return this.companyName != null && !companyName.isEmpty();
    }

    public SortType getSortType(){
        return this.sortType;
    }

    public void setSortType(SortType sortType){
        this.sortType = sortType;
    }
}

