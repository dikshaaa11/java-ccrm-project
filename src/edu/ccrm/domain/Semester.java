// File: src/edu/ccrm/domain/Semester.java
package edu.ccrm.domain;

public enum Semester {
    FALL("Fall"),
    SPRING("Spring"),
    SUMMER("Summer") ;

    private final String displayName;

    // Enum constructor [cite: 74]
    Semester(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}