package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class AcademicMember extends Person {
    private Department department;
    private String memberNumber;
    private LocalDate joinDate;
    private String academicStatus;
    private String institutionalEmail;
    private String advisorName;

    protected AcademicMember(String personId, String firstName, String lastName, String email,
                             String phoneNumber, LocalDate dateOfBirth, String memberNumber,
                             Department department, LocalDate joinDate, String academicStatus,
                             String institutionalEmail, String advisorName) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public String getMemberNumber() { return memberNumber; }
    public void setMemberNumber(String memberNumber) { this.memberNumber = memberNumber; }
    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    public String getAcademicStatus() { return academicStatus; }
    public void setAcademicStatus(String academicStatus) { this.academicStatus = academicStatus; }
    public String getInstitutionalEmail() { return institutionalEmail; }
    public void setInstitutionalEmail(String institutionalEmail) { this.institutionalEmail = institutionalEmail; }
    public String getAdvisorName() { return advisorName; }
    public void setAdvisorName(String advisorName) { this.advisorName = advisorName; }
    public abstract String getAcademicCategory();

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (memberNumber == null || memberNumber.trim().length() < 4
                || memberNumber.trim().length() > 30)
            throw new ValidationException("Member number must be 4 to 30 characters.");
        if (department == null) throw new ValidationException("Department is required.");
        if (joinDate == null || joinDate.isAfter(LocalDate.now()))
            throw new ValidationException("Join date must not be in the future.");
        if (academicStatus == null || !academicStatus.matches("(?i)ACTIVE|INACTIVE"))
            throw new ValidationException("Academic status must be ACTIVE or INACTIVE.");
        if (institutionalEmail == null || institutionalEmail.length() > MAX_EMAIL_LENGTH
                || !institutionalEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new ValidationException("A valid institutional email is required.");
        if (advisorName == null || advisorName.trim().length() < 2
                || advisorName.trim().length() > 120)
            throw new ValidationException("Advisor name must be 2 to 120 characters.");
    }
}
