package One;

public class Demo {
    public static void main(String args[]) {
        Thread t = new Thread(new MyTask());
        t.start();
        DoTask doTask = new DoTask();
        doTask.start();
        for(int i=1;i<=1500;i++){
            System.out.print("M");
        }

    }
}
