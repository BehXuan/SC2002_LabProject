package src;

import src.entity.Student;
import src.entity.CareerCenterStaff;
import src.entity.CompanyRepresentative;
import src.entity.Internship;
import src.entity.InternshipApplication;
import java.util.ArrayList;
import src.enums.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class DataStore {
    private static DataStore instance;
    private ArrayList<Student> studentList;
    private ArrayList<CompanyRepresentative> companyRepresentativeList;
    private ArrayList<CareerCenterStaff> careerCenterStaffList;
    private ArrayList<Internship> internshipList;
    private ArrayList<InternshipApplication> internshipApplicationsList;

    private DataStore() {
        // Initialize empty ArrayLists
        this.studentList = new ArrayList<>();
        this.companyRepresentativeList = new ArrayList<>();
        this.careerCenterStaffList = new ArrayList<>();
        this.internshipList = new ArrayList<>();
        this.internshipApplicationsList = new ArrayList<>();

        loadInitialData();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void loadInitialData() {
        loadStudentsFromCSV("src\\csvFiles\\sample_student_list.csv");
        loadStaffFromCSV("src\\csvFiles\\sample_staff_list.csv");
        loadCompanyRepsFromCSV("src\\csvFiles\\sample_company_representative_list.csv");
        
        System.out.println("DataStore initialized with:");
        System.out.println("- " + studentList.size() + " students");
        System.out.println("- " + careerCenterStaffList.size() + " staff members");
        System.out.println("- " + companyRepresentativeList.size() + " company representatives");


    }


    private void loadStudentsFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String studentId = data[0].trim();
                    String name = data[1].trim();
                    String major = data[2].trim();
                    int yearOfStudy = Integer.parseInt(data[3].trim());
                    String email = data[4].trim();
                    
                    // Default password is "password" as per requirements
                    Student student = new Student(studentId, "password", name, email, yearOfStudy, major);
                    studentList.add(student);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load student data from " + filename);
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Warning: Invalid year format in student data");
        }
    }

    private void loadStaffFromCSV(String filename) {
         try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
             String line;
             boolean isFirstLine = true;
            
             while ((line = br.readLine()) != null) {
                 if (isFirstLine) {
                     isFirstLine = false;
                     continue; // Skip header
                 }
                
                 String[] data = line.split(",");
                 if (data.length >= 5) {
                     String staffId = data[0].trim();
                    String name = data[1].trim();
                     String role = data[2].trim();
                     String department = data[3].trim();
                     String email = data[4].trim();
                    
                     // Default password is "password" as per requirements
                     CareerCenterStaff staff = new CareerCenterStaff(staffId, "password", name, email,role,department);
                     careerCenterStaffList.add(staff);
                 }
             }
         } catch (IOException e) {
             System.out.println("Warning: Could not load staff data from " + filename);
             System.out.println("Error: " + e.getMessage());
         }
     }

    private void loadCompanyRepsFromCSV(String filename) {
         try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
             String line;
             boolean isFirstLine = true;
            
             while ((line = br.readLine()) != null) {
                 if (isFirstLine) {
                     isFirstLine = false;
                     continue; // Skip header
                 }
                
                 String[] data = line.split(",");
                 if (data.length >= 7) {
                     String repId = data[0].trim();
                     String name = data[1].trim();
                     String companyName = data[2].trim();
                     String department = data[3].trim();
                     String position = data[4].trim();
                     String email = data[5].trim();
                     String status = data[6].trim();
                    
                     // Default password is "password" as per requirements
                     CompanyRepresentative rep = new CompanyRepresentative(repId, "password", name, email, companyName, department, position);
                    
    //                 // Set approval status based on CSV
                     if ("APPROVED".equalsIgnoreCase(status) || status == null) {
                         rep.setApproval(CompanyApprovalStatus.APPROVED);
                     } else if ("REJECTED".equalsIgnoreCase(status)){
                         rep.setApproval(CompanyApprovalStatus.REJECTED);
                     }else{
                        rep.setApproval(CompanyApprovalStatus.PENDING);
                     }
                    
                     companyRepresentativeList.add(rep);
                 }
             }
         } catch (IOException e) {
             System.out.println("Warning: Could not load company representative data from " + filename);
             System.out.println("Error: " + e.getMessage());
         }
     }
    // GETTERS

    public ArrayList<Student> getStudentList() {
        return this.studentList;
    }

    public ArrayList<CompanyRepresentative> getCompanyRepresentativeList() {
        return this.companyRepresentativeList;
    }

    public ArrayList<CareerCenterStaff> getCareerCenterStaffList() {
        return this.careerCenterStaffList;
    }

    public ArrayList<Internship> getInternshipList() {
        return this.internshipList;
    }

    public ArrayList<InternshipApplication> getInternshipApplicationsList() {
        return this.internshipApplicationsList;
    }

    // SETTERS
    public void studentAdd(String name) {  // might need redo
        this.studentList.add(new Student());
    }

    public void CompanyRepresentativeAdd(CompanyRepresentative rep) {
        this.companyRepresentativeList.add(rep);
    }

    public void CareerCenterStaffAdd(String name) {
        this.careerCenterStaffList.add(new CareerCenterStaff());
    }

    public void InternshipAdd() {
        this.internshipList.add(new Internship());
    }

    public void InternshipApplicationAdd() {
        this.internshipApplicationsList.add(new InternshipApplication());
    }

    public void addInternship(Internship internship) {
        internshipList.add(internship);
    }

    // FINDERS: USED TO FIND WHETHER USERID EXISTs WITHIN RESP DATASTORE

    public CompanyRepresentative findCompanyRep(String repId) {
        for (CompanyRepresentative rep : companyRepresentativeList) {
            if (rep.getUserId().equals(repId)) {
                return rep;
            }
        }
        return null;
    }

    public Student findStudent(String studentId) {
        for (Student s : studentList) {
            if (s.getUserId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }

    public Internship findInternship(int internshipId) {
        for (Internship i : internshipList) {
            if (i.getInternshipId() == internshipId) {
                return i;
            }
        }
        return null;
    }

}
