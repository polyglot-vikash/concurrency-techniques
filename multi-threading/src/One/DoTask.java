package One;

/***
 *  This method is not recommended in favor of runnable:
 *  1.  It internally implements Runnable
 *  2.  Runnable can be written using lambda code.
 */
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
