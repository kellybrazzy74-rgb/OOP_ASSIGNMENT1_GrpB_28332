package com.auca.department;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.teacher.Teacher;


public class Department implements Identifiable, Registrable {
    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String officeLocation;
    private String contactEmail;
    private Teacher headTeacher;

    public Department(String departmentId, String departmentName, String departmentCode,
                      String officeLocation, String contactEmail, Teacher headTeacher) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.officeLocation = officeLocation;
        this.contactEmail = contactEmail;
        this.headTeacher = headTeacher;
    }
    public String getDepartmentId(){return departmentId;} public void setDepartmentId(String value){departmentId=value;}
    public String getId(){return departmentId;} public void setId(String value){departmentId=value;}
    public String getDepartmentName(){return departmentName;} public void setDepartmentName(String value){departmentName=value;}
    public String getName(){return departmentName;} public void setName(String value){departmentName=value;}
    public String getDepartmentCode(){return departmentCode;} public void setDepartmentCode(String value){departmentCode=value;}
    public String getCode(){return departmentCode;} public void setCode(String value){departmentCode=value;}
    public String getOfficeLocation(){return officeLocation;} public void setOfficeLocation(String value){officeLocation=value;}
    public String getLocation(){return officeLocation;} public void setLocation(String value){officeLocation=value;}
    public String getContactEmail(){return contactEmail;} public void setContactEmail(String value){contactEmail=value;}
    public Teacher getHeadTeacher(){return headTeacher;} public void setHeadTeacher(Teacher value){headTeacher=value;}
    public Teacher getHead(){return headTeacher;} public void setHead(Teacher value){headTeacher=value;}
    public boolean isActive(){return true;}
    public String getDisplayName(){return departmentName;}
    public void validate() throws ValidationException {
        if(departmentId==null||departmentId.trim().isEmpty()) throw new ValidationException("Department ID is required.");
        if(departmentName==null||departmentName.trim().length()<2||departmentName.trim().length()>60) throw new ValidationException("Department name must be 2 to 60 characters.");
        if(departmentCode==null||departmentCode.trim().isEmpty()) throw new ValidationException("Department code is required.");
        if(officeLocation==null||officeLocation.trim().isEmpty()) throw new ValidationException("Office location is required.");
        if(contactEmail==null||contactEmail.length()>MAX_EMAIL_LENGTH||!contactEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new ValidationException("A valid contact email is required.");
    }
    public void printDetails(){System.out.println(getRegistrationSummary());}
    public String getRegistrationSummary(){return "["+departmentId+"] "+departmentName+" ("+departmentCode+") - "+officeLocation+(headTeacher==null?"":" - Head: "+headTeacher.getName());}
    public String toString(){return getRegistrationSummary();}
}
