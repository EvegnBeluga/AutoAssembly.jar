package com.example.autoassembly;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public record FileSearchTask(File file) implements Callable<List<String>>{
    @Override
    public List<String> call() {
        List<String> results = new ArrayList<>();
        results.add(file.getAbsolutePath());
        System.out.println(file.getAbsolutePath());
        return results;
    }
}
