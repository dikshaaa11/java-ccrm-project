// File: src/edu/ccrm/domain/Grade.java
package edu.ccrm.domain;

/**
 * Enum representing grades with associated grade points.
 * Demonstrates an enum with fields and a constructor. [cite: 74]
 */
public enum Grade{
    S(10.0),
    A(9.0),
    B(8.0),
    C(7.0),
    D(6.0),
    E(5.0),
    F(0.0);

    private final double gradePoint;

    Grade(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public double getGradePoint() {
        return gradePoint ;
    }
}