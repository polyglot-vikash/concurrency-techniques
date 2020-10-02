package com.java.util.concurrent.examples;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/***
 * Blocking queue is an interface with different implementations,
 * it has 2 special methods which blocks the thread when element is full or empty in queue
 * these methods are put and take
 *
 * It works in FIFO
 *
 * In this example we are creating a producer consumer app where thread will be blocked id queue is full or empty
 */

class Producer extends Thread {
    BlockingQueue<String> blockingQueue;

    public Producer(BlockingQueue<String> queue) {
        this.blockingQueue = queue;
    }

    @Override
    public void run() {
        for(int i=1; i<=10;i++) {
            String message = "Message : " + Integer.toString(i);
            try {
                blockingQueue.put(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Produced " + message);
        }
    }

}

class Consumer extends Thread {
    BlockingQueue<String> blockingQueue;

    public Consumer(BlockingQueue<String> queue) {
        this.blockingQueue = queue;
    }

    @Override
    public void run() {
        for(int i=1; i<=10;i++) {
          String message = null;
            try {
                message = blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consumed :  " + message);
        }
    }

}


public class ProducerConsumerUsingBlockingQueue {
    public static void main(String args[]) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        Thread producer= new Producer(blockingQueue);
        Thread consumer = new Consumer(blockingQueue);

        producer.start();
        consumer.start();

        try {
            // join method will make control wait until the respective threads are completed
            producer.join();
            consumer.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Done ....");
    }


}
