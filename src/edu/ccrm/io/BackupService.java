// File: src/edu.ccrm/io/BackupService.java
package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles backup operations using NIO.2.
 */
public class BackupService{

    private static final Path DATA_DIRECTORY = Paths.get("data");
    private static final Path BACKUP_DIRECTORY = Paths.get("backup");

    public void performBackup() {
        try {
            if (!Files.exists(DATA_DIRECTORY)) {
                System.out.println("Data directory does not exist. Nothing to back up.");
                return;
            }

            // Create a timestamped folder name e.g., "backup_2025-09-25_18-30-00"
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            Path newBackupDir = BACKUP_DIRECTORY.resolve("backup_" + timestamp);
            Files.createDirectories(newBackupDir);

            // Copy files from 'data' to the new backup folder
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(DATA_DIRECTORY)) {
                for (Path file : stream) {
                    Files.copy(file, newBackupDir.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                }
            }
            System.out.println("Backup completed successfully to: "  + newBackupDir.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Failed to perform backup: " + e.getMessage());
        }
    }
}