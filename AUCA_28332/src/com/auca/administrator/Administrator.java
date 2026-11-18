

package com.auca.administrator;

import com.auca.abstracts.Person;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class Administrator extends Person {
    private String staffNumber;
    private String jobTitle;
    private String assignedOffice;
    private LocalDate employmentDate;
    private int accessLevel;
    private boolean active;

    public Administrator(String personId, String firstName, String lastName, String email,
                         String phoneNumber, LocalDate dateOfBirth, String staffNumber,
                         String jobTitle, String assignedOffice, LocalDate employmentDate,
                         int accessLevel, boolean active) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.staffNumber = staffNumber;
        this.jobTitle = jobTitle;
        this.assignedOffice = assignedOffice;
        this.employmentDate = employmentDate;
        this.accessLevel = accessLevel;
        this.active = active;
    }
    public String getStaffNumber(){return staffNumber;} public void setStaffNumber(String value){staffNumber=value;}
    public String getJobTitle(){return jobTitle;} public void setJobTitle(String value){jobTitle=value;}
    public String getAssignedOffice(){return assignedOffice;} public void setAssignedOffice(String value){assignedOffice=value;}
    public LocalDate getEmploymentDate(){return employmentDate;} public void setEmploymentDate(LocalDate value){employmentDate=value;}
    public int getAccessLevel(){return accessLevel;} public void setAccessLevel(int value){accessLevel=value;}
    public boolean isActive(){return active;} public void setActive(boolean value){active=value;}
    public String getRoleDescription(){return "Administrator";}
    public void validate() throws ValidationException {
        super.validate();
        if(staffNumber==null||staffNumber.trim().isEmpty()) throw new ValidationException("Staff number is required.");
        if(jobTitle==null||jobTitle.trim().isEmpty()) throw new ValidationException("Job title is required.");
        if(assignedOffice==null||assignedOffice.trim().isEmpty()) throw new ValidationException("Assigned office is required.");
        if(employmentDate==null||employmentDate.isAfter(LocalDate.now())) throw new ValidationException("Employment date must not be in the future.");
        if(accessLevel<1||accessLevel>5) throw new ValidationException("Access level must be from 1 to 5.");
    }
    public String getRegistrationSummary(){return "["+getId()+"] "+getName()+" | Staff "+staffNumber+" | "+jobTitle+" | Office "+assignedOffice;}
    public String toString(){return getRegistrationSummary();}
}
