

package com.auca.feepayment;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import java.math.BigDecimal;
import java.time.LocalDate;
public class FeePayment implements Identifiable,Registrable{
 private String paymentId; private Student student; private BigDecimal amount; private LocalDate paymentDate; private String paymentMethod; private String receiptNumber;
 public FeePayment(String paymentId,Student student,BigDecimal amount,LocalDate paymentDate,String paymentMethod,String receiptNumber){
  this.paymentId=paymentId;this.student=student;this.amount=amount;this.paymentDate=paymentDate;this.paymentMethod=paymentMethod;this.receiptNumber=receiptNumber;
 }
 public String getPaymentId(){return paymentId;} public void setPaymentId(String value){paymentId=value;}
 public String getId(){return paymentId;} public void setId(String value){paymentId=value;} public Student getStudent(){return student;} public void setStudent(Student value){student=value;}
 public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal value){amount=value;} public LocalDate getPaymentDate(){return paymentDate;} public void setPaymentDate(LocalDate value){paymentDate=value;}
 public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String value){paymentMethod=value;} public String getReceiptNumber(){return receiptNumber;} public void setReceiptNumber(String value){receiptNumber=value;}
 public String getDisplayName(){return receiptNumber+" - "+student.getName();} public boolean isActive(){return true;}
 public void validate() throws ValidationException{if(paymentId==null||paymentId.trim().isEmpty())throw new ValidationException("Payment ID is required.");if(student==null)throw new ValidationException("Student is required.");if(amount==null||amount.compareTo(BigDecimal.ZERO)<=0)throw new ValidationException("Payment amount must be strictly positive.");if(paymentDate==null||paymentDate.isAfter(LocalDate.now()))throw new ValidationException("Payment date must not be in the future.");if(paymentMethod==null||!paymentMethod.matches("(?i)CASH|CARD|MOBILE_MONEY|BANK_TRANSFER"))throw new ValidationException("Payment method is invalid.");if(receiptNumber==null||receiptNumber.trim().isEmpty())throw new ValidationException("Receipt number is required.");}
 public void printDetails(){System.out.println(getRegistrationSummary());}  public String getRegistrationSummary(){return "["+paymentId+"] "+student.getName()+" paid "+amount+" via "+paymentMethod+" - receipt "+receiptNumber;}
 public String toString(){return getRegistrationSummary();}
}
