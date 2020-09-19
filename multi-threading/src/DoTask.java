public class DoTask extends Thread{
    public void run() {
        doTask();
    }
    private void doTask() {
        for(int i=1;i<=1500;i++){
            System.out.print("T");
        }
    }
}
