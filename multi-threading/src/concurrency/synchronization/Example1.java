package concurrency.synchronization;

class Sample {
    private int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    // A method to demonstrate change in the shared variable x
    public synchronized void incr() {
        int y = getX();
        y++;
        // For simulation we need this so that other thread gets the cpu before actual set happens
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        setX(y);
    }


}

class MyThread extends Thread {
    Sample object;

    public MyThread(Sample obj) {

        this.object = obj;
    }

    public void run() {
        object.incr();
    }

}

public class Example1 {
    public static void main(String args[]) {
        Sample obj = new Sample();
        obj.setX(10);
        MyThread t1 = new MyThread(obj);
        MyThread t2 = new MyThread(obj);
        t1.start();
        t2.start();

        try {
           t1.join();
           t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(obj.getX());
    }
}
