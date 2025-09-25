// File: src/edu/ccrm/cli/CliHandler.java
package edu.ccrm.cli;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.DataPersistenceService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.util.FileUtils;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handles all command-line interactions for the CCRM application.
 */
public class CliHandler {

    private final Scanner scanner;
    private final DataStore dataStore;
    private final EnrollmentService enrollmentService;
    private final DataPersistenceService persistenceService;
    private final BackupService backupService;

    public CliHandler(EnrollmentService enrollmentService) {
        this.scanner = new Scanner(System.in);
        this.dataStore = DataStore.getInstance(); //Get the singleton instance
        this.enrollmentService = enrollmentService;
        this.persistenceService = new DataPersistenceService();
        this.backupService = new BackupService();
    }

    /**
     *Starts the main application loop.
     */
    public void start() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1: manageStudents(); break;
                case 2: manageCourses(); break;
                case 3: manageEnrollments(); break;
                case 4: exportData(); break;
                case 5: importData(); break;
                case 6: performBackup(); break;
                case 7: showBackupSize(); break;
                case 0: running = false; break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using CCRM. Goodbye! ");
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("\n--- 🎓 Campus Course & Records Manager ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments");
        System.out.println("4. Export Data");
        System.out.println("5. Import Data");
        System.out.println("6. Perform Backup");
        System.out.println("7. Show Total Backup Size");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // --- Student Management ---

    private void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. List All Students");
        System.out.println("3. Find Student by Registration Number");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");

        switch (getUserChoice()) {
            case 1: addStudent(); break;
            case 2: listStudents(); break;
            case 3: findStudent(); break;
            case 0: break;
            default: System.out.println("Invalid choice.");
        }
    }

    private void addStudent() {
        try {
            System.out.println("\n--- Add New Student ---");
            System.out.print("Enter ID (integer): ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter Registration Number (e.g., S001): ");
            String regNo = scanner.nextLine();

            Student newStudent = new Student(id, name, email, dob, regNo);
            dataStore.getStudents().add(newStudent);
            System.out.println("Student added successfully!");
        } catch (NumberFormatException | DateTimeParseException e) {
            System.err.println("Invalid input format. Please try again. " + e.getMessage());
        }
    }

    private void listStudents() {
        System.out.println("\n--- List of All Students ---");
        List<Student> students = dataStore.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(s -> System.out.println(s.getProfile()));
        }
    }

    private void findStudent() {
        System.out.print("Enter Registration Number to find: ");
        String regNo = scanner.nextLine();
        findStudentByRegNo(regNo)
            .ifPresentOrElse(
                student -> System.out.println(student.getProfile()),
                () -> System.out.println("Student not found with Registration Number: " + regNo)
            );
    }
    
    // --- Course Management ---

    private void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add New Course");
        System.out.println("2. List All Courses");
        System.out.println("0. Back to Main Menu");
        System.out.print(" Enter your choice: ");

        switch (getUserChoice()) {
            case 1: addCourse(); break;
            case 2: listCourses(); break;
            case 0: break;
            default: System.out.println("Invalid choice.");
        }
    }
    
    private void addCourse(){
        try {
            System.out.println("\n--- Add New Course ---");
            System.out.print("Enter Course Code (e.g., CS101): ") ;
            String code = scanner.nextLine();
            System.out.print("Enter Course Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter Credits (integer): ");
            int credits = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Department: ");
            String department = scanner.nextLine();
            
            //Using the builder pattern to create the course object
            Course newCourse = new Course.Builder(code, title)
                .credits(credits)
                .department(department)
                .build();
            
            dataStore.getCourses().add(newCourse);
            System.out.println("Course added successfully!");
        } catch (NumberFormatException e) {
            System.err.println("Invalid input for credits. Please enter a number.");
        }
    }
    
    private void listCourses() {
        System.out.println("\n--- List of All Courses ---");
        List<Course> courses = dataStore.getCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    //--- Enrollment Management ---

    private void manageEnrollments() {
        System.out.println("\n--- Enrollment Management ---");
        System.out.println("1. Enroll Student in a Course");
        System.out.println("2. Unenroll Student from a Course");
        System.out.println("3. View a Student's Enrolled Courses");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");

        switch (getUserChoice()) {
            case 1: enrollStudentInCourse(); break;
            case 2: unenrollStudentFromCourse(); break;
            case 3: viewStudentCourses(); break;
            case 0: break;
            default: System.out.println("Invalid choice.");
        }
    }

    private void enrollStudentInCourse() {
        System.out.print("Enter Student Registration Number: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        Optional<Student> studentOpt = findStudentByRegNo(regNo);
        Optional<Course> courseOpt = findCourseByCode(courseCode);
        
        if (studentOpt.isPresent() && courseOpt.isPresent()) {
            try {
                enrollmentService.enrollStudent(studentOpt.get(), courseOpt.get());
                System.out.println("Enrollment successful!");
            } catch (MaxCreditLimitExceededException | DuplicateEnrollmentException e) {
                System.err.println("Enrollment failed: " + e.getMessage());
            }
        } else {
            System.err.println("Student or Course not found. Please check the details.");
        }
    }

    private void unenrollStudentFromCourse() {
         System.out.print("Enter Student Registration Number: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        Optional<Student> studentOpt = findStudentByRegNo(regNo);
        Optional<Course> courseOpt = findCourseByCode(courseCode);
        
        if (studentOpt.isPresent() && courseOpt.isPresent()) {
            enrollmentService.unenrollStudent(studentOpt.get(), courseOpt.get());
            System.out.println("Unenrollment successful!");
        } else {
            System.err.println("Student or Course not found.");
        }
    }

    private void viewStudentCourses() {
        System.out.print("Enter Student Registration Number: ");
        String regNo = scanner.nextLine();
        System.out.println("\n--- Courses for " + regNo + " ---");
        List<Course> courses = enrollmentService.findCoursesByStudent(regNo);
        if (courses.isEmpty()) {
            System.out.println("Student is not enrolled in any courses.");
        } else {
            courses.forEach(System.out::println);
        }
    }
    
    // --- Data & Backup Operations ---

    private void exportData() {
        persistenceService.exportStudents(dataStore.getStudents(), "students_export.csv");
        // You can add export for courses here as well
    }

    private void importData() {
        List<Student> importedStudents = persistenceService.importStudents("students_export.csv");
        if (!importedStudents.isEmpty()) {
            dataStore.getStudents().clear(); // Clear existing data before import
            dataStore.getStudents().addAll(importedStudents);
            System.out.println("Imported " + importedStudents.size() + " students.");
        }
    }
    
    private void performBackup() {
        backupService.performBackup();
    }
    
    private void showBackupSize() {
        try {
            long size = FileUtils.calculateDirectorySize(Paths.get("backup"));
            System.out.printf("Total size of all backups: %.2f MB\n", size / (1024.0 * 1024.0));
        } catch (Exception e) {
            System.err.println("Could not calculate backup size: " + e.getMessage());
        }
    }

    // --- Utility Methods ---

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Return an invalid choice
        }
    }
    
    private Optional<Student> findStudentByRegNo(String regNo) {
        return dataStore.getStudents().stream()
                .filter(s -> s.getRegNo().equalsIgnoreCase(regNo))
                .findFirst();
    }
    
    private Optional<Course> findCourseByCode(String courseCode) {
        return dataStore.getCourses().stream()
                .filter(c -> c.getCode().equalsIgnoreCase(courseCode))
                .findFirst();
    }
}