// File: src/edu/ccrm/service/EnrollmentService.java
package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

import java.util.List;

/**
 * An interface defining the contract for enrollment operations.
 * This demonstrates abstraction and prepares our code for different implementations.
 */
public interface EnrollmentService{

    void enrollStudent(Student student, Course course) throws MaxCreditLimitExceededException, DuplicateEnrollmentException;

    void unenrollStudent(Student student, Course course);

    List<Student> findStudentsByCourse(String courseCode);

    List<Course> findCoursesByStudent(String studentRegNo);
}