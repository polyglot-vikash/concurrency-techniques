import com.example.utils.PatternFinder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * This class takes a list of files under folder /src/sample
 * and returns all the line numbers of a file which contains a special pattern(mmultithreaded code)
 */

public class PatternSearchParallel {
    private static PatternFinder patternFinder;

    public static void main(String args[]) throws Exception {
        String pattern = "public";
        File dir = new File("./src/sample");
        File[] files = dir.listFiles();

        // Dependency Injection
        setPatternFinder(new PatternFinder());

        ExecutorService executor = Executors.newFixedThreadPool(3);
        Map<String, Object> resultMap = new HashMap<String, Object>();

        long startTime = System.currentTimeMillis();
        for (File file : files) {
            Future<List<Integer>> future = executor.submit(new Callable<List<Integer>>() {

                @Override
                public List<Integer> call() throws Exception {
                    List<Integer> lineNumbers = patternFinder.find(file, pattern);
                    return lineNumbers;
                }
            });

            resultMap.put(file.getName(), future);
        }
        waitForAll(resultMap);
        long endTime = System.currentTimeMillis();

        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            System.out.println("pattern " + pattern + "  found at " + entry.getValue() + " for file " + entry.getKey());
        }
        System.out.println("Time taken to complete in milli seconds " + (endTime - startTime));
    }

    private static void waitForAll(Map<String, Object> resultMap) throws Exception {
        Set<String> keys = resultMap.keySet();
        List<Future<List<Integer>>> futures = resultMap.values().stream().map(future -> (Future<List<Integer>>) future).collect(Collectors.toList());
        while (!isAllCompleted(futures)) {
            // wait
        }
        for (String key : keys) {
            Future<List<Integer>> future = (Future<List<Integer>>) resultMap.get(key);
            resultMap.put(key, future.get());
        }
    }

    private static boolean isAllCompleted(List<Future<List<Integer>>> futures) {
        return futures.stream().filter(future -> !future.isDone()).collect(Collectors.toList()).isEmpty();
    }

    private static void setPatternFinder(PatternFinder finder) {
        patternFinder = finder;
    }
}
