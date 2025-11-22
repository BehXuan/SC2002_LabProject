package src;

import src.entity.Student;
import src.entity.CareerCenterStaff;
import src.entity.CompanyRepresentative;
import src.entity.Internship;
import src.entity.InternshipApplication;
import java.util.ArrayList;
import src.enums.CompanyApprovalStatus;
import src.enums.InternshipLevel;
import src.enums.InternshipStatus;
import src.enums.InternshipWithdrawalStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;

/**
 * Singleton in-memory repository for application data.
 *
 * <p>
 * This class maintains lists of students, company representatives,
 * career center staff, internships, and internship applications. On
 * first access, the singleton loads initial data from CSV files found
 * under {@code src/csvFiles}. It also provides convenience methods to
 * find entities by id and to persist the current in-memory state back
 * to CSV files.
 *
 * <p>
 * Usage: call {@link #getInstance()} to obtain the singleton
 * instance, then use provided getters, finders, adders, and save
 * methods to operate on the data.
 */
public class DataStore {
    private static DataStore instance;
    private ArrayList<Student> studentList;
    private ArrayList<CompanyRepresentative> companyRepresentativeList;
    private ArrayList<CareerCenterStaff> careerCenterStaffList;
    private ArrayList<Internship> internshipList;
    private ArrayList<InternshipApplication> internshipApplicationsList;

    /**
     * Private constructor for the singleton DataStore class.
     * Initializes all internal ArrayLists and triggers the initial
     * CSV load to populate the repository with sample data.
     */
    private DataStore() {
        // Initialize empty ArrayLists
        this.studentList = new ArrayList<>();
        this.companyRepresentativeList = new ArrayList<>();
        this.careerCenterStaffList = new ArrayList<>();
        this.internshipList = new ArrayList<>();
        this.internshipApplicationsList = new ArrayList<>();

        loadInitialData();
    }

    /**
     * Returns the singleton instance of the DataStore. The first call
     * will create the instance and load initial CSV data.
     *
     * @return the singleton {@code DataStore} instance
     */
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    /**
     * Load all initial data from CSV files during singleton initialization.
     * Calls each CSV loader method in sequence and prints a summary of
     * loaded entity counts to the console.
     */
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

    /**
     * Load students from a CSV file into the internal student list.
     * The CSV is expected to contain a header row which will be
     * ignored. Missing passwords default to "password".
     *
     * @param filename path to the student CSV file
     */
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

    /**
     * Load career center staff records from a CSV file into the
     * internal staff list. Header row is ignored; missing password
     * values default to "password".
     *
     * @param filename path to the staff CSV file
     */
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
                    CareerCenterStaff staff = new CareerCenterStaff(staffId, password, name, email, role, department);
                    careerCenterStaffList.add(staff);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load staff data from " + filename);
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Load company representative records from a CSV file into the
     * internal company representative list. The CSV may include an
     * approval status column that is mapped to
     * {@link src.enums.CompanyApprovalStatus}.
     *
     * @param filename path to the company representative CSV file
     */
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
                    CompanyRepresentative rep = new CompanyRepresentative(repId, password, name, email, companyName,
                            department, position);

