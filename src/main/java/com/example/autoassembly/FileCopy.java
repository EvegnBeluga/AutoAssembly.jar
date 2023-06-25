package com.example.autoassembly;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileCopy {

    public void copyFiles(String file, String rootDirectory, String destinationDir) throws IOException {
        Path source = Paths.get(rootDirectory);
        Path target = Paths.get(destinationDir);
        String extension = file.substring(file.lastIndexOf(".") + 1);

        Files.walk(source)
                .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(extension))
                .forEach(sourceFile -> {
                    try {
                        Path targetFile = target.resolve(source.relativize(sourceFile));
                        //Files.createDirectories(targetFile.getParent());
                        Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.out.println("Error copying file: " + e.getMessage());
                    }
                });
    }
}
