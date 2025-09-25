// File: src/edu.ccrm/Main.java
package edu.ccrm;

import edu.ccrm.cli.CliHandler;
import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.EnrollmentServiceImpl;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        System.out.println("Starting CCRM Application...");

        // Use the Singleton DataStore to get our lists
        DataStore dataStore = DataStore.getInstance();
        
        // --- Setup initial data for testing ---
        Student student1 = new Student(1, "Alice Johnson", "alice@example.com", LocalDate.of(2002, 5, 20), "S001");
        Instructor profDavis = new Instructor(101, "Prof. Robert Davis", "davis@example.com", LocalDate.of(1975, 11, 30), "Computer Science", "Professor");
        
        // Use the Course Builder
        Course cs101 = new Course.Builder("CS101", "Intro to Programming" )
                .credits(4)
                .department("Computer Science")
                .instructor(profDavis)
                .semester(Semester.FALL)
                .build();
        
        dataStore.getStudents().add(student1);
        dataStore.getCourses().add(cs101);

        // Initialize the services with the data from the DataStore
        EnrollmentService enrollmentService = new EnrollmentServiceImpl(dataStore.getStudents(), dataStore.getCourses());

        // Initialize and start the command-line interface
        CliHandler cli = new CliHandler(enrollmentService);
        cli.start();
    }
}