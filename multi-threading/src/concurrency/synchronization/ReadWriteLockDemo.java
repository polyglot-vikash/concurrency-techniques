package concurrency.synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * ReadWrite lock should be preferred over synchronized methods and blocks
 * Main benifit of read write lock is that if we have acquired write lock over a resource still read can happen.
 */
class ReadWriteSample {
    private int x;
    ReadWriteLock reLock = new ReentrantReadWriteLock();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void incr() {
        Lock lock = reLock.writeLock();

        lock.lock();
        try {
            int y = getX();
            y++;
            // just for simulation
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            setX(y);
        } finally {
            lock.unlock();
        }
    }
}

class ReadWriteSampleDemo implements Runnable {
    ReadWriteSample sample;

    public ReadWriteSampleDemo(ReadWriteSample sample) {
        this.sample = sample;
    }

    @Override
    public void run() {
        sample.incr();
    }
}


public class ReadWriteLockDemo {
    public static void main(String args[]) {
        ReadWriteSample sample = new ReadWriteSample();
        sample.setX(10);
        Thread t1 = new Thread(new ReadWriteSampleDemo(sample));
        Thread t2 = new Thread(new ReadWriteSampleDemo(sample));
        t1.start();
        t2.start();

        try {
            // join method will make control wait until the respective threads are completed
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("x = " + sample.getX());
    }


}
