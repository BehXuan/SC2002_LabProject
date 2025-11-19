package src;

import src.entity.Student;
import src.entity.CareerCenterStaff;
import src.entity.CompanyRepresentative;
import src.entity.Internship;
import src.entity.InternshipApplication;
import java.util.ArrayList;
import java.util.List;

import src.enums.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;




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
        loadInternshipsFromCSV("src\\csvFiles\\sample_internship_list.csv");
        loadApplicationsFromCSV("src\\csvFiles\\sample_internship_applications.csv");

        System.out.println("DataStore initialized with:");
        System.out.println("- " + studentList.size() + " students");
        System.out.println("- " + careerCenterStaffList.size() + " staff members");
        System.out.println("- " + companyRepresentativeList.size() + " company representatives");
        System.out.println("- " + internshipList.size() + " Internships");
        System.out.println("- " + internshipApplicationsList.size() + " Internship applications");
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
                    
                    String password = (data.length >= 6) ? data[5].trim() : "password";
                    // Default password is "password" as per requirements
                    Student student = new Student(studentId, password, name, email, yearOfStudy, major);
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
                    
                     String password = (data.length >= 6) ? data[5].trim() : "password";
                     // Default password is "password" as per requirements
                     CareerCenterStaff staff = new CareerCenterStaff(staffId, password, name, email,role,department);
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
                    
                     String password = (data.length >= 8) ? data[7].trim() : "password";
                     // Default password is "password" as per requirements
                     CompanyRepresentative rep = new CompanyRepresentative(repId, password, name, email, companyName, department, position);
                    
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

    private void loadInternshipsFromCSV(String filename) {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        boolean isFirstLine = true;
        while ((line = br.readLine()) != null) {
            if (isFirstLine) { isFirstLine = false; continue; }

            // Split CSV while respecting quoted commas
            String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (data.length < 12) continue;

            int id = Integer.parseInt(data[0].trim());
            String title = data[1].trim();
            String description = data[2].trim();
            if (description.startsWith("\"") && description.endsWith("\"")) {
                description = description.substring(1, description.length()-1);
            }
            InternshipLevel level = InternshipLevel.valueOf(data[3].trim());
            String major = data[4].trim();
            LocalDate open = LocalDate.parse(data[5].trim());
            LocalDate close = LocalDate.parse(data[6].trim());
            int slots = Integer.parseInt(data[7].trim());
            String repId = data[8].trim();
            InternshipStatus status = InternshipStatus.valueOf(data[9].trim());
            boolean visibility = Boolean.parseBoolean(data[10].trim());
            String applicantsStr = data[11].trim();

            CompanyRepresentative rep = findCompanyRep(repId);
            if (rep != null) {
                Internship internship = new Internship(id, title, description, level, major, open, close, slots, rep);
                internship.setStatus(status);
                internship.setVisibility(visibility);

                if (!applicantsStr.isEmpty()) {
                    String[] studentIds = applicantsStr.split(";");
                    for (String sid : studentIds) {
                        Student s = findStudent(sid);
                        if (s != null) internship.addApplicant(s);
                    }
                }
                internshipList.add(internship);
                rep.getInternships().add(internship);
                rep.setInternshipCount(rep.getInternships().size());
            }
        }
    } catch (Exception e) {
        System.out.println("Error loading internships: " + e.getMessage());
        e.printStackTrace();
    }
}


    private void loadApplicationsFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }
                String[] data = line.split(",");
                if (data.length >= 7) {
                    int appId = Integer.parseInt(data[0].trim());
                    String studentId = data[1].trim();
                    int internshipId = Integer.parseInt(data[2].trim());
                    String repId = data[3].trim();
                    InternshipStatus companyAccept = InternshipStatus.valueOf(data[4].trim());
                    InternshipStatus studentAccept = InternshipStatus.valueOf(data[5].trim());
                    InternshipWithdrawalStatus studentWithdraw = InternshipWithdrawalStatus.valueOf(data[6].trim());

                    Student student = findStudent(studentId);
                    Internship internship = findInternship(internshipId);
                    CompanyRepresentative rep = findCompanyRep(repId);

                    if (student != null && internship != null && rep != null) {
                        InternshipApplication app = new InternshipApplication(appId, rep, student, internship);
                        app.setCompanyAccept(companyAccept);
                        app.setStudentAccept(studentAccept);
                        app.setInternshipWithdrawalStatus(studentWithdraw);

                        internshipApplicationsList.add(app);

                        student.applyInternship(app);
                        // internship.addApplicant(student);
                        if (!rep.getInternships().contains(internship)) {
                            rep.getInternships().add(internship);
                            rep.setInternshipCount(rep.getInternships().size());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading applications: " + e.getMessage());
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

    public ArrayList<Internship> getAllInternships() {
        return internshipList; 
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

    public int getNextInternshipId(){
        return internshipList.size();
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

    public void saveStudents(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("ID,Name,Major,Year,Email,Password");
            for (Student s : studentList) {
                pw.printf("%s,%s,%s,%d,%s,%s\n",
                    s.getUserId(), s.getName(), s.getMajor(), s.getYearOfStudy(), s.getEmail(), s.getPassword());
            }
        } catch (Exception e) { System.out.println("Error saving students: " + e.getMessage()); }
    }

    public void saveStaff(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("ID,Name,Role,Department,Email,Password");
            for (CareerCenterStaff s : careerCenterStaffList) {
                pw.printf("%s,%s,%s,%s,%s,%s\n",
                    s.getUserId(), s.getName(), s.getStaffRole(), s.getStaffDepartment(), s.getEmail(), s.getPassword());
            }
        } catch (Exception e) { System.out.println("Error saving staff: " + e.getMessage()); }
    }

    public void saveCompanyReps(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("ID,Name,CompanyName,Department,Position,Email,Status,Password");
            for (CompanyRepresentative rep : companyRepresentativeList) {
                pw.printf("%s,%s,%s,%s,%s,%s,%s,%s\n",
                    rep.getUserId(), rep.getName(), rep.getCompanyName(), rep.getDepartment(),
                    rep.getPosition(), rep.getEmail(), rep.getApproval().name(), rep.getPassword());
            }
        } catch (Exception e) { System.out.println("Error saving company reps: " + e.getMessage()); }
    }

    public void saveInternships(String filename) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
        pw.println("ID,Title,Description,Level,Major,OpenDate,CloseDate,Slots,RepID,Status,Visibility,Applicants");
        for (Internship i : internshipList) {
            String applicantsStr = String.join(";", 
                i.getApplicants().stream().map(Student::getUserId).toArray(String[]::new)
            );

            pw.printf("%d,%s,%s,%s,%s,%s,%s,%d,%s,%s,%b,%s\n",i.getInternshipId(),i.getTitle(),i.getDescription(),i.getLevel().name(),i.getMajor(),i.getOpenDate(),i.getCloseDate(),i.getNumberOfSlotsLeft(),i.getCompanyRep().getUserId(),i.getStatus().name(),i.getVisibility(),
                applicantsStr
            );
        }
    } catch (Exception e) {
        System.out.println("Error saving internships: " + e.getMessage());
    }
}


    public void saveApplications(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("AppID,StudentID,InternshipID,RepID,CompanyAccept,StudentAccept,StudentWithdraw");
            for (InternshipApplication app : internshipApplicationsList) {
                pw.printf("%d,%s,%d,%s,%s,%s,%s\n",
                    app.getApplicationId(),
                    app.getStudent().getUserId(),
                    app.getInternship().getInternshipId(),
                    app.getCompanyRep().getUserId(),
                    app.getCompanyAccept().name(),
                    app.getStudentAccept().name(),
                    app.getInternshipWithdrawalStatus().name()
                );
            }
        } catch (Exception e) { System.out.println("Error saving applications: " + e.getMessage()); }
    }

    // Convenience: Save everything at once
    public void saveAll() {
        saveStudents("src\\csvFiles\\sample_student_list.csv");
        saveStaff("src\\csvFiles\\sample_staff_list.csv");
        saveCompanyReps("src\\csvFiles\\sample_company_representative_list.csv");
        saveInternships("src\\csvFiles\\sample_internship_list.csv");
        saveApplications("src\\csvFiles\\sample_internship_applications.csv");
        System.out.println("All data saved to CSV.");
    }


}
