package thread;

/**
 * Created by jlgaoyuan on 2018/12/10.
 */
public class MutilThread2 extends Thread {

    @Override
    public void run() {
        for (int i = 0 ; i < 100 ; i++){
            System.out.println("mutil two run step :"+i);
        }
    }
}
