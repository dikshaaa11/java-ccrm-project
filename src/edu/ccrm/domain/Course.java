// File: src/edu/ccrm/domain/Course.java
package edu.ccrm.domain;

import java.util.Objects;

/**
 * Represents a Course. This class uses the Builder design pattern for object creation. 
 * It also demonstrates a static nested class (the Builder itself). 
 */
public class Course {

    private final String code; // e.g., "CS101"
    private final String title;
    private final int credits;
    private final String department;
    private Instructor instructor;
    private Semester semester;

    // Private constructor to enforce the use of the Builder
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.department = builder.department;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
    }

    // --- Getters (no setters to make fields effectively final after creation) ---

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getDepartment() { return department; }
    public Instructor getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }

    // --- We can have a setter for instructor as they might be assigned later ---
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "Course [Code=" + code + ", Title=" + title + ", Credits=" + credits + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }


    // --- Static Nested Builder Class ---
    public static class Builder {
        // Required parameters
        private final String code;
        private final String title;

        // Optional parameters - initialized to default values
        private int credits = 3;
        private String department = "General";
        private Instructor instructor = null;
        private Semester semester = Semester.FALL ;

        public Builder(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this; //Return the builder for chaining
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        // The final build method that  returns the constructed Course object
        public Course build() {
            return new Course(this) ;
        }
    }
}