/***
 * The objective of this program is to figure out demon threads from the below threads(including System Threads)
 */
public class DemonThreadDemoPart2 {
    public static void main(String args[]) {
        System.out.println("System threads...");
        Thread thread = Thread.currentThread();
        ThreadGroup threadGroup = thread.getThreadGroup();
        // trying to get root threadGroup
        while(threadGroup.getParent() != null) {
            threadGroup = threadGroup.getParent();
        }


        Thread [] threads = new Thread[10];
        int n = threadGroup.enumerate(threads);

        // You would see that Main is not a demon thread
        for(int i=0; i<n;i++) {
            System.out.println(
                    "Thread name : " + threads[i].getName() +
                            " is a deamon thread ? " + threads[i].isDaemon()

            );

        }
    }
}
