package Three;

import com.example.io.utils.IOUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CopyTask implements Runnable {
    String sourceFile;
    String destinationFile;

    public CopyTask(String sourceFile, String destinationFile) {
        this.sourceFile = sourceFile;
        this.destinationFile = destinationFile;
    }

    @Override
    public void run() {
        try {
            IOUtils.copyFile(sourceFile, destinationFile);
            System.out.println("Copied from : " + sourceFile + " to " + destinationFile);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}

public class ExecutorServiceDemo {
    public static void main(String args[]) throws Exception{
        String sourceFile1 = "a.txt";
        String sourceFile2 = "b.txt";
        String destinationFile1 = "c.txt";
        String destinationFile2 = "d.txt";
        /** Old approach */
//        // Create two threads and submit them to start method
//        Thread t1 = new Thread(new Three.CopyTask(sourceFile1, destinationFile1));
//        t1.start();
//        Thread t2 = new Thread(new Three.CopyTask(sourceFile2, destinationFile2));
//        t2.start();
//        System.out.println("Done copying files...");

        // This will create a pool of threads to execute tasks
        // 100 files -> 10 threads, each thread wil copy 10 files
        // once a thread is busy, tasks are put in blocking queue and once thread
        // it will execute
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(new CopyTask(sourceFile1, destinationFile1));
        executorService.execute(new CopyTask(sourceFile2, destinationFile2));
        System.out.println("Done copying files...");
    }
}
