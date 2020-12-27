package Four;

import java.util.concurrent.*;

/***
 * Since Runnable has run method as void , public void run(), we can't use it if we want to run something asynchronusly and
 * get the return value, for this purpose we use Callable, it returns Future<T> type
 *
 * In this example we are computing add(x,y) asynchronusly, we can assume this as a heavy operation which is being done in a thread
 */

class MyMath {
    public static int add(int a, int b) {
        return a+b;
    }
}

public class CallableDemo {
    public static void main(String args[]) throws ExecutionException, InterruptedException {
        int x = 20;
        int y = 30;

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return MyMath.add(x, y);
            }
        });
        // We can run something here in parallel, till the addTask is running
        System.out.println("Main thread is running while add is being done..");

        while(! future.isDone()) {
            // wait
        }
        int z = future.get();
        System.out.println("z = " + z);
    }

}
