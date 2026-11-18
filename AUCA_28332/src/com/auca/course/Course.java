package com.auca.course;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.teacher.Teacher;


public class Course implements Identifiable,Registrable{
 private String courseId; private String courseCode; private String courseTitle; private int creditHours;
 private Department department; private Teacher assignedTeacher;
 public Course(String courseId,String courseCode,String courseTitle,int creditHours,Department department,Teacher assignedTeacher){
  this.courseId=courseId; this.courseCode=courseCode; this.courseTitle=courseTitle; this.creditHours=creditHours;
  this.department=department; this.assignedTeacher=assignedTeacher;
 }
 public String getCourseId(){return courseId;} public void setCourseId(String value){courseId=value;}
 public String getId(){return courseId;} public void setId(String value){courseId=value;}
 public String getCourseCode(){return courseCode;} public void setCourseCode(String value){courseCode=value;}
 public String getCode(){return courseCode;} public void setCode(String value){courseCode=value;}
 public String getCourseTitle(){return courseTitle;} public void setCourseTitle(String value){courseTitle=value;}
 public String getName(){return courseTitle;} public void setName(String value){courseTitle=value;}
 public int getCreditHours(){return creditHours;} public void setCreditHours(int value){creditHours=value;}
 public Department getDepartment(){return department;} public void setDepartment(Department value){department=value;}
 public Teacher getAssignedTeacher(){return assignedTeacher;} public void setAssignedTeacher(Teacher value){assignedTeacher=value;}
 public Teacher getTeacher(){return assignedTeacher;} public void setTeacher(Teacher value){assignedTeacher=value;}
 public String getDisplayName(){return courseTitle;} public boolean isActive(){return true;}
 public void validate() throws ValidationException{
  if(courseId==null||courseId.trim().isEmpty())throw new ValidationException("Course ID is required.");
  if(courseCode==null||courseCode.trim().isEmpty())throw new ValidationException("Course code is required.");
  if(courseTitle==null||courseTitle.trim().length()<2)throw new ValidationException("Course title is required.");
  if(creditHours<1||creditHours>6)throw new ValidationException("Credit hours must be 1 to 6.");
  if(department==null)throw new ValidationException("Department is required.");
  if(assignedTeacher==null)throw new ValidationException("Assigned teacher is required.");
 }
 public void printDetails(){System.out.println(getRegistrationSummary());} public String getRegistrationSummary(){return "["+courseId+"] "+courseCode+" "+courseTitle+" ("+creditHours+" credits) - "+department.getDepartmentName()+", "+assignedTeacher.getName();}
 public String toString(){return getRegistrationSummary();}
}
