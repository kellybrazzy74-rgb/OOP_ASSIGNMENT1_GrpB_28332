

package com.auca.enrollment;

import com.auca.course.Course;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import java.time.LocalDate;
public class Enrollment implements Identifiable,Registrable{
 private String enrollmentId; private Student student; private Course course; private LocalDate enrollmentDate; private String semester; private String enrollmentStatus;
 public Enrollment(String enrollmentId,Student student,Course course,LocalDate enrollmentDate,String semester,String enrollmentStatus){
  this.enrollmentId=enrollmentId; this.student=student; this.course=course; this.enrollmentDate=enrollmentDate; this.semester=semester; this.enrollmentStatus=enrollmentStatus;
 }
 public String getEnrollmentId(){return enrollmentId;} public void setEnrollmentId(String value){enrollmentId=value;}
 public String getId(){return enrollmentId;} public void setId(String value){enrollmentId=value;}
 public Student getStudent(){return student;} public void setStudent(Student value){student=value;}
 public Course getCourse(){return course;} public void setCourse(Course value){course=value;}
 public LocalDate getEnrollmentDate(){return enrollmentDate;} public void setEnrollmentDate(LocalDate value){enrollmentDate=value;}
 public String getSemester(){return semester;} public void setSemester(String value){semester=value;}
 public String getEnrollmentStatus(){return enrollmentStatus;} public void setEnrollmentStatus(String value){enrollmentStatus=value;}
 public boolean isActive(){return "ACTIVE".equalsIgnoreCase(enrollmentStatus);} public void setActive(boolean value){enrollmentStatus=value?"ACTIVE":"INACTIVE";}
 public String getDisplayName(){return student.getName()+" - "+course.getName();}
 public void validate() throws ValidationException{
  if(enrollmentId==null||enrollmentId.trim().isEmpty())throw new ValidationException("Enrollment ID is required.");
  if(student==null)throw new ValidationException("Student is required.");
  if(course==null)throw new ValidationException("Course is required.");
  if(enrollmentDate==null||enrollmentDate.isAfter(LocalDate.now()))throw new ValidationException("Enrollment date must not be in the future.");
  if(semester==null||semester.trim().isEmpty())throw new ValidationException("Semester is required.");
  if(enrollmentStatus==null||(!enrollmentStatus.equalsIgnoreCase("ACTIVE")&&!enrollmentStatus.equalsIgnoreCase("INACTIVE")))
   throw new ValidationException("Enrollment status must be ACTIVE or INACTIVE.");
 }
 public void printDetails(){System.out.println(getRegistrationSummary());} public String getRegistrationSummary(){return "["+enrollmentId+"] "+student.getName()+" enrolled in "+course.getName()+" - "+semester+" - "+enrollmentStatus;}
 public String toString(){return getRegistrationSummary();}
}
