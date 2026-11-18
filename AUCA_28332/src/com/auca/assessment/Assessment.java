

package com.auca.assessment;

import com.auca.enrollment.Enrollment;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import java.time.LocalDate;
public class Assessment implements Identifiable,Registrable{
 private String assessmentId; private Enrollment enrollment; private String assessmentType; private double maximumScore; private double obtainedScore; private LocalDate assessmentDate;
 public Assessment(String assessmentId,Enrollment enrollment,String assessmentType,double maximumScore,double obtainedScore,LocalDate assessmentDate){
  this.assessmentId=assessmentId;this.enrollment=enrollment;this.assessmentType=assessmentType;this.maximumScore=maximumScore;this.obtainedScore=obtainedScore;this.assessmentDate=assessmentDate;
 }
 public String getAssessmentId(){return assessmentId;} public void setAssessmentId(String value){assessmentId=value;}
 public String getId(){return assessmentId;} public void setId(String value){assessmentId=value;} public Enrollment getEnrollment(){return enrollment;} public void setEnrollment(Enrollment value){enrollment=value;}
 public String getAssessmentType(){return assessmentType;} public void setAssessmentType(String v){assessmentType=v;} public double getMaximumScore(){return maximumScore;} public void setMaximumScore(double v){maximumScore=v;}
 public double getObtainedScore(){return obtainedScore;} public void setObtainedScore(double v){obtainedScore=v;} public LocalDate getAssessmentDate(){return assessmentDate;} public void setAssessmentDate(LocalDate v){assessmentDate=v;}
 public String getDisplayName(){return assessmentType+" - "+enrollment.getDisplayName();} public boolean isActive(){return true;}
 public void validate() throws ValidationException{if(assessmentId==null||assessmentId.trim().isEmpty())throw new ValidationException("Assessment ID is required.");if(enrollment==null||!enrollment.isActive())throw new ValidationException("An active enrollment is required.");if(assessmentType==null||!assessmentType.matches("(?i)QUIZ|ASSIGNMENT|MIDTERM|FINAL|PROJECT"))throw new ValidationException("Assessment type must be QUIZ, ASSIGNMENT, MIDTERM, FINAL, or PROJECT.");if(maximumScore<=0)throw new ValidationException("Maximum score must be greater than zero.");if(obtainedScore<0||obtainedScore>maximumScore)throw new ValidationException("Obtained score must be between 0 and maximum score.");if(assessmentDate==null||assessmentDate.isAfter(LocalDate.now()))throw new ValidationException("Assessment date must not be in the future.");}
 public void printDetails(){System.out.println(getRegistrationSummary());}  public String getRegistrationSummary(){return "["+assessmentId+"] "+assessmentType+": "+obtainedScore+"/"+maximumScore+" - "+enrollment.getDisplayName();}
 public String toString(){return getRegistrationSummary();}
}
