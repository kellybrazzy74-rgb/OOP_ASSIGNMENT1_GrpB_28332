package com.auca.abstracts;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {
    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    protected Person(String personId, String firstName, String lastName, String email,
                     String phoneNumber, LocalDate dateOfBirth) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public String getPersonId() { return personId; }
    public void setPersonId(String personId) { this.personId = personId; }
    public String getId() { return personId; }
    public void setId(String personId) { this.personId = personId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getName() {
        return ((firstName == null ? "" : firstName) + " "
                + (lastName == null ? "" : lastName)).trim();
    }
    public void setName(String name) {
        if (name != null) {
            String[] parts = name.trim().split("\\s+", 2);
            firstName = parts.length > 0 ? parts[0] : "";
            lastName = parts.length > 1 ? parts[1] : "";
        }
    }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPhone() { return phoneNumber; }
    public void setPhone(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public boolean isActive() { return true; }
    public String getDisplayName() { return getName(); }
    public abstract String getRoleDescription();

    public void validate() throws ValidationException {
        if (personId == null || personId.trim().length() < 4 || personId.trim().length() > 30)
            throw new ValidationException("Person ID must be 4 to 30 characters.");
        if (!validName(firstName) || !validName(lastName))
            throw new ValidationException("First and last names must each be 2 to 60 characters.");
        if (email == null || email.length() > MAX_EMAIL_LENGTH
                || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new ValidationException("A valid email is required.");
        String digits = phoneNumber == null ? "" : phoneNumber.replace("+", "").replace(" ", "").replace("-", "");
        if (!digits.matches("\\d{" + MIN_PHONE_LENGTH + "," + MAX_PHONE_LENGTH + "}"))
            throw new ValidationException("Phone must contain 10 to 15 digits.");
        if (dateOfBirth == null || !dateOfBirth.isBefore(LocalDate.now()))
            throw new ValidationException("Date of birth must be in the past.");
    }

    private boolean validName(String value) {
        return value != null && value.trim().length() >= MIN_NAME_LENGTH
                && value.trim().length() <= MAX_NAME_LENGTH;
    }

    public void printDetails() { System.out.println(getRegistrationSummary()); }
    public abstract String getRegistrationSummary();
}
