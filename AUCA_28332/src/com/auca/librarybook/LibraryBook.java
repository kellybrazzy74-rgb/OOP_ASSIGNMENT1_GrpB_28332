package com.auca.librarybook;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;


public class LibraryBook implements Identifiable,Registrable{
 private String bookId; private String isbn; private String title; private String author; private int totalCopies; private int availableCopies;
 public LibraryBook(String bookId,String isbn,String title,String author,int totalCopies,int availableCopies){
  this.bookId=bookId;this.isbn=isbn;this.title=title;this.author=author;this.totalCopies=totalCopies;this.availableCopies=availableCopies;
 }
 public String getBookId(){return bookId;} public void setBookId(String value){bookId=value;}
 public String getId(){return bookId;} public void setId(String value){bookId=value;} public String getTitle(){return title;} public void setTitle(String value){title=value;}
 public String getIsbn(){return isbn;} public void setIsbn(String v){isbn=v;} public String getAuthor(){return author;} public void setAuthor(String v){author=v;}
 public int getTotalCopies(){return totalCopies;} public void setTotalCopies(int v){totalCopies=v;} public int getAvailableCopies(){return availableCopies;} public void setAvailableCopies(int v){availableCopies=v;}
 public String getDisplayName(){return title;} public boolean isActive(){return availableCopies>0;}
 public void validate() throws ValidationException{if(bookId==null||bookId.trim().isEmpty())throw new ValidationException("Book ID is required.");if(title==null||title.trim().length()<2)throw new ValidationException("Book title is required.");if(isbn==null||isbn.trim().isEmpty())throw new ValidationException("ISBN is required.");if(author==null||author.trim().length()<2)throw new ValidationException("Author is required.");if(totalCopies<0)throw new ValidationException("Total copies must not be negative.");if(availableCopies<0||availableCopies>totalCopies)throw new ValidationException("Available copies must be from 0 to total copies.");}
 public void printDetails(){System.out.println(getRegistrationSummary());} public String getRegistrationSummary(){return "["+bookId+"] "+title+" by "+author+" | ISBN "+isbn+" | "+availableCopies+"/"+totalCopies+" available";}
 public String toString(){return getRegistrationSummary();}
}
