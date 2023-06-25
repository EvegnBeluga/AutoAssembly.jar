package com.example.autoassembly;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedFileSearch {

    private final String rootDirectory;
    private final String fileToFind;
    private final String mask;

    public MultithreadedFileSearch(String rootDirectory, String fileToFind, String mask) {
        this.rootDirectory = rootDirectory;
        this.fileToFind = fileToFind;
        this.mask = mask;
    }

    public List<String> search() throws InterruptedException, ExecutionException, IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<String> results = new ArrayList<>();
        List<Future<List<String>>> futures = new ArrayList<>();

        File rootDir = new File(rootDirectory);
        if (rootDir.exists()) {
            search(rootDir, executorService, futures);
            searchMask(rootDir, executorService, futures);
        }
        for (Future<List<String>> future : futures) {
            results.addAll(future.get());
        }
        executorService.shutdown();
        return results;
    }

    private void searchMask(File dir, ExecutorService executorService, List<Future<List<String>>> futures) throws IOException {
        if (dir.isDirectory()) {
            Path directory = Path.of(dir.toURI());
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + mask);

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (Path path : stream) {
                    if (Files.isRegularFile(path) && pathMatcher.matches(path.getFileName())) {
                        futures.add(executorService.submit(new FileSearchTask(path.toFile())));
                    }
                }
            }
        }
    }

    private void search(File dir, ExecutorService executorService, List<Future<List<String>>> futures) {

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {

                for (File file : files) {
                    if (file.isDirectory()) {
                        search(file, executorService, futures);
                    }
                    if (file.getName().equals(fileToFind)) {
                        futures.add(executorService.submit(new FileSearchTask(file)));
                    }
                }
            }
        }
    }
}

