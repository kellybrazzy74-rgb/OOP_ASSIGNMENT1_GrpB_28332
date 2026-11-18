package com.auca.main;

import com.auca.abstracts.Person;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.*;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Identifiable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Person> people = new ArrayList<>();
    private static final List<Department> departments = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Classroom> classrooms = new ArrayList<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();
    private static final List<Assessment> assessments = new ArrayList<>();
    private static final List<FeePayment> payments = new ArrayList<>();
    private static final List<LibraryBook> books = new ArrayList<>();
    private static final List<BookLoan> loans = new ArrayList<>();

    private static class Cancelled extends RuntimeException {}

    public static void main(String[] args) {
        while (true) {
            printMenu();
            try {
                int choice = readInt("Select an option: ", 0, 19);
                switch (choice) {
                    case 0 -> { System.out.println("Goodbye."); return; }
                    case 1 -> registerDepartment();
                    case 2 -> registerStudent();
                    case 3 -> registerTeacher();
                    case 4 -> registerAdministrator();
                    case 5 -> registerCourse();
                    case 6 -> registerClassroom();
                    case 7 -> enrollStudent();
                    case 8 -> recordAssessment();
                    case 9 -> recordPayment();
                    case 10 -> registerBook();
                    case 11 -> borrowBook();
                    case 12 -> returnBook();
                    case 13 -> viewAll();
                    case 14 -> searchById();
                    case 15 -> updateRecord();
                    case 16 -> showStudentSummary();
                    case 17 -> showPaymentHistory();
                    case 18 -> showAvailableBooks();
                    case 19 -> showPolymorphism();
                }
            } catch (ValidationException | DuplicateRecordException | RecordNotFoundException |
                     PrerequisiteMissingException | BookUnavailableException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Cancelled e) {
                System.out.println("Cancelled. No changes were made.");
            } catch (Exception e) {
                System.out.println("Unexpected input: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n============== AUCA_ID SCHOOL MANAGEMENT ==============");
        System.out.println(" 1. Register Department       11. Borrow Library Book");
        System.out.println(" 2. Register Student          12. Return Library Book");
        System.out.println(" 3. Register Teacher          13. View All Records");
        System.out.println(" 4. Register Administrator    14. Search Record by ID");
        System.out.println(" 5. Register Course           15. Update Existing Record");
        System.out.println(" 6. Register Classroom        16. Student Academic Summary");
        System.out.println(" 7. Enroll Student            17. Student Payment History");
        System.out.println(" 8. Record Assessment         18. View Available Books");
        System.out.println(" 9. Record Fee Payment        19. Demonstrate Polymorphism");
        System.out.println("10. Register Library Book      0. Exit");
    }

    private static String readText(String label) {
        System.out.print(label + ": ");
        String value = scanner.nextLine().trim();
        if (value.equalsIgnoreCase("CANCEL")) throw new Cancelled();
        return value;
    }

    private static String requiredText(String label) {
        while (true) {
            String value = readText(label);
            if (!value.isEmpty()) return value;
            System.out.println("Error: this field is required.");
        }
    }

    private static int readInt(String label, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(readText(label));
                if (value >= min && value <= max) return value;
                System.out.println("Error: enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Error: enter a whole number.");
            }
        }
    }

    private static double readDouble(String label, double min, double max) {
        while (true) {
            try {
                double value = Double.parseDouble(readText(label));
                if (Double.isFinite(value) && value >= min && value <= max) return value;
                System.out.println("Error: enter a finite number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Error: enter a valid decimal number.");
            }
        }
    }

    private static BigDecimal readMoney(String label) {
        while (true) {
            try {
                BigDecimal value = new BigDecimal(readText(label));
                if (value.compareTo(BigDecimal.ZERO) > 0) return value;
                System.out.println("Error: amount must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Error: enter a valid amount.");
            }
        }
    }

    private static LocalDate readDate(String label, boolean pastOnly, boolean noFuture) {
        while (true) {
            try {
                LocalDate value = LocalDate.parse(readText(label + " (YYYY-MM-DD)"));
                if (pastOnly && !value.isBefore(LocalDate.now())) {
                    System.out.println("Error: date must be in the past.");
                } else if (noFuture && value.isAfter(LocalDate.now())) {
                    System.out.println("Error: date must not be in the future.");
                } else return value;
            } catch (DateTimeParseException e) {
                System.out.println("Error: invalid date. Use YYYY-MM-DD.");
            }
        }
    }

    private static boolean readYesNo(String label) {
        while (true) {
            int value = readInt(label + " (1 YES / 2 NO)", 1, 2);
            if (value == 1) return true;
            if (value == 2) return false;
        }
    }

    private static void printHeader(String title, int step) {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| " + title + "                    Step " + step + " of 4");
        System.out.println("| Enter values. Type BACK to return or CANCEL to discard.");
        System.out.println("+------------------------------------------------------------+");
    }

    private static boolean review(String title, String[] lines) throws ValidationException, DuplicateRecordException {
        System.out.println("\n=== " + title + " | Step 4 of 4: Review ===");
        for (int i = 0; i < lines.length; i++) System.out.println((i + 1) + ". " + lines[i]);
        int choice = readInt("1 Save  2 Edit field  0 Cancel", 0, 2);
        if (choice == 0) throw new Cancelled();
        if (choice == 2) {
            readInt("Field number to edit (0 to return)", 0, lines.length);
            throw new Cancelled();
        }
        return true;
    }

    private static void uniqueId(String id) throws DuplicateRecordException {
        for (Identifiable item : allRecords()) {
            if (item.getId().equalsIgnoreCase(id))
                throw new DuplicateRecordException("Duplicate ID: " + id);
        }
    }

    private static String validEmail(String email) {
        if (email == null || email.length() > 100 ||
            !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            return "Enter a valid email address.";
        return null;
    }

    private static void registerDepartment() throws Exception {
        printHeader("REGISTER DEPARTMENT", 1);
        String id = readUniqueId("Department ID");
        String name = readName("Department name", 2, 60);
        String code = readUniqueDepartmentCode();
        String location = requiredText("Office location");
        String email = readEmail("Contact email");
        printHeader("REGISTER DEPARTMENT", 2);
        Teacher head = null;
        if (!teachers().isEmpty() && readYesNo("Assign a head teacher now")) {
            head = choose("Select head teacher", teachers());
        }
        Department department = new Department(id, name, code, location, email, head);
        if (!review("REGISTER DEPARTMENT", new String[]{
                "01 Department ID: " + id, "02 Department name: " + name,
                "03 Department code: " + code, "04 Office location: " + location,
                "05 Contact email: " + email, "06 Head teacher: " + (head == null ? "None" : head.getName())
        })) return;
        department.validate();
        departments.add(department);
        System.out.println("+ Department " + id + " registered successfully.");
    }

    private static void registerStudent() throws Exception {
        printHeader("STUDENT REGISTRATION", 1);
        String personId = readUniqueId("01 Person ID");
        String firstName = readName("02 First name", 2, 60);
        String lastName = readName("03 Last name", 2, 60);
        String email = readUniquePersonEmail("04 Email");
        String phone = readPhone("05 Phone number");
        LocalDate birth = readDate("06 Date of birth", true, false);

        printHeader("STUDENT REGISTRATION", 2);
        String memberNumber = readUniqueMemberNumber();
        if (departments.isEmpty()) {
            System.out.println("No Department exists.");
            int add = readInt("1 Register Department now  0 Cancel", 0, 1);
            if (add == 1) registerDepartment();
            if (departments.isEmpty()) throw new PrerequisiteMissingException("Department is required.");
        }
        Department department = choose("08 Department", departments);
        LocalDate joinDate = readDate("09 Join date", false, true);
        String status = readInt("10 Academic status (1 ACTIVE / 2 INACTIVE)", 1, 2) == 1 ? "ACTIVE" : "INACTIVE";
        String institutionalEmail = readUniquePersonEmail("11 Institutional email");
        String advisor = readName("12 Advisor name", 2, 120);

        printHeader("STUDENT REGISTRATION", 3);
        String studentNumber = readUniqueStudentNumber();
        String program = readName("14 Program name", 2, 120);
        LocalDate admission = readDate("15 Admission date", false, true);
        int year = readInt("16 Study year", 1, 6);
        String guardian = readPhone("17 Guardian phone");
        boolean scholarship = readYesNo("18 Scholarship");

        Student student = new Student(personId, firstName, lastName, email, phone, birth,
                memberNumber, department, joinDate, status, institutionalEmail, advisor,
                studentNumber, program, admission, year, guardian, scholarship);
        if (!review("REVIEW STUDENT", new String[]{
                "01 Person ID: " + personId, "02 First name: " + firstName,
                "03 Last name: " + lastName, "04 Email: " + email,
                "05 Phone number: " + phone, "06 Date of birth: " + birth,
                "07 Member number: " + memberNumber, "08 Department: " + department.getDepartmentName(),
                "09 Join date: " + joinDate, "10 Academic status: " + status,
                "11 Institutional email: " + institutionalEmail, "12 Advisor name: " + advisor,
                "13 Student number: " + studentNumber, "14 Program name: " + program,
                "15 Admission date: " + admission, "16 Study year: " + year,
                "17 Guardian phone: " + guardian, "18 Scholarship: " + (scholarship ? "YES" : "NO")
        })) return;
        student.validate();
        uniqueId(personId);
        people.add(student);
        System.out.println("+ Student " + studentNumber + " registered successfully.");
    }

    private static void registerTeacher() throws Exception {
        if (departments.isEmpty()) {
            System.out.println("No Department exists.");
            int add = readInt("1 Register Department now  0 Cancel", 0, 1);
            if (add == 1) registerDepartment();
            if (departments.isEmpty()) throw new PrerequisiteMissingException("Department is required.");
        }
        printHeader("REGISTER TEACHER", 1);
        String personId = readUniqueId("Person ID");
        String firstName = readName("First name", 2, 60);
        String lastName = readName("Last name", 2, 60);
        String email = readUniquePersonEmail("Email");
        String phone = readPhone("Phone number");
        LocalDate birth = readDate("Date of birth", true, false);

        printHeader("REGISTER TEACHER", 2);
        String memberNumber = readUniqueMemberNumber();
        Department department = choose("Department", departments);
        LocalDate joinDate = readDate("Join date", false, true);
        String status = readInt("Academic status (1 ACTIVE / 2 INACTIVE)", 1, 2) == 1 ? "ACTIVE" : "INACTIVE";
        String institutionalEmail = readUniquePersonEmail("Institutional email");
        String advisor = readName("Advisor name", 2, 120);

        printHeader("REGISTER TEACHER", 3);
        String employeeNumber = readUniqueTeacherNumber();
        String specialization = requiredText("Specialization");
        LocalDate hireDate = readDate("Hire date", false, true);
        BigDecimal salary = readMoney("Salary");
        String officeNumber = requiredText("Office number");
        String employmentType = readChoice("Employment type", "FULL_TIME", "PART_TIME", "CONTRACT");

        Teacher teacher = new Teacher(personId, firstName, lastName, email, phone, birth,
                memberNumber, department, joinDate, status, institutionalEmail, advisor,
                employeeNumber, specialization, hireDate, salary, officeNumber, employmentType);
        if (!review("REVIEW TEACHER", new String[]{
                "Person ID: " + personId, "First name: " + firstName, "Last name: " + lastName,
                "Email: " + email, "Phone number: " + phone, "Date of birth: " + birth,
                "Member number: " + memberNumber, "Department: " + department.getDepartmentName(),
                "Join date: " + joinDate, "Academic status: " + status,
                "Institutional email: " + institutionalEmail, "Advisor name: " + advisor,
                "Employee number: " + employeeNumber, "Specialization: " + specialization,
                "Hire date: " + hireDate, "Salary: " + salary,
                "Office number: " + officeNumber, "Employment type: " + employmentType
        })) return;
        teacher.validate();
        people.add(teacher);
        System.out.println("+ Teacher " + employeeNumber + " registered successfully.");
    }

    private static void registerAdministrator() throws Exception {
        printHeader("REGISTER ADMINISTRATOR", 1);
        String personId = readUniqueId("Person ID");
        String firstName = readName("First name", 2, 60);
        String lastName = readName("Last name", 2, 60);
        String email = readUniquePersonEmail("Email");
        String phone = readPhone("Phone number");
        LocalDate birth = readDate("Date of birth", true, false);
        printHeader("REGISTER ADMINISTRATOR", 2);
        String staffNumber = readUniqueText("Staff number", 3, 30);
        String jobTitle = requiredText("Job title");
        String office = requiredText("Assigned office");
        LocalDate employmentDate = readDate("Employment date", false, true);
        int accessLevel = readInt("Access level", 1, 5);
        boolean active = readYesNo("Active");
        Administrator administrator = new Administrator(personId, firstName, lastName, email, phone, birth,
                staffNumber, jobTitle, office, employmentDate, accessLevel, active);
        if (!review("REVIEW ADMINISTRATOR", new String[]{
                "Person ID: " + personId, "First name: " + firstName, "Last name: " + lastName,
                "Email: " + email, "Phone number: " + phone, "Date of birth: " + birth,
                "Staff number: " + staffNumber, "Job title: " + jobTitle,
                "Assigned office: " + office, "Employment date: " + employmentDate,
                "Access level: " + accessLevel, "Active: " + (active ? "YES" : "NO")
        })) return;
        administrator.validate();
        people.add(administrator);
        System.out.println("+ Administrator " + staffNumber + " registered successfully.");
    }

    private static void registerCourse() throws Exception {
        if (departments.isEmpty() || teachers().isEmpty()) {
            System.out.println("No Department or Teacher exists.");
            int add = readInt("1 Register missing prerequisites now  0 Cancel", 0, 1);
            if (add == 1) {
                if (departments.isEmpty()) registerDepartment();
                if (teachers().isEmpty()) registerTeacher();
            }
            if (departments.isEmpty() || teachers().isEmpty())
                throw new PrerequisiteMissingException("Department and Teacher are required.");
        }
        printHeader("REGISTER COURSE", 1);
        String id = readUniqueId("Course ID");
        String code = readUniqueCourseCode();
        String title = readName("Course title", 2, 120);
        int creditHours = readInt("Credit hours", 1, 6);
        printHeader("REGISTER COURSE", 2);
        Department department = choose("Department", departments);
        List<Teacher> inDepartment = new ArrayList<>();
        for (Teacher teacher : teachers()) {
            if (teacher.getDepartment() == department) inDepartment.add(teacher);
        }
        if (inDepartment.isEmpty()) {
            System.out.println("No Teacher belongs to this Department.");
            int add = readInt("1 Register Teacher now  0 Cancel", 0, 1);
            if (add == 1) registerTeacher();
            inDepartment.clear();
            for (Teacher teacher : teachers()) {
                if (teacher.getDepartment() == department) inDepartment.add(teacher);
            }
            if (inDepartment.isEmpty()) throw new PrerequisiteMissingException("A Teacher in this Department is required.");
        }
        Teacher teacher = choose("Assigned teacher", inDepartment);
        Course course = new Course(id, code, title, creditHours, department, teacher);
        if (!review("REVIEW COURSE", new String[]{
                "Course ID: " + id, "Course code: " + code, "Course title: " + title,
                "Credit hours: " + creditHours, "Department: " + department.getDepartmentName(),
                "Assigned teacher: " + teacher.getName()
        })) return;
        course.validate();
        courses.add(course);
        System.out.println("+ Course " + code + " registered successfully.");
    }

    private static void registerClassroom() throws Exception {
        printHeader("REGISTER CLASSROOM", 1);
        String id = readUniqueId("Classroom ID");
        String building = requiredText("Building name");
        String room = requiredText("Room number");
        printHeader("REGISTER CLASSROOM", 2);
        int capacity = readInt("Capacity", 1, 100000);
        int floor = readInt("Floor number", 0, 1000);
        boolean available = readYesNo("Available");
        Classroom classroom = new Classroom(id, building, room, capacity, floor, available);
        if (!review("REVIEW CLASSROOM", new String[]{
                "Classroom ID: " + id, "Building name: " + building, "Room number: " + room,
                "Capacity: " + capacity, "Floor number: " + floor, "Available: " + (available ? "YES" : "NO")
        })) return;
        classroom.validate();
        classrooms.add(classroom);
        System.out.println("+ Classroom " + id + " registered successfully.");
    }

    private static void enrollStudent() throws Exception {
        if (students().isEmpty()) {
            System.out.println("No Student exists.");
            int add = readInt("1 Register Student now  0 Cancel", 0, 1);
            if (add == 1) registerStudent();
            if (students().isEmpty()) throw new PrerequisiteMissingException("Student is required.");
        }
        if (courses.isEmpty()) {
            System.out.println("No Course exists.");
            int add = readInt("1 Register Course now  0 Cancel", 0, 1);
            if (add == 1) registerCourse();
            if (courses.isEmpty()) throw new PrerequisiteMissingException("Course is required.");
        }
        printHeader("ENROLL STUDENT", 1);
        String id = readUniqueId("Enrollment ID");
        Student student = choose("Student", students());
        printHeader("ENROLL STUDENT", 2);
        Course course = choose("Course", courses);
        LocalDate date = readDate("Enrollment date", false, true);
        String semester = requiredText("Semester");
        String status = readInt("Enrollment status (1 ACTIVE / 2 INACTIVE)", 1, 2) == 1 ? "ACTIVE" : "INACTIVE";
        if ("ACTIVE".equals(status)) {
            for (Enrollment old : enrollments) {
                if (old.isActive() && old.getStudent() == student &&
                    old.getCourse() == course && old.getSemester().equalsIgnoreCase(semester))
                    throw new DuplicateRecordException("Active duplicate enrollment exists.");
            }
        }
        Enrollment enrollment = new Enrollment(id, student, course, date, semester, status);
        if (!review("REVIEW ENROLLMENT", new String[]{
                "Enrollment ID: " + id, "Student: " + student.getName(), "Course: " + course.getCourseTitle(),
                "Enrollment date: " + date, "Semester: " + semester, "Enrollment status: " + status
        })) return;
        enrollment.validate();
        enrollments.add(enrollment);
        System.out.println("+ Enrollment " + id + " registered successfully.");
    }

    private static void recordAssessment() throws Exception {
        List<Enrollment> active = new ArrayList<>();
        for (Enrollment enrollment : enrollments) if (enrollment.isActive()) active.add(enrollment);
        if (active.isEmpty()) {
            System.out.println("No active Enrollment exists.");
            int add = readInt("1 Register Enrollment now  0 Cancel", 0, 1);
            if (add == 1) enrollStudent();
            active.clear();
            for (Enrollment enrollment : enrollments) if (enrollment.isActive()) active.add(enrollment);
            if (active.isEmpty()) throw new PrerequisiteMissingException("Active Enrollment is required.");
        }
        printHeader("RECORD ASSESSMENT", 1);
        String id = readUniqueId("Assessment ID");
        Enrollment enrollment = choose("Active enrollment", active);
        printHeader("RECORD ASSESSMENT", 2);
        String type = readChoice("Assessment type", "QUIZ", "ASSIGNMENT", "MIDTERM", "FINAL", "PROJECT");
        double maximum = readDouble("Maximum score", 0.0001, 1000000);
        double obtained = readDouble("Obtained score", 0, maximum);
        LocalDate date = readDate("Assessment date", false, true);
        Assessment assessment = new Assessment(id, enrollment, type, maximum, obtained, date);
        if (!review("REVIEW ASSESSMENT", new String[]{
                "Assessment ID: " + id, "Enrollment: " + enrollment.getId(),
                "Assessment type: " + type, "Maximum score: " + maximum,
                "Obtained score: " + obtained, "Assessment date: " + date
        })) return;
        assessment.validate();
        assessments.add(assessment);
        System.out.println("+ Assessment " + id + " recorded successfully.");
    }

    private static void recordPayment() throws Exception {
        if (students().isEmpty()) {
            System.out.println("No Student exists.");
            int add = readInt("1 Register Student now  0 Cancel", 0, 1);
            if (add == 1) registerStudent();
            if (students().isEmpty()) throw new PrerequisiteMissingException("Student is required.");
        }
        printHeader("RECORD FEE PAYMENT", 1);
        String id = readUniqueId("Payment ID");
        Student student = choose("Student", students());
        printHeader("RECORD FEE PAYMENT", 2);
        BigDecimal amount = readMoney("Amount");
        LocalDate date = readDate("Payment date", false, true);
        String method = readChoice("Payment method", "CASH", "CARD", "MOBILE_MONEY", "BANK_TRANSFER");
        String receipt = requiredText("Receipt number");
        for (FeePayment payment : payments) {
            if (payment.getReceiptNumber().equalsIgnoreCase(receipt))
                throw new DuplicateRecordException("Duplicate receipt number: " + receipt);
        }
        FeePayment payment = new FeePayment(id, student, amount, date, method, receipt);
        if (!review("REVIEW FEE PAYMENT", new String[]{
                "Payment ID: " + id, "Student: " + student.getName(), "Amount: " + amount,
                "Payment date: " + date, "Payment method: " + method, "Receipt number: " + receipt
        })) return;
        payment.validate();
        payments.add(payment);
        System.out.println("+ Fee payment " + id + " recorded successfully.");
    }

    private static void registerBook() throws Exception {
        printHeader("REGISTER LIBRARY BOOK", 1);
        String id = readUniqueId("Book ID");
        String isbn = readUniqueIsbn();
        String title = readName("Title", 2, 120);
        String author = readName("Author", 2, 120);
        printHeader("REGISTER LIBRARY BOOK", 2);
        int total = readInt("Total copies", 0, 100000);
        int available = readInt("Available copies", 0, total);
        LibraryBook book = new LibraryBook(id, isbn, title, author, total, available);
        if (!review("REVIEW LIBRARY BOOK", new String[]{
                "Book ID: " + id, "ISBN: " + isbn, "Title: " + title, "Author: " + author,
                "Total copies: " + total, "Available copies: " + available
        })) return;
        book.validate();
        books.add(book);
        System.out.println("+ Library book " + id + " registered successfully.");
    }

    private static void borrowBook() throws Exception {
        if (students().isEmpty()) {
            System.out.println("No Student exists.");
            int add = readInt("1 Register Student now  0 Cancel", 0, 1);
            if (add == 1) registerStudent();
            if (students().isEmpty()) throw new PrerequisiteMissingException("Student is required.");
        }
        List<LibraryBook> available = new ArrayList<>();
        for (LibraryBook book : books) if (book.getAvailableCopies() > 0) available.add(book);
        if (available.isEmpty()) {
            System.out.println("No Library Book is available.");
            int add = readInt("1 Register Library Book now  0 Cancel", 0, 1);
            if (add == 1) registerBook();
            available.clear();
            for (LibraryBook book : books) if (book.getAvailableCopies() > 0) available.add(book);
            if (available.isEmpty()) throw new BookUnavailableException("No copy is available.");
        }
        printHeader("BORROW LIBRARY BOOK", 1);
        String id = readUniqueId("Loan ID");
        Student student = choose("Student", students());
        printHeader("BORROW LIBRARY BOOK", 2);
        LibraryBook book = choose("Library book", available);
        LocalDate borrowed = readDate("Borrowed date", false, true);
        LocalDate due = readDate("Due date", false, false);
        while (!due.isAfter(borrowed)) {
            System.out.println("Error: due date must be after borrowed date.");
            due = readDate("Due date", false, false);
        }
        BookLoan loan = new BookLoan(id, student, book, borrowed, due, null);
        if (!review("REVIEW BOOK LOAN", new String[]{
                "Loan ID: " + id, "Student: " + student.getName(), "Book: " + book.getTitle(),
                "Borrowed date: " + borrowed, "Due date: " + due, "Returned date: None"
        })) return;
        if (book.getAvailableCopies() <= 0)
            throw new BookUnavailableException("The selected book has no available copy.");
        loan.validate();
        loans.add(loan);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        System.out.println("+ Book borrowed successfully. Available copies: " + book.getAvailableCopies());
    }

    private static void returnBook() throws Exception {
        List<BookLoan> active = new ArrayList<>();
        for (BookLoan loan : loans) if (loan.isActive()) active.add(loan);
        if (active.isEmpty()) throw new RecordNotFoundException("No active book loan exists.");
        BookLoan loan = choose("Select active loan", active);
        LocalDate returned = readDate("Returned date", false, true);
        if (returned.isBefore(loan.getBorrowedDate()))
            throw new ValidationException("Returned date cannot be before borrowed date.");
        loan.setReturnedDate(returned);
        LibraryBook book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        System.out.println("+ Book returned successfully. Available copies: " + book.getAvailableCopies());
    }

    private static void viewAll() {
        printList("Departments", departments);
        printList("People", people);
        printList("Courses", courses);
        printList("Classrooms", classrooms);
        printList("Enrollments", enrollments);
        printList("Assessments", assessments);
        printList("Fee payments", payments);
        printList("Library books", books);
        printList("Book loans", loans);
    }

    private static void printList(String title, List<? extends Object> list) {
        System.out.println("\n--- " + title + " ---");
        if (list.isEmpty()) System.out.println("No records.");
        else for (Object item : list) System.out.println(item);
    }

    private static void searchById() throws RecordNotFoundException {
        String id = requiredText("Enter record ID");
        for (Identifiable item : allRecords()) {
            if (item.getId().equalsIgnoreCase(id)) {
                System.out.println(item.getDisplayName());
                if (item instanceof com.auca.interfaces.Registrable registrable)
                    registrable.printDetails();
                return;
            }
        }
        throw new RecordNotFoundException("No record found with ID " + id);
    }

    private static void updateRecord() throws Exception {
        String id = requiredText("Enter record ID to update");
        for (Person person : people) {
            if (person.getId().equalsIgnoreCase(id)) {
                person.setFirstName(readName("New first name", 2, 60));
                person.setLastName(readName("New last name", 2, 60));
                person.setEmail(readEmail("New email"));
                person.setPhoneNumber(readPhone("New phone number"));
                person.validate();
                System.out.println("Person updated.");
                return;
            }
        }
        for (Department department : departments) {
            if (department.getId().equalsIgnoreCase(id)) {
                department.setDepartmentName(readName("New department name", 2, 60));
                department.validate();
                System.out.println("Department updated.");
                return;
            }
        }
        for (Course course : courses) {
            if (course.getId().equalsIgnoreCase(id)) {
                course.setCourseTitle(readName("New course title", 2, 120));
                course.validate();
                System.out.println("Course updated.");
                return;
            }
        }
        for (LibraryBook book : books) {
            if (book.getId().equalsIgnoreCase(id)) {
                book.setTitle(readName("New book title", 2, 120));
                book.validate();
                System.out.println("Library book updated.");
                return;
            }
        }
        throw new RecordNotFoundException("No editable record found with ID " + id);
    }

    private static void showStudentSummary() throws RecordNotFoundException {
        List<Student> list = students();
        if (list.isEmpty()) throw new RecordNotFoundException("No Students are registered.");
        Student student = choose("Select student", list);
        System.out.println("\nAcademic summary for " + student.getName());
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent() == student) {
                System.out.println(enrollment.getRegistrationSummary());
                for (Assessment assessment : assessments) {
                    if (assessment.getEnrollment() == enrollment)
                        assessment.printDetails();
                }
            }
        }
    }

    private static void showPaymentHistory() throws RecordNotFoundException {
        List<Student> list = students();
        if (list.isEmpty()) throw new RecordNotFoundException("No Students are registered.");
        Student student = choose("Select student", list);
        boolean found = false;
        for (FeePayment payment : payments) {
            if (payment.getStudent() == student) {
                payment.printDetails();
                found = true;
            }
        }
        if (!found) System.out.println("No payment history.");
    }

    private static void showAvailableBooks() {
        boolean found = false;
        for (LibraryBook book : books) {
            if (book.getAvailableCopies() > 0) {
                book.printDetails();
                found = true;
            }
        }
        if (!found) System.out.println("No available books.");
    }

    private static void showPolymorphism() {
        System.out.println("\nPerson polymorphism:");
        for (Person person : people)
            System.out.println(person.getRoleDescription() + ": " + person.getRegistrationSummary());
        System.out.println("\nIdentifiable polymorphism:");
        for (Identifiable item : allRecords())
            System.out.println(item.getId() + " | " + item.getDisplayName() + " | active=" + item.isActive());
    }

    private static String readName(String label, int min, int max) {
        while (true) {
            String value = requiredText(label);
            if (value.length() >= min && value.length() <= max) return value;
            System.out.println("Error: " + label + " must be " + min + "-" + max + " characters.");
        }
    }

    private static String readEmail(String label) {
        while (true) {
            String value = requiredText(label);
            String error = validEmail(value);
            if (error == null) return value;
            System.out.println("Error: " + error);
        }
    }

    private static String readUniquePersonEmail(String label) throws Exception {
        while (true) {
            String email = readEmail(label);
            boolean duplicate = false;
            for (Person person : people) {
                if (person.getEmail().equalsIgnoreCase(email)) duplicate = true;
                if (person instanceof com.auca.abstracts.AcademicMember member &&
                    member.getInstitutionalEmail().equalsIgnoreCase(email)) duplicate = true;
            }
            if (!duplicate) return email;
            System.out.println("Error: duplicate email.");
        }
    }

    private static String readPhone(String label) {
        while (true) {
            String phone = requiredText(label);
            String digits = phone.replace("+", "").replace(" ", "").replace("-", "");
            if (digits.matches("\\d{10,15}") && (phone.startsWith("+") || phone.matches("[0-9 -]+")))
                return phone;
            System.out.println("Error: phone must contain 10-15 digits and may start with +.");
        }
    }

    private static String readUniqueId(String label) throws Exception {
        while (true) {
            String id = readName(label, 4, 30);
            try { uniqueId(id); return id; }
            catch (DuplicateRecordException e) { System.out.println("Error: " + e.getMessage()); }
        }
    }

    private static String readUniqueText(String label, int min, int max) {
        while (true) {
            String value = readName(label, min, max);
            boolean duplicate = false;
            for (Identifiable item : allRecords())
                if (item.getId().equalsIgnoreCase(value)) duplicate = true;
            if (!duplicate) return value;
            System.out.println("Error: duplicate " + label + ".");
        }
    }

    private static String readUniqueDepartmentCode() {
        while (true) {
            String code = requiredText("Department code");
            boolean duplicate = false;
            for (Department department : departments)
                if (department.getDepartmentCode().equalsIgnoreCase(code)) duplicate = true;
            if (!duplicate) return code;
            System.out.println("Error: duplicate department code.");
        }
    }

    private static String readUniqueCourseCode() {
        while (true) {
            String code = requiredText("Course code");
            boolean duplicate = false;
            for (Course course : courses)
                if (course.getCourseCode().equalsIgnoreCase(code)) duplicate = true;
            if (!duplicate) return code;
            System.out.println("Error: duplicate course code.");
        }
    }

    private static String readUniqueIsbn() {
        while (true) {
            String isbn = requiredText("ISBN");
            boolean duplicate = false;
            for (LibraryBook book : books)
                if (book.getIsbn().equalsIgnoreCase(isbn)) duplicate = true;
            if (!duplicate) return isbn;
            System.out.println("Error: duplicate ISBN.");
        }
    }

    private static String readUniqueMemberNumber() {
        while (true) {
            String number = readName("Member number", 4, 30);
            boolean duplicate = false;
            for (Person person : people) {
                if (person instanceof com.auca.abstracts.AcademicMember member &&
                    member.getMemberNumber().equalsIgnoreCase(number))
                    duplicate = true;
            }
            if (!duplicate) return number;
            System.out.println("Error: duplicate member number.");
        }
    }

    private static String readUniqueStudentNumber() {
        while (true) {
            String number = readName("Student number", 4, 30);
            boolean duplicate = false;
            for (Student student : students())
                if (student.getStudentNumber().equalsIgnoreCase(number))
                    duplicate = true;
            if (!duplicate) return number;
            System.out.println("Error: duplicate student number.");
        }
    }

    private static String readUniqueTeacherNumber() {
        while (true) {
            String number = readName("Employee number", 4, 30);
            boolean duplicate = false;
            for (Teacher teacher : teachers())
                if (teacher.getEmployeeNumber().equalsIgnoreCase(number))
                    duplicate = true;
            if (!duplicate) return number;
            System.out.println("Error: duplicate employee number.");
        }
    }

    private static String readChoice(String label, String... choices) {
        while (true) {
            System.out.print(label + " (");
            for (int i = 0; i < choices.length; i++) {
                if (i > 0) System.out.print(" / ");
                System.out.print((i + 1) + " " + choices[i]);
            }
            System.out.print("): ");
            try {
                int selected = Integer.parseInt(scanner.nextLine().trim());
                if (selected >= 1 && selected <= choices.length) return choices[selected - 1];
            } catch (NumberFormatException e) {}
            System.out.println("Error: choose a displayed number.");
        }
    }

    private static <T> T choose(String label, List<T> list) throws RecordNotFoundException {
        if (list.isEmpty()) throw new RecordNotFoundException("No " + label + " exists.");
        System.out.println("Select " + label + ":");
        for (int i = 0; i < list.size(); i++) {
            T item = list.get(i);
            if (item instanceof Department department)
                System.out.println((i + 1) + ". " + department.getRegistrationSummary());
            else if (item instanceof Student student)
                System.out.println((i + 1) + ". " + student.getRegistrationSummary());
            else if (item instanceof Teacher teacher)
                System.out.println((i + 1) + ". " + teacher.getRegistrationSummary());
            else if (item instanceof Course course)
                System.out.println((i + 1) + ". " + course.getRegistrationSummary());
            else if (item instanceof Enrollment enrollment)
                System.out.println((i + 1) + ". " + enrollment.getRegistrationSummary());
            else if (item instanceof LibraryBook book)
                System.out.println((i + 1) + ". " + book.getRegistrationSummary());
            else if (item instanceof BookLoan loan)
                System.out.println((i + 1) + ". " + loan.getRegistrationSummary());
            else System.out.println((i + 1) + ". " + item);
        }
        int choice = readInt("Selection", 1, list.size());
        return list.get(choice - 1);
    }

    private static List<Student> students() {
        List<Student> list = new ArrayList<>();
        for (Person person : people) if (person instanceof Student student) list.add(student);
        return list;
    }

    private static List<Teacher> teachers() {
        List<Teacher> list = new ArrayList<>();
        for (Person person : people) if (person instanceof Teacher teacher) list.add(teacher);
        return list;
    }

    private static List<Identifiable> allRecords() {
        List<Identifiable> records = new ArrayList<>();
        records.addAll(people);
        records.addAll(departments);
        records.addAll(courses);
        records.addAll(classrooms);
        records.addAll(enrollments);
        records.addAll(assessments);
        records.addAll(payments);
        records.addAll(books);
        records.addAll(loans);
        return records;
    }
}
