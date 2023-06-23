package com.example.autoassembly;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class AutoAssemblyApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String rootDirectory = "C:\\Users\\Евгений\\IdeaProjects";
        String fileToFind = "Maven__com_fasterxml_jackson_datatype_jackson_datatype_jdk8_2_14_0_rc3.xml";
        String fileMask = "spri*";
        MultithreadedFileSearch fileSearch = new MultithreadedFileSearch(rootDirectory, fileToFind, fileMask);
        List<String> results = fileSearch.search();
        for (String file : results) {
            System.out.println(file);
        }
    }

}
