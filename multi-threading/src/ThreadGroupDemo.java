/*** ThreadGroups are used to create a logical unit of threads for various operations like,
 * interruption, setting priorities etc.
 */

class MyDemoTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class  ThreadGroupDemo{
    public static void main(String args[]) {
        ThreadGroup myDemoGroup = new ThreadGroup("myGroup");
        myDemoGroup.setMaxPriority(8);
        Thread myTaskThread = new Thread(myDemoGroup, new MyDemoTask(), "DemoThread");
        myTaskThread.start();

        System.out.println("System threads...");
        Thread thread = Thread.currentThread();
        ThreadGroup threadGroup = thread.getThreadGroup();
        // trying to get root threadGroup
        while(threadGroup.getParent() != null) {
            threadGroup = threadGroup.getParent();
        }
        threadGroup.list();
    }

}
