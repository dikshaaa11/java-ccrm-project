// File: src/edu/ccrm/service/EnrollmentServiceImpl.java
package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of the EnrollmentService.
 * This class handles the business logic for enrollments.
 */
public class EnrollmentServiceImpl implements EnrollmentService {

    // For now, we manage data in memory. Later, this could be a database.
    private final List<Student> allStudents;
    private final List<Course> allCourses;

    // A constant for a business rule
    private static final int MAX_CREDITS_PER_SEMESTER = 18;

    public EnrollmentServiceImpl(List<Student> students, List<Course> courses) {
        this.allStudents = students;
        this.allCourses = courses;
    }

    @Override
    public void enrollStudent(Student student, Course course) throws MaxCreditLimitExceededException, DuplicateEnrollmentException {
        // Rule 1: Check if the student is already enrolled in this course.
        if (student.getEnrolledCourses().contains(course)){
            throw new DuplicateEnrollmentException("Student " + student.getRegNo() + " is already enrolled in course " + course.getCode());
        }

        // Rule 2: Check the credit limit.
        // This demonstrates the Stream API for a calculation.
        int currentCredits = student.getEnrolledCourses().stream()
                .filter(c -> c.getSemester() == course.getSemester()) // Only count credits for the same semester
                .mapToInt(Course::getCredits) // Map each course to its credit value
                .sum(); // Sum them up

        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException("Cannot enroll. Exceeds max credit limit of " + MAX_CREDITS_PER_SEMESTER + " for the semester.");
        }

        // If all rules pass, enroll the student.
        student.enrollInCourse(course);
        System.out.println("Enrollment successful for " + student.getFullName() + " in " + course.getTitle());
    }

    @Override
    public void unenrollStudent(Student student, Course course) {
        student.unenrollFromCourse(course);
        System.out.println("Unenrolled " + student.getFullName() + " from " + course.getTitle());
    }

    @Override
    public List<Student> findStudentsByCourse(String courseCode) {
        // This demonstrates filtering with a Lambda expression.
        return allStudents.stream()
                .filter(student -> student.getEnrolledCourses().stream()
                        .anyMatch(course -> course.getCode().equalsIgnoreCase(courseCode)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findCoursesByStudent(String studentRegNo) {
        // Find the student first, then return their  courses.
        return allStudents.stream()
                .filter(student -> student.getRegNo().equalsIgnoreCase(studentRegNo))
                .findFirst() //Find the first match
                .map(Student::getEnrolledCourses) // If found, get their list of courses
                .orElse(new ArrayList<>()); // If not found, return an empty list
    }
}