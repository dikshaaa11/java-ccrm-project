// File: src/edu/ccrm/io/DataPersistenceService.java
package edu.ccrm.io;

import edu.ccrm.domain.Student;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles importing and exporting data to/from text files using NIO.2 and Streams.
 */
public class DataPersistenceService {

    /**
     * Exports a list of students to a CSV-like file.
     * Demonstrates Files.write and Streams.
     * @param students The list of students to export.
     * @param filename The name of the file to create (e.g., "students.csv").
     */
    public void exportStudents(List<Student> students, String filename) {
        Path filePath = Paths.get("data", filename); // Creates a path like "data/students.csv"
        try {
            // Ensure the parent directory exists
            Files.createDirectories(filePath.getParent());

            // Map each student object to a CSV string line
            List<String> lines = students.stream()
                    .map(s -> String.join(",",
                            String.valueOf(s.getId()),
                            s.getRegNo(),
                            s.getFullName(),
                            s.getEmail(),
                            s.getDateOfBirth().toString()
                    ))
                    .collect(Collectors.toList());

            // Write all lines to the file
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Successfully exported " + students.size() + " students to " + filePath.toAbsolutePath());

        } catch (IOException e){
            System.err.println("Error exporting student data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Imports students from a CSV-like text file.
     * Demonstrates Files.lines to get a Stream of lines from a file .
     * @param filename The file to read from.
     * @return A list of Student objects.
     */
    public List<Student> importStudents(String filename) {
        Path filePath = Paths.get("data", filename);

        if (!Files.exists(filePath)) {
            System.err.println("Import file not found: " + filePath.toAbsolutePath());
            return List.of(); // Return an empty list if file doesn't exist
        }

        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length < 5) return null; // Basic validation
                        // Parse each part and create a new Student object
                        int id = Integer.parseInt(parts[0]);
                        String regNo = parts[1];
                        String fullName = parts[2];
                        String email = parts[3];
                        LocalDate dob = LocalDate.parse(parts[4]);
                        return new Student(id, fullName, email, dob, regNo);
                    })
                    .filter(s -> s != null) //Filter out any lines that failed to parse
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error importing student data: " + e.getMessage());
            return List.of();
        }
    }
}