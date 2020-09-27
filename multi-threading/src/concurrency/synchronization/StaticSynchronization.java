package concurrency.synchronization;

/***
 * To synchronize static members we need to have a different stratrgy as it's stored in heap
 * and shared across all the objects of a class, normal synchronization does not lock the static resources
 *
 * Note: a ++ b++ does not require lock it's just for demo
 *
 */

class StaticSample {
    static int a = 5;
    int b = 0;

    public void increment() {
        // This will require lock over class resource
        synchronized (StaticSample.class) {
            a++;
        }
        // This will acquire lock over current object
        synchronized (this) {
            b++;
        }
    }

    // above method can also be re-written using static methods like
    public static synchronized void incrementA() {
        a++;
    }

    public synchronized void incrementB() {
        b++;
    }
}

public class StaticSynchronization {
}
