import java.util.TreeMap;

/***
 * A demon thread runs in the background, it is used for purposed like garbage collection
 * some stats collection etc.
 */

class MyDemonThreadTask implements Runnable{
    @Override
    public void run() {
        for(;;) {
            System.out.println("Demon Thread");
        }
    }
}
public class DemonThreadDemo {
    public static void main(String args[]) {
        Thread thread = new Thread(new MyDemonThreadTask());
        thread.setDaemon(true);
        thread.start();

        for(int i=0;i<=200;i++) {
            System.out.println("Main Thread");
        }
    }




}
