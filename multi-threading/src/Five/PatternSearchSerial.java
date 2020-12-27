package Five;

import com.example.utils.PatternFinder;

import java.io.File;
import java.util.List;

/**
 * This class takes a list of files under folder /src/sample
 * and returns all the line numbers of a file which contains a special pattern(serially)
 */

public class PatternSearchSerial {
    private static PatternFinder patternFinder;

    public static void main(String args[]) {
        String pattern = "public";
        File dir = new File("./src/sample");
        File[] files = dir.listFiles();

        // Dependency Injection
        setPatternFinder(new PatternFinder());

        long startTime = System.currentTimeMillis();
        for (File file : files) {
            List<Integer> lineNumbers = patternFinder.find(file, pattern);
            if (!lineNumbers.isEmpty()) {
                System.out.println("pattern " + pattern + " found at " + lineNumbers + " in file " + file.getName());
            }

        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to complete in milli seconds " + (endTime-startTime));
    }

    private static void setPatternFinder(PatternFinder finder) {
        patternFinder = finder;
    }
}
