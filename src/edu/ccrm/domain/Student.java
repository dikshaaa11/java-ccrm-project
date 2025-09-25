// File: src/edu/ccrm/domain/Student.java
package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Student, inheriting properties from Person.
 * Demonstrates Inheritance. [cite: 60]
 */
public class Student extends Person  {

    private String regNo;
    private StudentStatus status;
    private List<Course> enrolledCourses; // A student can enroll in many courses

    public Student(int id, String fullName, String email, LocalDate dateOfBirth, String regNo) {
        // Call the constructor of the parent class (Person) using 'super' 
        super(id, fullName, email, dateOfBirth);
        this.regNo = regNo;
        this.status = StudentStatus.ACTIVE; // Default status
        this.enrolledCourses = new ArrayList<>();
    }

    //Implementation of the abstract method from Person class
    @Override
    public String getProfile(){
        return String.format("Student Profile:\nID: %d\nRegNo: %s\nName: %s\nStatus: %s",
                getId(), getRegNo(), getFullName(), getStatus());
    }

    // --- Student-specific methods ---

    public void enrollInCourse(Course course) {
        if (course != null && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void unenrollFromCourse(Course course) {
        enrolledCourses.remove(course);
    }


    // -- Getters and Setters ---

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student [RegNo=" + getRegNo() + ", Name=" + getFullName() + "]";
    }
}

//An enum to represent the student's status. Enums are great for fixed sets of constants.
enum StudentStatus {
    ACTIVE,
    INACTIVE,
    GRADUATED
}