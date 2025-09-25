// File: src/edu/ccrm/domain/Instructor.java
package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Represents an Instructor, inheriting from Person.
 */
public class Instructor extends Person {

    private String department;
    private String title; // e.g., "Professor", "Lecturer"

    public Instructor(int id, String fullName, String email, LocalDate dateOfBirth, String department, String title) {
        super(id, fullName, email, dateOfBirth);
        this.department = department;
        this.title = title;
    }

    @Override
    public String getProfile() {
        return String.format("Instructor Profile:\nID: %d\nName: %s (%s)\nDepartment: %s",
                getId(), getFullName(), getTitle(), getDepartment());
    }

    //--- Getters and Setters ---

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}