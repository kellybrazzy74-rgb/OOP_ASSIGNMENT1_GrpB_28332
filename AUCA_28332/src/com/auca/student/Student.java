package com.auca.student;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class Student extends AcademicMember {
    private String studentNumber;
    private String programName;
    private LocalDate admissionDate;
    private int studyYear;
    private String guardianPhone;
    private boolean scholarshipStatus;

    public Student(String personId, String firstName, String lastName, String email, String phoneNumber,
                   LocalDate dateOfBirth, String memberNumber, Department department,
                   LocalDate joinDate, String academicStatus, String institutionalEmail,
                   String advisorName, String studentNumber, String program, LocalDate admissionDate,
                   int studyYear, String guardianPhone, boolean scholarship) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth, memberNumber, department,
                joinDate, academicStatus, institutionalEmail, advisorName);
        this.studentNumber = studentNumber;
        this.programName = program;
        this.admissionDate = admissionDate;
        this.studyYear = studyYear;
        this.guardianPhone = guardianPhone;
        this.scholarshipStatus = scholarship;
    }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }
    public String getProgram() { return programName; }
    public void setProgram(String program) { this.programName = program; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public int getStudyYear() { return studyYear; }
    public void setStudyYear(int studyYear) { this.studyYear = studyYear; }
    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String guardianPhone) { this.guardianPhone = guardianPhone; }
    public boolean isScholarshipStatus() { return scholarshipStatus; }
    public void setScholarshipStatus(boolean scholarshipStatus) { this.scholarshipStatus = scholarshipStatus; }
    public boolean hasScholarship() { return scholarshipStatus; }
    public void setScholarship(boolean scholarship) { this.scholarshipStatus = scholarship; }
    public String getRoleDescription() { return "Student"; }
    public String getAcademicCategory() { return "Undergraduate"; }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (studentNumber == null || studentNumber.trim().length() < 4
                || studentNumber.trim().length() > 30)
            throw new ValidationException("Student number must be 4 to 30 characters.");
        if (programName == null || programName.trim().length() < 2 || programName.trim().length() > 120)
            throw new ValidationException("Program name must be 2 to 120 characters.");
        if (admissionDate == null || admissionDate.isAfter(LocalDate.now()))
            throw new ValidationException("Admission date must not be in the future.");
        if (studyYear < 1 || studyYear > 6)
            throw new ValidationException("Study year must be from 1 to 6.");
        String digits = guardianPhone == null ? ""
                : guardianPhone.replace("+", "").replace(" ", "").replace("-", "");
        if (!digits.matches("\\d{10,15}"))
            throw new ValidationException("Guardian phone must contain 10 to 15 digits.");
    }

    public String getRegistrationSummary() {
        String departmentName = getDepartment() == null ? "No department" : getDepartment().getName();
        return "[" + getId() + "] " + getName() + " | Student " + studentNumber + " | "
                + programName + " | " + departmentName;
    }

    @Override
    public void printDetails() {
        System.out.println("Person ID: " + getId());
        System.out.println("First name: " + getFirstName());
        System.out.println("Last name: " + getLastName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone number: " + getPhoneNumber());
        System.out.println("Date of birth: " + getDateOfBirth());
        System.out.println("Member number: " + getMemberNumber());
        System.out.println("Department: " + getDepartment().getRegistrationSummary());
        System.out.println("Join date: " + getJoinDate());
        System.out.println("Academic status: " + getAcademicStatus());
        System.out.println("Institutional email: " + getInstitutionalEmail());
        System.out.println("Advisor name: " + getAdvisorName());
        System.out.println("Student number: " + studentNumber);
        System.out.println("Program name: " + programName);
        System.out.println("Admission date: " + admissionDate);
        System.out.println("Study year: " + studyYear);
        System.out.println("Guardian phone: " + guardianPhone);
        System.out.println("Scholarship status: " + (scholarshipStatus ? "YES" : "NO"));
    }

    public String toString() { return getRegistrationSummary(); }
}