                    // // Set approval status based on CSV
                    if ("APPROVED".equalsIgnoreCase(status) || status == null) {
                        rep.setApproval(CompanyApprovalStatus.APPROVED);
                    } else if ("REJECTED".equalsIgnoreCase(status)) {
                        rep.setApproval(CompanyApprovalStatus.REJECTED);
                    } else {
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

    /**
     * Load internships from a CSV file. This method respects quoted
     * fields (commas inside quoted descriptions) and will attach
     * applicants to internships when present.
     *
     * @param filename path to the internships CSV file
     */
    private void loadInternshipsFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Split CSV while respecting quoted commas
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (data.length < 12)
                    continue;

                String id = data[0].trim();
                String title = data[1].trim();
                String description = data[2].trim();
                if (description.startsWith("\"") && description.endsWith("\"")) {
                    description = description.substring(1, description.length() - 1);
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
                    Internship internship = new Internship(id, title, description, level, major, open, close, slots,
                            rep);
                    internship.setStatus(status);
                    internship.setVisibility(visibility);

                    if (!applicantsStr.isEmpty()) {
                        String[] studentIds = applicantsStr.split(";");
                        for (String sid : studentIds) {
                            Student s = findStudent(sid);
                            if (s != null)
                                internship.addApplicant(s);
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

    /**
     * Load internship application records from a CSV file and populate
     * the internal application list. The method will connect each
     * application with existing student, internship and company
     * representative objects if they can be found.
     *
     * @param filename path to the applications CSV file
     */
    private void loadApplicationsFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length >= 7) {
                    String appId = data[0].trim();
                    String studentId = data[1].trim();
                    String internshipId = data[2].trim();
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

    /**
     * Returns the list of students currently in memory.
     *
     * @return list of {@link src.entity.Student}
     */
    // GETTERS
    public ArrayList<Student> getStudentList() {
        return this.studentList;
    }

    /**
     * Returns the list of company representatives.
     *
     * @return list of {@link src.entity.CompanyRepresentative}
     */
    public ArrayList<CompanyRepresentative> getCompanyRepresentativeList() {
        return this.companyRepresentativeList;
    }

    /**
     * Returns the list of career center staff members.
     *
     * @return list of {@link src.entity.CareerCenterStaff}
     */
    public ArrayList<CareerCenterStaff> getCareerCenterStaffList() {
        return this.careerCenterStaffList;
    }

    /**
     * Returns the list of currently loaded internships.
     *
     * @return list of {@link src.entity.Internship}
     */
    public ArrayList<Internship> getInternshipList() {
        return this.internshipList;
    }

    /**
     * Returns the list of all internships. Alias for
     * {@link #getInternshipList()}.
     *
     * @return list of {@link src.entity.Internship}
     */
    public ArrayList<Internship> getAllInternships() {
        return internshipList;
    }

    /**
     * Returns the list of internship applications currently in memory.
     *
     * @return list of {@link src.entity.InternshipApplication}
     */
    public ArrayList<InternshipApplication> getInternshipApplicationsList() {
        return this.internshipApplicationsList;
    }

    // SETTERS

    /**
     * Convenience method to add a company representative to the
     * in-memory list.
     *
     * @param rep the {@link src.entity.CompanyRepresentative} to add
     */
    public void CompanyRepresentativeAdd(CompanyRepresentative rep) {
        this.companyRepresentativeList.add(rep);
    }

    /**
     * Adds the provided internship to the internal list.
     *
     * @param internship the {@link src.entity.Internship} to add
     */
    public void addInternship(Internship internship) {
        internshipList.add(internship);
    }

    /**
     * Returns an integer suitable for generating the next internship
     * id based on the current number of internships.
     *
     * @return suggested next internship id (current size)
     */
    public int getNextInternshipId() {
        return internshipList.size();
    }

    /**
     * Find a company representative by id.
     *
     * @param repId id to search for
     * @return the {@link src.entity.CompanyRepresentative} if found, otherwise
     *         {@code null}
     */
    // FINDERS: USED TO FIND WHETHER USERID EXISTs WITHIN RESP DATASTORE
    public CompanyRepresentative findCompanyRep(String repId) {
        for (CompanyRepresentative rep : companyRepresentativeList) {
            if (rep.getUserId().equals(repId)) {
                return rep;
            }
        }
        return null;
    }

    /**
     * Find a student by id.
     *
     * @param studentId id to search for
     * @return the {@link src.entity.Student} if found, otherwise {@code null}
     */
    public Student findStudent(String studentId) {
        for (Student s : studentList) {
            if (s.getUserId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Find an internship by id.
     *
     * @param internshipId id to search for
     * @return the {@link src.entity.Internship} if found, otherwise {@code null}
     */
    public Internship findInternship(String internshipId) {
        for (Internship i : internshipList) {
            if (i.getInternshipId().equals(internshipId)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Find an internship application by its application id.
     *
     * @param applicationId application id to search for
     * @return the {@link src.entity.InternshipApplication} if found, otherwise
     *         {@code null}
     */
    public InternshipApplication findInternshipApplication(String applicationId) {
        for (InternshipApplication app : internshipApplicationsList) {
            if (app.getApplicationId() == applicationId) {
                return app;
            }
        }
        return null;
    }

    /**
     * Find a career center staff member by id.
     *
     * @param staffId id to search for
     * @return the {@link src.entity.CareerCenterStaff} if found, otherwise
     *         {@code null}
     */
    public CareerCenterStaff findCareerCenterStaff(String staffId) {
        for (CareerCenterStaff staff : careerCenterStaffList) {
            if (staff.getUserId().equals(staffId)) {
                return staff;
            }
        }
        return null;
    }

    /**
     * Save the current list of students to a CSV file. Overwrites the
     * file if it exists.
     *
     * @param filename path to write the students CSV to
     */
    public void saveStudents(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("ID,Name,Major,Year,Email,Password");
            for (Student s : studentList) {
                pw.printf("%s,%s,%s,%d,%s,%s\n",
                        s.getUserId(), s.getName(), s.getMajor(), s.getYearOfStudy(), s.getEmail(), s.getPassword());
            }
        } catch (Exception e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    /**
     * Save the current list of career center staff to a CSV file.
     *
     * @param filename path to write the staff CSV to
     */
    public void saveStaff(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("ID,Name,Role,Department,Email,Password");
            for (CareerCenterStaff s : careerCenterStaffList) {
                pw.printf("%s,%s,%s,%s,%s,%s\n",
                        s.getUserId(), s.getName(), s.getStaffRole(), s.getStaffDepartment(), s.getEmail(),
                        s.getPassword());
            }
        } catch (Exception e) {
            System.out.println("Error saving staff: " + e.getMessage());
        }
    }

    /**
     * Save company representatives to a CSV file. The CSV includes the
     * approval status column and stored passwords.
     *
     * @param filename path to write the company reps CSV to
     */
    public void saveCompanyReps(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("ID,Name,CompanyName,Department,Position,Email,Status,Password");
            for (CompanyRepresentative rep : companyRepresentativeList) {
                pw.printf("%s,%s,%s,%s,%s,%s,%s,%s\n",
                        rep.getUserId(), rep.getName(), rep.getCompanyName(), rep.getDepartment(),
                        rep.getPosition(), rep.getEmail(), rep.getApproval().name(), rep.getPassword());
            }
        } catch (Exception e) {
            System.out.println("Error saving company reps: " + e.getMessage());
        }
    }

    /**
     * Save the current list of internships to a CSV file. Includes all
     * internship details, applicants, and status information.
     *
     * @param filename path to write the internships CSV to
     */
    public void saveInternships(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("ID,Title,Description,Level,Major,OpenDate,CloseDate,Slots,RepID,Status,Visibility,Applicants");
            for (Internship i : internshipList) {
                String applicantsStr = String.join(";",
                        i.getApplicants().stream().map(Student::getUserId).toArray(String[]::new));

                pw.printf("%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%b,%s\n", i.getInternshipId(), i.getTitle(),
                        i.getDescription(), i.getLevel().name(), i.getMajor(), i.getOpenDate(), i.getCloseDate(),
                        i.getNumberOfSlotsLeft(), i.getCompanyRep().getUserId(), i.getStatus().name(),
                        i.getVisibility(),
                        applicantsStr);
            }
        } catch (Exception e) {
            System.out.println("Error saving internships: " + e.getMessage());
        }
    }

    /**
     * Save the current list of internship applications to a CSV file.
     * Persists student decisions, company decisions, and withdrawal
     * status for each application.
     *
     * @param filename path to write the applications CSV to
     */
    public void saveApplications(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("AppID,StudentID,InternshipID,RepID,CompanyAccept,StudentAccept,StudentWithdraw");
            for (InternshipApplication app : internshipApplicationsList) {
                pw.printf("%s,%s,%s,%s,%s,%s,%s\n",
                        app.getApplicationId(),
                        app.getStudent().getUserId(),
                        app.getInternship().getInternshipId(),
                        app.getCompanyRep().getUserId(),
                        app.getCompanyAccept().name(),
                        app.getStudentAccept().name(),
                        app.getInternshipWithdrawalStatus().name());
            }
        } catch (Exception e) {
            System.out.println("Error saving applications: " + e.getMessage());
        }
    }

    /**
     * Convenience method to save all entities (students, staff, company
     * representatives, internships, and applications) to their
     * respective CSV files in one operation.
     */
    public void saveAll() {
        saveStudents("src\\csvFiles\\sample_student_list.csv");
        saveStaff("src\\csvFiles\\sample_staff_list.csv");
        saveCompanyReps("src\\csvFiles\\sample_company_representative_list.csv");
        saveInternships("src\\csvFiles\\sample_internship_list.csv");
        saveApplications("src\\csvFiles\\sample_internship_applications.csv");
        System.out.println("All data saved to CSV.");
    }

}
