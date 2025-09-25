// File: src/edu/ccrm/domain/Person.java
package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * An abstract base class representing a person in the institution.
 * Demonstrates Abstraction by defining a common template for Students and Instructors.
 * Demonstrates Encapsulation with private fields and public getters/setters. [cite: 59, 61]
 */
public abstract class Person {

    // private fields for Encapsulation
    private int id;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;

    // Constructor to initialize Person objects
    public Person(int id, String fullName, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    // Abstract method - subclasses MUST provide their own implementation
    public abstract String getProfile();

    // --- Standard Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    // Overriding toString() for better object representation [cite:  77]
    @Override
    public String toString() {
        return "Person [ID=" + id + ", Name=" + fullName + ", Email=" + email + "]";
    }

    // Overriding equals and hashCode for proper object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false ;
        Person person = (Person) o;
        return id == person.id && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}