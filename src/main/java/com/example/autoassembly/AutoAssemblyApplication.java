package com.example.autoassembly;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class AutoAssemblyApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        String rootDirectory = "path\\to\\source\\folder"; //корневая директория
        String fileToFind = "fileName"; //имя искомого файла
        String mask = "mask"; // маска

        MultithreadedFileSearch fileSearch = new MultithreadedFileSearch(rootDirectory, fileToFind, mask);
        List<String> results = fileSearch.search();

        for (String file : results) {
            System.out.println(file);

            String destinationDir = "path\\to\\target\\folder"; //целевая директория
            FileCopy fileCopy = new FileCopy();
            fileCopy.copyFiles(file, rootDirectory, destinationDir);
        }
    }
}
