// File: src/edu.ccrm/config/DataStore.java
package edu.ccrm.config;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * A Singleton class to hold in-memory application data.
 * This ensures that there is only one instance of the data lists throughout the app.
 */
public class DataStore {

    // The single, static instance of this class
    private static DataStore instance;

    private final List<Student> students;
    private final List<Course> courses;

    // Private constructor prevents anyone else from creating an instance
    private DataStore() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        // We can add some initial dummy data here if we want
    }

    // The public, static method to get the single instance
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // Public methods to access the data
    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }
}