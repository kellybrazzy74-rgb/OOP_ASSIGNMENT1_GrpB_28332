package com.auca.classroom;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;


public class Classroom implements Identifiable,Registrable{
 private String classroomId; private String buildingName; private String roomNumber; private int capacity; private int floorNumber; private boolean available;
 public Classroom(String classroomId,String buildingName,String roomNumber,int capacity,int floorNumber,boolean available){
  this.classroomId=classroomId; this.buildingName=buildingName; this.roomNumber=roomNumber; this.capacity=capacity; this.floorNumber=floorNumber; this.available=available;
 }
 public String getClassroomId(){return classroomId;} public void setClassroomId(String value){classroomId=value;}
 public String getId(){return classroomId;} public void setId(String value){classroomId=value;}
 public String getBuildingName(){return buildingName;} public void setBuildingName(String value){buildingName=value;}
 public String getBuilding(){return buildingName;} public void setBuilding(String value){buildingName=value;}
 public String getRoomNumber(){return roomNumber;} public void setRoomNumber(String value){roomNumber=value;}
 public int getCapacity(){return capacity;} public void setCapacity(int value){capacity=value;}
 public int getFloorNumber(){return floorNumber;} public void setFloorNumber(int value){floorNumber=value;}
 public boolean isAvailable(){return available;} public void setAvailable(boolean value){available=value;}
 public boolean isActive(){return available;} public void setActive(boolean value){available=value;}
 public String getDisplayName(){return buildingName+" "+roomNumber;}
 public void validate() throws ValidationException{
  if(classroomId==null||classroomId.trim().isEmpty())throw new ValidationException("Classroom ID is required.");
  if(buildingName==null||buildingName.trim().isEmpty())throw new ValidationException("Building name is required.");
  if(roomNumber==null||roomNumber.trim().isEmpty())throw new ValidationException("Room number is required.");
  if(capacity<=0)throw new ValidationException("Capacity must be greater than zero.");
  if(floorNumber<0)throw new ValidationException("Floor number must not be negative.");
 }
 public void printDetails(){System.out.println(getRegistrationSummary());} public String getRegistrationSummary(){return "["+classroomId+"] "+buildingName+" Room "+roomNumber+" - capacity "+capacity+" - floor "+floorNumber;}
 public String toString(){return getRegistrationSummary();}
}
