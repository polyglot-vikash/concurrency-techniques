package com.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PatternFinder {
    public List<Integer> find(File file, String pattern) {
        List<Integer> lineNumbers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Integer lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if (line.contains(pattern)) {
                    lineNumbers.add(lineNumber);
                }
                lineNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // to show the illusion of a complex processing sleeping for 500MS
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lineNumbers;
    }
}
