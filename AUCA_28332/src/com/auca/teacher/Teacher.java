

package com.auca.teacher;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Teacher extends AcademicMember {
    private String employeeNumber;
    private String specialization;
    private LocalDate hireDate;
    private BigDecimal salary;
    private String officeNumber;
    private String employmentType;

    public Teacher(String id, String firstName, String lastName, String email, String phone,
                   LocalDate dateOfBirth, String memberNumber, Department department,
                   LocalDate joinDate, String academicStatus, String institutionalEmail,
                   String advisorName, String employeeNumber, String specialization,
                   LocalDate hireDate, BigDecimal salary, String officeNumber, String employmentType) {
        super(id, firstName, lastName, email, phone, dateOfBirth, memberNumber, department,
                joinDate, academicStatus, institutionalEmail, advisorName);
        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.salary = salary;
        this.officeNumber = officeNumber;
        this.employmentType = employmentType;
    }
    public String getEmployeeNumber(){return employeeNumber;}
    public void setEmployeeNumber(String value){employeeNumber=value;}
    public String getSpecialization(){return specialization;}
    public void setSpecialization(String value){specialization=value;}
    public LocalDate getHireDate(){return hireDate;}
    public void setHireDate(LocalDate value){hireDate=value;}
    public BigDecimal getSalary(){return salary;}
    public void setSalary(BigDecimal value){salary=value;}
    public String getOfficeNumber(){return officeNumber;}
    public void setOfficeNumber(String value){officeNumber=value;}
    public String getEmploymentType(){return employmentType;}
    public void setEmploymentType(String value){employmentType=value;}
    public String getRoleDescription(){return "Teacher";} public String getAcademicCategory(){return "Faculty";}
    public void validate() throws ValidationException {
        super.validate();
        if(employeeNumber==null||!employeeNumber.matches("(?i)[a-z0-9_-]{4,30}")) throw new ValidationException("Employee number must be 4 to 30 letters, digits, underscores, or hyphens.");
        if(salary==null||salary.compareTo(BigDecimal.ZERO)<=0) throw new ValidationException("Salary must be strictly positive.");
        if(hireDate==null||hireDate.isAfter(LocalDate.now())) throw new ValidationException("Hire date must not be in the future.");
        if(specialization==null||specialization.trim().isEmpty()) throw new ValidationException("Specialization is required.");
        if(officeNumber==null||officeNumber.trim().isEmpty()) throw new ValidationException("Office number is required.");
        if(employmentType==null||!employmentType.matches("(?i)FULL_TIME|PART_TIME|CONTRACT"))
            throw new ValidationException("Employment type must be FULL_TIME, PART_TIME, or CONTRACT.");
    }
    public String getRegistrationSummary(){return "["+getId()+"] "+getName()+" | Employee "+employeeNumber+" | "+specialization+" | "+getDepartment().getName();}
    public String toString(){return getRegistrationSummary();}
}
