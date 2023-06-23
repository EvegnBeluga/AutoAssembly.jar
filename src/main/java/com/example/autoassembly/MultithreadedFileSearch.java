package com.example.autoassembly;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultithreadedFileSearch {

    private final String rootDirectory;
    private final String fileToFind;
    private final String fileMask;

    public MultithreadedFileSearch(String rootDirectory, String fileToFind, String fileMask) {
        this.rootDirectory = rootDirectory;
        this.fileToFind = fileToFind;
        this.fileMask = fileMask;
    }

    public List<String> search() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(15);
        List<String> results = new ArrayList<>();
        List<Future<List<String>>> futures = new ArrayList<>();

        File rootDir = new File(rootDirectory);
        if (rootDir.exists()) {
            search(rootDir, executorService, futures);
        }

        for (Future<List<String>> future : futures) {
            results.addAll(future.get());
        }

        executorService.shutdown();
        return results;
    }

    private void search(File dir, ExecutorService executorService, List<Future<List<String>>> futures) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    search(file, executorService, futures);
                }
                if (file.getName().equals(fileToFind) || (file.getName().matches(fileMask))) {
                    futures.add(executorService.submit(new FileSearchTask(file)));
                }
            }
        }
    }
}


