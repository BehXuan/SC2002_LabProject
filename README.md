# SC2002_LabProject Grp 2
Internship Placement Management System 

## Overview of the System
- The system will act as a centralized hub for all Students, Company Representatives, and Career Center Staff.
- All users will need to login to this hub using their account. 
  - Students’ IDs will start with U, followed by 7-digit numbers and ends with a letter (e.g., U2345123F) 
  - Company Representatives ID is their company email address. 
  - Career Center Staff’s ID is their NTU account. 
  - Assume all users use the default password, which is password. 
  - A user can change password in the system.
 
- Additional Information about the user: 
  - Student: Year of Study and Major 
  - Company Representatives: Company Name, Department, and position. 
  - Carrer Center Staff: Staff Department 
- A user list can be initiated through a file uploaded into the system at initialisation.

## User's capabilities
1. All Users 
  - All users should have a User ID, Name and Password 
  - They should all have access to the basic user management features, including login, logout and change password 
2. Student 
  - Possesses all the base user capabilities 
  - Registration is automatic by reading in from the student list file. 
  - Can only view the list of internship opportunities based: 
    - Student’s own profile: 
      - Year of study (Year 1 to 4) 
      - Major (CSC, EEE, MAE, etc...) 
    - Visibility has been toggled “on” 
  - Able to apply for a maximum of 3 internship opportunities at once 
    - Year 1 and 2 students can ONLY apply for Basic-level internships 
    - Year 3 and above students can apply for any level (Basic, Intermediate, Advanced) 
  - Able to view the internship he/she applied for, even after visibility is turned off, and the application status (“Pending”, “Successful” or “Unsuccessful”) 
    - Status will be “Pending” by default, and updated based on the input from the Company Representative 
  - If application status is “Successful”, students can accept the internship placement 
    - Only 1 internship placement can be accepted 
    - All other applications will be withdrawn once an internship placement is accepted 
  - Allowed to request withdrawal for their internship application before/after placement confirmation 
    - Subject to approval from Career Center Staff 
3. Company Representatives 
  - Company Representative list is empty at very beginning. 
  - Company Representatives must register as a representative of a specific company, and they can only log in once approved by the Career Center Staff. 
  - Able to create internship opportunities (up to 5) for their companies, which should include the following details: 
    - Internship Title 
    - Description 
    - Internship Level (Basic, Intermediate, Advanced) 
    - Preferred Majors (Assume 1 preferred major will do) 
SC/CE/CZ2002 Object-Oriented Design C Programming 
Assignment 
    - Application opening date  
    - Application closing date 
    - Status (“Pending”, “Approved”, “Rejected”, “Filled”) 
    - Company Name 
    - Company Representatives in charge (automatically assigned) 
    - Number of slots (max of 10) 
  - Internship opportunities created must be approved by the career center staff 
    - Once status is “Approved”, students may apply for them 
    - If “Filled” or after the Closing Date, students will not be able to apply for them anymore 
    - Able to view application details and student details for each of their internship opportunities 
  - May Approve or Reject the internship application 
    - Once approved, student application status becomes "Successful" 
    - Student can then accept the placement confirmation 
    - Internship opportunity status becomes "Filled" only when all available slots are confirmed by students 
  - Able to toggle the visibility of the internship opportunity to “on” or “off”. This will be reflected in the internship list that will be visible to Students 
4. Career Center Staff 
  - Registration is automatic by reading in from the staff list file. 
  - Able to authorize or reject the account creation of Company Representatives 
  - Able to approve or reject internship opportunities submitted by Company Representatives 
    - Once approved, internship opportunity status changes to "Approved" and becomes visible to eligible students 
  - Able to approve or reject student withdrawal requests (both before and after placement confirmation) 
  - Able to generate comprehensive reports regarding internship opportunities created: 
    - There should be filters to generate filter opportunities based on their Status, Preferred Majors, Internship Level, etc...

## Miscellaneous: 
  - All users can use filters to view internship opportunities (Status, Preferred Majors, Internship Level, Closing Date, etc.) Assume that default is by alphabetical order. User filter settings are saved when they switch menu pages. 
