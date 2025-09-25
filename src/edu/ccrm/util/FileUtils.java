// File: src/edu.ccrm/util/FileUtils.java
package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileUtils {

    /**
     * Recursively calculates the size of a directory.
     * @param path The directory path.
     * @return The total size in bytes.
     */
    public static long calculateDirectorySize(Path path) throws IOException {
        try (Stream<Path> walk = Files.walk(path)) {
            return walk
                .filter(Files::isRegularFile)
                .mapToLong(p -> {
                    try {
                        return Files.size(p);
                    } catch (IOException e) {
                        return 0L;
                    }
                })
                .sum();
        }
    }
}