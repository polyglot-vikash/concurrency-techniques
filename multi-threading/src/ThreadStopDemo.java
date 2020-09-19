class MyThread extends Thread {
    public void run() {
        for (; ; ) {
            if (interrupted()) {
                // Here we usually do all Cleanup
                System.out.println("Cleaning up...");
                break;
            }
            System.out.print("T");
        }
    }
}

public class ThreadStopDemo {
    public static void main(String args[]) throws Exception {
        MyThread myThread = new MyThread();
        myThread.start();
        Thread.sleep(2000);
        // We should not stop a thread abruptly, rather we should use interrupt,
        // interupt gives chance of cleanup
        // myThread.stop();
        myThread.interrupt();
    }

}
