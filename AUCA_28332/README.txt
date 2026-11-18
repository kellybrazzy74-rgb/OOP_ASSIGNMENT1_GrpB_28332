AUCA_28332 Console School Management System

Requirements:
- Java 17 or later
- Run run.bat

Manual compilation:
javac -d bin src\com\auca\interfaces\*.java src\com\auca\exceptions\*.java src\com\auca\abstracts\*.java src\com\auca\student\*.java src\com\auca\teacher\*.java src\com\auca\administrator\*.java src\com\auca\department\*.java src\com\auca\course\*.java src\com\auca\classroom\*.java src\com\auca\enrollment\*.java src\com\auca\assessment\*.java src\com\auca\feepayment\*.java src\com\auca\librarybook\*.java src\com\auca\bookloan\*.java src\com\auca\main\*.java

Run:
java -cp bin com.auca.main.Main

Registration:
- All entity forms use four steps, numbered fields, and Scanner-based input.
- Type BACK to return to the previous step (or the main menu from Step 1), or CANCEL to discard the current form.
- The final review lists all fields and offers Save, Edit field, Back, and Cancel.
- Required related records can be registered from their numbered selector; the original form resumes afterward.
- Student registration follows the 18-field Person, AcademicMember, and Student layout in the assignment, including nested Department registration.
