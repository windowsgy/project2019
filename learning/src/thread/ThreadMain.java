package thread;

/**
 * Created by jlgaoyuan on 2018/12/10.
 *
 */
public class ThreadMain {
    public static void main(String[] args) {
        MutilThread1 thread1 = new MutilThread1();
        MutilThread2 thread2 = new MutilThread2();
        thread1.start();
        thread2.start();
        for (int i = 0 ; i < 100 ; i ++){
            System.out.println("main run step : "+ i );
        }
    }
}
