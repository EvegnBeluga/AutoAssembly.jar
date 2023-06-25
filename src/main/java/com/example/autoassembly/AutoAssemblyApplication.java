package com.example.autoassembly;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class AutoAssemblyApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        String rootDirectory = "C:\\Users\\Евгений\\OneDrive\\Рабочий стол\\музыка Наталии"; //корневая директория
        String fileToFind = "солнышко.mp3"; //имя искомого файла
        String mask = "*.mp3"; // маска

        MultithreadedFileSearch fileSearch = new MultithreadedFileSearch(rootDirectory, fileToFind, mask);
        List<String> results = fileSearch.search();

        for (String file : results) {
            System.out.println(file);

            String destinationDir = "C:\\Users\\Евгений\\OneDrive\\Рабочий стол\\test\\"; //целевая директория
            FileCopy fileCopy = new FileCopy();
            fileCopy.copyFiles(file, rootDirectory, destinationDir);
        }
    }
}
