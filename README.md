# SC2002 Lab Project

A Java-based console application designed to manage a university Career
Center system. It supports multiple user types (Student, Staff, Company
Representative) and handles internship listings, applications,
approvals, and reporting.

## ✨ Features

### 👤 User Management

-   Login system for **Students**, **Staff**, and **Company
    Representatives**
-   Authentication and role-based access

### 📝 Internship Management

-   Companies can create internship postings
-   Students can browse, apply, withdraw
-   Staff can approve/reject postings

### 📄 Application Workflow

-   Tracks application status
-   Students can view/update their applications
-   Companies can view & manage incoming applications

### 📊 Reporting

-   Generate internship and application reports
-   Multiple sorting options
-   Export summary through report generator

## 📁 Project Structure

    SC2002_LabProject/
    │
    ├── src/
    │   ├── controller/       
    │   ├── csvFiles/         
    │   ├── entity/           
    │   ├── enums/            
    │   ├── interfaces/       
    │   ├── report/           
    │   ├── view/             
    │   ├── ProjectApp.java
    |   └── DataStore.java
    │
    ├── docs/                 
    └── .git/                 

## ▶️ How to Run

### Prerequisites

-   Java **17** or later
-   A terminal or Java-capable IDE (IntelliJ, Eclipse, VS Code)

### Run via Terminal

    cd SC2002_LabProject/src
    javac ProjectApp.java
    java ProjectApp

### Run via IDE

1.  Import the project as a Java project
2.  Mark `src/` as the source root
3.  Run `ProjectApp.java`

## 📚 Data Files

Located in `src/csvFiles/`: - `sample_student_list.csv` -
`sample_staff_list.csv` - `sample_companyrep_list.csv` -
`sample_internship_list.csv` - `sample_internship_applications.csv`

## 🧪 Testing

You may test the system using the sample accounts from the CSV files.

## 📌 Notes

-   The project follows an **MVC architecture**.
-   Controllers mediate between views and entities.
-   Data is persisted in CSV format.
