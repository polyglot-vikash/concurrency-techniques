package concurrency.synchronization;

class Writer1 extends Thread {
    Object book;
    Object pen;

    public Writer1(Object book, Object pen) {
        this.book = book;
        this.pen = pen;
    }

    @Override
    public void run() {
        synchronized (book) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (pen) {
                System.out.println("Writer1 writing");
            }
        }
    }
}

class Writer2 extends Thread {
    Object book;
    Object pen;

    public Writer2(Object book, Object pen) {
        this.book = book;
        this.pen = pen;
    }

    @Override
    public void run() {
        synchronized (pen) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (book) {
                System.out.println("Writer2 writing");
            }
        }
    }
}

public class DeadlockDemo {
    public static void main(String args[]) {
        Object book = new Object();
        Object pen = new Object();


        // This  is creating a deadlock, because writer 1 has acquired a lock on book object
        // and witer2 has acquired lock on pen object and they are waiting for pen and book respectively
        // And the program is stuck due to deadlock
        new Writer1(book, pen).start();
        new Writer2(book, pen).start();

        // To Avoid the deadlock we need to make sure the lock sequence is same among threads

        System.out.println("Main is done");
    }


}
